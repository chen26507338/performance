/**
 * 初始化字典详情对话框
 */
var DictInfoDlg = {
    count: $("#itemSize").val(),
    dictName: '',			//字典的名称
    mutiString: '',		//拼接字符串内容(拼接字典条目)
    itemTemplate: $("#itemTemplate").html()
};

/**
 * item获取新的id
 */
DictInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "dictItem" + this.count;
};

/**
 * 关闭此对话框
 */
DictInfoDlg.close = function () {
    location.href = "/code/interface";
};

var parameterIndex = 0;

/**
 * 添加条目
 */
DictInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#itemType").attr("name","parameters["+parameterIndex+"].itemType");
    $("#itemType").attr("id","itemType["+parameterIndex+"]");
    $("#itemName").attr("name","parameters["+parameterIndex+"].itemName");
    $("#itemName").attr("id","itemName["+parameterIndex+"]");
    $("#required").attr("name","parameters["+parameterIndex+"].required");
    $("#required").attr("id","required["+parameterIndex+"]");
    $("#parameterName").attr("name","parameters["+parameterIndex+"].parameterName");
    $("#parameterName").attr("id","parameterName["+parameterIndex+"]");
    parameterIndex++;
};

/**
 * 删除item
 */
DictInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

/**
 * 清除为空的item Dom
 */
DictInfoDlg.clearNullDom = function(){
    $("[name='dictItem']").each(function(){
        var num = $(this).find("[name='itemNum']").val();
        var name = $(this).find("[name='itemName']").val();
        if(num == '' || name == ''){
            $(this).remove();
        }
    });
};

/**
 * 收集添加字典的数据
 */
DictInfoDlg.collectData = function () {
    this.clearNullDom();
    var mutiString = "";
    $("[name='dictItem']").each(function(){
        var num = $(this).find("[name='itemNum']").val();
        var name = $(this).find("[name='itemName']").val();
        mutiString = mutiString + (num + ":" + name + ";");
    });
    this.dictName = $("#dictName").val();
    this.mutiString = mutiString;
};


/**
 * 提交添加字典
 */
DictInfoDlg.addSubmit = function () {
    var data = $("#interfaceGenForm").serialize();

    var index = layer.open({
        type: 2,
        title: '生成业务表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/code/interface/gen?' + data
    });
    layer.full(index);

};

/**
 * 提交修改
 */
DictInfoDlg.editSubmit = function () {
    this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/dict/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Dict.table.refresh();
        DictInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set('dictId',$("#dictId").val());
    ajax.set('dictName',this.dictName);
    ajax.set('dictValues',this.mutiString);
    ajax.start();
};

$(function () {
    layui.use('layer', function(){
        layer = layui.layer;
    });
});
