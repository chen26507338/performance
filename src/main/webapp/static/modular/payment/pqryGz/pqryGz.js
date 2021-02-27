/**
 * 派遣人员管理初始化
 */
var PqryGz = {
    id: "PqryGzTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PqryGz.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
        ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
        ,{title: '时间', field:'expand.time', visible: true, align: 'center', valign: 'middle'}
        ,{title: '工资', field:'gz', visible: true, align: 'center', valign: 'middle'}
       ,{title: '补发工资差额', field:'bfgzce', visible: true, align: 'center', valign: 'middle'}
       ,{title: '扣产假', field:'kcj', visible: true, align: 'center', valign: 'middle'}
       ,{title: '应发工资', field:'yfgz', visible: true, align: 'center', valign: 'middle'}
       ,{title: '养老失业基数', field:'ylsyjs', visible: true, align: 'center', valign: 'middle'}
       ,{title: '医保生育基数', field:'ybsyjs', visible: true, align: 'center', valign: 'middle'}
       ,{title: '公积金基数', field:'gjjjs', visible: true, align: 'center', valign: 'middle'}
       ,{title: '工伤基数', field:'gsjs', visible: true, align: 'center', valign: 'middle'}
       ,{title: '养老', field:'yl', visible: true, align: 'center', valign: 'middle'}
       ,{title: '失业', field:'sy', visible: true, align: 'center', valign: 'middle'}
       ,{title: '医保', field:'yb', visible: true, align: 'center', valign: 'middle'}
       ,{title: '公积金', field:'gjj', visible: true, align: 'center', valign: 'middle'}
       ,{title: '合计', field:'hj', visible: true, align: 'center', valign: 'middle'}
       ,{title: '实发工资', field:'sfgz', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PqryGz.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PqryGz.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
PqryGz.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 点击导入考核指标库
 */
PqryGz.openImport = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/pqryGz/pqryGz_import'
    });
};

/**
 * 点击添加派遣人员
 */
PqryGz.openAddPqryGz = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加派遣人员',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/pqryGz/pqryGz_add'
    });
};

/**
 * 打开查看派遣人员详情
 */
PqryGz.openPqryGzDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '派遣人员详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/pqryGz/pqryGz_update/' + PqryGz.seItem.id
        });
    }
};

/**
 * 删除派遣人员
 */
PqryGz.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除派遣人员", function () {
            var ajax = new $ax(Feng.ctxPath + "/pqryGz/delete", function (data) {
                Feng.success("删除成功!");
                PqryGz.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("pqryGzId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询派遣人员列表
 */
PqryGz.search = function () {
    PqryGz.table.refresh({query: PqryGz.formParams()});
};

var layer;
$(function () {
    var defaultColunms = PqryGz.initColumn();
    var table = new BSTable(PqryGz.id, "/pqryGz/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(PqryGz.formParams());
    PqryGz.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
