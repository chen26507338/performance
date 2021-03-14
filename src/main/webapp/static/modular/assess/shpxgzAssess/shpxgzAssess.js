/**
 * 社会培训工作考核管理初始化
 */
var ShpxgzAssess = {
    id: "ShpxgzAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ShpxgzAssess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
        ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
        ,{title: '考核项目', field:'assessName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '名称', field:'name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '数量', field:'num', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校积分', field:'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年度', field:'year', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ShpxgzAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ShpxgzAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ShpxgzAssess.formParams = function() {
    var queryData = {};
    queryData['expand["user"]'] = $("#user").val();
    return queryData;
};

/**
 * 点击导入考核指标库
 */
ShpxgzAssess.openImportAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/shpxgzAssess/shpxgzAssess_import'
    });
};


/**
 * 点击添加社会培训工作考核
 */
ShpxgzAssess.openAddShpxgzAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加社会培训工作考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/shpxgzAssess/shpxgzAssess_add'
    });
};

/**
 * 打开查看社会培训工作考核详情
 */
ShpxgzAssess.openShpxgzAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '社会培训工作考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/shpxgzAssess/shpxgzAssess_update/' + ShpxgzAssess.seItem.id
        });
    }
};

/**
 * 删除社会培训工作考核
 */
ShpxgzAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除社会培训工作考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/shpxgzAssess/delete", function (data) {
                Feng.success("删除成功!");
                ShpxgzAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("shpxgzAssessId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询社会培训工作考核列表
 */
ShpxgzAssess.search = function () {
    ShpxgzAssess.table.refresh({query: ShpxgzAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = ShpxgzAssess.initColumn();
    var table = new BSTable(ShpxgzAssess.id, "/shpxgzAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ShpxgzAssess.formParams());
    ShpxgzAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
