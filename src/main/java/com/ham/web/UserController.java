package com.ham.web;

import com.ham.constance.FileSubfix;
import com.ham.constance.NameSpace;
import com.ham.entity.User;
import com.ham.service.FileService;
import com.ham.service.UserService;
import com.ham.service.strategy.PersonalUserStrategy;
import com.ham.util.FileUtils;
import com.ham.util.ReflectUtils;
import com.ham.vo.OpResult;
import com.ham.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by hamsbon on 2017/2/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @Autowired
    private PersonalUserStrategy personalUserStrategy;

    @PostMapping("/update")
    @ResponseBody
    @RequiresUser
    OpResult<String> update(User user,HttpSession session){

        User currentUser = (User)SecurityUtils.getSubject().getPrincipal();
//        User currentUser = (User)session.getAttribute("user");
        Long id = currentUser == null ? null : currentUser.getId();
        user.setId(id);
        if("".equals(user.getPassword())){
            user.setPassword(null);
        }
        OpResult<String> result = userService.updateInfo(user);
        if(result.isSuccess()){
            UserVO userVO = userService.getUserInfo(personalUserStrategy,id).getData().get(0);
            session.setAttribute("user",userVO);
            if(userVO.getPassword().equals(currentUser.getPassword())){
                ReflectUtils.updateObj(currentUser,userVO);
            }else{
                SecurityUtils.getSubject().logout();
            }
        }
        return result;
    }

    @RequestMapping("/login")
    public String login( HttpServletRequest request,HttpSession session,Model model)
            throws Exception {
        OpResult<String> result = new OpResult<>(false,null,"登录失败");

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
//        session.setAttribute("user", user);

        // shiro在认证过程中出现错误后将异常类路径通过request返回
        String exceptionClassName = (String) request
                .getAttribute("shiroLoginFailure");
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            result.setOpMsg("账号不存在");
        } else if (IncorrectCredentialsException.class.getName().equals(
                exceptionClassName)) {
            result.setOpMsg("用户名/密码错误");
        }else{
            result.setSuccess(true);
            result.setOpMsg("");
        }
        session.setAttribute("opResult",result);
        session.setAttribute("error_msg",result.getOpMsg());
        return "login";
    }



    //没有Shiro之前的登录
//    @PostMapping("/login")
//    @ResponseBody
//    public OpResult<User> login(HttpSession session, String username, String password, Boolean rememberMe, HttpServletResponse response) throws Exception {
//        OpResult<User> result = userService.loginIn(username, password);
//        if (result.isSuccess()) {
//            User user = result.getData();
//            session.setAttribute("user", user);
//
//            if (rememberMe) {
//                String key = MD5Utils.getMD5(String.valueOf(random.nextLong()) + EncryConst.REMEMBER_SALT + System.currentTimeMillis());
//
//                Jedis jedis = RedisUtils.getJedis();
//                jedis.set(key,JsonUtils.toJSONString(user));//置于缓存
//                jedis.expire(key,3600*24*7);//7天有效期
//
//                Cookie cookie = new Cookie("userMD5", key);
//                cookie.setMaxAge(3600*24*7);
//                response.addCookie(cookie);
//            }
//        }
//        return result;
//    }

    @PostMapping("/uploadhead")
    @ResponseBody
    public OpResult<String> uploadHead(@RequestParam("file")MultipartFile file){
        OpResult<String> result = new OpResult<>(false,null,"上传失败");
        if(file!=null){
            String subfix = FileUtils.getSubfix(file.getOriginalFilename());
            if(FileSubfix.PIC_SUBFIX.contains(subfix)){
                result = fileService.upload(NameSpace.USER_HEAD_DIR,file);
            }else{
                result.setOpMsg("文件必须为图片格式");
            }
        }
        return result;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "login";
    }


    //Shiro之前的登出
//    @PostMapping("/logout")
//    public String logout(HttpSession session, HttpServletRequest request) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        for(Cookie cookie:cookies){
//            cookie.setMaxAge(0);
//        }
//        session.invalidate();
//        return "/login";
//    }


    @PostMapping("/register")
    @ResponseBody
    public OpResult<String> register(User user) throws Exception {
        OpResult<String> result = userService.register(user);
        return result;
    }

//    @GetMapping("/head/{id}")
//    @Description("获取头像")
//    public void getHead(@PathVariable("id")Long id , HttpServletResponse response) throws IOException {
//        OutputStream os = response.getOutputStream();
//        UserVO user = userService.getUserInfo(personalUserStrategy, Collections.singletonList(id)).getData().get(0);
//        OpResult<byte[]> r = fileService.getFile(user.getPhotoUrl());
//        if(r.isSuccess()){
//            os.write(r.getData());
//            os.flush();
//        }
//    }
//
//    @GetMapping("/name/{id}")
//    @Description("获取用户名")
//    @ResponseBody
//    public String getName(@PathVariable("id")Long id , HttpServletResponse response) throws IOException {
//        OutputStream os = response.getOutputStream();
//        UserVO user = userService.getUserInfo(personalUserStrategy, Collections.singletonList(id)).getData().get(0);
//        return user==null?"":user.getUsername();
//    }



}
