/**
 * 岗位管理管理初始化
 */
var Job = {
    id: "JobTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Job.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '名称', field:'name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '描述', field:'des', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '部门', field:'deptId', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.deptIdDict;}}
       ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.statusDict;}}
    ];
};

/**
 * 检查是否选中
 */
Job.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Job.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
Job.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加岗位管理
 */
Job.openAddJob = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加岗位管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/job/job_add'
    });
};
/**
 * 点击添加岗位管理
 */
Job.openAddJobDuties = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: this.seItem.name + '职责管理',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/jobDuties?jobId=' + this.seItem.id
        });
        layer.full(this.layerIndex);
    }
};

/**
 * 打开查看岗位管理详情
 */
Job.openJobDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '岗位管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/job/job_update/' + Job.seItem.id
        });
    }
};

/**
 * 删除岗位管理
 */
Job.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除岗位管理", function () {
            var ajax = new $ax(Feng.ctxPath + "/job/delete", function (data) {
                Feng.success("删除成功!");
                Job.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jobId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询岗位管理列表
 */
Job.search = function () {
    Job.table.refresh({query: Job.formParams()});
};

var layer;
$(function () {
    var defaultColunms = Job.initColumn();
    var table = new BSTable(Job.id, "/job/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Job.formParams());
    Job.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
