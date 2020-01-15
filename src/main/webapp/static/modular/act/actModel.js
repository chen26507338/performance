/**
 * 系统管理--用户管理的单例对象
 */
var ActModel = {
    id: "actModelTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid:0
};

/**
 * 初始化表格的列
 */
ActModel.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '标识', field: 'key', align: 'center', valign: 'middle', sortable: true},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '版本号', field: 'version', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
        {title: '更新时间', field: 'lastUpdateTime', align: 'center', valign: 'middle', sortable: true}
        ]
};

/**
 * 检查是否选中
 */
ActModel.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ActModel.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理员
 */
ActModel.openAddActModel = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加模型',
        area: ['800px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/act/model/add'
    });
};

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
ActModel.openChangeModel = function () {
    if (this.check()) {
        window.open(Feng.ctxPath + "/static/act/process-editor/modeler.html?modelId=" + this.seItem.id);
    }
};

/**
 * 导出
 * @param
 */
ActModel.export = function () {
    if (this.check()) {
        window.open(Feng.ctxPath + "/act/model/export?id=" + this.seItem.id);
    }
};

/**
 * 删除
 */
ActModel.delActModel = function () {
    if (this.check()) {

        var operation = function(){
            var modelId = ActModel.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/act/model/delete", function () {
                Feng.success("删除成功!");
                ActModel.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", modelId);
            ajax.start();
        };

        Feng.confirm("是否删除模型" + ActModel.seItem.name + "?",operation);
    }
};

/**
 * 部署
 */
ActModel.deploy = function () {
    if (this.check()) {
        var operation = function(){
            var modelId = ActModel.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/act/model/deploy", function () {
                Feng.success("部署成功!");
                ActModel.table.refresh();
            }, function (data) {
                Feng.error("部署失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", modelId);
            ajax.start();
        };

        Feng.confirm("是否部署模型" + ActModel.seItem.name + "?",operation);
    }
};

ActModel.resetSearch = function () {
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");

    ActModel.search();
};

ActModel.search = function () {
    var queryData = {};

    queryData['deptid'] = ActModel.deptid;
    queryData['name'] = $("#name").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();

    ActModel.table.refresh({query: queryData});
};

ActModel.onClickDept = function (e, treeId, treeNode) {
    ActModel.deptid = treeNode.id;
    ActModel.search();
};

$(function () {
    var defaultColunms = ActModel.initColumn();
    var table = new BSTable("actModelTable", "/act/model/list", defaultColunms);
    table.setPaginationType("server");
    ActModel.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
});
