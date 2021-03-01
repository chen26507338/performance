/**
 * 初始化教学考核详情对话框
 */
var TeachingLoadAssessInfoDlg = {
    teachingLoadAssessInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
TeachingLoadAssessInfoDlg.clearData = function() {
    this.teachingLoadAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeachingLoadAssessInfoDlg.set = function(key, val) {
    this.teachingLoadAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
TeachingLoadAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
TeachingLoadAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
TeachingLoadAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('procInsId')
    .set('status')
    .set('hrHandleId')
    .set('deanOfficeLeaderId')
    .set('normId')
    .set('result')
    .set('year')
    .set('deanUserId')
    .set('deptTeachingId')
    .set('deanOfficeCommissioner')
    .set('coePoint')
    .set('mainNormPoint')
    .set('collegeNormPoint')
    .set('userId')
    ;
};

/**
 * 验证数据是否为空
 */
TeachingLoadAssessInfoDlg.validate = function () {
    $('#TeachingLoadAssessForm').data("bootstrapValidator").resetForm();
    $('#TeachingLoadAssessForm').bootstrapValidator('validate');
    return $("#TeachingLoadAssessForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
TeachingLoadAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teachingLoadAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.TeachingLoadAssess.table.refresh();
        TeachingLoadAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teachingLoadAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
TeachingLoadAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teachingLoadAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.TeachingLoadAssess.table.refresh();
        TeachingLoadAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teachingLoadAssessInfoData);
    ajax.start();
};
/**
 * 提交导入
 */
TeachingLoadAssessInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teachingLoadAssess/importAssess", function(data){
        Feng.success("导入成功!");
        window.parent.TeachingLoadAssess.table.refresh();
        TeachingLoadAssessInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.teachingLoadAssessInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.teachingLoadAssessInfoData);
    ajax.start();
};


/**
 * 验收审核
 */
TeachingLoadAssessInfoDlg.auditSubmit = function(pass) {
    var that = this;
    this.collectData();
    if (!this.validate()) {
        return;
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/teachingLoadAssess/act/audit", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            TeachingLoadAssessInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.teachingLoadAssessInfoData['expand["pass"]'] = pass;
        that.teachingLoadAssessInfoData['expand["comment"]'] = $("#comment").val();
        that.teachingLoadAssessInfoData['expand["data"]'] = JSON.stringify(datas);
        that.teachingLoadAssessInfoData['act.taskId'] = $("#taskId").val();
        that.teachingLoadAssessInfoData['act.procInsId'] = $("#procInsId").val();
        that.teachingLoadAssessInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.teachingLoadAssessInfoData['year'] = $("#year").val();
        that.teachingLoadAssessInfoData['id'] = $("#id").val();
        ajax.set(that.teachingLoadAssessInfoData);
        ajax.start();
    });
};


/**
 * item获取新的id
 */
TeachingLoadAssessInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "StuWorkItem" + this.count;
};

/**
 * 添加条目
 */
TeachingLoadAssessInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#StuWorkItem").attr("id", this.newId());
};

/**
 * 删除item
 */
TeachingLoadAssessInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

var table;
var datas = [];

$(function() {
    Feng.initValidator("TeachingLoadAssessForm", TeachingLoadAssessInfoDlg.validateFields);

    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#assessTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/teachingLoadAssess/act/data'
            ,where: {procInsId: $("#procInsId").val(),id:$("#id").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'account', title: '职工编号'}
                ,{field: 'name', title: '职工姓名'}
                ,{field: 'result', title: '考核结果',edit:'text'}
                ,{field: 'coePoint', title: '考核系数'}
                ,{field: 'mainNormPoint', title: '校级指标分'}
                ,{field: 'collegeNormPoint', title: '院级指标分'}
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
