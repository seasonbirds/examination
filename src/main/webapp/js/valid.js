$(function () {
	jQuery.validator.addMethod("pattern", function(value, element, param) {       
		 return this.optional(element) || param.test(value);       
	}); 
	//双方必须是数字，小于或等于对方
	jQuery.validator.addMethod("lessOrEqualThan", function(value, element, toElementexpression) {  
		if(this.optional(element)){
			return true;
		}
		$toElement = $(""+toElementexpression); //获取目标对象 
		if($toElement.length == 0){//目标对象不存在
			return false;
		}
		tovalue = $toElement.val();
		if(tovalue == ""){
			return true;
		}
		tovalueInt = parseInt(tovalue,10);
		if(tovalueInt == NaN){
			return false;
		}
		if(value == ""){
			return true;
		}
		valueInt = parseInt(value,10);
		if(valueInt == NaN){
			return false;
		}
		if(valueInt <= tovalueInt){
			return true;
		}else{
			return false;
		}		
	},"请输入一个比目标小的整数"); 
	
	//双方必须是浮点数，小于或等于对方
	jQuery.validator.addMethod("floatLessOrEqualThan", function(value, element, toElementexpression) {  
		if(this.optional(element)){
			return true;
		}
		$toElement = $(""+toElementexpression); //获取目标对象 
		if($toElement.length == 0){//目标对象不存在
			return false;
		}
		tovalue = $toElement.val();
		if(tovalue == ""){
			return true;
		}
		tovalueFloat = parseFloat(tovalue,10);
		if(tovalueFloat == NaN){
			return false;
		}
		if(value == ""){
			return true;
		}
		valueFloat = parseFloat(value,10);
		if(valueFloat == NaN){
			return false;
		}
		if(valueFloat <= tovalueFloat){
			return true;
		}else{
			return false;
		}		
	},"请输入一个比目标小的浮点数"); 
	
	
	//双方必须是浮点数，小于或等于对方,比较的两个元素在同一个tr下
	jQuery.validator.addMethod("floatLessOrEqualThanInSameTr", function(value, element, toElementexpression) {  
		if(this.optional(element)){
			return true;
		}
		$toElement = $(element).closest("tr").find(""+toElementexpression); //获取目标对象 
		if($toElement.length == 0){//目标对象不存在
			return false;
		}
		tovalue = $toElement.val();
		if(tovalue == ""){
			return true;
		}
		tovalueFloat = parseFloat(tovalue,10);
		if(tovalueFloat == NaN){
			return false;
		}
		if(value == ""){
			return true;
		}
		valueFloat = parseFloat(value,10);
		if(valueFloat == NaN){
			return false;
		}
		if(valueFloat <= tovalueFloat){
			return true;
		}else{
			return false;
		}		
	},"请输入一个比目标小的浮点数"); 
	
	
	// 校验含中文字符的字符串长度       
	jQuery.validator.addMethod("charRangeLength", function(value, element, param) {       
		 var length = value.length;       
		 for(var i = 0; i < value.length; i++){       
			 if(value.charCodeAt(i) > 127){       
				 length++;       
			 }       
		 }       
		 return this.optional(element) || ( length >= param[0] && length <= param[1] );       
	}, "请输入一个长度介于 {0} 和 {1} 之间的字符串"); 
	
	jQuery.validator.addMethod("urlWithoutDomain", function( value, element ) {
		if(value.length > 0 && value.substr(0,1) != "/"){//必须以'/'开头
			return false;
		}
		value = "http://jqueryvalidation.org"+value;//补上url domain
		// 以下部分copy validator 的url 验证代码
		return this.optional(element) || /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
	});
});