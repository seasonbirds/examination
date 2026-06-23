//禁止Tab键切换焦点
var disabledTabKey=true;
document.onkeydown=function(event){
	var event = event ? event : (
	window.event ? window.event : null);
	if(event.keyCode==9&&disabledTabKey)
		return false;	
}

//等待页面
function showWaitBar() {
	setWaitBarVisible(true);
}
function hiddenWaitBar() {
	setWaitBarVisible(false);
}
function setWaitBarVisible(isVisible) {
	disabledTabKey = isVisible;
	var obj = document.getElementById("WaitBar");
	var l=(document.documentElement.clientWidth-352)/2;
	var t=(document.documentElement.clientHeight-121)/2;
	if (obj != null) {
		obj.style.visibility = isVisible ? "visible" : "hidden";
		if (isVisible) {
			obj.style.left=(l+document.documentElement.scrollLeft)+"px";
			obj.style.top=(t+document.documentElement.scrollTop)+"px"; 
		}
	}
	var ifrm = document.getElementById("WaitBarFrm");
	if (ifrm != null) {
		ifrm.style.display = isVisible ? "block" : "none";
		if (isVisible) {
			ifrm.style.left = 0;
			ifrm.style.top = 0;
			var relWidth; //页面宽度  
			var relHeight;//页面高度  
			if (document.documentElement && document.documentElement.clientHeight) {  
				var doc = document.documentElement;  
				relWidth = (doc.clientWidth > doc.scrollWidth) ? doc.clientWidth - 1 : doc.scrollWidth;  
				relHeight = (doc.clientHeight > doc.scrollHeight) ? doc.clientHeight : doc.scrollHeight;  
			}  
			else {  
				var doc = document.body;  
				relWidth = (window.innerWidth > doc.scrollWidth) ? window.innerWidth : doc.scrollWidth;  
				relHeight = (window.innerHeight > doc.scrollHeight) ? window.innerHeight : doc.scrollHeight;  
			}  
			ifrm.style.width=(relWidth)+"px";
			ifrm.style.height=(relHeight)+"px";
		}
	}
}

