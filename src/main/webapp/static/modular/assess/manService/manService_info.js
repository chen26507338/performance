/**
 * 初始化管理服务详情对话框
 */
var ManServiceInfoDlg = {
    manServiceInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],
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
ManServiceInfoDlg.clearData = function() {
    this.manServiceInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ManServiceInfoDlg.set = function(key, val) {
    this.manServiceInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
ManServiceInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ManServiceInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
ManServiceInfoDlg.collectData = function() {
    this
    .set('id')
    .set('procInsId')
    .set('status')
    .set('hrHandleId')
    .set('deptLeaderId')
    .set('hrLeaderId')
    .set('generalManId')
    .set('normId')
    .set('normCode')
    .set('result')
    .set('year')
    ;


    datas = [];
    $("[name='ManServiceItem']").each(function(){
        var item = $(this).serializeObject();
        datas.push(item);
    });
};

/**
 * 验证数据是否为空
 */
ManServiceInfoDlg.validate = function () {
    $('#ManServiceForm').data("bootstrapValidator").resetForm();
    $('#ManServiceForm').bootstrapValidator('validate');
    return $("#ManServiceForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
ManServiceInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/manService/add", function(data){
        Feng.success("添加成功!");
        window.parent.ManService.table.refresh();
        ManServiceInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.manServiceInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
ManServiceInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/manService/update", function(data){
        Feng.success("修改成功!");
        window.parent.ManService.table.refresh();
        ManServiceInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.manServiceInfoData);
    ajax.start();
};

/**
 * 提交申请
 */
ManServiceInfoDlg.addApply = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/manService/act/apply", function(data){
        Feng.success("申请成功!");
        window.parent.ManService.table.refresh();
        ManServiceInfoDlg.close();
    },function(data){
        Feng.error("申请失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.manServiceInfoData);
    ajax.start();
};


/**
 * 验收审核
 */
ManServiceInfoDlg.auditSubmit = function(pass) {
    var that = this;
    this.collectData();
    if (!this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/manService/act/audit", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            ManServiceInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.manServiceInfoData['expand["pass"]'] = pass;
        that.manServiceInfoData['expand["comment"]'] = $("#comment").val();
        that.manServiceInfoData['expand["data"]'] = JSON.stringify(datas);
        that.manServiceInfoData['act.taskId'] = $("#taskId").val();
        that.manServiceInfoData['act.procInsId'] = $("#procInsId").val();
        that.manServiceInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.manServiceInfoData['year'] = $("#year").val();
        that.manServiceInfoData['id'] = $("#id").val();
        ajax.set(that.manServiceInfoData);
        ajax.start();
    });
};


/**
 * item获取新的id
 */
ManServiceInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "ManServiceItem" + this.count;
};

/**
 * 添加条目
 */
ManServiceInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#ManServiceItem").attr("id", this.newId());
};

/**
 * 删除item
 */
ManServiceInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

var table;
var datas = [];


$(function() {
    Feng.initValidator("ManServiceForm", ManServiceInfoDlg.validateFields);

    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#assessTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/manService/act/data'
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
