/**
 * 初始化
 */
var Code = {
    ztreeInstance: null,
    genTable: {id: tableId},
    submitData: {},
    switchs: {}
};

/**
 * 选择table的事件
 */
Code.selectTable = function (tableName) {

    SelectList.clearSelect("templateList");
    Code.switchs = {};

    if (SelectList.singelSelect("tableList", "tableName", tableName) == true) {
        Code.tableName = tableName;
        Code.setTableName(tableName);
    } else {
        Code.tableName = "";
    }
};

/**
 * 选择模板的事件
 */
Code.selectTemplate = function (templateKey) {
    if (SelectList.mutiSelect("templateList", "key", templateKey) == true) {
        Code.switchs[templateKey] = true;
    } else {
        Code.switchs[templateKey] = false;
    }
};

/**
 * 点击生成
 */
Code.generate = function () {
    Code.submitData = {};
    Code.submitData['genTable.id'] = Code.genTable.id;
    var i = 0;
    for (var item in Code.switchs) {
        Code.submitData[item] = Code.switchs[item];
        if(Code.switchs[item] == true) {
            Code.submitData[item] = Code.switchs[item];
            i++;
        }
    }

    if (i === 0) {
        Feng.info("请选择模板");
        return
    }
    this.set('tableName').set('projectPath').set('author').set('projectPackage').set('corePackage').set('ignoreTabelPrefix').set('bizName').set('moduleName').set('parentMenuName');
    var baseAjax = Feng.baseAjax("/code/generate", "生成代码");


    baseAjax.setData(Code.submitData);
    baseAjax.start();
};

Code.preview = function () {
    Code.submitData = {};
    var i = 0;
    for (var item in Code.switchs) {
        Code.submitData[item] = Code.switchs[item];
        if(Code.switchs[item] == true) {
            Code.submitData[item] = Code.switchs[item];
            i++;
        }
    }

    if (i === 0) {
        Feng.info("请选择模板");
        return
    }

    if (i > 1) {
        Feng.info("只能选择一个模板预览");
        return
    }

    this.set('tableName').set('projectPath').set('author').set('projectPackage').set('corePackage').set('ignoreTabelPrefix').set('bizName').set('moduleName').set('parentMenuName');
    var paramStr = "";
    for (var item in Code.submitData) {
        paramStr += item + "=" + Code.submitData[item] + "&";
    }

    paramStr += "genTable.id=" + Code.genTable.id;

    var index = layer.open({
        type: 2,
        title: '预览代码',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/code/preview?' + paramStr
    });
    layer.full(index);

};

/**
 * 设置表名称
 */
Code.setTableName = function (tableName) {
    var preSize = $("#ignoreTabelPrefix").val().length;
    $("#tableName").val(tableName);
    $("#className").val(Feng.underLineToCamel(tableName.substring(preSize)));
};

/**
 * 点击父级编号input框时
 */
Code.onClickDept = function (e, treeId, treeNode) {
    $("#parentMenuName").attr("value", Code.ztreeInstance.getSelectedVal());
};

/**
 * 显示父级菜单选择的树
 */
Code.showMenuSelectTree = function () {
    Feng.showInputTree("parentMenuName", "pcodeTreeDiv", 15, 34);
};

$(function () {
    var ztree = new $ZTree("pcodeTree", "/menu/selectMenuTreeList");
    ztree.bindOnClick(Code.onClickDept);
    ztree.init();
    Code.ztreeInstance = ztree;
    $("#pcodeTree").css('width', $("#parentMenuName").css('width'));

    layui.use('layer', function(){
        layer = layui.layer;
    });
});

Code.set = function (key, val) {
    Code.submitData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};