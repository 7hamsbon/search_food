package com.ham.web;

import com.ham.constance.FileSubfix;
import com.ham.constance.NameSpace;
import com.ham.entity.User;
import com.ham.service.BlogService;
import com.ham.service.FileService;
import com.ham.service.UserService;
import com.ham.service.strategy.PersonalUserStrategy;
import com.ham.util.FileUtils;
import com.ham.vo.BlogVO;
import com.ham.vo.OpResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by hamsbon on 2017/2/21.
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private PersonalUserStrategy personalUserStrategy;

    /**
     * 上传博客图片获得文件路径
     */
    @PostMapping("/uploadPhoto")
    @ResponseBody
    @RequiresPermissions("blog:uploadPhoto")
    public OpResult<String> uploadPhoto(@RequestParam("food")MultipartFile file){
        OpResult<String> result = new OpResult<>(false,null,"上传失败");
        if(file != null){
            String subfix = FileUtils.getSubfix(file.getOriginalFilename());
            if(FileSubfix.PIC_SUBFIX.contains(subfix)){
                result = fileService.upload(NameSpace.BLOG_PHOTO_DIR,file);
            }else{
                result.setOpMsg("文件必须为图片格式");
            }
        }
        return result;
    }

    /**
     * 发布博客
     */
    @RequiresPermissions("blog:publish")
    @PostMapping("/publish")
    @ResponseBody
    public OpResult<BlogVO> publish(BlogVO blog){
        OpResult<BlogVO> result;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        User user = (User)session.getAttribute("user");
        if(blog == null && user!=null){
            result = new OpResult<>(false,null,"发布失败");
        }else{
            blog.setUserId(user.getId());
            result = blogService.publish(blog);
            //设置用户头像
            blog.setUserHeadPath(userService.getUserInfo(personalUserStrategy,user.getId()).getData().get(0).getPhotoUrl());
        }
        return result;
    }

    /**
     * 删除博客
     */
    @PostMapping("/delete/{id}")
    @ResponseBody
    @RequiresPermissions("blog:delete")
    public OpResult<String> delete(@PathVariable("id") Long id){
        return blogService.delete(id);
    }

}
