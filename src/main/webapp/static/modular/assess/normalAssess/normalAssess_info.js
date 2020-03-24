/**
 * 初始化考核指标库详情对话框
 */
var NormalAssessInfoDlg = {
    normalAssessInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
    }
};

/**
 * 清除数据
 */
NormalAssessInfoDlg.clearData = function() {
    this.normalAssessInfoData = {};
    this.data = [];
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
NormalAssessInfoDlg.set = function(key, val) {
    this.normalAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
NormalAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
NormalAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 清除为空的item Dom
 */
NormalAssessInfoDlg.clearNullDom = function(){
    $("[name='normalAssessItem']").each(function(){
        var account = $(this).find("[name='account']").val();
        var normCode = $(this).find("[name='normCode']").val();
        var result = $(this).find("[name='result']").val();
        if(account == '' || normCode == ''|| result == ''){
            $(this).remove();
        }
    });
};


/**
 * 收集数据
 */
NormalAssessInfoDlg.collectData = function() {
    this
        .set('id')
        .set('deptId')
        .set('userId')
        .set('normId')
        .set('result')
        .set('year')
        .set('createTime')
        .set('type')
        .set('status')
        .set('procInsId')
    ;
    this.clearNullDom();
    var that = this;
    $("[name='normalAssessItem']").each(function(){
        var data = {};
        var account = $(this).find("[name='account']").val();
        var normCode = $(this).find("[name='normCode']").val();
        var result = $(this).find("[name='result']").val();
        data['account'] = account;
        data['normCode'] = normCode;
        data['result'] = result;
        that.data.push(data);
    });
    // this.mutiString = mutiString;
};

/**
 * 验证数据是否为空
 */
NormalAssessInfoDlg.validate = function () {
    $('#NormalAssessForm').data("bootstrapValidator").resetForm();
    $('#NormalAssessForm').bootstrapValidator('validate');
    return $("#NormalAssessForm").data('bootstrapValidator').isValid();
};
/**
 * 删除item
 */
NormalAssessInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

/**
 * item获取新的id
 */
NormalAssessInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "normalAssessItem" + this.count;
};


/**
 * 添加条目
 */
NormalAssessInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#normalAssessItem").attr("id", this.newId());
};


/**
 * 提交添加
 */
NormalAssessInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/normalAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.NormalAssess.table.refresh();
        NormalAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.setContentType("application/json;");
    var param = {};
    param['data'] = this.data;
    param['type'] = this.normalAssessInfoData.type;
    ajax.setData(JSON.stringify(param));
    ajax.start();
};

/**
 * 提交导入
 */
NormalAssessInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/normalAssess/import", function(data){
        Feng.success("导入成功!");
        window.parent.NormalAssess.table.refresh();
        NormalAssessInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.normalAssessInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.normalAssessInfoData);
    ajax.start();
};

/**
 * 审核
 */
NormalAssessInfoDlg.auditSubmit = function(pass) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/normalAssess/audit", function(data){
        Feng.success("提交成功!");
        window.parent.ActTodoTask.table.refresh();
        NormalAssessInfoDlg.close();
    },function(data){
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
    this.normalAssessInfoData['expand["pass"]'] = pass;
    this.normalAssessInfoData['expand["comment"]'] = $("#comment").val();
    this.normalAssessInfoData['expand["data"]'] = JSON.stringify(datas);
    this.normalAssessInfoData['type'] = $("#type").val();
    this.normalAssessInfoData['year'] = $("#year").val();
    this.normalAssessInfoData['act.taskId'] = $("#taskId").val();
    this.normalAssessInfoData['act.procInsId'] = $("#procInsId").val();
    this.normalAssessInfoData['act.taskDefKey'] = $("#taskDefKey").val();
    ajax.set(this.normalAssessInfoData);
    ajax.start();
};

var table;
var datas = [];
$(function() {
    Feng.initValidator("NormalAssessForm", NormalAssessInfoDlg.validateFields);

    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#assessTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/normalAssess/normalAssessProcData/'
            ,where: {procInsId: proInsId}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'account', title: '职工编号'}
                ,{field: 'userName', title: '姓名'}
                ,{field: 'deptName', title: '部门名'}
                ,{field: 'normCode', title: '指标代码',edit:'text'}
                ,{field: 'normName', title: '指标名'}
                ,{field: 'coePoint', title: '考核系数'}
                ,{field: 'mainNormPoint', title: '校级指标分'}
                ,{field: 'collegeNormPoint', title: '院级浮动值'}
                ,{field: 'mainPoint', title: '校级分'}
                ,{field: 'collegePoint', title: '院级分'}
                ,{field: 'result', title: '考核结果',edit:'text'}
                ,{field: 'year', title: '年度'}
                ,{field: 'createTime', title: '提交时间'}
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
