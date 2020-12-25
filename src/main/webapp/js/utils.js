// 全局常量
var globalState = {
	icons: {
		export: 'M842.24 312.746667l-189.44-213.333334a42.666667 42.666667 0 0 0-31.573333-14.08h-341.333334A107.946667 107.946667 0 0 0 170.666667 192v640A107.946667 107.946667 0 0 0 279.893333 938.666667h464.213334A107.946667 107.946667 0 0 0 853.333333 832V341.333333a42.666667 42.666667 0 0 0-11.093333-28.586666zM597.333333 170.666667l159.573334 170.666666h-128a33.706667 33.706667 0 0 1-31.573334-36.266666z'
	},
	urls: {
		downloadCSV: 'remoteMonitor/enterpriseMainPanel/exportHistoryRecordList.do',
	}
}
var players=new Array();

/**
 * 
 * 常用工具类
 */

/**
 * 响应菜单，刷新内容
 * 
 * @param {}
 *            url 请求url
 * @param {}
 *            id 返回的html填充的div Id
 * @param {}
 *            params 请求所带的参数
 * @param {}
 *            type 请求类型
 * @param {}
 *            fnCallback 成功后回调
 */
function showContent(url, id, params, type, fnCallback){
	
		if(players != undefined){
			for(var i=0;i<players.length;i++){
				players[i].stop();
			}
		}
		if(params == undefined){
			params={};
		}
		stopMenuAnimate();
		openLoading();
		$("#lay-loading").css("height","64px");
		$.ajax({
			url : url,
			data : params,
			// cache: false,
			type: type==undefined?"GET":type,
			success : function(data){
				$("#"+id).html(data);
				if(fnCallback) fnCallback();
			},
			error: function (xhr, error, thrown) {
				
				closeLoading();
				if (error == "parsererror") {
					console.log("AjaxRequest warning: JSON data from " +
					"server could not be parsed. This is caused by a JSON formatting error.");
				} else {
					console.log("request error");
				}
			}
		});
}

/**
 * 添加tab标签
 * 
 * @param url
 * @param id
 * @param name
 * @param icon
 * @param params
 * @param refresh 是否刷新
 * @returns
 */
function addMainTab(url, id, name, icon,params,refresh,location){
	 // 新增一个Tab项
	if(params==undefined) params={};
	element = layui.element;
	openLoading();
	$("#lay-loading").css("height","64px");
	var exist = existTab(id);
//	if(refresh && exist){
//		element.tabDelete('main-tab', id);
//		exist = false;
//	}
	if(!exist){
		var title = '<img src="css/images/tabs/' + icon + '.png">';
		title += '<span>' + name + '</span>';
		stopMenuAnimate();
		
		$.get(url,params,function(data){
			element.tabAdd('main-tab', {
				id: id,
				title: title,
				content: data
//				content: '<iframe src="'+ url + '"></frame>'
			});
			
			_tabId = id;
			
			var $p = $("#main-tab .layui-tab-title");
			var iev = isIE();
			if((iev>0 && iev<10) && $p.find("li").length>=10){
				var firstId = $p.find("li:first").attr("lay-id");
				element.tabDelete('main-tab', firstId);
			}
			
			$("#main-tab-content>.layui-tab-item.layui-show").html("");
			element.tabChange('main-tab', id);
			checkMainTabsClose()
			
		})
	}else{
		if(location){
			_taburl = url;
		}
		element.tabChange('main-tab', id);
		
		if(refresh){
			if(id == 'overall-search')
				refrestTabs(url, id, params);
		}else{
			closeLoading();
		}
	}
	
}

function refrestTabs(url, id, params){
	openLoading();
	var TITLE = '.layui-tab-title'
	    ,tabElem = $('.layui-tab[lay-filter=main-tab]')
	    ,titElem = tabElem.children(TITLE)
	    ,liElem = titElem.find('>li[lay-id="'+ id +'"]');
	var othis = liElem[0];
	var index = $(othis).parent().children('li').index(othis);
	
	var parents =$(othis).parents('#main-tab.layui-tab').eq(0);
	var item = parents.children('.layui-tab-content').children('.layui-tab-item');
	
	var othiscontent = item.eq(index);

	$.get(url,params,function(data){
		othiscontent.html(data);
		$("#main-tab-content>.layui-tab-item.layui-show").siblings().html("");
	})
}


