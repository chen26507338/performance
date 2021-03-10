/**
 * 初始化广告管理详情对话框
 */
var BannerInfoDlg = {
    bannerInfoData : {},
    validateFields:{
        bannerName: {
            validators: {
                notEmpty: {
                    message: '名称不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
BannerInfoDlg.clearData = function() {
    this.bannerInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BannerInfoDlg.set = function(key, val) {
    this.bannerInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
BannerInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
BannerInfoDlg.close = function() {
    parent.layer.close(window.parent.Banner.layerIndex);
};

/**
 * 收集数据
 */
BannerInfoDlg.collectData = function() {
    this
    .set('id')
    .set('bannerName')
    .set('bannerNameID')
    .set('bannerNameMY')
    .set('bannerNameEN')
    .set('bannerNameVI')
    .set('bannerNameTC')
    .set('bannerImgurl')
    .set('bannerImgurlID')
    .set('bannerImgurlMY')
    .set('bannerImgurlEN')
    .set('bannerImgurlVI')
    .set('bannerImgurlTC')
    .set('bannerOrder')
    .set('isGo')
    .set('createTime')
    .set('status')
    .set('bannerPlace')
    .set('createTime')
    .set('url')
    .set('content',layedit.getContent(contentEdit))
    .set('contentEN',layedit.getContent(contentENEdit))
    .set('contentID',layedit.getContent(contentIDEdit))
    .set('contentMY',layedit.getContent(contentMYEdit))
    .set('contentVI',layedit.getContent(contentVIEdit))
    .set('contentTC',layedit.getContent(contentTCEdit))
    ;
};

/**
 * 验证数据是否为空
 */
BannerInfoDlg.validate = function () {
    $('#BannerForm').data("bootstrapValidator").resetForm();
    $('#BannerForm').bootstrapValidator('validate');
    return $("#BannerForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
BannerInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/banner/add", function(data){
        Feng.success("添加成功!");
        window.parent.Banner.table.refresh();
        BannerInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bannerInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
BannerInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/banner/update", function(data){
        Feng.success("修改成功!");
        window.parent.Banner.table.refresh();
        BannerInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bannerInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("BannerForm", BannerInfoDlg.validateFields);

});
