/**
 * 初始化辅导员日常工作考核成员详情对话框
 */
var FdygzAssessMemberInfoDlg = {
    fdygzAssessMemberInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
FdygzAssessMemberInfoDlg.clearData = function() {
    this.fdygzAssessMemberInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FdygzAssessMemberInfoDlg.set = function(key, val) {
    this.fdygzAssessMemberInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
FdygzAssessMemberInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
FdygzAssessMemberInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
FdygzAssessMemberInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('mainNormPoint')
    .set('collegeNormPoint')
    .set('status')
    .set('coePoint')
    .set('year')
    .set('fWorkId')
    ;
};

/**
 * 验证数据是否为空
 */
FdygzAssessMemberInfoDlg.validate = function () {
    $('#FdygzAssessMemberForm').data("bootstrapValidator").resetForm();
    $('#FdygzAssessMemberForm').bootstrapValidator('validate');
    return $("#FdygzAssessMemberForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
FdygzAssessMemberInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/fdygzAssessMember/add", function(data){
        Feng.success("添加成功!");
        window.parent.FdygzAssessMember.table.refresh();
        FdygzAssessMemberInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fdygzAssessMemberInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
FdygzAssessMemberInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/fdygzAssessMember/update", function(data){
        Feng.success("修改成功!");
        window.parent.FdygzAssessMember.table.refresh();
        FdygzAssessMemberInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fdygzAssessMemberInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("FdygzAssessMemberForm", FdygzAssessMemberInfoDlg.validateFields);

});
