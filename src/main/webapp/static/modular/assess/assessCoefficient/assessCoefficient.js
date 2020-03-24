/**
 * 考核系数管理初始化
 */
var AssessCoefficient = {
    id: "AssessCoefficientTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AssessCoefficient.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '类型', field:'type', visible: true, align: 'center', valign: 'middle'}
       ,{title: '名称', field:'name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '系数', field:'coefficient', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AssessCoefficient.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AssessCoefficient.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
AssessCoefficient.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加考核系数
 */
AssessCoefficient.openAddAssessCoefficient = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加考核系数',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/assessCoefficient/assessCoefficient_add'
    });
};

/**
 * 打开查看考核系数详情
 */
AssessCoefficient.openAssessCoefficientDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '考核系数详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/assessCoefficient/assessCoefficient_update/' + AssessCoefficient.seItem.type
        });
    }
};

/**
 * 删除考核系数
 */
AssessCoefficient.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除考核系数", function () {
            var ajax = new $ax(Feng.ctxPath + "/assessCoefficient/delete", function (data) {
                Feng.success("删除成功!");
                AssessCoefficient.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("assessCoefficientId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询考核系数列表
 */
AssessCoefficient.search = function () {
    AssessCoefficient.table.refresh({query: AssessCoefficient.formParams()});
};

var layer;
$(function () {
    var defaultColunms = AssessCoefficient.initColumn();
    var table = new BSTable(AssessCoefficient.id, "/assessCoefficient/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(AssessCoefficient.formParams());
    AssessCoefficient.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
