//jQuery.noConflict();
function jQueryPost(url,parm,call){
	$.post(url,jQuery.param(parm,true),function(data){
		var ajaxresponse=eval('(' + data + ')');
		call(parseInt(ajaxresponse.result),ajaxresponse.msg);
	});
};
String.prototype.trim = function(){ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
};

//设置分页器每页显示个数
function setPageSize(s,h){
	startRow=0;
	pageSize=parseInt(s);
	pageQuery();		
}

//排序搜索
function searchSort(fieldName){	
	sortFieldName=fieldName;
	sortDesc=!sortDesc;
	pageQuery();
};

//分页查询首页
function pageFirst(){
	if(startRow==0){
		$("a").each(function(i){if($(this).attr("id")=="page.first")$(this).tip({content:"已经是第首页了！"});});
		return;
	}
	
	startRow=0;
	pageQuery();
}
//分页查询上一页
function pagePrev(){
	if(startRow==0){
		$("a").each(function(i){if($(this).attr("id")=="page.prev")$(this).tip({content:"已经是第首页了！"});});
		return;
	}
	
	startRow=startRow-pageSize;
	if(startRow<0)
		startRow=0;
	pageQuery();
}
//分页查询下一页
function pageNext(){
	if(startRow+pageSize>=parseInt(document.getElementById("page.total").innerHTML)){
		$("a").each(function(i){if($(this).attr("id")=="page.next")$(this).tip({content:"已经是最后一页了！"});});
		return;
	}
	startRow=startRow+pageSize;
	pageQuery();
}
//分页查询最后一页
function pageLast(){
	var t = parseInt(document.getElementById("page.total").innerHTML);
	if(startRow+pageSize>=t){
		$("a").each(function(i){if($(this).attr("id")=="page.last")$(this).tip({content:"已经是最后一页了！"});});
		return;
	}
	startRow=t;
	pageQuery();
}

//跳转页面
function gotoPage(index){
	startRow=(index-1)*pageSize;
	pageQuery();
}

//查询后回调
function onFinish(result){
	//parent.hiddenWaitBar();
	document.getElementById("page.result").innerHTML=result;
	startRow=parseInt(document.getElementById("page.startRow").innerHTML);	
	isLastPage=document.getElementById("page.lastPage").innerHTML=="true";
	sortFieldName=document.getElementById("page.sortFieldName").innerHTML;
	sortDesc=document.getElementById("page.sortDesc").innerHTML=="true";
	/*	
	$("input[type=checkbox]").each(function(i){		
		if(this.value>0){			
			if(selectList.contains(this.value)){
				this.checked=true;
			}
		}
	});*/
	selectList.clear();	
	setPageInfo();
}

function setPageInfo(){
	var pageInfo = document.getElementById("page.info");
	if(pageInfo){
		$("div").each(function(i){
			if($(this).attr("id")=="page.navigation"){
				$(this).html(pageInfo.innerHTML);				
			}			 
		});			
	}
	
	$('.f_single_delete').jConfirmAction({
		question:"确定删除该记录吗？",
		yesAnswer:"是",cancelAnswer:"否",
		yesAction:function($this){
			jQueryPost($this.attr("href"),
				{},
				function(result,msg){
					if(result==0){					
						pageQuery();
						jAlert("删除成功!");
					}else{
						jAlertError("删除失败!"+msg);
					}
				}
			);
		}
	});	
	$('.f_tblist_edit').colorbox();
	$('.f_tblist_show').colorbox();
	/************知识点特殊处理:刷新左侧树结构*****************/
	if($('.deleteKeyPoint').length > 0){
		$('.deleteKeyPoint').jConfirmAction({
			question:"确定删除该记录吗？",
			yesAnswer:"是",cancelAnswer:"否",
			yesAction:function($this){
				jQueryPost($this.attr("href"),
						{},
						function(result,msg){
							if(result==0){
								refreshTree();
								pageQuery();
								jAlert("删除成功!");
							}else{
								jAlertError("删除失败!"+msg);
							}
						}
				);
			}
		});
	}
	/************入学、模拟考试试卷按钮特殊处理*****************/
	if($(".specialPaper").length > 0){
		$(".specialPaper").jConfirmAction({
			question:"请选择你要的操作？",
			yesAnswer:"查看",cancelAnswer:"导出",
			yesAction:function($this){$.colorbox({href:"examPaper!details.action?examPaper.id=" + $this.attr("id")});},
			cancelAction:function($this){window.location.href = "examPaper!batchDownloadPaper.action?examPaper.ids="+$this.attr("id");}
		});
	}
}

