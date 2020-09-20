/**
 * 其他设置管理初始化
 */
var OtherInfo = {
    id: "OtherInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OtherInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        , {title: '备注', field: 'beizhu', visible: true, align: 'center', valign: 'middle'}
        , {title: '键名', field: 'otherKey', visible: true, align: 'center', valign: 'middle'}
        , {title: '值', field: 'otherValue', visible: true, align: 'center', valign: 'middle'}
        , {title: '排序', field: 'sorts', visible: true, align: 'center', valign: 'middle', sortable: true,order:'desc'}
    ];
};

/**
 * 检查是否选中
 */
OtherInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        OtherInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
OtherInfo.formParams = function () {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加其他设置
 */
OtherInfo.openAddOtherInfo = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加其他设置',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/otherInfo/otherInfo_add'
    });
};

/**
 * 打开查看其他设置详情
 */
OtherInfo.openOtherInfoDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '其他设置详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/otherInfo/otherInfo_update/' + OtherInfo.seItem.id
        });
    }
};

/**
 * 打开查看其他设置详情
 */
OtherInfo.openOtherInfoFile = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '其他设置详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/otherInfo/otherInfo_file/' + OtherInfo.seItem.id
        });
    }
};

/**
 * 删除其他设置
 */
OtherInfo.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除其他设置", function () {
            var ajax = new $ax(Feng.ctxPath + "/otherInfo/delete", function (data) {
                Feng.success("删除成功!");
                OtherInfo.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("otherInfoId", that.seItem.id);
            ajax.set("otherKey", that.seItem.otherKey);
            ajax.set("otherValue", that.seItem.otherValue);
            ajax.set("beizhu", that.seItem.beizhu);
            ajax.start();
        });
    }
};

/**
 * 查询其他设置列表
 */
OtherInfo.search = function () {
    OtherInfo.table.refresh({query: OtherInfo.formParams()});
};

var layer;
$(function () {
    var defaultColunms = OtherInfo.initColumn();
    var table = new BSTable(OtherInfo.id, "/otherInfo/list", defaultColunms);
    table.setPaginationType("server");
    table.setSortName("sorts");
    table.setQueryParams(OtherInfo.formParams());
    OtherInfo.table = table.init();

    layui.use('layer', function () {
        layer = layui.layer;
    });
    layui.use('laydate', function () {
        laydate = layui.laydate;
    });
});
