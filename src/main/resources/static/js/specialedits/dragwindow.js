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

//var dragMinWidth = 250;
//var dragMinHeight = 124;
function menu_drag(oDrag,handle){
	var disX = dixY = 0;
	var oClose = get.byClass("close", oDrag)[0];
	handle = handle || oDrag;
	handle.style.cursor = "move";
	handle.onmousedown = function (event){
		
		event = event || window.event;
		disX = event.clientX - oDrag.offsetLeft;
		disY = event.clientY - oDrag.offsetTop;
		document.onmousemove = function (event){
		    event = event || window.event;
		    var iL = event.clientX - disX;
		    var iT = event.clientY - disY;
		    var maxL = document.documentElement.clientWidth - oDrag.offsetWidth;
		    var maxT = document.documentElement.clientHeight - oDrag.offsetHeight;
		    iL <= 0 && (iL = 0);
		    iT <= 0 && (iT = 0);
		    iL >= maxL && (iL = maxL);
		    iT >= maxT && (iT = maxT);
		    oDrag.style.left = iL + "px";
		    oDrag.style.top = iT + "px";
		    return false;
		};

	    document.onmouseup = function (){
		    document.onmousemove = null;
		    document.onmouseup = null;
		    this.setCapture && this.setCapture();
	    };
	    this.setCapture && this.setCapture();
	    releaseCapture(); 
	    return false;
	};  
	oClose.onclick = function (){
		oDrag.style.display = "none";
		var oA = document.createElement("a");
		oA.className = "open";
		oA.href = "javascript:;";
		oA.title = "还原";
		document.body.appendChild(oA);
		oA.onclick = function (){
			oDrag.style.display = "block";
			oDrag.style.left = "5px";
			oDrag.style.top = "100px";
			document.body.removeChild(this);
			this.onclick = null;
		};
	};
	//oMin.onmousedown = oMax.onmousedown = 
	oClose.onmousedown = function (event){
		this.onfocus = function () {this.blur();};
		(event || window.event).cancelBubble = true;  
	};
}

window.onload = window.onresize = function (){
	var oDrag = document.getElementById("toolbar");
	var oTitle = get.byClass("title", oDrag)[0];
	menu_drag(oDrag, oTitle);
	
	var edit = document.getElementById("drag_div");
	var handel = document.getElementById("info");
	menu_drag(handel,edit);

}
