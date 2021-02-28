/**
 * 初始化月度岗位责任奖详情对话框
 */
var JobPriceMonthInfoDlg = {
    jobPriceMonthInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
JobPriceMonthInfoDlg.clearData = function() {
    this.jobPriceMonthInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JobPriceMonthInfoDlg.set = function(key, val) {
    this.jobPriceMonthInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 提交导入
 */
JobPriceMonthInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobPriceMonth/act/apply", function(data){
        Feng.success("导入成功!");
        window.parent.JobPriceMonth.table.refresh();
        JobPriceMonthInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.jobPriceMonthInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.jobPriceMonthInfoData);
    ajax.start();
};
/**
 * 提交导入
 */
JobPriceMonthInfoDlg.importData = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobPriceMonth/importData", function(data){
        Feng.success("导入成功!");
        window.parent.JobPriceMonth.table.refresh();
        JobPriceMonthInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.jobPriceMonthInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.jobPriceMonthInfoData);
    ajax.start();
};


/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
JobPriceMonthInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
JobPriceMonthInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
JobPriceMonthInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('basePrice')
    .set('mgrPrice')
    .set('retroactivePrice')
    .set('garnishedPrice')
    .set('remark')
    .set('year')
    .set('deptId')
    .set('month')
    ;
};

/**
 * 验证数据是否为空
 */
JobPriceMonthInfoDlg.validate = function () {
    $('#JobPriceMonthForm').data("bootstrapValidator").resetForm();
    $('#JobPriceMonthForm').bootstrapValidator('validate');
    return $("#JobPriceMonthForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
JobPriceMonthInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobPriceMonth/add", function(data){
        Feng.success("添加成功!");
        window.parent.JobPriceMonth.table.refresh();
        JobPriceMonthInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobPriceMonthInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
JobPriceMonthInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobPriceMonth/update", function(data){
        Feng.success("修改成功!");
        window.parent.JobPriceMonth.table.refresh();
        JobPriceMonthInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobPriceMonthInfoData);
    ajax.start();
};


/**
 * 验收审核
 */
JobPriceMonthInfoDlg.auditSubmit = function(pass) {
    var that = this;
    this.collectData();
    if (!this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/jobPriceMonth/act/audit", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            JobPriceMonthInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.jobPriceMonthInfoData['expand["pass"]'] = pass;
        that.jobPriceMonthInfoData['expand["comment"]'] = $("#comment").val();
        that.jobPriceMonthInfoData['expand["data"]'] = JSON.stringify(datas);
        that.jobPriceMonthInfoData['act.taskId'] = $("#taskId").val();
        that.jobPriceMonthInfoData['act.procInsId'] = $("#procInsId").val();
        that.jobPriceMonthInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.jobPriceMonthInfoData['year'] = $("#year").val();
        that.jobPriceMonthInfoData['id'] = $("#id").val();
        ajax.set(that.jobPriceMonthInfoData);
        ajax.start();
    });
};


/**
 * item获取新的id
 */
JobPriceMonthInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "StuWorkItem" + this.count;
};

/**
 * 添加条目
 */
JobPriceMonthInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#StuWorkItem").attr("id", this.newId());
};

/**
 * 删除item
 */
JobPriceMonthInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

var table;
var datas = [];
$(function() {
    Feng.initValidator("JobPriceMonthForm", JobPriceMonthInfoDlg.validateFields);

    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#assessTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/jobPriceMonth/act/data'
            ,where: {procInsId: $("#procInsId").val(),id:$("#id").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'name', title: '职工姓名'}
                ,{field: 'account', title: '职工编号'}
                ,{title: '基本岗位责任奖', field:'basePrice'}
                ,{title: '管理服务工作奖', field:'mgrPrice'}
                ,{title: '补发', field:'retroactivePrice'}
                ,{title: '应发数', field:'shouldPrice'}
                ,{title: '扣发', field:'garnishedPrice'}
                ,{title: '实发数', field:'resultPrice'}
                ,{title: '备注', field:'remark'}
                ,{title: ' 月度', field:'month'}
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
