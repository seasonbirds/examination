/** * 全选js功能 start*** */
function toggleSelect() {
	var c = $(".f_tblist").find('input[type=checkbox]')[0];
	if (c.checked) {
		selectAll();
	} else {
		unselectAll();
	}
};

function selectAll() {
	var checks = $(".f_tblist").find('input[type=checkbox]');
	for ( var i = 0; i < checks.length; i++) {
		var c = $(checks[i]);
		c.prop('checked', true);
	}
};

function unselectAll() {
	var ckecks = $(".f_tblist").find('input[type=checkbox]');
	for ( var i = 0; i < ckecks.length; i++) {
		var c = $(ckecks[i]);
		c.prop('checked', false);
	}
};
/** * 全选js功能 end*** */

// 重新设置左边菜单高度
function reset_left_content_height() {
	if ($('#left_content').offset().top + $('#left_content').height() > $(window).height()){
		$('#left_content').height(400);// 先变小，防止因为left_content撑大document的高度
	}
	height_more = 0;
	if (!$.support.leadingWhitespace) { // 是否ie6 - 8
		height_more = 4;
		if ($(window).width() < $('body').width()) { // ie横向滚动条
			height_more += 18;
		}
	}
	height = $(document).height() - $('#left_content').offset().top - 8
			- height_more;// 减去8是因为$('#left_content')的padding-top 为 8px
	min_height = $("#sidebarmenu").height() + 112; //112为图片的高度
	height = height > min_height ? height : min_height;
	if ($('#left_content').height() != height) {
		$('#left_content').height(height);
		$(window).unbind("resize",reset_left_content_height);
		setTimeout('$(window).bind("resize",reset_left_content_height);',50);
	}
};

/**
 * 把以逗号分隔的字符串参数转化为数组
 */
function createParamStr(str, splitStr) {
	var paramArray = new Array();
	if (str == null || str.toString() == "") {
		return null;
	} else if (str.toString().indexOf(splitStr) == -1) {
		paramArray[0] = str;
	} else {
		var s = str.toString().split(splitStr);
		$.each(s, function(i, v) {
			paramArray[i] = v;
		});
	}
	return paramArray;
}

// 添加ajax=true的参数
function append_ajax_true(url) {
	url += url.indexOf("?") == -1 ? '?' : '&';
	url += 'ajax=true';
	return url;
}

// 添加请求的页码参数
function append_page_no(url, page_no) {
	url += url.indexOf("?") == -1 ? '?' : '&';
	url += 'page.pageNo=' + page_no;
	return url;
}

//扩展string函数,添加url参数
String.prototype.appendUrlParam = function(name,value) {
	url = this.toString();
	url = url + (url.indexOf("?") == -1 ? '?' : '&');
	url = url + name + "=" + value;
	return url;
};

// 扩展string函数,这个函数是否以s开头
String.prototype.startWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substr(0, s.length) == s)
		return true;
	else
		return false;
	return true;
};

// 刷新tblist
// result_data:ajax返回的数据 fromColorbox:调用该函数的组件是否在colorbox上，默认false
function refresh_page(result_data, fromColorbox) {
	if (is_succeed_result(result_data, fromColorbox)) { // 正常响应
		if (fromColorbox) {
			$.colorbox.close();
			setTimeout(function() {
				$('#right_content').html(result_data);
				reset_left_content_height();
			}, 200);
		} else {
			$('#right_content').html(result_data);
			reset_left_content_height();
		}

	}
}

// 刷新tblist
// result_data:ajax返回的数据 fromColorbox:调用该函数的组件是否在colorbox上，默认false
function refresh_tblist(result_data, fromColorbox) {
	if (is_succeed_result(result_data, fromColorbox)) { // 正常响应
		if (fromColorbox) {
			$.colorbox.close();
			setTimeout(function() {
				$('#query_result').html(result_data);
				reset_left_content_height();
			}, 200);
		} else {
			$('#query_result').html(result_data);
			reset_left_content_height();
		}

	}
}

// 判断ajax 返回来的结果是否有异常，没有返回true，有异常就立即处理掉
// result_data:ajax返回的数据 fromColorbox:调用该函数的组件是否在colorbox上，默认false
function is_succeed_result(result_data, fromColorbox) {
	if (typeof (fromColorbox) == "undefined") {
		fromColorbox = false;
	}
	if (result_data.startWith('<!-- notice -->')) {// 捕获的异常
		if (fromColorbox) {
			$(result_data).insertAfter(".box_title");
		} else {
			$('#error_tip').prepend(result_data);
			reset_left_content_height();
		}
		return false;
	} else if (result_data.startWith('<!-- error -->')) {// 未捕获的异常
		if (fromColorbox) {
			$(".box_title").parent().html(result_data);
		} else {
			if($('#tblist_content').length > 0){ //优先使用tblist_content包装错误
				$('#tblist_content').html(result_data);
			}else{
				$('#query_result').html(result_data);
			}
			reset_left_content_height();
		}
		return false;
	}
	return true;
}

//wait等待事件
$(document).on('click','.wait',function(event){
	$(this).tip({content:"正在处理，请等待"});	
});

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
