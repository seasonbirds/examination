/**
 * 考试开始时间和结束时间校验，结束时间要在开始时间之后
 * @returns {Boolean}
 */
function validDate(){
	var examStart = $("input[name='examPolicy.examTimeStart']").val();
	var examEnd = $("input[name='examPolicy.examTimeEnd']").val();
	var start = Date.parse(examStart.replace(/-/g,"/"));
	var end = Date.parse(examEnd.replace(/-/g,"/"));
	if(end <= start){
		jAlertError("考试结束时间必须晚于开始时间");
		return false;
	}
	return true;
}
/**
 * 通过分数和总分校验，通过分数必须小于或等于总分
 * @returns {Boolean}
 */
function validScore(){
	var passScore = $("input[name='examPolicy.passScore']").val();
	if(passScore != undefined && passScore != null &&  "" != $.trim(passScore)){
		var totalScore = $("#sum_score").val();
		if(parseInt(passScore) > parseInt(totalScore)){
			jAlertError("通过分数不能大于总分");
			return false;
		}
	}
	return true;
}
/**
 * 试题设置校验：题量和分数只能同时出现，所有题目总分要等于试卷总分，题量不能大于题库的数量
 * @returns {Boolean}
 */
function validQuestionSetting(){
	var result = true;

	//先校验题量的设置是否大于题库的数量
	$(".f_item").each(function(){
		var tmp = true;
		$(this).find(".f_number").each(function(){
			var value = $(this).val();
			if(value != undefined && value != null && "" != $.trim(value)){
				var qnum = parseInt($(this).parent().find("label").html());
				if(parseInt(value) > qnum){
					tmp = false;
					$(this).focus();
					return tmp;
				}
			}
		});
		if(!tmp){
			result = tmp;
			return result;
		}
	});
	
	if(!result){
		jAlertError("题量不能大于题库的数量！");
		return result;
	}
	
	//校验总分、题量和分数是否同时出现
	var total = 0;
	$(".f_item").each(function(){
		var numberEasy = $.trim($(this).find(".f_number.f_easy").val());
		var scoreEasy = $.trim($(this).find(".f_score.f_easy").val());
		if(numberEasy == "" && scoreEasy != ""){
			$(this).find(".f_number.f_easy").addClass("filedError");
			result = false;
		}else if(numberEasy != "" && scoreEasy == ""){
			$(this).find(".f_score.f_easy").addClass("filedError");
			result = false;
		}else if(numberEasy != "" && scoreEasy != ""){
			total += parseInt(numberEasy) * parseInt(scoreEasy);
		}
		
		var numberNormal = $.trim($(this).find(".f_number.f_normal").val());
		var scoreNormal = $.trim($(this).find(".f_score.f_normal").val());
		if(numberNormal == "" && scoreNormal != ""){
			$(this).find(".f_number.f_normal").addClass("filedError");
			result = false;
		}else if(numberNormal != "" && scoreNormal == ""){
			$(this).find(".f_score.f_normal").addClass("filedError");
			result = false;
		}else if(numberNormal != "" && scoreNormal != ""){
			total += parseInt(numberNormal) * parseInt(scoreNormal);
		}
		
		var numberDifficulty = $.trim($(this).find(".f_number.f_difficulty").val());
		var scoreDifficulty = $.trim($(this).find(".f_score.f_difficulty").val());
		if(numberDifficulty == "" && scoreDifficulty != ""){
			$(this).find(".f_number.f_difficulty").addClass("filedError");
			result = false;
		}else if(numberDifficulty != "" && scoreDifficulty == ""){
			$(this).find(".f_score.f_difficulty").addClass("filedError");
			result = false;
		}else if(numberDifficulty != "" && scoreDifficulty != ""){
			total += parseInt(numberDifficulty) * parseInt(scoreDifficulty);
		}
	});
	
	if(result){
		if(total != $("#sum_score").val()){
			jAlertError("题目设置的分数与试卷总分不一致");
			result = false;
		}
	}
	return result;
}
/**
 * 增加或者更新策略提交函数
 * @returns {Boolean}
 */
