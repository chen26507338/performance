/**
 * 科研成果管理初始化
 */
var ScientificAchievement = {
    id: "ScientificAchievementTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ScientificAchievement.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '名称', field:'name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '简要描述', field:'des', visible: true, align: 'center', valign: 'middle'}
       ,{title: '时间', field:'time', visible: true, align: 'center', valign: 'middle'}
       ,{title: '类型', field:'type', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ScientificAchievement.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ScientificAchievement.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ScientificAchievement.formParams = function() {
    var queryData = {};
    queryData['userId'] = $("#userId").val();
    queryData['status'] = $("#status").val();
    return queryData;
};

/**
 * 点击申请修改
 */
ScientificAchievement.openAddApply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请审核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scientificAchievement/addApply'
    });
    layer.full(this.layerIndex);
};
/**
 * 点击添加科研成果
 */
ScientificAchievement.openAddScientificAchievement = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加科研成果',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scientificAchievement/scientificAchievement_add'
    });
};

/**
 * 打开查看科研成果详情
 */
ScientificAchievement.openScientificAchievementDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '科研成果详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/scientificAchievement/scientificAchievement_update/' + ScientificAchievement.seItem.id
        });
    }
};

/**
 * 删除科研成果
 */
ScientificAchievement.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除科研成果", function () {
            var ajax = new $ax(Feng.ctxPath + "/scientificAchievement/delete", function (data) {
                Feng.success("删除成功!");
                ScientificAchievement.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("scientificAchievementId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询科研成果列表
 */
ScientificAchievement.search = function () {
    ScientificAchievement.table.refresh({query: ScientificAchievement.formParams()});
};

var layer;
$(function () {
    var defaultColunms = ScientificAchievement.initColumn();
    var table = new BSTable(ScientificAchievement.id, "/scientificAchievement/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ScientificAchievement.formParams());
    ScientificAchievement.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
