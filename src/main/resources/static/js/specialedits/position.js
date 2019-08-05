var get = {
	byId: function(id) {
		return typeof id === "string" ? document.getElementById(id) : id;
	},
	byClass: function(sClass, oParent) {
		var aClass = [];
		var reClass = new RegExp("(^| )" + sClass + "( |$)");
		var aElem = this.byTagName("*", oParent);
		for (var i = 0; i < aElem.length; i++) reClass.test(aElem[i].className) && aClass.push(aElem[i]);
		return aClass;
	},
	byTagName: function(elem, obj) {
		return (obj || document).getElementsByTagName(elem);
	}
};
//按钮最小值
var dragMinWidth = 0;
var dragMinHeight = 0;

var maxWidth=1280;
var maxHeight=720;
var resolution='hd';
/*-------------------------- +
  拖拽函数
 +-------------------------- */
function drag(oDrag,num,resolution_cs){
	$("#viewid").val($(oDrag).attr("id"));
	var disX = dixY = 0;
	oDrag.style.cursor = "move";
	
	oDrag.onmousedown = function (event){
		var resolution_cur=$('#resolution').val();
		if(resolution_cs=='sd' || resolution_cur =='sd'){
			maxWidth=640;
			maxHeight=530;
		}else if(resolution_cs=='hd' || resolution_cur =='hd'){
			maxWidth=1280;
			maxHeight=720;
		}
		var bg_imgid=$(oDrag).attr('id');
		//but_blur_videos_1
		var lastnum=bg_imgid.lastIndexOf('_');
		var but_type=bg_imgid.substring(lastnum-6,bg_imgid.length-2);
		var color_style;
		if(but_type =='videos'){
			color_style='red';
		}else if(but_type =='button'){
			color_style='green';
		}else if(but_type=='images'){
			color_style='blue';
		}
		$("#butn_type").css("color",color_style);
		
		$("table .tr_pic").remove();
		if('but_background_img' !=bg_imgid){
			createImgButn(bg_imgid,resolution_cs);
		}
		var event = event || window.event;
		disX = event.clientX - oDrag.offsetLeft;
		disY = event.clientY - oDrag.offsetTop;
		
		var iLMD = event.clientX - disX;
		var iTMD = event.clientY - disY;
		iLMD <= 0 && (iLMD = 0);
		iTMD <= 0 && (iTMD = 0);
		//限制图片不能移除窗口（640*530）
		(iLMD+$(oDrag).width()) >= maxWidth && (iLMD = maxWidth-$(oDrag).width());
		(iTMD+$(oDrag).height()) >= maxHeight && (iTMD = maxHeight-$(oDrag).height());
		
		$("#xzb").val(parseInt(iLMD));
		$("#yzb").val(parseInt(iTMD));
		$("#wh").val(parseInt($(oDrag).width()));
		$("#ht").val(parseInt($(oDrag).height()));
		$("#viewid").val($(oDrag).attr("id"));
		$("#info").css("display","block");
		document.onmousemove = function (event){
			var event = event || window.event;
			var iL = event.clientX - disX;
			var iT = event.clientY - disY;
			//var maxL = document.documentElement.clientWidth - oDrag.offsetWidth;
			//var maxT = document.documentElement.clientHeight - oDrag.offsetHeight;
			
			iL <= 0 && (iL = 0);
			iT <= 0 && (iT = 0);
			//限制图片不能移除窗口（640*530）
			(iL+$(oDrag).width()) >= maxWidth && (iL = maxWidth-$(oDrag).width());
			(iT+$(oDrag).height()) >= maxHeight && (iT = maxHeight-$(oDrag).height());
			//iL >= maxL && (iL = maxL);
			//iT >= maxT && (iT = maxT);
			oDrag.style.left = iL + "px";
			oDrag.style.top = iT + "px";
			$("#xzb").val(parseInt(iL));
			$("#yzb").val(parseInt(iT));
			$("#wh").val(parseInt($(oDrag).width()));
			$("#ht").val(parseInt($(oDrag).height()));
			return false;
		};
		document.onmouseup = function (){
			document.onmousemove = null;
			document.onmouseup = null;
			this.releaseCapture && this.releaseCapture();
		};
		//this.setCapture && this.setCapture();
		return false;
	};
}
/*-------------------------- +
  改变大小函数
 +-------------------------- */
