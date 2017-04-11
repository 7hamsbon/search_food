/**
 * Created by hamsbon on 2017/3/11.
 */
var photo_url="default_food.png";
var module = new Object({
    page_init:function () {
        var $pub_btn_div = $("<div></div>")
            .addClass("circular ui inverted blue icon button")
            .css("margin-left","20px")
            .attr("onclick","module.pub_view()")
            .attr("id","pub_btn")
            .attr("data-inverted","")
            .attr("data-tooltip","Publish Blog")
            .attr("data-position","bottom left");
        var $pub_btn_i = $('<i></i>').addClass("loading icon Share Alternate");
        $pub_btn_div.append($pub_btn_i);
        $('#homepage_btn').after($pub_btn_div);

        // $('#head')
        //     .sticky({
        //         context: $('#example1'),
        //         offset:1
        //     });
        // $('#cont')
        //     .sticky({
        //         context: $('#example1'),
        //         offset:90
        //     });
        $('.coupled.modal')
            .modal({
                allowMultiple: false
            })
        ;
        $('.test.modal').modal('hide');
        $file = $('#file');
        $file.change(module.upload);
    },
   upload:function () {
       var fd = new FormData();
       fd.append("food",document.getElementById("file").files[0]);
       common.upload_pic('/blog/uploadPhoto',fd,'#pub_pic','#file','150px');
   },
    pub_view:function c() {
        $('#pub_pic').css("background","url('/pic?filePath=default_food.png')").css("background-repeat","no-repeat");
        var fn = $('#food_name'),
            addr = $('#addr'),
            con = $('#content'),
            desc = $('#desc'),
            pri = $('#price'),
            $file = $('#file');
        $file.val("");
        photo_url = "default_food.png";
        fn.val("");
        addr.val("");
        con.val("");
        desc.val("");
        pri.val("");

        $('.test.modal')
            .modal({
                closable  : false,
                onDeny    : function(){
                },
                onApprove : function() {
                    var e = false;
                    //以下验证逻辑
                    var fn_val = $.trim(fn.val()); //食物名非空
                    if(fn_val.length<=0 || fn_val.length>=100){
                        fn.parent().addClass("error");
                        e= true;
                    }else{
                        e=false;
                        fn.parent().removeClass("error");
                    }
                    var addr_val = $.trim(addr.val());  //地址非空
                    if(addr_val.length<=0 || addr_val.length>=100){
                        e=true;
                        addr.parent().addClass("error");
                    }else{
                        e=false;
                        addr.parent().removeClass("error");
                    }
                    var con_val = $.trim(con.val()); //内容非空
                    if(con_val.length<=0 || con_val.length>=400){
                        e=true;
                        con.parent().addClass("error");
                    }else{
                        e=false;
                        con.parent().removeClass("error");
                    }
                    var desc_val = $.trim(desc.val()); //描述非空
                    if(desc_val.length<=0 || desc_val.length>=400){
                        e=true;
                        desc.parent().addClass("error");
                    }else{
                        e=false;
                        desc.parent().removeClass("error");
                    }
                    var pri_val = $.trim(pri.val()); //价格是数字
                    if(!$.isNumeric(pri_val)){
                        e=true;
                        pri.parent().addClass("error");
                    }else{
                        e=false;
                        pri.parent().removeClass("error");
                    }
                    if(!e){
                        $.ajax("/blog/publish",{
                            type:"post",
                            data:{
                                address:addr_val,
                                foodName:fn_val,
                                content:con_val,
                                description:desc_val,
                                price:pri_val,
                                photoPath:photo_url
                            },
                            success:function (data) {
                                $('#pub_status').append(data.opMsg);
                                if(data.success){
                                    // $('#pub_pic').css("background","url('/pic?filePath=default_food.png')");
                                    $('.second.modal')
                                        .modal('show');
                                    var $new_blog_view = common.generate_blog_view('/pic?filePath='+data.data.userHeadPath,$('#username').text(),new Date(),pri_val,data.data.id,"/pic?filePath="+photo_url,fn_val,con_val,desc_val,addr_val,0,0,false,data.data.userId,false,null,true);
                                    $('#example1').prepend($new_blog_view);
                                }
                            },
                            error:function () {

                            }
                        });
                    }
                    return false;

                }
            })
            .modal('show');
    }
});
$(function () {
    module.page_init();
});


