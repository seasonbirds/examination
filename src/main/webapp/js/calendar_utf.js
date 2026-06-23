var bMoveable=true;  
var _VersionInfo="Version:2.02&#13;2.01&02 Author: Athos;2.0 Author:walkingpoison&#13;1.0 Author: F.R.Huang(meizz)&#13;MAIL: meizz@hzcnc.com";
var strFrame;  
document.writeln('<iframe id=meizzDateLayer Author=wayx frameborder=0 style="position:absolute;width:215; height:225; z-index:9998; display:none;"></iframe>');
strFrame='<style>';
strFrame+='INPUT.button{BORDER-RIGHT: #ff9900 1px solid;BORDER-TOP: #ff9900 1px solid;BORDER-LEFT: #ff9900 1px solid;';
strFrame+='BORDER-BOTTOM: #ff9900 1px solid;BACKGROUND-COLOR: #fff8ec;}';
strFrame+='TD{FONT-SIZE: 9pt;}';
strFrame+='</style>';
strFrame+='<scr' + 'ipt>';
strFrame+='var datelayerx,datelayery; ';
strFrame+='var bDrag; ';
strFrame+='function document.onmousemove() ';
strFrame+='{if(bDrag && window.event.button==1)';
strFrame+=' {var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+='  DateLayer.posLeft += window.event.clientX-datelayerx;';
strFrame+='  DateLayer.posTop += window.event.clientY-datelayery;}}';
strFrame+='function DragStart()  ';
strFrame+='{var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+=' datelayerx=window.event.clientX;';
strFrame+=' datelayery=window.event.clientY;';
strFrame+=' bDrag=true;}';
strFrame+='function DragEnd(){  ';
strFrame+=' bDrag=false;}';
strFrame+='</scr' + 'ipt>';
strFrame+='<div style="z-index:9999;position: absolute; left:0; top:0;" onselectstart="return false">';

strFrame+='<table border=1 cellspacing=0 cellpadding=0 width=100% height=100%  bordercolor=#ff9900 bgcolor=#ff9900 Author="wayx">';
strFrame+='  <tr Author="wayx"><td width=210 height=23 Author="wayx" bgcolor=#FFFFFF><table border=0 cellspacing=1 cellpadding=0 width=210 Author="wayx" height=23>';
strFrame+='      <tr align=center Author="wayx"><td width=16 align=center bgcolor=#ff9900 style="font-size:12px;cursor: hand;color: #ffffff" ';
strFrame+='        onclick="parent.meizzPrevM()" title="上个月" Author=meizz><b Author=meizz>&lt;</b>';

strFrame+='        </td><td width=30 align=center style="font-size:12px;cursor:default" Author=meizz  onmouseout="style.backgroundColor=\'white\'" >';
strFrame+='<span Author=meizz id=meizzYearHead></span></td>';

strFrame+='<td width=78 align=center style="font-size:12px;cursor:default" Author=meizz  onmouseout="style.backgroundColor=\'white\'" >';
strFrame+=' <span id=meizzMonthHead Author=meizz></span></td>';

strFrame+='<td width=78 id=hourTd align=center style="font-size:12px;cursor:default" Author=meizz onmouseout="style.backgroundColor=\'white\'">';
strFrame+='<span id=meizzHourHead Author=meizz></span></td>';

strFrame+='<td width=78 id=minTd align=center style="font-size:12px;cursor:default" Author=meizz  onmouseout="style.backgroundColor=\'white\'" >';
strFrame+='<span id=meizzMinHead Author=meizz></span></td></span>';


strFrame+='        <td width=16 bgcolor=#ff9900 align=center style="font-size:12px;cursor: hand;color: #ffffff" ';
strFrame+='         onclick="parent.meizzNextM()" title="下个月" Author=meizz><b Author=meizz>&gt;</b></td></tr>';
strFrame+='    </table></td></tr>';
strFrame+='  <tr Author="wayx"><td width=210 height=18 Author="wayx">';
strFrame+='<table border=1 cellspacing=0 cellpadding=0 bgcolor=#ff9900 ' + (bMoveable? 'onmousedown="DragStart()" onmouseup="DragEnd()"':'');
strFrame+=' BORDERCOLORLIGHT=#FF9900 BORDERCOLORDARK=#FFFFFF width=100% height=20 Author="wayx" style="cursor:' + (bMoveable ? 'move':'default') + '">';
strFrame+='<tr Author="wayx" align=center valign=bottom>';
strFrame+='<td width=18px style="font-size:12px;color:#FFFFFF" Author=meizz >日</td>';
strFrame+='<td width=18px style="font-size:12px;color:#FFFFFF" Author=meizz >一</td>';
strFrame+='<td width=18px style="font-size:12px;color:#FFFFFF" Author=meizz >二</td>';
strFrame+='<td width=18px style="font-size:12px;color:#FFFFFF" Author=meizz >三</td>';
strFrame+='<td width=18px style="font-size:12px;color:#FFFFFF" Author=meizz >四</td>';
strFrame+='<td width=18px style="font-size:12px;color:#FFFFFF" Author=meizz >五</td>';
strFrame+='<td width=20px style="font-size:12px;color:#FFFFFF" Author=meizz >六</td>';
strFrame+='</tr>'; 


