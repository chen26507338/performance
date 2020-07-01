/**
 * 亲属关系管理初始化
 */
var Kinship = {
    id: "KinshipTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Kinship.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '关系', field:'relationshipDict', visible: true, align: 'center', valign: 'middle'}
       ,{title: '姓名', field:'name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '生日', field:'birthday', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年龄', field:'age', visible: true, align: 'center', valign: 'middle'}
       ,{title: '政治面貌', field:'politicsStatusDict', visible: true, align: 'center', valign: 'middle'}
       ,{title: '工作单位及职务', field:'company', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Kinship.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Kinship.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
Kinship.formParams = function() {
    var queryData = {};
    queryData['userId'] = $("#userId").val();
    queryData['status'] = $("#status").val();
    return queryData;
};

/**
 * 点击添加学历培训
 */
Kinship.openAddApply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请修改',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/kinship/addApply'
    });
    layer.full(this.layerIndex);
};
/**
 * 点击添加亲属关系
 */
Kinship.openAddKinship = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加亲属关系',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/kinship/kinship_add'
    });
};

/**
 * 打开查看亲属关系详情
 */
Kinship.openKinshipDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '亲属关系详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/kinship/kinship_update/' + Kinship.seItem.id
        });
    }
};

/**
 * 删除亲属关系
 */
Kinship.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除亲属关系", function () {
            var ajax = new $ax(Feng.ctxPath + "/kinship/delete", function (data) {
                Feng.success("删除成功!");
                Kinship.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("kinshipId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询亲属关系列表
 */
Kinship.search = function () {
    Kinship.table.refresh({query: Kinship.formParams()});
};

var layer;
$(function () {
    var defaultColunms = Kinship.initColumn();
    var table = new BSTable(Kinship.id, "/kinship/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Kinship.formParams());
    Kinship.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
