/**
 * 辅导员工作考核管理初始化
 */
var FdygzAssess = {
    id: "FdygzAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
FdygzAssess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '考核内容', field:'content', visible: true, align: 'center', valign: 'middle'}
       ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核年度', field:'year', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核分值', field:'normPoint', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
FdygzAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        FdygzAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
FdygzAssess.formParams = function() {
    var queryData = {};
    queryData['expand["user"]'] = $("#user").val();
    return queryData;
};


/**
 * 点击添加辅导员工作考核
 */
FdygzAssess.openAddFdygzAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加辅导员工作考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/fdygzAssess/fdygzAssess_add'
    });
};

/**
 * 打开查看辅导员工作考核详情
 */
FdygzAssess.openFdygzAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '辅导员工作考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/fdygzAssess/fdygzAssess_update/' + FdygzAssess.seItem.id
        });
    }
};

/**
 * 删除辅导员工作考核
 */
FdygzAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除辅导员工作考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/fdygzAssess/delete", function (data) {
                Feng.success("删除成功!");
                FdygzAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("fdygzAssessId",that.seItem.id);
            ajax.start();
        });
    }
};


/**
 * 点击添加学生工作考核
 */
FdygzAssess.apply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/fdygzAssess/fdygzAssess_apply'
    });
};

/**
 * 查询辅导员工作考核列表
 */
FdygzAssess.search = function () {
    FdygzAssess.table.refresh({query: FdygzAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = FdygzAssess.initColumn();
    var table = new BSTable(FdygzAssess.id, "/fdygzAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(FdygzAssess.formParams());
    FdygzAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
