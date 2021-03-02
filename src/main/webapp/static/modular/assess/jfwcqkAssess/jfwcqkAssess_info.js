/**
 * 初始化经费完成情况考核详情对话框
 */
var JfwcqkAssessInfoDlg = {
    jfwcqkAssessInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
JfwcqkAssessInfoDlg.clearData = function() {
    this.jfwcqkAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JfwcqkAssessInfoDlg.set = function(key, val) {
    this.jfwcqkAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
JfwcqkAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
JfwcqkAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
JfwcqkAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('assessName')
    .set('jfwcf')
    .set('mainNormPoint')
    .set('year')
    .set('coePoint')
    ;
};

/**
 * 验证数据是否为空
 */
JfwcqkAssessInfoDlg.validate = function () {
    $('#JfwcqkAssessForm').data("bootstrapValidator").resetForm();
    $('#JfwcqkAssessForm').bootstrapValidator('validate');
    return $("#JfwcqkAssessForm").data('bootstrapValidator').isValid();
};

/**
 * 提交导入
 */
JfwcqkAssessInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jfwcqkAssess/importAssess", function(data){
        Feng.success("导入成功!");
        window.parent.JfwcqkAssess.table.refresh();
        JfwcqkAssessInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.jfwcqkAssessInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.jfwcqkAssessInfoData)
    ;
    ajax.start();
};


/**
 * 提交添加
 */
JfwcqkAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jfwcqkAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.JfwcqkAssess.table.refresh();
        JfwcqkAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jfwcqkAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
JfwcqkAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jfwcqkAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.JfwcqkAssess.table.refresh();
        JfwcqkAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jfwcqkAssessInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("JfwcqkAssessForm", JfwcqkAssessInfoDlg.validateFields);

});
