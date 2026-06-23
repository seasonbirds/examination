/**
 * copyright c by zhangxinxu v1.0 2009-09-05
 * http://www.zhangxinxu.com
 * 用于截取字符串,被截取的字符串后后面加3个英文省略号
 * tips: 此插件会自动给截取的字符串外套一个span标签,
 * @param num 截取的字符串的个数，如果没有定义，则根据字符串所在的dom组件的显示宽度进行截取
 * @param addtitle 是否给截取的字符串加一个title（没有被截取的不会加）,使得鼠标移上去时，显示所有的内容 true:是（默认） false:否  
 * @example:
 * 1)$(".f_word_limit").wordLimit();
 * 2)$(".f_word_limit").wordLimit({num:20,addtitle:false});         
 */
(function($){
	$.fn.wordLimit = function(options){	
		var theOptions = jQuery.extend ({
			addtitle:true, //鼠标在上面时，是否出现title
		}, options);
		this.each(function(){
			$(this).wrapInner("<span></span>"); //内部嵌入span标签
			this_span = $(this).children('span')[0];
			if(!theOptions.num){ //没有定义num参数，默认使用所在的dom组件的显示宽度进行截取
				var copyThis = $(this_span.cloneNode(true)).hide().css({
					'position': 'absolute',
					'width': 'auto',
					'overflow': 'visible',
				});	//复制span节点，但不同的是，它显示所有的内容
				$(this_span).after(copyThis);
				if(copyThis.width()>$(this_span).width()){//内容宽度超过显示宽度
				//算法：先根据两个节点的显示比例，大幅度的减少字符，然后再一个字一个字的减少，使之刚好充满显示区域
					addTitle(this_span,theOptions); //根据需要，添加title
					sublength = $(this_span).text().length * $(this_span).width()/copyThis.width(); 
					$(this_span).text($(this_span).text().substring(0,sublength));
					$(this_span).html($(this_span).html()+'...');
					copyThis.remove();	//清除复制				
					exactSetLength(this_span);//精确的控制显示内容，使之刚好充满显示区域
				}else{
					copyThis.remove(); //清除复制
					return;
				}	
			}else{
				var maxwidth=theOptions.num;
				if($(this_span).text().length>maxwidth){//超过截字的限制
					addTitle(this_span,theOptions);//根据需要，添加title
					$(this_span).text($(this_span).text().substring(0,maxwidth));
					$(this_span).html($(this_span).html()+'...');
				}
			}					 
		});
	};
	
	//根据需要，添加title
	function addTitle(this_span,theOptions){
		if(theOptions.addtitle){
			$(this_span).attr("title",$(this_span).text());
		}
	}
	
	//每次减少一个字符，来回递归，精确的控制显示内容，使之刚好充满显示区域
	function exactSetLength(this_dom){
		var copyThis = $(this_dom.cloneNode(true)).hide().css({
					'position': 'absolute',
					'width': 'auto',
					'overflow': 'visible'
		});	
		$(this_dom).after(copyThis);
		if(copyThis.width()>$(this_dom).width()){//内容宽度超过显示宽度
			$(this_dom).text($(this_dom).text().substring(0,$(this_dom).text().length-4));//减去4是因为后面的省略号有三个字符
			$(this_dom).html($(this_dom).html()+'...');
			copyThis.remove();
			exactSetLength(this_dom); //递归调用
		}else{//递归出口
			copyThis.remove(); //清除复制
			return;
		}			
	}
})(jQuery);