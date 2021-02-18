/**
 * 机构职务配置管理初始化
 */
var DeptPost = {
    id: "DeptPostTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DeptPost.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '部门id', field:'deptId', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职务id', field:'postId', visible: true, align: 'center', valign: 'middle'}
       ,{title: '用户id', field:'userId', visible: true, align: 'center', valign: 'middle'}
       ,{title: '是否定编', field:'isDb', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.isDbDict;}}
       ,{title: '是否星号', field:'isStar', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.isStarDict;}}
    ];
};

/**
 * 检查是否选中
 */
DeptPost.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        DeptPost.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
DeptPost.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 点击导入考核指标库
 */
DeptPost.import = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入用户',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deptPost/open_import'
    });
};

/**
 * 点击添加机构职务配置
 */
DeptPost.openAddDeptPost = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加机构职务配置',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deptPost/deptPost_add'
    });
};

/**
 * 打开查看机构职务配置详情
 */
DeptPost.openDeptPostDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '机构职务配置详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deptPost/deptPost_update/' + DeptPost.seItem.id
        });
    }
};

/**
 * 删除机构职务配置
 */
DeptPost.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除机构职务配置", function () {
            var ajax = new $ax(Feng.ctxPath + "/deptPost/delete", function (data) {
                Feng.success("删除成功!");
                DeptPost.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("deptPostId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询机构职务配置列表
 */
DeptPost.search = function () {
    DeptPost.table.refresh({query: DeptPost.formParams()});
};

var layer;
$(function () {
    var defaultColunms = DeptPost.initColumn();
    var table = new BSTable(DeptPost.id, "/deptPost/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(DeptPost.formParams());
    DeptPost.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
