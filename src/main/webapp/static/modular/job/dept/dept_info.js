/**
 * 初始化部门管理详情对话框
 */
var DeptInfoDlg = {
    deptInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
DeptInfoDlg.clearData = function() {
    this.deptInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptInfoDlg.set = function(key, val) {
    this.deptInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
DeptInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
DeptInfoDlg.close = function() {
    parent.layer.close(window.parent.Dept.layerIndex);
};

/**
 * 收集数据
 */
DeptInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('des')
    .set('status')
    ;
};

/**
 * 验证数据是否为空
 */
DeptInfoDlg.validate = function () {
    $('#DeptForm').data("bootstrapValidator").resetForm();
    $('#DeptForm').bootstrapValidator('validate');
    return $("#DeptForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
DeptInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dept/add", function(data){
        Feng.success("添加成功!");
        window.parent.Dept.table.refresh();
        DeptInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
DeptInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dept/update", function(data){
        Feng.success("修改成功!");
        window.parent.Dept.table.refresh();
        DeptInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("DeptForm", DeptInfoDlg.validateFields);

});
