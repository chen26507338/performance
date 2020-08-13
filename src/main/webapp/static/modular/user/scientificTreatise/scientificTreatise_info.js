/**
 * 初始化科研论著详情对话框
 */
var ScientificTreatiseInfoDlg = {
    scientificTreatiseInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
    }
};

/**
 * 清除数据
 */
ScientificTreatiseInfoDlg.clearData = function() {
    this.scientificTreatiseInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScientificTreatiseInfoDlg.set = function(key, val) {
    this.scientificTreatiseInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
ScientificTreatiseInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ScientificTreatiseInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
ScientificTreatiseInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('publishDate')
    .set('title')
    .set('periodicalName')
    .set('authorOrder')
    .set('periodicalLevel')
    .set('procInsId')
    .set('status')
    ;
};

/**
 * 验证数据是否为空
 */
ScientificTreatiseInfoDlg.validate = function () {
    $('#ScientificTreatiseForm').data("bootstrapValidator").resetForm();
    $('#ScientificTreatiseForm').bootstrapValidator('validate');
    return $("#ScientificTreatiseForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
ScientificTreatiseInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scientificTreatise/add", function(data){
        Feng.success("添加成功!");
        window.parent.ScientificTreatise.table.refresh();
        ScientificTreatiseInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scientificTreatiseInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
ScientificTreatiseInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scientificTreatise/update", function(data){
        Feng.success("修改成功!");
        window.parent.ScientificTreatise.table.refresh();
        ScientificTreatiseInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scientificTreatiseInfoData);
    ajax.start();
};


/**
 * 审核
 */
ScientificTreatiseInfoDlg.auditSubmit = function(pass) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scientificTreatise/audit", function(data){
        Feng.success("提交成功!");
        window.parent.ActTodoTask.table.refresh();
        ScientificTreatiseInfoDlg.close();
    },function(data){
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
    this.scientificTreatiseInfoData['expand["pass"]'] = pass;
    this.scientificTreatiseInfoData['expand["comment"]'] = $("#comment").val();
    this.scientificTreatiseInfoData['expand["data"]'] = JSON.stringify(datas);
    this.scientificTreatiseInfoData['act.taskId'] = $("#taskId").val();
    this.scientificTreatiseInfoData['act.procInsId'] = $("#procInsId").val();
    this.scientificTreatiseInfoData['act.taskDefKey'] = $("#taskDefKey").val();
    this.scientificTreatiseInfoData['year'] = $("#year").val();
    ajax.set(this.scientificTreatiseInfoData);
    ajax.start();
};


/**
 * item获取新的id
 */
ScientificTreatiseInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "ScientificTreatiseItem" + this.count;
};

/**
 * 添加条目
 */
ScientificTreatiseInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#ScientificTreatiseItem").attr("id", this.newId());
};

/**
 * 删除item
 */
ScientificTreatiseInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};
/**
 * 提交添加
 */
ScientificTreatiseInfoDlg.addApply = function() {
    this.clearData();
    this.collectItems();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scientificTreatise/doAddApply", function(data){
        Feng.success("提交申请成功!");
        window.parent.ScientificTreatise.table.refresh();
        ScientificTreatiseInfoDlg.close();
    },function(data){
        Feng.error("提交申请失败!" + data.responseJSON.message + "!");
    });
    ajax.setContentType("application/json;");
    ajax.setData(JSON.stringify(this.data));
    ajax.start();
};

/**
 * 清除为空的item Dom
 */
ScientificTreatiseInfoDlg.clearNullDom = function(){
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
ScientificTreatiseInfoDlg.collectItems = function() {
    // this.clearNullDom();
    var that = this;
    this.data = [];
    $("[name='ScientificTreatiseItem']").each(function(){
        that.data.push($(this).serializeObject());
    });
};

var table;
var datas = [];

$(function() {
    Feng.initValidator("ScientificTreatiseForm", ScientificTreatiseInfoDlg.validateFields);
    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#editTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/scientificTreatise/scientificTreatiseProcData/'
            ,where: {procInsId: $("#procInsId").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'publishDate', title: '发表时间',edit:'text'}
                ,{field: 'title', title: '论文题目',edit:'text'}
                ,{field: 'periodicalName', title: '刊物名称',edit:'text'}
                ,{field: 'authorOrder', title: '作者顺序',edit:'text'}
                ,{field: 'periodicalLevel', title: '刊物级别',edit:'text'}
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
