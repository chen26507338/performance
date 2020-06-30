/**
 * 初始化工作简历详情对话框
 */
var WorkResumeInfoDlg = {
    workResumeInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
    }
};

/**
 * 清除数据
 */
WorkResumeInfoDlg.clearData = function() {
    this.workResumeInfoData = {};
};
/**
 * 清除为空的item Dom
 */
WorkResumeInfoDlg.clearNullDom = function(){
    $("[name='WorkResumeItem']").each(function(){
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
WorkResumeInfoDlg.collectItems = function() {
    this.clearNullDom();
    var that = this;
    this.data = [];
    $("[name='WorkResumeItem']").each(function(){
        that.data.push($(this).serializeObject());
    });
};
/**
 * 提交添加
 */
WorkResumeInfoDlg.addApply = function() {
    this.clearData();
    this.collectItems();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workResume/doAddApply", function(data){
        Feng.success("提交申请成功!");
        window.parent.WorkResume.table.refresh();
        WorkResumeInfoDlg.close();
    },function(data){
        Feng.error("提交申请失败!" + data.responseJSON.message + "!");
    });
    ajax.setContentType("application/json;");
    ajax.setData(JSON.stringify(this.data));
    ajax.start();
};

/**
 * 提交添加
 */
WorkResumeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/WorkResume/add", function(data){
        Feng.success("添加成功!");
        window.parent.WorkResume.table.refresh();
        WorkResumeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workResumeInfoData);
    ajax.start();
};

/**
 * 审核
 */
WorkResumeInfoDlg.auditSubmit = function(pass) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workResume/audit", function(data){
        Feng.success("提交成功!");
        window.parent.ActTodoTask.table.refresh();
        WorkResumeInfoDlg.close();
    },function(data){
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
    this.workResumeInfoData['expand["pass"]'] = pass;
    this.workResumeInfoData['expand["comment"]'] = $("#comment").val();
    this.workResumeInfoData['expand["data"]'] = JSON.stringify(datas);
    this.workResumeInfoData['act.taskId'] = $("#taskId").val();
    this.workResumeInfoData['act.procInsId'] = $("#procInsId").val();
    this.workResumeInfoData['act.taskDefKey'] = $("#taskDefKey").val();
    ajax.set(this.workResumeInfoData);
    ajax.start();
};


/**
 * item获取新的id
 */
WorkResumeInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "WorkResumeItem" + this.count;
};

/**
 * 添加条目
 */
WorkResumeInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#WorkResumeItem").attr("id", this.newId());
};

/**
 * 删除item
 */
WorkResumeInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkResumeInfoDlg.set = function(key, val) {
    this.workResumeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
WorkResumeInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
WorkResumeInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
WorkResumeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('startDate')
    .set('endDate')
    .set('company')
    .set('department')
    .set('job')
    .set('title')
    .set('pensionMonth')
    .set('procInsId')
    .set('status')
    ;
};

/**
 * 验证数据是否为空
 */
WorkResumeInfoDlg.validate = function () {
    $('#WorkResumeForm').data("bootstrapValidator").resetForm();
    $('#WorkResumeForm').bootstrapValidator('validate');
    return $("#WorkResumeForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
WorkResumeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workResume/add", function(data){
        Feng.success("添加成功!");
        window.parent.WorkResume.table.refresh();
        WorkResumeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workResumeInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
WorkResumeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workResume/update", function(data){
        Feng.success("修改成功!");
        window.parent.WorkResume.table.refresh();
        WorkResumeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workResumeInfoData);
    ajax.start();
};

var table;
var datas = [];
$(function() {
    Feng.initValidator("WorkResumeForm", WorkResumeInfoDlg.validateFields);
    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#editTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/workResume/workResumeProcData/'
            ,where: {procInsId: $("#procInsId").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'startDate', title: '开始时间',edit:'text'}
                ,{field: 'endDate', title: '结束时间',edit:'text'}
                ,{field: 'company', title: '工作单位',edit:'text'}
                ,{field: 'department', title: '所在部门',edit:'text'}
                ,{field: 'job', title: '职务',edit:'text'}
                ,{field: 'title', title: '职称',edit:'text'}
                ,{field: 'pensionMonth', title: '养老缴交月数',edit:'text'}
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
