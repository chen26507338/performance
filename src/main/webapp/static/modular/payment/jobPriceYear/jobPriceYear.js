/**
 * 年度岗位责任奖管理初始化
 */
var JobPriceYear = {
    id: "JobPriceYearTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
JobPriceYear.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '职工编号', field:'expand.account', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职工姓名', field:'expand.name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '一月', field:'month1', visible: true, align: 'center', valign: 'middle'}
       ,{title: '二月', field:'month2', visible: true, align: 'center', valign: 'middle'}
       ,{title: '三月', field:'month3', visible: true, align: 'center', valign: 'middle'}
       ,{title: '四月', field:'month4', visible: true, align: 'center', valign: 'middle'}
       ,{title: '五月', field:'month5', visible: true, align: 'center', valign: 'middle'}
       ,{title: '六月', field:'month6', visible: true, align: 'center', valign: 'middle'}
       ,{title: '七月', field:'month7', visible: true, align: 'center', valign: 'middle'}
       ,{title: '八月', field:'month8', visible: true, align: 'center', valign: 'middle'}
       ,{title: '九月', field:'month9', visible: true, align: 'center', valign: 'middle'}
       ,{title: '十月', field:'month10', visible: true, align: 'center', valign: 'middle'}
       ,{title: '十一月', field:'month11', visible: true, align: 'center', valign: 'middle'}
       ,{title: '十二月', field:'month12', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
JobPriceYear.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        JobPriceYear.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
JobPriceYear.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 导出
 */
JobPriceYear.exportJobPrice = function () {

    Feng.confirm("是否导出年度报表", function () {
        window.open(Feng.ctxPath + "/jobPriceYear/exportData");
    });
};


/**
 * 点击添加年度岗位责任奖
 */
JobPriceYear.openAddJobPriceYear = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加年度岗位责任奖',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jobPriceYear/jobPriceYear_add'
    });
};

/**
 * 打开查看年度岗位责任奖详情
 */
JobPriceYear.openJobPriceYearDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '年度岗位责任奖详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/jobPriceYear/jobPriceYear_update/' + JobPriceYear.seItem.id
        });
    }
};

/**
 * 删除年度岗位责任奖
 */
JobPriceYear.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除年度岗位责任奖", function () {
            var ajax = new $ax(Feng.ctxPath + "/jobPriceYear/delete", function (data) {
                Feng.success("删除成功!");
                JobPriceYear.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jobPriceYearId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询年度岗位责任奖列表
 */
JobPriceYear.search = function () {
    JobPriceYear.table.refresh({query: JobPriceYear.formParams()});
};

var layer;
$(function () {
    var defaultColunms = JobPriceYear.initColumn();
    var table = new BSTable(JobPriceYear.id, "/jobPriceYear/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(JobPriceYear.formParams());
    JobPriceYear.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
