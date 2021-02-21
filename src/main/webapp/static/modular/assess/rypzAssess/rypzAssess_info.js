/**
 * 初始化人员配置考核详情对话框
 */
var RypzAssessInfoDlg = {
    rypzAssessInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
RypzAssessInfoDlg.clearData = function() {
    this.rypzAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RypzAssessInfoDlg.set = function(key, val) {
    this.rypzAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
RypzAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
RypzAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
RypzAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('assessName')
    .set('mainPoint')
    .set('year')
    ;
};

/**
 * 验证数据是否为空
 */
RypzAssessInfoDlg.validate = function () {
    $('#RypzAssessForm').data("bootstrapValidator").resetForm();
    $('#RypzAssessForm').bootstrapValidator('validate');
    return $("#RypzAssessForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
RypzAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rypzAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.RypzAssess.table.refresh();
        RypzAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.rypzAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
RypzAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rypzAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.RypzAssess.table.refresh();
        RypzAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.rypzAssessInfoData);
    ajax.start();
};


/**
 * 提交导入
 */
RypzAssessInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rypzAssess/importAssess", function(data){
        Feng.success("导入成功!");
        window.parent.RypzAssess.table.refresh();
        RypzAssessInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.rypzAssessInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.rypzAssessInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("RypzAssessForm", RypzAssessInfoDlg.validateFields);

});
