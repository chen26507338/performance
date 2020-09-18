/**
 * 管理服务管理初始化
 */
var ManService = {
    id: "ManServiceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ManService.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ManService.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ManService.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ManService.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加管理服务
 */
ManService.openAddManService = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加管理服务',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/manService/manService_add'
    });
};

/**
 * 打开查看管理服务详情
 */
ManService.openManServiceDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '管理服务详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/manService/manService_update/' + ManService.seItem.id
        });
    }
};

/**
 * 删除管理服务
 */
ManService.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除管理服务", function () {
            var ajax = new $ax(Feng.ctxPath + "/manService/delete", function (data) {
                Feng.success("删除成功!");
                ManService.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("manServiceId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 点击添加学生工作考核
 */
ManService.apply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/manService/manService_apply'
    });
};

/**
 * 查询管理服务列表
 */
ManService.search = function () {
    ManService.table.refresh({query: ManService.formParams()});
};

var layer;
$(function () {
    var defaultColunms = ManService.initColumn();
    var table = new BSTable(ManService.id, "/manService/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ManService.formParams());
    ManService.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
