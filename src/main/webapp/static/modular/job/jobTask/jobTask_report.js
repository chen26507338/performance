/**
 * 初始化工作任务详情对话框
 */
var JobTaskInfoDlg = {
    jobTaskInfoData : {},
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
                },
                numeric:{
                    message:"需输入数字"
                }
            }
        }
        ,point:{
            validators:{
                notEmpty:{
                    message: "得分不能为空"
                },
                numeric:{
                    message:"需输入数字"
                }
            }
        }
        ,duties:{
            validators:{
                notEmpty:{
                    message: "职责不能为空"
                },
            }
        }
        ,des:{
            validators:{
                notEmpty:{
                    message: "任务描述不能为空"
                },
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
JobTaskInfoDlg.addReport = function(pass) {
    var that = this;
    Feng.confirm("是否提交汇报申请", function () {
        that.clearData();
        that.collectData();

        if (!that.validate()) {
            return;
        }

        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/jobTask/reportAdd", function(data){
            Feng.success("提交成功!");
            // window.parent.JobTask.table.refresh();
            // JobTaskInfoDlg.close();
        },function(data){
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        ajax.set(that.jobTaskInfoData);
        ajax.start();
    });
};

/**
 * 提交修改
 */
JobTaskInfoDlg.editSubmit = function(pass) {

    var that = this;
    Feng.confirm("确认提交", function () {
        that.clearData();
        that.collectData();

        if (!that.validate()) {
            return;
        }

        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/jobTask/handleReport", function(data){
            Feng.success("办理成功!");
            window.parent.ActTodoTask.table.refresh();
            JobTaskInfoDlg.close();
        },function(data){
            Feng.error("办理失败!" + data.responseJSON.message + "!");
        });

        that.jobTaskInfoData['expand["pass"]'] = pass;
        that.jobTaskInfoData['act.taskId'] = $("#taskId").val();
        that.jobTaskInfoData['act.procInsId'] = $("#procInsId").val();
        that.jobTaskInfoData['act.taskDefKey'] = $("#taskDefKey").val();
        that.jobTaskInfoData['expand["comment"]'] = $("#comment").val();
        ajax.set(that.jobTaskInfoData);
        ajax.start();
    });

};
var duties;
$(function() {
    Feng.initValidator("JobTaskForm", JobTaskInfoDlg.validateFields);
    $('#comment').attr('placeholder',"同意");

    var s = $('#userId').val();
    if ($("#dutiesId").length) {
        $.post(Feng.ctxPath + "/jobDuties/list/noPage", {id: s}, function (data) {
            duties = data;
            var options = '';
            for (var item in duties) {
                options += '<option value="'+duties[item].id+'">'+duties[item].des+'</option>'
            }
            var procId = $("#procInsId").val();
            if (!procId) {
                $("#point").val(duties[0].point);
                $("#des").val(duties[0].des);
                $("#duties").val(duties[0].des);
            }
            $('#dutiesId').html(options);
        });

        $('#dutiesId').change(function(){
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
