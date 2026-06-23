/*
  jQuery Colorbox language configuration
  language: Chinese (zh)
  translated by: zq
*/
jQuery.extend(jQuery.colorbox.settings, {
	current: "第{current}页，共{total}页",
	previous: "",
	next: "",
	close: "关闭",
	xhrError: "不能加载指定内容。",
	imgError: "图片加载失败。",
	slideshowStart: "开始自动翻页",
	slideshowStop: "结束自动翻页",
	overlayClose:false,  //点击背景，是否关闭浮层   true:是  false：否
	opacity:0.2,   //背景透明度，取值0-1
	speed:20,     //渲染时间，单位毫秒
	transition:"elastic",    //弹出方式，"elastic"：弹出  "fade":淡入；淡出 “none”：直接展开
	title:false,              //展示标题
	scrolling:true,           //超出部分如何处理， true:出现滚动条 false:直接隐藏
	open:false,               //定义完后是否立刻展开
	preloading:true,           //在当前页面未加载完全时，是否可以预先加载其他页面
	escKey:false,			//按esc键是否关闭当前浮层
	data:false,                //请求附加参数
	className:false,        //给colorbox 和 overlay 添加class
	fadeOut:200,            //浮层关闭，淡出时间
	maxWidth:false,			//设置浮层最大宽度  例如: "100%", 500, "500px"
	maxHeight:false			//设置浮层最大高度  例如: "100%", 500, "500px"
});
