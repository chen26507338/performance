/**
 * 科研论著管理初始化
 */
var ScientificTreatise = {
    id: "ScientificTreatiseTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ScientificTreatise.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '发表时间', field:'publishDate', visible: true, align: 'center', valign: 'middle'}
       ,{title: '论文题目', field:'title', visible: true, align: 'center', valign: 'middle'}
       ,{title: '刊物名称', field:'periodicalName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '作者顺序', field:'authorOrder', visible: true, align: 'center', valign: 'middle'}
       ,{title: '刊物级别', field:'periodicalLevel', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ScientificTreatise.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ScientificTreatise.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ScientificTreatise.formParams = function() {
    var queryData = {};
    queryData['userId'] = $("#userId").val();
    queryData['status'] = $("#status").val();
    return queryData;
};

/**
 * 点击申请修改
 */
ScientificTreatise.openAddApply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请修改',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scientificTreatise/addApply'
    });
    layer.full(this.layerIndex);
};
/**
 * 点击添加科研论著
 */
ScientificTreatise.openAddScientificTreatise = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加科研论著',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scientificTreatise/scientificTreatise_add'
    });
};

/**
 * 打开查看科研论著详情
 */
ScientificTreatise.openScientificTreatiseDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '科研论著详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/scientificTreatise/scientificTreatise_update/' + ScientificTreatise.seItem.id
        });
    }
};

/**
 * 删除科研论著
 */
ScientificTreatise.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除科研论著", function () {
            var ajax = new $ax(Feng.ctxPath + "/scientificTreatise/delete", function (data) {
                Feng.success("删除成功!");
                ScientificTreatise.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("scientificTreatiseId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询科研论著列表
 */
ScientificTreatise.search = function () {
    ScientificTreatise.table.refresh({query: ScientificTreatise.formParams()});
};

var layer;
$(function () {
    var defaultColunms = ScientificTreatise.initColumn();
    var table = new BSTable(ScientificTreatise.id, "/scientificTreatise/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ScientificTreatise.formParams());
    ScientificTreatise.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
