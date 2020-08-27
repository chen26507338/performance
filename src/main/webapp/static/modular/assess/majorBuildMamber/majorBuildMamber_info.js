/**
 * 初始化专业建设项目成员详情对话框
 */
var MajorBuildMemberInfoDlg = {
    MajorBuildMemberInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
MajorBuildMemberInfoDlg.clearData = function() {
    this.MajorBuildMemberInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MajorBuildMemberInfoDlg.set = function(key, val) {
    this.MajorBuildMemberInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
MajorBuildMemberInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
MajorBuildMemberInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
MajorBuildMemberInfoDlg.collectData = function() {
    this
    .set('id')
    .set('buildId')
    .set('mainNormPoint')
    .set('collegeNormPoint')
    .set('status')
    .set('coePoint')
    ;
};

/**
 * 验证数据是否为空
 */
MajorBuildMemberInfoDlg.validate = function () {
    $('#MajorBuildMemberForm').data("bootstrapValidator").resetForm();
    $('#MajorBuildMemberForm').bootstrapValidator('validate');
    return $("#MajorBuildMemberForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
MajorBuildMemberInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/MajorBuildMember/add", function(data){
        Feng.success("添加成功!");
        window.parent.MajorBuildMember.table.refresh();
        MajorBuildMemberInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.MajorBuildMemberInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
MajorBuildMemberInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/MajorBuildMember/update", function(data){
        Feng.success("修改成功!");
        window.parent.MajorBuildMember.table.refresh();
        MajorBuildMemberInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.MajorBuildMemberInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("MajorBuildMemberForm", MajorBuildMemberInfoDlg.validateFields);

});
