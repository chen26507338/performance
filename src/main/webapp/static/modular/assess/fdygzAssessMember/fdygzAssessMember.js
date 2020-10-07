/**
 * 辅导员日常工作考核成员管理初始化
 */
var FdygzAssessMember = {
    id: "FdygzAssessMemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
FdygzAssessMember.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
    ];
};

/**
 * 检查是否选中
 */
FdygzAssessMember.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        FdygzAssessMember.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
FdygzAssessMember.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加辅导员日常工作考核成员
 */
FdygzAssessMember.openAddFdygzAssessMember = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加辅导员日常工作考核成员',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/fdygzAssessMember/fdygzAssessMember_add'
    });
};

/**
 * 打开查看辅导员日常工作考核成员详情
 */
FdygzAssessMember.openFdygzAssessMemberDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '辅导员日常工作考核成员详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/fdygzAssessMember/fdygzAssessMember_update/' + FdygzAssessMember.seItem.id
        });
    }
};

/**
 * 删除辅导员日常工作考核成员
 */
FdygzAssessMember.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除辅导员日常工作考核成员", function () {
            var ajax = new $ax(Feng.ctxPath + "/fdygzAssessMember/delete", function (data) {
                Feng.success("删除成功!");
                FdygzAssessMember.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("fdygzAssessMemberId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询辅导员日常工作考核成员列表
 */
FdygzAssessMember.search = function () {
    FdygzAssessMember.table.refresh({query: FdygzAssessMember.formParams()});
};

var layer;
$(function () {
    var defaultColunms = FdygzAssessMember.initColumn();
    var table = new BSTable(FdygzAssessMember.id, "/fdygzAssessMember/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(FdygzAssessMember.formParams());
    FdygzAssessMember.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
