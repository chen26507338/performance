/**
 * 初始化党支部工作考核详情对话框
 */
var DzbWorkAssessInfoDlg = {
    dzbWorkAssessInfoData : {},
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
DzbWorkAssessInfoDlg.clearData = function() {
    this.dzbWorkAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DzbWorkAssessInfoDlg.set = function(key, val) {
    this.dzbWorkAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
DzbWorkAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
DzbWorkAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
DzbWorkAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('procInsId')
    .set('status')
    .set('hrHandleId')
    .set('normId')
    .set('result')
    .set('year')
    .set('zzbLeaderId')
    .set('dzbCommissioner')
    .set('mainNormPoint')
    .set('collegeNormPoint')
    .set('deptId')
    ;


    datas = [];
    $("[name='DzbWorkAssessItem']").each(function(){
        var item = $(this).serializeObject();
        datas.push(item);
    });
};

/**
 * 验证数据是否为空
 */
DzbWorkAssessInfoDlg.validate = function () {
    $('#DzbWorkAssessForm').data("bootstrapValidator").resetForm();
    $('#DzbWorkAssessForm').bootstrapValidator('validate');
    return $("#DzbWorkAssessForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
DzbWorkAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dzbWorkAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.DzbWorkAssess.table.refresh();
        DzbWorkAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dzbWorkAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
DzbWorkAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dzbWorkAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.DzbWorkAssess.table.refresh();
        DzbWorkAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dzbWorkAssessInfoData);
    ajax.start();
};


/**
 * 提交申请
 */
DzbWorkAssessInfoDlg.addApply = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dzbWorkAssess/act/apply", function(data){
        Feng.success("申请成功!");
        window.parent.DzbWorkAssess.table.refresh();
        DzbWorkAssessInfoDlg.close();
    },function(data){
        Feng.error("申请失败!" + data.responseJSON.message + "!");
    });
    this.dzbWorkAssessInfoData['expand["data"]'] = JSON.stringify(datas);
    ajax.set(this.dzbWorkAssessInfoData);
    ajax.start();
};

/**
 * 分配分数
 */
DzbWorkAssessInfoDlg.doAllocation = function() {
    this.collectData();

    var that = this;
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/dzbWorkAssess/doAllocation", function(data){
            Feng.success("提交成功!");
            window.parent.DzbWorkAssess.table.refresh();
            DzbWorkAssessInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.dzbWorkAssessInfoData['expand["data"]'] = JSON.stringify(datas);
        ajax.set(that.dzbWorkAssessInfoData);
        ajax.start();
    });
};

/**
 * 验收审核
 */
DzbWorkAssessInfoDlg.auditSubmit = function(pass) {
    var that = this;
    // this.collectData();
    if (pass == 1 && !this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/dzbWorkAssess/act/audit", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            DzbWorkAssessInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.dzbWorkAssessInfoData['expand["pass"]'] = pass;
        that.dzbWorkAssessInfoData['expand["comment"]'] = $("#comment").val();
        that.dzbWorkAssessInfoData['expand["data"]'] = JSON.stringify(datas);
        that.dzbWorkAssessInfoData['act.taskId'] = $("#taskId").val();
        that.dzbWorkAssessInfoData['act.procInsId'] = $("#procInsId").val();
        that.dzbWorkAssessInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.dzbWorkAssessInfoData['year'] = $("#year").val();
        that.dzbWorkAssessInfoData['id'] = $("#id").val();
        ajax.set(that.dzbWorkAssessInfoData);
        ajax.start();
    });
};


/**
 * item获取新的id
 */
DzbWorkAssessInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "DzbWorkAssessItem" + this.count;
};

/**
 * 添加条目
 */
DzbWorkAssessInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#DzbWorkAssessItem").attr("id", this.newId());
};

/**
 * 删除item
 */
DzbWorkAssessInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

var table;
var datas = [];

$(function() {
    Feng.initValidator("DzbWorkAssessForm", DzbWorkAssessInfoDlg.validateFields);

    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#assessTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/dzbWorkAssess/act/data'
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
