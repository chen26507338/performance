/**
 * 初始化管理服务成员详情对话框
 */
var ManServiceMemberInfoDlg = {
    manServiceMemberInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
ManServiceMemberInfoDlg.clearData = function() {
    this.manServiceMemberInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ManServiceMemberInfoDlg.set = function(key, val) {
    this.manServiceMemberInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
ManServiceMemberInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ManServiceMemberInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
ManServiceMemberInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('mainNormPoint')
    .set('collegeNormPoint')
    .set('status')
    .set('coePoint')
    .set('year')
    .set('mServiceId')
    ;
};

/**
 * 验证数据是否为空
 */
ManServiceMemberInfoDlg.validate = function () {
    $('#ManServiceMemberForm').data("bootstrapValidator").resetForm();
    $('#ManServiceMemberForm').bootstrapValidator('validate');
    return $("#ManServiceMemberForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
ManServiceMemberInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/manServiceMember/add", function(data){
        Feng.success("添加成功!");
        window.parent.ManServiceMember.table.refresh();
        ManServiceMemberInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.manServiceMemberInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
ManServiceMemberInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/manServiceMember/update", function(data){
        Feng.success("修改成功!");
        window.parent.ManServiceMember.table.refresh();
        ManServiceMemberInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.manServiceMemberInfoData);
    ajax.start();
};

/**
 * 提交导入
 */
ManServiceMemberInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/manServiceMember/importAssess", function(data){
        Feng.success("导入成功!");
        window.parent.ManServiceMember.table.refresh();
        ManServiceMemberInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.manServiceMemberInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.manServiceMemberInfoData);
    ajax.start();
};


$(function() {
    Feng.initValidator("ManServiceMemberForm", ManServiceMemberInfoDlg.validateFields);

});
