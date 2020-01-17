/**
 * 系统管理--用户管理的单例对象
 */
var ActProcess = {
    id: "actProcessTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid:0
};

/**
 * 初始化表格的列
 */
ActProcess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'process.id', visible: true, align: 'center', valign: 'middle'},
        {title: '标识', field: 'process.key', align: 'center', valign: 'middle', sortable: false},
        {title: '名称', field: 'process.name', align: 'center', valign: 'middle', sortable: false},
        {title: '版本号', field: 'process.version', align: 'center', valign: 'middle', sortable: false},
        {title: '部署时间', field: 'deployment.deploymentTime', align: 'center', valign: 'middle', sortable: false}
        ,{title: '流程XML', field: 'process.resourceName', align: 'center', valign: 'middle', sortable: false,formatter: function (value, row, index) {
                return '<a target="_blank" href="'+Feng.ctxPath+'/act/process/resource/read?procDefId='+row.process.id+'&resType=xml">' + value + '</a>';
            }}
        ,{title: '流程图片', field: 'process.diagramResourceName', align: 'center', valign: 'middle', sortable: false,formatter: function (value, row, index) {
                return '<a target="_blank" href="'+Feng.ctxPath+'/act/process/resource/read?procDefId='+row.process.id+'&resType=image">' + value + '</a>';
            }}
        ,{title: '状态', field: 'process.suspensionState', align: 'center', valign: 'middle', sortable: false,formatter: function (value, row, index) {
                return value == 1 ? '激活' : '挂起';
            }}
        ]
};

/**
 * 检查是否选中
 */
ActProcess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ActProcess.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理员
 */
ActProcess.openAddActProcess = function () {
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
ActProcess.openChangeModel = function () {
    if (this.check()) {
        window.open(Feng.ctxPath + "/static/act/process-editor/modeler.html?modelId=" + this.seItem.id);
    }
};

/**
 * 导出
 * @param
 */
ActProcess.export = function () {
    if (this.check()) {
        window.open(Feng.ctxPath + "/act/model/export?id=" + this.seItem.id);
    }
};

/**
 * 删除
 */
ActProcess.delActProcess = function () {
    if (this.check()) {

        var operation = function(){
            var modelId = ActProcess.seItem.process.deploymentId;
            var ajax = new $ax(Feng.ctxPath + "/act/process/delete", function () {
                Feng.success("删除成功!");
                ActProcess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("deploymentId", modelId);
            ajax.start();
        };

        Feng.confirm("是否删除模型" + ActProcess.seItem.process.deploymentId + "?",operation);
    }
};

/**
 * 激活/挂起
 */
ActProcess.pending = function () {
    if (this.check()) {
        var state;
        var message;
        if (ActProcess.seItem.process.suspensionState == 1) {
            state = "suspend";
            message = "挂起";
        } else {
            state = "active";
            message = "激活";
        }
        var operation = function(){

            var modelId = ActProcess.seItem.process.id;
            var ajax = new $ax(Feng.ctxPath + "/act/process/update/" + state, function () {
                Feng.success("删除成功!");
                ActProcess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("procDefId", modelId);
            ajax.start();
        };

        Feng.confirm("是否" + message + "模型" + ActProcess.seItem.process.id + "?", operation);
    }
};

/**
 * 部署
 */
ActProcess.deploy = function () {
    if (this.check()) {
        var operation = function(){
            var modelId = ActProcess.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/act/model/deploy", function () {
                Feng.success("部署成功!");
                ActProcess.table.refresh();
            }, function (data) {
                Feng.error("部署失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", modelId);
            ajax.start();
        };

        Feng.confirm("是否部署模型" + ActProcess.seItem.name + "?",operation);
    }
};

ActProcess.resetSearch = function () {
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");

    ActProcess.search();
};

ActProcess.search = function () {
    var queryData = {};

    queryData['deptid'] = ActProcess.deptid;
    queryData['name'] = $("#name").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();

    ActProcess.table.refresh({query: queryData});
};

ActProcess.onClickDept = function (e, treeId, treeNode) {
    ActProcess.deptid = treeNode.id;
    ActProcess.search();
};

$(function () {
    var defaultColunms = ActProcess.initColumn();
    var table = new BSTable("actProcessTable", "/act/process/list", defaultColunms);
    table.setPaginationType("server");
    ActProcess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
});
