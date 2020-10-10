/**
 * 实训绩效考核管理初始化
 */
var SxjxAssess = {
    id: "SxjxAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SxjxAssess.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.statusDict;}}
       ,{title: '考核内容', field:'content', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核问题图片', field: 'problemUrl', visible: true, align: 'center', valign: 'middle',width:100,
            formatter: function(value,row,index){
            var src;
            if (value.indexOf("http") != -1) {
               src = value;
            }else{
               src = Feng.ctxPath + '/kaptcha/' + value;
            }
            return '<img src='+src+' class="img-rounded"  width="60%" height="50">';}}
       ,{title: '整改内容', field:'result', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校级指标分', field:'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '院级浮动值', field:'collegeNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '整改结果图片', field: 'resultUrl', visible: true, align: 'center', valign: 'middle',width:100,
            formatter: function(value,row,index){
            var src;
            if (value.indexOf("http") != -1) {
               src = value;
            }else{
               src = Feng.ctxPath + '/kaptcha/' + value;
            }
            return '<img src='+src+' class="img-rounded"  width="60%" height="50">';}}
       ,{title: '考核系数', field:'coePoint', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SxjxAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SxjxAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
SxjxAssess.formParams = function() {
    var queryData = {};
    return queryData;
};


/**
 * 点击添加实训绩效考核
 */
SxjxAssess.openAddSxjxAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加实训绩效考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sxjxAssess/sxjxAssess_add'
    });
};

/**
 * 打开查看实训绩效考核详情
 */
SxjxAssess.openSxjxAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '实训绩效考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/sxjxAssess/sxjxAssess_update/' + SxjxAssess.seItem.id
        });
    }
};

/**
 * 删除实训绩效考核
 */
SxjxAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除实训绩效考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/sxjxAssess/delete", function (data) {
                Feng.success("删除成功!");
                SxjxAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("sxjxAssessId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询实训绩效考核列表
 */
SxjxAssess.search = function () {
    SxjxAssess.table.refresh({query: SxjxAssess.formParams()});
};


/**
 * 点击添加学生工作考核
 */
SxjxAssess.apply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sxjxAssess/sxjxAssess_apply'
    });
};

/**
 * 点击添加学生工作考核
 */
SxjxAssess.allocation = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '分配分数',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sxjxAssess/sxjxAssess_allocation'
    });
    layer.full(this.layerIndex);
};

var layer;
$(function () {
    var defaultColunms = SxjxAssess.initColumn();
    var table = new BSTable(SxjxAssess.id, "/sxjxAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SxjxAssess.formParams());
    SxjxAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
