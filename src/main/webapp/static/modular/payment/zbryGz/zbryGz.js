/**
 * 在编人员管理初始化
 */
var ZbryGz = {
    id: "ZbryGzTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ZbryGz.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
        ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
        ,{title: '年份', field:'year', visible: true, align: 'center', valign: 'middle'}
        ,{title: '月份', field:'month', visible: true, align: 'center', valign: 'middle'}
       ,{title: '岗位工资', field:'gwgz', visible: true, align: 'center', valign: 'middle'}
       ,{title: '薪级工资', field:'xjgz', visible: true, align: 'center', valign: 'middle'}
       ,{title: '岗位津贴', field:'gwjt', visible: true, align: 'center', valign: 'middle'}
       ,{title: '生活补贴', field:'shbt', visible: true, align: 'center', valign: 'middle'}
       ,{title: '特殊补贴', field:'tsbt', visible: true, align: 'center', valign: 'middle'}
       ,{title: '提租', field:'tz', visible: true, align: 'center', valign: 'middle'}
       ,{title: '补发', field:'bf', visible: true, align: 'center', valign: 'middle'}
       ,{title: '应发数', field:'yfs', visible: true, align: 'center', valign: 'middle'}
       ,{title: '扣款', field:'kk', visible: true, align: 'center', valign: 'middle'}
       ,{title: '调整各类保险金', field:'tzglbxj', visible: true, align: 'center', valign: 'middle'}
       ,{title: '公积金', field:'gjj', visible: true, align: 'center', valign: 'middle'}
       ,{title: '医保', field:'yb', visible: true, align: 'center', valign: 'middle'}
       ,{title: '养老金', field:'ylj', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职业年金', field:'zynj', visible: true, align: 'center', valign: 'middle'}
       ,{title: '失业金', field:'syj', visible: true, align: 'center', valign: 'middle'}
       ,{title: '会员费', field:'hyf', visible: true, align: 'center', valign: 'middle'}
       ,{title: '房水费', field:'fsf', visible: true, align: 'center', valign: 'middle'}
       ,{title: '所得税', field:'sds', visible: true, align: 'center', valign: 'middle'}
       ,{title: '实发数', field:'sfs', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ZbryGz.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ZbryGz.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
ZbryGz.formParams = function() {
    var queryData = {};
    return queryData;
};

/**
 * 点击导入考核指标库
 */
ZbryGz.openImport = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/zbryGz/zbryGz_import'
    });
};


/**
 * 点击添加在编人员
 */
ZbryGz.openAddZbryGz = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加在编人员',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/zbryGz/zbryGz_add'
    });
};

/**
 * 打开查看在编人员详情
 */
ZbryGz.openZbryGzDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '在编人员详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/zbryGz/zbryGz_update/' + ZbryGz.seItem.id
        });
    }
};

/**
 * 删除在编人员
 */
ZbryGz.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除在编人员", function () {
            var ajax = new $ax(Feng.ctxPath + "/zbryGz/delete", function (data) {
                Feng.success("删除成功!");
                ZbryGz.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("zbryGzId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询在编人员列表
 */
ZbryGz.search = function () {
    ZbryGz.table.refresh({query: ZbryGz.formParams()});
};

var layer;
$(function () {
    var defaultColunms = ZbryGz.initColumn();
    var table = new BSTable(ZbryGz.id, "/zbryGz/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ZbryGz.formParams());
    ZbryGz.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
