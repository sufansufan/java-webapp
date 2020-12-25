
var specialCharReg  = "[ _\\+\\-！`~!@%#$^&*=|{}':;',\\[\\]<>/?\\.；：%……+￥【】()（）‘”“'。，、？]";

var pageParam = {
		curr : 1,
		limits :[10, 20, 50, 100, 200, 300, 400, 500]
	}
var userTreeIdx;
var isFull;

var isDisabledRefresh = false;

var table,laytpl,form,element,laydate,upload, treetable, loading;
var xhrOnProgress=function(fun) {
	xhrOnProgress.onprogress = fun; 
	return function() {
		var xhr = $.ajaxSettings.xhr();
		  if (typeof xhrOnProgress.onprogress !== 'function')
		       return xhr;
		    if (xhrOnProgress.onprogress && xhr.upload) {
		          xhr.upload.onprogress = xhrOnProgress.onprogress;
		    }
		    return xhr;
	 }
}

$(function(){
	$.ajaxSetup({
		cache: false
	});
	
	window.console = window.console || (function(){ 
		var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile 
		= c.clear = c.exception = c.trace = c.assert = function(){}; 
		return c; 
	})();
	
	$(document).on("blur","input.search-maxlenght",function(){
		var v = $(this).val();
		if(v != undefined){
			$(this).val(v.substring(0,15));
		}
		
	});
	
	$(document).on("click",".kit-side-fold",function(){
		
		if(!$(".layui-side").is(":animated")){
			if($(".layui-side").hasClass("kit-sided")){
				$("#side-menu").rotate({animateTo: 0});
				$(".layui-nav-child dd a").css("padding-left","30px");
				$(".layui-side,.kit-side-fold,.more-menu,.more-menu-up").animate({width:"200px"});
				$(".layui-side,.layui-side-scroll").removeClass("kit-sided");
				$(".second-main-content").animate({left:"200px"});
				$(".layui-side-scroll").animate({width:"220px"},function(){
				})
				
			}else{
				$("#side-menu").rotate({animateTo: -90});
				$(".kit-side-fold,.more-menu,.more-menu-up").animate({width:"50px"});
				$(".layui-side").animate({width:"50px"},function(){
				})
				$(".second-main-content").animate({left:"50px"});
				$(".layui-nav-child dd a").animate({paddingLeft:"0px"});
				$(".layui-side-scroll").animate({width:"70px"},function(){
					$(".layui-side").addClass("kit-sided");
				});
				
			}
		}
		
	});
	
	$(document).on('focus','.layui-input.layui-unselect',function () {
		$(this).attr("readonly","readonly");
		$(this).attr("unselectable","on");
		$(this).blur();
	});
	
	$(document).on('click','.admin-side-full',function () {
		if(isFull){
			
			if (document.exitFullscreen) { 
				document.exitFullscreen(); 
			} 
			else if (document.msExitFullscreen) { 
				document.msExitFullscreen(); 
			} 
			else if (document.mozCancelFullScreen) { 
				document.mozCancelFullScreen(); 
			} 
			else if (document.webkitCancelFullScreen) { 
				document.webkitCancelFullScreen(); 
			} else{
				var wscript = new ActiveXObject("WScript.Shell");
			    if(wscript != null) {
			        wscript.SendKeys("{F11}");
			    }
			}
			isFull = false;
		}else{
			
			$(document).keyup(function(event){
		    	 switch(event.keyCode) {
		    	 case 27:
					 isFull = false;
		    	 }
	    	});
			
			var docElm = document.documentElement;
			// W3C
			if (docElm.requestFullscreen) {
				docElm.requestFullscreen();
			}
			// FireFox
			else if (docElm.mozRequestFullScreen) {
				docElm.mozRequestFullScreen();
				
				document.addEventListener('mozfullscreenchange', function(e){ 
					if (!e.currentTarget.mozFullScreen) {
						 isFull = false;
		            }
				});
			}
			// Chrome等
			else if (docElm.webkitRequestFullScreen) {
				docElm.webkitRequestFullScreen();
				document.addEventListener("webkitfullscreenchange", function(e) {
					  if (!e.currentTarget.webkitIsFullScreen) {
							 isFull = false;
			            }
					});
			}
			// IE11
			else if (docElm.msRequestFullscreen) {
				docElm.msRequestFullscreen();
			}else{
				//其他IE
				var wscript = new ActiveXObject("WScript.Shell");
			    if(wscript != null) {
			        wscript.SendKeys("{F11}");
			    }
			    $(document).unbind("keyup");
			    $(document).keyup(function(event){
			    	 switch(event.keyCode) {
			    	 case 27:
			    		 if(isFull){
			    			 var wscript = new ActiveXObject("WScript.Shell");
			    			 if(wscript != null) {
			    				 wscript.SendKeys("{F11}");
			    			 }
			    		 }
						 isFull = false;
			    	 }
		    	});
			}
			
			layer.msg('按Esc即可退出全屏');
			isFull = true;
		}
		
	});
	
	$(document).on("mouseenter mouseleave",".kit-side-fold",function(event){
		if (event.type == "mouseenter") {
			
			var str = "";
			if($(".layui-side").hasClass("kit-sided")){
				str = "点击展开菜单";
			}else{
				str = "点击折叠菜单";
			}
			layer.tips(str, this,{
				  tips: [2, '#353A39'],
				  time: 1500
			});
		} else if (event.type == "mouseleave") {
			layer.closeAll('tips');
		}

	})
	
	$(document).on("mouseenter mouseleave",".layui-side.kit-sided ul.layui-nav-tree li.layui-nav-item .first-menu," +
			".layui-side.kit-sided ul.layui-nav-tree li.layui-nav-item .second-menu",function(event){
		if (event.type == "mouseenter") {
			layer.tips($(this).find("span:first").text(), this,{
				  tips: [2, '#353A39'],
				  time: 1500
			});
		} else if (event.type == "mouseleave") {
			layer.closeAll('tips');
		}

	})

	$(document).on("click","ul.layui-nav.layui-nav-tree li",function() {
		var m = $(this), s = m.siblings();
		m.find("img.icon").addClass("cell");
		s.find("img.icon").removeClass("cell");
	})
	
});

