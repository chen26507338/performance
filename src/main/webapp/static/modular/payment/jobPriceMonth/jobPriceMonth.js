/**
 * 月度岗位责任奖管理初始化
 */
var JobPriceMonth = {
    id: "JobPriceMonthTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
JobPriceMonth.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '职工编号', field:'expand.account', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职工姓名', field:'expand.name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '基本岗位责任奖', field:'basePrice', visible: true, align: 'center', valign: 'middle'}
       ,{title: '管理服务工作奖', field:'mgrPrice', visible: true, align: 'center', valign: 'middle'}
       ,{title: '补发', field:'retroactivePrice', visible: true, align: 'center', valign: 'middle'}
       ,{title: '应发数', field:'shouldPrice', visible: true, align: 'center', valign: 'middle'}
       ,{title: '扣发', field:'garnishedPrice', visible: true, align: 'center', valign: 'middle'}
       ,{title: '实发数', field:'resultPrice', visible: true, align: 'center', valign: 'middle'}
       ,{title: '备注', field:'remark', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年度', field:'year', visible: true, align: 'center', valign: 'middle'}
       ,{title: '部门', field:'expand.deptName', visible: true, align: 'center', valign: 'middle'}
       ,{title: ' 月份', field:'month', visible: true, align: 'center', valign: 'middle'}
        ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.statusDict;}}
    ];
};

/**
 * 点击导入考核指标库
 */
JobPriceMonth.openImportNormalAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入岗位责任奖',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jobPriceMonth/jobPriceMonth_import'
    });
};


/**
 * 检查是否选中
 */
JobPriceMonth.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        JobPriceMonth.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
JobPriceMonth.formParams = function() {
    var queryData = {};
    queryData['month'] = $("#month").val();
    return queryData;
};


/**
 * 点击添加月度岗位责任奖
 */
JobPriceMonth.openAddJobPriceMonth = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加月度岗位责任奖',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jobPriceMonth/jobPriceMonth_add'
    });
};

/**
 * 打开查看月度岗位责任奖详情
 */
JobPriceMonth.openJobPriceMonthDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '月度岗位责任奖详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/jobPriceMonth/jobPriceMonth_update/' + JobPriceMonth.seItem.id
        });
    }
};

/**
 * 删除月度岗位责任奖
 */
JobPriceMonth.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除月度岗位责任奖", function () {
            var ajax = new $ax(Feng.ctxPath + "/jobPriceMonth/delete", function (data) {
                Feng.success("删除成功!");
                JobPriceMonth.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jobPriceMonthId",that.seItem.id);
            ajax.start();
        });
    }
};
/**
 * 删除月度岗位责任奖
 */
JobPriceMonth.exportJobPrice = function () {
    var month = $("#month").val();
    if (!month) {
        Feng.error("请输入要导出的月份");
        return;
    }
    Feng.confirm("是否导出月度报表", function () {
        window.open(Feng.ctxPath + "/jobPriceMonth/exportData?month=" + month);
    });
};

/**
 * 查询月度岗位责任奖列表
 */
JobPriceMonth.search = function () {
    JobPriceMonth.table.refresh({query: JobPriceMonth.formParams()});
};

var layer;
$(function () {
    var defaultColunms = JobPriceMonth.initColumn();
    var table = new BSTable(JobPriceMonth.id, "/jobPriceMonth/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(JobPriceMonth.formParams());
    JobPriceMonth.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
