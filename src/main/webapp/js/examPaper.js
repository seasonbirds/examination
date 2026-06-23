var webRoot;

/**
 * 答题卡滚动函数
 */
function answerCardScroll(){
	document.getElementById("Javascript.Div5").style.top=(document.documentElement.scrollTop + document.documentElement.clientHeight
			- document.getElementById("Javascript.Div5").offsetHeight) -10 + "px";
	document.getElementById("Javascript.Div5").style.left=(document.documentElement.scrollLeft + document.documentElement.clientWidth
			- document.getElementById("Javascript.Div5").offsetWidth) - 10 + "px";
}

/**
 * 客观题单击事件函数：改变答题卡的颜色、设置未答题数
 * @param qId
 * @returns
 */
function chooseAnswer(qid){
	var objs = $("input[name=result"+qid + "]");
	var answerNumber=0;
	for(var i=0;i<objs.length;i++){
		if(objs[i].checked){
			answerNumber++;
		}
	}

	//利用答题卡的背景作为值的初始状态
	var cardBackGround = $("#td"+qid)[0].style.background;
	if(answerNumber==1){
		if(cardBackGround == ""){
			$("#td"+qid).css("background", "#dadada");
			$("#examCount").html(parseInt($("#examCount").html()) - 1);
		}
	}else if(answerNumber < 1){
		$("#td"+qid).css("background", "");
		$("#examCount").html(parseInt($("#examCount").html()) + 1);
	}
}

/**
 * 主观题值改变事件函数：改变答题卡的颜色、设置未答题数
 * @param qid
 * @returns
 */
function changeAnswer(qid){
	var value = $("#textarea_"+qid).val();
	var cardBackGround = $("#td"+qid)[0].style.background;
	if(value != undefined && value != null && $.trim(value) != "" ){
		if(cardBackGround == ""){
			$("#td"+qid).css("background", "#dadada");
			$("#examCount").html(parseInt($("#examCount").html()) - 1);
		}
	}else{
		$("#td"+qid).css("background", "");
		$("#examCount").html(parseInt($("#examCount").html()) + 1);
	}
}

//定时执行函数handler
var timehandler;
/**
 * 倒计时函数
 * @returns
 */
var totalTime=0;
function goThread(){
	if(totalTime>0){
		
		var min = parseInt(totalTime/60);
		var sec = totalTime%60;
		if(min < 10){
			min = "0" + min;
		}
		if(sec < 10){
			sec = "0" + sec;
		}
		
		var hastime = "";
		if(totalTime < 300){
			hastime = "<font style='color:red'>"+min+"</font>"+"&nbsp;分<font style='color:red'>&nbsp;"+sec+"</font>&nbsp;秒";
		}else{
			hastime = min+"&nbsp;分&nbsp;"+sec+"&nbsp;秒";
		}
		$("#hastime").html(hastime);
		totalTime--;
		timehandler = setTimeout('goThread()',1000);
		
		if(totalTime%120 == 0){
			saveExamResult();
		}
	}if(totalTime==0){
		$("#hastime").html("时间到了");
		jConfirm("考试时间结束，您是否要保存您的成绩?","成绩保存",function(result){
			//取消定时函数
			clearTimeout(timehandler);
			
			if(result){
				paperSubmit();
			}else{
				//关闭考试页面
				window.close();
			}
		});
	}
}

/**
 * 答题卡的放大与缩小
 */
function disb(){
	var src = $("#mg").attr("src");
	var nn =src.lastIndexOf('/');
	if(src.substr(nn+1)=="down.gif"){
		$("#mg").attr("src", webRoot + "/img/up.gif");
		$("#mg").attr("title", "");
		$("#disnb1").css("display","none");
		$("#disnb2").css("height","0");
		$("#disnb4").css("display", "none");
		answerCardScroll();
	}else{
		$("#mg").attr("src", webRoot + "/img/down.gif");
		$("#mg").attr("title", "");
		$("#disnb1").css("display","block");
		$("#disnb2").css("height","37px");
		$("#disnb4").css("display", "block");
		answerCardScroll();
	}	
}

/**
 * 保存考试结果
 */
function saveExamResult(){
	
	//客观题直接获取所有选择的Id
	var ids = "";
	$("input[name^=result]").each(function(){
		var type = $(this).attr("type");
		if(this.checked){
			//单选和判断题
			if(type == "radio" || type == "checkbox"){
				ids += $(this).val() + ",";
			}
		}
	});
	
	if(ids.length > 0){
		ids = ids.substring(0, ids.length - 1);
	}
	var data = "ids=" + ids + "&";
	//主观题获取题目Id和答案
	$("textarea").each(function(){
		var id = $(this).attr("id");
		//Id="textarea_"+questionId
		var questionId = id.substring(9);
		var value = $(this).val();
		if(value != undefined && value != null && $.trim(value) != ""){
			data += "answers=" + questionId + "," + value + "&";
		}
	});
	
	data += "examPaper.guid=" + $("#paperGuid").val();
	$.post(webRoot + "/examPaper!saveExamResult.action", data, function(response){
		if(response.result != 0){
			jAlertWarn("考试答案不能自动保存", null);
		}
	},"json");
}

/**
 * 交卷函数
 */
