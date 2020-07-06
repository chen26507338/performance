/**
 * 初始化亲属关系详情对话框
 */
var KinshipInfoDlg = {
    kinshipInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
    }
};

/**
 * 清除数据
 */
KinshipInfoDlg.clearData = function() {
    this.kinshipInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
KinshipInfoDlg.set = function(key, val) {
    this.kinshipInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
KinshipInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
KinshipInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
KinshipInfoDlg.collectData = function() {
    this
    .set('id')
    .set('relationship')
    .set('name')
    .set('birthday')
    .set('age')
    .set('politicsStatus')
    .set('company')
    .set('procInsId')
    .set('status')
    .set('userId')
    ;
};

/**
 * 验证数据是否为空
 */
KinshipInfoDlg.validate = function () {
    $('#KinshipForm').data("bootstrapValidator").resetForm();
    $('#KinshipForm').bootstrapValidator('validate');
    return $("#KinshipForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
KinshipInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/kinship/add", function(data){
        Feng.success("添加成功!");
        window.parent.Kinship.table.refresh();
        KinshipInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.kinshipInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
KinshipInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/kinship/update", function(data){
        Feng.success("修改成功!");
        window.parent.Kinship.table.refresh();
        KinshipInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.kinshipInfoData);
    ajax.start();
};

/**
 * 审核
 */
KinshipInfoDlg.auditSubmit = function(pass) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/kinship/audit", function(data){
        Feng.success("提交成功!");
        window.parent.ActTodoTask.table.refresh();
        KinshipInfoDlg.close();
    },function(data){
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
    this.kinshipInfoData['expand["pass"]'] = pass;
    this.kinshipInfoData['expand["comment"]'] = $("#comment").val();
    this.kinshipInfoData['expand["data"]'] = JSON.stringify(datas);
    this.kinshipInfoData['act.taskId'] = $("#taskId").val();
    this.kinshipInfoData['act.procInsId'] = $("#procInsId").val();
    this.kinshipInfoData['act.taskDefKey'] = $("#taskDefKey").val();
    ajax.set(this.kinshipInfoData);
    ajax.start();
};


/**
 * item获取新的id
 */
KinshipInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "KinshipItem" + this.count;
};

/**
 * 添加条目
 */
KinshipInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#KinshipItem").attr("id", this.newId());
};

/**
 * 删除item
 */
KinshipInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

/**
 * 清除为空的item Dom
 */
KinshipInfoDlg.clearNullDom = function(){
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
KinshipInfoDlg.collectItems = function() {
    // this.clearNullDom();
    var that = this;
    this.data = [];
    $("[name='KinshipItem']").each(function(){
        that.data.push($(this).serializeObject());
    });
};
/**
 * 提交添加
 */
KinshipInfoDlg.addApply = function() {
    this.clearData();
    this.collectItems();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/kinship/doAddApply", function(data){
        Feng.success("提交申请成功!");
        window.parent.Kinship.table.refresh();
        KinshipInfoDlg.close();
    },function(data){
        Feng.error("提交申请失败!" + data.responseJSON.message + "!");
    });
    ajax.setContentType("application/json;");
    ajax.setData(JSON.stringify(this.data));
    ajax.start();
};

var table;
var datas = [];
$(function() {
    Feng.initValidator("KinshipForm", KinshipInfoDlg.validateFields);
    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#editTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/kinship/kinshipProcData/'
            ,where: {procInsId: $("#procInsId").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'relationshipDict', title: '关系',edit:'text'}
                ,{field: 'name', title: '姓名',edit:'text'}
                ,{field: 'birthday', title: '出生年月',edit:'text'}
                ,{field: 'age', title: '年龄',edit:'text'}
                ,{field: 'politicsStatusDict', title: '政治面貌',edit:'text'}
                ,{field: 'company', title: '工作单位及职务',edit:'text'}
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
