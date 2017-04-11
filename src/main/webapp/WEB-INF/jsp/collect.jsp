<%@ page import="com.ham.entity.User" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: hamsbon
  Date: 2017/3/9
  Time: 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="common.jsp"%>
    <script>
        function cancel_collect_callback(data) {
            if(data.success){
//                $('#miss_segment_'+data.data).remove();
                $('#miss_segment_'+data.data)
                    .transition('fade down')
                ;
            }else{
                alert(data.opMsg);
            }
        }
        $(function () {
            var $blog_content = $('#blog_content');
            <c:forEach items="${blogs.data}" var="blog">
                <c:choose>
                    <c:when test="${blog.userName==null}">
                        var $miss_segment = $('<div></div>').addClass("ui  center aligned segment").css("margin-top","10px").css("width","700px").attr("id","miss_segment_${blog.id}");
                        var $warning_i = $('<i></i>').addClass("circular blue Warning icon");
                        var $miss_span1 = $('<span>该博客已被博主删除,收藏于 <fmt:formatDate value="${blog.collectTime}" pattern="yyyy.MM.dd"/></span>');
                        var $miss_button = $('<button></button>').addClass("ui circular basic gery right  floated icon  button").attr("data-tooltip","remove").attr("data-inverted","").attr("data-position","right center").attr("onclick","common.cancel_collect(${blog.id},cancel_collect_callback)");
                        var $del_btn = $('<i></i>').addClass("Minus icon");
                        $miss_button.append($del_btn);
                        $miss_segment.append($warning_i).append($miss_span1).append($miss_button);
                        $blog_content.append($miss_segment);
                    </c:when>
                    <c:otherwise>
                        <%--$blog_content.append(common.generate_blog_view('/pic?filePath=${blog.userHeadPath}','${blog.userName}','<fmt:formatDate value="${blog.ctime}" pattern="yyyy-MM-dd HH:mm"/> ','${blog.price}','${blog.id}','/pic?filePath=${blog.photoPath}','${blog.foodName}','${blog.content}','${blog.description}','${blog.address}','${blog.likeCount}','${blog.commentCount}',${blog.liked},${blog.userId},${blog.collected},'<fmt:formatDate value="${blog.collectTime}" pattern="yyyy.MM.dd"/>',<shiro:authenticated><%= ((User)SecurityUtils.getSubject().getPrincipal()).getId() == blog.userId%> </shiro:authenticated>));--%>
                        $blog_content.append(common.generate_blog_view('/pic?filePath=${blog.userHeadPath}','${blog.userName}','<fmt:formatDate value="${blog.ctime}" pattern="yyyy-MM-dd HH:mm"/> ','${blog.price}','${blog.id}','/pic?filePath=${blog.photoPath}','${blog.foodName}','${blog.content}','${blog.description}','${blog.address}','${blog.likeCount}','${blog.commentCount}',${blog.liked},${blog.userId},${blog.collected},'<fmt:formatDate value="${blog.collectTime}" pattern="yyyy.MM.dd"/>',${sessionScope.user.id==blog.userId}));
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        });
    </script>

</head>
<body style="background-color: #d9d7d9">
    <%@include file="head.jsp"%>

    <c:choose>
        <c:when test="${blogs.success}">
            <div style="padding:0px;margin-top: 80px;margin-bottom:40px;margin-left:25%;border-radius: 10px;width: 55%;background-color: #d0d6d6">
                <h2 class="ui block header" style="margin: 0px">
                    <i class="yellow Empty Star icon"></i>
                    <div class="content">
                            我收藏的博客
                    </div>
                </h2>
                <div id="blog_content" style="padding-left: 3%;">

                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div style="margin-top: 70px">
                用户不存在
            </div>
        </c:otherwise>
    </c:choose>

</body>
</html>
