/**
 * 专项工作考核管理初始化
 */
var SpecialAssessMember = {
    id: "SpecialAssessMemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SpecialAssessMember.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '用户id', field:'userId', visible: true, align: 'center', valign: 'middle'}
       ,{title: '专项项目id', field:'saId', visible: true, align: 'center', valign: 'middle'}
       ,{title: '积分', field:'point', visible: true, align: 'center', valign: 'middle'}
       ,{title: '薪酬', field:'money', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SpecialAssessMember.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SpecialAssessMember.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SpecialAssessMember.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加专项工作考核
 */
SpecialAssessMember.openAddSpecialAssessMember = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加专项工作考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/specialAssessMember/specialAssessMember_add'
    });
};

/**
 * 打开查看专项工作考核详情
 */
SpecialAssessMember.openSpecialAssessMemberDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '专项工作考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/specialAssessMember/specialAssessMember_update/' + SpecialAssessMember.seItem.id
        });
    }
};

/**
 * 删除专项工作考核
 */
SpecialAssessMember.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除专项工作考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/specialAssessMember/delete", function (data) {
                Feng.success("删除成功!");
                SpecialAssessMember.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("specialAssessMemberId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询专项工作考核列表
 */
SpecialAssessMember.search = function () {
    SpecialAssessMember.table.refresh({query: SpecialAssessMember.formParams()});
};

var layer;
$(function () {
    var defaultColunms = SpecialAssessMember.initColumn();
    var table = new BSTable(SpecialAssessMember.id, "/specialAssessMember/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SpecialAssessMember.formParams());
    SpecialAssessMember.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
