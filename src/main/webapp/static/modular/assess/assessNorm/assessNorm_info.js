/**
 * 初始化 考核指标库详情对话框
 */
var AssessNormInfoDlg = {
    assessNormInfoData : {},
    validateFields:{
        code: {
            validators: {
                notEmpty: {
                    message: '代码不能为空'
                }
            }
        },
        type: {
            validators: {
                notEmpty: {
                    message: '考核项目不能为空'
                }
            }
        },
        point: {
            validators: {
                notEmpty: {
                    message: '校级指标/院级浮动值不能为空'
                }
            }
        },
        content: {
            validators: {
                notEmpty: {
                    message: '内容不能为空'
                }
            }
        },
    }
};

/**
 * 清除数据
 */
AssessNormInfoDlg.clearData = function() {
    this.assessNormInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AssessNormInfoDlg.set = function(key, val) {
    this.assessNormInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 */
AssessNormInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
AssessNormInfoDlg.close = function() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};

/**
 * 收集数据
 */
AssessNormInfoDlg.collectData = function() {
    this
    .set('id')
    .set('content')
    .set('code')
    .set('type')
    .set('deptId')
    .set('point')
    .set('des')
    ;
};

/**
 * 验证数据是否为空
 */
AssessNormInfoDlg.validate = function () {
    $('#AssessNormForm').data("bootstrapValidator").resetForm();
    $('#AssessNormForm').bootstrapValidator('validate');
    return $("#AssessNormForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
AssessNormInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/assessNorm/add", function(data){
        Feng.success("添加成功!");
        window.parent.AssessNorm.table.refresh();
        AssessNormInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.assessNormInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
AssessNormInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/assessNorm/update", function(data){
        Feng.success("修改成功!");
        window.parent.AssessNorm.table.refresh();
        AssessNormInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.assessNormInfoData);
    ajax.start();
};

$(function() {
    Feng.initValidator("AssessNormForm", AssessNormInfoDlg.validateFields);

});
