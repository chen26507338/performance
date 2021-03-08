/**
 * 代理校聘人员管理初始化
 */
var DlxpryGz = {
    id: "DlxpryGzTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DlxpryGz.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
        ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
        ,{title: '年份', field:'year', visible: true, align: 'center', valign: 'middle'}
        ,{title: '月份', field:'month', visible: true, align: 'center', valign: 'middle'}
        ,{title: '基本工资', field:'jbgz', visible: true, align: 'center', valign: 'middle'}
       ,{title: '基础性绩效', field:'jcxjx', visible: true, align: 'center', valign: 'middle'}
       ,{title: '补发工资等', field:'bfgzd', visible: true, align: 'center', valign: 'middle'}
       ,{title: '应发工资', field:'yfgz', visible: true, align: 'center', valign: 'middle'}
       ,{title: '养老保险', field:'ylbx', visible: true, align: 'center', valign: 'middle'}
       ,{title: '医疗保险', field:'yl', visible: true, align: 'center', valign: 'middle'}
       ,{title: '失业保险', field:'sybx', visible: true, align: 'center', valign: 'middle'}
       ,{title: '公积金', field:'gjj', visible: true, align: 'center', valign: 'middle'}
       ,{title: '工会费', field:'ghf', visible: true, align: 'center', valign: 'middle'}
       ,{title: '其他扣款', field:'qtkk', visible: true, align: 'center', valign: 'middle'}
       ,{title: '实发数', field:'sfs', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
DlxpryGz.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        DlxpryGz.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
DlxpryGz.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 点击导入考核指标库
 */
DlxpryGz.openImport = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dlxpryGz/dlxpryGz_import'
    });
};

/**
 * 点击添加代理校聘人员
 */
DlxpryGz.openAddDlxpryGz = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加代理校聘人员',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dlxpryGz/dlxpryGz_add'
    });
};

/**
 * 打开查看代理校聘人员详情
 */
DlxpryGz.openDlxpryGzDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '代理校聘人员详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dlxpryGz/dlxpryGz_update/' + DlxpryGz.seItem.id
        });
    }
};

/**
 * 删除代理校聘人员
 */
DlxpryGz.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除代理校聘人员", function () {
            var ajax = new $ax(Feng.ctxPath + "/dlxpryGz/delete", function (data) {
                Feng.success("删除成功!");
                DlxpryGz.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("dlxpryGzId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询代理校聘人员列表
 */
DlxpryGz.search = function () {
    DlxpryGz.table.refresh({query: DlxpryGz.formParams()});
};

var layer;
$(function () {
    var defaultColunms = DlxpryGz.initColumn();
    var table = new BSTable(DlxpryGz.id, "/dlxpryGz/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(DlxpryGz.formParams());
    DlxpryGz.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
