/**
 * 初始化工作得分详情对话框
 */
var JobTaskPointInfoDlg = {
    jobTaskPointInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
JobTaskPointInfoDlg.clearData = function() {
    this.jobTaskPointInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JobTaskPointInfoDlg.set = function(key, val) {
    this.jobTaskPointInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
JobTaskPointInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
JobTaskPointInfoDlg.close = function() {
    parent.layer.close(window.parent.JobTaskPoint.layerIndex);
};

/**
 * 收集数据
 */
JobTaskPointInfoDlg.collectData = function() {
    this
    .set('id')
    .set('taskId')
    .set('userId')
    .set('point')
    .set('type')
    .set('createTime')
    ;
};

/**
 * 验证数据是否为空
 */
JobTaskPointInfoDlg.validate = function () {
    $('#JobTaskPointForm').data("bootstrapValidator").resetForm();
    $('#JobTaskPointForm').bootstrapValidator('validate');
    return $("#JobTaskPointForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
JobTaskPointInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobTaskPoint/add", function(data){
        Feng.success("添加成功!");
        window.parent.JobTaskPoint.table.refresh();
        JobTaskPointInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobTaskPointInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
JobTaskPointInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobTaskPoint/update", function(data){
        Feng.success("修改成功!");
        window.parent.JobTaskPoint.table.refresh();
        JobTaskPointInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobTaskPointInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("JobTaskPointForm", JobTaskPointInfoDlg.validateFields);

});
