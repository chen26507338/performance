/**
 * 初始化专项工作考核详情对话框
 */
var SpecialAssessMemberInfoDlg = {
    specialAssessMemberInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
SpecialAssessMemberInfoDlg.clearData = function() {
    this.specialAssessMemberInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SpecialAssessMemberInfoDlg.set = function(key, val) {
    this.specialAssessMemberInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
SpecialAssessMemberInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
SpecialAssessMemberInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
SpecialAssessMemberInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('saId')
    .set('point')
    .set('money')
    ;
};

/**
 * 验证数据是否为空
 */
SpecialAssessMemberInfoDlg.validate = function () {
    $('#SpecialAssessMemberForm').data("bootstrapValidator").resetForm();
    $('#SpecialAssessMemberForm').bootstrapValidator('validate');
    return $("#SpecialAssessMemberForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
SpecialAssessMemberInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/specialAssessMember/add", function(data){
        Feng.success("添加成功!");
        window.parent.SpecialAssessMember.table.refresh();
        SpecialAssessMemberInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.specialAssessMemberInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
SpecialAssessMemberInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/specialAssessMember/update", function(data){
        Feng.success("修改成功!");
        window.parent.SpecialAssessMember.table.refresh();
        SpecialAssessMemberInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.specialAssessMemberInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("SpecialAssessMemberForm", SpecialAssessMemberInfoDlg.validateFields);

});