function existTab(id){
	if($("#main-tab .layui-tab-title li[lay-id='"+id +"']").length>0){
		return true;
	}else{
		return false;
	}
	
}

/**
 * 图片展示弹窗
 * 
 * @param imgUrl
 * @returns
 */
var imgIdx;
function openPhoto(imgUrl,title){
	
	if(isIE()==0 || isIE()>8){
		openLoading();
	}
	loadImage(imgUrl, function(img){
		imgIdx = layer.open({
	      type: 1,
	      id: 'layui-layer-photos',
	      area: function(){
	        var imgarea = [img.width, img.height];
	        var winarea = [$(window).width() - 120, $(window).height() - 120];
	        
	        // 如果 实际图片的宽或者高比 屏幕大（那么进行缩放）
	        if(imgarea[0]>winarea[0]||imgarea[1]>winarea[1]){
	          var wh = [imgarea[0]/winarea[0],imgarea[1]/winarea[1]];// 取宽度缩放比例、高度缩放比例
	          if(wh[0] > wh[1]){// 取缩放比例最大的进行缩放
	            imgarea[0] = imgarea[0]/wh[0];
	            imgarea[1] = imgarea[1]/wh[0];
	          } else if(wh[0] < wh[1]){
	            imgarea[0] = imgarea[0]/wh[1];
	            imgarea[1] = imgarea[1]/wh[1];
	          }
	        }
	        return [imgarea[0]+'px', (imgarea[1]+42)+'px']; 
	      }(),
	      title: title==undefined?"查看图片":title,
	      shade: 0.3,
	      shadeClose: true,
	      closeBtn: 1,
	      maxmin :false,
	      anim: 0,
	      scrollbar: false,
	      move :false,
	      moveOut: false,
	      isOutAnim: false,
	      skin: 'layui-layer-photos',
	      content: '<div class="layui-layer-phimg">'
	        +'<img src="'+ img.src +'" alt="'+ (img.alt||'') +'" layer-pid="'+ img.pid +'">'
	      +'</div>',
	      success: function(layero, index){
	    	  closeLoading();
	      }
	    });
	  }, function(){
		if(isIE()==0 || isIE()>8){
			closeLoading();
		}
		openErrAlert("图片地址失效!");
	  });
}

// 图片预加载
function loadImage(url, callback, error) {   
  var img = new Image();
  img.src = url; 
  if(img.complete){
    return callback(img);
  }
  img.onload = function(){
    img.onload = null;
    callback(img);
  };
  img.onerror = function(e){
    img.onerror = null;
    error(e);
  };  
};

/**
 * 判断str是否不为空
 * 
 * @returns {Boolean}
 */
function isNotEmpty(str){
	// 非string类型则转为string类型
	if(str != undefined && typeof str != "string") str=str.toString();
	var flag = false;
	if(str!="" && str!=undefined){
		flag = true;
	}
	return flag;
}
/**
 * ie浏览器版本
 * 
 * @returns {Boolean}
 */
function isIE() { 
	var version = 0;
	var agent = navigator.userAgent.toLowerCase();
	if( agent.indexOf( 'msie 6' )>=0 ) {
		version = 6;
	} else if( agent.indexOf( 'msie 7' )>=0 ) {
		version = 7;
	} else if( agent.indexOf( 'msie 8' )>=0 ) {
		version = 8;
	} else if( agent.indexOf( 'msie 9' )>=0 ) {
		version = 9;
	} else if( agent.indexOf( 'msie 10' )>=0 ) {
		version = 10;
	} else if( agent.indexOf( 'rv:11' )>=0 ) {
		version = 11;
	}
	return version;
}

/**
 * 文件大小转换
 * 
 * @param limit
 * @returns
 */
