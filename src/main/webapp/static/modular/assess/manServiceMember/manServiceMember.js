/**
 * 管理服务成员管理初始化
 */
var ManServiceMember = {
    id: "ManServiceMemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ManServiceMember.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '用户ID', field:'userId', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校级指标分', field:'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '院级浮动值', field:'collegeNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核系数', field:'coePoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年份', field:'year', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ManServiceMember.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ManServiceMember.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ManServiceMember.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加管理服务成员
 */
ManServiceMember.openAddManServiceMember = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加管理服务成员',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/manServiceMember/manServiceMember_add'
    });
};

/**
 * 打开查看管理服务成员详情
 */
ManServiceMember.openManServiceMemberDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '管理服务成员详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/manServiceMember/manServiceMember_update/' + ManServiceMember.seItem.id
        });
    }
};

/**
 * 删除管理服务成员
 */
ManServiceMember.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除管理服务成员", function () {
            var ajax = new $ax(Feng.ctxPath + "/manServiceMember/delete", function (data) {
                Feng.success("删除成功!");
                ManServiceMember.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("manServiceMemberId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询管理服务成员列表
 */
ManServiceMember.search = function () {
    ManServiceMember.table.refresh({query: ManServiceMember.formParams()});
};

var layer;
$(function () {
    var defaultColunms = ManServiceMember.initColumn();
    var table = new BSTable(ManServiceMember.id, "/manServiceMember/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ManServiceMember.formParams());
    ManServiceMember.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
