/**
 * 初始化教学工作量详情对话框
 */
var TeachingLoadInfoDlg = {
    teachingLoadInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
    }
};
var datas = [];

/**
 * 清除数据
 */
TeachingLoadInfoDlg.clearData = function() {
    this.teachingLoadInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeachingLoadInfoDlg.set = function(key, val) {
    this.teachingLoadInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
TeachingLoadInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
TeachingLoadInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
TeachingLoadInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('courseName')
    .set('weeks')
    .set('courseType')
    .set('courseTimes')
    .set('year')
    .set('status')
    ;

    datas = [];
    $("[name='TeachingLoadItem']").each(function(){
        var item = $(this).serializeObject();
        datas.push(item);
    });
};

/**
 * 验证数据是否为空
 */
TeachingLoadInfoDlg.validate = function () {
    $('#TeachingLoadForm').data("bootstrapValidator").resetForm();
    $('#TeachingLoadForm').bootstrapValidator('validate');
    return $("#TeachingLoadForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
TeachingLoadInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teachingLoad/add", function(data){
        Feng.success("添加成功!");
        window.parent.TeachingLoad.table.refresh();
        TeachingLoadInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    this.teachingLoadInfoData['expand["data"]'] = JSON.stringify(datas);
    ajax.set(this.teachingLoadInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
TeachingLoadInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teachingLoad/update", function(data){
        Feng.success("修改成功!");
        window.parent.TeachingLoad.table.refresh();
        TeachingLoadInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teachingLoadInfoData);
    ajax.start();
};

/**
 * item获取新的id
 */
TeachingLoadInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "TeachingLoadItem" + this.count;
};

/**
 * 添加条目
 */
TeachingLoadInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#TeachingLoadItem").attr("id", this.newId());
};

/**
 * 删除item
 */
TeachingLoadInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

$(function() {
    Feng.initValidator("TeachingLoadForm", TeachingLoadInfoDlg.validateFields);

});
