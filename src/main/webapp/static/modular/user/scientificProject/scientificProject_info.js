/**
 * 初始化科研项目详情对话框
 */
var ScientificProjectInfoDlg = {
    scientificProjectInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
        year: {
            validators: {
                notEmpty: {
                    message: '年度不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
ScientificProjectInfoDlg.clearData = function() {
    this.scientificProjectInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScientificProjectInfoDlg.set = function(key, val) {
    this.scientificProjectInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
ScientificProjectInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ScientificProjectInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
ScientificProjectInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('nature')
    .set('type')
    .set('startTime')
    .set('endTime')
    .set('expenditure')
    .set('rank')
    .set('userId')
    .set('procInsId')
    .set('status')
    ;
};

/**
 * 验证数据是否为空
 */
ScientificProjectInfoDlg.validate = function () {
    $('#ScientificProjectForm').data("bootstrapValidator").resetForm();
    $('#ScientificProjectForm').bootstrapValidator('validate');
    return $("#ScientificProjectForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
ScientificProjectInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scientificProject/add", function(data){
        Feng.success("添加成功!");
        window.parent.ScientificProject.table.refresh();
        ScientificProjectInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scientificProjectInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
ScientificProjectInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scientificProject/update", function(data){
        Feng.success("修改成功!");
        window.parent.ScientificProject.table.refresh();
        ScientificProjectInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scientificProjectInfoData);
    ajax.start();
};

/**
 * 提交导入
 */
ScientificProjectInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scientificProject/importAssess", function(data){
        Feng.success("导入成功!");
        window.parent.ScientificProject.table.refresh();
        ScientificProjectInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.scientificProjectInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.scientificProjectInfoData);
    ajax.start();
};

/**
 * 审核
 */
ScientificProjectInfoDlg.auditSubmit = function(pass) {
    var that = this;
    if (!this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/scientificProject/audit", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            ScientificProjectInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.scientificProjectInfoData['expand["pass"]'] = pass;
        that.scientificProjectInfoData['expand["comment"]'] = $("#comment").val();
        that.scientificProjectInfoData['expand["data"]'] = JSON.stringify(datas);
        that.scientificProjectInfoData['act.taskId'] = $("#taskId").val();
        that.scientificProjectInfoData['act.procInsId'] = $("#procInsId").val();
        that.scientificProjectInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.scientificProjectInfoData['year'] = $("#year").val();
        ajax.set(that.scientificProjectInfoData);
        ajax.start();
    });
};

/**
 * item获取新的id
 */
ScientificProjectInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "ScientificProjectItem" + this.count;
};

/**
 * 添加条目
 */
ScientificProjectInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#ScientificProjectItem").attr("id", this.newId());
};

/**
 * 删除item
 */
ScientificProjectInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};
/**
 * 提交添加
 */
ScientificProjectInfoDlg.addApply = function() {
    this.clearData();
    this.collectItems();

    var that = this;
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/scientificProject/doAddApply", function(data){
            Feng.success("提交申请成功!");
            window.parent.ScientificProject.table.refresh();
            ScientificProjectInfoDlg.close();
        },function(data){
            Feng.error("提交申请失败!" + data.responseJSON.message + "!");
        });
        ajax.setContentType("application/json;");
        ajax.setData(JSON.stringify(that.data));
        ajax.start();
    });

};

/**
 * 清除为空的item Dom
 */
ScientificProjectInfoDlg.clearNullDom = function(){
    $("[name='KinshipItem']").each(function(){
        var company = $(this).find("[name='company']").val();
        var department = $(this).find("[name='department']").val();
        var job = $(this).find("[name='job']").val();
        if(company == '' || department == ''|| job == ''){
            $(this).remove();
        }
    });
};
/**
 * 收集数据
 */
ScientificProjectInfoDlg.collectItems = function() {
    // this.clearNullDom();
    var that = this;
    this.data = [];
    $("[name='ScientificProjectItem']").each(function(){
        that.data.push($(this).serializeObject());
    });
};

var table;
var datas = [];

$(function() {
    Feng.initValidator("ScientificProjectForm", ScientificProjectInfoDlg.validateFields);
    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#editTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/scientificProject/scientificProjectProcData/'
            ,where: {procInsId: $("#procInsId").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'name', title: '课题名称',edit:'text'}
                ,{field: 'nature', title: '课题性质',edit:'text'}
                ,{field: 'type', title: '课题项目类别',edit:'text'}
                ,{field: 'startTime', title: '开题时间',edit:'text'}
                ,{field: 'endTime', title: '结题时间',edit:'text'}
                ,{field: 'expenditure', title: '到账经费',edit:'text'}
                ,{field: 'rank', title: '排名',edit:'text'}
                ,{field: 'normCode', title: '指标代码',edit:'text'}
                ,{field: 'normName', title: '指标名'}
                ,{field: 'coePoint', title: '考核系数'}
                ,{field: 'mainNormPoint', title: '校级指标分'}
                ,{field: 'year', title: '年度'}
            ]] //设置表头
        });

        table.on('edit(editFilter)', function(obj){ //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
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
