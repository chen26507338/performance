/**
 * 广告管理管理初始化
 */
var Banner = {
    id: "BannerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Banner.initColumn = function () {
    return [
        {field: 'selectItem', radio: true}
       ,{title: '名称', field: 'bannerName', visible: true, align: 'center', valign: 'middle'}
       ,{title: '图片', field: 'bannerImgurl', visible: true, align: 'center', valign: 'middle',width:100,
            formatter: function(value,row,index){
            var src;
            if (value.indexOf("http") != -1) {
               src = value;
            }else{
               src = Feng.ctxPath + '/kaptcha/' + value;
            }
            return '<img src='+src+' class="img-rounded"  width="60%" height="50">';}}
       ,{title: '状态', field: 'expand.statusDict', visible: true, align: 'center', valign: 'middle'}
       ,{title: '类型', field: 'bannerPlace', visible: true, align: 'center', valign: 'middle',sortable:true,
        formatter:function (value, row, index) {
            return row.expand.bannerPlaceDict;
        }}
       ,{title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle',sortable:true}
    ];
};

/**
 * 检查是否选中
 */
Banner.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Banner.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
Banner.formParams = function() {
    var queryData = {};
    queryData['bannerName'] = $("#bannerName").val();
    return queryData;
};


/**
 * 点击添加广告管理
 */
Banner.openAddBanner = function () {
    this.layerIndex = layer.open({
        type: 2,
        title: '添加广告管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/banner/banner_add'
    });
};

/**
 * 打开查看广告管理详情
 */
Banner.openBannerDetail = function () {
    if (this.check()) {
        this.layerIndex = layer.open({
            type: 2,
            title: '广告管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/banner/banner_update/' + Banner.seItem.id
        });
    }
};

/**
 * 删除广告管理
 */
Banner.delete = function () {
    if (this.check()) {
        var that = this;
        Feng.confirm("是否要删除广告管理", function () {
            var ajax = new $ax(Feng.ctxPath + "/banner/delete", function (data) {
                Feng.success("删除成功!");
                Banner.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("bannerId",that.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询广告管理列表
 */
Banner.search = function () {
    Banner.table.refresh({query: Banner.formParams()});
};

var layer;
$(function () {
    var defaultColunms = Banner.initColumn();
    var table = new BSTable(Banner.id, "/banner/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Banner.formParams());
    table.setSortName("bannerPlace");
    table.setSortOrder("asc");
    Banner.table = table.init();

    layui.use('layer', function(){
        layer = layui.layer;
    });
    layui.use('laydate', function(){
        laydate = layui.laydate;
    });
});