function fileSizeConver(limit){ 
	//limit=limit.replace(/\s+/g,""); 
    var size = "";  
    if(limit=="null"){
    	return "";
    }else if(limit!=undefined && !isNaN(limit)){
    	if( limit < 1024){ 
    		size = limit + " B";    
    	}else if(limit < 1024 * 1024 ){
    		size = (limit / 1024).toFixed(2) + " KB";              
    	}else if(limit < 1024 * 1024 * 1024){ 
    		size = (limit / (1024 * 1024)).toFixed(2) + " MB";  
    	}else{
    		size = (limit / (1024 * 1024 * 1024)).toFixed(2) + " GB";  
    	}  
    	var sizestr = size + "";   
        var len = sizestr.indexOf("\.");  
        var dec = sizestr.substr(len + 1, 2);  
        if(dec == "00"){
            return sizestr.substring(0,len) + sizestr.substr(len + 3,3);  
        }  
        return sizestr;  
    }else{
    	return limit;
    }
}  

function flashChecker() {
	var hasFlash = 0; // 是否安装了flash
	var flashVersion =0;// flash版本
    if (document.all) {
        var swf
        try {
        	swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
		} catch (e) {
			// 浏览器安全性限制
			return undefined;
		}
        
        if (swf) {
            hasFlash = 1;
            VSwf = swf.GetVariable("$version");
            flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0]);
        }
    } else {
        if (navigator.plugins && navigator.plugins.length > 0) {
            var swf = navigator.plugins["Shockwave Flash"];
            if (swf) {
                hasFlash = 1;
                var words = swf.description.split(" ");
                for (var i = 0; i < words.length; ++i) {
                    if (isNaN(parseInt(words[i]))) continue;
                    flashVersion = parseInt(words[i]);
                }
            }
        }
    }
    return {
        f: hasFlash,
        v: flashVersion
    };
}

function SecondsFormat(value){
	
	var result = value;
	if (isNotEmpty(value)) {
		var t = parseInt(value);
		t = parseInt(t/1000);
		var h;
		if(value>60*60){
			h = parseInt(t/(60*60)); 
			t = t%(60*60);
		}
		var s = t%60;
		var m = parseInt(t/60);
		
		if(h != undefined){
			result = fillSeat(h)+":"+fillSeat(m)+":"+fillSeat(s);
		}else{
			result = fillSeat(m)+":"+fillSeat(s);
		}
		
	}
	return result;
}

function fillSeat(h) {
	var r = h+"";
	if(r.length<2) {
		r = "0"+r;
	}
	return r;
}

/**
 * 公共请求成功
 * 
 * @param data
 * @param successCallback
 * @param errorCallback
 * @returns
 */
function requestSuccess(data,successCallback,errorCallback){
	
	if(data.success){
		layer.alert(data.msg, {icon: 1});
		if(successCallback) successCallback();
	}else{
		layer.alert(data.msg, {icon: 2});
		if(errorCallback) errorCallback();
	}
	
	
}

/**
 * 获取当前选中节点的所有父节点
 * 
 * @param treeId
 * @returns
 */
function getNodeIdPathByTreeId(treeId){
	var defaultId = "ztree";
	if(treeId != undefined && treeId != "") defaultId = treeId;
	var treeObj = $.fn.zTree.getZTreeObj(defaultId);
	var selectdNode = treeObj.getSelectedNodes()[0];
	
	var nodes= selectdNode.getPath();
	var ids = [];
	for(var obj in nodes){
		ids.push(nodes[obj].id);
	}
	
	return ids;
}

/**
 * 获取当前选中节点的所有子节点
 * 
 * @param treeId
 *            树id,默认为ztree
 */
function getNodeIdsByTreeId(treeId){
	var defaultId = "ztree";
	if(treeId != undefined && treeId != "") defaultId = treeId;
	var treeObj = $.fn.zTree.getZTreeObj(defaultId);
	var selectdNode = treeObj.getSelectedNodes()[0];
	var ids = [];
	
	ids = getChildren(ids,selectdNode);
	
	return ids;
}

/**
 * 递归获取树节点下的所有叶子节点
 * 
 * @param ids
 *            存放叶子节点id数组
 * @param treeNode
 *            节点
 * @returns
 */
function getChildren(ids,treeNode){
	ids.push(treeNode.id);
	if (treeNode.isParent){
		for(var obj in treeNode.children){
			getChildren(ids,treeNode.children[obj]);
		}
	}else{
		// if(treeNode.isParent!=true)
			// ids.push(treeNode.id);
	}
	return ids;
}

