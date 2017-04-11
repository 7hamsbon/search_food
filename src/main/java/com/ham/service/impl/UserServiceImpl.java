package com.ham.service.impl;

import com.ham.constance.EntityConst;
import com.ham.dao.UserMapper;
import com.ham.entity.User;
import com.ham.entity.UserExample;
import com.ham.service.UserService;
import com.ham.service.strategy.BaseUserStrategy;
import com.ham.util.MD5Utils;
import com.ham.vo.OpResult;
import com.ham.vo.UserVO;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hamsbon on 2017/2/15.
 */
@Service
public class UserServiceImpl implements UserService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    @Override
    public OpResult<String> register(User user) {
        OpResult<String> opResult = new OpResult<String>();
        opResult.setSuccess(false);
        if(user != null){
            if(!StringUtils.isNullOrEmpty(user.getPassword())&&!StringUtils.isNullOrEmpty(user.getUsername())){
                user.setPassword(MD5Utils.getMD5(user.getPassword()));
                int result = userMapper.insertSelective(user);
                if(result > 0){
                    opResult.setSuccess(true);
                    opResult.setOpMsg("注册成功");
                }else{
                    opResult.setOpMsg("用户名已存在");
                }
            }else{
                opResult.setOpMsg("用户账号、密码不可为空");
            }
        }else{
            opResult.setOpMsg("用户数据不可为空");
        }
        return opResult;
    }

    @Override
    public OpResult<User> loginIn(String username, String password) {
        OpResult<User> opResult = new OpResult<User>(false,"用户名不可为空");
        if(!StringUtils.isNullOrEmpty(username)){
            UserExample example = new UserExample();
            UserExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameEqualTo(username);
            User user = null;
            List<User> users = userMapper.selectByExample(example);
            if(users != null && users.size()>0){
                user = users.get(0);
            }
            if(user != null && !StringUtils.isNullOrEmpty(password) && user.getValid()== EntityConst.USER_VALID){
                if(MD5Utils.getMD5(password).equals(user.getPassword())){
                    opResult.setSuccess(true);
                    opResult.setData(user);
                    opResult.setOpMsg("登录成功");
                }else{
                    opResult.setOpMsg("密码错误");
                }
            }else{
                if(user == null){
                    opResult.setOpMsg("用户不存在");
                }else if(StringUtils.isNullOrEmpty(password)){
                    opResult.setOpMsg("密码不可为空");
                }else{
                    opResult.setOpMsg("用户不可用");
                }
            }
        }
        return opResult;
    }

    @Override
    public OpResult<String> updateInfo(User user) {
        OpResult<String> result = new OpResult<>(false,null,"更新失败");
        if(user!=null && user.getId()!=null){
            if(!StringUtils.isNullOrEmpty(user.getPassword())){
                user.setPassword(MD5Utils.getMD5(user.getPassword()));
            }
            user.setUsername(null);
            int r = userMapper.updateByPrimaryKeySelective(user);
            if(r > 0){
                result.setSuccess(true);
                result.setOpMsg("更新成功");
            }else{
                result.setOpMsg("用户不存在，请重新登录");
            }
        }
        return result;
    }

    @Override
    public OpResult<List<UserVO>> getUserInfo(BaseUserStrategy strategy,Object param) {
        OpResult<List<UserVO>> result = new OpResult<>(false,null,"请选择一种获取用户策略！");
        if(strategy!=null){
            if(param !=null){
                List<UserVO> users = strategy.getCompleteUsers(param);
                if(users==null||users.size()<=0){
                    result.setOpMsg("查询用户不存在！");
                }else{
                    result.setSuccess(true);
                    result.setData(users);
                    result.setOpMsg("");
                }
            }else{
                result.setOpMsg("id不存在！");
            }
        }
        return result;
    }

    @Override
    public OpResult<User> getUserByUsername(String username) {
        OpResult<User> result = new OpResult<>(false,null,"查询失败");

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> ul = userMapper.selectByExample(example);
        User user = ul==null || ul.size()==0 ? null : ul.get(0);
        result.setSuccess(true);
        result.setData(user);
        result.setOpMsg("");

        return result;
    }

    @Override
    public OpResult<String> updateUserVaild(Long id,int valid) {
        OpResult<String> result = new OpResult<>(false,null,"用户更新失败!");
        if(id!=null){
            if(!EntityConst.getUserValidList().contains(valid)){
                result.setOpMsg("用户类型错误");
            }else{
                User user = userMapper.selectByPrimaryKey(id);
                user.setValid(valid);
                userMapper.updateByPrimaryKey(user);
                result.setSuccess(true);
                result.setOpMsg("用户类型更新成功！");
            }
        }
        return result;
    }

    @Override
    public OpResult<List<UserVO>> getSameFans(Long userId1, Long userId2) {
        OpResult<List<UserVO>> result = new OpResult<>(false,null,"没有相同的粉丝");
        if(userId1!=null && userId2!=null){
            List<UserVO> list = userMapper.getSameFans(userId1,userId2);
            if(list!=null && list.size()>0){
                result.setSuccess(true);
                result.setData(list);
                result.setOpMsg("");
            }
        }
        return result;
    }

    @Override
    public OpResult<List<UserVO>> getSameFollowers(Long userId1, Long userId2) {
        OpResult<List<UserVO>> result = new OpResult<>(false,null,"没有相同的关注者");
        if(userId1!=null && userId2!=null){
            List<UserVO> list = userMapper.getSameFollower(userId1,userId2);
            if(list!=null && list.size()>0){
                result.setSuccess(true);
                result.setData(list);
                result.setOpMsg("");
            }
        }
        return result;
    }

}
