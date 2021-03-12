/**
 * 学生工作成员管理初始化
 */
var StuWorkMember = {
    id: "StuWorkMemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
StuWorkMember.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
        ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
        ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
        ,{title: '考核项目', field:'assessName', visible: true, align: 'center', valign: 'middle'}
        ,{title: '参赛项目名称/团队名称/类别/百分率', field:'mixture', visible: true, align: 'center', valign: 'middle'}
        ,{title: '校级指标分', field:'mainNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '院级浮动值', field:'collegeNormPoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核系数', field:'coePoint', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年份', field:'year', visible: true, align: 'center', valign: 'middle'}
        ,{title: '状态', field:'status', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){return row.expand.statusDict;}}
    ];
};

/**
 * 检查是否选中
 */
StuWorkMember.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        StuWorkMember.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
StuWorkMember.formParams = function() {
    var queryData = {};
    queryData['expand["user"]'] = $("#user").val();
    return queryData;
};

/**
 * 点击导入考核指标库
 */
StuWorkMember.openImportAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/stuWorkMember/stuWorkMember_import'
    });
};


/**
 * 点击添加学生工作成员
 */
StuWorkMember.openAddStuWorkMember = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加学生工作成员',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/stuWorkMember/stuWorkMember_add'
    });
};

/**
 * 打开查看学生工作成员详情
 */
StuWorkMember.openStuWorkMemberDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '学生工作成员详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/stuWorkMember/stuWorkMember_update/' + StuWorkMember.seItem.id
        });
    }
};

/**
 * 删除学生工作成员
 */
StuWorkMember.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除学生工作成员", function () {
            var ajax = new $ax(Feng.ctxPath + "/stuWorkMember/delete", function (data) {
                Feng.success("删除成功!");
                StuWorkMember.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("stuWorkMemberId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询学生工作成员列表
 */
StuWorkMember.search = function () {
    StuWorkMember.table.refresh({query: StuWorkMember.formParams()});
};

var layer;
$(function () {
    var defaultColunms = StuWorkMember.initColumn();
    var table = new BSTable(StuWorkMember.id, "/stuWorkMember/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(StuWorkMember.formParams());
    StuWorkMember.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
