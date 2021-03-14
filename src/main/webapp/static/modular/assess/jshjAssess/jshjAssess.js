/**
 * 竞赛获奖管理初始化
 */
var JshjAssess = {
    id: "JshjAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
JshjAssess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
        ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
        ,{title: '竞赛类别', field:'type', visible: true, align: 'center', valign: 'middle'}
       ,{title: '竞赛名称', field:'jsName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '赛项名称', field:'sxName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '竞赛级别', field:'jsLevel', visible: true, align: 'center', valign: 'middle'}
       ,{title: '获奖等级', field:'hjLevel', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校级积分', field:'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '参赛/指导/管理', field:'jsType', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年度', field:'year', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
JshjAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        JshjAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
JshjAssess.formParams = function() {
    var queryData = {};
    queryData['expand["user"]'] = $("#user").val();
    return queryData;
};


/**
 * 点击添加竞赛获奖
 */
JshjAssess.openAddJshjAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加竞赛获奖',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jshjAssess/jshjAssess_add'
    });
};

/**
 * 打开查看竞赛获奖详情
 */
JshjAssess.openJshjAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '竞赛获奖详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/jshjAssess/jshjAssess_update/' + JshjAssess.seItem.id
        });
    }
};

/**
 * 点击导入考核指标库
 */
JshjAssess.openImportAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jshjAssess/jshjAssess_import'
    });
};


/**
 * 删除竞赛获奖
 */
JshjAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除竞赛获奖", function () {
            var ajax = new $ax(Feng.ctxPath + "/jshjAssess/delete", function (data) {
                Feng.success("删除成功!");
                JshjAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jshjAssessId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询竞赛获奖列表
 */
JshjAssess.search = function () {
    JshjAssess.table.refresh({query: JshjAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = JshjAssess.initColumn();
    var table = new BSTable(JshjAssess.id, "/jshjAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(JshjAssess.formParams());
    JshjAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
