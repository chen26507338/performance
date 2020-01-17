/**
 * 初始化工作任务详情对话框
 */
var JobTaskInfoDlg = {
    jobTaskInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
JobTaskInfoDlg.clearData = function() {
    this.jobTaskInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JobTaskInfoDlg.set = function(key, val) {
    this.jobTaskInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
JobTaskInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
JobTaskInfoDlg.close = function() {
    parent.layer.close(window.parent.JobTask.layerIndex);
};

/**
 * 收集数据
 */
JobTaskInfoDlg.collectData = function() {
    this
    .set('id')
    .set('dutiesId')
    .set('deptId')
    .set('userId')
    .set('appointUserId')
    .set('applyUserId')
    .set('point')
    .set('des')
    .set('userDes')
    .set('appointUserDes')
    .set('applyUserDes')
    .set('createTime')
    .set('endTime')
    ;
};

/**
 * 验证数据是否为空
 */
JobTaskInfoDlg.validate = function () {
    $('#JobTaskForm').data("bootstrapValidator").resetForm();
    $('#JobTaskForm').bootstrapValidator('validate');
    return $("#JobTaskForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
JobTaskInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobTask/add", function(data){
        Feng.success("添加成功!");
        window.parent.JobTask.table.refresh();
        JobTaskInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobTaskInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
JobTaskInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobTask/update", function(data){
        Feng.success("修改成功!");
        window.parent.JobTask.table.refresh();
        JobTaskInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobTaskInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("JobTaskForm", JobTaskInfoDlg.validateFields);

});
