/**
 * 业务表管理初始化
 */
var GenTable = {
    id: "GenTableTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
GenTable.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '描述', field: 'comments', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
GenTable.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        GenTable.seItem = selected[0];
        return true;
    }
};

GenTable.gen = function () {
    if(this.check()) {
        var index = layer.open({
            type: 2,
            title: '生成业务表',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/code?id=' + GenTable.seItem.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 点击添加业务表
 */
GenTable.openAddGenTable = function () {
    var selectTable = $("#tableName").val();
    if (selectTable == null || selectTable == '') {
        Feng.info("请先选择要生成的表！");
        return ;
    }
    var index = layer.open({
        type: 2,
        title: '添加业务表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/genTable/genTable_add?tableName='+selectTable
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 打开查看业务表详情
 */
GenTable.openGenTableDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '业务表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/genTable/genTable_update/' + GenTable.seItem.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 删除业务表
 */
GenTable.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除业务表", function () {
            var ajax = new $ax(Feng.ctxPath + "/genTable/delete", function (data) {
                Feng.success("删除成功!");
                GenTable.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("genTableId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询业务表列表
 */
GenTable.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    GenTable.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = GenTable.initColumn();
    var table = new BSTable(GenTable.id, "/genTable/list", defaultColunms);
    table.setPaginationType("client");
    GenTable.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
});
