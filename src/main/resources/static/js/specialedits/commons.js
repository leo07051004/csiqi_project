function deleteButtn(){
	edit_num=edit_num-1;
	var butnid=$("#viewid").val();
	$("#"+butnid).remove();
	var id_tmp=butnid.substr(4,butnid.length);
	$("#uploadify_"+id_tmp+"_message").html("未上传图片！");
	document.getElementById("info").style.display='none';
	//$("table .tr_pic").remove();
	$("input.coordinate").attr('value','');
	$("input.butnimg").attr('value','');
}
/* 清空原来的数据 */
function clearData(){
	$("input.coordinate").attr('value','');
	$("input.butnimg").attr('value','');
	$("table .tr_pic").remove();
}
var lastsign;
function edit(sign){
	if(edit_num>1){
		if($('#url').val()==''){
			if(lastsign=='videos'){
				alert('请检查是否输入视频id');
			}else if(lastsign=='button'){
				alert('请检查是否输入跳转路径');
			}
			return false;
		}
	}
	var butn_type;
	var color_style;
	if(sign =='videos'){
		butn_type='视频';
		color_style='red';
	}else if(sign =='button'){
		butn_type='按钮';
		color_style='green';
	}else if(sign=='images'){
		butn_type='图片';
		color_style='blue';
	}
	$('#butn_type').html(butn_type).css('color',color_style);
	var id=sign+'_'+edit_num;
	var obj='{"id":"blur_'+id+'","sign":"'+sign+'","type":"'+id+'"}';
	createPicObject('blur_'+id,obj,resolution);
	
	clearData();
	if(resolution=='sd'){
		$('#info').css({'left':'760px','top':'110px'});
		document.getElementById("info").style.display='block';
		if(sign=='button'){
			$("#butn_info").append("<tr class='tr_pic'><td colspan='6'>跳转地址:<input type='text' id='url' name='video' /></td></tr>");
		}else if(sign=='videos'){
			$("#butn_info").append("<tr class='tr_pic'><td colspan='6'>视频内容id:<input type='text' id='url' name='desturl' /></td></tr>");
		}
	}else{
		$('#info').css({'left':'1400px','top':'110px'});
		document.getElementById("info").style.display='block';
		if(sign=='button'){
			$("#butn_info").append("<tr class='tr_pic'><td colspan='6'>跳转地址:<input type='text' id='url' name='video' /></td></tr>");
			$("#butn_info").append("<tr class='tr_pic'><td  colspan='6'>选中时放大系数:<input type='text' id='fdxs' name='fdxs' /></td></tr>");
			$("#butn_info").append("<tr colspan='3' class='tr_pic'><td>获得焦点图片:</td><td colspan='2'><input type='file' id='focus_"+id+"' class='butnimg' value='浏览'/></td><td><span id='uploadify_focus_"+id+"_message' style='color:red;'></span></td></tr>");
		}else if(sign=='videos'){
			$("#butn_info").append("<tr class='tr_pic'><td colspan='6'>视频内容id:<input type='text' id='url' name='desturl' /></td></tr>");
			$("#butn_info").append("<tr class='tr_pic'><td  colspan='6'>选中时放大系数:<input type='text' id='fdxs' name='fdxs' /></td></tr>");
			$("#butn_info").append("<tr colspan='3' class='tr_pic'><td>获得焦点图片:</td><td colspan='2'><input type='file' id='focus_"+id+"' class='butnimg' value='浏览'/></td><td><span id='uploadify_focus_"+id+"_message' style='color:red;'></span></td></tr>");
		}
		$("#butn_info").append("<tr class='tr_pic'><td>原始图片:</td><td colspan='2'><input type='file' id='blur_"+id+"' class='butnimg'  value='浏览'/></td><td><span id='uploadify_"+id+"_message' style='color:red;'></span></td></tr>");
		$("#focus_"+id).uploadPic();
		$("#blur_"+id).uploadPic();
		
	}
	edit_num=edit_num+1;
	lastsign=sign;	
}
function keyDown(event){
	event=event||window.event;
	var keycode = event.which ||event.keyCode;
    // var realkey = String.fromCharCode(e.which);
    if(46==keycode){
    	deleteButtn();
    }
}
document.onkeydown = keyDown;

