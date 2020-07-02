/**
 * 初始化科研项目详情对话框
 */
var ScientificProjectInfoDlg = {
    scientificProjectInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
    }
};

/**
 * 清除数据
 */
ScientificProjectInfoDlg.clearData = function() {
    this.scientificProjectInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScientificProjectInfoDlg.set = function(key, val) {
    this.scientificProjectInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
ScientificProjectInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ScientificProjectInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
ScientificProjectInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('nature')
    .set('type')
    .set('startTime')
    .set('endTime')
    .set('expenditure')
    .set('rank')
    .set('userId')
    .set('procInsId')
    .set('status')
    ;
};

/**
 * 验证数据是否为空
 */
ScientificProjectInfoDlg.validate = function () {
    $('#ScientificProjectForm').data("bootstrapValidator").resetForm();
    $('#ScientificProjectForm').bootstrapValidator('validate');
    return $("#ScientificProjectForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
ScientificProjectInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scientificProject/add", function(data){
        Feng.success("添加成功!");
        window.parent.ScientificProject.table.refresh();
        ScientificProjectInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scientificProjectInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
ScientificProjectInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scientificProject/update", function(data){
        Feng.success("修改成功!");
        window.parent.ScientificProject.table.refresh();
        ScientificProjectInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scientificProjectInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("ScientificProjectForm", ScientificProjectInfoDlg.validateFields);

});
