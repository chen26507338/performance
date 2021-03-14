/**
 * 经费完成情况考核管理初始化
 */
var JfwcqkAssess = {
    id: "JfwcqkAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
JfwcqkAssess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
        ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
        ,{title: '经费项目', field:'assessName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '经费完成费', field:'jfwcf', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校级分', field:'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年度', field:'year', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核系数', field:'coePoint', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
JfwcqkAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        JfwcqkAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
JfwcqkAssess.formParams = function() {
    var queryData = {};
    queryData['expand["user"]'] = $("#user").val();
    return queryData;
};

/**
 * 点击导入考核指标库
 */
JfwcqkAssess.openImportAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jfwcqkAssess/jfwcqkAssess_import'
    });
};


/**
 * 点击添加经费完成情况考核
 */
JfwcqkAssess.openAddJfwcqkAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加经费完成情况考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jfwcqkAssess/jfwcqkAssess_add'
    });
};

/**
 * 打开查看经费完成情况考核详情
 */
JfwcqkAssess.openJfwcqkAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '经费完成情况考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/jfwcqkAssess/jfwcqkAssess_update/' + JfwcqkAssess.seItem.id
        });
    }
};

/**
 * 删除经费完成情况考核
 */
JfwcqkAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除经费完成情况考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/jfwcqkAssess/delete", function (data) {
                Feng.success("删除成功!");
                JfwcqkAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jfwcqkAssessId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询经费完成情况考核列表
 */
JfwcqkAssess.search = function () {
    JfwcqkAssess.table.refresh({query: JfwcqkAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = JfwcqkAssess.initColumn();
    var table = new BSTable(JfwcqkAssess.id, "/jfwcqkAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(JfwcqkAssess.formParams());
    JfwcqkAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
