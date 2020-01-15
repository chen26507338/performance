;( function () {
    "use strict";
    var param_map = Result.getLocationParamObject();

    $(function () {


    });

    window.wxregister = function () {
        location.href = "https://open.weixin.qq.com/connect/qrconnect?appid=wxf511d616f2efa086&redirect_uri=http%3a%2f%2f116.62.212.151%3a8080%2fpcdd-portal-cms%2fwechat%2fsign&response_type=code&scope=snsapi_login&state="+param_map.user_id+"#wechat_redirect";
    };

    window.register = function () {
        param_map.code = param_map.user_id;
        param_map.is_wap = 1;
        param_map.account = $("#account").val();
        param_map.password = $("#password").val();
        param_map.er_password = $("#er_password").val();
        if (param_map.account == null || param_map.account == "") {
            alert($("#account").attr("placeholder"));
            return;
        }
        if (param_map.account.length < 6) {
            alert("帐号需要六位以上");
            return;
        }
        if (param_map.password == null || param_map.password == "") {
            alert($("#password").attr("placeholder"));
            return;
        }

        if (param_map.er_password == null || param_map.er_password == "") {
            alert($("#er_password").attr("placeholder"));
            return;
        }
        if (param_map.er_password != param_map.password) {
            alert("两次密码不一致");
            return;
        }
        Result.doResult({
            url: Interface.dong.register,
            data: param_map,
            type: "POST"
        }, function (data) {
            alert("注册成功");
            setTimeout(function () {
                Result.forward("index.html", param_map);
            }, 300)

        }, function (desc) {
            alert("对不起: " + desc);
        }, function (err) {
            alert("error" + err);
        });

    };

    window.toXieyi = function () {
        Result.forward("../common/userXieyi.html", param_map);
    };

    window.toUpload = function () {
        window.location.href = Interface.host.dong+"/pcdd-wap";
    };


}(window) );
