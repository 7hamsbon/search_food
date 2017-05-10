<%@ page import="com.ham.entity.User" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/3/9
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<html>
<head>
    <%@include file="common.jsp"%>
    <script src="/js/personalInfo.js"></script>
    <link rel="stylesheet" href="/css/personalInfo.css">
    <script type="text/javascript">
        $(function () {
            personalInfo.page_init();
        });

        var photo_url="<shiro:principal property='photoUrl'/>";
        <%--var photo_url="${sessionScope.user.photoUrl}"/>";--%>
    </script>
</head>
<body >
    <%@include file="head.jsp"%>

    <div id="error_modal" class="ui small test modal scrolling transition visible active" >
        <%--<div style="margin-left: 10px;font-size: larger">Please check your form request~</div>--%>
        <div class="header">Please check your form request~</div>
        <div class="content" id="error_modal_content">

        </div>
        <div class="actions">
            <div class="ui negative button">OK</div>
        </div>
    </div>


    <div id="example1">
        <div class="ui grid" >
            <div class="ten wide column" style="margin-left: 20px">
                <div class="ui grid">
                    <div class="ui form">
                        <div class="row">
                            <div class="three fields">
                                <div class="field">
                                    <label>Password</label>
                                    <div class="ui icon input">
                                        <i class="lock icon"></i>
                                        <input type="password" placeholder="New Password" id="pass">
                                    </div>
                                </div>
                                <div class="field">
                                    <label>Address</label>
                                    <div class="ui icon input">
                                        <i class="marker icon"></i>
                                        <input type="text" placeholder="Address" id="addr" value="<shiro:principal property='address'/> ">
                                        <%--<input type="text" placeholder="Address" id="addr" value="${sessionScope.user.address}">--%>
                                    </div>
                                </div>
                                <div class="field">
                                    <label>E-Mail</label>
                                    <div class="ui icon input">
                                        <i class="Mail icon"></i>
                                        <%--<input type="email" placeholder="E-Mail" id="email" value="${sessionScope.user.email}">--%>
                                        <input type="email" placeholder="E-Mail" id="email" value="<shiro:principal property='email'/>">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="fields">
                                <div class="field">
                                    <label>Gender</label>
                                    <select class="ui dropdown" name="gender" id="gender">
                                        <option value="1">Male</option>
                                        <option value="0" <c:if test="<%=((User)SecurityUtils.getSubject().getPrincipal()).getGender()==0%>">selected="selected"</c:if>>Female</option>
                                        <%--<option value="0" <c:if test="${sessionScope.user.gender == 0}">selected="selected"</c:if>>Female</option>--%>
                                    </select>
                                </div>
                                <div class="field">
                                    <label>Age</label>
                                    <div class="ui icon input">
                                        <i class="Child icon"></i>
                                        <%--<input type="text" placeholder="Age" id="age" value="<shiro:principal property='age' defaultValue=""/>">--%>
                                        <input type="text" placeholder="Age" id="age" value="${sessionScope.user.age}">
                                    </div>
                                </div>
                                <div class="field">
                                    <label>Phone</label>
                                    <div class="ui icon input">
                                        <i class="phone icon"></i>
                                        <%--<input type="text" placeholder="Phone" id="phone" value="${sessionScope.user.phone}">--%>

                                        <input type="text" placeholder="Phone" id="phone" value="<shiro:principal property='phone' defaultValue=''/>">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="field">
                                <label>Profile</label>

                                <%--<textarea style="resize: none" id="summ" placeholder="Profile"><shiro:principal property='summary' defaultValue=''/></textarea>--%>
                                <textarea style="resize: none" id="summ" placeholder="Profile">${sessionScope.user.summary}</textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="five wide column" >
                <form id="fo" enctype="multipart/form-data" action="${pageContext.request.contextPath}/user/uploadhead">
                    <div class="ui center aligned container">
                        <div id="user_head" class="user header " style="background: url('/pic?filePath=<%=((User)SecurityUtils.getSubject().getPrincipal()).getPhotoUrl()%>') no-repeat;-webkit-background-size: 150px 150px;">
                        <%--<div id="user_head" class="user header " style="background: url('/pic?filePath=${sessionScope.user.photoUrl}') no-repeat;-webkit-background-size: 150px 150px;">--%>
                            <input class="fileInput" type="file" name="file" id="file" />
                        </div><br/>
                        <div class="ui basic big purple label"><shiro:principal property="username"/> </div>
                        <%--<div class="ui basic big purple label">${sessionScope.user.username}</div>--%>
                    </div>
                </form>
                <br/>
                <div class="ui center aligned container" style="padding-left: 9px" onclick="personalInfo.update_user_info(event)">
                    <button class="ui big positive button">Save!</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>