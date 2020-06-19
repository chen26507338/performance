/**
 * 初始化学历培训详情对话框
 */
var EducationExperienceInfoDlg = {
    educationExperienceInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
EducationExperienceInfoDlg.clearData = function() {
    this.educationExperienceInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EducationExperienceInfoDlg.set = function(key, val) {
    this.educationExperienceInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
EducationExperienceInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
EducationExperienceInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
EducationExperienceInfoDlg.collectData = function() {
    this
    .set('id')
    .set('enrollmentTime')
    .set('graduateTime')
    .set('school')
    .set('major')
    .set('educationBackground')
    .set('degree')
    .set('learnStyle')
    .set('userId')
    ;
};

/**
 * 验证数据是否为空
 */
EducationExperienceInfoDlg.validate = function () {
    $('#EducationExperienceForm').data("bootstrapValidator").resetForm();
    $('#EducationExperienceForm').bootstrapValidator('validate');
    return $("#EducationExperienceForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
EducationExperienceInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/educationExperience/add", function(data){
        Feng.success("添加成功!");
        window.parent.EducationExperience.table.refresh();
        EducationExperienceInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.educationExperienceInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
EducationExperienceInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/educationExperience/update", function(data){
        Feng.success("修改成功!");
        window.parent.EducationExperience.table.refresh();
        EducationExperienceInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.educationExperienceInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("EducationExperienceForm", EducationExperienceInfoDlg.validateFields);

});
