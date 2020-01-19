/**
 * 工作任务管理初始化
 */
var JobTask = {
    id: "JobTaskTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
JobTask.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '职责', field:'dutiesId', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.jobDutiesDes;}}
       ,{title: '部门', field:'deptId', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.deptName;}}
       ,{title: '经办人', field:'userId', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.userName;}}
       ,{title: '委派协助人', field:'appointUserId', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.appointUserName;}}
       ,{title: '经办协作人', field:'applyUserId', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.applyUserName;}}
       ,{title: '任务分', field:'point', visible: true, align: 'center', valign: 'middle'}
       ,{title: '任务描述', field:'des', visible: true, align: 'center', valign: 'middle'}
       ,{title: '经办人处理结果', field:'userDes', visible: true, align: 'center', valign: 'middle'}
       ,{title: '委派协助人办理结果', field:'appointUserDes', visible: true, align: 'center', valign: 'middle'}
       ,{title: '经办协作人办理结果', field:'applyUserDes', visible: true, align: 'center', valign: 'middle'}
       ,{title: '提交时间', field:'createTime', visible: true, align: 'center', valign: 'middle'}
       ,{title: '处理结束时间', field:'endTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
JobTask.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        JobTask.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
JobTask.formParams = function() {
    var queryData = {};
    queryData['startCreateTime'] = $("#startCreateTime").val();
    queryData['endCreateTime'] = $("#endCreateTime").val();
    queryData['startEndTime'] = $("#startEndTime").val();
    queryData['endEndTime'] = $("#endEndTime").val();
    return queryData;
};


/**
 * 点击添加工作任务
 */
JobTask.openAddJobTask = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加工作任务',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jobTask/jobTask_add'
    });
};

/**
 * 打开查看工作任务详情
 */
JobTask.openJobTaskDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '工作任务详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/jobTask/jobTask_update/' + JobTask.seItem.id
        });
    }
};

/**
 * 删除工作任务
 */
JobTask.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除工作任务", function () {
            var ajax = new $ax(Feng.ctxPath + "/jobTask/delete", function (data) {
                Feng.success("删除成功!");
                JobTask.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jobTaskId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询工作任务列表
 */
JobTask.search = function () {
    JobTask.table.refresh({query: JobTask.formParams()});
};

var layer;
$(function () {
    var defaultColunms = JobTask.initColumn();
    var table = new BSTable(JobTask.id, "/jobTask/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(JobTask.formParams());
    JobTask.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