function paperSubmit(){
	showWaitBar();
	//客观题型
	var objective = 1;
	//主观题型
	var subjective = 2;
	var data = "";
	var map = new HashMap();
	$("input[name^=result]").each(function(){
		var type = $(this).attr("type");
		var name = $(this).attr("name");
		var questionId = name.substring(6);
		if(this.checked){
			//单选和判断题
			if(type == "radio"){
				data += "answers=" + objective + "," + questionId + "," + $(this).val() + "&";
				//多选题型将各答案连接
			}else if(type == "checkbox"){
				if(map.containKey(questionId)){
					map.put(questionId, map.get(questionId) + "-" + $(this).val());
				}else{
					map.put(questionId, $(this).val());
				}
			}
		}
	});
	
	//多选题
	var keyArray = map.keySet();
	for(var index in keyArray){
		data += "answers=" + objective + "," + keyArray[index] + "," + map.get(keyArray[index]) + "&";
	}
	
	$("textarea").each(function(){
		var id = $(this).attr("id");
		//Id="textarea_"+questionId
		var questionId = id.substring(9);
		var value = $(this).val();
		if(value != undefined && value != null && $.trim(value) != ""){
			data += "answers=" + subjective + "," + questionId + "," + value + "&";
		}
	});
	
	data += "examPaper.guid=" + $("#paperGuid").val(); 
	$.post(webRoot + "/examPaper!saveFinalExamResult.action", data, function(response){
			if(response.result==1){ 
				jAlertError('添加失败！');
			}
			else{
				hiddenWaitBar();
				jAlertSuccess(response.msg);
				location.href = webRoot + "/examPaper!showExamResult.action?examPaper.guid=" + $("#paperGuid").val(); 
			}
		
	},"json");
}

/**
 * 定义键值唯一的Map类
 */
function HashMap(){
	this.key = new Array();
	this.value = new Array();
	
	this.get = function(key){ 
		for(var i=0;i<this.key.length;i++){
			if(this.key[i] == key){
				return this.value[i];
			}
		}
	};
	
	this.put = function(key,value){
		var i = 0;
		for(; i < this.key.length; i++){
			if(this.key[i] === key){
				break;
			}
		}
		if(i < this.key.length){
			this.value[i] = value;
		}else{
			this.key.push(key);
			this.value.push(value);
		}
	};
	
	this.containKey = function(key){
		for(var i=0;i<this.key.length;i++){
			if(this.key[i] == key){
				return true;
			}
		}
		return false;
	};
	
	this.keySet = function(){
		return this.key;
	};
	
	this.valueSet = function(){
		return this.value;
	};
}

/**
 * 手动提交试卷函数
 */
function manualsubmit(){
	var noAnswerCount = parseInt($("#examCount").html());
	var msg = "";
	if(noAnswerCount == 0){
		msg = "您确认要交卷吗？";
	}else{
		msg = "还有" + noAnswerCount + "道题未答，您确认要提交吗？";
	}
	
	jConfirm(msg,"交卷提示",function(result){
		if(result){
			//取消定时函数
			clearTimeout(timehandler);
			paperSubmit();
		}
	});
}

/**
 * 加载学生的答题状态
 */
function fillAnswer(parameter){
	var paperGuid = $("#paperGuid").val();
	if(parameter == undefined || parameter == null || $.trim(parameter) == ""){
		$.post(webRoot + "/examPaper!getRemainTime.action", "examPaper.guid="+paperGuid, function(response){
			if(response.result == 0){
				var data = response.data;
				if(data.time != undefined && data.time != null && data.time > 0){
					totalTime = data.time;
					goThread();
				}else{
					$("#hastime").html("考试已结束");
				}
			}
		},"json");
		return ;
	}
	
	showWaitBar();
	$.post(webRoot + "/examPaper!getStudentAnswerStatus.action", "examPaper.guid="+paperGuid, function(response){
		if(response.result == -1){
			jAlertWarn("您的答题记录未能加载", null);
		}else{
			//客观题,赋值的同时计算未答题数
			var noAnswerCount = parseInt($("#examCount").html());
			var data = response.data;
			if(data.ids != undefined && data.ids != null){
				var ids = data.ids.split(",");
				var map = new HashMap();
				for(var i in ids){
					var obj = $("#" + ids[i]);
					//选中
					obj.attr("checked",true);
					//答题卡改变背景色
					var name = obj.attr("name");
					var questionId = name.substring(6);
					$("#td"+questionId).css("background", "#dadada");
					//根据类型计算未答题数
					var type = obj.attr("type"); 
					//单选题未答题数直接减一
					if(type === "radio"){
						noAnswerCount--;
					}else if(type === "checkbox"){
						//多选题根据问题的Id判断是否是同一道题
						var name = obj.attr("name");
						var questionId = name.substring(6);
						if(!map.containKey(questionId)){
							map.put(questionId, "");
							noAnswerCount--;
						}
					}
				}
			}
			//剩余时间
			if(data.time != undefined && data.time != null && data.time > 0){
				totalTime = data.time;
				goThread();
			}else{
				$("#hastime").html("考试已结束");
			}
			//客观题，赋值的同时计算未答题数
			if(data.detail != undefined && data.detail != null){
				for(var i in data.detail){
					$("#textarea_" + data.detail[i].questionId).val(data.detail[i].content);
					$("#td" + data.detail[i].questionId).css("background", "#dadada");
					noAnswerCount--;
				}
			}
			$("#examCount").html(noAnswerCount);
			hiddenWaitBar();
		}
	},"json");
}
