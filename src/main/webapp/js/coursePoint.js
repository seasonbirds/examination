/*******************************************************************************
 * VNETOO Register JavaScript 
 * 初始化课程（添加与修改）
 * 关联知识点
 * Author:huangjs
 * Time:2014.03.19
 * Copyright(c)  VNETOO All rights reserved.
 ********************************************************************************/
//与具体控件Id无关(response,此处应该直接返回json数据，而不是数据+result[标志]；通过判断返回值即可判断是否发生异常)
function initCourse($course,url, courseVale) { 
		$.ajax({
			url : url,
			data : {},
			dataType : "json",
			async : false,
			success : function(response) {
				if(response.result == "success"){
					
					var data = response.data;
					var len = data.length;				
					if (courseVale == 'undefined' || courseVale=='') {
						for ( var i = 0; i < len; i++) {
							$course.append("<option value='" + data[i].id + "'>"
									+ data[i].name + "</option>"); // 添加一项option
						}
					} else {
						for ( var i = 0; i < len; i++) {
							if(data[i].id==courseVale){
								$course.append("<option selected='selected' value='" + data[i].id + "'>"
										+ data[i].name + "</option>"); 
							}else{
								$course.append("<option value='" + data[i].id + "'>"
									+ data[i].name + "</option>"); 
							}
						}
					}
				}else{
					jAlertError(response.msg);
				}
							 
			},
			error :function(){
				jAlertError("网络异常");
			}
		}); 
};
/**
 * 下拉树的配置选项
 */
var editSetting = {
	view: {
		dblClickExpand: false
	},
	data: {
		simpleData: {
			enable: true,
			idKey:"id",
			pIdKey:"pid",
			rootPId: 0
		}
	},
	async: {
		enable: true,
		url:function(){return "examKeyPoint!getKeyPointTree.action?examKeyPoint.courseId="+$("#qcourseId").val(); },//每次刷新取选择的课程作参数
		type:"get"
	},
	callback: {
		//树节点点击时给隐藏域Id和显示名赋值,触发onchang事件，设置表单验证并隐藏树
		onClick:function(event,treeId,treeNode){
			var zTree = $.fn.zTree.getZTreeObj("qkeyPointTree");
			var nodes = zTree.getSelectedNodes();
			$("#qkeyPointId").val(nodes[0].id);
			
			var a = new Array();
			a.push(nodes[0].name);
			var node = nodes[0].getParentNode();
			while(node != null){
				a.push(node.name);
				node = node.getParentNode();
			}
			var parentName = "";
			for(var i=a.length-1; i>=0; i--){
				parentName += a[i];
				if(i != 0){
					parentName += ";";
				}
			}
			$("#qselectKeyPointId").val(parentName);
			$("#qselectKeyPointId").change();
			isValid = true;
			hidePointMenu();
		}
	}
};
 

/**
 * 显示下拉树
 */
function showPointTree() {
	//var select = $("#qselectKeyPointId");
	//var selectOffset = $("#qselectKeyPointId").offset();
	//alert(selectOffset.left);
	//$("#qselectTree").css({left:"146px", top: "369px"}).slideDown("fast");
	$("body").bind("mousedown", onPointBodyMouseDown);
	$("#qselectTree").slideDown("fast");
	//alert("ddd");
}

/**
 * 隐藏下拉树
 */
function hidePointMenu() {
	$("#qselectTree").fadeOut("fast");
	$("body").unbind("mousedown", onPointBodyMouseDown);
}

/**
 * 鼠标按下事件函数
 */
function onPointBodyMouseDown(event) {
	if (!(event.target.id == "qselectTree" || $(event.target).parents("#qselectTree").length>0)) {
		hidePointMenu();
	}
}

$().ready(function() {	
	var $course=$("#qcourseId");
	var url='appCourse!getCourseList.action';
	var courseValue=$("#qcourseIdHidden").val();
	initCourse($course,url,courseValue);
	$.fn.zTree.init($("#qkeyPointTree"), editSetting);
	//课程发生变化时
	$course.change(function(){
		selectCourseId = $(this).val();
		var tree = $.fn.zTree.getZTreeObj("qkeyPointTree");
		tree.reAsyncChildNodes(null, "refresh");
		isValid = true;
		$("#qcourseIdHidden").val("");
		$("#qselectKeyPointId").val("");
	});
	//知识点显示框获得焦点时显示下拉树
	$("#qselectKeyPointId").focus(function(){
		showPointTree();
	});
});