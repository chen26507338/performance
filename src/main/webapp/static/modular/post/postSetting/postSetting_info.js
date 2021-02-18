/**
 * 初始化职务设置详情对话框
 */
var PostSettingInfoDlg = {
    postSettingInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
PostSettingInfoDlg.clearData = function() {
    this.postSettingInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PostSettingInfoDlg.set = function(key, val) {
    this.postSettingInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
PostSettingInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
PostSettingInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
PostSettingInfoDlg.collectData = function() {
    this
    .set('id')
    .set('ldks')
    .set('zj')
    .set('zw')
    ;
};

/**
 * 验证数据是否为空
 */
PostSettingInfoDlg.validate = function () {
    $('#PostSettingForm').data("bootstrapValidator").resetForm();
    $('#PostSettingForm').bootstrapValidator('validate');
    return $("#PostSettingForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
PostSettingInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/postSetting/add", function(data){
        Feng.success("添加成功!");
        window.parent.PostSetting.table.refresh();
        PostSettingInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.postSettingInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
PostSettingInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/postSetting/update", function(data){
        Feng.success("修改成功!");
        window.parent.PostSetting.table.refresh();
        PostSettingInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.postSettingInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("PostSettingForm", PostSettingInfoDlg.validateFields);

});
