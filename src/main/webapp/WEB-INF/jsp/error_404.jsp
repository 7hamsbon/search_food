<%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/3/29
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="common.jsp"%>
    <script>
        function jump_back(event) {
            window.history.back();
            event.preventDefault();
        }

        function jump_homePage(event) {
            location.href = "/homePage";
            event.preventDefault();
        }
    </script>
</head>
<body style="background-color: rgb(241, 241, 241)">
<%--<%@include file="head.jsp"%>--%>
<div style="margin-left: 30%;width:40%;margin-top: 100px" class="ui center aligned container">
    <img src="/images/404.jpg">
    <div class="ui buttons">
        <button onclick="jump_back(event)" class="ui large black  button"><i class=" teal icon Arrow Left"></i> <span >GO BACK!</span></button>
        <button onclick="jump_homePage(event)" class="ui large black  button"><i class=" teal icon Lightning"></i> <span >GO HOME!</span></button>
    </div>
</div>
</body>
</html>
