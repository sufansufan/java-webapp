/**
 * 公用的ztree
 * @param id  tree id
 * @param url 请求的路径
 * @param onclickFun 回调执行的方法
 * @param callbackFun 加载成功回调执行的方法
 * 
 */
 var ztreeFun = function ztreeInit(id,url,onclickFun,callbackFun, otherParam){
	var setting = {  
			   view: {nameIsHTML: true},
		    //获取json数据  
	        async : {    
	            enable : true,   
	            url : url, 
	            autoParam : ["id","pId","name"],
	            otherParam: otherParam==undefined?[]:otherParam
	        },    
	        data:{  
	        	   key: {
	     	   	   title:"name"
	     	   },
	            simpleData : {    
	            	 enable : true,    
                     idKey : "id", // id   
                     pIdKey : "pId", // 父id      
                     rootId : 0 
	            }  
	         }, 
	      // 回调函数    
	         callback : {    
	             onClick : function nodeClick(event, treeId, treeNode, clickFlag){
	            	   if(onclickFun!=null){
	            		   //通过点击左侧获取右侧内容
	            		   onclickFun(treeNode);
	            	   }
	               },
	             onAsyncSuccess: function asyncSuccess(event, treeId, treeNode, msg) {
	            	 
	            	 if(callbackFun!=null){
	            		 //通过点击左侧获取右侧内容
	            		 callbackFun(treeId, treeNode);
	            	 }else{
	            		 onAsyncSuccess(event, treeId, treeNode, msg);
	            	 }
	             }
	            	 
	         }
	}; 
	
	 return $.fn.zTree.init(id, setting);
}
 

$(".treeOpt div.operate div.selection").click(function () {
    if ($(this).children("ul").is(":hidden")) {
        $(this).children("ul").show();
    } else {
        $(this).children("ul").hide();
    }
});
$(".treeOpt div.operate div.selection").bind("mouseleave", function () {
    $(this).children("ul").hide();
});


// 异步加载成功回调函数
function onAsyncSuccess(event, treeId, treeNode, msg) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	// 获取节点
	var nodes = treeObj.getNodes();
	if (nodes.length > 0) {
		treeObj.selectNode(nodes[0]);
		// 默认点击第一个节点
		treeObj.setting.callback.onClick(null, treeObj.setting.treeId, nodes[0]);
		
		// 展开第一父节点
		var cNodes = treeObj.getSelectedNodes();
		if (cNodes!=undefined && cNodes.length > 0) {
//			treeObj.expandNode(treeObj.getNodes()[0],true);
			treeObj.expandNode(cNodes[0], true, true, false);
		}
		
//		treeObj.expandAll(true);
	}else{
		closeLoading();
	}
	
	// 展开所有节点
	//treeObj.expandAll(true);	
}  



var showZtreeFun = function showZtreeInit(id,url,onclickFun,hideTreeFun){
	 var setting = {
	            view: {
	                dblClickExpand: false,
	                showLine: false,
	                selectedMulti: false
	            },
	            data: {
	                simpleData: {
	                    enable:true,
	                    idKey: "id",
	                    pIdKey: "pId",
	                    rootPId: ""
	                }
	            },
	            async : {
	            	enable : true,// 开启异步加载
	            	url : url,//对应的后台请求路径
	            	dataType : "json",
	            	autoParam : [ "id=parentId" ]// 异步加载时需要自动提交父节点属性的参数
	            },
	            callback: {
	                onClick : function nodeClick(event, treeId, treeNode) {
	                    // 通过点击左侧获取右侧内容
	                    // fun(treeNode);
	                	onclickFun(treeNode);
	                	hideTreeFun();
	                },
	                onAsyncSuccess:  function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
	                	      var treeObj = $.fn.zTree.getZTreeObj(treeId);
	                	      var nodes = treeObj.getNodes();
	                	         if (nodes.length>0) {
	                	          for(var i=0;i<nodes.length;i++){
	                	          treeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
	                	          }
	                	      }
	                	   }
	            }
	        };
	
	 return $.fn.zTree.init(id, setting);
}
