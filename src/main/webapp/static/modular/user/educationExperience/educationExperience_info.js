/**
 * 初始化学历培训详情对话框
 */
var EducationExperienceInfoDlg = {
    educationExperienceInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    data: [],		//拼接字符串内容
    validateFields:{
    }
};

/**
 * 清除数据
 */
EducationExperienceInfoDlg.clearData = function() {
    this.educationExperienceInfoData = {};
};

/**
 * item获取新的id
 */
EducationExperienceInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "educationExperienceItem" + this.count;
};

/**
 * 添加条目
 */
EducationExperienceInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#educationExperienceItem").attr("id", this.newId());
};

/**
 * 删除item
 */
EducationExperienceInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EducationExperienceInfoDlg.set = function(key, val) {
    this.educationExperienceInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
EducationExperienceInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
EducationExperienceInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 清除为空的item Dom
 */
EducationExperienceInfoDlg.clearNullDom = function(){
    $("[name='educationExperienceItem']").each(function(){
        var school = $(this).find("[name='school']").val();
        var major = $(this).find("[name='major']").val();
        var educationBackground = $(this).find("[name='educationBackground']").val();
        if(school == '' || major == ''|| educationBackground == ''){
            $(this).remove();
        }
    });
};

/**
 * 收集数据
 */
EducationExperienceInfoDlg.collectData = function() {
    this
    .set('id')
    .set('enrollmentTime')
    .set('graduateTime')
    .set('school')
    .set('major')
    .set('educationBackground')
    .set('degree')
    .set('learnStyle')
    .set('userId')
    ;
};

/**
 * 收集数据
 */
EducationExperienceInfoDlg.collectItems = function() {
    this.clearNullDom();
    var that = this;
    this.data = [];
    $("[name='educationExperienceItem']").each(function(){
        var data = {};
        var school = $(this).find("[name='school']").val();
        var major = $(this).find("[name='major']").val();
        var educationBackground = $(this).find("[name='educationBackground']").val();
        var enrollmentTime = $(this).find("[name='enrollmentTime']").val();
        var graduateTime = $(this).find("[name='graduateTime']").val();
        var degree = $(this).find("[name='degree']").val();
        var learnStyle = $(this).find("[name='learnStyle']").val();
        data['school'] = school;
        data['major'] = major;
        data['educationBackground'] = educationBackground;
        data['enrollmentTime'] = enrollmentTime;
        data['graduateTime'] = graduateTime;
        data['degree'] = degree;
        data['learnStyle'] = learnStyle;
        that.data.push(data);
    });
};

/**
 * 验证数据是否为空
 */
EducationExperienceInfoDlg.validate = function () {
    $('#EducationExperienceForm').data("bootstrapValidator").resetForm();
    $('#EducationExperienceForm').bootstrapValidator('validate');
    return $("#EducationExperienceForm").data('bootstrapValidator').isValid();
};
/**
 * 提交添加
 */
EducationExperienceInfoDlg.addApply = function() {
    this.clearData();
    this.collectItems();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/educationExperience/doAddApply", function(data){
        Feng.success("提交申请成功!");
        window.parent.EducationExperience.table.refresh();
        EducationExperienceInfoDlg.close();
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
EducationExperienceInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/educationExperience/add", function(data){
        Feng.success("添加成功!");
        window.parent.EducationExperience.table.refresh();
        EducationExperienceInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.educationExperienceInfoData);
    ajax.start();
};

/**
 * 审核
 */
EducationExperienceInfoDlg.auditSubmit = function(pass) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/educationExperience/audit", function(data){
        Feng.success("提交成功!");
        window.parent.ActTodoTask.table.refresh();
        EducationExperienceInfoDlg.close();
    },function(data){
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
    this.educationExperienceInfoData['expand["pass"]'] = pass;
    this.educationExperienceInfoData['expand["comment"]'] = $("#comment").val();
    this.educationExperienceInfoData['expand["data"]'] = JSON.stringify(datas);
    this.educationExperienceInfoData['act.taskId'] = $("#taskId").val();
    this.educationExperienceInfoData['act.procInsId'] = $("#procInsId").val();
    this.educationExperienceInfoData['act.taskDefKey'] = $("#taskDefKey").val();
    ajax.set(this.educationExperienceInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
EducationExperienceInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/educationExperience/update", function(data){
        Feng.success("修改成功!");
        window.parent.EducationExperience.table.refresh();
        EducationExperienceInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.educationExperienceInfoData);
    ajax.start();
};

var table;
var datas = [];
$(function() {
    Feng.initValidator("EducationExperienceForm", EducationExperienceInfoDlg.validateFields);

    layui.use('table', function(){
        table = layui.table;

        //执行渲染
        table.render({
            elem: '#educationExperienceTable' //指定原始表格元素选择器（推荐id选择器）
            ,height: 315 //容器高度
            ,url: Feng.ctxPath+'/educationExperience/educationExperienceProcData/'
            ,where: {procInsId: $("#procInsId").val()}
            ,cols: [[ //表头
                {field: 'id', title: 'ID',hide:true, fixed: 'left'}
                ,{field: 'enrollmentTime', title: '入学时间',edit:'text'}
                ,{field: 'graduateTime', title: '毕业时间',edit:'text'}
                ,{field: 'school', title: '毕业学校',edit:'text'}
                ,{field: 'major', title: '所学专业',edit:'text'}
                ,{field: 'educationBackgroundDict', title: '学历',edit:'text'}
                ,{field: 'degreeDict', title: '学位',edit:'text'}
                ,{field: 'learnStyleDict', title: '学习方式',edit:'text'}
            ]] //设置表头
        });

        table.on('edit(eduExperience)', function(obj){ //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
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
