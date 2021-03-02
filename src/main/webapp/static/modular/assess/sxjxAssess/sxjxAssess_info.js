/**
 * 初始化实训绩效考核详情对话框
 */
var SxjxAssessInfoDlg = {
    sxjxAssessInfoData : {},
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
        },
        content: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                }
            }
        },
        problemUrl: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                }
            }
        },
        sxsglUser: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                }
            }
        },
        result: {
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
SxjxAssessInfoDlg.clearData = function() {
    this.sxjxAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SxjxAssessInfoDlg.set = function(key, val) {
    this.sxjxAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
SxjxAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
SxjxAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
SxjxAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('procInsId')
    .set('status')
    .set('hrHandleId')
    .set('content')
    .set('problemUrl')
    .set('result')
    .set('deanId')
    .set('sxjxCommissioner')
    .set('mainNormPoint')
    .set('collegeNormPoint')
    .set('deptId')
    .set('hrLeaderId')
    .set('sxsglUser')
    .set('resultUrl')
    .set('coePoint')
    ;
    
    datas = [];
    $("[name='SxjxAssessItem']").each(function(){
        var item = $(this).serializeObject();
        datas.push(item);
    });
};

/**
 * 验证数据是否为空
 */
SxjxAssessInfoDlg.validate = function () {
    $('#SxjxAssessForm').data("bootstrapValidator").resetForm();
    $('#SxjxAssessForm').bootstrapValidator('validate');
    return $("#SxjxAssessForm").data('bootstrapValidator').isValid();
};

SxjxAssessInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sxjxAssess/importAssess", function(data){
        Feng.success("导入成功!");
        window.parent.SxjxAssess.table.refresh();
        SxjxAssessInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.sxjxAssessInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.sxjxAssessInfoData)
    ;
    ajax.start();
}

/**
 * 提交添加
 */
SxjxAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sxjxAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.SxjxAssess.table.refresh();
        SxjxAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.sxjxAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
SxjxAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sxjxAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.SxjxAssess.table.refresh();
        SxjxAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.sxjxAssessInfoData);
    ajax.start();
};


/**
 * 提交申请
 */
SxjxAssessInfoDlg.addApply = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sxjxAssess/act/apply", function(data){
        Feng.success("申请成功!");
        window.parent.SxjxAssess.table.refresh();
        SxjxAssessInfoDlg.close();
    },function(data){
        Feng.error("申请失败!" + data.responseJSON.message + "!");
    });
    this.sxjxAssessInfoData['expand["data"]'] = JSON.stringify(datas);
    ajax.set(this.sxjxAssessInfoData);
    ajax.start();
};

/**
 * 分配分数
 */
SxjxAssessInfoDlg.doAllocation = function() {
    this.collectData();

    var that = this;
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/sxjxAssess/doAllocation", function(data){
            Feng.success("提交成功!");
            window.parent.SxjxAssess.table.refresh();
            SxjxAssessInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.sxjxAssessInfoData['expand["data"]'] = JSON.stringify(datas);
        ajax.set(that.sxjxAssessInfoData);
        ajax.start();
    });
};

/**
 * 验收审核
 */
SxjxAssessInfoDlg.auditSubmit = function(pass) {
    var that = this;
    this.collectData();
    if (pass == 1 && !this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/sxjxAssess/act/audit", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            SxjxAssessInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.sxjxAssessInfoData['expand["pass"]'] = pass;
        that.sxjxAssessInfoData['expand["comment"]'] = $("#comment").val();
        that.sxjxAssessInfoData['expand["data"]'] = JSON.stringify(datas);
        that.sxjxAssessInfoData['act.taskId'] = $("#taskId").val();
        that.sxjxAssessInfoData['act.procInsId'] = $("#procInsId").val();
        that.sxjxAssessInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.sxjxAssessInfoData['year'] = $("#year").val();
        that.sxjxAssessInfoData['id'] = $("#id").val();
        ajax.set(that.sxjxAssessInfoData);
        ajax.start();
    });
};


/**
 * item获取新的id
 */
SxjxAssessInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "SxjxAssessItem" + this.count;
};

/**
 * 添加条目
 */
SxjxAssessInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#SxjxAssessItem").attr("id", this.newId());
};

/**
 * 删除item
 */
SxjxAssessInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

var table;
var datas = [];
$(function() {
    Feng.initValidator("SxjxAssessForm", SxjxAssessInfoDlg.validateFields);

    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#assessTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/sxjxAssess/act/data'
            ,where: {procInsId: $("#procInsId").val(),id:$("#id").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'deptName', title: '部门'}
                ,{field: 'normCode', title: '指标代码',edit:'text'}
                ,{field: 'normContent', title: 'II级指标'}
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
