/**
 * 重写jquery.ajax方法
 * 
 */
jQuery(function($) {
	
	var _ajax = $.ajax;
	
	$.ajax = function(opt) {
		var _success = opt && opt.success || function(a, b) {
		};
		var _error = opt && opt.error || function(a, b, c) {
		};
		
		var _opt = $.extend(opt, {
			success : function(data, textStatus) {
				
				if(typeof data == "object" && false == data.success && data.status==401){
					openErrAlert(data.msg);
					return;
				}
				
				if (typeof data == "string" && data.indexOf('top.location.href=') != -1) {
					eval(data);
					return;
				}
				_success(data, textStatus);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){  
	            //错误方法增强处理  
				var content = XMLHttpRequest.responseText;
				
				if(typeof data == "object" && false == data.success && data.status==401){
					openErrAlert(data.msg);
					return;
				}
				
				if(XMLHttpRequest.status==401){
					//未授权，刷新页面
					location.reload();
				}
				
				if (typeof content == "string" && content.indexOf('top.location.href=') != -1) {
					eval(content);
					return;
				}else{
					closeLoading();
				}
				_error(XMLHttpRequest, textStatus, errorThrown);
	        }
		});
		return _ajax(_opt);
	};
});