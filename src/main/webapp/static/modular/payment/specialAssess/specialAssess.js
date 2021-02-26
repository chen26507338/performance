/**
 * 专项工作奖项目列表管理初始化
 */
var SpecialAssess = {
    id: "SpecialAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SpecialAssess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        ,{title: '编号', field:'zxNo', visible: true, align: 'center', valign: 'middle'}
       ,{title: '部门', field:'expand.dept.name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '申请积分', field:'applyPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '增加积分', field:'addPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '申请参考分', field:'referencePoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '申请项目内容', field:'projectContent', visible: true, align: 'center', valign: 'middle'}
        ,{title: '是否计入', field:'isJr', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.isJrDict;}}
        ,{title: '是否记入部门优绩考核', field:'isYjkh', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.isYjkhDict;}}
    ];
};

/**
 * 检查是否选中
 */
SpecialAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SpecialAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SpecialAssess.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加专项工作奖项目列表
 */
SpecialAssess.openAddSpecialAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加专项工作奖项目列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/specialAssess/specialAssess_add'
    });
};

/**
 * 点击导入考核指标库
 */
SpecialAssess.openImportProject = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/specialAssess/specialProject_import'
    });
};


/**
 * 打开查看专项工作奖项目列表详情
 */
SpecialAssess.openSpecialAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '专项工作奖项目列表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/specialAssess/specialAssess_update/' + SpecialAssess.seItem.id
        });
    }
};

/**
 * 删除专项工作奖项目列表
 */
SpecialAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除专项工作奖项目列表", function () {
            var ajax = new $ax(Feng.ctxPath + "/specialAssess/delete", function (data) {
                Feng.success("删除成功!");
                SpecialAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("specialAssessId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询专项工作奖项目列表列表
 */
SpecialAssess.search = function () {
    SpecialAssess.table.refresh({query: SpecialAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = SpecialAssess.initColumn();
    var table = new BSTable(SpecialAssess.id, "/specialAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SpecialAssess.formParams());
    SpecialAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
