/**
 * 初始化辅导员工作考核详情对话框
 */
var FdygzAssessInfoDlg = {
    fdygzAssessInfoData : {},
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
        },
        content: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                }
            }
        },
        point: {
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
FdygzAssessInfoDlg.clearData = function() {
    this.fdygzAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FdygzAssessInfoDlg.set = function(key, val) {
    this.fdygzAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
FdygzAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
FdygzAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
FdygzAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('content')
    .set('procInsId')
    .set('status')
    .set('hrHandleId')
    .set('hrLeaderId')
    .set('result')
    .set('year')
    .set('studentsOfficeLeaderId')
    .set('point')
    .set('commissionerId')
    .set('normPoint')
    .set('sjId')
    .set('twsjId')
    ;
    datas = [];
    $("[name='FdygzAssessItem']").each(function(){
        var item = $(this).serializeObject();
        datas.push(item);
    });
};

/**
 * 验证数据是否为空
 */
FdygzAssessInfoDlg.validate = function () {
    $('#FdygzAssessForm').data("bootstrapValidator").resetForm();
    $('#FdygzAssessForm').bootstrapValidator('validate');
    return $("#FdygzAssessForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
FdygzAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/fdygzAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.FdygzAssess.table.refresh();
        FdygzAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fdygzAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
FdygzAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/fdygzAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.FdygzAssess.table.refresh();
        FdygzAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fdygzAssessInfoData);
    ajax.start();
};


/**
 * 提交申请
 */
FdygzAssessInfoDlg.addApply = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/fdygzAssess/act/apply", function(data){
        Feng.success("申请成功!");
        window.parent.FdygzAssess.table.refresh();
        FdygzAssessInfoDlg.close();
    },function(data){
        Feng.error("申请失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fdygzAssessInfoData);
    ajax.start();
};
/**
 * 提交添加
 */
FdygzAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/fdygzAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.FdygzAssess.table.refresh();
        FdygzAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fdygzAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
FdygzAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/fdygzAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.FdygzAssess.table.refresh();
        FdygzAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.fdygzAssessInfoData);
    ajax.start();
};

/**
 * 验收审核
 */
FdygzAssessInfoDlg.auditSubmit = function(pass) {
    var that = this;
    this.collectData();
    if (!this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/fdygzAssess/act/audit", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            FdygzAssessInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.fdygzAssessInfoData['expand["pass"]'] = pass;
        that.fdygzAssessInfoData['expand["comment"]'] = $("#comment").val();
        that.fdygzAssessInfoData['expand["data"]'] = JSON.stringify(datas);
        that.fdygzAssessInfoData['act.taskId'] = $("#taskId").val();
        that.fdygzAssessInfoData['act.procInsId'] = $("#procInsId").val();
        that.fdygzAssessInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.fdygzAssessInfoData['year'] = $("#year").val();
        that.fdygzAssessInfoData['id'] = $("#id").val();
        ajax.set(that.fdygzAssessInfoData);
        ajax.start();
    });
};


/**
 * item获取新的id
 */
FdygzAssessInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "FdygzAssessItem" + this.count;
};

/**
 * 添加条目
 */
FdygzAssessInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#FdygzAssessItem").attr("id", this.newId());
};

/**
 * 删除item
 */
FdygzAssessInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

var table;
var datas = [];
$(function() {
    Feng.initValidator("FdygzAssessForm", FdygzAssessInfoDlg.validateFields);

    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#assessTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/fdygzAssess/act/data'
            ,where: {procInsId: $("#procInsId").val(),id:$("#id").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'account', title: '职工编号'}
                ,{field: 'name', title: '职工姓名'}
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
