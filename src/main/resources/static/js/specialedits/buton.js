//初始化参数
var defaultParams = {
    specialid : null,//活动ID
    picMap : {},//存储上传的图片对象
    current_viewid : null,//当前编辑的视图ID
    current_info : null,//编辑按钮的对象info值
    current_obj : null,//编辑按钮的对象
    imgUrl : null,//图片地址
    type : null,//编辑的视图类型
    sessionId : null,
    //显示的按钮ID
    buttons_type : {}//需要显示的工具按钮类型
};
//创建图片对象，控制拖拽和大小
function createPicObject(id,obj,resolution){
	var max_width;
	var max_height;
	if(resolution=='sd'){
		max_width=640;
		max_height=530;
	}else if(resolution=='hd'){
		max_width=1280;
		max_height=720;
	}
	obj = $.parseJSON(obj);
	var width = (obj.width>=max_width?max_width:obj.width);
	var height = (obj.height>=max_height?max_height:obj.height);
	var imgsrc=obj.imgpath ;//|| "specialimgs/system/dot.gif";
	//'http://192.168.0.64:8080/music/'
	//img_url 是在编辑页面定义的变量
	imgsrc=imgsrc ?img_url+imgsrc:'../static/images/specialedits/dot.gif';
	var obj_type;//按钮类型
	var color_style;
	obj_type=id.substring(id.lastIndexOf('_')-6,id.length-2);
	if(obj_type =='videos'){
		color_style='red';
	}else if(obj_type =='button'){
		color_style='green';
	}else if(obj_type=='images'){
		color_style='blue';
	}
	if($("#but_"+id).length > 0){//已存在视图对象
		$("#but_"+id+">img").attr("src",imgsrc);//先换好图片，然后再定坐标 "left":"0px","top":"0px",
		$("#but_"+id).css({"width":width+"px","height":height+"px"});
	}else{//不存在就生成
		var str = "";
		if(id=='background_img'){
			str += '<div id="but_'+id+'" class="drag"  style="position:absolute;left:0px;top:0px;width:'+width+'px;height:'+height+'px;border:0px solid transparent;z-index:1;">';
		}else{
			str += '<div id="but_'+id+'" class="drag"  style="position:absolute;left:0px;top:0px;width:'+width+'px;height:'+height+'px;border:2px solid '+color_style+';z-index:6;">';
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
		str+='<input id="input_url" type="hidden" class="userinput">';
		str += '</div>';
		//str+='<div id="drag_butn_type" style="display:none;z-index:999;"><span>'+obj_type+'</span></div>';
		$("#background").append(str);
	}
	$("#but_"+id).show();//
	change(document.getElementById('but_'+id),0,resolution);
}

function mouseOver(id){
	
}

    function createImgButn(bg_imgid,resolution_cs){
    	var obj=bg_imgid.lastIndexOf('_');
		var num=bg_imgid.substring(obj+1,bg_imgid.length);
		var sign=bg_imgid.substring(obj-6,obj);
		var mohuzd_focus='focus_'+sign+'_';
		var mohuzd_blur='blur_'+sign+'_';
		mohuzd_focus+=num;
		mohuzd_blur+=num;
		focus_id='img_focus_'+sign+'_'+num;
		if(sign=='button'){
			$("#butn_info").append("<tr class='tr_pic'><td colspan='6'>跳转地址:<input type='text' id='url' name='video' /></td></tr>");
		}else if(sign=='videos'){
			$("#butn_info").append("<tr class='tr_pic'><td colspan='6'>视频内容id:<input type='text' id='url' name='desturl' /></td></tr>");
		}
		if(resolution_cs=='hd'){
			if(sign=='button' || sign=='videos'){
				$("#butn_info").append("<tr class='tr_pic'><td  colspan='6'>选中时放大系数:<input type='text' id='fdxs' name='fdxs' /></td></tr>");
		    	$("#butn_info").append("<tr colspan='3' class='tr_pic'><td>获得焦点图片:</td><td colspan='2'><input type='file' id='"+mohuzd_focus+"' class='butnimg' value='浏览'/></td><td><span id='uploadify_"+mohuzd_focus+"_message' style='color:red;'></span></td></tr>");
			}
			$("#butn_info").append("<tr class='tr_pic'><td>原始图片:</td><td colspan='2'><input type='file' id='"+mohuzd_blur+"' class='butnimg'  value='浏览'/></td><td><span id='uploadify_"+mohuzd_blur+"_message' style='color:red;'></span></td></tr>");
			$("#"+mohuzd_focus).uploadPic();
			$("#"+mohuzd_blur).uploadPic();//objId:video_1_blur
		}
		var cur_id='but_'+mohuzd_blur;
		var input_url=$('#'+cur_id+' input[id=input_url]').val();
		var input_fdxs=$('#'+cur_id+' input[id=input_fdxs]').val();
		if(input_url){
			$('#butn_info input[id=url]').attr('value',input_url);
		}
		if(input_fdxs){
			$('#butn_info input[id=fdxs]').attr('value',input_fdxs);
		}
		var butn_type;
		if(sign =='videos'){
			butn_type='视频';
		}else if(sign =='button'){
			butn_type='按钮';
		}else if(sign=='images'){
			butn_type='图片';
		}
		$('#butn_type').html(butn_type);
    }
	function setPositionStr(){
		var jsondata = "[";
		$(".drag").each(function(index,element){
		   var x = $(this).offset().left-110-2;
		   var y = $(this).offset().top-110-2;
		   var w = $(this).width();
		   var h = $(this).height();
		   var zindex = $(this).css("z-index");
		   //alert('zindex:'+zindex);
		   var type = $(this).attr("id");
		   
		   var lastnum=type.lastIndexOf('_');
		   var sign=type.substring(lastnum-6,type.length-2);
		   
		   var picpath = $(this).children("img").attr("src");
		   var url= $(this).children("input[id=input_url]").attr("value");
		   var fdxs= $(this).children("input[id=input_fdxs]").attr("value");
		   if(picpath.indexOf("undefined")>=0)
		   		picpath= "";
		   else
		   		picpath = picpath.substring(picpath.indexOf("specialimgs/"));
		   
		   if(jsondata.indexOf("}") != -1){
			   jsondata += ",";
		   }
		   jsondata += '{"sign":"'+sign+'",';
		   jsondata += '"x":"'+x+'","y":"'+y+'","w":"'+w+'","h":"'+h+'","zindex":"'+zindex+'","type":"'+type+'","picpath":"'+picpath+'","url":"'+url+'","fdxs":"'+fdxs+'"}';
		});
		jsondata += "]";
		return jsondata;
	}
	
	//保存视图坐标设置
	function submitView(){
		var flag=true;
		$(".userinput").each(function(index,element){
			var partentid=$(this).parent().attr('id');
			var userinput=$(this).val();
			if(!userinput){
				var num=partentid.lastIndexOf("_");
				var but_num=partentid.substring(num+1,partentid.length);
				var but_type=partentid.substring(num-6,num);
				var tsinfo;
				if(but_type=='videos'){
					tsinfo='请检查第'+but_num+'个视频按钮的视频id是否填写！';
				}else if(but_type=='button'){
					tsinfo='请检查第'+but_num+'个按钮对应的跳转路径是否填写！';
				}
				alert(tsinfo);
				flag=false;
			}
			
		});
		var specialname=$('#specialname').val();
		if(specialname==''){
			alert('请输入专辑名称！');
			return false;
		}
		var jsondata = "{";
		var specialid=$('#specialid').val();
		if(specialid && specialid!='undefined'){
			jsondata += '"specialid":"'+specialid+'",';
		}
		jsondata+='"specialname":"'+specialname+'"';
		
		var resolution=$('#resolution').val();
		jsondata+=',"resolution":"'+resolution+'"';
		var viewstr = setPositionStr();
		jsondata += ',view:'+viewstr;
		jsondata += "}";
		if(!flag){
			return false;
		}
		$.post("specialViewDataUpdate.do",{"json":jsondata},function(data){
			alert("保存成功！");
			//defaultParams.current_obj.siblings('span[name="msg"]').text("视图编辑成功");
			//$("#message").text("视图保存成功");
		});
	}
	
	//存入上传图片对象
	function setImgData(objId,data){
		defaultParams.picMap[objId] = data;
	}
	
	//获取上传图片对象
	function getImgData(id){
		return defaultParams.picMap[id];
	}
	
/*	function getSessionId(){
	   var c_name = 'JSESSIONID';
	   if(document.cookie.length>0){
	      c_start=document.cookie.indexOf(c_name + "=")
	      if(c_start!=-1){ 
	        c_start=c_start + c_name.length+1 
	        c_end=document.cookie.indexOf(";",c_start)
	        if(c_end==-1) c_end=document.cookie.length
	        return unescape(document.cookie.substring(c_start,c_end));
	      }
	   }
	  }*/

(function($){
	
	$.fn.extend({
		uploadPic : function(){
			return this.each(function(){
				var objId = $(this).attr("id");
				//var specialid=$('#specialid').val();
				$(this).uploadify({
					'swf'           :'../swf/uploadify.swf',
					'uploader'      :'../specialedits/uploadPictures.do;jsessionid=' + getSessionId(),
					'fileObjName'   :'uploadify',
					'cancelImg' 	:"../imgs/uploadify-cancel.png",
					'queueId'		:"fileQueue",
					'fileTypeExts'	:"*.jpg;*.gif;*.png",//限制文件类型
					'auto'			:true,//是否自动上传
					'multi'			:false,//是否允许多文件上传
					'buttonText'	:"图片上传",
					'method'		:"POST",
					'onUploadSuccess' : function(file,data,response) {//上传完成时触发 每个文件触发
						$("#uploadify_"+objId+"_message").html("图片上传成功！");
						createPicObject(objId,data,resolution);
					}
					
				});
			});
		}
	});
	function getSessionId(){
		var c_name = 'JSESSIONID';
		if(document.cookie.length>0){
			c_start=document.cookie.indexOf(c_name + "=")
			if(c_start!=-1){
				c_start=c_start + c_name.length+1
				c_end=document.cookie.indexOf(";",c_start)
		        if(c_end==-1) c_end=document.cookie.length;
		        return unescape(document.cookie.substring(c_start,c_end));
		    }
	   }
	}
	
})(jQuery);
	
	