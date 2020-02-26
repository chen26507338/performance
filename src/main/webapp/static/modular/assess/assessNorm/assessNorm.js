/**
 *  考核指标库管理初始化
 */
var AssessNorm = {
    id: "AssessNormTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AssessNorm.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '内容', field:'content', visible: true, align: 'center', valign: 'middle'}
       ,{title: '代码', field:'code', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核项目', field:'type', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.typeDict;}}
        ,{title: '校级指标/院级浮动值', field:'point', visible: true, align: 'center', valign: 'middle'}
        ,{title: '部门', field:'deptId', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.deptDict;}}
       ,{title: '描述', field:'des', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AssessNorm.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AssessNorm.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
AssessNorm.formParams = function() {
    var queryData = {};
    queryData['content'] = $("#content").val();
    queryData['code'] = $("#code").val();
    queryData['type'] = $("#type").val();
    return queryData;
};


/**
 * 点击添加 考核指标库
 */
AssessNorm.openAddAssessNorm = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加 考核指标库',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/assessNorm/assessNorm_add'
    });
};

/**
 * 打开查看 考核指标库详情
 */
AssessNorm.openAssessNormDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: ' 考核指标库详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/assessNorm/assessNorm_update/' + AssessNorm.seItem.id
        });
    }
};

/**
 * 删除 考核指标库
 */
AssessNorm.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除 考核指标库", function () {
            var ajax = new $ax(Feng.ctxPath + "/assessNorm/delete", function (data) {
                Feng.success("删除成功!");
                AssessNorm.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("assessNormId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询 考核指标库列表
 */
AssessNorm.search = function () {
    AssessNorm.table.refresh({query: AssessNorm.formParams()});
};

var layer;
$(function () {
    var defaultColunms = AssessNorm.initColumn();
    var table = new BSTable(AssessNorm.id, "/assessNorm/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(AssessNorm.formParams());
    AssessNorm.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
