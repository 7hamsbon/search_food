/**
 * Created by hamsbon on 2017/3/18.
 */
var personalInfo = new Object({
    page_init:function () {
        $('#test').dropdown();
        $('#head')
            .sticky({
                context: $('#example1'),
                offset:1
            });
        $('.small.modal')
            .modal('hide');
        $file = $('#file');
        $file.change(personalInfo.upload);
    },
    upload:function () {
    var fd = new FormData();
    fd.append("file",document.getElementById("file").files[0]);
    common.upload_pic('/user/uploadhead',fd,'#user_head','#file','150px');
    },
    generate_error_item: function (header, description) {
        var $item_div = $('<div></div>').addClass("item");
        var $icon = $('<i></i>').addClass("red Remove icon");
        var $content = $('<div></div>').addClass("content");
        var $header = $('<div>'+header+'</div>').addClass("header");
        var $description = $('<div>'+description+'</div>').addClass("description");
        $item_div.append($icon);
        $content.append($header);
        $content.append($description);
        $item_div.append($content);
        return $item_div;
    },
    update_user_info:function (event) {
        var pass = $.trim($('#pass').val()),
            age = $.trim($('#age').val()),
            gender = $('#gender').val(),
            addr = $.trim($('#addr').val()),
            profile = $.trim($('#summ').val()),
            email = $.trim($('#email').val()),
            phone = $.trim($('#phone').val());

        var $error_msg_list = $('<div></div>').addClass("ui list");

        var validate = false;

        //以下为验证逻辑
        if(profile.length > 400){
            $error_msg_list.append(personalInfo.generate_error_item("Profile","Characters should less than 400!"));
        }
        if(age!="" && !$.isNumeric(age)){
            $error_msg_list.append(personalInfo.generate_error_item("Age","Age should be a number!"));
        }
        if(phone!="" && !$.isNumeric(phone) && phone.length!=11){
            $error_msg_list.append(personalInfo.generate_error_item("Phone","Phone should be a number & its characters should equal to 11!"));
        }
        if(email!="" && !email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
            $error_msg_list.append(personalInfo.generate_error_item("E-mail","E-mail should look like xxx@xxx.xxx!"));
        }
        if($error_msg_list.children().length==0){
            validate = true;
        }
        //验证通过发送请求注册
        if(validate){
            $.ajax("/user/update",{
                type:'post',
                data:{
                    password:pass,
                    photoUrl:photo_url,
                    gender:gender,
                    age:age,
                    address:addr,
                    email:email,
                    phone:phone,
                    summary:profile
                },
                success:function (data) {
                    if(data.success){
                        alert("更新成功");
                        location.href="/homePage";//跳转主页
                    }else{
                        alert(data.opMsg);
                        event.preventDefault();
                    }
                },
                error:function () {
                    alert("更新失败，请检查网络情况");
                }
            });
            event.preventDefault();
        }else{//验证不通过
            var $error_modal = $('.small.modal');
            var $error_modal_content = $('#error_modal_content');
            $error_modal_content.html("");
            $error_modal_content.append($error_msg_list);
            $error_modal.modal('show');
            event.preventDefault();
        }
    }
});