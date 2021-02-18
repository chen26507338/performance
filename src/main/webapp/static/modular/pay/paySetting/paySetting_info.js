/**
 * 初始化薪酬设置详情对话框
 */
var PaySettingInfoDlg = {
    paySettingInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
PaySettingInfoDlg.clearData = function() {
    this.paySettingInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PaySettingInfoDlg.set = function(key, val) {
    this.paySettingInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
PaySettingInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
PaySettingInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
PaySettingInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('money')
    ;
};

/**
 * 验证数据是否为空
 */
PaySettingInfoDlg.validate = function () {
    $('#PaySettingForm').data("bootstrapValidator").resetForm();
    $('#PaySettingForm').bootstrapValidator('validate');
    return $("#PaySettingForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
PaySettingInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/paySetting/add", function(data){
        Feng.success("添加成功!");
        window.parent.PaySetting.table.refresh();
        PaySettingInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.paySettingInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
PaySettingInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/paySetting/update", function(data){
        Feng.success("修改成功!");
        window.parent.PaySetting.table.refresh();
        PaySettingInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.paySettingInfoData);
    ajax.start();
};

/**
 * 提交导入
 */
PaySettingInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/importSetting", function(data){
        Feng.success("导入成功!");
        window.parent.MgrUser.table.refresh();
        PaySettingInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.paySettingInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.paySettingInfoData);
    ajax.start();
};


$(function() {
    Feng.initValidator("PaySettingForm", PaySettingInfoDlg.validateFields);

});
