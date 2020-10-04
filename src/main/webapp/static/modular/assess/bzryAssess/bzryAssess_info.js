/**
 * 初始化表彰荣誉考核详情对话框
 */
var BzryAssessInfoDlg = {
    bzryAssessInfoData : {},
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
BzryAssessInfoDlg.clearData = function() {
    this.bzryAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BzryAssessInfoDlg.set = function(key, val) {
    this.bzryAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
BzryAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
BzryAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
BzryAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('level')
    .set('procInsId')
    .set('status')
    .set('hrHandleId')
    .set('hrLeaderId')
    .set('type')
    .set('year')
    .set('commissionerId')
    .set('mainNormPoint')
    .set('collegeNormPoint')
    .set('coePoint')
    ;

    datas = [];
    $("[name='BzryAssessItem']").each(function(){
        var item = $(this).serializeObject();
        datas.push(item);
    });
};

/**
 * 验证数据是否为空
 */
BzryAssessInfoDlg.validate = function () {
    $('#BzryAssessForm').data("bootstrapValidator").resetForm();
    $('#BzryAssessForm').bootstrapValidator('validate');
    return $("#BzryAssessForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
BzryAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bzryAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.BzryAssess.table.refresh();
        BzryAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bzryAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
BzryAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bzryAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.BzryAssess.table.refresh();
        BzryAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bzryAssessInfoData);
    ajax.start();
};

/**
 * 提交申请
 */
BzryAssessInfoDlg.addApply = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bzryAssess/act/apply", function(data){
        Feng.success("申请成功!");
        window.parent.BzryAssess.table.refresh();
        BzryAssessInfoDlg.close();
    },function(data){
        Feng.error("申请失败!" + data.responseJSON.message + "!");
    });
    this.bzryAssessInfoData['expand["data"]'] = JSON.stringify(datas);
    ajax.set(this.bzryAssessInfoData);
    ajax.start();
};

/**
 * 验收审核
 */
BzryAssessInfoDlg.auditSubmit = function(pass) {
    var that = this;
    // this.collectData();
    if (pass == 1 && !this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/bzryAssess/act/audit", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            BzryAssessInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.bzryAssessInfoData['expand["pass"]'] = pass;
        that.bzryAssessInfoData['expand["comment"]'] = $("#comment").val();
        that.bzryAssessInfoData['expand["data"]'] = JSON.stringify(datas);
        that.bzryAssessInfoData['act.taskId'] = $("#taskId").val();
        that.bzryAssessInfoData['act.procInsId'] = $("#procInsId").val();
        that.bzryAssessInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.bzryAssessInfoData['year'] = $("#year").val();
        that.bzryAssessInfoData['id'] = $("#id").val();
        ajax.set(that.bzryAssessInfoData);
        ajax.start();
    });
};


/**
 * item获取新的id
 */
BzryAssessInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "BzryAssessItem" + this.count;
};

/**
 * 添加条目
 */
BzryAssessInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#BzryAssessItem").attr("id", this.newId());
};

/**
 * 删除item
 */
BzryAssessInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

var table;
var datas = [];
$(function() {
    Feng.initValidator("BzryAssessForm", BzryAssessInfoDlg.validateFields);

    layui.use('table', function () {
        table = layui.table;
        //执行渲染
        table.render({
            elem: '#assessTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/bzryAssess/act/data'
            ,where: {procInsId: $("#procInsId").val(),id:$("#id").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'account', title: '职工编号'}
                ,{field: 'name', title: '职工姓名'}
                ,{field: 'mainNormPoint', title: '校级指标分',edit:'text'}
                ,{field: 'level', title: '荣誉级别'}
                ,{field: 'type', title: '荣誉类型'}
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
