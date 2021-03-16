/**
 * 初始化考核分详情对话框
 */
var AssessNormPointInfoDlg = {
    assessNormPointInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
AssessNormPointInfoDlg.clearData = function() {
    this.assessNormPointInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AssessNormPointInfoDlg.set = function(key, val) {
    this.assessNormPointInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
AssessNormPointInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
AssessNormPointInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
AssessNormPointInfoDlg.collectData = function() {
    this
        .set('id')
        .set('userId')
        .set('year')
        .set('jxddMain')
        .set('jxddCollege')
        .set('zzpaMain')
        .set('zzpaColleage')
        .set('sdsfMain')
        .set('sdsfCollege')
        .set('zzjlMain')
        .set('zzjlColleage')
        .set('dfdjMain')
        .set('dfdjCollege')
        .set('ysxtMain')
        .set('ysxtCollege')
        .set('ldjlMain')
        .set('ldjlCollege')
        .set('deptId')
        .set('kygzMain')
        .set('kygzCollege')
        .set('zyjsMain')
        .set('zyjsCollege')
        .set('xsgzMain')
        .set('xsgzCollege')
        .set('glfwMain')
        .set('glfwCollege')
        .set('jxgzMain')
        .set('jxgzCollege')
        .set('dzbgzMain')
        .set('dzbgzCollege')
        .set('fdyszgzMain')
        .set('fdyszgzCollege')
        .set('szjsszgzMain')
        .set('szjsszgzCollege')
        .set('bzryMain')
        .set('bzryCollege')
        .set('fdyrcgzMain')
        .set('fdyrcgzCollege')
        .set('sxjxMain')
        .set('sxjxCollege')
        .set('rypzMain')
        .set('rypzCollege')
        .set('jshjMain')
        .set('jshjCollege')
        .set('zxgzMain')
        .set('zxgzCollege')
        .set('shpxgzMain')
        .set('shpxgzCollege')
        .set('jfwcqkMain')
        .set('jfwcqkCollege')
    ;
};

/**
 * 验证数据是否为空
 */
AssessNormPointInfoDlg.validate = function () {
    $('#AssessNormPointForm').data("bootstrapValidator").resetForm();
    $('#AssessNormPointForm').bootstrapValidator('validate');
    return $("#AssessNormPointForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
AssessNormPointInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/assessNormPoint/add", function(data){
        Feng.success("添加成功!");
        window.parent.AssessNormPoint.table.refresh();
        AssessNormPointInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.assessNormPointInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
AssessNormPointInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/assessNormPoint/update", function(data){
        Feng.success("修改成功!");
        window.parent.AssessNormPoint.table.refresh();
        AssessNormPointInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.assessNormPointInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("AssessNormPointForm", AssessNormPointInfoDlg.validateFields);

});
