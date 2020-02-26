/**
 * 初始化考核系数详情对话框
 */
var AssessCoefficientInfoDlg = {
    assessCoefficientInfoData : {},
    validateFields:{
        type: {
            validators: {
                notEmpty: {
                    message: '类型不能为空'
                }
            }
        },
    }
};

/**
 * 清除数据
 */
AssessCoefficientInfoDlg.clearData = function() {
    this.assessCoefficientInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AssessCoefficientInfoDlg.set = function(key, val) {
    this.assessCoefficientInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
AssessCoefficientInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
AssessCoefficientInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
AssessCoefficientInfoDlg.collectData = function() {
    this
    .set('type')
    .set('name')
    .set('coefficient')
    ;
};

/**
 * 验证数据是否为空
 */
AssessCoefficientInfoDlg.validate = function () {
    $('#AssessCoefficientForm').data("bootstrapValidator").resetForm();
    $('#AssessCoefficientForm').bootstrapValidator('validate');
    return $("#AssessCoefficientForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
AssessCoefficientInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/assessCoefficient/add", function(data){
        Feng.success("添加成功!");
        window.parent.AssessCoefficient.table.refresh();
        AssessCoefficientInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.assessCoefficientInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
AssessCoefficientInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/assessCoefficient/update", function(data){
        Feng.success("修改成功!");
        window.parent.AssessCoefficient.table.refresh();
        AssessCoefficientInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.assessCoefficientInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("AssessCoefficientForm", AssessCoefficientInfoDlg.validateFields);

});
