/**
 * 初始化在编人员详情对话框
 */
var ZbryGzInfoDlg = {
    zbryGzInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
ZbryGzInfoDlg.clearData = function() {
    this.zbryGzInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ZbryGzInfoDlg.set = function(key, val) {
    this.zbryGzInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
ZbryGzInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ZbryGzInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
ZbryGzInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('inTime')
    .set('gwgz')
    .set('xjgz')
    .set('gwjt')
    .set('shbt')
    .set('tsbt')
    .set('tz')
    .set('bf')
    .set('yfs')
    .set('kk')
    .set('tzglbxj')
    .set('gjj')
    .set('yb')
    .set('ylj')
    .set('zynj')
    .set('syj')
    .set('hyf')
    .set('fsf')
    .set('sds')
    .set('sfs')
    ;
};

/**
 * 验证数据是否为空
 */
ZbryGzInfoDlg.validate = function () {
    $('#ZbryGzForm').data("bootstrapValidator").resetForm();
    $('#ZbryGzForm').bootstrapValidator('validate');
    return $("#ZbryGzForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
ZbryGzInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/zbryGz/add", function(data){
        Feng.success("添加成功!");
        window.parent.ZbryGz.table.refresh();
        ZbryGzInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.zbryGzInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
ZbryGzInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/zbryGz/update", function(data){
        Feng.success("修改成功!");
        window.parent.ZbryGz.table.refresh();
        ZbryGzInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.zbryGzInfoData);
    ajax.start();
};

/**
 * 提交导入
 */
ZbryGzInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/zbryGz/importData", function(data){
        Feng.success("导入成功!");
        window.parent.ZbryGz.table.refresh();
        ZbryGzInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.zbryGzInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.zbryGzInfoData);
    ajax.start();
};


$(function() {
    Feng.initValidator("ZbryGzForm", ZbryGzInfoDlg.validateFields);

});
