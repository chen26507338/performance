/**
 * 教学工作量管理初始化
 */
var TeachingLoad = {
    id: "TeachingLoadTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TeachingLoad.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '课程名称', field:'courseName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '周次', field:'weeks', visible: true, align: 'center', valign: 'middle'}
       ,{title: '课程类型', field:'courseType', visible: true, align: 'center', valign: 'middle'}
       ,{title: '课时时数', field:'courseTimes', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年度', field:'year', visible: true, align: 'center', valign: 'middle'}
       ,{title: '状态', field:'expand.statusDict', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TeachingLoad.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TeachingLoad.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
TeachingLoad.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加教学工作量
 */
TeachingLoad.openAddTeachingLoad = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加教学工作量',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/teachingLoad/teachingLoad_add'
    });
    layer.full(this.layerIndex);
};

/**
 * 打开查看教学工作量详情
 */
TeachingLoad.openTeachingLoadDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '教学工作量详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/teachingLoad/teachingLoad_update/' + TeachingLoad.seItem.id
        });
    }
};

/**
 * 删除教学工作量
 */
TeachingLoad.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除教学工作量", function () {
            var ajax = new $ax(Feng.ctxPath + "/teachingLoad/delete", function (data) {
                Feng.success("删除成功!");
                TeachingLoad.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("teachingLoadId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询教学工作量列表
 */
TeachingLoad.search = function () {
    TeachingLoad.table.refresh({query: TeachingLoad.formParams()});
};

var layer;
$(function () {
    var defaultColunms = TeachingLoad.initColumn();
    var table = new BSTable(TeachingLoad.id, "/teachingLoad/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(TeachingLoad.formParams());
    TeachingLoad.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
