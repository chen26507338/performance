/**
 * 初始化打卡签到管理详情对话框
 */
var SignInLogInfoDlg = {
    signInLogInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
SignInLogInfoDlg.clearData = function() {
    this.signInLogInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SignInLogInfoDlg.set = function(key, val) {
    this.signInLogInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
SignInLogInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
SignInLogInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
SignInLogInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('location')
    .set('longitude')
    .set('latitude')
    .set('createTime')
    .set('type')
    ;
};

/**
 * 验证数据是否为空
 */
SignInLogInfoDlg.validate = function () {
    $('#SignInLogForm').data("bootstrapValidator").resetForm();
    $('#SignInLogForm').bootstrapValidator('validate');
    return $("#SignInLogForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
SignInLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/signInLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.SignInLog.table.refresh();
        SignInLogInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.signInLogInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
SignInLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/signInLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.SignInLog.table.refresh();
        SignInLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.signInLogInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("SignInLogForm", SignInLogInfoDlg.validateFields);

});
