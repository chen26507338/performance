/**
 * 表彰荣誉考核管理初始化
 */
var BzryAssess = {
    id: "BzryAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BzryAssess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '荣誉级别', field:'level', visible: true, align: 'center', valign: 'middle'}
       ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle'}
       ,{title: '荣誉类型', field:'type', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核年度', field:'year', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校级指标分', field:'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核系数', field:'coePoint', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 点击添加学生工作考核
 */
BzryAssess.apply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/bzryAssess/bzryAssess_apply'
    });
    layer.full(this.layerIndex);
};
/**
 * 检查是否选中
 */
BzryAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BzryAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
BzryAssess.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加表彰荣誉考核
 */
BzryAssess.openAddBzryAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加表彰荣誉考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/bzryAssess/bzryAssess_add'
    });
};

/**
 * 打开查看表彰荣誉考核详情
 */
BzryAssess.openBzryAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '表彰荣誉考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/bzryAssess/bzryAssess_update/' + BzryAssess.seItem.id
        });
    }
};

/**
 * 删除表彰荣誉考核
 */
BzryAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除表彰荣誉考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/bzryAssess/delete", function (data) {
                Feng.success("删除成功!");
                BzryAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("bzryAssessId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询表彰荣誉考核列表
 */
BzryAssess.search = function () {
    BzryAssess.table.refresh({query: BzryAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = BzryAssess.initColumn();
    var table = new BSTable(BzryAssess.id, "/bzryAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(BzryAssess.formParams());
    BzryAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
