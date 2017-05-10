package com.ham.config;

import com.ham.realm.AuthShiroRealm;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hamsbon on 2017/3/27.
 */
@Configuration
public class SecurityConfig implements EnvironmentAware{

    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private RelaxedPropertyResolver propertyResolver;

    public void setEnvironment(Environment env) {
        this.propertyResolver = new RelaxedPropertyResolver(env, "spring.redis.");
    }
    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean(name = "myShiroRealm")
    public AuthShiroRealm myShiroRealm(){
        AuthShiroRealm realm = new AuthShiroRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }

    /**
     * 配置shiro redisManager
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(propertyResolver.getProperty("host"));
        redisManager.setPort(Integer.valueOf(propertyResolver.getProperty("port")));
        redisManager.setExpire(1800);// 配置过期时间
        redisManager.setTimeout(Integer.valueOf(propertyResolver.getProperty("timeout")));
        // redisManager.setPassword(password);
        return redisManager;
    }
    /**
     * cacheManager 缓存 redis实现
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     */
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
    /**
     * shiro session的管理
     */
    public DefaultWebSessionManager SessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间7天 ,单位秒;-->
        simpleCookie.setMaxAge(604800);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(AuthShiroRealm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(realm);
        //用户授权/认证信息Cache, 采用EhCache缓存
        //securityManager.setCacheManager(getEhCacheManager());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis,注入这个会在退出时报错
        //securityManager.setSessionManager(SessionManager());
        //注入记住我管理器;
        securityManager.setRememberMeManager(rememberMeManager());

        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
//        ShiroFilterFactoryBean factoryBean = new MyShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        Map<String,Filter> filters = new HashMap();
        filters.put("authc",formAuthenticationFilter());
        factoryBean.setFilters(filters);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        factoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的连接
        factoryBean.setSuccessUrl("/homePage");
//        factoryBean.setUnauthorizedUrl("/403");
        loadShiroFilterChain(factoryBean);
        logger.info("shiro拦截器工厂类注入成功");
        return factoryBean;
    }

    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter(){
            @Override
            protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
                WebUtils.getAndClearSavedRequest(request);
                WebUtils.redirectToSavedRequest(request,response,"/homePage");
                return false;
            }
        };
        formAuthenticationFilter.setLoginUrl("/user/login");
        formAuthenticationFilter.setSuccessUrl("/homePage");
        return formAuthenticationFilter;
    }

    /**
     * 加载ShiroFilter权限控制规则
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean factoryBean) {
        /**下面这些规则配置最好配置到配置文件中*/
        Map<String, String> filterChainMap = new LinkedHashMap<String, String>();
        //配置记住我或认证通过可以访问的地址
        filterChainMap.put("/user/logout", "logout");
        filterChainMap.put("/homePage", "user");
        filterChainMap.put("/", "user");
        /** authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器
         * org.apache.shiro.web.filter.authc.FormAuthenticationFilter */
        // anon：它对应的过滤器里面是空的,什么都没做,可以理解为不拦截
        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        filterChainMap.put("/friend/add", "anon");
        filterChainMap.put("/register", "anon");
        filterChainMap.put("/user/register", "anon");
        filterChainMap.put("/fansPage/*", "anon");
        filterChainMap.put("/followerPage/*", "anon");
        filterChainMap.put("/blogs/*", "anon");
        filterChainMap.put("/comment/*", "anon");
        filterChainMap.put("/user/uploadhead", "anon");
        filterChainMap.put("/searchPage", "anon");
        filterChainMap.put("/searchResult", "anon");
        filterChainMap.put("/js/**", "anon");
        filterChainMap.put("/css/**", "anon");
        filterChainMap.put("/images/**", "anon");
        filterChainMap.put("/pic", "anon");
        filterChainMap.put("/error", "anon");
        filterChainMap.put("/**", "authc");

        factoryBean.setFilterChainDefinitionMap(filterChainMap);
    }





    //
//
//    @Bean
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
//        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
//
//        // 必须设置 SecurityManager
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//
//        Map<String,Filter> filters = new HashMap();
//        filters.put("authc",formAuthenticationFilter());
//        shiroFilterFactoryBean.setFilters(filters);
//
//        //拦截器.
//        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
//
//        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
////        filterChainDefinitionMap.put("/user/logout", "logout");
//
//        //配置记住我或认证通过可以访问的地址
//        filterChainDefinitionMap.put("/homePage", "user");
////        filterChainDefinitionMap.put("/", "user");
//
//
//        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
//        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/error*", "anon");
//        filterChainDefinitionMap.put("/searchPage", "anon");
//        filterChainDefinitionMap.put("/searchResult", "anon");
//        filterChainDefinitionMap.put("/js/**", "anon");
//        filterChainDefinitionMap.put("/css/**", "anon");
//        filterChainDefinitionMap.put("/images/**", "anon");
//        filterChainDefinitionMap.put("/pic", "anon");
//        filterChainDefinitionMap.put("/**", "anon");
//
//        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/homePage");
//        //未授权界面;
////        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//    }
//
//
//    @Bean
//    public SecurityManager securityManager(){
//        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
//        //设置realm.
//        securityManager.setRealm(myShiroRealm());   //做验证是通过Reaml来做的,所以在这必须通过securityManager来设置Reaml
//        //注入缓存管理器
//        securityManager.setCacheManager(ehCacheManager());
//        securityManager.setRememberMeManager(cookieRememberMeManager());
//        return securityManager;
//    }
//
//
//    public AuthShiroRealm myShiroRealm(){
//        AuthShiroRealm myShiroRealm = new AuthShiroRealm();//这里实例化一个我们自己写的MyShiroRealm类
//        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//        return myShiroRealm;
//    }
//
//
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher(){
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""));
//
//        return hashedCredentialsMatcher;
//    }
//
//    @Bean
//    LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean
//    @DependsOn({"lifecycleBeanPostProcessor"})
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        return defaultAdvisorAutoProxyCreator;
//    }
//
//    //开启shiro aop注解支持
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//    //注入缓存
//    public EhCacheManager ehCacheManager(){
//        EhCacheManager cacheManager=new EhCacheManager();
//        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
//        return cacheManager;
//    }
//
////    @Bean
////    public LogoutFilter logoutFilter(){
////        LogoutFilter logoutFilter = new LogoutFilter();
////        logoutFilter.setRedirectUrl("/login");
////        return logoutFilter;
////    }
//
//    @Bean
//    public FormAuthenticationFilter formAuthenticationFilter(){
//        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter(){
//            @Override
//            protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
//                WebUtils.getAndClearSavedRequest(request);
//                WebUtils.redirectToSavedRequest(request,response,"/homePage");
//                return false;
//            }
//        };
//        formAuthenticationFilter.setLoginUrl("/user/login");
//        formAuthenticationFilter.setSuccessUrl("/homePage");
//        return formAuthenticationFilter;
//    }
//
//    @Bean
//    public SimpleCookie simpleCookie(){
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        simpleCookie.setMaxAge(604800);//7天
//        return simpleCookie;
//    }
//
//    @Bean
//    public CookieRememberMeManager cookieRememberMeManager(){
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(simpleCookie());
//        return cookieRememberMeManager;
//    }

}
