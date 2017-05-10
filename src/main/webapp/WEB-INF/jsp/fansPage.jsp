<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/3/9
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>食·人·族</title>
    <%@include file="common.jsp"%>
    <script>

    </script>
</head>
<body style="background-color: #d9d7d9">

<%@include file="head.jsp"%>
<c:choose>
    <c:when test="${user.success}">
        <div style="padding:20px;margin-top: 80px;margin-bottom:40px;margin-left:6%;border-radius: 10px;width: 82%;background-color: #ffffff">
            <h2 class="ui block header">
                <i class="red Users icon"></i>
                <div class="content">
                        ${user.data[0].username}的粉丝
                </div>
            </h2>
            <div class="ui divider"></div>

            <c:choose>
                <c:when test="${fans.success}">
                    <div class="ui five column grid">
                        <div class="row">
                            <c:forEach items="${fans.data}" var="fan">
                                <div class="column" style="margin-bottom: 20px">
                                    <div class="ui fluid card" style="height: 371px">
                                        <a class="image" href="/blogs/${fan.id}">
                                            <img src="/pic?filePath=${fan.photoUrl}" style="height: 150px">
                                        </a>
                                        <div class="content">
                                            <h4 class="ui header">
                                                <c:choose>
                                                    <c:when test="${fan.gender == 0}">
                                                        <i class="female pink icon"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="male blue icon"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                                <a class="content" href="<%=request.getContextPath()%>/blogs/${fan.id}">${fan.username} </a>
                                            </h4>
                                            <div class="meta">
                                                <span class="date"><fmt:formatDate value="${fan.friendTime}" pattern="yyyy.MM.dd"/> 关注 </span>
                                            </div>
                                            <div class="description" style="height: 50px">
                                                <c:choose>
                                                    <c:when test="${fan.summary != null}">
                                                        <c:choose>
                                                            <c:when test="${ (fn:length(fan.summary ) >40)}">
                                                                ${fn:substring(fan.summary ,0,40)}
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${fan.summary}
                                                            </c:otherwise>
                                                        </c:choose>
                                                        ${ fn:length(fan.summary ) >40 ? '...':''}
                                                    </c:when>
                                                    <c:otherwise>这个吃货好懒，没有说啥~</c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <div class="extra content" style="height: 55px;padding: 4px">
                                            <div class="ui internally celled grid" style="height: 42px;">
                                                <div class="row" >
                                                    <div class="four wide column">
                                                        <div class="ui center aligned container">
                                                            <div class="statistic" data-tooltip="点赞数">
                                                                <div class="value" id="likeCount_${fan.id}"><i class="red heart icon"></i> ${fan.likeNum}</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="four wide column">
                                                        <div class="ui center aligned container">
                                                            <div class="statistic" data-tooltip="粉丝数">
                                                                <a class="link" href="/fansPage/${fan.id}">
                                                                    <div class="value" id="fansCount_${fan.id}"><i class="purple sitemap icon"></i> ${fan.fansNum}</div>
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="four wide column">
                                                        <div class="ui center aligned container">
                                                            <div class="statistic" data-tooltip="关注的人">
                                                                <a class="link" href="/followerPage/${fan.id}">
                                                                    <div class="value" id="followersCount_${fan.id}"><i class="green users icon"></i>${fan.followerNum} </div>
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="four wide column">
                                                        <div class="ui center aligned container">
                                                            <div class="statistic" data-tooltip="博客数">
                                                                <a class="link" href="/blogs/${fan.id}">
                                                                    <div class="value" id="blogCount_${fan.id}"><i class="blue idea icon"></i> ${fan.blogNum} </div>
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div >
                            </c:forEach>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    一个关注者都没有！
                </c:otherwise>
            </c:choose>



        </div>
    </c:when>
    <c:otherwise>
        <div style="margin-top: 60px">
            用户不存在
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>