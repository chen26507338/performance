/**
 * 初始化竞赛获奖详情对话框
 */
var JshjAssessInfoDlg = {
    jshjAssessInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
JshjAssessInfoDlg.clearData = function() {
    this.jshjAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JshjAssessInfoDlg.set = function(key, val) {
    this.jshjAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
JshjAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
JshjAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
JshjAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('type')
    .set('jsName')
    .set('sxName')
    .set('jsLevel')
    .set('hjLevel')
    .set('mainNormPoint')
    .set('jsType')
    .set('year')
    .set('userId')
    ;
};

/**
 * 验证数据是否为空
 */
JshjAssessInfoDlg.validate = function () {
    $('#JshjAssessForm').data("bootstrapValidator").resetForm();
    $('#JshjAssessForm').bootstrapValidator('validate');
    return $("#JshjAssessForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
JshjAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jshjAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.JshjAssess.table.refresh();
        JshjAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jshjAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
JshjAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jshjAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.JshjAssess.table.refresh();
        JshjAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jshjAssessInfoData);
    ajax.start();
};

/**
 * 提交导入
 */
JshjAssessInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jshjAssess/importAssess", function(data){
        Feng.success("导入成功!");
        window.parent.JshjAssess.table.refresh();
        JshjAssessInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.jshjAssessInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.jshjAssessInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("JshjAssessForm", JshjAssessInfoDlg.validateFields);

});
