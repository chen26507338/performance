/**
 * 初始化岗位职责管理详情对话框
 */
var JobDutiesInfoDlg = {
    jobDutiesInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
JobDutiesInfoDlg.clearData = function() {
    this.jobDutiesInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JobDutiesInfoDlg.set = function(key, val) {
    this.jobDutiesInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
JobDutiesInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
JobDutiesInfoDlg.close = function() {
    parent.layer.close(window.parent.JobDuties.layerIndex);
};

/**
 * 收集数据
 */
JobDutiesInfoDlg.collectData = function() {
    this
    .set('id')
    .set('des')
    .set('point')
    .set('jobId')
    ;
};

/**
 * 验证数据是否为空
 */
JobDutiesInfoDlg.validate = function () {
    $('#JobDutiesForm').data("bootstrapValidator").resetForm();
    $('#JobDutiesForm').bootstrapValidator('validate');
    return $("#JobDutiesForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
JobDutiesInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobDuties/add", function(data){
        Feng.success("添加成功!");
        window.parent.JobDuties.table.refresh();
        JobDutiesInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobDutiesInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
JobDutiesInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobDuties/update", function(data){
        Feng.success("修改成功!");
        window.parent.JobDuties.table.refresh();
        JobDutiesInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobDutiesInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("JobDutiesForm", JobDutiesInfoDlg.validateFields);

});
