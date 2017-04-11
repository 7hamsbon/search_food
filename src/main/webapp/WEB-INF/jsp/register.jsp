<%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/2/25
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--<title>加入食人族</title>--%>
    <%--<link href="http://cdn.bootcss.com/semantic-ui/2.2.7/semantic.css" rel="stylesheet">--%>
    <%--<script src="http://cdn.bootcss.com/jquery/3.1.1/jquery.js"></script>--%>
    <%--<script src="http://cdn.bootcss.com/semantic-ui/2.2.7/semantic.js"></script>--%>
    <%--<script type="text/javascript" src="/js/common.js"></script>--%>
        <%@include file="common.jsp"%>
    <link href="/css/register.css" rel="stylesheet">
    <script type="text/javascript" src="/js/register.js"></script>
</head>
<body>
    <div class="container">
        <%--<div class="join">--%>
            <%--<img src="/images/JOIN_logo_167.jpg" style="border-radius: 10px">--%>
        <%--</div>--%>
        <%--<div class="us">--%>
            <%--<img src="/images/US_logo_98.jpg" style="border-radius: 10px">--%>
        <%--</div>--%>
        <%--<div class="eat">--%>
            <%--<img src="/images/eat_logo_83.jpg" style="border-radius: 10px">--%>
        <%--</div>--%>
        <%--<div class="people">--%>
            <%--<img src="/images/people_logo_77.jpg" style="border-radius: 10px">--%>
        <%--</div>--%>

        <form id="fo" enctype="multipart/form-data" action="/user/uploadhead">
            <div class="header ">
                <input class="fileInput" type="file" name="file" id="file" />
            </div>
        </form>

        <div  class="ui horizontal divider" style="padding-left: 50px;padding-right: 50px;padding-bottom: 10px">·</div>
        <form class="ui form" action="r">
            <div class="input_div field">
                <div class="ui left icon input">
                    <input type="text" placeholder="User Name" name="username" id="username">
                    <i class="circular user icon"></i>
                </div>
            </div>
            <div class="input_div field" >
                <div class="ui left icon input">
                    <input type="password" placeholder="Password" name="password" id="password">
                    <i class="circular lock icon"></i>
                </div>
            </div>
            <div class="input_div field" >
                <select class="ui dropdown" name="gender" id="gender">
                    <option value="1">Male</option>
                    <option value="0">Female</option>
                </select>
            </div>
            <div class="input_div ui field" >
                <div class="ui left icon input">
                    <input type="text" placeholder="Phone" name="phone" id="phone">
                    <i class="circular Call icon"></i>
                </div>
            </div>
            <div class="input_div ui field" >
                <div class="ui left icon input">
                    <input type="text" placeholder="Age" name="age" id="age">
                    <i class="circular child icon"></i>
                </div>
            </div>
            <div class="input_div ui field" >
                <div class="ui left icon input">
                    <input type="text" placeholder="address" name="address" id="address">
                    <i class="circular marker icon"></i>
                </div>
            </div>
            <div class="input_div ui field" >
                <div class="ui left icon input">
                    <input type="email" placeholder="E-mail" name="email" id="email">
                    <i class="circular Mail Outline icon"></i>
                </div>
            </div>
        </form>
            <div style="padding-right: 50px;padding-left: 175px">
                <button id="sub_btn" onclick="registry(event)" class="ui positive basic button">Come On & Hi~</button>
            </div>
        <div class="ui error message" id="err_msg" style="position: absolute;left: 970px;top: 230px"></div>
    </div>
</body>
</html>