function stopMenuAnimate(){
	$(".contain-content").stop(true);
	$(".layui-side").stop(true);
	$(".kit-side-fold").stop(true);
	$(".layui-side,.layui-side-scroll").stop(true);
}

function removeTableTips(){
	$(".layui-layer.layui-layer-tips.layui-table-tips").remove();
}

function loadDefaultImg(obj){
	$(obj).attr("src", "css/images/no_pictures.png");
}

function loadErrImg(obj){
	$(obj).attr("src", "css/images/pictures_404.png");
}

/**
 * 关闭最上层弹窗
 * @returns
 */
var topIdx;
function closeLayer(){
	layer.close(topIdx);
}

function getTableFullHeight(){
	var p = 126;//top+bottom;
	p+=73;
	return p;
}

function resizeTable(id){
	var $p = $("#"+id).next("div.layui-table-view");
	var h = $p.height()-42-61;
	var $c = $p.find(".layui-table-body.layui-table-main");
	$c.css("height",h+"px");
	$c.css("overflow-x","hidden");
}

function getTableHeight(){
	var $par = $("#cabinet-child-body");
	var p=0;
	if($par.length>0) {
		p=$par.offset().top;
	}else{
		var $par = $("#allSearch-content");
		if($par.length>0) {
			p=$par.offset().top+61;
		}else{
			$par = $(".tree-content");
			if($par.length>0) p=$par.offset().top;
			else p = $(".contain-content").offset().top;
		}
	}
	var $quote = $par.find(".layui-elem-quote");
	if($quote.length>0) p+=$quote.height()+30;
	var $rsearch = $(".layui-form.right-search");
	if($rsearch.length>0) p+=$rsearch.height()+40;
	var $search = $(".layui-form.search");
	if($search.length>0) p+=$search.height()+10;//
	
	var $statistic = $(".statisticNum");
	if($statistic.length>0) p+=$statistic.height();//+10
	
	p+=$(".footer").height()+35
	return 'full-'+p;
}

function emptyList(tableId){
	var $p = $("#" + tableId).next("div.layui-table-view");
	$p.find(".layui-table-page").hide();
}

/**
 * 形式判断图片路径是否有效
 * @param imgurl
 * @returns
 */
function isIllegalImgUrl(imgurl){
	if(!isNotEmpty(imgurl) || (imgurl.indexOf("jpg")<0 && imgurl.indexOf("png")<0 && imgurl.indexOf("jpeg")<0 && imgurl.indexOf("gif")<0)){
		return true;
	}else{
		return false;
	}
}

/**
 * 空操作项列处理
 * @param tableId
 * @returns
 */
function emptyOptBar(tableId){
	$("#" + tableId).next(".layui-table-view").find(".layui-table-main tbody td .optBar").each(function(){
		var ht = $.trim($(this).html());
		if(ht==undefined || ht.length==0 || $(this).find("button:visible").length==0){
			$(this).html('<div style="color: #959595;font-size: 14px;" title="无可操作项">-</div>'+ht)
		}
	 });
}

function turnToMenu(idxUrl, point){
	
	var _$btn = $(".left ul.layui-nav li a[onclick*='" + idxUrl + "']");
	if(_$btn==null || _$btn.length==0){
		layer.msg("无相关权限");
	}else{
		var nodeName = _$btn.parent().prop("nodeName");
		if(nodeName.toLowerCase()=="dd"){
			_$btn.parent().parent().parent("li.layui-nav-item").addClass("layui-nav-itemed");
			resizeLeft();
		}
		$("li.layui-nav-item,dl.layui-nav-child dd").removeClass("layui-this");
		_$btn.parent().addClass("layui-this");
		var ock = _$btn.attr("onclick");
		var url = ock.split("'");
		
		var tabid = _$btn.attr("tabid");
		var tabname = _$btn.attr("tabname");
		var tabicon = _$btn.attr("tabicon");
		
		var $scroll = $(".left.layui-side .layui-side-scroll");
		$scroll.stop(true)
		var t = $scroll.scrollTop();
		$scroll.animate({  
			scrollTop:t+_$btn.parent().offset().top-$scroll.offset().top
		},200) 
		
		
		if(url.length>1){
			var _u = url[1];
			
			if(isNotEmpty(point)){
				_u += "&nodeId=" + point;
			}
			
			var exist = existTab(tabid);
			
			if(!exist){
				addMainTab(_u, tabid, tabname, tabicon)
			}else{
				isDisabledRefresh = true;
				element.tabChange('main-tab', tabid);
				refrestTabs(_u, tabid);
				isDisabledRefresh = false;
			}
			
		}
	}
}

