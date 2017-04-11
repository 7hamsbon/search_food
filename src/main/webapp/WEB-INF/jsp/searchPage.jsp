<%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/3/9
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <%@include file="common.jsp"%>
        <script type="text/javascript">
            function search() {
                var keyword = $('#search_input').val();
                var encode_key = encodeURI(keyword);
                location.href='/searchResult?keyword='+encode_key;
            }
        </script>
    </head>
    <body style=" background-color:#e4e3dd;">
    <%@include file="head.jsp"%>
        <div class="ui grid" style="margin-top: 65px;margin-left: 25%;">
            <div class="row" style="margin-left: 40px;padding-bottom: 0px">
                <img class="ui medium  image" src="/images/f-walk.gif" style="width: 600px;height: 190px;">
            </div>
            <div class="row" style="padding-top: 0px">
                <div class="ui huge icon input">
                    <input type="text" placeholder="Search..." style="width: 600px" id="search_input">
                    <i class="circular search link icon" onclick="search()"></i>
                </div>
            </div>
        </div>
    </body>
</html>
