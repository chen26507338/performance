/**
 * 初始化岗位管理详情对话框
 */
var JobInfoDlg = {
    jobInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
JobInfoDlg.clearData = function() {
    this.jobInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JobInfoDlg.set = function(key, val) {
    this.jobInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
JobInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
JobInfoDlg.close = function() {
    parent.layer.close(window.parent.Job.layerIndex);
};

/**
 * 收集数据
 */
JobInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('des')
    .set('deptId')
    .set('status')
    ;
};

/**
 * 验证数据是否为空
 */
JobInfoDlg.validate = function () {
    $('#JobForm').data("bootstrapValidator").resetForm();
    $('#JobForm').bootstrapValidator('validate');
    return $("#JobForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
JobInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/job/add", function(data){
        Feng.success("添加成功!");
        window.parent.Job.table.refresh();
        JobInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
JobInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/job/update", function(data){
        Feng.success("修改成功!");
        window.parent.Job.table.refresh();
        JobInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("JobForm", JobInfoDlg.validateFields);

});
