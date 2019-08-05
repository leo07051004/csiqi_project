//初始化左侧
function InitLeftMenu() {
	$("#nav").accordion({animate:false});//为id为nav的div增加手风琴效果，并去除动态滑动效果
    $.each(_menus.menus, function(i, n) {//$.each 遍历_menu中的元素
		var menulist ='';
		menulist +='<ul>';
        $.each(n.resourceList, function(j, o) {
			menulist += '<li><div><a ref="'+o.f_pr_id+'" href="#" rel="' + o.f_pr_url + '" ><span class="icon icon-page" >&nbsp;</span><span class="nav">' + o.f_pr_name + '</span></a></div></li> ';
        })
		menulist += '</ul>';

		$('#nav').accordion('add', {
            title: n.f_pr_name,
            content: menulist,
            iconCls: 'icon icon-sys'
        });

    });

	$('.easyui-accordion li a').click(function(){//当单击菜单某个选项时，在右边出现对用的内容
		var tabTitle = $(this).children('.nav').text();//获取超链里span中的内容作为新打开tab的标题
	
		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");//获取超链接属性中ref中的内容
		//var icon = getIcon(menuid,icon);
		var icon = 'icon';

		addTab(tabTitle,url,icon);//增加tab
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});

	//选中第一个
	var panels = $('#nav').accordion('panels');
	var t = panels[panels.length - 1].panel('options').title;
    $('#nav').accordion('unselect', t);
}
//获取左侧导航的图标
function getIcon(menuid){
	var icon = 'icon ';
	$.each(_menus.menus, function(i, n) {
		 $.each(n.resourceList, function(j, o) {
		 	if(o.f_pr_id==menuid){
				icon += o.icon;
			}
		 })
	})

	return icon;
}

function addTab(subtitle,url,icon){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			icon:icon
		});
	}else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url)
{
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven()
{
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		})
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

function app_ajax(url,formData,fun,error){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		data : formData,
		dataType: "json",
		error : function() {// 请求失败处理函数
		},
		success : function(d) {
			if(d.total==-1) {
				alert("用户尚未登录 请登录");
				window.location.href='';
				return false;
			}	
			fun(d);
		},
		error: function(msg){
			error(msg);
		}
	});
}

//对Date的扩展，将 Date 转化为指定格式的String 
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) 
{ //author: meizz 
	var o = { 
	 "M+" : this.getMonth()+1,                 //月份 
	 "d+" : this.getDate(),                    //日 
	 "h+" : this.getHours(),                   //小时 
	 "m+" : this.getMinutes(),                 //分 
	 "s+" : this.getSeconds(),                 //秒 
	 "q+" : Math.floor((this.getMonth()+3)/3), //季度 
	 "S"  : this.getMilliseconds()             //毫秒 
	}; 
	if(/(y+)/.test(fmt)) 
	 fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	for(var k in o) 
	 if(new RegExp("("+ k +")").test(fmt)) 
	fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
	return fmt; 
}
/**
 * 获取字符串长度
 * @return {字符串字节数，中文按两个字节计算}
 */
String.prototype.len = function() {
	return this.replace(/[^\x00-\xff]/g, "00").length;       
};

/**
 * 获得打开该页的URL源
 */
function getOpenerHref() {
	var referrer = document.referrer; // 载入当前页的URL地址
	var parentHref = '';
	try{parentHref = parent.location.href;}catch(e){} // 在iframe中的URL
	var selfHref = self.location.href; // 当前URL
	// 无载入URL或者载入URL为iframe外层url返回当前页URL
	return (referrer != null && referrer != '' && referrer != parentHref) ? referrer : selfHref;
}
function openDialog(dialogObj,url) {
	dialogObj.find("iframe").attr("src",url);
	dialogObj.dialog('open');
	
	
}
function closeDialog(obj) {
	obj.dialog('close');
}
function showMessager(title, msg){
	$.messager.show({
		title:title,
		msg:msg,
		showType:'show'
	});
}
function gotoSearch(obj) {
	if(typeof EasyuiDatagrid == 'function'){
		obj.datagrid('reload',(new EasyuiDatagrid()).queryParams);
		obj.datagrid('clearSelections');
		if($.isFunction(window.setBtnDisableEnable)){
			window.setBtnDisableEnable();
		}
	}
}

/**
 * 检查ajax请求有效性
 * @param {Object} data 返回数据
 * @return {Boolean} 校验结果
 */
function checkAjaxRequestValidity(data) {
	if (data) {
		var obj = null;
		if (typeof(data) == "string") {
			try{obj = eval("(" + data + ")");}catch(e){}
		} else if (typeof(data) == "object") {
			obj = data;
		}
		if (obj && obj.http_status_code_) {
			var code = obj.http_status_code_;
			if (code == "exception") {
				var msg = "服务器异常，请稍后再试！";
				if ($ && $.messager) {
					$.messager.alert("提示", msg, 'warning');
					$.messager.progress('close');
				} else {
					alert(msg);
				}
				return false;
			}
		}
		return true;
	}
	return true;
}