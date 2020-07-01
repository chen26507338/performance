/**
 * 考核奖惩管理初始化
 */
var RewardsPunishment = {
    id: "RewardsPunishmentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
RewardsPunishment.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '奖或惩', field:'content', visible: true, align: 'center', valign: 'middle'}
       ,{title: '时间', field:'time', visible: true, align: 'center', valign: 'middle'}
       ,{title: '种类', field:'typeDict', visible: true, align: 'center', valign: 'middle'}
       ,{title: '名称', field:'name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '批准依据（文号）', field:'referenceNumber', visible: true, align: 'center', valign: 'middle'}
       ,{title: '批准单位', field:'approvedBy', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
RewardsPunishment.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        RewardsPunishment.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
RewardsPunishment.formParams = function() {
    var queryData = {};
    queryData['userId'] = $("#userId").val();
    queryData['status'] = $("#status").val();
    return queryData;
};
/**
 * 点击申请修改
 */
RewardsPunishment.openAddApply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请修改',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/rewardsPunishment/addApply'
    });
    layer.full(this.layerIndex);
};


/**
 * 点击添加考核奖惩
 */
RewardsPunishment.openAddRewardsPunishment = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加考核奖惩',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/rewardsPunishment/rewardsPunishment_add'
    });
};

/**
 * 打开查看考核奖惩详情
 */
RewardsPunishment.openRewardsPunishmentDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '考核奖惩详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/rewardsPunishment/rewardsPunishment_update/' + RewardsPunishment.seItem.id
        });
    }
};

/**
 * 删除考核奖惩
 */
RewardsPunishment.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除考核奖惩", function () {
            var ajax = new $ax(Feng.ctxPath + "/rewardsPunishment/delete", function (data) {
                Feng.success("删除成功!");
                RewardsPunishment.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("rewardsPunishmentId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询考核奖惩列表
 */
RewardsPunishment.search = function () {
    RewardsPunishment.table.refresh({query: RewardsPunishment.formParams()});
};

var layer;
$(function () {
    var defaultColunms = RewardsPunishment.initColumn();
    var table = new BSTable(RewardsPunishment.id, "/rewardsPunishment/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(RewardsPunishment.formParams());
    RewardsPunishment.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
