/**
 * 学历培训管理初始化
 */
var EducationExperience = {
    id: "EducationExperienceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
EducationExperience.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '入学时间', field:'enrollmentTime', visible: true, align: 'center', valign: 'middle'}
       ,{title: '毕业时间', field:'graduateTime', visible: true, align: 'center', valign: 'middle'}
       ,{title: '毕业学校', field:'school', visible: true, align: 'center', valign: 'middle'}
       ,{title: '所学专业', field:'major', visible: true, align: 'center', valign: 'middle'}
       ,{title: '学历', field:'educationBackgroundDict', visible: true, align: 'center', valign: 'middle'}
       ,{title: '学位', field:'degreeDict', visible: true, align: 'center', valign: 'middle'}
       ,{title: '学习方式', field:'learnStyleDict', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
EducationExperience.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        EducationExperience.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
EducationExperience.formParams = function() {
    var queryData = {};
    queryData['userId'] = $("#userId").val();
    queryData['status'] = $("#status").val();
    return queryData;
};


/**
 * 点击添加学历培训
 */
EducationExperience.openAddEducationExperience = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加学历培训',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/educationExperience/educationExperience_add'
    });
};
/**
 * 点击添加学历培训
 */
EducationExperience.openAddApply = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '申请修改',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/educationExperience/addApply'
    });
    layer.full(this.layerIndex);
};

/**
 * 打开查看学历培训详情
 */
EducationExperience.openEducationExperienceDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '学历培训详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/educationExperience/educationExperience_update/' + EducationExperience.seItem.id
        });
    }
};

/**
 * 删除学历培训
 */
EducationExperience.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除学历培训", function () {
            var ajax = new $ax(Feng.ctxPath + "/educationExperience/delete", function (data) {
                Feng.success("删除成功!");
                EducationExperience.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("educationExperienceId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询学历培训列表
 */
EducationExperience.search = function () {
    EducationExperience.table.refresh({query: EducationExperience.formParams()});
};

var layer;
$(function () {
    var defaultColunms = EducationExperience.initColumn();
    var table = new BSTable(EducationExperience.id, "/educationExperience/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(EducationExperience.formParams());
    EducationExperience.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