function resize(oParent, handle, isLeft, isTop, lockX, lockY,num){
	
	handle.onmousedown = function (event){
		var resolution_cur=$('#resolution').val();
		if(resolution=='sd' || resolution_cur =='sd'){
			maxWidth=640;
			maxHeight=530;
		}else if(resolution=='hd' || resolution_cur =='hd'){
			maxWidth=1280;
			maxHeight=720;
		}
		//阻止拖拽事件
		oParent.onmousedown = function (e){
			return false;
		};
		var event = event || window.event;
		var disX = event.clientX - handle.offsetLeft;
		var disY = event.clientY - handle.offsetTop;	
		var iParentTop = oParent.offsetTop;//按钮离上边框的距离
		var iParentLeft = oParent.offsetLeft;//按钮离左边框的距离
		var iParentWidth = oParent.scrollWidth;//按钮原始宽度
		var iParentHeight = oParent.scrollHeight;//按钮原始高度
		
		document.onmousemove = function (event){
			var event = event || window.event;
			var iL = event.clientX - disX;//移动后变化的宽度
			var iT = event.clientY - disY;//移动后变化的高度
			//var maxW = document.documentElement.clientWidth - oParent.offsetLeft - 2;
			//var maxH = document.documentElement.clientHeight - oParent.offsetTop - 2;		
			var maxW = maxWidth-iParentLeft;
			var maxH = maxHeight-iParentTop;
			var iW = isLeft ? iParentWidth - iL : handle.offsetWidth + iL;
			var iH = isTop ? iParentHeight - iT : handle.offsetHeight + iT;
			
			//限制图片不能移除窗口（640*530）
			//alert(event.clientX);
			isLeft && (event.clientX>0 && event.clientX<maxWidth) && (oParent.style.left = iParentLeft + iL + "px");
			isTop && (event.clientY>0 && event.clientY<maxHeight) && (oParent.style.top = iParentTop + iT + "px");
			isLeft && (event.clientX<=0 || event.clientX>=maxWidth) && (oParent.style.left = "0px");
			isTop && (event.clientY<=0 || event.clientY>=maxHeight) && (oParent.style.top = "0px");
			
			iW < dragMinWidth && (iW = dragMinWidth);
			iW > maxW && (iW = maxW);
			lockX || (oParent.style.width = iW + "px");
			
			iH < dragMinHeight && (iH = dragMinHeight);
			iH > maxH && (iH = maxH);
			lockY || (oParent.style.height = iH + "px");
			
			$("#wh").val(parseInt($(oParent).width()));
			$("#ht").val(parseInt($(oParent).height()));
			$("#xzb").val(parseInt($(oParent).offset().left));
			$("#yzb").val(parseInt($(oParent).offset().top));
			
			if( (isLeft && iW == dragMinWidth) || (isTop && iH == dragMinHeight) ) document.onmousemove = null;
			return false;	
		};
		document.onmouseup = function (){
			document.onmousemove = null;
			document.onmouseup = null;
			this.releaseCapture && this.releaseCapture();
			//重新启用拖拽事件
			drag(oParent,num,resolution);
		};
		return false;
	};
}
//调用调整大小函数
function change(oDrag,num,resolution_cs){
	var oL = get.byClass("resizeL", oDrag)[0];
	var oT = get.byClass("resizeT", oDrag)[0];
	var oR = get.byClass("resizeR", oDrag)[0];
	var oB = get.byClass("resizeB", oDrag)[0];
	var oLT = get.byClass("resizeLT", oDrag)[0];
	var oTR = get.byClass("resizeTR", oDrag)[0];
	var oBR = get.byClass("resizeBR", oDrag)[0];
	var oLB = get.byClass("resizeLB", oDrag)[0];
	if(resolution_cs=='sd'){
		maxWidth=640;
		maxHeight=530;
		resolution='sd';
	}else if(resolution_cs=='hd'){
		maxWidth=1280;
		maxHeight=720;
		resolution='hd';
	}
	drag(oDrag,num,resolution);
	//四角
	resize(oDrag, oLT, true, true, false, false,num);
	resize(oDrag, oTR, false, true, false, false,num);
	resize(oDrag, oBR, false, false, false, false,num);
	resize(oDrag, oLB, true, false, false, false,num);
	//四边
	resize(oDrag, oL, true, false, false, true,num);
	resize(oDrag, oT, false, true, true, false,num);
	resize(oDrag, oR, false, false, false, true,num);
	resize(oDrag, oB, false, false, true, false,num);
}
//检查是否支持flash插件
function checkFirefoxOrIE(){
	var isIE=/*@cc_on!@*/0;//是否IE浏览器
	var swf = navigator.plugins["Shockwave Flash"];
	if(isIE){
	}else {
		if(swf){
		}else {
			alert("请检查是否安装Abobe Shockwave Flash插件，防止影响部分功能显示。");
			window.open("http://get2.adobe.com/cn/flashplayer/");
		}
	}
}
