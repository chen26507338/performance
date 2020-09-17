/**
 * 学生工作考核管理初始化
 */
var StuWork = {
    id: "StuWorkTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
StuWork.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
StuWork.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        StuWork.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
StuWork.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加学生工作考核
 */
StuWork.apply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/stuWork/stuWork_apply'
    });
};

/**
 * 点击添加学生工作考核
 */
StuWork.openAddStuWork = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加学生工作考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/stuWork/stuWork_add'
    });
};

/**
 * 打开查看学生工作考核详情
 */
StuWork.openStuWorkDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '学生工作考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/stuWork/stuWork_update/' + StuWork.seItem.id
        });
    }
};

/**
 * 删除学生工作考核
 */
StuWork.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除学生工作考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/stuWork/delete", function (data) {
                Feng.success("删除成功!");
                StuWork.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("stuWorkId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询学生工作考核列表
 */
StuWork.search = function () {
    StuWork.table.refresh({query: StuWork.formParams()});
};

var layer;
$(function () {
    var defaultColunms = StuWork.initColumn();
    var table = new BSTable(StuWork.id, "/stuWork/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(StuWork.formParams());
    StuWork.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
