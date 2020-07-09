/**
 * 初始化自然信息详情对话框
 */
var PersonalInfoInfoDlg = {
    personalInfoInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
PersonalInfoInfoDlg.clearData = function() {
    this.personalInfoInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PersonalInfoInfoDlg.set = function(key, val) {
    this.personalInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
PersonalInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
PersonalInfoInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
PersonalInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('qq')
    .set('wechat')
    .set('idCard')
    .set('age')
    .set('address')
    .set('nativePlace')
    .set('birthplace')
    .set('retireDate')
    .set('partyGroupings')
    .set('jobType')
    .set('mobile')
    .set('emergencyContact')
    .set('emergencyRelation')
    .set('emergencyMobile')
    .set('nation')
    .set('healthCondition')
    .set('proPosts')
    .set('majorSpeciality')
    .set('joinPartyTime')
    .set('firstWorkTime')
    .set('email')
    .set('name')
    .set('birthday')
    .set('sex')
    .set('phone')
    .set('personalState')
    .set('procInsId')
    .set('status')
    .set('userId')
    ;
};

/**
 * 验证数据是否为空
 */
PersonalInfoInfoDlg.validate = function () {
    $('#PersonalInfoForm').data("bootstrapValidator").resetForm();
    $('#PersonalInfoForm').bootstrapValidator('validate');
    return $("#PersonalInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
PersonalInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/personalInfo/add", function(data){
        Feng.success("提交成功!");
        window.parent.PersonalInfo.table.refresh();
        PersonalInfoInfoDlg.close();
    },function(data){
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.personalInfoInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
PersonalInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/personalInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.PersonalInfo.table.refresh();
        PersonalInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.personalInfoInfoData);
    ajax.start();
};


/**
 * 审核
 */
PersonalInfoInfoDlg.auditSubmit = function(pass) {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/personalInfo/update", function(data){
        Feng.success("提交成功!");
        window.parent.ActTodoTask.table.refresh();
        PersonalInfoInfoDlg.close();
    },function(data){
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
    this.personalInfoInfoData['expand["pass"]'] = pass;
    this.personalInfoInfoData['expand["comment"]'] = $("#comment").val();
    this.personalInfoInfoData['act.taskId'] = $("#taskId").val();
    this.personalInfoInfoData['act.procInsId'] = $("#procInsId").val();
    this.personalInfoInfoData['act.taskDefKey'] = $("#taskDefKey").val();
    ajax.set(this.personalInfoInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("PersonalInfoForm", PersonalInfoInfoDlg.validateFields);

});
