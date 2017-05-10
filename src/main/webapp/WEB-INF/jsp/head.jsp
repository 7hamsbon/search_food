<%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/3/17
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%--首页头--%>
<div id="head" class="ui fixed top sticky" style="left:0px;width: 100% !important; !important;">
    <div class="ui segment" style="padding-left: 10%;background-color: #37474f;height: 60px">
            <shiro:authenticated>
                <div id="homepage_btn" onclick="common.redirect_to_homepage()" class="circular ui inverted olive icon button"  data-inverted="" data-tooltip="Home" data-position="bottom left"><i class="loading icon home"></i></div>
            </shiro:authenticated>
        <div class="circular ui inverted grey icon button" onclick="common.redirect_to_searchpage()" data-inverted="" style="margin-left: 20px;"  data-tooltip="Search" data-position="bottom left"><i class="loading icon search" ></i></div>
        <shiro:authenticated>

            <div class="circular ui inverted orange icon button" onclick="common.redirect_to_personal()"  data-inverted="" style="margin-left: 20px;"  data-tooltip="Setting" data-position="bottom left"><i class="loading icon Privacy" ></i></div>
            <div class="circular ui inverted yellow icon button" onclick="common.redirect_to_collectpage()"  data-inverted="" style="margin-left: 20px;"  data-tooltip="Collected Blog" data-position="bottom left"><i class="loading Empty Star icon" ></i></div>
            <div onclick="common.logout()" class="circular ui inverted purple icon button" data-inverted="" style="margin-left: 20px;"  data-tooltip="Sign out" data-position="bottom left"><i class="loading icon Sign Out" ></i></div>
        </shiro:authenticated>
        <shiro:notAuthenticated>
            <div onclick="common.redirect_to_loginpage()" class="circular ui inverted purple icon button" data-inverted="" style="margin-left: 20px;"  data-tooltip="Sign in" data-position="bottom left"><i class="loading icon Sign in" ></i></div>
        </shiro:notAuthenticated>
    </div>
</div>
