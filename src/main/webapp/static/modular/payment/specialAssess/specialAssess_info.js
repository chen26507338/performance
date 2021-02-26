/**
 * 初始化专项工作奖项目列表详情对话框
 */
var SpecialAssessInfoDlg = {
    specialAssessInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
SpecialAssessInfoDlg.clearData = function() {
    this.specialAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SpecialAssessInfoDlg.set = function(key, val) {
    this.specialAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
SpecialAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
SpecialAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
SpecialAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('deptId')
    .set('pointPercent')
    .set('applyPoint')
    .set('addPoint')
    .set('remark')
    .set('createTime')
    .set('isAllPoint')
    .set('status')
    .set('referencePoint')
    .set('isJr')
    .set('isYjkh')
    .set('projectContent')
    .set('zxNo')
    ;
};

/**
 * 验证数据是否为空
 */
SpecialAssessInfoDlg.validate = function () {
    $('#SpecialAssessForm').data("bootstrapValidator").resetForm();
    $('#SpecialAssessForm').bootstrapValidator('validate');
    return $("#SpecialAssessForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
SpecialAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/specialAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.SpecialAssess.table.refresh();
        SpecialAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.specialAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
SpecialAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/specialAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.SpecialAssess.table.refresh();
        SpecialAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.specialAssessInfoData);
    ajax.start();
};

/**
 * 提交导入
 */
SpecialAssessInfoDlg.importProject = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/specialAssess/importProject", function(data){
        Feng.success("导入成功!");
        window.parent.SpecialAssess.table.refresh();
        SpecialAssessInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.specialAssessInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.specialAssessInfoData);
    ajax.start();
};
/**
 * 提交导入
 */
SpecialAssessInfoDlg.importAssess = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/specialAssess/importAssess", function(data){
        Feng.success("导入成功!");
        window.parent.SpecialAssess.table.refresh();
        SpecialAssessInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.specialAssessInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.specialAssessInfoData);
    ajax.start();
};


$(function() {
    Feng.initValidator("SpecialAssessForm", SpecialAssessInfoDlg.validateFields);

});
