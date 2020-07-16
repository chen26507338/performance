/**
 * 初始化工作任务详情对话框
 */
var JobTaskInfoDlg = {
    jobTaskInfoData : {},
    count: $("#itemSize").val(),
    itemTemplate: $("#itemTemplate").html(),
    validateFields:{
        appointUserId:{
            validators:{
                different:{
                    field:'userId',
                    message:'不能与经办人相同'
                }
            }
        }
        ,userId:{
            validators:{
                notEmpty:{
                    message: "经办人不能为空"
                }
            }
        }
        ,userDes:{
            validators:{
                notEmpty:{
                    message: "办理结果不能为空"
                }
            }
        }
        ,applyUserDes:{
            validators:{
                notEmpty:{
                    message: "办理结果不能为空"
                }
            }
        }
        ,summary:{
            validators:{
                notEmpty:{
                    message: "汇总结果不能为空"
                }
            }
        }
        ,userPoint:{
            validators:{
                notEmpty:{
                    message: "得分不能为空"
                }
            }
        }
        ,applyUserPoint:{
            validators:{
                notEmpty:{
                    message: "得分不能为空"
                }
            }
        }
        ,appointUserPoint:{
            validators:{
                notEmpty:{
                    message: "得分不能为空"
                }
            }
        }
    }
};


/**
 * item获取新的id
 */
JobTaskInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "applyUserItem" + this.count;
};

/**
 * 添加条目
 */
JobTaskInfoDlg.addItem = function () {
    $("#dataRow").append(this.itemTemplate);
    var applyUserItem = $("#applyUserItem");
    applyUserItem.attr("id", this.newId());
};

/**
 * 删除item
 */
JobTaskInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().parent().remove();
};


/**
 * 清除数据
 */
JobTaskInfoDlg.clearData = function() {
    this.jobTaskInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
JobTaskInfoDlg.set = function(key, val) {
    this.jobTaskInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
JobTaskInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
JobTaskInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
JobTaskInfoDlg.collectData = function() {
    this
    .set('id')
    .set('dutiesId')
    .set('duties')
    .set('deptId')
    .set('userId')
    .set('appointUserId')
    .set('applyUserId')
    .set('point')
    .set('summary')
    .set('des')
    .set('userDes')
    .set('appointUserDes')
    .set('applyUserDes')
    .set('createTime')
    .set('endTime')
    ;
};

/**
 * 验证数据是否为空
 */
JobTaskInfoDlg.validate = function () {
    $('#JobTaskForm').data("bootstrapValidator").resetForm();
    $('#JobTaskForm').bootstrapValidator('validate');
    return $("#JobTaskForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
JobTaskInfoDlg.addSubmit = function(pass) {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobTask/add", function(data){
        Feng.success("下达成功!");
        window.parent.JobTask.table.refresh();
        JobTaskInfoDlg.close();
    },function(data){
        Feng.error("下达失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.jobTaskInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
JobTaskInfoDlg.editSubmit = function(pass) {


    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    var that = this;
    Feng.confirm("是否进行操作？", function () {

        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/jobTask/update", function(data){
            Feng.success("办理成功!");
            window.parent.ActTodoTask.table.refresh();
            JobTaskInfoDlg.close();
        },function(data){
            Feng.error("办理失败!" + data.responseJSON.message + "!");
        });

        var applyUserItems = $(".applyUserItem");
        if (applyUserItems.length > 0) {
            var ids = [];
            for (var i = 0; i < applyUserItems.length; i++) {
                var itemEle = $(applyUserItems[i]);
                if (itemEle) {
                    var id = itemEle.val();
                    if (!id) {
                        Feng.error("请选择协助人");
                        return;
                    }
                    if (ids.indexOf(id) > -1) {
                        Feng.error("协助人不能重复选择");
                        return;
                    }
                    ids.push(id);
                }
            }
            that.jobTaskInfoData['expand["applyUserList"]'] = ids.join(",");
        }

        var applyUserPoints = $(".applyUserPoint");
        if (applyUserPoints.length > 0) {
            for (var index = 0; index < applyUserPoints.length; index++) {
                itemEle = $(applyUserPoints[index]);
                if (itemEle) {
                    var point = itemEle.val();
                    if (!point) {
                        Feng.error("经派协作人分数不能为空");
                        return;
                    }
                    that.jobTaskInfoData['expand["' + applyUserPoints[index].name + '"]'] = point;
                }
            }
        }

        that.jobTaskInfoData['expand["pass"]'] = pass;
        that.jobTaskInfoData['act.taskId'] = $("#taskId").val();
        that.jobTaskInfoData['act.procInsId'] = $("#procInsId").val();
        that.jobTaskInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        var userPoint = $("#userPoint").val();
        that.jobTaskInfoData['expand["userPoint"]'] = userPoint ? userPoint : 0;
        // var applyUserPoint = $("#applyUserPoint").val();
        // that.jobTaskInfoData['expand["applyUserPoint"]'] = applyUserPoint ? applyUserPoint : 0;
        var appointUserPoint = $("#appointUserPoint").val();
        that.jobTaskInfoData['expand["appointUserPoint"]'] = appointUserPoint ? appointUserPoint : 0;
        ajax.set(that.jobTaskInfoData);
        ajax.start();
    });
};
var duties;
$(function() {
    Feng.initValidator("JobTaskForm", JobTaskInfoDlg.validateFields);

    if ($("#dutiesId").length) {
        $('#userId').change(function () {
            var s = $('#userId').val();
            $.post(Feng.ctxPath + "/jobDuties/list/noPage", {id: s}, function (data) {
                duties = data;
                var options = '';
                for (var item in duties) {
                    options += '<option value="' + duties[item].id + '">' + duties[item].des + '</option>'
                }
                $("#point").val(duties[0].point);
                $("#des").val(duties[0].des);
                $("#duties").val(duties[0].des);
                $('#dutiesId').html(options);
            });
        });

        $('#dutiesId').change(function () {
            var s = $('#dutiesId').val();
            for (var item in duties) {
                if (duties[item].id == s) {
                    $("#point").val(duties[item].point);
                    $("#des").val(duties[item].des);
                    $("#duties").val($(this).find("option:selected").text());
                    break;
                }
            }
        });
    }
});