//定义Map类
function Map(){
	this.akey = new Array();
	this.avalue = new Array();
	
	this.put = function(key,value){ 
		this.akey.push(key);
		this.avalue.push(value);
	} 

	this.get = function(key){ 
		for(var i=0;i<this.akey.length;i++){
			var temp = this.akey[i];
			if(temp == key){
				return this.avalue[i];
			}
		}
	}
	
	this.toJson=function(){
		var result="";
		for (var i=0;i<this.akey.length;i++){
			if(Object.prototype.toString.call(this.avalue[i]) === '[object Array]'){
				var temp = "";				
				for(var j=0;j<this.avalue[i].length;j++){
					temp+=",'"+this.avalue[i][j]+"'";
				}
				temp = "["+temp.substring(1)+"]";
				result+=",\""+this.akey[i]+"\":"+temp;
			}
			else{
				result+=",\""+this.akey[i]+"\":"+this.avalue[i];
			}				
		}
		return "{"+result.substring(1)+"}";
	}	
//*****************************create by lijie 2012-08-02*******************************************
	//不重复
	this.putt = function(key,value){ 
		for (var i=0;i<this.akey.length;i++){
			var temp = this.akey[i];
			if(temp==key){
				this.avalue[i] = value;
				return false;
			}
		}
		this.akey.push(key);
		this.avalue.push(value);
	} 
	this.gett = function(key){ 
		for(var i=0;i<this.akey.length;i++){
			var temp = this.akey[i];
			if(temp == key){
				return this.avalue[i];
			}
		}
		return false;
	}
	this.size = function(){
		return this.akey.length;
	}
	//删除
	this.remove=function(item){
		for (var i=0;i<this.akey.length;i++){
			var temp = this.akey[i];
			if(temp==item){
				this.akey[i]=this.akey[this.akey.length-1];
				this.avalue[i]=this.avalue[this.avalue.length-1];
				this.akey.pop();
				this.avalue.pop();
				return;
			}
		}
	}
	this.getKey = function(){
		return this.akey;
	}
//*****************************end by lijie 2012-08-02*******************************************
}

//定义Set类
function Set(){
	this.data=new Array();
	
	this.add=function(item){
		for (var i=0;i<this.data.length;i++){
			var temp = this.data[i];
			if(temp==item)
				return;
		}
		this.data[this.data.length]=item;		
	}
	
	this.contains=function(item){
		for (var i=0;i<this.data.length;i++){
			var temp = this.data[i];
			if(temp==item)
				return true;
		}
		return false;
	}
	
	this.remove=function(item){
		for (var i=0;i<this.data.length;i++){
			var temp = this.data[i];
			if(temp==item){
				this.data[i]=this.data[this.data.length-1];
				this.data.pop();
				return;
			}
		}
	}
	
	this.size=function(){
		return this.data.length;
	}
	
	this.clear=function(){
		while(this.data.length>0)
			this.data.pop();
	}

	this.toString=function(){
		var temp="";
		for (var i=0;i<this.data.length;i++){
			temp+=",\""+this.data[i]+"\"";
		}
		return "["+temp.substring(1)+"]";
	}
	
	this.getData=function(){
		return this.data;
	}
}


//分页查询查询
//url 			请求url，可以为空
//paraMap 		参数，可以为空
function paginatorSearch(url,paraMap){
	if(arguments.length==0){
		var queryString = $('#query_form').formSerialize();
		queryString+="&paginator.startRowNum="+startRow;
		queryString+="&paginator.sortFieldName="+sortFieldName;
		queryString+="&paginator.sortDesc="+sortDesc;
		queryString+="&paginator.pageSize="+pageSize;
		//alert($('#query_form').attr("action")+queryString);
		jQuery.post(
				$('#query_form').attr("action"),
				queryString,
				onFinish
		);
	}
	else{
		//parent.showWaitBar();
		if(paraMap==null)
			paraMap = new Map();
		paraMap.put("paginator.startRowNum",startRow);
		paraMap.put("paginator.sortFieldName","'"+sortFieldName+"'");
		paraMap.put("paginator.sortDesc",sortDesc);
		paraMap.put("paginator.pageSize",pageSize);
		var m=paraMap.toJson();	
		//alert(url);
		//alert(jQuery.param(eval('(' + m + ')'),true));
		jQuery.post(
				url,
				jQuery.param(eval('(' + m + ')'),true),
				onFinish
		);
	}
    
}

//取消所有选中
function cancelAll(){
	selectList.clear();
	$("input[type=checkbox]").each(function(i){
		this.checked=false;				
	});	
	$("#selCount")[0].innerHTML=selectList.size();
	showCancelAll();
}

//显示按钮
function showCancelAll(){
	if(selectList.size()>0)
		$("#cancelAll")[0].style.visibility='visible';
	else
		$("#cancelAll")[0].style.visibility='hidden';
}

//选中所有
function selectForAll(checkbox){	
	selectAll=checkbox.checked;	
	var checks = $(".f_tblist").find('input[type=checkbox]');
	for ( var i = 0; i < checks.length; i++) {
		var c = $(checks[i]);
		c.prop('checked', selectAll);
		if(c.val()>0){
			if(selectAll)
				selectList.add(c.val())
			else
				selectList.remove(c.val())
		}
	}
	/*$("input[type=checkbox]").each(function(i){
		this.checked=selectAll;
		if(this.value>0){
			if(selectAll)
				selectList.add(this.value)
			else
				selectList.remove(this.value)
		}		
	});	*/
	//$("#selCount")[0].innerHTML=selectList.size();
	//showCancelAll();
	//alert(selectList.toString());
}

//选中其中一个
function selectOne(checkbox){	
	var checkboxAll=document.getElementById("checkboxAll");
	if(checkbox.checked){		
		selectList.add(checkbox.value);
	}
	else{
		checkboxAll.checked=false;
		selectList.remove(checkbox.value);
	}
	//$("#selCount")[0].innerHTML=selectList.size();
	//showCancelAll();
	//alert(selectList.toString());
}

