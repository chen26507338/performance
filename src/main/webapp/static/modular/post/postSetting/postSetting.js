/**
 * 职务设置管理初始化
 */
var PostSetting = {
    id: "PostSettingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PostSetting.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '领导科室', field:'ldks', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职级', field:'zj', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职务', field:'zw', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PostSetting.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PostSetting.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
PostSetting.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加职务设置
 */
PostSetting.openAddPostSetting = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加职务设置',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/postSetting/postSetting_add'
    });
};

/**
 * 打开查看职务设置详情
 */
PostSetting.openPostSettingDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '职务设置详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/postSetting/postSetting_update/' + PostSetting.seItem.id
        });
    }
};

/**
 * 删除职务设置
 */
PostSetting.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除职务设置", function () {
            var ajax = new $ax(Feng.ctxPath + "/postSetting/delete", function (data) {
                Feng.success("删除成功!");
                PostSetting.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("postSettingId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询职务设置列表
 */
PostSetting.search = function () {
    PostSetting.table.refresh({query: PostSetting.formParams()});
};

var layer;
$(function () {
    var defaultColunms = PostSetting.initColumn();
    var table = new BSTable(PostSetting.id, "/postSetting/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(PostSetting.formParams());
    PostSetting.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
