/**
 * 人员配置考核管理初始化
 */
var RypzAssess = {
    id: "RypzAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
RypzAssess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核项目', field:'assessName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校积分', field:'mainPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年度', field:'year', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
RypzAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        RypzAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
RypzAssess.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加人员配置考核
 */
RypzAssess.openAddRypzAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加人员配置考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/rypzAssess/rypzAssess_add'
    });
};

/**
 * 打开查看人员配置考核详情
 */
RypzAssess.openRypzAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '人员配置考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/rypzAssess/rypzAssess_update/' + RypzAssess.seItem.id
        });
    }
};


/**
 * 点击导入考核指标库
 */
RypzAssess.openImportAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/rypzAssess/rypzAssess_import'
    });
};

/**
 * 删除人员配置考核
 */
RypzAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除人员配置考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/rypzAssess/delete", function (data) {
                Feng.success("删除成功!");
                RypzAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("rypzAssessId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询人员配置考核列表
 */
RypzAssess.search = function () {
    RypzAssess.table.refresh({query: RypzAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = RypzAssess.initColumn();
    var table = new BSTable(RypzAssess.id, "/rypzAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(RypzAssess.formParams());
    RypzAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
