/**
 * 专业建设项目成员管理初始化
 */
var MajorBuildMember = {
    id: "MajorBuildMemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MajorBuildMember.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
    ];
};

/**
 * 检查是否选中
 */
MajorBuildMember.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MajorBuildMember.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
MajorBuildMember.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加专业建设项目成员
 */
MajorBuildMember.openAddMajorBuildMember = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加专业建设项目成员',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/MajorBuildMember/MajorBuildMember_add'
    });
};

/**
 * 打开查看专业建设项目成员详情
 */
MajorBuildMember.openMajorBuildMemberDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '专业建设项目成员详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/MajorBuildMember/MajorBuildMember_update/' + MajorBuildMember.seItem.id
        });
    }
};

/**
 * 删除专业建设项目成员
 */
MajorBuildMember.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除专业建设项目成员", function () {
            var ajax = new $ax(Feng.ctxPath + "/MajorBuildMember/delete", function (data) {
                Feng.success("删除成功!");
                MajorBuildMember.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("MajorBuildMemberId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询专业建设项目成员列表
 */
MajorBuildMember.search = function () {
    MajorBuildMember.table.refresh({query: MajorBuildMember.formParams()});
};

var layer;
$(function () {
    var defaultColunms = MajorBuildMember.initColumn();
    var table = new BSTable(MajorBuildMember.id, "/MajorBuildMember/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(MajorBuildMember.formParams());
    MajorBuildMember.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