var loadIndex;
function openLoading(msg,area){
	removeTableTips();
	try {
		if(loadIndex == undefined){
			layer.msg(isNotEmpty(msg)?msg:'数据请求中', {
				icon: 16,
				id:'lay-loading',
				area: area==undefined?['180px','64px']:area,
				// maxWidth: '200px',
				// maxHeight: '80px',
				shade: 0.06,
				time: -1,
				success: function(layero, index){
					loadIndex = index;
				}
			});
		}
	} catch (e) {
	}
	
}

function closeLoading(){
	if(loadIndex != undefined)
		layer.close(loadIndex);
	loadIndex = undefined;
}

function openErrAlert(msg){
	layer.msg(msg, {icon: 5,anim: 6});
}
function openAlert(msg){
	layer.msg(msg, {icon: 7});
}
function downLoadFn(url){
	window.location.href="download.do?fileName=" +  encodeURI(encodeURI(url));
}
function downLoadExtendFn(url){
	if(isIE()>0){
		window.open(url);
	}else{
		downloadFile(url);
	}
}

function getDateNow(){
	if (!Date.now) {
	  Date.now = function now() {
	    return new Date().getTime();
	  };
	}else{
		return Date.now();
	}
}

function stringToJson(str){
	return eval('(' + str + ')'); 
}


/**
 * 公共请求成功1
 * 
 * @param data
 * @param successCallback
 * @param errorCallback
 * @returns
 */
function requestSuccess1(data,successCallback,errorCallback){
	
	if(data.success){
		if(successCallback) successCallback();
	}else{
		if(errorCallback) errorCallback();
	}
	
	
}


function FormatUnixTime(dt, formatString) {
    if (arguments.length == 0 || dt == undefined || dt == '') {
        return "";
    }

    var t, y, m, d, h, i, s;
    t = new Date(dt);
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();

    if (formatString == 'yyyy-MM') {
        return y + '-' + (m < 10 ? '0' + m : m);
    }

    if (formatString == 'yyyy-MM-dd') {
        return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
    }
    if (formatString == 'MM-dd') {
        return (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
    }
    if (formatString == 'MM月dd日') {
        return m + '月' + d + '日';
    }

    if (formatString == 'yyyy-MM-dd HH:mm') {
        return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) + ' ' + (h < 10 ? '0' + h : h) + ':' + (i < 10 ? '0' + i : i);
    }
    if (formatString == 'yyyy-MM-dd HH:mm:ss') {
        return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) + ' ' + (h < 10 ? '0' + h : h) + ':' + (i < 10 ? '0' + i : i) + ':' + (s < 10 ? '0' + s : s);
    }
    if (formatString == 'HH:mm:ss') {
        return (h < 10 ? '0' + h : h) + ':' + (i < 10 ? '0' + i : i) + ':' + (s < 10 ? '0' + s : s);
    }
}


function Map() {
    /** 存放键的数组(遍历用到) */
    this.keys = new Array();
    /** 存放数据 */
    this.data = new Object();

    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function (key, value) {
        if (this.data[key] == null) {
            this.keys.push(key);
        }
        this.data[key] = value;
    };

    /**
     * 获取某键对应的值
     * @param {String} key
     * @return {Object} value
     */
    this.get = function (key) {
        return this.data[key];
    };

    /**
     * 删除一个键值对
     * @param {String} key
     */
    this.remove = function (key) {
        //this.keys.remove(key);
        var len = this.keys.length;
        for (var i = 0; i < len; i++) {
            if(this.keys[i] == key) {
                this.keys.splice(i, 1);
            }
        }
        var data = this.data[key];
        delete this.data[key];
        return data;
    };

    /**
     * 遍历Map,执行处理函数
     *
     * @param {Function} 回调函数 function(key,value,index){..}
     */
    this.each = function (fn) {
        if (typeof fn != 'function') {
            return;
        }
        var len = this.keys.length;
        for (var i = 0; i < len; i++) {
            var k = this.keys[i];
            fn(k, this.data[k], i);
        }
    };

    /**
     * 获取键值数组(类似Java的entrySet())
     * @return 键值对象{key,value}的数组
     */
    this.entrys = function () {
        var len = this.keys.length;
        var entrys = new Array(len);
        for (var i = 0; i < len; i++) {
            entrys[i] = {
                key: this.keys[i],
                value: this.data[i]
            };
        }
        return entrys;
    };

    /**
     * 判断Map是否为空
     */
    this.isEmpty = function () {
        return this.keys.length == 0;
    };

    /**
     * 获取键值对数量
     */
    this.size = function () {
        return this.keys.length;
    };

    /**
     * 重写toString
     */
    this.toString = function () {
        var s = "{";
        for (var i = 0; i < this.keys.length; i++, s += ',') {
            var k = this.keys[i];
            s += k + "=" + this.data[k];
        }
        s += "}";
        return s;
    };
}

