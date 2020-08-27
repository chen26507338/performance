/**
 * 专业建设考核管理初始化
 */
var MajorBuild = {
    id: "MajorBuildTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MajorBuild.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '负责人ID', field:'principalId', visible: true, align: 'center', valign: 'middle'}
       ,{title: '名称', field:'name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '立项时间', field:'time', visible: true, align: 'center', valign: 'middle'}
       ,{title: '流程实例ID', field:'procInsId', visible: true, align: 'center', valign: 'middle'}
       ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MajorBuild.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MajorBuild.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
MajorBuild.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加专业建设考核
 */
MajorBuild.openAddMajorBuild = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加专业建设考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/majorBuild/majorBuild_add'
    });
};

/**
 * 点击添加专业建设考核
 */
MajorBuild.openApplyApproval = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '立项申请',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/majorBuild/applyApproval'
    });
};

/**
 * 打开查看专业建设考核详情
 */
MajorBuild.openMajorBuildDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '专业建设考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/majorBuild/majorBuild_update/' + MajorBuild.seItem.id
        });
    }
};

/**
 * 删除专业建设考核
 */
MajorBuild.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除专业建设考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/majorBuild/delete", function (data) {
                Feng.success("删除成功!");
                MajorBuild.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("majorBuildId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询专业建设考核列表
 */
MajorBuild.search = function () {
    MajorBuild.table.refresh({query: MajorBuild.formParams()});
};

var layer;
$(function () {
    var defaultColunms = MajorBuild.initColumn();
    var table = new BSTable(MajorBuild.id, "/majorBuild/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(MajorBuild.formParams());
    MajorBuild.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
