/**
 * 初始化学历培训详情对话框
 */
var EducationExperienceInfoDlg = {
    educationExperienceInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
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
 * item获取新的id
 */
EducationExperienceInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "educationExperienceItem" + this.count;
};

/**
 * 添加条目
 */
EducationExperienceInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#educationExperienceItem").attr("id", this.newId());
};

/**
 * 删除item
 */
EducationExperienceInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
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
 * 清除为空的item Dom
 */
EducationExperienceInfoDlg.clearNullDom = function(){
    $("[name='normalAssessItem']").each(function(){
        var school = $(this).find("[name='school']").val();
        var major = $(this).find("[name='major']").val();
        var educationBackground = $(this).find("[name='educationBackground']").val();
        if(school == '' || major == ''|| educationBackground == ''){
            $(this).remove();
        }
    });
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
 * 收集数据
 */
EducationExperienceInfoDlg.collectItems = function() {
    this.clearNullDom();
    var that = this;
    $("[name='educationExperienceItem']").each(function(){
        var data = {};
        var school = $(this).find("[name='school']").val();
        var major = $(this).find("[name='major']").val();
        var educationBackground = $(this).find("[name='educationBackground']").val();
        var enrollmentTime = $(this).find("[name='enrollmentTime']").val();
        var graduateTime = $(this).find("[name='graduateTime']").val();
        var degree = $(this).find("[name='degree']").val();
        var learnStyle = $(this).find("[name='learnStyle']").val();
        data['school'] = school;
        data['major'] = major;
        data['educationBackground'] = educationBackground;
        data['enrollmentTime'] = enrollmentTime;
        data['graduateTime'] = graduateTime;
        data['degree'] = degree;
        data['learnStyle'] = learnStyle;
        that.data.push(data);
    });
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
EducationExperienceInfoDlg.addApply = function() {
    this.clearData();
    this.collectItems();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/educationExperience/doAddApply", function(data){
        Feng.success("提交申请成功!");
        window.parent.EducationExperience.table.refresh();
        EducationExperienceInfoDlg.close();
    },function(data){
        Feng.error("提交申请失败!" + data.responseJSON.message + "!");
    });
    ajax.setContentType("application/json;");
    ajax.setData(JSON.stringify(this.data));
    ajax.start();
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
