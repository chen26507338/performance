/**
 * 初始化代理校聘人员详情对话框
 */
var DlxpryGzInfoDlg = {
    dlxpryGzInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
DlxpryGzInfoDlg.clearData = function() {
    this.dlxpryGzInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DlxpryGzInfoDlg.set = function(key, val) {
    this.dlxpryGzInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
DlxpryGzInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
DlxpryGzInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
DlxpryGzInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('year')
    .set('month')
    .set('jbgz')
    .set('jcxjx')
    .set('bfgzd')
    .set('yfgz')
    .set('ylbx')
    .set('yl')
    .set('sybx')
    .set('gjj')
    .set('ghf')
    .set('qtkk')
    .set('sfs')
    ;
};

/**
 * 验证数据是否为空
 */
DlxpryGzInfoDlg.validate = function () {
    $('#DlxpryGzForm').data("bootstrapValidator").resetForm();
    $('#DlxpryGzForm').bootstrapValidator('validate');
    return $("#DlxpryGzForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
DlxpryGzInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dlxpryGz/add", function(data){
        Feng.success("添加成功!");
        window.parent.DlxpryGz.table.refresh();
        DlxpryGzInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dlxpryGzInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
DlxpryGzInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dlxpryGz/update", function(data){
        Feng.success("修改成功!");
        window.parent.DlxpryGz.table.refresh();
        DlxpryGzInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dlxpryGzInfoData);
    ajax.start();
};

/**
 * 提交导入
 */
DlxpryGzInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dlxpryGz/importData", function(data){
        Feng.success("导入成功!");
        window.parent.DlxpryGz.table.refresh();
        DlxpryGzInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.dlxpryGzInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.dlxpryGzInfoData);
    ajax.start();
};


$(function() {
    Feng.initValidator("DlxpryGzForm", DlxpryGzInfoDlg.validateFields);

});
