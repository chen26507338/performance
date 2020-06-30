/**
 * 工作简历管理初始化
 */
var WorkResume = {
    id: "WorkResumeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
WorkResume.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '开始时间', field:'startDate', visible: true, align: 'center', valign: 'middle'}
       ,{title: '结束时间', field:'endDate', visible: true, align: 'center', valign: 'middle'}
       ,{title: '工作单位', field:'company', visible: true, align: 'center', valign: 'middle'}
       ,{title: '所在部门', field:'department', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职务', field:'job', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职称', field:'title', visible: true, align: 'center', valign: 'middle'}
       ,{title: '养老缴交月数 ', field:'pensionMonth', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
WorkResume.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        WorkResume.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
WorkResume.formParams = function() {
    var queryData = {};
    queryData['userId'] = $("#userId").val();
    queryData['status'] = $("#status").val();
    return queryData;
};

/**
 * 点击申请修改
 */
WorkResume.openAddApply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请修改',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/workResume/addApply'
    });
    layer.full(this.layerIndex);
};

/**
 * 点击添加工作简历
 */
WorkResume.openAddWorkResume = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加工作简历',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/workResume/workResume_add'
    });
};

/**
 * 打开查看工作简历详情
 */
WorkResume.openWorkResumeDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '工作简历详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/workResume/workResume_update/' + WorkResume.seItem.id
        });
    }
};

/**
 * 删除工作简历
 */
WorkResume.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除工作简历", function () {
            var ajax = new $ax(Feng.ctxPath + "/workResume/delete", function (data) {
                Feng.success("删除成功!");
                WorkResume.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("workResumeId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询工作简历列表
 */
WorkResume.search = function () {
    WorkResume.table.refresh({query: WorkResume.formParams()});
};

var layer;
$(function () {
    var defaultColunms = WorkResume.initColumn();
    var table = new BSTable(WorkResume.id, "/workResume/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(WorkResume.formParams());
    WorkResume.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
