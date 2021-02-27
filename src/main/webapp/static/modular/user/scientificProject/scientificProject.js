/**
 * 科研项目管理初始化
 */
var ScientificProject = {
    id: "ScientificProjectTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ScientificProject.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '课题名称', field:'name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '科研项目', field:'assessName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '分类/级别', field:'type', visible: true, align: 'center', valign: 'middle'}
       ,{title: '到账经费', field:'expenditure', visible: true, align: 'center', valign: 'middle'}
       ,{title: '期刊名称/排名', field:'rank', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年度', field:'year', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校级积分', field:'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ScientificProject.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ScientificProject.seItem = selected[0];
        return true;
    }
};


/**
 * 点击导入考核指标库
 */
ScientificProject.openImportAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scientificProject/scientificProject_import'
    });
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ScientificProject.formParams = function() {
    var queryData = {};
    queryData['userId'] = $("#userId").val();
    queryData['status'] = $("#status").val();
    return queryData;
};
/**
 * 点击申请修改
 */
ScientificProject.openAddApply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请审核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scientificProject/addApply'
    });
    layer.full(this.layerIndex);
};

/**
 * 点击添加科研项目
 */
ScientificProject.openAddScientificProject = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加科研项目',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scientificProject/scientificProject_add'
    });
};

/**
 * 打开查看科研项目详情
 */
ScientificProject.openScientificProjectDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '科研项目详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/scientificProject/scientificProject_update/' + ScientificProject.seItem.id
        });
    }
};

/**
 * 删除科研项目
 */
ScientificProject.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除科研项目", function () {
            var ajax = new $ax(Feng.ctxPath + "/scientificProject/delete", function (data) {
                Feng.success("删除成功!");
                ScientificProject.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("scientificProjectId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询科研项目列表
 */
ScientificProject.search = function () {
    ScientificProject.table.refresh({query: ScientificProject.formParams()});
};

var layer;
$(function () {
    var defaultColunms = ScientificProject.initColumn();
    var table = new BSTable(ScientificProject.id, "/scientificProject/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ScientificProject.formParams());
    ScientificProject.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
