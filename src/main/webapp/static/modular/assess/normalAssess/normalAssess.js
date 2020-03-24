/**
 * 考核指标库管理初始化
 */
var NormalAssess = {
    id: "NormalAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
NormalAssess.initColumn = function () {
    return [
        {field: 'selectItem', check: true}
        ,{title: '职工编号', field:'expand.account', visible: true, align: 'center', valign: 'middle'}
        ,{title: '职工姓名', field:'expand.userName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '部门', field:'expand.deptName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '指标', field:'expand.normName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核系数', field:'coePoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校级标准分', field:'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '院级浮动值', field:'collegeNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核结果', field:'result', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校级分', field:'expand.mainPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '院级分', field:'expand.collegePoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年度', field:'year', visible: true, align: 'center', valign: 'middle'}
       ,{title: '创建时间', field:'createTime', visible: true, align: 'center', valign: 'middle'}
       ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.statusDict;}}
    ];
};

/**
 * 检查是否选中
 */
NormalAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        NormalAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
NormalAssess.formParams = function() {
    var queryData = {};
    queryData['createTime'] = $("#createTime").val();
    queryData['year'] = $("#year").val();
    queryData['deptId'] = $("#deptId").val();
    queryData['status'] = $("#status").val();
    queryData["expand['name']"] = $("#name").val();
    queryData["expand['account']"] = $("#account").val();
    queryData['type'] = type;
    return queryData;
};


/**
 * 点击添加考核指标库
 */
NormalAssess.openAddNormalAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加考核指标库',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/normalAssess/normalAssess_add?type=' + type
    });
    layer.full(this.layerIndex);
};

/**
 * 点击导入考核指标库
 */
NormalAssess.openImportNormalAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加考核指标库',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/normalAssess/normalAssess_import?type=' + type
    });
};

/**
 * 打开查看考核指标库详情
 */
NormalAssess.openNormalAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '考核指标库详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/normalAssess/normalAssess_update/' + NormalAssess.seItem.id
        });
    }
};

/**
 * 删除考核指标库
 */
NormalAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除考核指标库", function () {
            var ajax = new $ax(Feng.ctxPath + "/normalAssess/delete", function (data) {
                Feng.success("删除成功!");
                NormalAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("normalAssessId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询考核指标库列表
 */
NormalAssess.search = function () {
    NormalAssess.table.refresh({query: NormalAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = NormalAssess.initColumn();
    var table = new BSTable(NormalAssess.id, "/normalAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(NormalAssess.formParams());
    NormalAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
