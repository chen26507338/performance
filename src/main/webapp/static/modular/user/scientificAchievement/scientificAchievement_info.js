/**
 * 初始化科研成果详情对话框
 */
var ScientificAchievementInfoDlg = {
    scientificAchievementInfoData : {},
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
ScientificAchievementInfoDlg.clearData = function() {
    this.scientificAchievementInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScientificAchievementInfoDlg.set = function(key, val) {
    this.scientificAchievementInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
ScientificAchievementInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ScientificAchievementInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
ScientificAchievementInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('des')
    .set('time')
    .set('type')
    .set('userId')
    .set('procInsId')
    .set('status')
    .set('hrHandleId')
    .set('deptLeaderId')
    .set('hrLeaderId')
    .set('sciCommissioner')
    .set('normId')
    .set('coePoint')
    .set('mainNormPoint')
    .set('collegeNormPoint')
    .set('result')
    .set('year')
    .set('sciLeaderId')
    ;
};

/**
 * 验证数据是否为空
 */
ScientificAchievementInfoDlg.validate = function () {
    $('#ScientificAchievementForm').data("bootstrapValidator").resetForm();
    $('#ScientificAchievementForm').bootstrapValidator('validate');
    return $("#ScientificAchievementForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
ScientificAchievementInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scientificAchievement/add", function(data){
        Feng.success("添加成功!");
        window.parent.ScientificAchievement.table.refresh();
        ScientificAchievementInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scientificAchievementInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
ScientificAchievementInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scientificAchievement/update", function(data){
        Feng.success("修改成功!");
        window.parent.ScientificAchievement.table.refresh();
        ScientificAchievementInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scientificAchievementInfoData);
    ajax.start();
};


/**
 * 审核
 */
ScientificAchievementInfoDlg.auditSubmit = function(pass) {
    var that = this;
    if (!this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/scientificAchievement/audit", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            ScientificAchievementInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.scientificAchievementInfoData['expand["pass"]'] = pass;
        that.scientificAchievementInfoData['expand["comment"]'] = $("#comment").val();
        that.scientificAchievementInfoData['expand["data"]'] = JSON.stringify(datas);
        that.scientificAchievementInfoData['act.taskId'] = $("#taskId").val();
        that.scientificAchievementInfoData['act.procInsId'] = $("#procInsId").val();
        that.scientificAchievementInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.scientificAchievementInfoData['year'] = $("#year").val();
        ajax.set(that.scientificAchievementInfoData);
        ajax.start();
    });
};


/**
 * item获取新的id
 */
ScientificAchievementInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "ScientificAchievementItem" + this.count;
};

/**
 * 添加条目
 */
ScientificAchievementInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#ScientificAchievementItem").attr("id", this.newId());
};

/**
 * 删除item
 */
ScientificAchievementInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};
/**
 * 提交添加
 */
ScientificAchievementInfoDlg.addApply = function() {
    this.clearData();
    this.collectItems();

    var that = this;
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/scientificAchievement/doAddApply", function(data){
            Feng.success("提交申请成功!");
            window.parent.ScientificAchievement.table.refresh();
            ScientificAchievementInfoDlg.close();
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
ScientificAchievementInfoDlg.clearNullDom = function(){
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
ScientificAchievementInfoDlg.collectItems = function() {
    // this.clearNullDom();
    var that = this;
    this.data = [];
    $("[name='ScientificAchievementItem']").each(function(){
        that.data.push($(this).serializeObject());
    });
};

var table;
var datas = [];

$(function() {
    Feng.initValidator("ScientificAchievementForm", ScientificAchievementInfoDlg.validateFields);
    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#editTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/scientificAchievement/scientificAchievementProcData/'
            ,where: {procInsId: $("#procInsId").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'name', title: '名称',edit:'text'}
                ,{field: 'des', title: '简要描述',edit:'text'}
                ,{field: 'type', title: '课题项目类别',edit:'text'}
                ,{field: 'time', title: '时间',edit:'text'}
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
