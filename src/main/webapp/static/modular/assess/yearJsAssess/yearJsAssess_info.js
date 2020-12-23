/**
 * 初始化教师考核详情对话框
 */
var YearJsAssessInfoDlg = {
    yearJsAssessInfoData : {},
    validateFields:{
        level: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                }
            }
        },
        comments: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                }
            }
        },
        jyszrUser: {
            validators: {
                notEmpty: {
                    message: '请选择'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
YearJsAssessInfoDlg.clearData = function() {
    this.yearJsAssessInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
YearJsAssessInfoDlg.set = function(key, val) {
    this.yearJsAssessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
YearJsAssessInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
YearJsAssessInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
YearJsAssessInfoDlg.collectData = function() {
    this
    .set('id')
    .set('procInsId')
    .set('status')
    .set('year')
    .set('userId')
    .set('remark')
    .set('level')
    .set('jyszrUser')
    .set('zbsjUser')
    .set('deanUser')
    .set('zbzrUser')
    .set('comments')
    .set('kygz')
    .set('sysgz')
    ;
};

/**
 * 验证数据是否为空
 */
YearJsAssessInfoDlg.validate = function () {
    $('#YearJsAssessForm').data("bootstrapValidator").resetForm();
    $('#YearJsAssessForm').bootstrapValidator('validate');
    return $("#YearJsAssessForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
YearJsAssessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/yearJsAssess/add", function(data){
        Feng.success("添加成功!");
        window.parent.YearJsAssess.table.refresh();
        YearJsAssessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.yearJsAssessInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
YearJsAssessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/yearJsAssess/update", function(data){
        Feng.success("修改成功!");
        window.parent.YearJsAssess.table.refresh();
        YearJsAssessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.yearJsAssessInfoData);
    ajax.start();
};


/**
 * 提交申请
 */
YearJsAssessInfoDlg.addApply = function() {

    this.clearData();
    this.collectData();

    if (!$("#kygz").val()) {
        Feng.error("请上传考核文档");
        return;
    }

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/yearJsAssess/act/apply", function(data){
        Feng.success("申请成功!");
        window.parent.YearJsAssess.table.refresh();
        YearJsAssessInfoDlg.close();
    },function(data){
        Feng.error("申请失败!" + data.responseJSON.message + "!");
    });

    // this.yearJsAssessInfoData.kygz = ","+$("#keti").val() +","+ $("#ketiTask").val()+","+ $("#ketiFinish").val();
    // this.yearJsAssessInfoData.sysgz = ","+$("#sysgz").val() + ","+$("#sysTask").val()+ ","+$("#sysFinish").val();
    // this.yearJsAssessInfoData['expand["data"]'] = JSON.stringify(datas);
    ajax.set(this.yearJsAssessInfoData);
    ajax.start();
};


/**
 * 验收审核
 */
YearJsAssessInfoDlg.auditSubmit = function(pass) {
    var that = this;
    this.collectData();

    if (pass == 1 ) {
        var defKey = $("#taskDefKey").val();
        if (defKey == "re_submit" && !$("#kygz").val()) {
            Feng.error("请上传考核文档");
            return;
        }
        if (!this.validate()) {
            return;
        }
    }
    Feng.confirm("确认操作", function () {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/yearJsAssess/act/audit", function(data){
            Feng.success("提交成功!");
            window.parent.ActTodoTask.table.refresh();
            YearJsAssessInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        that.yearJsAssessInfoData['expand["pass"]'] = pass;
        that.yearJsAssessInfoData['expand["comment"]'] = $("#comment").val();
        // that.yearJsAssessInfoData['expand["data"]'] = JSON.stringify(datas);
        that.yearJsAssessInfoData['act.taskId'] = $("#taskId").val();
        that.yearJsAssessInfoData['act.procInsId'] = $("#procInsId").val();
        that.yearJsAssessInfoData['act.taskDefKey'] = defKey;
        that.yearJsAssessInfoData['year'] = $("#year").val();

        ajax.set(that.yearJsAssessInfoData);
        ajax.start();
    });
};

$(function() {
    Feng.initValidator("YearJsAssessForm", YearJsAssessInfoDlg.validateFields);

});
