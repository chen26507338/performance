/**
 * 党支部工作考核管理初始化
 */
var DzbWorkAssess = {
    id: "DzbWorkAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DzbWorkAssess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
        ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
        ,{title: '名称', field:'name', visible: true, align: 'center', valign: 'middle'}
        ,{title: '支部得分', field:'zbdf', visible: true, align: 'center', valign: 'middle'}
        ,{title: '指标代码', field:'expand.normCode', visible: true, align: 'center', valign: 'middle'}
       ,{title: 'II级指标', field:'expand.normContent', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '考核结果', field:'result', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核年度', field:'year', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校级指标分', field:'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '院级浮动值', field:'collegeNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '部门', field:'expand.deptName', visible: true, align: 'center', valign: 'middle'}
        ,{title: '状态', field:'expand.statusDict', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
DzbWorkAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        DzbWorkAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
DzbWorkAssess.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 点击导入考核指标库
 */
DzbWorkAssess.openImportAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dzbWorkAssess/dzbWorkAssess_import'
    });
};


/**
 * 点击添加党支部工作考核
 */
DzbWorkAssess.openAddDzbWorkAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加党支部工作考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dzbWorkAssess/dzbWorkAssess_add'
    });
};

/**
 * 打开查看党支部工作考核详情
 */
DzbWorkAssess.openDzbWorkAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '党支部工作考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dzbWorkAssess/dzbWorkAssess_update/' + DzbWorkAssess.seItem.id
        });
    }
};

/**
 * 删除党支部工作考核
 */
DzbWorkAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除党支部工作考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/dzbWorkAssess/delete", function (data) {
                Feng.success("删除成功!");
                DzbWorkAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("dzbWorkAssessId",that.seItem.id);
            ajax.start();
        });
    }
};


/**
 * 点击添加学生工作考核
 */
DzbWorkAssess.apply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dzbWorkAssess/dzbWorkAssess_apply'
    });
    layer.full(this.layerIndex);
};

/**
 * 点击添加学生工作考核
 */
DzbWorkAssess.allocation = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '分配分数',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dzbWorkAssess/dzbWorkAssess_allocation'
    });
    layer.full(this.layerIndex);
};

/**
 * 查询党支部工作考核列表
 */
DzbWorkAssess.search = function () {
    DzbWorkAssess.table.refresh({query: DzbWorkAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = DzbWorkAssess.initColumn();
    var table = new BSTable(DzbWorkAssess.id, "/dzbWorkAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(DzbWorkAssess.formParams());
    DzbWorkAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
