/**
 * 初始化机构职务配置详情对话框
 */
var DeptPostInfoDlg = {
    deptPostInfoData : {},
    validateFields:{
    }
};

/**
 * 清除数据
 */
DeptPostInfoDlg.clearData = function() {
    this.deptPostInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptPostInfoDlg.set = function(key, val) {
    this.deptPostInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
DeptPostInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
DeptPostInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
DeptPostInfoDlg.collectData = function() {
    this
    .set('id')
    .set('deptId')
    .set('postId')
    .set('userId')
    .set('isDb')
    .set('isStar')
    ;
};

/**
 * 验证数据是否为空
 */
DeptPostInfoDlg.validate = function () {
    $('#DeptPostForm').data("bootstrapValidator").resetForm();
    $('#DeptPostForm').bootstrapValidator('validate');
    return $("#DeptPostForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
DeptPostInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deptPost/add", function(data){
        Feng.success("添加成功!");
        window.parent.DeptPost.table.refresh();
        DeptPostInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptPostInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
DeptPostInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deptPost/update", function(data){
        Feng.success("修改成功!");
        window.parent.DeptPost.table.refresh();
        DeptPostInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptPostInfoData);
    ajax.start();
};


/**
 * 提交导入
 */
DeptPostInfoDlg.importSubmit = function() {

    this.clearData();
    this.collectData();
    //
    // if (!this.validate()) {
    //     return;
    // }
    //
    // //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deptPost/import", function(data){
        Feng.success("导入成功!");
        window.parent.DeptPost.table.refresh();
        DeptPostInfoDlg.close();
    },function(data){
        Feng.error("导入失败!" + data.responseJSON.message + "!");
    });
    this.deptPostInfoData['expand["fileName"]'] = $("#fileName").val();
    ajax.set(this.deptPostInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("DeptPostForm", DeptPostInfoDlg.validateFields);

    $('#deptId').change(function(){
        //此处写状态改变要实现的功能
        // var s=$('#deptId').children('option:selected').val();
        var s=$('#deptId').val();
        $.post(Feng.ctxPath + "/mgr/dept/list/noPage", {deptId: s}, function (data) {
            var record = data;
            var options = '';
            for (var item in record) {
                options += '<option value="'+record[item].id+'">'+record[item].name
                    +' '+record[item].account+'</option>'
            }
            $('#userId').html(options);
        });
    });
});
