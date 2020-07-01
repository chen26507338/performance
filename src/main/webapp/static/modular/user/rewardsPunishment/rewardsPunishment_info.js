/**
 * 初始化考核奖惩详情对话框
 */
var RewardsPunishmentInfoDlg = {
    rewardsPunishmentInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
    }
};

/**
 * 清除数据
 */
RewardsPunishmentInfoDlg.clearData = function() {
    this.rewardsPunishmentInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RewardsPunishmentInfoDlg.set = function(key, val) {
    this.rewardsPunishmentInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
RewardsPunishmentInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
RewardsPunishmentInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
RewardsPunishmentInfoDlg.collectData = function() {
    this
    .set('id')
    .set('content')
    .set('time')
    .set('type')
    .set('name')
    .set('referenceNumber')
    .set('approvedBy')
    .set('procInsId')
    .set('status')
    .set('userId')
    ;
};

/**
 * 验证数据是否为空
 */
RewardsPunishmentInfoDlg.validate = function () {
    $('#RewardsPunishmentForm').data("bootstrapValidator").resetForm();
    $('#RewardsPunishmentForm').bootstrapValidator('validate');
    return $("#RewardsPunishmentForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
RewardsPunishmentInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rewardsPunishment/add", function(data){
        Feng.success("添加成功!");
        window.parent.RewardsPunishment.table.refresh();
        RewardsPunishmentInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.rewardsPunishmentInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
RewardsPunishmentInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rewardsPunishment/update", function(data){
        Feng.success("修改成功!");
        window.parent.RewardsPunishment.table.refresh();
        RewardsPunishmentInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.rewardsPunishmentInfoData);
    ajax.start();
};


/**
 * 审核
 */
RewardsPunishmentInfoDlg.auditSubmit = function(pass) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rewardsPunishment/audit", function(data){
        Feng.success("提交成功!");
        window.parent.ActTodoTask.table.refresh();
        RewardsPunishmentInfoDlg.close();
    },function(data){
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
    this.rewardsPunishmentInfoData['expand["pass"]'] = pass;
    this.rewardsPunishmentInfoData['expand["comment"]'] = $("#comment").val();
    this.rewardsPunishmentInfoData['expand["data"]'] = JSON.stringify(datas);
    this.rewardsPunishmentInfoData['act.taskId'] = $("#taskId").val();
    this.rewardsPunishmentInfoData['act.procInsId'] = $("#procInsId").val();
    this.rewardsPunishmentInfoData['act.taskDefKey'] = $("#taskDefKey").val();
    ajax.set(this.rewardsPunishmentInfoData);
    ajax.start();
};


/**
 * item获取新的id
 */
RewardsPunishmentInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "RewardsPunishmentItem" + this.count;
};

/**
 * 添加条目
 */
RewardsPunishmentInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#RewardsPunishmentItem").attr("id", this.newId());
};

/**
 * 删除item
 */
RewardsPunishmentInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};
/**
 * 提交添加
 */
RewardsPunishmentInfoDlg.addApply = function() {
    this.clearData();
    this.collectItems();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rewardsPunishment/doAddApply", function(data){
        Feng.success("提交申请成功!");
        window.parent.RewardsPunishment.table.refresh();
        RewardsPunishmentInfoDlg.close();
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
RewardsPunishmentInfoDlg.clearNullDom = function(){
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
RewardsPunishmentInfoDlg.collectItems = function() {
    // this.clearNullDom();
    var that = this;
    this.data = [];
    $("[name='RewardsPunishmentItem']").each(function(){
        that.data.push($(this).serializeObject());
    });
};

var table;
var datas = [];
$(function() {
    Feng.initValidator("RewardsPunishmentForm", RewardsPunishmentInfoDlg.validateFields);
    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#editTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/rewardsPunishment/rewardsPunishmentProcData/'
            ,where: {procInsId: $("#procInsId").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'content', title: '奖或惩',edit:'text'}
                ,{field: 'time', title: '时间',edit:'text'}
                ,{field: 'typeDict', title: '种类',edit:'text'}
                ,{field: 'name', title: '名称',edit:'text'}
                ,{field: 'referenceNumber', title: '批准依据（文号）',edit:'text'}
                ,{field: 'approvedBy', title: '批准单位',edit:'text'}
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
