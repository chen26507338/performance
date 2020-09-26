/**
 * 初始化分配分数记录详情对话框
 */
var AllocationPointLogInfoDlg = {
    allocationPointLogInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
AllocationPointLogInfoDlg.clearData = function() {
    this.allocationPointLogInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AllocationPointLogInfoDlg.set = function(key, val) {
    this.allocationPointLogInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
AllocationPointLogInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
AllocationPointLogInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
AllocationPointLogInfoDlg.collectData = function() {
    this
    .set('id')
    .set('type')
    .set('year')
    .set('deptId')
    ;
};

/**
 * 验证数据是否为空
 */
AllocationPointLogInfoDlg.validate = function () {
    $('#AllocationPointLogForm').data("bootstrapValidator").resetForm();
    $('#AllocationPointLogForm').bootstrapValidator('validate');
    return $("#AllocationPointLogForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
AllocationPointLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/allocationPointLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.AllocationPointLog.table.refresh();
        AllocationPointLogInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.allocationPointLogInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
AllocationPointLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/allocationPointLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.AllocationPointLog.table.refresh();
        AllocationPointLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.allocationPointLogInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("AllocationPointLogForm", AllocationPointLogInfoDlg.validateFields);

});