function submitPolicy(isCreatePaper){
	
	if ($("#tb_input").valid() && validQuestionSetting() && validDate() && validScore()) {
		showWaitBar();
		var queryString = $('#tb_input').formSerialize();
		
		//消除simId和simRatio参数
		if($("#simId").length > 0){
			queryString = queryString.replace(/&simId=\d*|&simRatio=\d*/g,"");
		}
		
		$(".f_item").each(function(){
			var trId = $(this).attr("id");
			var type = trId.split("_")[1];
			var numberEasy = $.trim($(this).find(".f_number.f_easy").val());
			var scoreEasy = $.trim($(this).find(".f_score.f_easy").val());
			if(numberEasy != "" && scoreEasy != ""){
				queryString += "&policyDetails=" + type + ',easy,' + numberEasy + "," + scoreEasy;
			}
			
			var numberNormal = $.trim($(this).find(".f_number.f_normal").val());
			var scoreNormal = $.trim($(this).find(".f_score.f_normal").val());
			if(numberNormal != "" && scoreNormal != ""){
				queryString += "&policyDetails=" + type + ',medium,' + numberNormal + "," + scoreNormal;
			}
			
			var numberDifficulty = $.trim($(this).find(".f_number.f_difficulty").val());
			var scoreDifficulty = $.trim($(this).find(".f_score.f_difficulty").val());
			if(numberDifficulty != "" && scoreDifficulty != ""){
				queryString += "&policyDetails=" + type + ',hard,' + numberDifficulty + "," + scoreDifficulty;
			}
		});
		
		//queryString += "&examPolicy.scopeId=" + $("#scopeId").val();
		if(isCreatePaper != undefined && isCreatePaper != null){
			queryString += "&isCreatePaper="+isCreatePaper;
		}
		
		var otherExamInfo = $("#simId").val();
		if(otherExamInfo != undefined && otherExamInfo != null){
			queryString += "&otherExamInfo=" + otherExamInfo+"," + $("#simRatio").val();
		}
		$.post("examPolicy!save.action", queryString, function(response){
			hiddenWaitBar();
			if(response.result != -1){
				$.colorbox.close();
				jAlertSuccess(response.msg);
				pageQuery();
			}else{
				jAlertError(response.msg);
			}
		},"json");
		
	}else{
		return false;
	}
}
/**
 * 根据对话框类型打开对话框
 * @param type
 * @param id
 */
function showPolicydialog(type, id){
	if(type != "show" && type != "add" && type != "edit"){
		return;
	}
	
	var url = "examPolicy!toCreateOrUpdate.action?dialogType=" + type + "&examPolicy.examType=" + $("#examType").val() + "&examPolicy.scopeId=" + $("#scopeId").val();
	if(id != undefined && id != null){
		url += "&examPolicy.id=" + id;
	}
	$.colorbox({
		href:url
	});
}
/**根据策略id获取题目设置信息
 * 
 */
function loadQuestionSetting(id){
	if(id == undefined || id == null || $.trim(id) == ""){
		return;
	}
	
	showWaitBar();
	$.post("examPolicyDetail!getPolicyDetails.action","examPolicyDetail.examPolicyId="+id, function(response){
		if(response.result == "success"){
			var data = response.data;
			var judgeNum=0,singleNum=0,multNum=0,clozeNum=0,explainNum=0,qnqNum=0;
			for(var index in data){
				var item = data[index];
				var type = item.type.toLowerCase();
				switch(type){
					case "judge":
						judgeNum += item.questionCount;
						break;
					case "single":
						singleNum += item.questionCount;
						break;
					case "mult":
						multNum += item.questionCount;
						break;
					case "cloze":
						clozeNum += item.questionCount;
						break;
					case "explain":
						explainNum += item.questionCount;
						break;
					case "qnq":
						qnqNum += item.questionCount;
						break;
					default:
						return;
				}
				var level = "f_";
				if(item.level == "EASY"){
					level += "easy";
				}else if(item.level == "MEDIUM"){
					level += "normal";
				}else if(item.level == "HARD"){
					level += "difficulty";
				}
				$("#tr_" + type).find(".f_number."+level).val(item.questionCount);
				$("#tr_" + type).find(".f_score."+level).val(item.perScore);
			}
			
			$("#judge_number").html(judgeNum);
			$("#single_number").html(singleNum);
			$("#mult_number").html(multNum);
			$("#cloze_number").html(clozeNum);
			$("#explain_number").html(explainNum);
			$("#qnq_number").html(qnqNum);
			setNumberAndScore("easy");
			setNumberAndScore("normal");
			setNumberAndScore("difficulty");
			setTotal();
		}else{
			jAlertError("获取题目设置信息出错");
		}
		hiddenWaitBar();
	},"json");
}
/**
 * 过滤无关的字符
 * @param event
 */
function filterKey(event){
	if(event.which == 13 || event.which == 9){//换行和tab
	}else if(47<event.which &&  event.which < 58 ){//大键盘数字
	}else if(95<event.which &&  event.which < 106 ){//小键盘数字
	}else if(8 == event.which || event.which == 46 ){//删除
	}else if(36<event.which &&  event.which < 41 ){//方向键
	}else{
		event.preventDefault();
	}	
}

//重新计算总分
function setTotal(){
	number = parseInt($("#easy_number").html()) + parseInt($("#normal_number").html()) + parseInt($("#difficulty_number").html());
	score = parseFloat($("#easy_score").html()) + parseFloat($("#normal_score").html()) + parseFloat($("#difficulty_score").html());
	$("#total_number").html(number);	
	$("#total_score").html(score);
}

//获取单项统计
function setNumberAndScore(level){
	number = 0;
	score = 0;
	$(".f_item_list tr.f_item").each(function(index, element) {
		item_number = $(this).find(".f_number.f_"+level).val();
		if(item_number != "" && item_number != "0"){
			number += parseInt(item_number);
			item_score = $(this).find(".f_score.f_"+level).val();
			if(item_score != "" && item_score != "0"){
				score += parseFloat(item_score) * parseInt(item_number);
			}
		}
	});	
	$("#"+ level +"_number").html(number);
	$("#"+ level +"_score").html(score);
}
