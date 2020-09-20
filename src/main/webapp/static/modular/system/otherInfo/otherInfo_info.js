/**
 * 初始化其他设置详情对话框
 */
var OtherInfoInfoDlg = {
    otherInfoInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
OtherInfoInfoDlg.clearData = function() {
    this.otherInfoInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OtherInfoInfoDlg.set = function(key, val) {
    this.otherInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
OtherInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
OtherInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.OtherInfo.layerIndex);
};

/**
 * 收集数据
 */
OtherInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('otherKey')
    .set('otherValue')
    .set('beizhu')
    .set('sorts')
    ;
};

/**
 * 验证数据是否为空
 */
OtherInfoInfoDlg.validate = function () {
    $('#OtherInfoForm').data("bootstrapValidator").resetForm();
    $('#OtherInfoForm').bootstrapValidator('validate');
    return $("#OtherInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
OtherInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/otherInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.OtherInfo.table.refresh();
        OtherInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.otherInfoInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
OtherInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/otherInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.OtherInfo.table.refresh();
        OtherInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.otherInfoInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("OtherInfoForm", OtherInfoInfoDlg.validateFields);

});