strFrame+='</table></td></tr><!-- Author:F.R.Huang(meizz) http://www.meizz.com/ mail: meizz@hzcnc.com 2002-10-8 -->';
strFrame+='  <tr Author="wayx"><td width=210 height=120 Author="wayx">';
strFrame+='    <table border=1 cellspacing=2 cellpadding=0 BORDERCOLORLIGHT=#FF9900 BORDERCOLORDARK=#FFFFFF bgcolor=#fff8ec width=100% height=120 Author="wayx">';
var n=0; for (j=0;j<5;j++){ strFrame+= ' <tr align=center Author="wayx">'; for (i=0;i<7;i++){
strFrame+='<td width=30 height=20 id=meizzDay'+n+' style="font-size:12px" Author=meizz onclick=parent.meizzDayClick(this.innerText,0)></td>';n++;}
strFrame+='</tr>';}
strFrame+='      <tr align=center Author="wayx">';
for (i=35;i<39;i++)strFrame+='<td width=20 height=20 id=meizzDay'+i+' style="font-size:12px" Author=wayx onclick="parent.meizzDayClick(this.innerText,0)"></td>';
strFrame+='        <td align=right Author=meizz><span onclick=parent.clearAndCloseLayer() style="font-size:11px;cursor: hand;color:#00aaaa;"';
strFrame+='         Author=meizz title="清除"><b>清除</b></span></td>';
strFrame+='        <td align=right Author=meizz><span onclick=parent.closeLayer() style="font-size:11px;cursor: hand;color:Red;"';
strFrame+='         Author=meizz title="关闭"><b>关闭</b></span></td>';
strFrame+='        <td align=right Author=meizz></td>';
strFrame+='</tr>';


strFrame+='</table></td></tr><tr Author="wayx"><td Author="wayx">';
strFrame+='  <table border=0 cellspacing=1 cellpadding=0 width=100% Author="wayx" bgcolor=#FFFFFF>';
strFrame+='    <tr Author="wayx"><td Author=meizz align=left>';
strFrame+='       <input Author=meizz type=button class=button value="&larr;" title="10年前" onclick="parent.meizzPrevY(10)" ';
strFrame+='          onfocus="this.blur()" style="font-size: 12px; height: 20px">';
strFrame+='       <input Author=meizz type=button class=button value="&#171;" title="上一年" onclick="parent.meizzPrevY(1)" ';
strFrame+='          onfocus="this.blur()" style="font-size: 12px; height: 20px">';
strFrame+='        <input Author=meizz class=button title="上个月" type=button ';
strFrame+='          value="&lsaquo;" onclick="parent.meizzPrevM()" onfocus="this.blur()" style="font-size: 12px; height: 20px"></td><td ';
strFrame+='             Author=meizz align=center><input Author=meizz type=button class=button value="今天" style="color:#00007f;background-color:FFD700;font:italic bolder 10pt;" onclick="parent.meizzToday()" ';
strFrame+='             onfocus="this.blur()" title="今天" style="font-size: 12px; height: 20px; cursor:hand"></td><td ';
strFrame+='             Author=meizz align=right>';
strFrame+='        <input Author=meizz type=button class=button value="&rsaquo;" onclick="parent.meizzNextM()" ';
strFrame+='             onfocus="this.blur()" title="下个月" class=button style="font-size: 12px; height: 20px">';
strFrame+='        <input Author=meizz type=button class=button value="&#187;" title="下一年" onclick="parent.meizzNextY(1)"';
strFrame+='             onfocus="this.blur()" style="font-size: 12px; height: 20px">';
strFrame+='       <input Author=meizz type=button class=button value="&rarr;" title="10年后" onclick="parent.meizzNextY(10)" ';
strFrame+='          onfocus="this.blur()" style="font-size: 12px; height: 20px">';
strFrame+='      </td></tr></table></td></tr></table></div>';


