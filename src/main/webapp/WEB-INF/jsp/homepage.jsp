<%@ page import="com.ham.entity.User" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="com.ham.vo.BlogVO" %><%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/3/8
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>食·人·族</title>
    <%@include file="common.jsp"%>
    <link href="/css/homepage.css" rel="stylesheet"/>
    <script src="/js/homepage.js"></script>
</head>
<body >

    <%-- 发布成功显示的视图 --%>
    <div class="ui basic second coupled modal ">
        <div class="header" id="pub_status">
        </div>
        <div class="image content">
            <div class="image">
                <i class="Checkmark icon"></i>
            </div>
        </div>
        <div class="actions">
            <div class="two fluid ui inverted buttons">
                <div class="ui ok green basic inverted button">
                    <i class="checkmark icon"></i>
                    Yes
                </div>
            </div>
        </div>
    </div>

    <%-- 发布博客视图 --%>
    <div class="ui test first coupled modal transition scrolling visible active  " style="top: 700px;">
        <div class="header">
            Publish Blog
        </div>
        <div class="content">
            <div class="ui grid">
                <div class="three wide column">
                    <form id="fooo" enctype="multipart/form-data" action="/blog/uploadPhoto">
                        <div id="pub_pic" data-tooltip="Choose a very delicious picture of your sharing~">
                            <input class="fileInput" type="file" name="food" id="file" />
                        </div>
                    </form>
                </div>
                <div class="twelve wide column" style="padding-bottom: 5px">
                    <div class="ui grid">
                        <form class="ui form" id="pub_form" style="width: 100%">
                            <div class="row three fields">
                                <div class="five wide column field">
                                    <div class="ui icon input " style="width: 100%" data-tooltip="Can't be empty!">
                                        <i class="food icon"></i>
                                        <input type="text" placeholder="Food Name~" id="food_name">
                                    </div>
                                </div>
                                <div class="five wide column field">
                                    <div class="ui  icon input " data-tooltip="Must be a number!">
                                        <i class="Yen icon"></i>
                                        <input type="text" placeholder="How much it cost?" id="price">
                                    </div>
                                </div>
                                <div class="seven wide column field">
                                    <div class="ui icon input " data-tooltip="Could you plz specify it?">
                                        <i class="marker icon"></i>
                                        <input type="text" placeholder="Address?" id="addr" >
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="padding-bottom: 5px;padding-top: 5px">
                                <div class=" column">
                                    <div class="field" data-tooltip="The number of description characters should less than 400!">
                                        <textarea style="width: 100%;height:100px;resize: none;border-radius: 10px" placeholder="Description..." id="desc" ></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="padding-top: 5px">
                                <div class=" column">
                                    <div class="field" style="width: 100%" data-tooltip="The number of feeling characters should less than 400!">
                                        <textarea style="width: 100%;height:100px;resize: none;border-radius: 10px" placeholder="Feeling..." id="content"></textarea>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="actions">
            <div class="two fluid ui buttons">
                <div class="ui red basic cancel  button">
                    <i class="remove icon"></i>
                    Cancel
                </div>
                <div class="ui green ok basic prove button">
                    <i class="checkmark icon"></i>
                    Publish!
                </div>
            </div>
        </div>
    </div>

    <!--个人信息-->
    <div id="cont" class="ui sticky fixed top">
        <div align="center">
            <div style="margin-top:9px;border:2px solid #ef3f52;width: 100px;height: 100px;border-radius: 50%;background: url('/pic?filePath=${userInfo.photoUrl}') no-repeat;-webkit-background-size: 100%">

            </div>
            <%--<img id="headPic" class="ui Small circular image" src="/pic?filePath=${userInfo.photoUrl}">--%>
        </div>
        <h3 class="ui purple header" id="username" align="center">
            ${userInfo.username}
        </h3>
        <div  class="ui horizontal divider" id="p_divider" >·</div>
        <div class="ui internally celled grid">
            <div class="row">
                <div class="four wide column">
                    <div class="ui center aligned container">
                        <div class="statistic" data-tooltip="点赞数">
                            <div class="value" id="likeCount"><i class="red heart icon"></i> ${userInfo.likeNum} </div>
                            <div class="label">Like</div>
                        </div>
                    </div>
                </div>
                <div class="four wide column">
                    <div class="ui center aligned container">
                        <div class="statistic" data-tooltip="粉丝数">
                            <a class="link" href="/fansPage/${userInfo.id}">
                                <div class="value" id="fansCount"><i class="purple sitemap icon"></i> ${userInfo.fansNum} </div>
                                <div class="label">Fans</div>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="four wide column">
                    <div class="ui center aligned container">
                        <div class="statistic" data-tooltip="关注的人">
                            <a class="link" href="/followerPage/${userInfo.id}">
                                <div class="value" id="followersCount"><i class="green users icon"></i> ${userInfo.followerNum} </div>
                                <div class="label">Followers</div>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="four wide column">
                    <div class="ui center aligned container">
                        <div class="statistic" data-tooltip="博客数">
                            <a class="link" href="/blogs/${userInfo.id}">
                                <div class="value" id="blogCount"><i class="blue idea icon"></i> ${userInfo.blogNum} </div>
                                <div class="label">Blogs</div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="head.jsp"%>

    <!--博客信息-->
    <div class="ui" id="example1" >
        <c:forEach items="${blogs}" var="blog">
            <div id="blog_${blog.id}" class="blog" style="margin-bottom: 10px; margin-top: 10px;">
                <div class="out_f" style="position:relative;border: 2px solid rgb(219, 219, 219); width: 700px; margin-top: 20px; background-color: rgb(255, 255, 255); border-radius: 10px;">
                <%--<c:if test='<%=((User)SecurityUtils.getSubject().getPrincipal()).getId()==((BlogVO)request.getAttribute("blog")).getUserId()%>'>--%>
                <c:if test="${blog.userId==sessionScope.user.id}">
                    <a class="ui corner label" onclick="common.delete_blog(${blog.id})">
                        <i class="remove icon" ></i>
                    </a>
                </c:if>
                    <div class="ui grid" >
                        <div class="two wide column">
                            <a href="/blogs/${blog.userId}" class="ui tiny circular image">
                                <div style="border:1px solid #d0d6d6;width:50px;height:50px;margin-left: 10px; margin-top: 10px;border-radius: 50%;background: url('${pageContext.request.contextPath}/pic?filePath=${blog.userHeadPath}') no-repeat;-webkit-background-size:100%">
                                </div>
                                <%--<img src="${pageContext.request.contextPath}/pic?filePath=${blog.userHeadPath}" style="margin-left: 10px; margin-top: 10px;">--%>
                            </a>
                        </div>
                        <div class="thirteen wide column">
                            <div class="ui grid">
                                <div class="row" style="padding-top: 30px; padding-bottom: 0px;">
                                    <div class="circular ui basic purple label" >
                                       ${blog.userName}
                                    </div>
                                </div>
                                <div class="row" style="padding-top: 5px; padding-bottom: 0px;">
                                    <div class="meta" style="color: rgb(116, 119, 123);">
                                        <span class="date">
                                            <fmt:formatDate value="${blog.ctime}" pattern="yyyy-MM-dd HH:mm"/>
                                        </span>
                                    </div>
                                </div>
                                <div class="row" style="padding-top: 9px; padding-bottom: 0px;">
                                    <div class="ui segment">
                                        <div class="ui orange basic bottom right attached label">
                                            ￥${blog.price}
                                        </div>
                                        <c:if test="${blog.collectTime!=null}">
                                            <div class="ui blue basic bottom left attached label">
                                                收藏于${blog.collectTime}
                                            </div>
                                        </c:if>
                                        <div onclick="common.like_change(${blog.id})">
                                            <a class="like_a floating ui label" style="width: 35px;">
                                                <c:choose>
                                                    <c:when test="${blog.liked}">
                                                        <i class="Heart red icon" id="like_${blog.id}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="Heart grey icon" id="like_${blog.id}">
                                                    </c:otherwise>
                                                </c:choose>

                                                </i>
                                                ${blog.likeCount}
                                            </a>
                                        </div>
                                        <img class="ui small left floated rounded image" src="/pic?filePath=${blog.photoPath}">
                                        <h4 class="ui violet header" style="margin-top: 0px;">
                                            ${blog.foodName}
                                        </h4>
                                        <p style="color: rgb(116, 119, 123);">
                                            ${blog.content}
                                        </p>
                                        <p style="color: rgb(116, 119, 123);">
                                            ${blog.description}
                                        </p>
                                    </div>
                                </div>
                                <div class="row" style="color: rgb(61, 68, 68); padding-top: 3px; margin-bottom: 9px; padding-bottom: 13px;">
                                    <div class="eight wide column">
                                        <div class="ui pointing blue basic label">
                                            <i class="marker icon"></i>
                                            ${blog.address}
                                        </div>
                                    </div>
                                    <div class="eight wide column"  style="margin-top: 5px;">
                                        <button class="ui small right floated basic blue button" onclick="common.collect_change(${blog.id})">
                                            <c:choose>
                                                <c:when test="${blog.collected}">
                                                    <i  class="icon yellow star" id="collect_icon_${blog.id}"></i>
                                                    <span id="collect_text_span_${blog.id}">取消收藏</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="icon grey star" id="collect_icon_${blog.id}"></i>
                                                    <span id="collect_text_span_${blog.id}">收藏</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </button>
                                        <button class="ui small right floated basic blue button" onclick="common.comment_toggle(${blog.id})">
                                            Comments(${blog.commentCount})
                                        </button>
                                    </div>
                                </div>
                                <div class="row" id="comment_${blog.id}" style="display: none;">
                                    <div class="eighteen wide column">
                                        <div class="ui threaded comments">
                                            <h3 class="ui dividing header" >
                                                Comments
                                            </h3>
                                            <div id="comment_container_${blog.id}"></div>
                                            <form class="ui reply form" id="comment_form_${blog.id}">
                                                <div class="field">
                                                    <div class="ui action input" >
                                                        <input type="text" placeholder="Add Comment" id="comment_input_${blog.id}">
                                                        <div onclick="common.send_comment(event,${blog.id})">
                                                            <button class="ui icon button" >
                                                                <i class="send icon"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
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
</body>
</html>
