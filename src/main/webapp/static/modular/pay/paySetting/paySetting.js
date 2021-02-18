/**
 * 薪酬设置管理初始化
 */
var PaySetting = {
    id: "PaySettingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PaySetting.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '名称', field:'name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '薪酬', field:'money', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PaySetting.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PaySetting.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
PaySetting.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加薪酬设置
 */
PaySetting.openAddPaySetting = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加薪酬设置',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/paySetting/paySetting_add'
    });
};

/**
 * 打开查看薪酬设置详情
 */
PaySetting.openPaySettingDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '薪酬设置详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/paySetting/paySetting_update/' + PaySetting.seItem.id
        });
    }
};

/**
 * 删除薪酬设置
 */
PaySetting.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除薪酬设置", function () {
            var ajax = new $ax(Feng.ctxPath + "/paySetting/delete", function (data) {
                Feng.success("删除成功!");
                PaySetting.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("paySettingId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询薪酬设置列表
 */
PaySetting.search = function () {
    PaySetting.table.refresh({query: PaySetting.formParams()});
};

var layer;
$(function () {
    var defaultColunms = PaySetting.initColumn();
    var table = new BSTable(PaySetting.id, "/paySetting/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(PaySetting.formParams());
    PaySetting.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
