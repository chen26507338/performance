/**
 * 系统管理--用户管理的单例对象
 */
var ActTaskHistoric = {
    id: "actTaskHistoricTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid:0
};

/**
 * 初始化表格的列
 */
ActTaskHistoric.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'taskId', visible: false, align: 'center', valign: 'middle'},
        {title: '标题', field: 'process.key', align: 'center', valign: 'middle', sortable: false,formatter: function (value, row, index) {
                return row.vars.map.title ? row.vars.map.title : row.id;
            }},
        {title: '流程名称', field: 'expand.proDef.name', align: 'center', valign: 'middle', sortable: false},
        {title: '版本号', field: 'expand.proDef.version', align: 'center', valign: 'middle', sortable: false},
        {title: '结束时间', field: 'taskEndDate', align: 'center', valign: 'middle', sortable: false}
        ]
};

/**
 * 检查是否选中
 */
ActTaskHistoric.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ActTaskHistoric.seItem = selected[0];
        return true;
    }
};

/**
 * 任务办理
 */
ActTaskHistoric.handle = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '任务办理 ' + this.seItem.taskName,
            area: ['800px', '560px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/act/task/form?taskId=' + this.seItem.taskId + "&procInsId=" + this.seItem.procInsId
                + "&taskDefKey=" + this.seItem.taskDefKey+"&procDefId="+this.seItem.procDefId
        });
        layer.full(this.layerIndex);
    }
};

/**
 * 跟踪
 */
ActTaskHistoric.track = function () {
    if (this.check()) {
        window.open(Feng.ctxPath + "/static/act/diagram-viewer/index.html?processDefinitionId="+this.seItem.procDefId+"&processInstanceId=" + this.seItem.procInsId);
    }
};

/**
 * 导出
 * @param
 */
ActTaskHistoric.export = function () {
    if (this.check()) {
        window.open(Feng.ctxPath + "/act/model/export?id=" + this.seItem.id);
    }
};

/**
 * 删除
 */
ActTaskHistoric.delActTaskHistoric = function () {
    if (this.check()) {

        var operation = function(){
            var modelId = ActTaskHistoric.seItem.process.deploymentId;
            var ajax = new $ax(Feng.ctxPath + "/act/process/delete", function () {
                Feng.success("删除成功!");
                ActTaskHistoric.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("deploymentId", modelId);
            ajax.start();
        };

        Feng.confirm("是否删除模型" + ActTaskHistoric.seItem.process.deploymentId + "?",operation);
    }
};

/**
 * 激活/挂起
 */
ActTaskHistoric.pending = function () {
    if (this.check()) {
        var state;
        var message;
        if (ActTaskHistoric.seItem.process.suspensionState == 1) {
            state = "suspend";
            message = "挂起";
        } else {
            state = "active";
            message = "激活";
        }
        var operation = function(){

            var modelId = ActTaskHistoric.seItem.process.id;
            var ajax = new $ax(Feng.ctxPath + "/act/process/update/" + state, function () {
                Feng.success("删除成功!");
                ActTaskHistoric.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("procDefId", modelId);
            ajax.start();
        };

        Feng.confirm("是否" + message + "模型" + ActTaskHistoric.seItem.process.id + "?", operation);
    }
};

/**
 * 部署
 */
ActTaskHistoric.deploy = function () {
    if (this.check()) {
        var operation = function(){
            var modelId = ActTaskHistoric.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/act/model/deploy", function () {
                Feng.success("部署成功!");
                ActTaskHistoric.table.refresh();
            }, function (data) {
                Feng.error("部署失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", modelId);
            ajax.start();
        };

        Feng.confirm("是否部署模型" + ActTaskHistoric.seItem.name + "?",operation);
    }
};

ActTaskHistoric.resetSearch = function () {
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");

    ActTaskHistoric.search();
};

ActTaskHistoric.search = function () {
    var queryData = {};

    // queryData['deptid'] = ActTaskHistoric.deptid;
    queryData['name'] = $("#name").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();

    ActTaskHistoric.table.refresh({query: queryData});
};

ActTaskHistoric.onClickDept = function (e, treeId, treeNode) {
    ActTaskHistoric.deptid = treeNode.id;
    ActTaskHistoric.search();
};

$(function () {
    var defaultColunms = ActTaskHistoric.initColumn();
    var table = new BSTable("actTaskHistoricTable", "/act/task/historicList", defaultColunms);
    // table.setPaginationType("client");
    ActTaskHistoric.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
});
