/**
 * 打卡签到管理管理初始化
 */
var SignInLog = {
    id: "SignInLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SignInLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '用户ID', field:'userId', visible: true, align: 'center', valign: 'middle'}
       ,{title: '位置信息', field:'location', visible: true, align: 'center', valign: 'middle'}
       ,{title: '打卡时间', field:'createTime', visible: true, align: 'center', valign: 'middle'}
       ,{title: '打卡类型', field:'type', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.typeDict;}}
    ];
};

/**
 * 检查是否选中
 */
SignInLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SignInLog.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SignInLog.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加打卡签到管理
 */
SignInLog.openAddSignInLog = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加打卡签到管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/signInLog/signInLog_add'
    });
};

/**
 * 打开查看打卡签到管理详情
 */
SignInLog.openSignInLogDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '打卡签到管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/signInLog/signInLog_update/' + SignInLog.seItem.id
        });
    }
};

/**
 * 删除打卡签到管理
 */
SignInLog.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除打卡签到管理", function () {
            var ajax = new $ax(Feng.ctxPath + "/signInLog/delete", function (data) {
                Feng.success("删除成功!");
                SignInLog.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("signInLogId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询打卡签到管理列表
 */
SignInLog.search = function () {
    SignInLog.table.refresh({query: SignInLog.formParams()});
};

var layer;
$(function () {
    var defaultColunms = SignInLog.initColumn();
    var table = new BSTable(SignInLog.id, "/signInLog/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SignInLog.formParams());
    SignInLog.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
