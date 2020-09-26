/**
 * 分配分数记录管理初始化
 */
var AllocationPointLog = {
    id: "AllocationPointLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AllocationPointLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
    ];
};

/**
 * 检查是否选中
 */
AllocationPointLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AllocationPointLog.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
AllocationPointLog.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加分配分数记录
 */
AllocationPointLog.openAddAllocationPointLog = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加分配分数记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/allocationPointLog/allocationPointLog_add'
    });
};

/**
 * 打开查看分配分数记录详情
 */
AllocationPointLog.openAllocationPointLogDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '分配分数记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/allocationPointLog/allocationPointLog_update/' + AllocationPointLog.seItem.id
        });
    }
};

/**
 * 删除分配分数记录
 */
AllocationPointLog.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除分配分数记录", function () {
            var ajax = new $ax(Feng.ctxPath + "/allocationPointLog/delete", function (data) {
                Feng.success("删除成功!");
                AllocationPointLog.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("allocationPointLogId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询分配分数记录列表
 */
AllocationPointLog.search = function () {
    AllocationPointLog.table.refresh({query: AllocationPointLog.formParams()});
};

var layer;
$(function () {
    var defaultColunms = AllocationPointLog.initColumn();
    var table = new BSTable(AllocationPointLog.id, "/allocationPointLog/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(AllocationPointLog.formParams());
    AllocationPointLog.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
