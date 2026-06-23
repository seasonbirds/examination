// JavaScript Document

$(function(){
	
	$("#allmenu #tsecbk").each(function(){
		$(this).bind("click",function(){
			$("#allmenu #tsecbk").removeClass("selectItem");
			$(this).addClass("selectItem");							   
		})
	});

})
function sx(a)
{//alert(a);
		var tag = document.getElementById("nav").getElementsByTagName("li");
		var taglength = tag.length
		for (var b=0;b<taglength;b++)
		{
			document.getElementById("nav"+b).style.background="none"
			document.getElementById("nav"+b).style.color="#FFF"
			document.getElementById("sec"+b).style.display="none"
		}
		document.getElementById("nav"+a).style.background="url(img/navhover.gif)";
		document.getElementById("nav"+a).style.color="#1D4A6C";
		document.getElementById("sec"+a).style.display="block";
		
		//清除所有2级菜单的CSS效果
		var allA = document.getElementById("allsmenu"+a);
		var allLi = document.getElementById("allsmenu"+a);
		if(allA!=null&&allLi!=null){
			allA = allA.getElementsByTagName("a");
			allLi = allLi.getElementsByTagName("li");
			for(var i = 0; i < allA.length; i++)
			{
				allA[i].className = ""
			}
			allA[0].className="selectItem";
			var str = allLi[0].id.split('_');
			//alert(str[0]+"___"+str[1]);
			checkMenuRight(str[0],str[1]);
		}
}

