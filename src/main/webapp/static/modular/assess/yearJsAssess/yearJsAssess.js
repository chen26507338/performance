/**
 * 教师考核管理初始化
 */
var YearJsAssess = {
    id: "YearJsAssessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
YearJsAssess.initColumn = function () {
    return [
        {field: 'selectItem', check: true}
       ,{title: '状态', field:'expand.statusDict', visible: true, align: 'center', valign: 'middle'}
       ,{title: '年份', field:'year', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职工编号', field:'expand.user.account', visible: true, align: 'center', valign: 'middle'}
       ,{title: '职工姓名', field:'expand.user.name', visible: true, align: 'center', valign: 'middle'}
       ,{title: '考核等次', field:'level', visible: true, align: 'center', valign: 'middle'}
        ,{title: '教研室评语', field:'jyscom', visible: true, align: 'center', valign: 'middle'}
        ,{title: '书记评语', field:'sjcom', visible: true, align: 'center', valign: 'middle'}
        ,{title: '评语', field:'comments', visible: true, align: 'center', valign: 'middle'}
        ,{title: '备注', field:'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
YearJsAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        YearJsAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
YearJsAssess.formParams = function() {
    var queryData = {};
    queryData['type'] = $("#type").val();
    queryData['level'] = $("#level").val();
    queryData['expand["userInfo"]'] = $("#userInfo").val();
    return queryData;
};


/**
 * 点击添加教师考核
 */
YearJsAssess.openAddYearJsAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加教师考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/yearJsAssess/yearJsAssess_add'
    });
};

/**
 * 点击添加教师考核
 */
YearJsAssess.exportDoc = function () {
    if (this.check()) {
        window.open(Feng.ctxPath + "/yearJsAssess/downloadDoc/" + YearJsAssess.seItem.id);
    }
};

/**
 * 点击添加教师考核
 */
YearJsAssess.openAddApply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请教师考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/yearJsAssess/yearJsAssess_apply/' + $("#type").val()
    });
};

/**
 * 打开查看教师考核详情
 */
YearJsAssess.openYearJsAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '教师考核详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/yearJsAssess/yearJsAssess_update/' + YearJsAssess.seItem.id
        });
    }
};

/**
 * 删除教师考核
 */
YearJsAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除教师考核", function () {
            var ajax = new $ax(Feng.ctxPath + "/yearJsAssess/delete", function (data) {
                Feng.success("删除成功!");
                YearJsAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("yearJsAssessId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 作废考核
 */
YearJsAssess.cancellation = function () {
    if (this.check()) {
        var assesses = $('#' + this.id).bootstrapTable('getSelections');
        var ids = "";
        for (var index in assesses) {
            if (assesses[index].status == 0) {
                Feng.error("存在考核中的项目");
                return;
            }
            ids += assesses[index].id + ",";
        }

        parent.layer.confirm('是否要作废？', {
            btn: ['确定', '取消'],
            shade: false //不显示遮罩
        }, function () {
            var ajax = new $ax(Feng.ctxPath + "/yearJsAssess/delete", function (data) {
                Feng.success( "作废成功!");
                YearJsAssess.search();
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.message);
            });
            ajax.set("ids", ids);
            ajax.start();
        });
    }

};

/**
 * 作废考核
 */
YearJsAssess.audit = function () {
    if (this.check()) {
        var assesses = $('#' + this.id).bootstrapTable('getSelections');
        var ids = "";
        for (var index in assesses) {
            if (assesses[index].status == 0) {
                Feng.error("存在考核中的项目");
                return;
            }
            ids += assesses[index].id + ",";
        }

        var level;
        var choice = $("#level").val();
        if (!choice) {
            choice = "本次操作不修改评级";
        } else {
            level = choice;
            choice = "将评级修改为 " + level;
        }

        parent.layer.confirm(choice + ',是否要审核？', {
            btn: ['确定', '取消'],
            shade: false //不显示遮罩
        }, function () {
            var ajax = new $ax(Feng.ctxPath + "/yearJsAssess/update", function (data) {
                Feng.success( "审核成功!");
                YearJsAssess.search();
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.message);
            });
            ajax.set("ids", ids);
            ajax.set("level", level);
            ajax.start();
        });
    }

};

/**
 * 查询教师考核列表
 */
YearJsAssess.search = function () {
    YearJsAssess.table.refresh({query: YearJsAssess.formParams()});
};

/**
 * 查询教师考核列表
 */
YearJsAssess.exportTotal = function (type) {
    Feng.confirm("是否导出考核汇总", function () {
        window.open(Feng.ctxPath + "/yearJsAssess/exportTotal?type=" + type);
    });
};


var layer;
$(function () {
    var defaultColunms = YearJsAssess.initColumn();
    var table = new BSTable(YearJsAssess.id, "/yearJsAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(YearJsAssess.formParams());
    YearJsAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
