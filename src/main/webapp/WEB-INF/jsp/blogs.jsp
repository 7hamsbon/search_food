<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/3/22
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        食·人·族
    </title>
    <%@include file="common.jsp"%>
    <style>
        .column
        {
            font-family:Arial,Helvetica,sans-serif;
            font-size:1em;
            vertical-align:middle;
            font-weight:normal
        }
    </style>
    <script>
        $(function () {
            var $friend = $('#friend');
            $friend.click(function (event) {
                var fansNumSpan = $('.fans_num_span');
                if($friend.hasClass("positive")){
                    $.ajax("/friend/add",{
                        type:"post",
                        data:{
                            follower:${userResult.data[0].id}
                        },
                        success:function (data) {
                            if(data.success){
                                $friend.removeClass("positive");
                                $friend.addClass("negative");
                                $friend.text("取消关注");
                                fansNumSpan.text(parseInt(fansNumSpan.text())+1);
                            }else{
                                alert(data.opMsg);
                            }
                        }
                    });
                }else{
                    $.ajax("/friend/cancel",{
                        type:"post",
                        data:{
                            follower:${userResult.data[0].id}
                        },
                        success:function (data) {
                            if(data.success){
                                $friend.removeClass("negative");
                                $friend.addClass("positive");
                                $friend.text("关注一波");
                                fansNumSpan.text(parseInt(fansNumSpan.text())-1);
                            }else{
                                alert(data.opMsg);
                            }
                        }
                    });
                }
                event.preventDefault();
            });
            <c:forEach items="${personalBlogs.data}" var="blog">
                var desc = '${blog.description}',
                    content = '${blog.content}';
                $('#blog_content').append(common.generate_blog_view('/pic?filePath=${blog.userHeadPath}','${blog.userName}','<fmt:formatDate value="${blog.ctime}" pattern="yyyy-MM-dd HH:mm"/>','${blog.price}','${blog.id}','/pic?filePath=${blog.photoPath}','${blog.foodName}',content,desc,'${blog.address}','${blog.likeCount}','${blog.commentCount}',${blog.liked},${blog.userId},${blog.collected},null,${sessionScope.user!=null && sessionScope.user.id==blog.userId}));
            </c:forEach>
        });

    </script>
