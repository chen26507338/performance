/**
 * 自然信息管理初始化
 */
var PersonalInfo = {
    id: "PersonalInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PersonalInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
    ];
};

/**
 * 检查是否选中
 */
PersonalInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PersonalInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
PersonalInfo.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 导出干部登记表
 */
PersonalInfo.exportCadreRegister = function () {
    window.open(Feng.ctxPath + "/personalInfo/exportCadreRegister");
};

/**
 * 导出干部登记表
 */
PersonalInfo.exportCadreAppoint = function () {
    window.open(Feng.ctxPath + "/personalInfo/exportAppoint");
};

/**
 * 点击添加自然信息
 */
PersonalInfo.openAddPersonalInfo = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请修改',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/personalInfo/personalInfo_add'
    });
    layer.full(this.layerIndex);
};

/**
 * 打开查看自然信息详情
 */
PersonalInfo.openPersonalInfoDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '自然信息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/personalInfo/personalInfo_update/' + PersonalInfo.seItem.id
        });
    }
};

/**
 * 删除自然信息
 */
PersonalInfo.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除自然信息", function () {
            var ajax = new $ax(Feng.ctxPath + "/personalInfo/delete", function (data) {
                Feng.success("删除成功!");
                PersonalInfo.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("personalInfoId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询自然信息列表
 */
PersonalInfo.search = function () {
    PersonalInfo.table.refresh({query: PersonalInfo.formParams()});
};

var layer;
$(function () {
    var defaultColunms = PersonalInfo.initColumn();
    var table = new BSTable(PersonalInfo.id, "/personalInfo/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(PersonalInfo.formParams());
    PersonalInfo.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
