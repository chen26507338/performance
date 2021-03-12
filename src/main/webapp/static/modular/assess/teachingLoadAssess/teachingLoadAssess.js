/**
 * 教学考核管理初始化
 */
var TeachingLoadAssess = {
    id: "TeachingLoadAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TeachingLoadAssess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
        ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
        , {title: '课时数', field: 'courseTimes', visible: true, align: 'center', valign: 'middle'}
        , {title: '课程类型', field: 'courseType', visible: true, align: 'center', valign: 'middle'}
        , {title: '考核结果', field: 'result', visible: true, align: 'center', valign: 'middle'}
        , {title: '考核年度', field: 'year', visible: true, align: 'center', valign: 'middle'}
        , {title: '考核系数', field: 'coePoint', visible: true, align: 'center', valign: 'middle'}
        , {title: '校级指标分', field: 'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
        , {title: '院级浮动值', field: 'collegeNormPoint', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TeachingLoadAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        TeachingLoadAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
TeachingLoadAssess.formParams = function () {
    var queryData = {};
    queryData['expand["user"]'] = $("#user").val();
    return queryData;
};

/**
 * 点击导入考核指标库
 */
TeachingLoadAssess.openImportAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/teachingLoadAssess/teachingLoadAssess_import'
    });
};


/**
 * 点击添加教学考核
 */
TeachingLoadAssess.openAddTeachingLoadAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加教学考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/teachingLoadAssess/teachingLoadAssess_add'
    });
};

/**
 * 打开查看教学考核详情
 */
TeachingLoadAssess.openTeachingLoadAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '教学考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/teachingLoadAssess/teachingLoadAssess_update/' + TeachingLoadAssess.seItem.id
        });
    }
};

/**
 * 删除教学考核
 */
TeachingLoadAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除教学考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/teachingLoadAssess/delete", function (data) {
                Feng.success("删除成功!");
                TeachingLoadAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("teachingLoadAssessId", that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 开启教学考核
 */
TeachingLoadAssess.startTeachingLoadAssess = function () {
    var that = this;
    Feng.confirm("是否要开启教学考核", function () {
        var ajax = new $ax(Feng.ctxPath + "/teachingLoadAssess/act/apply", function (data) {
            Feng.success("开启成功!");
            TeachingLoadAssess.table.refresh();
        }, function (data) {
            Feng.error("开启失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
    });
};

/**
 * 查询教学考核列表
 */
TeachingLoadAssess.search = function () {
    TeachingLoadAssess.table.refresh({query: TeachingLoadAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = TeachingLoadAssess.initColumn();
    var table = new BSTable(TeachingLoadAssess.id, "/teachingLoadAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(TeachingLoadAssess.formParams());
    TeachingLoadAssess.table = table.init();

    layui.use('layer', function () {
        layer = layui.layer;
    });
    layui.use('laydate', function () {
        laydate = layui.laydate;
    });
});
