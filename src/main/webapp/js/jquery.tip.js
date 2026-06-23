/**
 * 用于显示 小提示， 鼠标在小提示之外持续1.5秒或者鼠标点击提示之外的内容 小提示自动消失
 * tips: 此插件会自动给截取的字符串外套一个span标签,
 * @param content 提示的内容
 * @example:
 * 1)$(this).tip({content:"succeed"});
 */
(function($){
	var tip_click = 0; //点击页面除了浮层之外的其他地方，浮层消失
	var tip_timeout  = ""; //自动消失函数，此变量用来取消函数实现
	var disappear_time = 2000; //小提示自动消失时间，单位毫秒
	jQuery.fn.tip = function (options) {
		var theOptions = jQuery.extend ({
			content: "please input tip content!",
		}, options);
		
		$this	= $(this);
		offset = $this.offset(); 	
		
		$('.tip').children('div').html(theOptions.content);
		var tipWidth=$('.tip').width();
		width_half= tipWidth > 90 ? (tipWidth - 90)/2:0;
		
		$('.tip').css({'left':offset.left - width_half,'top':offset.top  - 2,'z-index':"9999"}).css('display','').animate({opacity: 1}, 300);//定位
		
		//重新计算浮沉消失时间
		clearTimeout(tip_timeout);
		tip_timeout = setTimeout(function(){$('.tip').hide();},disappear_time);
		
		tip_click = 1; //赋值给浮层点击标记，使对应的浮层不隐藏
	};
	
	$(function(){
		$('<div class="tip"><span class="tip-arrow" style="left:40%;"><em>◆</em><i>◆</i></span><div></div></div>').appendTo("body").hide();
		
		
		//鼠标在浮层上时，停止倒数计时，离开时重新倒数计时
		$('.tip').hover(function(){
				clearTimeout(tip_timeout);
			},
			function(){
				tip_timeout = setTimeout(function(){$('.tip').hide();},disappear_time);
			}
		);
		
		//点击页面除了浮层之外的其他地方，浮层消失
		$('.tip').click(function(event){
			tip_click = 1;	
		});
		
		$(document).click(function(event){
			if(tip_click != 0){ //点击的点在这个浮层上
				tip_click = 0;
			}else{
				clearTimeout(tip_timeout);
				$('.tip').hide();
			}
		});	
	});
})(jQuery);