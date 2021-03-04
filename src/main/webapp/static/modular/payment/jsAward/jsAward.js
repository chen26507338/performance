/**
 * 竞赛奖励管理初始化
 */
var JsAward = {
    id: "JsAwardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
JsAward.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
        ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
        ,{title: '用人形式', field:'yrxs', visible: true, align: 'center', valign: 'middle'}
       ,{title: '项目', field:'project', visible: true, align: 'center', valign: 'middle'}
       ,{title: '获奖类别', field:'hjlb', visible: true, align: 'center', valign: 'middle'}
       ,{title: '获奖等级', field:'hjdj', visible: true, align: 'center', valign: 'middle'}
       ,{title: '金额', field:'money', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年度', field:'year', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
JsAward.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        JsAward.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
JsAward.formParams = function() {
    var queryData = {};
    queryData['type'] = $("#type").val();
    return queryData;
};

/**
 * 点击导入考核指标库
 */
JsAward.openImport = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入数据',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jsAward/jsAward_import?type=' + type
    });
};


/**
 * 点击添加竞赛奖励
 */
JsAward.openAddJsAward = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加竞赛奖励',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jsAward/jsAward_add'
    });
};

/**
 * 打开查看竞赛奖励详情
 */
JsAward.openJsAwardDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '竞赛奖励详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/jsAward/jsAward_update/' + JsAward.seItem.id
        });
    }
};

/**
 * 删除竞赛奖励
 */
JsAward.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除竞赛奖励", function () {
            var ajax = new $ax(Feng.ctxPath + "/jsAward/delete", function (data) {
                Feng.success("删除成功!");
                JsAward.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jsAwardId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询竞赛奖励列表
 */
JsAward.search = function () {
    JsAward.table.refresh({query: JsAward.formParams()});
};

var layer;
$(function () {
    var defaultColunms = JsAward.initColumn();
    var table = new BSTable(JsAward.id, "/jsAward/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(JsAward.formParams());
    JsAward.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
