/*
 * 用于下拉框级联操作
 * tips:
 * 1:只能作用于select组件
 * 2: select 属性 data-to 和 data-from 里的值是用separator隔开的id值，separator之间不能有空格
 * 3：目标select的option 属性 data-group 里的值是用separator隔开，separator之间不能有空格值,用separator隔开的值为源select的option的值，如果没有对应的值，则使用空字符串"",代表对应所有的值，如果目标select的option对应多个源select的option值，用这个分隔符隔开，默认加号
 * 4：data-group的值 与 data-from的值相对应，顺序一致,个数一致
 * @param separator 分隔符，默认英文逗号
 * @param group_separator 如果目标select的option对应多个源select的option值，用这个分隔符隔开，默认加号
 * @param form_reset_button form表单reset按钮的选择器，附加一个点击事件，可以重置级联的值，默认"#query_reset"
 * @example:
 * $('.f_cascade_select').cascadeSelect();
 * @demo /nec/cascade-select-demo.html
 */
(function($) {
	// 只要选择源select就可以，目标select不用选择
	jQuery.fn.cascadeSelect = function(options) {
		var theOptions = $.extend({
			separator : ",",
			group_separator : "+",
			form_reset_button : "#query_reset"
		}, options);
		theOptions.$this = $(this);//用theOptions是为了当代码多次调用cascadeSelect时，$this不会混乱
		create_assist_dom(theOptions.$this, theOptions);// 创建辅助节点，来保存对应节点的option的值
		theOptions.$this.change(function() { // 创建源select的值改变事件，引起联动
			cascade_select($(this), theOptions);//$(this)不能改为theOptions.$this，因为此时的this是当前触发事件的元素
		});
		
		init_select(theOptions.$this,theOptions);//初始化
		//重置引起联动
		$(theOptions.form_reset_button).click(function(){
			restore_select(theOptions.$this, theOptions);
			init_select(theOptions.$this,theOptions);
		});
	};
	
	//初始化
	function init_select($this,theOptions){
		$this.each(function(index, element) {$(this).triggerHandler('change');});
	}
	
	//还原节点
	function restore_select($this, theOptions) {
		$this.each(function(index, element) {
			array_to = $(this).data("to").split(theOptions.separator);
			//从辅助节点复制到对应节点
			for ( var j = 0; j < array_to.length; j++) {
				selector = $.trim(array_to[j]);
				if (selector == "") {
					continue;
				}
				$selector = $("#" + selector);
				$selectorAssist = $("#c_" + selector);
				if ($selector.length > 0 && $selector.children().length != $selectorAssist.children().length) {
					$selector.empty();	
					$selectorAssist.children().clone().appendTo($selector);
				}
			}				
		});
	}

	// 创建辅助节点，来保存对应节点的option的值
	// ，联动时，将清空目标select的option节点，然后通过选择符合条件的辅助节点的option节点插入目标select
	function create_assist_dom($this, theOptions) {
		$this.each(function(index, element) {
			array_to = $(this).data("to").split(theOptions.separator);
			for ( var j = 0; j < array_to.length; j++) {
				selector = $.trim(array_to[j]);
				if (selector == "") {
					continue;
				}
				$selectorAssist = $("#c_" + selector);
				if ($selectorAssist.length == 0) {// 不存在辅助节点，则创建辅助节点
					$selector = $("#" + selector);
					if ($selector.length > 0) {
						$children = $selector.children().clone();
						$("<select></select>").attr("id", "c_" + selector)
								.insertAfter($selector).hide();
						$children.appendTo("#c_" + selector);
					}
				}
			}
		});
	}

	// 联动操作 $change_select：源select
	function cascade_select($change_select, theOptions) {
		data_to = $change_select.data("to");
		if (data_to === undefined || data_to == "") {
			alert("错误1：事件源 select  的 data-to 配置错误;select id:"+$change_select.attr("id"));
			return;
		}
		array_to = data_to.split(theOptions.separator); // 获取目标select
		for ( var j = 0; j < array_to.length; j++) {// 遍历目标select ，联动目标的select
			// 获取目标select的jquery对象 start
			selector = $.trim(array_to[j]);
			if (selector == "") {
				continue;
			}
			$selector = $("#" + selector);
			if ($selector.length == 0) {
				alert("错误2：事件源 select  的 data-to 配置错误;select id:"+$change_select.attr("id"));
				return;
			}
			// 获取目标select的jquery对象 end
			filterStringArray = new Array();
			data_from = $selector.data("from");
			if (data_from === undefined || data_from == "") {
				alert("错误3：目标 select  的 data-from 配置错误;select id:"+$selector.attr("id"));
				return;
			}
			array_from = data_from.split(theOptions.separator);
			for ( var k = 0; k < array_from.length; k++) {// 遍历目标select，获取对应的值
				from_selector = $.trim(array_from[k]);
				if (from_selector == "") {
					alert("错误4：目标 select  的 data-from 配置错误;select id:"+$selector.attr("id"));
					return;
				}
				// 获取源select的值，压入数组用，用来比较目标select的option的data-group的值
				filter_value = $("#" + from_selector).val();
				filterStringArray.push(filter_value);
			}
			change_item_show($selector, filterStringArray, theOptions);// 级联操作
		}
	}

	// 单个级联操作
	// 先使用辅助节点获取符合条件的option节点，然后改节点清空，插入获取的option节点
	function change_item_show($item_parent, filterStringArray, theOptions) {
		if (filterStringArray.length == 0) {
			return;
		}
		// 获取辅助节点的孩子节点
		$item_parent_assist = $("#c_" + $item_parent.attr("id"));
		$children = $item_parent_assist.children();
		// 遍历孩子节点，符合条件则加class，否则去掉class,class作为符不符合条件的标记
		$children.each(function(index, element) {
			stringArray = $.trim($(this).data('group')).split(
					theOptions.separator);
			// 判断是否符合条件
			is_equal = compare_string($(this),stringArray, filterStringArray,
					theOptions);
			// 加减标记
			if (is_equal) {
				$(this).addClass('selected_item');
			} else {
				$(this).removeClass('selected_item');
			}
		});
		// 确定选中的元素，如果符合条件，已经被选中，则保持，否则第一个将被选中
		$selected = $item_parent.find(':selected');
		$option = $item_parent_assist.children('.selected_item');
		$first = $option.first();// 第一个
		is_trigger = true;// 是否要触发值改变事件标记
		if ($selected.length > 0) {
			$selected_option = $option
					.filter("[value=" + $selected.val() + "]");
			if ($selected_option.length > 0) {
				$first = $selected_option;
				is_trigger = false;
			}
		}
		$first.prop('selected', true);
		// 插入节点
		$item_parent.empty();
		$option.clone().appendTo($item_parent);
		$item_parent.val($first.val());
		if (is_trigger) {// 触发值改变事件
			$item_parent.triggerHandler('change');
		}
	}
	// 判断是否符合条件，值一样则符合，空字符串代表符合所有
	function compare_string($this,stringArray, filterStringArray, theOptions) {
		if (stringArray.length != filterStringArray.length) {
			alert("错误5：目标 select 的 option 的data-group 配置错误;select id:"+$this.parent().attr("id") + ",option value:"+$this.val());
			return false;
		}
		if (stringArray.length == 0) {
			return true;
		}
		// 比较数组里的每一个值，只有全部对应上，才算符合条件
		for ( var i = 0; i < stringArray.length; i++) {
			if (stringArray[i] == "" || filterStringArray[i] == "") {// ，空字符串代表符合所有
				continue;
			} else if (stringArray[i] == filterStringArray[i]) {
				continue;
			} else {
				if(stringArray[i].indexOf(theOptions.group_separator) > 0){ //代表了多个值
					group_value = stringArray[i].split(theOptions.group_separator);	
					in_group = false;//是否包含过滤值
					//遍历数组，判断是否包含过滤值
					for(var n = 0 ; n < group_value.length; n++){
						if (group_value[n] == filterStringArray[i]) {
							in_group = true;
							break;
						}	
					}
					if(in_group){//包含该值，则继续循环
						continue;
					}
				}
				return false;
			}//else
		}//for
		return true;
	}

})(jQuery);
