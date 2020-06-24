/**
 * 初始化 Tree Table 的封装
 *
 * @author cyf
 */
(function () {
    var BSTreeGrid = function (bstableId, url, columns) {
        this.btInstance = null;					//jquery和bootstrapTreeTable绑定的对象
        this.bstableId = bstableId;
        this.url = Feng.ctxPath + url;
        this.method = "post";
        this.columns = columns;
        this.data = {};// ajax的参数
        this.expandColumn = null;// 展开显示的列 
        this.id = 'id';// 选取记录返回的值
        this.code = 'code';// 用于设置父子关系
        this.parentCode = 'pcode';// 用于设置父子关系
        this.expandAll = false;// 是否默认全部展开
        this.toolbarId = bstableId + "Toolbar";
        this.height = 665;						//默认表格高度665
        this.queryParams = {}; // 向后台传递的自定义参数
    };

    BSTreeGrid.prototype = {
        /**
         * 初始化bootstrap table
         */
        init: function () {
            var tableId = this.bstableId;
            var $table = $('#'+tableId);
            var that = this;
            this.btInstance =
                $table.bootstrapTable({
                    // id: this.id,// 选取记录返回的值
                    idField: this.code,// 用于设置父子关系
                    parentIdField: this.parentCode,// 用于设置父子关系
                    rootCodeValue: this.rootCodeValue,//设置根节点code值----可指定根节点，默认为null,"",0,"0"
                    type: this.method, //请求数据的ajax类型
                    url: this.url,   //请求数据的ajax的url
                    // queryParams: this.data, //请求数据的ajax的data属性
                    queryParams: function (param) {
                        return $.extend(that.queryParams, param);
                    },
                    treeShowField: this.code,//在哪一列上面显示展开按钮,从0开始
                    striped: true,   //是否各行渐变色
                    expandAll: this.expandAll,  //是否全部展开
                    columns: this.columns,		//列数组
                    sidePagination: 'server',
                    toolbar: "#" + this.toolbarId,//顶部工具条
                    // fixedColumns: true,
                    // fixedNumber: 3,
                    showColumns: true,
                    height: this.height
                    ,onLoadSuccess: function(data) {
                        $table.treegrid({
                            initialState: 'collapsed',
                            treeColumn: that.expandColumn,
                            expanderExpandedClass: 'glyphicon glyphicon-minus',
                            expanderCollapsedClass: 'glyphicon glyphicon-plus',
                            onChange: function() {
                                $table.bootstrapTable('resetView');
                            }
                        });
                    }
                });
            return this;
        },

        /**
         * 设置在哪一列上面显示展开按钮,从0开始
         */
        setExpandColumn: function (expandColumn) {
            this.expandColumn = expandColumn;
        },
        /**
         * 设置记录返回的id值
         */
        setIdField: function (id) {
            this.id = id;
        },
        /**
         * 设置记录分级的字段
         */
        setCodeField: function (code) {
            this.code = code;
        },
        /**
         * 设置记录分级的父级字段
         */
        setParentCodeField: function (parentCode) {
            this.parentCode = parentCode;
        },
        /**
         * 设置根节点code值----可指定根节点，默认为null,"",0,"0"
         */
        setRootCodeValue: function (rootCodeValue) {
            this.rootCodeValue = rootCodeValue;
        },
        /**
         * 设置是否默认全部展开
         */
        setExpandAll: function (expandAll) {
            this.expandAll = expandAll;
        },
        /**
         * 设置表格高度
         */
        setHeight: function (height) {
            this.height = height;
        },
        /**
         * 设置ajax post请求时候附带的参数
         */
        set: function (key, value) {
            if (typeof key == "object") {
                for (var i in key) {
                    if (typeof i == "function")
                        continue;
                    this.data[i] = key[i];
                }
            } else {
                this.data[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
            }
            return this;
        },

        /**
         * 设置ajax post请求时候附带的参数
         */
        setData: function (data) {
            this.data = data;
            return this;
        },

        /**
         * 清空ajax post请求参数
         */
        clear: function () {
            this.data = {};
            return this;
        },

        /**
         * 刷新表格
         */
        refresh: function (parms) {
            if (typeof parms != "undefined") {
                // this.data = parms;
                this.btInstance.bootstrapTable('refresh', parms);
            } else {
                this.btInstance.bootstrapTable('refresh');
            }
        }
    };

    window.BSTreeGrid = BSTreeGrid;

}());