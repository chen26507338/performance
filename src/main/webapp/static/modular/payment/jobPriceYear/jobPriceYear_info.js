/**
 * 初始化年度岗位责任奖详情对话框
 */
var JobPriceYearInfoDlg = {
    jobPriceYearInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
JobPriceYearInfoDlg.clearData = function() {
    this.jobPriceYearInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JobPriceYearInfoDlg.set = function(key, val) {
    this.jobPriceYearInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
JobPriceYearInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
JobPriceYearInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
JobPriceYearInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('month1')
    .set('month2')
    .set('month3')
    .set('month4')
    .set('month5')
    .set('month6')
    .set('month7')
    .set('month8')
    .set('month9')
    .set('month10')
    .set('month11')
    .set('month12')
    .set('status')
    ;
};

/**
 * 验证数据是否为空
 */
JobPriceYearInfoDlg.validate = function () {
    $('#JobPriceYearForm').data("bootstrapValidator").resetForm();
    $('#JobPriceYearForm').bootstrapValidator('validate');
    return $("#JobPriceYearForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
JobPriceYearInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobPriceYear/add", function(data){
        Feng.success("添加成功!");
        window.parent.JobPriceYear.table.refresh();
        JobPriceYearInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobPriceYearInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
JobPriceYearInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobPriceYear/update", function(data){
        Feng.success("修改成功!");
        window.parent.JobPriceYear.table.refresh();
        JobPriceYearInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobPriceYearInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("JobPriceYearForm", JobPriceYearInfoDlg.validateFields);

});
