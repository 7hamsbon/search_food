<%@ page import="com.ham.entity.User" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="com.ham.vo.OpResult" %>
<%@ page import="com.mysql.jdbc.StringUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/2/16
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/htmleaf-demo.css">
    <link rel="stylesheet" type="text/css" href="/css/login.css">
    <script src="http://cdn.bootcss.com/jquery/3.1.1/jquery.js"></script>
    <link rel="shortcut icon" href="/images/website_logo2.ico" type="image/x-icon">
    <script type="application/javascript">
        $(function(){
            <shiro:authenticated>
                location.href='/homePage';
            </shiro:authenticated>
            <%--alert('${sessionScope.hello}');--%>
            <% String result = (String) session.getAttribute("error_msg");%>
            <c:if test="<%= !StringUtils.isNullOrEmpty(result)%>">
                alert("<%=result%>");
            </c:if>
            <%--<c:if test="${opResult!=null}">--%>
                <%--alert("${opResult}");--%>
            <%--</c:if>--%>
            $("#sub_btn").click(function (event) {
                var rememberMe_checkbox = $('#checkbox1');
                rememberMe_checkbox.val(rememberMe_checkbox.is(':checked'));
                $('#login_form').submit();
//                $.ajax({
//                    type:'POST',
//                    url:'/user/login',
//                    dataType:"json",
//                    data:{
//                        username:$('#inputEmail3').val(),
//                        password:$('#inputPassword3').val(),
//                        rememberMe:$('#checkbox1').is(':checked')
//                    },
//                    success:function (data) {
//                        if(!data.success){
//                            alert(data.opMsg);
//                        }else{
//                            location.href="/homePage";
//                        }
//                    }
//                });
                event.preventDefault();
            })
        });
    </script>
    <title>登录食人族</title>
</head>
<body>
<div align="center">
    <div class="demo form-bg" style="padding: 120px ;">
        <div class="container" style="margin-top: 0px;">
            <div class="row">
                <div class="col-md-offset-3 col-md-6">
                    <form class="form-horizontal" id="login_form" action="/user/login" method="post">
                        <span class="heading"><img src="/images/login_logo_245.jpg"></span>
                        <%--<span class="heading"><img src="/pic/914417e8-06e8-4413-abb2-70452847fc55.jpg"></span>--%>
                        <!--<span class="heading">用户登录</span>-->
                        <div class="form-group">
                            <input type="text" class="form-control" id="inputEmail3" placeholder="用户名" name="username">
                            <i class="fa fa-user"></i>
                        </div>
                        <div class="form-group help">
                            <input type="password" class="form-control" id="inputPassword3" placeholder="密　码" name="password">
                            <i class="fa fa-lock"></i>
                            <!--<a href="#" class="fa fa-question-circle"></a>-->
                        </div>
                        <div class="form-group">
                            <div class="main-checkbox" style="margin-top: 10px;">
                                <input type="checkbox" id="checkbox1" name="rememberMe"/>
                                <label for="checkbox1"></label>
                            </div>
                            <span class="text" style="margin-top: 5px">Remember me</span>
                            <button id="sub_btn" class="btn btn-default">
                                <img src="${pageContext.request.contextPath}/images/check_in_logo_137.jpg">
                            </button>

                            <a href="/register"><img src="${pageContext.request.contextPath}/images/register_logo.jpg" style="margin-top: 7px"></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
