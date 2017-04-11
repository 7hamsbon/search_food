<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/3/9
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <%@include file="common.jsp"%>
    <script type="text/javascript">
        $(function () {
            <c:choose>
                <c:when test="${blogs.success}">
                    <c:forEach items="${blogs.data}" var="blog">
                    $('#blog_content').append(common.generate_blog_view('/pic?filePath=${blog.userHeadPath}','${blog.userName}','<fmt:formatDate value="${blog.ctime}" pattern="yyyy-MM-dd HH:mm"/>','${blog.price}','${blog.id}','/pic?filePath=${blog.photoPath}','${blog.foodName}','${blog.content}','${blog.description}','${blog.address}','${blog.likeCount}','${blog.commentCount}',${blog.liked},${blog.userId},${blog.collected},null,${sessionScope.user!=null && sessionScope.user.id==blog.userId}));
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    $('#blog_content').append("没有更多匹配的博客了");
                </c:otherwise>
            </c:choose>
        });
    </script>
</head>
<body style="background-color: #d9d7d9">

    <%@include file="head.jsp"%>

    <div class="ui container" style="margin-top: 100px">
        <div class="ui grid">
            <div class="row">
                <div class="ui huge icon input" style="margin-left: 23%">
                    <input type="text" placeholder="Search..." style="width: 600px" id="search_input">
                    <i class="circular search link icon" onclick="common.search($('#search_input').val())"></i>
                </div>
            </div>
            <div class="row">
                <div class="ui segment" style="border-radius: 15px;width: 100%">
                    <h3 class="ui divided header">
                        <i class="User icon"></i>
                        相关用户
                    </h3>
                    <div class="ui divider"></div>
                    <c:choose>
                        <c:when test="${users.success}">
                            <div class="ui five column grid">
                                <c:forEach items="${users.data}" var="user">
                                    <div class="column">
                                        <div class="ui fluid card" style="height: 370px">
                                            <a class="image" href="/blogs/${user.id}">
                                                <img src="/pic?filePath=${user.photoUrl}" style="height: 150px">
                                            </a>
                                            <div class="content">
                                                <h4 class="ui header">
                                                    <c:choose>
                                                        <c:when test="${user.gender==0}">
                                                            <i class="female pink icon"></i>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i class="male blue icon"></i>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <a class="content">${user.username} </a>
                                                </h4>
                                                <div class="meta">
                                                    <span class="date">Joined in <fmt:formatDate value="${user.registerTime}" pattern="yyyy.MM"/> </span>
                                                </div>
                                                    <div class="description">
                                                        <c:choose>
                                                            <c:when test="${user.summary != null}">
                                                            <c:choose>
                                                                <c:when test="${ (fn:length(user.summary ) >40)}">
                                                                    ${fn:substring(user.summary ,0,40)}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${user.summary}
                                                                </c:otherwise>
                                                            </c:choose>
                                                            ${ fn:length(user.summary ) >40 ? '...':''}
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
                                                                    <div class="value" id="likeCount_${user.id}"><i class="red heart icon"></i> ${user.likeNum} </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="four wide column">
                                                            <div class="ui center aligned container">
                                                                <div class="statistic" data-tooltip="粉丝数">
                                                                    <a class="link" href="/fansPage/${user.id}">
                                                                        <div class="value" id="fansCount_${user.id}"><i class="purple sitemap icon"></i> ${user.fansNum} </div>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="four wide column">
                                                            <div class="ui center aligned container">
                                                                <div class="statistic" data-tooltip="关注的人">
                                                                    <a class="link" href="/followerPage/${user.id}">
                                                                        <div class="value" id="followersCount_${user.id}"><i class="green users icon"></i> ${user.followerNum} </div>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="four wide column">
                                                            <div class="ui center aligned container">
                                                                <div class="statistic" data-tooltip="博客数">
                                                                    <a class="link" href="/blogs/${user.id}">
                                                                        <div class="value" id="blogCount_${user.id}"><i class="blue idea icon"></i> ${user.blogNum} </div>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            没有匹配的用户~
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="row">
                <div class="ui segment" style="width:63%;border-radius: 15px;margin-left: 18%;background-color:#d0d6d6" id="blog_content">
                    <h3 class="ui divided header">
                        <i class="User icon"></i>
                        相关博客
                    </h3>
                    <div class="ui divider"></div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>