window.frames.meizzDateLayer.document.writeln(strFrame);
window.frames.meizzDateLayer.document.close();  

var outObject;    //输出到的对象
var outButton;    //输出到的按键
var outDate="";    //输出的日期
var odatelayer=window.frames.meizzDateLayer.document.all; 
var meizzTheHour;    //输出的小时
var	meizzTheMin;  //输出的分钟
var isSetTime = false;   //是否设置时间


function showCalender(tt,obj) 
{
	
 if (arguments.length >  2){alert("l, too many parameters");return;}
 if (arguments.length == 0){alert("Sorry, none parameter!");return;}
 var dads  = document.all.meizzDateLayer.style;
 var th = tt;
 var ttop  = tt.offsetTop;
 var thei  = tt.clientHeight;  
 var tleft = tt.offsetLeft;  
 var ttyp  = tt.type;   
 while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}

 dads.top  = (ttyp=="image")? ttop+thei : ttop+thei+6;

//如果位置超过右界限，刚调整一下
 if(dads.pixelWidth+tleft>document.body.offsetWidth)
{
	 tleft = document.body.offsetWidth-dads.pixelWidth;
}

//如果位置超过下界限，刚调整一下
 if(dads.pixelHeight+ttop>document.body.offsetHeight)
{
	//dads.top = document.body.offsetHeight-dads.pixelHeight;
	dads.top = document.body.offsetHeight-dads.pixelHeight;
}



 dads.left = tleft;
 outObject = (arguments.length == 1) ? th : obj;
 outButton = (arguments.length == 1) ? null : th; 
 isSetTime = false;

//alert( dads.top +"---"+ dads.left);

//隐藏时间和分钟选择
odatelayer.meizzHourHead.innerHTML="";
odatelayer.meizzMinHead.innerHTML="";



 var reg = /^(\d+)\-(\d{1,2})\-(\d{1,2})$/; 
 //var reg = /^(\d+)\-(\d{1,2})\-(\d{1,2})\s(\d{1,2}):(\d{1,2})$/; 


 var r = outObject.value.match(reg); 
 
 if(r!=null){


  r[2]=r[2]-1; 
  var d= new Date(r[1], r[2],r[3]); 
  if(d.getFullYear()==r[1] && d.getMonth()==r[2] && d.getDate()==r[3]){
   outDate=d;
  }
  else outDate="";
   meizzSetDay(r[1],r[2]+1);
 }
 else{
  outDate="";
  meizzSetDay(new Date().getFullYear(), new Date().getMonth() + 1);
 }
 dads.display = 'block';

 event.returnValue=false;
}

function setTime(tt,obj) 
{
 if (arguments.length >  2){alert("l, too many parameters");return;}
 if (arguments.length == 0){alert("Sorry, none parameter!");return;}
 isSetTime = true;


 var dads  = document.all.meizzDateLayer.style;
 var th = tt;
 var ttop  = tt.offsetTop;
 var thei  = tt.clientHeight;  
 var tleft = tt.offsetLeft;  
 var ttyp  = tt.type;        
 while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}

//如果位置超过右界限，刚调整一下
 if(dads.pixelWidth+tleft>document.body.offsetWidth)
{
	 tleft = document.body.offsetWidth-dads.pixelWidth;
}


 dads.top  = (ttyp=="image")? ttop+thei : ttop+thei+6;
 dads.left = tleft;
 outObject = (arguments.length == 1) ? th : obj;
 outButton = (arguments.length == 1) ? null : th; 

//如果位置超过下界限，刚调整一下
 if(dads.pixelHeight+ttop>document.body.offsetHeight)
{
	  dads.top = document.body.offsetHeight-dads.pixelHeight;
}


 var reg = /^(\d+)\-(\d{1,2})\-(\d{1,2})\s(\d{1,2}):(\d{1,2}):(\d{1,2})(.\d{1,2})*$/; 


 var r = outObject.value.match(reg); 
 
 if(r!=null){
	


  r[2]=r[2]-1; 
  var d= new Date(r[1], r[2],r[3]); 
  if(d.getFullYear()==r[1] && d.getMonth()==r[2] && d.getDate()==r[3]){
   outDate=d;
  }
  else outDate="";
   meizzSetDay(r[1],r[2]+1);
    //显示时间和分钟选择	
  //设置时间
  meizzSetTimeHead(r[4],r[5]);
 }
 else{
  outDate="";
  meizzSetDay(new Date().getFullYear(), new Date().getMonth() + 1);
  //设置时间
   var tempMin = eval(Math.floor(new Date().getMinutes()/5)*5);
  meizzSetTimeHead(new Date().getHours(),tempMin);
 }
 dads.display = '';

 event.returnValue=false;
}

