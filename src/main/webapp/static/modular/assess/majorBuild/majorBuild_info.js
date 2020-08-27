/**
 * 初始化专业建设考核详情对话框
 */
var MajorBuildInfoDlg = {
    majorBuildInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
    }
};

/**
 * 清除数据
 */
MajorBuildInfoDlg.clearData = function() {
    this.majorBuildInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MajorBuildInfoDlg.set = function(key, val) {
    this.majorBuildInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
MajorBuildInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
MajorBuildInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
MajorBuildInfoDlg.collectData = function() {
    this
    .set('id')
    .set('principalId')
    .set('name')
    .set('time')
    .set('procInsId')
    .set('status')
    .set('hrHandleId')
    .set('deanUserId')
    .set('hrLeaderId')
    .set('majorCommissioner')
    .set('normId')
    .set('normCode')
    .set('result')
    .set('approvalYear')
    .set('deanOfficeLeaderId')
    .set('checkYear')
    ;

    var that = this;
    datas = [];
    $("[name='MajorBuildItem']").each(function(){
        var item = $(this).serializeObject();
        datas.push(item);
    });
};

/**
 * 验证数据是否为空
 */
MajorBuildInfoDlg.validate = function () {
    $('#MajorBuildForm').data("bootstrapValidator").resetForm();
    $('#MajorBuildForm').bootstrapValidator('validate');
    return $("#MajorBuildForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
MajorBuildInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/majorBuild/add", function(data){
        Feng.success("添加成功!");
        window.parent.MajorBuild.table.refresh();
        MajorBuildInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.majorBuildInfoData);
    ajax.start();
};

/**
 * 立项申请
 */
MajorBuildInfoDlg.addApplySubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/majorBuild/act/apply/approval", function(data){
        Feng.success("申请成功!");
        window.parent.MajorBuild.table.refresh();
        MajorBuildInfoDlg.close();
    },function(data){
        Feng.error("申请失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.majorBuildInfoData);
    ajax.start();
};

/**
 * 审核
 */
MajorBuildInfoDlg.auditSubmit = function(pass) {
    var that = this;
    this.collectData();
    if (!this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/majorBuild/act/audit/approval", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            MajorBuildInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.majorBuildInfoData['expand["pass"]'] = pass;
        that.majorBuildInfoData['expand["comment"]'] = $("#comment").val();
        that.majorBuildInfoData['expand["data"]'] = JSON.stringify(datas);
        that.majorBuildInfoData['act.taskId'] = $("#taskId").val();
        that.majorBuildInfoData['act.procInsId'] = $("#procInsId").val();
        that.majorBuildInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.majorBuildInfoData['year'] = $("#year").val();
        that.majorBuildInfoData['id'] = $("#id").val();
        ajax.set(that.majorBuildInfoData);
        ajax.start();
    });
};

/**
 * 提交修改
 */
MajorBuildInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/majorBuild/update", function(data){
        Feng.success("修改成功!");
        window.parent.MajorBuild.table.refresh();
        MajorBuildInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.majorBuildInfoData);
    ajax.start();
};


/**
 * item获取新的id
 */
MajorBuildInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "MajorBuildItem" + this.count;
};

/**
 * 添加条目
 */
MajorBuildInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#MajorBuildItem").attr("id", this.newId());
};

/**
 * 删除item
 */
MajorBuildInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

var table;
var datas = [];

$(function() {
    Feng.initValidator("MajorBuildForm", MajorBuildInfoDlg.validateFields);

});
