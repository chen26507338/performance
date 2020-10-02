/**
 * 思政工作管理初始化
 */
var SzgzAssess = {
    id: "SzgzAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SzgzAssess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '职工编号', field:'expand.account', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职工姓名', field:'expand.name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核结果', field:'result', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核年度', field:'year', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校级指标分', field:'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '院级浮动值', field:'collegeNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核系数', field:'coePoint', visible: true, align: 'center', valign: 'middle'}
        ,{title: '状态', field:'expand.statusDict', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SzgzAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SzgzAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SzgzAssess.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加思政工作
 */
SzgzAssess.openAddSzgzAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加思政工作',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/szgzAssess/szgzAssess_add'
    });
};

/**
 * 打开查看思政工作详情
 */
SzgzAssess.openSzgzAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '思政工作详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/szgzAssess/szgzAssess_update/' + SzgzAssess.seItem.id
        });
    }
};

/**
 * 删除思政工作
 */
SzgzAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除思政工作", function () {
            var ajax = new $ax(Feng.ctxPath + "/szgzAssess/delete", function (data) {
                Feng.success("删除成功!");
                SzgzAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("szgzAssessId",that.seItem.id);
            ajax.start();
        });
    }
};
/**
 * 点击添加学生工作考核
 */
SzgzAssess.apply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/szgzAssess/szgzAssess_apply?type=' + type
    });
    layer.full(this.layerIndex);
};
/**
 * 查询思政工作列表
 */
SzgzAssess.search = function () {
    SzgzAssess.table.refresh({query: SzgzAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = SzgzAssess.initColumn();
    var table = new BSTable(SzgzAssess.id, "/szgzAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SzgzAssess.formParams());
    SzgzAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
