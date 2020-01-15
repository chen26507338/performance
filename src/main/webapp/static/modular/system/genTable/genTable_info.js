/**
 * 初始化业务表详情对话框
 */
var GenTableInfoDlg = {
    ztreeInstance: null,
    genTableInfoData : {}
};

/**
 * 清除数据
 */
GenTableInfoDlg.clearData = function() {
    this.genTableInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
GenTableInfoDlg.set = function(key, val) {
    this.genTableInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 显示父级菜单选择的树
 */
GenTableInfoDlg.showMenuSelectTree = function () {
    Feng.showInputTree("parentMenuName", "pcodeTreeDiv", 15, 34);
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 */
GenTableInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
GenTableInfoDlg.close = function() {
    parent.layer.close(window.parent.GenTable.layerIndex);
};

/**
 * 收集数据
 */
GenTableInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('comments')
    .set('className')
    .set('parentTable')
    .set('parentTableFk')
    .set('author')
    .set('bizName')
    .set('moduleName')
    .set('parentMenuName')
    .set('remarks')
    ;
};

/**
 * 提交添加
 */
GenTableInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/genTable/add", function(data){
        Feng.success("添加成功!");
        window.parent.GenTable.table.refresh();
        GenTableInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.setFormData("genTableAddForm");
    ajax.start();
};

/**
 * 提交修改
 */
GenTableInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/genTable/update", function(data){
        Feng.success("修改成功!");
        window.parent.GenTable.table.refresh();
        GenTableInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.setFormData("genTableAddForm");
    ajax.start();
};

/**
 * 点击父级编号input框时
 */
    GenTableInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#parentMenuName").attr("value", GenTableInfoDlg.ztreeInstance.getSelectedVal());
};

$(function() {
    var ztree = new $ZTree("pcodeTree", "/menu/selectMenuTreeList");
    ztree.bindOnClick(GenTableInfoDlg.onClickDept);
    ztree.init();
    GenTableInfoDlg.ztreeInstance = ztree;
    $("#pcodeTree").css('width', $("#parentMenuName").css('width'));
});
