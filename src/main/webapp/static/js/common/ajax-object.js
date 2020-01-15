(function () {
	var $ax = function (url, success, error) {
		this.url = url;
		this.type = "post";
		this.data = {};
		this.dataType = "json";
		this.async = false;
		this.success = success;
		this.error = error;
        this.contentType = "application/x-www-form-urlencoded;";
	};
	
	$ax.prototype = {
		start : function () {	
			var me = this;
			
			if (this.url.indexOf("?") == -1) {
				this.url = this.url + "?jstime=" + new Date().getTime();
			} else {
				this.url = this.url + "&jstime=" + new Date().getTime();
			}
			
			$.ajax({
		        type: this.type,
		        url: this.url,
		        dataType: this.dataType,
		        async: this.async,
		        data: this.data,
                contentType:this.contentType,
				beforeSend: function(data) {
					
				},
		        success: function(data) {
		        	me.success(data);
		        },
		        error: function(data) {
		        	me.error(data);
		        }
		    });
		}, 
		
		set : function (key, value) {
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
		setFormData:function(formId)
        {
            var o = {};
            var a = $("#"+formId).serializeArray();
            $.each(a, function() {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            this.data = o ;
        },
		setData : function(data){
			this.data = data;
			return this;
		},
        setContentType : function(contentType){
			this.contentType = contentType;
			return this;
		},
		
		clear : function () {
			this.data = {};
			return this;
		}
	};
	
	window.$ax = $ax;
	
} ());