function switchTab() {
    if ($(this).hasClass('active')) {
        return;
    }

    var index =  $(this).data('index');

    var tabHead = $(this).parent().parent();
    if (tabHead.find('.active')) {
        tabHead.find('.active').removeClass('active');
    }
    $(this).addClass('active');

    var panelBody = tabHead.parent();

    var tabContent = panelBody.find('.tab-content').children();
    if (!$('.tab-content div[data-index="' + index + '"]').hasClass('.active')) {
        tabContent.removeClass('active');
        $('.tab-content div[data-index="' + index + '"]').addClass("active");
    }
}

function toEmptyString(obj) {
    if(obj == null || obj == 'undefined'){
        return "";
    } else {
        return obj;
    }
}
function undefinedToZero(obj) {
    if(obj == "" || obj == null || obj == 'undefined'){
        return 0;
    } else {
        return obj;
    }
}

/*
 * 给jq日期加format方法
 */
Date.prototype.Format = function(fmt) { //author: meizz
	var o = {
		"M+": this.getMonth() + 1,
		//月份
		"d+": this.getDate(),
		//日
		"h+": this.getHours(),
		//小时
		"m+": this.getMinutes(),
		//分
		"s+": this.getSeconds(),
		//秒
		"q+": Math.floor((this.getMonth() + 3) / 3),
		//季度
		"S": this.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o) if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

/*
 * 格式化图表数据
 */
function formatChartData (legendData, valueData, xAxis, units) {
	var mapper = {};
	for (var i = 0; i < xAxis.length; i++) {
		// var name = legendData[i] + '（' + units[i] + '）';
		var name = legendData[i];
		var value = valueData[i];
		var x = xAxis[i];
		
		for (var t = 0; t < x.length ;t++) {
			var xLabel = x[t];
			if (!mapper[xLabel]) mapper[xLabel] = [];
			mapper[xLabel].push({
				name,
				value: value[t],
			});
		}
	}
	var mapperKeys = Object.keys(mapper);
	var source = [];
	
	for (var k = 0; k < mapperKeys.length; k++) {
		var key = mapperKeys[k];
		var item = mapper[key];
		var sourceItem = { name: key };
		var obj = {};
		obj[' '] = key;
		for (var i = 0; i < item.length; i++) {
			sourceItem[item[i].name] = item[i].value;
			obj[item[i].name] = item[i].value;
		}
		source[k] = sourceItem;
	}

	return { source, convertData: [] };
}

/*
 * 渲染柱状图
 */
function renderBar (config, query) {
	var ele = config.ele;
	var title = config.title;
	var valueData = config.valueData;
	var legendData = config.legendData;
	var xAxis = config.xAxis;
	var unit = config.unit;
	var units = config.units;
	
	var dimensions = ['name'].concat(legendData);
	var formattedData = formatChartData(legendData, valueData, xAxis, units);
	var source = formattedData.source;

	var series = [];
	for (var s = 0; s < legendData.length; s++) {
		series.push({ type: 'bar' });
	}

	var option = {
		title : {
			text : title,
			left: 'center',
			textStyle:{
				fontWeight:'bold',
				fontSize:14,
			}
		},
		legend: { top: 25 },
		tooltip: {},
		grid: { top: 80 },
		dataset: {
			dimensions,
			source,
		},
		xAxis: {
			type: 'category',
			axisLabel: {
				interval: 'auto',
				// rotate: 15,
				textStyle: {
					color: '#000',
					fontSize: 10
				}
			},
		},
		yAxis: [
			{
				name: unit,
				type: 'value',
			},
		],
		toolbox: {
			feature: {
				saveAsImage: {},
				mytool: {
					show: true,
					title: '导出csv',
					icon: globalState.icons.export,
					onclick: function () {
						downloadCSVFile(title, query);
					}
				},
			}
		},
		series,
	};
	return renderChart(ele, option);
}

/*
 * 渲染散点图
 */
function renderDot (config, query) {
	var ele = config.ele;
	var title = config.title;
	var valueData = config.valueData;
	var legendData = config.legendData;
	var xAxis = config.xAxis;
	var unit = config.unit;
	var units = config.units;

	var source = [];
	for (var l = 0; l < xAxis.length; l++) {
		var axis = xAxis[l];
		source[l] = [];
		for (var d = 0; d < axis.length; d++) {
			var x = axis[d];
			source[l].push([ x, valueData[l][d], legendData[l] ]);
		}
	}
	var formatSeries = [];
	for (var s = 0; s < source.length; s++) {
		var item = source[s];
		formatSeries.push({
			name: legendData[s],
			data: item,
			type: 'scatter',
		});
	}

	var option = {
		title: {
			text: title,
			left: 'center',
			textStyle:{
				fontWeight:'bold',
				fontSize:14,
			}
		},
		legend: {
			top: 25,
			data: legendData,
		},
		grid: { top: 80 },
		tooltip: {
			trigger: 'axis'
		},
		xAxis: {
			type: 'category',
			axisLabel: { 
				interval: 'auto',
				// rotate: 15,
				textStyle: {
					color: '#000',
					fontSize: 10
				}
			},
		},
		yAxis: [
			{
				name: unit,
				type: 'value',
			},
		],
		toolbox: {
			feature: {
				saveAsImage: {},
				mytool: {
					show: true,
					title: '导出csv',
					icon: globalState.icons.export,
					onclick: function () {
						downloadCSVFile(title, query);
					}
				},
			}
		},
		series: formatSeries,
	};		
	return renderChart(ele, option);
}

/*
 * 渲染折线图
 */
function renderLine (config, query) {
	var ele = config.ele;
	var title = config.title;
	var valueData = config.valueData;
	var legendData = config.legendData;
	var xAxis = config.xAxis;
	var unit = config.unit;
	var units = config.units;

	var data = [];
	for (var i = 0; i < legendData.length; i++) {
		var legend = legendData[i];
		var d = valueData[i];
		data.push({
			name: legend,
			type: 'line',
			// stack: '总量',
			smooth: true,
			data: d,
		});
	}
	var formattedData = formatChartData (legendData, valueData, config.originXAxis, units);

	var option = {
		title : {
			text : title,
			// top:20,
			left: 'center',
			textStyle:{
				fontWeight:'bold',
				fontSize:14,
			}
		},
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			top: 25,
			data: legendData,
		},
		grid: {
			top: 80,
			containLabel: true
		},
		toolbox: {
			feature: {
				saveAsImage: {},
				mytool: {
					show: true,
					title: '导出csv',
					icon: globalState.icons.export,
					onclick: function () {
						downloadCSVFile(title, query);
					}
				},
			}
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			data: xAxis,
			axisLabel: { 
				interval: 'auto',
				// rotate: 15,
				textStyle: {
					color: '#000',
					fontSize: 10
				}
			},
		},
		yAxis: [
			{
				name: unit,
				type: 'value',
			},
		],
		series: data,
	};

	return renderChart(ele, option);
}

function renderChart (ele, option) {
	var chart = echarts.init(ele, {
		// width: 200,
		// height: 500,
	});
	chart.setOption(option);
	
	return chart;
}

function downloadCSVFile (title, query) {
	if (!query) query = {};
	// $.get( globalState.urls.downloadCSV, query, function(data) {
	// 	console.log(query, data);
	// });
	var a = document.createElement('a');
	var fileName = title + new Date().format('yyyyMMdd') + '.csv';
	a.download = fileName;
	a.href = globalState.urls.downloadCSV + '?' + joinQueryParams(query);
	document.body.appendChild(a);
	a.click();
	document.body.removeChild(a);
}

function joinQueryParams (params) {
	var paramKeys = Object.keys(params);
	var queryStr = '';
	for (var i = 0; i < paramKeys.length; i++) {
		var k = paramKeys[i];
		queryStr += k + '=' + params[k];

		if (i < paramKeys.length - 1) queryStr += '&';
	}
	return queryStr;
}