var MonHead = new Array(12);         
    MonHead[0] = 31; MonHead[1] = 28; MonHead[2] = 31; MonHead[3] = 30; MonHead[4]  = 31; MonHead[5]  = 30;
    MonHead[6] = 31; MonHead[7] = 31; MonHead[8] = 30; MonHead[9] = 31; MonHead[10] = 30; MonHead[11] = 31;

var meizzTheYear=new Date().getFullYear(); 
var meizzTheMonth=new Date().getMonth()+1; 
var meizzWDay=new Array(39);               

document.onclick = function () 
{ 
  with(window.event)
  { if (srcElement.getAttribute("Author")==null && srcElement != outObject && srcElement != outButton)
    closeLayer();
  }
};

document.onkeyup = function () 
{
    if (window.event.keyCode==27){
  		if(outObject)outObject.blur();
  		closeLayer();
 	}
 	else if(document.activeElement)
  		if(document.activeElement.getAttribute("Author")==null && document.activeElement != outObject && document.activeElement != outButton)
  	{
   		closeLayer();
  	}
};

//设置年月下拉框
function meizzWriteHead(yy,mm) 
{
	selectYearInnerHTML(String(yy));
    selectMonthInnerHTML(String(mm));
}

//设置时分下拉框
function meizzSetTimeHead(hh, min)
{
	meizzTheHour=hh;
	meizzTheMin=min;
	selectHourInnerHTML(String(hh));
    selectMinInnerHTML(String(min));

}


