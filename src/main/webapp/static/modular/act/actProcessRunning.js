/**
 * 系统管理--用户管理的单例对象
 */
var ActProcessRunning = {
    id: "actProcessRunningTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid:0
};

/**
 * 初始化表格的列
 */
ActProcessRunning.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '流程实例ID', field: 'processInstanceId', align: 'center', valign: 'middle', sortable: false},
        {title: '流程定义ID', field: 'processDefinitionId', align: 'center', valign: 'middle', sortable: false},
        {title: '当前环节', field: 'activityId', align: 'center', valign: 'middle', sortable: false},
        {title: '是否挂起', field: 'suspended', align: 'center', valign: 'middle', sortable: false}
        ]
};

/**
 * 检查是否选中
 */
ActProcessRunning.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ActProcessRunning.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理员
 */
ActProcessRunning.openAddActProcessRunning = function () {
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
ActProcessRunning.openChangeModel = function () {
    if (this.check()) {
        window.open(Feng.ctxPath + "/static/act/process-editor/modeler.html?modelId=" + this.seItem.id);
    }
};

/**
 * 导出
 * @param
 */
ActProcessRunning.export = function () {
    if (this.check()) {
        window.open(Feng.ctxPath + "/act/model/export?id=" + this.seItem.id);
    }
};

/**
 * 删除
 */
ActProcessRunning.delActProcessRunning = function () {
    if (this.check()) {

        var operation = function(){
            var modelId = ActProcessRunning.seItem.processInstanceId;
            var ajax = new $ax(Feng.ctxPath + "/act/process/deleteProcIns", function () {
                Feng.success("删除成功!");
                ActProcessRunning.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("procInsId", modelId);
            ajax.start();
        };

        Feng.confirm("是否删除流程实例" + ActProcessRunning.seItem.processInstanceId + "?",operation);
    }
};

/**
 * 激活/挂起
 */
ActProcessRunning.pending = function () {
    if (this.check()) {
        var state;
        var message;
        if (ActProcessRunning.seItem.process.suspensionState == 1) {
            state = "suspend";
            message = "挂起";
        } else {
            state = "active";
            message = "激活";
        }
        var operation = function(){

            var modelId = ActProcessRunning.seItem.process.id;
            var ajax = new $ax(Feng.ctxPath + "/act/process/update/" + state, function () {
                Feng.success("删除成功!");
                ActProcessRunning.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("procDefId", modelId);
            ajax.start();
        };

        Feng.confirm("是否" + message + "模型" + ActProcessRunning.seItem.process.id + "?", operation);
    }
};

/**
 * 部署
 */
ActProcessRunning.deploy = function () {
    if (this.check()) {
        var operation = function(){
            var modelId = ActProcessRunning.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/act/model/deploy", function () {
                Feng.success("部署成功!");
                ActProcessRunning.table.refresh();
            }, function (data) {
                Feng.error("部署失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", modelId);
            ajax.start();
        };

        Feng.confirm("是否部署模型" + ActProcessRunning.seItem.name + "?",operation);
    }
};

ActProcessRunning.resetSearch = function () {
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");

    ActProcessRunning.search();
};

ActProcessRunning.search = function () {
    var queryData = {};

    queryData['deptid'] = ActProcessRunning.deptid;
    queryData['name'] = $("#name").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();

    ActProcessRunning.table.refresh({query: queryData});
};

ActProcessRunning.onClickDept = function (e, treeId, treeNode) {
    ActProcessRunning.deptid = treeNode.id;
    ActProcessRunning.search();
};

$(function () {
    var defaultColunms = ActProcessRunning.initColumn();
    var table = new BSTable("actProcessRunningTable", "/act/process/running/list", defaultColunms);
    table.setPaginationType("server");
    ActProcessRunning.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
});
