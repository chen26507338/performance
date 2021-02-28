

/**
 * 检查是否选中
 */
NormalAssess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        NormalAssess.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
NormalAssess.formParams = function() {
    var queryData = {};
    queryData['createTime'] = $("#createTime").val();
    queryData['year'] = $("#year").val();
    queryData['deptId'] = $("#deptId").val();
    queryData['status'] = $("#status").val();
    queryData["expand['name']"] = $("#name").val();
    queryData["expand['account']"] = $("#account").val();
    queryData['type'] = type;
    return queryData;
};


/**
 * 点击添加考核指标库
 */
NormalAssess.openAddNormalAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加考核指标',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/normalAssess/normalAssess_add?type=' + type
    });
    layer.full(this.layerIndex);
};

/**
 * 点击导入考核指标库
 */
NormalAssess.openImportNormalAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入绩效考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/normalAssess/normalAssess_import?type=' + type
    });
};

/**
 * 点击导入考核指标库
 */
NormalAssess.openImportAssess = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '导入现有绩效考核',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/normalAssess/normal_importAssess?type=' + type
    });
};

/**
 * 打开查看考核指标库详情
 */
NormalAssess.openNormalAssessDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '考核指标库详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/normalAssess/normalAssess_update/' + NormalAssess.seItem.id
        });
    }
};

/**
 * 删除考核指标库
 */
NormalAssess.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除考核指标库", function () {
            var ajax = new $ax(Feng.ctxPath + "/normalAssess/delete", function (data) {
                Feng.success("删除成功!");
                NormalAssess.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("normalAssessId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询考核指标库列表
 */
NormalAssess.search = function () {
    NormalAssess.table.refresh({query: NormalAssess.formParams()});
};

var layer;
$(function () {
    var defaultColunms = NormalAssess.initColumn();
    var table = new BSTable(NormalAssess.id, "/normalAssess/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(NormalAssess.formParams());
    NormalAssess.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