function selectYearInnerHTML(strYear) 
{
  if (strYear.match(/\D/)!=null){alert("Year shall be a number.");return;}
  var m = (strYear) ? strYear : new Date().getFullYear();
  if (m < 1000 || m > 9999) {alert("Year shall between 1000 to 9999.");return;}
  var n = m - 80;
  if (n < 1000) n = 1000;
  if (n + 96 > 9999) n = 9904;
  var s = "<select Author=meizz name=tmpSelectYear style='font-size: 10px;width:48;border:0' "
     s += "onchange='parent.meizzTheYear = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";
  var selectInnerHTML = s;
  for (var i = n; i < n + 96; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option Author=wayx value='" + i + "' selected>" + i + "</option>\r\n";}
    else {selectInnerHTML += "<option Author=wayx value='" + i + "'>" + i + "</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  odatelayer.meizzYearHead.innerHTML = selectInnerHTML;
 // odatelayer.tmpSelectYear.focus();
}

function selectMonthInnerHTML(strMonth)
{
 if (strMonth.match(/\D/)!=null){alert("Month shall be a number");return;}
  var m = (strMonth) ? strMonth : new Date().getMonth() + 1;
  var s = "<select Author=meizz name=tmpSelectMonth style='font-size: 10px;width:45' "
     s += "onchange='parent.meizzTheMonth = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";
  var selectInnerHTML = s;
  for (var i = 1; i < 13; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option Author=wayx value='"+i+"' selected>"+  String(i) +'月'         +"</option>\r\n";}
    else {selectInnerHTML += "<option Author=wayx value='"+i+"'>"+        String(i) +'月'       +"</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  odatelayer.meizzMonthHead.style.display="";
  odatelayer.meizzMonthHead.innerHTML = selectInnerHTML;
//  odatelayer.tmpSelectMonth.focus();
}

function selectHourInnerHTML(strHour) 
{
  if (strHour.match(/\D/)!=null){alert("Hour shall be a number.");return;}
  var m = (strHour) ? strHour : new Date().getHours();
  if (m < 0 || m > 24) {alert("Hour shall between 0 to 24.");return;}
  var s = "<select Author=meizz name=tmpSelectHour style='font-size: 10px;width:45' "
     s += "onchange='parent.meizzTheHour = this.value; parent.meizzSetTimeHead(parent.meizzTheHour,parent.meizzTheMin)'>\r\n";
  var selectInnerHTML = s;
  for (var i = 0; i < 24; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option Author=wayx value='" + i + "' selected>" + i +'时'+ "</option>\r\n";}
    else {selectInnerHTML += "<option Author=wayx value='" + i + "'>" + i+'时' + "</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  odatelayer.meizzHourHead.style.display="";
  odatelayer.meizzHourHead.innerHTML = selectInnerHTML;
 // odatelayer.tmpSelectHour.focus();
}

function selectMinInnerHTML(strMin) 
{
  if (strMin.match(/\D/)!=null){alert("Minute shall be a number.");return;}
  var m = (strMin) ? strMin : new Date().getMinutes();
  if (m < 0 || m > 60) {alert("Minute shall between 0 to 60.");return;}
  var s = "<select Author=meizz name=tmpSelectMin style='font-size: 10px;width:45' "
     s += "onchange='parent.meizzTheMin = this.value; parent.meizzSetTimeHead(parent.meizzTheHour,parent.meizzTheMin)'>\r\n";
  var selectInnerHTML = s;
  for (var i = 0; i < 61; i+=5)
  {
    if (i == m)
       {selectInnerHTML += "<option Author=wayx value='" + i + "' selected>" + i +'分'+ "</option>\r\n";}
    else {selectInnerHTML += "<option Author=wayx value='" + i + "'>" + i+'分'+ "</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  odatelayer.meizzMinHead.style.display="";
  odatelayer.meizzMinHead.innerHTML = selectInnerHTML;
  //odatelayer.tmpSelectMin.focus();
}



function closeLayer() 
{
    document.all.meizzDateLayer.style.display="none"; 
 }

function clearAndCloseLayer()               
{
  if (outObject)
  {
   	outObject.value= ""; 
   	//如果有onchange事件，一并激活
	if(outObject && outObject.onchange){
     	outObject.onchange.call(outObject);
     }
	closeLayer(); 
  }
  else {closeLayer(); alert("None control to output!");}    
}

function IsPinYear(year)            
  {
    if (0==year%4&&((year%100!=0)||(year%400==0))) return true;else return false;
  }

function GetMonthCount(year,month)  
  {
    var c=MonHead[month-1];if((month==2)&&IsPinYear(year)) c++;return c;
  }
function GetDOW(day,month,year)     
  {
    var dt=new Date(year,month-1,day).getDay()/7; return dt;
  }


function meizzPrevY(intYears)  
  {
    if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear-=eval(intYears);}
    else{alert("Year beyond (1000-9999)!");}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzNextY(intYears)  
  {
    if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear=eval(parseInt(meizzTheYear)+intYears);}
    else{alert("Year beyond (1000-9999)!");}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzToday()  
  {
 var today;
    meizzTheYear = new Date().getFullYear();
    meizzTheMonth = new Date().getMonth()+1;
    today=new Date().getDate();
    //meizzSetDay(meizzTheYear,meizzTheMonth);
    if(outObject){
  outObject.value=meizzTheYear + "-" + meizzTheMonth + "-" + today;
		if(isSetTime)
		{
			outObject.value += " "+meizzTheHour+":"+meizzTheMin+":00";
		}
		
     	outObject.onchange.call(outObject);    
    }
    closeLayer();
  }
function meizzPrevM()  
  {
    if(meizzTheMonth>1){meizzTheMonth--}else{meizzTheYear--;meizzTheMonth=12;}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzNextM()  
  {
    if(meizzTheMonth==12){meizzTheYear++;meizzTheMonth=1}else{meizzTheMonth++}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }



function meizzSetDay(yy,mm)   
{
  meizzWriteHead(yy,mm);
  meizzTheYear=yy;
  meizzTheMonth=mm;
  
  for (var i = 0; i < 39; i++){meizzWDay[i]=""};  
  var day1 = 1,day2=1,firstday = new Date(yy,mm-1,1).getDay();  
  for (i=0;i<firstday;i++)meizzWDay[i]=GetMonthCount(mm==1?yy-1:yy,mm==1?12:mm-1)-firstday+i+1 
  for (i = firstday; day1 < GetMonthCount(yy,mm)+1; i++){meizzWDay[i]=day1;day1++;}
  for (i=firstday+GetMonthCount(yy,mm);i<39;i++){meizzWDay[i]=day2;day2++}
  for (i = 0; i < 39; i++)
  { var da = eval("odatelayer.meizzDay"+i)
    if (meizzWDay[i]!="")
      { 
  da.borderColorLight="#FF9900";
  da.borderColorDark="#FFFFFF";
  if(i<firstday)  
  {
   da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
   da.title=(mm==1?12:mm-1) +"月" + meizzWDay[i] + "日";
   da.onclick=Function("meizzDayClick(this.innerText,-1)");
   if(!outDate)
    da.style.backgroundColor = ((mm==1?yy-1:yy) == new Date().getFullYear() && 
     (mm==1?12:mm-1) == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate()) ?
      "#FFD700":"#e0e0e0";
   else
   {
    da.style.backgroundColor =((mm==1?yy-1:yy)==outDate.getFullYear() && (mm==1?12:mm-1)== outDate.getMonth() + 1 && 
    meizzWDay[i]==outDate.getDate())? "#00ffff" :
    (((mm==1?yy-1:yy) == new Date().getFullYear() && (mm==1?12:mm-1) == new Date().getMonth()+1 && 
    meizzWDay[i] == new Date().getDate()) ? "#FFD700":"#e0e0e0");
    if((mm==1?yy-1:yy)==outDate.getFullYear() && (mm==1?12:mm-1)== outDate.getMonth() + 1 && 
    meizzWDay[i]==outDate.getDate())
    {
     da.borderColorLight="#FFFFFF";
     da.borderColorDark="#FF9900";
    }
   }
  }
  else if (i>=firstday+GetMonthCount(yy,mm))
  {
   da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
   da.title=(mm==12?1:mm+1) +"月" + meizzWDay[i] + "日";
   da.onclick=Function("meizzDayClick(this.innerText,1)");
   if(!outDate)
    da.style.backgroundColor = ((mm==12?yy+1:yy) == new Date().getFullYear() && 
     (mm==12?1:mm+1) == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate()) ?
      "#FFD700":"#e0e0e0";
   else
   {
    da.style.backgroundColor =((mm==12?yy+1:yy)==outDate.getFullYear() && (mm==12?1:mm+1)== outDate.getMonth() + 1 && 
    meizzWDay[i]==outDate.getDate())? "#00ffff" :
    (((mm==12?yy+1:yy) == new Date().getFullYear() && (mm==12?1:mm+1) == new Date().getMonth()+1 && 
    meizzWDay[i] == new Date().getDate()) ? "#FFD700":"#e0e0e0");
    if((mm==12?yy+1:yy)==outDate.getFullYear() && (mm==12?1:mm+1)== outDate.getMonth() + 1 && 
    meizzWDay[i]==outDate.getDate())
    {
     da.borderColorLight="#FFFFFF";
     da.borderColorDark="#FF9900";
    }
   }
  }
  else  
  {
   da.innerHTML="<b>" + meizzWDay[i] + "</b>";
   da.title=mm +"月" + meizzWDay[i] + "日";
   da.onclick=Function("meizzDayClick(this.innerText,0)");  
   
   if(!outDate)
    da.style.backgroundColor = (yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
     "#FFD700":"#e0e0e0";
   else
   {
    da.style.backgroundColor =(yy==outDate.getFullYear() && mm== outDate.getMonth() + 1 && meizzWDay[i]==outDate.getDate())?
     "#00ffff":((yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
     "#FFD700":"#e0e0e0");
    if(yy==outDate.getFullYear() && mm== outDate.getMonth() + 1 && meizzWDay[i]==outDate.getDate())
    {
     da.borderColorLight="#FFFFFF";
     da.borderColorDark="#FF9900";
    }
   }
  }
        da.style.cursor="hand"
      }
    else{da.innerHTML="";da.style.backgroundColor="";da.style.cursor="default"}
  }
}

function meizzDayClick(n,ex)
{
  var yy=meizzTheYear;
  var mm = parseInt(meizzTheMonth)+ex; 
 if(mm<1){
  yy--;
  mm=12+mm;
 }
 else if(mm>12){
  yy++;
  mm=mm-12;
 }
 
  if (mm < 10){mm = "0" + mm;}
  if (outObject)
  {
    if (!n) {
      return;}
    if ( n < 10){n = "0" + n;}
    outObject.value= yy+"-"+mm + "-" + n; 

	if(isSetTime)  //如果是设置时间
	 {
		outObject.value += " "+meizzTheHour+":"+meizzTheMin+":00";
	 }
    closeLayer(); 
     //如果有onchange事件，一并激活
	if(outObject && outObject.onchange){
     	outObject.onchange.call(outObject);
     }
  }
  else {closeLayer(); alert("None control to output!");}
}
