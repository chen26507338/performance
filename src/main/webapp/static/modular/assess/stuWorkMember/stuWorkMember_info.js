/**
 * 初始化学生工作成员详情对话框
 */
var StuWorkMemberInfoDlg = {
    stuWorkMemberInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
StuWorkMemberInfoDlg.clearData = function() {
    this.stuWorkMemberInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StuWorkMemberInfoDlg.set = function(key, val) {
    this.stuWorkMemberInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
StuWorkMemberInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
StuWorkMemberInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
StuWorkMemberInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('mainNormPoint')
    .set('collegeNormPoint')
    .set('status')
    .set('coePoint')
    .set('year')
    .set('sWorkId')
    ;
};

/**
 * 验证数据是否为空
 */
StuWorkMemberInfoDlg.validate = function () {
    $('#StuWorkMemberForm').data("bootstrapValidator").resetForm();
    $('#StuWorkMemberForm').bootstrapValidator('validate');
    return $("#StuWorkMemberForm").data('bootstrapValidator').isValid();
};

/**
 * 提交导入
 */
StuWorkMemberInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/stuWorkMember/importAssess", function(data){
        Feng.success("导入成功!");
        window.parent.StuWorkMember.table.refresh();
        StuWorkMemberInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.stuWorkMemberInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.stuWorkMemberInfoData)
    ;
    ajax.start();
};


/**
 * 提交添加
 */
StuWorkMemberInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/stuWorkMember/add", function(data){
        Feng.success("添加成功!");
        window.parent.StuWorkMember.table.refresh();
        StuWorkMemberInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.stuWorkMemberInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
StuWorkMemberInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/stuWorkMember/update", function(data){
        Feng.success("修改成功!");
        window.parent.StuWorkMember.table.refresh();
        StuWorkMemberInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.stuWorkMemberInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("StuWorkMemberForm", StuWorkMemberInfoDlg.validateFields);

});
