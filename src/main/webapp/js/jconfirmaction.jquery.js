/*
 * jQuery Plugin : jConfirmAction
 * 
 * by Hidayat Sagita
 * http://www.webstuffshare.com
 * Licensed Under GPL version 2 license.
 *
 * 用于显示 确认框， 鼠标点击提示之外的内容或者点击浮层内按钮 确认框自动消失
 * @param question 确认框内容
 * @param yesAnswer 确认按钮的文字内容
 * @param cancelAnswer 取消按钮的文字内容
 * @param extClass  给确认框添加class,现支持long-bg,bubble_left,long_left
 * @param yesAction 点击确认按钮之后的回调函数，此函数有一个参数，为调用这个确认框的jquery对象
 * @example:
 * $('.f_single_delete').jConfirmAction({
 *		question:"确定删除该用户？",
 *		yesAnswer:"是",cancelAnswer:"否",
 *		yesAction:function($this){
 *			$.ajax({
 *				type:"post",
 *				url:append_ajax_true($this.attr("href")),
 *				success: function(){
 *					refleshListPage();
 *				},
 *				error:function(){
 *					}				
 *			});
 *		}
 *	});
 */
(function($){
	var global_confirm_index = 0,jConfirm_click = 0; //点击页面除了浮层之外的其他地方，浮层消失
	jQuery.fn.jConfirmAction = function (options) {
		
		// Some jConfirmAction options (limited to customize language) :
		// question : a text for your question.
		// yesAnswer : a text for Yes answer.
		// cancelAnswer : a text for Cancel/No answer.
		var theOptions = jQuery.extend ({
			question: "Are You Sure ?",
			yesAnswer: "Yes",
			cancelAnswer: "Cancel",
			extClass:'bubble_left',
			yesAction:function(){},
			cancelAction:function(){}
		}, options);
		
		return this.each (function () {
			global_confirm_index++;
			$(this).data("index",global_confirm_index);
			$(this).bind('click', function(e) {

				e.preventDefault();
				$this	= $(this);
				jConfirm_click = $this.data("index"); //点击按钮时，赋值给浮层点击标记，使对应的浮层不隐藏
				if($(this).next('.question').length <= 0){
					$(this).after('<div class="question">'+theOptions.question+'<br/> <span class="yes">'+theOptions.yesAnswer+'</span><span class="cancel">'+theOptions.cancelAnswer+'</span></div>');	
					$this.next('.question').addClass(theOptions.extClass);
					
					$next_question = $this.next('.question');
					setPosition($next_question,$this);//调整确认框出现的位
					
					/****  控制浮层是否隐藏 start ************************/
					$(document).on('click',{$target_node:$this},function(event){
						if(jConfirm_click != event.data.$target_node.data("index")){ //点击的点不在这个浮层上
							event.data.$target_node.next('.question').hide();  
						}else{
							jConfirm_click = 0;
						}
					});	
					$this.next('.question').on('click',{$target_node:$this},function(event){
						jConfirm_click = event.data.$target_node.data("index");	
					});
					/****  控制浮层是否隐藏 end ************************/
					
				
				    //点击 yesAnswer
					$(this).next('.question').children('.yes').bind('click', function(){
						theOptions.yesAction($this);
						$(this).parents('.question').hide();
					});
	                 
					//点击 noAnswer			
					$(this).next('.question').children('.cancel').bind('click', function(){
						theOptions.cancelAction($this);
						$(this).parents('.question').hide();
					});
					
					$(this).next('.question').animate({opacity: 1}, 300); //显示确认框

				}else{
					$next_question = $this.next('.question');
					setPosition($next_question,$this);//调整确认框出现的位置
					$next_question.css('display','').animate({opacity: 1}, 300);	//显示确认框
				}
			});
			
		});
	};
	
	//调整确认框出现的位置
	function setPosition($next_question,$this){
		width = $next_question.width();
		height = $next_question.height();
		this_width=$this.width();
		offset = $this.position(); 
		width_change = 0;
		if($next_question.hasClass("bubble_left")){ //箭头在右边，所以要减去浮层一半的长度
			width_change = width/2 - 17;	
		}
		if($next_question.hasClass("long_left")){ //箭头在右边，所以要减去浮层一半的长度
			width_change = width/2 - 17;	
		}
		$next_question.css({'left':offset.left - width/2 + this_width/2 + 2  - width_change,'top':offset.top - height - 10}); //-10 是因为.question 的padding-top为10px
	}
	
})(jQuery);