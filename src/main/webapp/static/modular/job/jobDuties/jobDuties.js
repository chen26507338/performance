/**
 * 岗位职责管理管理初始化
 */
var JobDuties = {
    id: "JobDutiesTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
JobDuties.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '描述', field:'des', visible: true, align: 'center', valign: 'middle'}
       ,{title: '得分', field:'point', visible: true, align: 'center', valign: 'middle'}
       ,{title: '岗位ID', field:'jobId', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.jobIdDict;}}
    ];
};

/**
 * 检查是否选中
 */
JobDuties.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        JobDuties.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
JobDuties.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加岗位职责管理
 */
JobDuties.openAddJobDuties = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加岗位职责管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jobDuties/jobDuties_add'
    });
};

/**
 * 打开查看岗位职责管理详情
 */
JobDuties.openJobDutiesDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '岗位职责管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/jobDuties/jobDuties_update/' + JobDuties.seItem.id
        });
    }
};

/**
 * 删除岗位职责管理
 */
JobDuties.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除岗位职责管理", function () {
            var ajax = new $ax(Feng.ctxPath + "/jobDuties/delete", function (data) {
                Feng.success("删除成功!");
                JobDuties.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jobDutiesId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询岗位职责管理列表
 */
JobDuties.search = function () {
    JobDuties.table.refresh({query: JobDuties.formParams()});
};

var layer;
$(function () {
    var defaultColunms = JobDuties.initColumn();
    var table = new BSTable(JobDuties.id, "/jobDuties/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(JobDuties.formParams());
    JobDuties.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
