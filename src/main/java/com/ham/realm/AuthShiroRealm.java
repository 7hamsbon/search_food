package com.ham.realm;

import com.ham.dao.PermissionMapper;
import com.ham.dao.RoleMapper;
import com.ham.dao.RolePermissionMapper;
import com.ham.entity.*;
import com.ham.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hamsbon on 2017/3/27.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class AuthShiroRealm extends AuthorizingRealm {

    @Resource
            @Lazy
    UserService userService;

    @Resource
            @Lazy
    RoleMapper roleMapper;

    @Resource
            @Lazy
    PermissionMapper permissionMapper;

    @Resource
            @Lazy
    RolePermissionMapper rolePermissionMapper;


    @Override
    public String getName() {
        return "AuthShiroRealm";
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        User user = userService.getUserByUsername(username).getData();
        if(user == null){
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, // 用户名
                user.getPassword(), // 密码
                getName() // realm name
        );
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("principal.class-------->>>"+principalCollection.getClass().getName());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User)principalCollection.getPrimaryPrincipal();
        Integer roleId = user.getRole();
        RolePermissionExample example = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);

        for(RolePermission rolePermission:rolePermissions){
            Integer permissionId = rolePermission.getPermissionId();
            Permission permission = permissionMapper.selectByPrimaryKey(permissionId);
            authorizationInfo.addStringPermission(permission.getName());
        }

        Role role = roleMapper.selectByPrimaryKey(roleId);
        authorizationInfo.addRole(role.getName());

        return authorizationInfo;
    }

}
