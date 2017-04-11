/**
 * Created by hamsbon on 2017/2/26.
 */
var photo_url = "/images/default_head.png";//默认头像地址
var $file;

$(function () {
    $('#err_msg').hide();
    $file = $('#file');
    $file.change(upload_head);
});
function upload_head() {
    common.upload_pic('/user/uploadhead',new FormData($('form')[0]),'.header','#file','150px');
}
// function upload_ead() {
//     //判断是否有选择上传文件
//     var imgPath = $file.val();
//     if (imgPath == "") {
//         return;
//     }
//     //判断上传文件的后缀名
//     var strExtension = imgPath.substr(imgPath.lastIndexOf('.') + 1);
//     if (strExtension != 'jpg' && strExtension != 'gif'
//         && strExtension != 'png' && strExtension != 'bmp') {
//         alert("请选择图片文件");
//         return;
//     }
//     var formData = new FormData($('form')[0]);
//     $.ajax({
//         url: '/user/uploadhead',  //server script to process data
//         type: 'POST',
//         xhr: function() {  // custom xhr
//             var myXhr = $.ajaxSettings.xhr();
//             if(myXhr.upload){ // check if upload property exists
//                 myXhr.upload.addEventListener('progress',function progressHandlingFunction(e){
//                     if(e.lengthComputable){
//                         $('progress').attr({value:e.loaded,max:e.total});
//                     }
//                 }, false); // for handling the progress of the upload
//             }
//             return myXhr;
//         },
//         //Ajax事件
//         success: function (data) {
//             if(data.success){
//                 photo_url = data.opMsg;//更新头像url
//                 //将新头像显示出来
//                 var u = "/pic?filePath="+data.opMsg;
//                 $('.header').css("background","url("+u+")")
//                     .css("background-repeat","no-repeat")
//                     .css("-webkit-background-size"," 150px 150px");
//             }else{
//                 alert(data.opMsg);
//             }
//         },
//         error: function () {
//             alert("上传失败，请检查网络后重试");
//         },
//         // Form数据
//         data: formData,
//         //Options to tell JQuery not to process data or worry about content-type
//         cache: false,
//         contentType: false,
//         processData: false
//     });
// }

function registry(event) {
    //获取用户输入的值
    var $msg = $('#err_msg'),
        user_name = $.trim($('#username').val()),
        pass = $.trim($('#password').val()),
        phone = $.trim($('#phone').val()),
        gender = $.trim($('#gender').val()),
        address = $.trim($('#address').val()),
        email = $.trim($('#email').val()),
        age = $.trim($('#age').val());

    var validate = false;

    var $l = $('<div></div>').addClass("ui list");
    //以下为验证逻辑
    if(user_name.length<=0){
        $l.append(getItemDiv("User name can't be empty!"));
    }
    if(pass.length<=0){
        $l.append(getItemDiv("Password can't be empty!"));
    }
    if(age!="" && !$.isNumeric(age)){
        $l.append(getItemDiv("Age should be a number!"));
    }
    if(phone!="" && !$.isNumeric(phone) && phone.length!=11){
        $l.append(getItemDiv("Phone should be a number & 11 charactors!"));
    }
    if(email!="" && !email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
        $l.append(getItemDiv("E-mail should like xxx@xxx.com!"));
    }
    if($l.children().length==0){
        validate = true;
    }
    //验证通过发送请求注册
    if(validate){
        $.ajax("/user/register",{
            type:'post',
            data:{
                username:user_name,
                password:pass,
                photoUrl:photo_url,
                gender:gender,
                age:age,
                address:address,
                email:email,
                phone:phone
            },
            success:function (data) {
                // alert("data.success--->>>"+data.success);
                if(data.success){
                    location.href="/login";//跳转登录页面
                }else{
                    //noinspection JSUnresolvedVariable
                    alert(data.opMsg);
                    event.preventDefault();
                }
            },
            error:function () {
                alert("注册失败，请检查网络情况");
            }
        });
        event.preventDefault();
    }else{//验证不通过
        if(!$msg.is(':hidden')){
            $msg.transition();
        }
        $msg.html("");
        $msg.append($l);
        $msg.transition();
        event.preventDefault();
    }
    console.log("username-->"+user_name+",pass-->"+pass+",phone-->>"+phone+",gender-->"+gender+",address-->>"+address+",email-->"+email+",age-->"+age);
}
//获得错误款项的div
function getItemDiv(content) {
    var $i = $('<i></i>').addClass("remove icon");
    var $c = $('<div></div>').addClass("content").text(content);
    return $('<div></div>').addClass("item").append($i).append($c);
}
