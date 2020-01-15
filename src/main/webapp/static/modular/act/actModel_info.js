/**
 * 模型详情对话框（可用于添加和修改对话框）
 */
var ActModelDlg = {
    ActModelData: {}
};

/**
 * 清除数据
 */
ActModelDlg.clearData = function () {
    this.ActModelData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActModelDlg.set = function (key, val) {
    this.ActModelData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActModelDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ActModelDlg.close = function () {
    parent.layer.close(window.parent.ActModelDlg.layerIndex);
};

/**
 * 点击部门input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
ActModelDlg.onClickDept = function (e, treeId, treeNode) {
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#deptid").attr("value", treeNode.id);
};



/**
 * 隐藏部门选择的树
 */
ActModelDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

/**
 * 收集数据
 */
ActModelDlg.collectData = function () {
    this.set('name').set('key').set('description').set('password').set('avatar')
        .set('email').set('name').set('birthday').set('rePassword').set('deptid').set('phone');
};

/**
 * 验证数据是否为空
 */
ActModelDlg.validate = function () {
    $('#actModelForm').data("bootstrapValidator").resetForm();
    $('#actModelForm').bootstrapValidator('validate');
    return $("#actModelForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加用户
 */
ActModelDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    window.open(Feng.ctxPath + "/act/model/create?name=" + this.ActModelData.name
        + "&key=" + this.ActModelData.key + "&description=" + this.ActModelData.description,'_blank');
    ActModelDlg.close();
};

/**
 * 提交修改
 */
ActModelDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/edit", function (data) {
        Feng.success("修改成功!");
        if (window.parent.MgrUser != undefined) {
            window.parent.MgrUser.table.refresh();
            ActModelDlg.close();
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ActModelData);
    ajax.start();
};

/**
 * 修改密码
 */
ActModelDlg.chPwd = function () {
    var ajax = new $ax(Feng.ctxPath + "/mgr/changePwd", function (data) {
        Feng.success("修改成功!");
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("oldPwd");
    ajax.set("newPwd");
    ajax.set("rePwd");
    ajax.start();

};

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
        ActModelDlg.hideDeptSelectTree();
    }
}

$(function () {
    Feng.initValidator("actModelForm", ActModelDlg.validateFields);

    //初始化性别选项
    $("#sex").val($("#sexValue").val());

    // 初始化头像上传
    var avatarUp = new $WebUpload("avatar");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();


});
