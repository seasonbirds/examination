/**
 * 用于显示文本框下面的浮层.
 * tips: 单选框后一定要紧接着一个label,
 *  如<input type="radio" id="radio31" value="111">
 *	  <label for="radio31">111</label>
 * @example:         $("#pick_radio").pick({type:"radio",pick_div:$("#pick_radio_div"),name:"user.radio",init_value:"555"}); 
 * @param type input类型，现支持两种 radio 和 checkbox
 * @param pick_div 弹出浮层对象，为jQuery对象  
 * @param name 赋值给浮沉里input类型的组件的name属性,传值给后台和检验用，如果检验的文本框的name为在param name前面加个”check_“字符串,
 * @example:
 * "check_user.radio":{required:true}, 
 * "check_user.checkbox":{min:1} 
 *  其中 radio 要检验的是个文本框，里面的值为被选中的元素的展示值， 
 *  checkbox  要检验的是个文本框，里面的值为被选中的个数
 * @param init_value  浮层选中的初始值             
 */
(function($){	
    var pick_number = 0; //标记浮层，用于关闭浮层用
	var pick_click = 0; //点击在哪个浮层上，0为浮层之外的位置
	
	$.fn.pick = function(options) {	
		pick_number += 1;
		var defaults = {
			pick_number:pick_number,
			target_text:$(this),
			type:"radio"
		};
		var opts = $.extend(defaults, options);
		
		if(opts.type == "radio"){
			radio_pick(opts);	
		}		
		if(opts.type=="checkbox"){			
			checkbox_pick(opts);
		}
		
		//单击目标文本框，文本框下方出现浮层
		opts.target_text.on('click',function(){
			offset = opts.target_text.offset(); 
			height =  opts.target_text.height();
			opts.pick_div.css({'left':offset.left,'top':offset.top + height + 2}).show();
			pick_click = opts.pick_number;	//点击时置为该浮层的标记值，说明点击的是该浮层，等冒泡到document时，作为是否关闭浮层标志使用
		});
			
		/**** 点击浮层以外的地方，浮层消失  start ***/		
		opts.pick_div.click(function(){
			pick_click = opts.pick_number;	//点击时置为该浮层的标记值，说明点击的是该浮层，等冒泡到document时，作为是否关闭浮层标志使用	
		});		
		$(document).on('click',function(){
			if(pick_click != opts.pick_number){ //点击的点不在这个浮层上
				opts.pick_div.hide();  
			}else{
				pick_click = 0;
			}
		});	
		/**** 点击浮层以后的地方，浮层消失  end ***/		
		
		
	};
	
	function radio_pick(opts){
		//加入要展示和检验的文本框，并设为目标文本框
		name = "";
		if(opts.name != undefined){
			name='name="check_'+ opts.name +'" ' ;		
		}
		input = '<input type="text"'+ name +'readonly="readonly"/>';
		opts.target_text.after(input);
		opts.target_text = opts.target_text.next();//设为目标文本框
		//点击错误提示时弹出浮层
		opts.target_text.parent().on("click","label.error",function(){
			$(this).siblings("input").trigger("click");
			return false;
		});
		
		//选中单选框，把值赋给目标文本框并关闭浮层
		opts.pick_div.on('change',':radio',function(){
			opts.target_text.val($(this).next().html());
			opts.target_text.trigger("focusout"); //触发校验事件
			opts.pick_div.hide();	
		});						
		
		
		/***** 初始化 start ********/
		
		//给这个div下所有的者单选框添加name
		if(opts.name != undefined){
			opts.pick_div.find(":radio").attr("name",opts.name);
		}
		
		$checked_radio = opts.pick_div.find(":radio[value="+ opts.init_value +"]");//得到初始值对应的radio
		if($checked_radio.length > 0){ // 有对应初始值的radio存在
			$checked_radio.prop("checked",true);//根据初始值选中单选框
			opts.target_text.val($checked_radio.next().html());//根据选中的对象，给目标文本框赋值
		}
		
		/***** 初始化 end ********/
	}
	
	
	
	function checkbox_pick(opts){
		//加入要展示和检验的文本框
		name = "";
		if(opts.name != undefined){
			name='name="check_'+ opts.name +'" ' ;		
		}
		input = '<input type="text"' + name + 'class="f_pick_input_checkbox" value="0" style="height:100%; width:0px; border:0;"/>';		
		opts.target_text.after(input);
		
		//点击错误提示时弹出浮层
		opts.target_text.parent().on("click","label.error",function(){
			$(this).siblings(".pick_div_checkbox").trigger("click");
			return false;
		});
		
		//选中单选框，把值赋给目标文本框并关闭浮层
		opts.pick_div.on('change',':checkbox',function(){
			old_height = opts.target_text.height();
			if($(this).prop("checked")){ //复选框选中				
				add_item(opts,$(this));		
			}else{ //复选框取消
				remove_item(opts,$(this));	
			}
			adaptive_height_change(opts,old_height);						
		});	
		
		//点击目标文本框的删除按钮事件，删除本身的文本块和对应的checkbox的值置为false
		opts.target_text.on("click",".pick_delete",function(){
			value = $(this).parent().attr("rel");
			opts.pick_div.find(":checkbox[value="+ value +"]").prop("checked",false);
			input_value_dec(opts);	
			$(this).parent().remove();
		});
		
		
		/***** 初始化 start ********/
		
		//给这个div下所有的复选框添加name
		if(opts.name != undefined){
			opts.pick_div.find(":checkbox").attr("name",opts.name);
		}
		//给这个div赋初始值（json数组），对checkbox进行选中和对目标文本框产生文本块
		if(opts.init_value != undefined){
			for(var i=0; i<opts.init_value.length; i++){
				init_value = opts.init_value[i];
				$checked_checkbox = opts.pick_div.find(":checkbox[value="+ init_value +"]");//得到初始值对应的checkbox
				if($checked_checkbox.length > 0){ // 有对应初始值的checkbox存在
					$checked_checkbox.prop("checked",true);//根据初始值选中复选框
					add_item(opts,$checked_checkbox);//根据选中的对象，给目标文本框赋值
				}
			}
		}
		
		/***** 初始化 end ********/	

	}
	
	//根据$checkbox_object产生目标文本框对应的文本块
	function add_item(opts,$checkbox_object){
		rel = $checkbox_object.val(); 
		show_value = $checkbox_object.next().html();
		new_object = '<a href="javascript:void(0);" rel="'+rel+'" class="pick_item" ><span class="pick_text">'+show_value+'</span><span class="pick_delete"> X</span></a>';
		opts.target_text.append(new_object);
		input_value_inc(opts);	
	}
	
	//根据$checkbox_object删除目标文本框对应的文本块
	function remove_item(opts,$checkbox_object){
		rel = $checkbox_object.val();
		opts.target_text.find("a[rel="+ rel +"]").remove();
		input_value_dec(opts);		
	}
	
	//递增校验框的值
	function input_value_inc(opts){
		$input = opts.target_text.siblings("input");
		$input.val(parseInt($input.val()) + 1);
		$input.trigger("focusout");//触发校验事件
	}
	
	//递减校验框的值
	function input_value_dec(opts){
		$input = opts.target_text.siblings("input");
		$input.val(parseInt($input.val()) - 1);
		$input.trigger("focusout");//触发校验事件
	}
	
	//判断目标文本高度是否发生变化,如果发生变化，则调整浮层位置
	function adaptive_height_change(opts,old_height){		
		new_height = opts.target_text.height(); 
		if(new_height != old_height){//只有当目标文本框的高度前后不一致时才需要调整浮层位置
			offset = opts.target_text.offset(); 
			opts.pick_div.css({'top':offset.top + new_height + 2});
		}		
	}
	
	
	
})(jQuery);