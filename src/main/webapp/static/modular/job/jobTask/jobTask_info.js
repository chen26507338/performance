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
        Feng.success("添加成功!");
        window.parent.JobTask.table.refresh();
        JobTaskInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
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

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/jobTask/update", function(data){
        Feng.success("办理成功!");
        window.parent.ActTodoTask.table.refresh();
        JobTaskInfoDlg.close();
    },function(data){
        Feng.error("办理失败!" + data.responseJSON.message + "!");
    });

    this.jobTaskInfoData['expand["pass"]'] = pass;
    this.jobTaskInfoData['act.taskId'] = $("#taskId").val();
    this.jobTaskInfoData['act.procInsId'] = $("#procInsId").val();
    this.jobTaskInfoData['act.taskDefKey'] = $("#taskDefKey").val();
    var userPoint = $("#userPoint").val();
    this.jobTaskInfoData['expand["userPoint"]'] = userPoint ? userPoint : 0;
    var applyUserPoint = $("#applyUserPoint").val();
    this.jobTaskInfoData['expand["applyUserPoint"]'] = applyUserPoint ? applyUserPoint : 0;
    var appointUserPoint = $("#appointUserPoint").val();
    this.jobTaskInfoData['expand["appointUserPoint"]'] = appointUserPoint ? appointUserPoint : 0;
    ajax.set(this.jobTaskInfoData);
    ajax.start();
};
var duties;
$(function() {
    Feng.initValidator("JobTaskForm", JobTaskInfoDlg.validateFields);

    $('#userId').change(function(){
        var s=$('#userId').val();
        $.post(Feng.ctxPath + "/jobDuties/list/noPage", {id: s}, function (data) {
            duties = data;
            var options = '';
            for (var item in duties) {
                options += '<option value="'+duties[item].id+'">'+duties[item].des+'</option>'
            }
            $("#point").val(duties[0].point);
            $("#des").val(duties[0].des);
            $('#dutiesId').html(options);
        });
    });

    $('#dutiesId').change(function(){
        var s=$('#dutiesId').val();
        for (var item in duties) {
            if (duties[item].id == s) {
                $("#point").val(duties[item].point);
                $("#des").val(duties[item].des);
                break;
            }
        }
    });
});
