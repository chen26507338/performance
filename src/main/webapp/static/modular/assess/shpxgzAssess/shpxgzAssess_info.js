/**
 * 初始化社会培训工作考核详情对话框
 */
var ShpxgzAssessInfoDlg = {
    shpxgzAssessInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
ShpxgzAssessInfoDlg.clearData = function() {
    this.shpxgzAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShpxgzAssessInfoDlg.set = function(key, val) {
    this.shpxgzAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
ShpxgzAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ShpxgzAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
ShpxgzAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('assessName')
    .set('name')
    .set('num')
    .set('mainNormPoint')
    .set('year')
    ;
};

/**
 * 验证数据是否为空
 */
ShpxgzAssessInfoDlg.validate = function () {
    $('#ShpxgzAssessForm').data("bootstrapValidator").resetForm();
    $('#ShpxgzAssessForm').bootstrapValidator('validate');
    return $("#ShpxgzAssessForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
ShpxgzAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/shpxgzAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.ShpxgzAssess.table.refresh();
        ShpxgzAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.shpxgzAssessInfoData);
    ajax.start();
};

/**
 * 提交导入
 */
ShpxgzAssessInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/shpxgzAssess/importAssess", function(data){
        Feng.success("导入成功!");
        window.parent.ShpxgzAssess.table.refresh();
        ShpxgzAssessInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.shpxgzAssessInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.shpxgzAssessInfoData);
    ajax.start();
};


/**
 * 提交修改
 */
ShpxgzAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/shpxgzAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.ShpxgzAssess.table.refresh();
        ShpxgzAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.shpxgzAssessInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("ShpxgzAssessForm", ShpxgzAssessInfoDlg.validateFields);

});