function goBack(){
	window.location.href='specialList.do';
}

/**
 * 修改专辑时根据json字符串生成原来编辑的专辑
 * @param obj 192.168.0.64
 */
function generatePicObject(obj,resolution){
	//var obj=eval('('+ str +')');
	var id=obj.type;
	var imgsrc=obj.picpath;
	var x=obj.x;
	var y=obj.y;
	var w=obj.w;
	var h=obj.h;
	var zindex=obj.zindex;
	var url=obj.url;
	var fdxs=obj.fdxs;
	
	imgsrc=imgsrc ?img_url+imgsrc:'../static/images/specialedits/dot.gif';
	var obj_type=obj.sign;//按钮类型
	var color_style;
	if(obj_type =='videos'){
		color_style='red';
	}else if(obj_type =='button'){
		color_style='green';
	}else if(obj_type=='images'){
		color_style='blue';
	}
	var str = "";
	if(id=='background_img'){
		str += '<div id="'+id+'" class="drag" style="position:absolute;left:'+x+'px;top:'+y+'px;width:'+w+'px;height:'+h+'px;border:0px solid transparent;z-index:'+zindex+';">';
	}else{
		str += '<div id="'+id+'" class="drag" style="position:absolute;left:'+x+'px;top:'+y+'px;width:'+w+'px;height:'+h+'px;border:2px solid '+color_style+';z-index:'+zindex+';">';
	}
	str += '<img src="'+imgsrc+'" width="100%" height="100%"/>';
	str += '<div class="resizeL"></div>';
	str += '<div class="resizeT"></div>';
	str += '<div class="resizeR"></div>';
	str += '<div class="resizeB"></div>';
	str += '<div class="resizeLT"></div>';
	str += '<div class="resizeTR"></div>';
	str += '<div class="resizeBR"></div>';
	str += '<div class="resizeLB"></div>';
	
	if(url && url!='undefined'){
		str+='<input class="userinput" id="input_url" type="hidden" value="'+url+'"/>';
	}
	if(fdxs && fdxs!='undefined'){
		str+='<input id="input_fdxs" type="hidden" value="'+fdxs+'"/>';
	}
	str += '</div>';
	//str+='<div id="drag_butn_type" style="display:none;z-index:999;"><span>'+obj_type+'</span></div>';
	$("#background").append(str);
	$('#'+id).show();//
	change(document.getElementById(id),0,resolution);
}

$.fn.extend({
	//获取用户输入的值并赋值
	getInputData : function(thisobj){
		var v=$(thisobj).val();
		var zbid=$(thisobj).attr('id');
		var butnid=$("#viewid").val();
		var options=null;
		if(zbid=='xzb'){//x坐标
			options='left';
		}else if(zbid=='yzb'){//y坐标
			options='top';
		}else if(zbid=='wh'){//width
			options='width';
		}else if(zbid=='ht'){//height
			options='height';
		}
		$('#'+butnid).css(options,v+'px');
	},
	
	getUrlInputData :function(thisobj){
		var v=$(thisobj).val();
		var zbid=$(thisobj).attr('id');//input控件id
		var butnid=$("#viewid").val();
		var lastnum=butnid.lastIndexOf('_');
		var but_type=butnid.substring(lastnum-6,butnid.length);
		var str='<input type="hidden" id="input_'+zbid+'" value="'+v+'"/>';
		if($("#but_blur_"+but_type +" input[id=input_"+zbid+"]").length>0){
			$("#but_blur_"+but_type +" input[id=input_"+zbid+"]").attr('value',v);
		}else{
			$("#but_blur_"+but_type).append(str);
		}
	}
	
});