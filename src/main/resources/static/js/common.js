/**
 * Created by hamsbon on 2017/3/11.
 */
var common = new Object({
    redirect_to_personal:function () {
        location.href="/personalInfo";
    },
    upload_pic:function upload_pic(url,formData,head_selector,file_selector,size) {
        //判断是否有选择上传文件
        var imgPath = $(file_selector).val();
        if (imgPath == "") {
            return;
        }
        //判断上传文件的后缀名
        var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
        if (strExtension != 'jpg' && strExtension != 'gif'
            && strExtension != 'png' && strExtension != 'bmp'
            && strExtension != 'jpeg' && strExtension != 'ico') {
            alert("请选择图片文件");
            return;
        }
        $.ajax({
            url: url,  //server script to process data
            type: 'POST',
            //Ajax事件
            success: function (data) {
                if(data.success){
                    photo_url = data.opMsg;//更新头像url
                    //将新头像显示出来
                    var u = "/pic?filePath="+data.opMsg;
                    // $('.header').css("background","url("+u+")")
                    $(head_selector).css("background","url("+u+")")
                        .css("background-repeat","no-repeat")
                        .css("-webkit-background-size",size+" "+size);
                }else{
                    alert("错误信息--->>>"+data.opMsg);
                }
            },
            error: function () {
                alert("上传失败，请检查网络后重试");
            },
            // Form数据
            data: formData,
            //Options to tell JQuery not to process data or worry about content-type
            cache: false,
            contentType: false,
            processData: false
        });
    },
    generate_blog_view:function generate_blog_view(head_src,username,pub_time,price,blog_id,food_pic_url,food_name,blog_content,blog_description,addr,likeNum,commentNum,isLike,userId,isCollect,collectTime,isSelf) {
        var $blog = $('<div></div>').css("margin-bottom","10px").css("margin-top","10px").addClass("blog").attr("id","blog_"+blog_id);
        var $out_f = $('<div></div>').addClass("out_f").css("border","2px solid #dbdbdb").css("width","700px").css("margin-top","20px").css("background-color","#ffffff").css("border-radius","10px").css("position","relative");
        if(isSelf){
            var $remove_a = $('<a></a>').addClass("ui corner label").attr("onclick","common.delete_blog("+blog_id+")");
            var $remove_i = $('<i></i>').addClass("remove icon");
            $remove_a.append($remove_i);
            $out_f.append($remove_a);
        }
        var $head_content_div = $('<div></div>').addClass("ui grid");
        var $head_column_div = $('<div></div>').addClass("two wide column");
        var $head_a = $('<a></a>').attr("href","/blogs/"+userId).addClass("ui tiny circular image");
        var $head_img = $('<div></div>').css({"border":"1px solid #d0d6d6","width":"50px","height":"50px","margin-left":"10px","margin-top":"10px","border-radius":"50%","background":"url('"+head_src+"')  no-repeat","-webkit-background-size":"100%"});
        // var $img = $('<img>').css({"margin-left":"10px","margin-top":"10px"}).attr("src",head_src);
        $head_a.append($head_img);
        $head_column_div.append($head_a);
        $head_content_div.append($head_column_div);
        var content_column_div = $('<div></div>').addClass("thirteen wide column");
        var content_grid = $('<div></div>').addClass("ui grid");
        var content_first_row = $('<div></div>').addClass("row").css({"padding-top":"30px","padding-bottom":"0px"});
        var username_div = $('<div></div>').addClass("circular ui basic purple label").text(username);
        content_first_row.append(username_div);
        content_grid.append(content_first_row);
        var content_second_row = $('<div></div>').addClass("row").css({"padding-top":"5px","padding-bottom":"0px"});
        var time_div = $('<div></div>').addClass("meta").css("color","#74777b");
        var time_span = $('<span></span>').addClass("date").text(common.deal_time(new Date(pub_time))+" 发布");

        if(collectTime!=null){
            // var collect_label = $('<div></div>').addClass("ui blue basic top left attached label").text("收藏于"+collectTime);
            // text_segment_div.append(collect_label);
            time_span.text(time_span.text()+"                                                                  收藏于"+collectTime).css("white-space","pre");
        }


        time_div.append(time_span);
        content_second_row.append(time_div);
        content_grid.append(content_second_row);
        var cotent_third_row = $('<div></div>').addClass("row").css({"padding-top":"9px","padding-bottom":"0px"});
        var text_segment_div = $('<div></div>').addClass("ui segment").css("width","100%");
        var price_div = $('<div></div>').addClass("ui orange basic bottom right attached label").text("￥" + price);
        text_segment_div.append(price_div);
        var like_div = $('<div></div>').attr("onclick","common.like_change("+blog_id+")");
        var like_a = $('<a></a>').addClass("like_a floating ui label").css("width","35px");
        var like_i = $('<i></i>').attr("id","like_"+blog_id);
        var like_class = isLike == true?"red":"grey";
        like_i.addClass("Heart "+like_class+" icon");
        like_a.text(likeNum).prepend(like_i);
        like_div.append(like_a);
        text_segment_div.append(like_div);
        var $food_pic = $('<img>').addClass("ui small left floated rounded image").attr("src",food_pic_url);
        text_segment_div.append($food_pic);
        var $food_name_h4 = $('<h4></h4>').addClass("ui violet header").css("margin-top","0px").text(food_name);
        text_segment_div.append($food_name_h4);
        var $content_p = $('<p></p>').css("color","#74777b").html(blog_content.replace("\n","<br />"));
        text_segment_div.append($content_p);
        var $description_p = $('<p></p>').css("color","#74777b").html(blog_description.replace("\n","<br />"));
        text_segment_div.append($description_p);
        cotent_third_row.append(text_segment_div);
        content_grid.append(cotent_third_row);
        var content_four_row = $('<div></div>').addClass("row").css({"color":"#3d4444","padding-top":"3px","margin-bottom":"9px","padding-bottom":"13px"});
        var addr_column_div = $('<div></div>').addClass("eight wide column");
        var addr_div = $('<div></div>').addClass("ui pointing blue basic label");
        var addr_i = $('<i></i>').addClass("marker icon");
        addr_div.append(addr_i).append(addr);
        addr_column_div.append(addr_div);
        content_four_row.append(addr_column_div);
        var comment_column_div = $('<div></div>').addClass("eight wide column").css("margin-top","5px");
        var collect_btn = $('<button></button>').addClass("ui small right floated basic blue button").attr("onclick","common.collect_change("+blog_id+")");
        var collect_icon = $('<i></i>').attr("id","collect_icon_"+blog_id);
        var collect_text_span = $('<span></span>').attr("id","collect_text_span_"+blog_id);
        if(isCollect){
            collect_icon.addClass("icon yellow star");
            collect_text_span.text("取消收藏") ;
        }else{
            collect_icon.addClass("icon grey star");
            collect_text_span.text("收藏") ;
        }
        collect_btn.append(collect_icon);
        collect_btn.append(collect_text_span);
        comment_column_div.append(collect_btn);
        var comment_btn = $('<button></button>').attr("onclick","common.comment_toggle("+blog_id+")").addClass("ui small right floated basic blue button").html("Comments(<span id='blog_comment_count_"+blog_id+"'>"+commentNum+"</span>)");
        comment_column_div.append(comment_btn);
        content_four_row.append(comment_column_div);
        content_grid.append(content_four_row);

        var $comment_row = $('<div></div>').addClass("row").attr("id","comment_"+blog_id).css("display","none");
        var $comment_column = $('<div></div>').addClass("eighteen wide column");
        var $comments_div = $('<div></div>').addClass("ui threaded comments");
        var $comment_header = $('<h3>Comments</h3>').addClass("ui dividing header");
        $comments_div.append($comment_header);
        var $comment_container = $('<div></div>').attr("id","comment_container_"+blog_id);
        $comments_div.append($comment_container);
        var $reply_form = $('<form></form>').addClass("ui reply form").attr("id","comment_form_"+blog_id);
        var $field_div = $('<div></div>').addClass("field");
        var $input_div = $('<div></div>').addClass("ui action input");
        var $input = $('<input>').attr("type","text").attr("placeholder","Add Comment").attr("id","comment_input_"+blog_id);
        var $reply_btn_container_div = $('<div></div>').attr("onclick","common.send_comment(event,"+blog_id+")");
        var $reply_btn = $('<button></button>').addClass("ui icon button");
        var $send_icon = $('<i></i>').addClass("send icon");
        $reply_btn.append($send_icon);
        $reply_btn_container_div.append($reply_btn);
        $input_div.append($input).append($reply_btn_container_div);
        $field_div.append($input_div);
        $reply_form.append($field_div);
        $comments_div.append($reply_form);
        $comment_column.append($comments_div);
        $comment_row.append($comment_column);

        content_grid.append($comment_row);
        content_column_div.append(content_grid);
        $head_content_div.append(content_column_div);
        $out_f.append($head_content_div);
        $blog.append($out_f);

        return $blog;
    },
    redirect_to_homepage:function () {
        location.href="/homePage"
    },
    redirect_to_searchpage:function () {
      location.href='/searchPage';
    },
    redirect_to_loginpage:function () {
      location.href='/login';
    },
    redirect_to_collectpage:function () {
      location.href='/collectPage';
    },
    search:function (keyword) {
      location.href='/searchResult?keyword='+encodeURI(keyword);
    },
    like_change:function e(blog_id) {
        var $like = $("#like_"+blog_id);
        if($like.hasClass("grey")){
            $.ajax("/like/"+blog_id,{
                type:'post',
                success:function (data) {
                    var $like_a = $like.parent("a");
                    var like_num = $like_a.text();
                    like_num++;
                    if(!data.success){
                        alert(data.opMsg);
                        like_num--;
                    }
                    $like_a.text(like_num);
                    $like_a.prepend($like);
                    $like.removeClass("grey");
                    $like.addClass("red");
                },
                error:function () {
                    alert("网络异常");
                }
            });
        }else{
            $.ajax("/like/cancel/"+blog_id,{
                type:'post',
                success:function (data) {
                    var $like_a = $like.parent("a");
                    var like_num = $like_a.text();
                    like_num--;
                    if(!data.success){
                        alert(data.opMsg);
                        like_num++;
                    }
                    $like_a.text(like_num);
                    $like_a.prepend($like);
                    $like.removeClass("red");
                    $like.addClass("grey");
                },
                error:function () {
                    alert("网络异常");
                }
            });
        }
    },
    generate_comment:function (userPhoto,userName,ctime,content,user_id) {
        var $single_comment = $('<div></div>').addClass("comment");
        var $avatar_a = $('<a></a>').addClass("avatar");
        var $head_img = $('<img>').addClass("ui mini image").css({"height":"auto"}).attr("src",userPhoto);
        $avatar_a.append($head_img);
        $single_comment.append($avatar_a);
        var $comment_content = $('<div></div>').addClass("content");
        var $author = $('<a>'+userName+'</a>').addClass("author").attr("href","/blogs/"+user_id);
        $comment_content.append($author);
        var $metadata_div = $('<div></div>').addClass("metadata");
        var $date_span = $('<span>'+common.deal_time(ctime)+'</span>').addClass("date");
        $metadata_div.append($date_span);
        $comment_content.append($metadata_div);
        var comment_content_text = $('<div>'+content+'</div>').addClass("text");
        $comment_content.append(comment_content_text);
        $single_comment.append($comment_content);
        return $single_comment;
    },
    comment_toggle:function d(blog_id) {
        var id = '#comment_'+blog_id;
        if($(id).is(":hidden")){
            var $comment_container = $('#comment_container_'+blog_id);
            $comment_container.html("");
            $.ajax("/comment/"+blog_id,{
                type:'get',
                success:function (data) {
                    if(data.success){
                        $.each(data.data,function (index,content) {
                            //生成评论
                            $comment_container.append(common.generate_comment("/pic?filePath="+content.userPhoto,content.userName,new Date(content.ctime),content.content,content.userId));
                        });
                    }else{
                        alert(data.opMsg);
                    }
                }
            });
        }
        $(id).toggle();
    },
    deal_time:function(da){ //传入一个Date对象
        var year = da.getFullYear();       //年
        var month = da.getMonth() + 1;     //月
        var day = da.getDate();            //日

        var hh = da.getHours();            //时
        var mm = da.getMinutes();          //分

        var clock = year + ".";

        if(month < 10)
            clock += "0";

        clock += month + ".";

        if(day < 10)
            clock += "0";

        clock += day + " ";

        if(hh < 10)
            clock += "0";

        clock += hh + ":";
        if (mm < 10) clock += '0';
        clock += mm;
        return(clock);
        // return da.getFullYear()+"."+ (da.getMonth()+1)+"." +da.getDate()+" " +da.getHours()+":"+da.getMinutes();
    },
    send_comment:function (event,blog_id) {
        var $comment_input = $('#comment_input_'+blog_id);
        var com_val = $.trim($comment_input.val());
        if(com_val.length<=0||com_val.length>100){
            alert("Comment characters should less than 100!");
        }else{
            $.ajax("/comment/publish",{
                type:'post',
                data:{
                    blogId:blog_id,
                    content:com_val
                },
                success:function (data) {
                    if(!data.success){
                        alert(data.opMsg);
                    }else{
                        var head_pic_url = data.data.userPhoto,
                            userName = data.data.userName;
                        // var head_pic_url = $('#headPic').attr("src"),
                        //     userName = $('#username').text();
                        var $comment_container = $('#comment_container_'+blog_id);
                        $comment_container.prepend(common.generate_comment('/pic?filePath='+head_pic_url,userName,new Date(),com_val,data.data.userId));
                        $comment_input.val("");
                        var blogCommentCountSpan = $('#blog_comment_count_'+blog_id);
                        blogCommentCountSpan.text(parseInt(blogCommentCountSpan.text())+1);
                    }
                },
                error:function () {
                    alert("网络异常");
                }
            });
        }
        event.preventDefault();
    },
    logout:function () {
        $.ajax("/user/logout",{
            type:"post",
            success:function () {
                location.href='/login';
            }
        })
    },
    collect_change:function (blog_id) {
        var $collect_icon = $('#collect_icon_'+blog_id);
        var $collect_text_span = $('#collect_text_span_'+blog_id);
        if($collect_icon.hasClass("grey")){
            $.ajax("/collect",{
                type:"post",
                data:{
                    blogId:blog_id
                },
                success:function (data) {
                    if(data.success){
                        $collect_icon.removeClass("grey");
                        $collect_icon.addClass("yellow");
                        $collect_text_span.text("取消收藏");
                    }else{
                        alert(data.opMsg);
                    }
                }
            });
        }else{
            common.cancel_collect(blog_id,function (data) {
                if(data.success){
                    $collect_icon.removeClass("yellow");
                    $collect_icon.addClass("grey");
                    $collect_text_span.text("收藏");
                }else{
                    alert(data.opMsg);
                }
            });
        }
    },
    cancel_collect:function (blog_id, callback) {
        $.ajax("/cancelCollect/"+blog_id,{
            type:"post",
            success:function(data){
                callback(data);
            }
        });
    },
    delete_blog:function (blog_id) {
        $.ajax("/blog/delete/"+blog_id,{
            type:"post",
            success:function (data) {
                if(data.success){
                    $('#blog_'+blog_id).transition('fade down');
                    var blogNumSpan = $('.blog_num_span');
                    blogNumSpan.text(parseInt(blogNumSpan.text())-1);
                }else {
                    alert(data.opMsg);
                }
            }
        });
    }
});

