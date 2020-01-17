/**
 * 部门管理管理初始化
 */
var Dept = {
    id: "DeptTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Dept.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '名称', field:'name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '描述', field:'des', visible: true, align: 'center', valign: 'middle'}
       ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.statusDict;}}
    ];
};

/**
 * 检查是否选中
 */
Dept.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Dept.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
Dept.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加部门管理
 */
Dept.openAddDept = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加部门管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dept/dept_add'
    });
};

/**
 * 打开查看部门管理详情
 */
Dept.openDeptDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '部门管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dept/dept_update/' + Dept.seItem.id
        });
    }
};

/**
 * 删除部门管理
 */
Dept.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除部门管理", function () {
            var ajax = new $ax(Feng.ctxPath + "/dept/delete", function (data) {
                Feng.success("删除成功!");
                Dept.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("deptId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询部门管理列表
 */
Dept.search = function () {
    Dept.table.refresh({query: Dept.formParams()});
};

var layer;
$(function () {
    var defaultColunms = Dept.initColumn();
    var table = new BSTable(Dept.id, "/dept/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Dept.formParams());
    Dept.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
