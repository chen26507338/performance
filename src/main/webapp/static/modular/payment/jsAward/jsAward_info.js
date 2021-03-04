/**
 * 初始化竞赛奖励详情对话框
 */
var JsAwardInfoDlg = {
    jsAwardInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
JsAwardInfoDlg.clearData = function() {
    this.jsAwardInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JsAwardInfoDlg.set = function(key, val) {
    this.jsAwardInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
JsAwardInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
JsAwardInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
JsAwardInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('yrxs')
    .set('project')
    .set('type')
    .set('hjlb')
    .set('hjdj')
    .set('money')
    .set('year')
    ;
};

/**
 * 验证数据是否为空
 */
JsAwardInfoDlg.validate = function () {
    $('#JsAwardForm').data("bootstrapValidator").resetForm();
    $('#JsAwardForm').bootstrapValidator('validate');
    return $("#JsAwardForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
JsAwardInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jsAward/add", function(data){
        Feng.success("添加成功!");
        window.parent.JsAward.table.refresh();
        JsAwardInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jsAwardInfoData);
    ajax.start();
};

/**
 * 提交导入
 */
JsAwardInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jsAward/importData", function(data){
        Feng.success("导入成功!");
        window.parent.JsAward.table.refresh();
        JsAwardInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.jsAwardInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.jsAwardInfoData);
    ajax.start();
};


/**
 * 提交修改
 */
JsAwardInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jsAward/update", function(data){
        Feng.success("修改成功!");
        window.parent.JsAward.table.refresh();
        JsAwardInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jsAwardInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("JsAwardForm", JsAwardInfoDlg.validateFields);

});