</head>
<body style="background-color: #d9d7d9">
    <%@include file="head.jsp"%>
    <c:choose>
        <c:when test="${userResult!=null && userResult.success }">
            <div class="ui grid" style="padding-bottom: 71px">
                    <%--suppress CssUnknownTarget --%>
                <%--个人首页--%>
                <div class="row" style="width: 100%;height: 370px;background: url('/images/bg1.jpg') no-repeat;-webkit-background-size: 100%;top: 0px;" >
                    <div style="position: absolute;margin-top: 88px;margin-left: 4%;width: 100%">
                        <div class="ui grid" style="">
                            <div class="three wide column">
                                <div style="background:url('/pic?filePath=${userResult.data[0].photoUrl}') no-repeat;-webkit-background-size: 100%;width: 150px;height: 150px;border-radius: 50%;border: 2px solid #ef3f52">
                                </div>
                            </div>
                            <div class="five wide column">
                                <div class="ui grid">
                                    <div class="row" style="padding-bottom: 0px">
                                        <h2 class="ui header" style="color: #ffffff;margin-bottom: 0px;">
                                            <div class="content">${userResult.data[0].username}</div>
                                        </h2>
                                        <c:choose>
                                            <c:when test="${userResult.data[0].gender == 1}">
                                                <i class="circular inverted teal Man icon"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="circular inverted pink woman icon"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="row" style="margin-bottom: 0px;padding-bottom: 0px">
                                        <i class="large inverted grey marker icon"></i>
                                        <div class="ui   header" style="color: #ffffff;margin: 0px" data-tooltip="Address" data-position="right center"  data-inverted="">
                                            <div class="content">${userResult.data[0].address}</div>
                                        </div>
                                    </div>
                                    <div class="row" style="margin-bottom: 0px;padding-bottom: 0px">
                                        <i class="large inverted grey mail icon"></i>
                                        <div class="ui   header" style="color: #ffffff;margin: 0px" data-tooltip="E-mail" data-position="right center"  data-inverted="">
                                            <div class="content">${userResult.data[0].email}</div>
                                        </div>
                                    </div>
                                    <div class="row" style="margin-bottom: 0px;padding-bottom: 0px">
                                        <i class="large inverted grey phone icon"></i>
                                        <div class="ui   header" style="color: #ffffff;margin: 0px" data-tooltip="Phone" data-position="right center"  data-inverted="">
                                            <div class="content">${userResult.data[0].phone}</div>
                                        </div>
                                    </div>
                                    <div class="row" style="margin-bottom: 0px;padding-bottom: 0px">
                                        <i class="large inverted grey child icon"></i>
                                        <div class="ui   header" style="color: #ffffff;margin: 0px" data-tooltip="Age" data-position="right center" data-inverted="">
                                            <div class="content">${userResult.data[0].age}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="five wide column" style="color: #ffffff;">
                                <div class="ui grid">
                                    <div class="row" style="margin-bottom: 40px">
                                        <br/><br/>
                                        ${userResult.data[0].summary}
                                    </div>
                                    <div class="row">
                                        <c:if test="${!isSelf}">
                                            <c:choose>
                                                <c:when test="${isFollow}">
                                                    <button id="friend" class="ui negative   button" style="margin-left: 40%">取消关注</button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button id="friend" class="ui positive  button" style="margin-left: 40%">关注一波</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <%--边栏和博客--%>
                <div class="row" style="margin-top: 30px">
                    <div class="ui grid" style="width: 80%;margin-left: 10%">
                        <%--边栏--%>
                        <div class="five wide column ui grid" style="padding: 0px;">
                            <%--点赞数、博客数、粉丝数、关注者统计--%>
                            <div class="row" style="padding: 0px;margin-bottom:20px;background-color: #ffffff;">
                                <div class="ui internally celled grid" style="padding-top: 10px;padding-bottom: 10px">
                                    <div class="row">
                                        <div class="four wide column">
                                            <div class="ui center aligned container">
                                                <div class="statistic" data-tooltip="点赞数">
                                                    <div class="value" id="likeCount"><i class="red heart icon"></i> ${userResult.data[0].likeNum} </div>
                                                    <div class="label">Like</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="four wide column">
                                            <div class="ui center aligned container">
                                                <div class="statistic" data-tooltip="粉丝数">
                                                    <a class="link" href="/fansPage/${userResult.data[0].id}">
                                                        <div class="value" id="fansCount"><i class="purple sitemap icon"></i> <span class="fans_num_span">${userResult.data[0].fansNum}</span></div>
                                                        <div class="label">Fans</div>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="four wide column">
                                            <div class="ui center aligned container">
                                                <div class="statistic" data-tooltip="关注的人">
                                                    <a class="link" href="/followerPage/${userResult.data[0].id}">
                                                        <div class="value" id="followersCount"><i class="green users icon"></i> ${userResult.data[0].followerNum}</div>
                                                        <div class="label">Followers</div>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="four wide column">
                                            <div class="ui center aligned container">
                                                <div class="statistic" data-tooltip="博客数">
                                                    <a class="link" href="/blogs/${userResult.data[0].id}">
                                                        <div class="value" id="blogCount"><i class="blue idea icon"></i> ${userResult.data[0].blogNum} </div>
                                                        <div class="label">Blogs</div>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                             <%--关系--%>
                            <div class="row" style="background-color: #ffffff;padding: 0px">
                                <div class="ui internally celled grid" style="padding-top: 0px;padding-bottom: 0px">
                                    <c:if test="${sameFollowers.success}">
                                        <div class="row" style="padding: 10px;">
                                            <table class="ui very basic table" >
                                                <thead>
                                                <tr>
                                                    <td>
                                                        <h4 class="ui  header">共同关注(${followersCount})</h4>
                                                    </td>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr >
                                                    <c:forEach items="${sameFollowers.data}" var="follower">
                                                        <th ><div  align="center" style="width:70px;height:110px;padding: 5px;">
                                                            <a href="/blogs/${follower.id}" class="ui circular image" >
                                                                <div style="margin-bottom: 5px;width:70px;height:70px;border-radius:50%;background: url('/pic?filePath=${follower.photoUrl}') no-repeat;-webkit-background-size:100%">
                                                                </div>
                                                            </a>
                                                                ${follower.username}
                                                        </div>
                                                        </th>
                                                    </c:forEach>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <%--<h4 class="ui  header">共同关注</h4>--%>
                                            <%--<div class="ui grid" style="padding: 0%">--%>
                                                <%--<c:forEach items="${sameFollowers.data}" var="follower">--%>
                                                    <%--&lt;%&ndash;<div class="four wide column" align="center">&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;<a href="/blogs/${follower.id}" class="ui  circular image">&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;<img  src="/pic?filePath=${follower.photoUrl}">&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;${follower.username}&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

                                                    <%--<div class="four wide column" align="center" style="width:70px;height:110px;padding: 5px;">--%>
                                                        <%--<a href="/blogs/${follower.id}" class="ui circular image" >--%>
                                                            <%--<div style="margin-bottom: 5px;width:70px;height:70px;border-radius:50%;background: url('/pic?filePath=${follower.photoUrl}') no-repeat;-webkit-background-size:100%">--%>

                                                            <%--</div>--%>
                                                                <%--&lt;%&ndash;<img src="/pic?filePath=${fans.photoUrl}">&ndash;%&gt;--%>
                                                        <%--</a>--%>
                                                        <%--${follower.username}--%>
                                                    <%--</div>--%>
                                                <%--</c:forEach>--%>
                                            <%--</div>--%>
                                        </div>
                                    </c:if>
                                    <c:if test="${sameFans.success}">
                                        <div class="row" style="padding: 10px;">
                                            <table class="ui very basic table" >
                                                <thead>
                                                <tr>
                                                    <td>
                                                        <h4 class="ui  header" style="height: 19px">我的粉丝也关注他(${fansCount})</h4>
                                                    </td>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <c:forEach items="${sameFans.data}" var="fans">
                                                        <th><div  align="center" style="width:70px;height:110px;padding: 5px;">
                                                            <a href="/blogs/${fans.id}" class="ui circular image" >
                                                                <div style="margin-bottom: 5px;width:70px;height:70px;border-radius:50%;background: url('/pic?filePath=${fans.photoUrl}') no-repeat;-webkit-background-size:100%">
                                                                </div>
                                                            </a>
                                                                ${fans.username}
                                                        </div>
                                                        </th>
                                                    </c:forEach>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <%--<div class="ui grid" style="padding: 0%">--%>
                                            <%--<c:forEach items="${sameFans.data}" var="fans">--%>
                                                <%--<div class="four wide column" align="center" style="width:70px;height:110px;padding: 5px;">--%>
                                                    <%--<a href="/blogs/${fans.id}" class="ui circular image" >--%>
                                                        <%--<div style="margin-bottom: 5px;width:70px;height:70px;border-radius:50%;background: url('/pic?filePath=${fans.photoUrl}') no-repeat;-webkit-background-size:100%">--%>
                                                        <%--</div>--%>
                                                    <%--</a>--%>
                                                    <%--${fans.username}--%>
                                                <%--</div>--%>
                                            <%--</c:forEach>--%>
                                            <%--</div>--%>
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                        </div>
                            <%--博客--%>
                        <div class="eleven wide column " id="blog_content" style="padding-top: 0px;margin-top:0px;margin-left: 20px">
                        </div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            用户不存在
        </c:otherwise>
    </c:choose>
</body>
</html>