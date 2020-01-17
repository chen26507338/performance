/**
 * 工作得分管理初始化
 */
var JobTaskPoint = {
    id: "JobTaskPointTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
JobTaskPoint.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '任务ID', field:'taskId', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职员ID', field:'userId', visible: true, align: 'center', valign: 'middle'}
       ,{title: '得分', field:'point', visible: true, align: 'center', valign: 'middle'}
       ,{title: '类型', field:'type', visible: true, align: 'center', valign: 'middle'}
       ,{title: '创建时间', field:'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
JobTaskPoint.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        JobTaskPoint.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
JobTaskPoint.formParams = function() {
    var queryData = {};
    queryData['startCreateTime'] = $("#startCreateTime").val();
    queryData['endCreateTime'] = $("#endCreateTime").val();
    return queryData;
};


/**
 * 点击添加工作得分
 */
JobTaskPoint.openAddJobTaskPoint = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加工作得分',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jobTaskPoint/jobTaskPoint_add'
    });
};

/**
 * 打开查看工作得分详情
 */
JobTaskPoint.openJobTaskPointDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '工作得分详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/jobTaskPoint/jobTaskPoint_update/' + JobTaskPoint.seItem.id
        });
    }
};

/**
 * 删除工作得分
 */
JobTaskPoint.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除工作得分", function () {
            var ajax = new $ax(Feng.ctxPath + "/jobTaskPoint/delete", function (data) {
                Feng.success("删除成功!");
                JobTaskPoint.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jobTaskPointId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询工作得分列表
 */
JobTaskPoint.search = function () {
    JobTaskPoint.table.refresh({query: JobTaskPoint.formParams()});
};

var layer;
$(function () {
    var defaultColunms = JobTaskPoint.initColumn();
    var table = new BSTable(JobTaskPoint.id, "/jobTaskPoint/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(JobTaskPoint.formParams());
    JobTaskPoint.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
