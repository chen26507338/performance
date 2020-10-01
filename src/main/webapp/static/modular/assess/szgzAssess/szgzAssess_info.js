/**
 * 初始化思政工作详情对话框
 */
var SzgzAssessInfoDlg = {
    szgzAssessInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
        year: {
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
SzgzAssessInfoDlg.clearData = function() {
    this.szgzAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SzgzAssessInfoDlg.set = function(key, val) {
    this.szgzAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
SzgzAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
SzgzAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
SzgzAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('content')
    .set('procInsId')
    .set('status')
    .set('hrHandleId')
    .set('hrLeaderId')
    .set('result')
    .set('year')
    .set('studentsOfficeLeaderId')
    .set('deanId')
    .set('commissionerId')
    .set('mainNormPoint')
    .set('collegeNormPoint')
    .set('coePoint')
    .set('type')
    ;

    datas = [];
    $("[name='SzgzAssessItem']").each(function(){
        var item = $(this).serializeObject();
        datas.push(item);
    });
};

/**
 * 验证数据是否为空
 */
SzgzAssessInfoDlg.validate = function () {
    $('#SzgzAssessForm').data("bootstrapValidator").resetForm();
    $('#SzgzAssessForm').bootstrapValidator('validate');
    return $("#SzgzAssessForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
SzgzAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/szgzAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.SzgzAssess.table.refresh();
        SzgzAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.szgzAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
SzgzAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/szgzAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.SzgzAssess.table.refresh();
        SzgzAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.szgzAssessInfoData);
    ajax.start();
};

/**
 * 提交申请
 */
SzgzAssessInfoDlg.addApply = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/szgzAssess/act/apply", function(data){
        Feng.success("申请成功!");
        window.parent.SzgzAssess.table.refresh();
        SzgzAssessInfoDlg.close();
    },function(data){
        Feng.error("申请失败!" + data.responseJSON.message + "!");
    });
    this.szgzAssessInfoData['expand["data"]'] = JSON.stringify(datas);
    ajax.set(this.szgzAssessInfoData);
    ajax.start();
};

/**
 * 验收审核
 */
SzgzAssessInfoDlg.auditSubmit = function(pass) {
    var that = this;
    // this.collectData();
    if (pass == 1 && !this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/szgzAssess/act/audit", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            SzgzAssessInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.szgzAssessInfoData['expand["pass"]'] = pass;
        that.szgzAssessInfoData['expand["comment"]'] = $("#comment").val();
        that.szgzAssessInfoData['expand["data"]'] = JSON.stringify(datas);
        that.szgzAssessInfoData['act.taskId'] = $("#taskId").val();
        that.szgzAssessInfoData['act.procInsId'] = $("#procInsId").val();
        that.szgzAssessInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.szgzAssessInfoData['year'] = $("#year").val();
        that.szgzAssessInfoData['id'] = $("#id").val();
        ajax.set(that.szgzAssessInfoData);
        ajax.start();
    });
};


/**
 * item获取新的id
 */
SzgzAssessInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "SzgzAssessItem" + this.count;
};

/**
 * 添加条目
 */
SzgzAssessInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#SzgzAssessItem").attr("id", this.newId());
};

/**
 * 删除item
 */
SzgzAssessInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

var table;
var datas = [];
$(function() {
    Feng.initValidator("SzgzAssessForm", SzgzAssessInfoDlg.validateFields);
    layui.use('table', function () {
        table = layui.table;
        //执行渲染
        table.render({
            elem: '#assessTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/szgzAssess/act/data'
            ,where: {procInsId: $("#procInsId").val(),id:$("#id").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'account', title: '职工编号'}
                ,{field: 'name', title: '职工姓名'}
                ,{field: 'mainNormPoint', title: '校级指标分',edit:'text'}
                ,{field: 'content', title: '考核内容'}
                ,{field: 'result', title: '考核结果'}
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
