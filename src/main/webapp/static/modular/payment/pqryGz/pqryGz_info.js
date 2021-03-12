/**
 * 初始化派遣人员详情对话框
 */
var PqryGzInfoDlg = {
    pqryGzInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
PqryGzInfoDlg.clearData = function() {
    this.pqryGzInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PqryGzInfoDlg.set = function(key, val) {
    this.pqryGzInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
PqryGzInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
PqryGzInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
PqryGzInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('year')
    .set('month')
    .set('gz')
    .set('bfgzce')
    .set('kcj')
    .set('yfgz')
    .set('ylsyjs')
    .set('ybsyjs')
    .set('gjjjs')
    .set('gsjs')
    .set('yl')
    .set('sy')
    .set('yb')
    .set('gjj')
    .set('hj')
    .set('sfgz')
    ;
};

/**
 * 验证数据是否为空
 */
PqryGzInfoDlg.validate = function () {
    $('#PqryGzForm').data("bootstrapValidator").resetForm();
    $('#PqryGzForm').bootstrapValidator('validate');
    return $("#PqryGzForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
PqryGzInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/pqryGz/add", function(data){
        Feng.success("添加成功!");
        window.parent.PqryGz.table.refresh();
        PqryGzInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.pqryGzInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
PqryGzInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/pqryGz/update", function(data){
        Feng.success("修改成功!");
        window.parent.PqryGz.table.refresh();
        PqryGzInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.pqryGzInfoData);
    ajax.start();
};
/**
 * 提交导入
 */
PqryGzInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/pqryGz/importData", function(data){
        Feng.success("导入成功!");
        window.parent.PqryGz.table.refresh();
        PqryGzInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.pqryGzInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.pqryGzInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("PqryGzForm", PqryGzInfoDlg.validateFields);

});
