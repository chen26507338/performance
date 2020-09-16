/**
 * 初始化专业建设考核详情对话框
 */
var MajorBuildInfoDlg = {
    majorBuildInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
        approvalYear: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                }
            }
        },
        checkYear: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                }
            }
        }
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
 * 验收审核
 */
MajorBuildInfoDlg.auditCheckSubmit = function(pass) {
    var that = this;
    this.collectData();
    if (!this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/majorBuild/act/audit/check", function(data){
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
    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#assessTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+dataUrl
            ,where: {procInsId: $("#procInsId").val(),id:$("#id").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'name', title: '名称'}
                ,{field: 'coePoint', title: '考核系数'}
                ,{field: 'mainNormPoint', title: '校级指标分'}
                ,{field: 'year', title: '年度'}
            ]] //设置表头
        });

        table.on('edit(test)', function(obj){ //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
            var isAdd = false;
            for (var index in datas) {
                if (datas[index].id == obj.data.id) {
                    datas[index] = obj.data;
                    isAdd = true;
                }
            }
            if (!isAdd) {
                datas.push(obj.data);
            }
        });
    });
});