function resizeLeft(){
	var maxH = $(".left").height();
	var currH = $("ul.layui-nav.layui-nav-tree")[0].scrollHeight;
	if(currH>maxH){
		$(".more-menu").show();
		$(".left.layui-side").addClass("layui-side-subheight")
	}else{
		$(".more-menu").hide();
		$(".left.layui-side").removeClass("layui-side-subheight")
	}
}

function checkMainTabsClose(){
	$li = $("#main-tab ul#main-tab-title li");
	$close = $li.find("i.layui-tab-close");
	if($li.length>1){
		$close.removeClass("layui-hide");
		$li.removeClass("noclose");
	}else{
		$li.addClass("noclose");
		$close.addClass("layui-hide");
	}
}

function selectedLeftMenu(layid){
	
//	$p = $(".left a[tabname='"+name +"']");
	$p = $(".left a[tabid='"+layid +"']");
	if($p.length>0){
		$(".left .layui-nav .layui-nav-item a").parent().removeClass("layui-this");
		var nodeName = $p.parent().prop("nodeName");
		if(nodeName.toLowerCase()=="dd"){
			$p.parent().parent().parent("li.layui-nav-item").addClass("layui-nav-itemed");
		}else{
		}
		$p.parent().addClass("layui-this");
		
		var $scroll = $(".left.layui-side .layui-side-scroll");
		var $lastA = $scroll.find("a:visible:last");
		
		$scroll.stop(true)
		var t = $scroll.scrollTop();
		$(".left.layui-side .layui-side-scroll").animate({  
			scrollTop:t+$p.parent().offset().top-$scroll.offset().top
		},200) 
	}
}

function moreMenu(){
	$(".left.layui-side .layui-side-scroll").stop(true)
	var t = $(".left.layui-side .layui-side-scroll").scrollTop();
	$(".left.layui-side .layui-side-scroll").animate({  
		scrollTop:t+80
	},200) 
}
function moreUpMenu(){
	$(".left.layui-side .layui-side-scroll").stop(true)
	var t = $(".left.layui-side .layui-side-scroll").scrollTop();
	$(".left.layui-side .layui-side-scroll").animate({  
		scrollTop:t-80
	},200) 
}

function getValueFromMap(map,key,defaultValue){
	if(!map) return key;
	var r = map[key];
	return !r?!defaultValue?key:defaultValue:r;
}

function changeWarningText(name) {
	let preStr = "["+name+"]";
	let afterStr = "-异常";
	let oldName = $("input[name='oldFactorName']").val();
	let currentLowerText = $("input[name='lowerLimitText']").val();
	let currentUpperText = $("input[name='upperLimitText']").val();
	let typeId = $("select[name='typeId']").val()
	if(typeId == "1") {
		if (currentLowerText == "" || currentLowerText == oldName + afterStr) {
			$("input[name='lowerLimitText']").val(preStr + afterStr);
		}
		;
		if (currentUpperText == "" || currentUpperText == oldName + "-异常") {
			$("input[name='upperLimitText']").val(preStr + afterStr);
		}
		;
	}
	$("input[name='oldFactorName']").val(preStr);
	return;
};

function changeWarningByType(index) {
	if(index == 1) {
		let afterStr = "-异常";
		let preStr = $("input[name='oldFactorName']").val();
		$("input[name='lowerLimitText']").removeAttr("disabled");
		$("input[name='upperLimitText']").removeAttr("disabled");
		$("input[name='lowerLimitText']").val(preStr + afterStr);
		$("input[name='upperLimitText']").val(preStr + afterStr);
	}
	else{
		$("input[name='lowerLimitText']").val("");
		$("input[name='upperLimitText']").val("");
		$("input[name='lowerLimitText']").attr("disabled");
		$("input[name='upperLimitText']").attr("disabled");
	}
	return;
};

function initWarningText() {
	let typeId = $("select[name='typeId']").val();
	if(typeId == "1"){
		$("input[name='lowerLimitText']").removeAttr("disabled");
		$("input[name='upperLimitText']").removeAttr("disabled");
	}
	return;
}

function initCheckBox() {
	let value = $("input[name='startSwitch']").val();
	if(value == "1"){
		$("input[name='isStartSwitch']").attr("checked",true);
	}
	return;
}

function changeCheckBox() {
	if($("input[name='isStartSwitch']").is(':checked')){
		$("input[name='startSwitch']").val("1");
	}
	else {
		$("input[name='startSwitch']").val("0");
	}
	return;
}