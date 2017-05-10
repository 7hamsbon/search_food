package com.ham.web;

import com.ham.constance.EntityConst;
import com.ham.entity.Music;
import com.ham.entity.User;
import com.ham.service.*;
import com.ham.service.strategy.*;
import com.ham.vo.BlogVO;
import com.ham.vo.OpResult;
import com.ham.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * Created by hamsbon on 2017/2/28.
 * 视图Controller
 */
@Controller
@Description("视图相关Controller")
public class ViewController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private MusicService musicService;

    @Autowired
    private TimeOrderBlogStrategy timeOrderBlogStrategy;

    @Autowired
    private CollectBlogStategy collectBlogStategy;

    @Autowired
    private FansUserStrategy fansUserStrategy;

    @Autowired
    private FollowerUserStrategy followerUserStrategy;

    @Autowired
    private PersonalUserStrategy personalUserStrategy;

    @Autowired
    private FuzzySearchUserStrategy fuzzySearchUserStrategy;

    @GetMapping("/homePage")
    @Description("个人主页")
    @RequiresUser
    public String homePage(Model model, HttpSession session){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        User user = (User) session.getAttribute("user");
        session.setAttribute("user",user);
        Long id = user.getId();
        OpResult<List<UserVO>> userInfo = userService.getUserInfo(personalUserStrategy,id);
        List<Long> followers = friendshipService.getFollowers(id);
        followers.add(id);
        OpResult<List<BlogVO>> blogs = blogService.getBlogsByUserIds(timeOrderBlogStrategy,followers);
        if(blogs.isSuccess()){
            likeService.loadIsLike(blogs.getData(),id);
            collectService.loadIsCollect(blogs.getData(),id);
        }
        OpResult<List<Music>> sp = musicService.getMusics(id);
        if(sp.getData().size()<1){
            sp = musicService.getMusics(EntityConst.DEFAULT_MUSIC_OWNER_ID);
        }
        model.addAttribute("musicList",sp.getData());
        model.addAttribute("userInfo",userInfo.getData().get(0));
        model.addAttribute("blogs",blogs.getData());
        return "homepage";
    }

    @GetMapping("/personalInfo")
    @Description("个人信息页面")
    @RequiresUser
    public String personalPage(){
        return "personalInfo";
    }

    @GetMapping("/fansPage/{id}")
    @Description("查看粉丝页面")
    public String fansPage(@PathVariable("id")Long id,Model model){
        OpResult<List<UserVO>> user = userService.getUserInfo(personalUserStrategy,id);
        OpResult<List<UserVO>> fans = userService.getUserInfo(fansUserStrategy,id);
        model.addAttribute("fans",fans);
        model.addAttribute("user",user);
        return "fansPage";
    }

    @GetMapping("/followerPage/{id}")
    @Description("查看关注者页面")
    public String followerPage(Model model,@PathVariable("id")Long id){
        OpResult<List<UserVO>> fans = userService.getUserInfo(personalUserStrategy,id);
        OpResult<List<UserVO>> followers = userService.getUserInfo(followerUserStrategy,id);
        model.addAttribute("fans",fans);
        model.addAttribute("followers",followers);
        return "followerPage";
    }

    @GetMapping("/searchPage")
    @Description("搜索页面")
    public String searchPage(){
        return "searchPage";
    }

    @GetMapping("/searchResult")
    @Description("搜索结果页面")
    public String searchResult(Model model,String keyword){
//        User user = (User)session.getAttribute("user");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        OpResult<List<UserVO>> users = userService.getUserInfo(fuzzySearchUserStrategy,keyword);
        OpResult<List<BlogVO>> blogs = blogService.fuzzySearchBlogByKeyword(keyword);
        if(blogs.isSuccess()){
            likeService.loadIsLike(blogs.getData(),user==null?-1L:user.getId());
            collectService.loadIsCollect(blogs.getData(),user==null?-1L:user.getId());
        }
        model.addAttribute("users",users);
        model.addAttribute("blogs",blogs);
        return "searchResult";
    }

    @GetMapping("/blogs/{id}")
    @Description("个人博客页面")
    public String blogs(Model model, @PathVariable("id")Long id){
//        User user = (User) session.getAttribute("user");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        OpResult<List<BlogVO>> blogs = blogService.getBlogsByUserIds(timeOrderBlogStrategy, Collections.singletonList(id));
        if(blogs.isSuccess()){
            likeService.loadIsLike(blogs.getData(),user==null?-1L:user.getId());
            collectService.loadIsCollect(blogs.getData(),user==null?-1L:user.getId());
        }
        OpResult<List<UserVO>> userInfo= userService.getUserInfo(personalUserStrategy,id);
        OpResult<List<UserVO>> sameFans = userService.getSameFans(user!=null && !id.equals(user.getId())?user.getId():-1,id);
        OpResult<List<UserVO>> sameFollowers = userService.getSameFollowers(user!=null && !id.equals(user.getId())?user.getId():-1,id);
        Long followersCount = friendshipService.getSameFollowersCount(user!=null && !id.equals(user.getId())?user.getId():-1,id).getData();
        Long fansCount = friendshipService.getSameFansCount(user!=null && !id.equals(user.getId())?user.getId():-1,id).getData();
        Boolean isFollow = friendshipService.isFollow(user!=null && !id.equals(user.getId())?user.getId():-1,id).getData();
        model.addAttribute("isFollow",isFollow==null?false:isFollow);
        model.addAttribute("personalBlogs",blogs);
        model.addAttribute("sameFans",sameFans);
        model.addAttribute("sameFollowers",sameFollowers);
        model.addAttribute("fansCount",fansCount);
        model.addAttribute("followersCount",followersCount);
        model.addAttribute("userResult",userInfo);
        model.addAttribute("isSelf",user!=null && id.equals(user.getId()));
        return "blogs";
    }

    @GetMapping("/collectPage")
    @Description("收藏页面")
    @RequiresUser
    public String collect(Model model){
//        User user = (User)session.getAttribute("user");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long id = user.getId();
        OpResult<List<BlogVO>> blogs = blogService.getBlogsByUserIds(collectBlogStategy,Collections.singletonList(id));
        likeService.loadIsLike(blogs.getData(),id);
        collectService.loadIsCollect(blogs.getData(),id);
        model.addAttribute("blogs",blogs);
        return "collect";
    }

    @GetMapping("/login")
    @Description("登录页面")
    public String loginView() throws Exception {
        return "login";
    }

    @GetMapping("/register")
    @Description("注册页面")
    public String registerView() {
        return "register";
    }

}
