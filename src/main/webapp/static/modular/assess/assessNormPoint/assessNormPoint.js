/**
 * 考核分管理初始化
 */
var AssessNormPoint = {
    id: "AssessNormPointTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AssessNormPoint.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '职工编号', field:'expand.account', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职工姓名', field:'expand.name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '部门', field:'expand.deptName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年度', field:'year', visible: true, align: 'center', valign: 'middle'}
       ,{title: '校级分', field:'expand.main', visible: true, align: 'center', valign: 'middle'}
       ,{title: '院级分', field:'expand.college', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '教学督导校级分', field:'jxddMain', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '教学督导院级分', field:'jxddCollege', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '综治平安校级分', field:'zzpaMain', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '综治平安院级分', field:'zzpaColleage', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '师德师风校级分', field:'sdsfMain', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '师德师风院级分', field:'sdsfCollege', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '组织纪律校级分', field:'zzjlMain', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '组织纪律院级分', field:'zzjlColleage', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '党风党纪校级分', field:'dfdjMain', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '党风党纪院级分', field:'dfdjCollege', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '意识形态校级分', field:'ysxtMain', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '意识形态院级分', field:'ysxtCollege', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '劳动纪律校级分', field:'ldjlMain', visible: true, align: 'center', valign: 'middle'}
       // ,{title: '劳动纪律院级分', field:'ldjlCollege', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AssessNormPoint.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AssessNormPoint.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
AssessNormPoint.formParams = function() {
    var queryData = {};
    queryData['userId'] = $("#userId").val();
    queryData['year'] = $("#year").val();
    queryData['deptId'] = $("#deptId").val();
    queryData["expand['account']"] = $("#account").val();
    queryData["expand['name']"] = $("#name").val();
    queryData["expand['type']"] = $("#type").val();
    return queryData;
};


/**
 * 点击添加考核分
 */
AssessNormPoint.openAddAssessNormPoint = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加考核分',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/assessNormPoint/assessNormPoint_add'
    });
};

/**
 * 打开查看考核分详情
 */
AssessNormPoint.openAssessNormPointDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '考核分详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/assessNormPoint/assessNormPoint_update/' + AssessNormPoint.seItem.id
        });
    }
};

/**
 * 打开查看考核分详情
 */
AssessNormPoint.openDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '考核项详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/normalAssess/?type=' + $("#type").val()+"&year="+this.seItem.year+
                "&expand['account']="+this.seItem.expand.account
        });
        layer.full(this.layerIndex);
    }
};

/**
 * 删除考核分
 */
AssessNormPoint.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除考核分", function () {
            var ajax = new $ax(Feng.ctxPath + "/assessNormPoint/delete", function (data) {
                Feng.success("删除成功!");
                AssessNormPoint.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("assessNormPointId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询考核分列表
 */
AssessNormPoint.search = function () {
    AssessNormPoint.table.refresh({query: AssessNormPoint.formParams()});
};

var layer;
$(function () {
    var defaultColunms = AssessNormPoint.initColumn();
    var table = new BSTable(AssessNormPoint.id, "/assessNormPoint/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(AssessNormPoint.formParams());
    AssessNormPoint.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
