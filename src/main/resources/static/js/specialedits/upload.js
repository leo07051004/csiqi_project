function getPath(obj,fileQuery,transImg) {
	var imgSrc = '', imgArr = [], strSrc = '' ;
	if(window.navigator.userAgent.indexOf("MSIE")>=1){ // IE������ж�
		if(obj.select){
			obj.select();
  			var path=document.selection.createRange().text;
			alert(path) ;
			obj.removeAttribute("src");
			imgSrc = fileQuery.value ;
			imgArr = imgSrc.split('.') ;
			strSrc = imgArr[imgArr.length - 1].toLowerCase() ;
			if(strSrc.localeCompare('jpg') === 0 || strSrc.localeCompare('jpeg') === 0 || strSrc.localeCompare('gif') === 0 || strSrc.localeCompare('png') === 0){
				obj.setAttribute("src",transImg);
				obj.style.filter=
				 "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+path+"', sizingMethod='scale');"; // IEͨ���˾��ķ�ʽʵ��ͼƬ��ʾ
			}else{
				throw new Error('File type Error! please image file upload..'); 
			}
		}else{
			// alert(fileQuery.value) ;
			imgSrc = fileQuery.value ;
			imgArr = imgSrc.split('.') ;
			strSrc = imgArr[imgArr.length - 1].toLowerCase() ;
			if(strSrc.localeCompare('jpg') === 0 || strSrc.localeCompare('jpeg') === 0 || strSrc.localeCompare('gif') === 0 || strSrc.localeCompare('png') === 0){
				obj.src = fileQuery.value ;
			}else{
				throw new Error('File type Error! please image file upload..') ;
			}
		}
	}else{
		var file =fileQuery.files[0];
		var reader = new FileReader();
		reader.onload = function(e){
			imgSrc = fileQuery.value ;
			imgArr = imgSrc.split('.') ;
			strSrc = imgArr[imgArr.length - 1].toLowerCase() ;
			if(strSrc.localeCompare('jpg') === 0 || strSrc.localeCompare('jpeg') === 0 || strSrc.localeCompare('gif') === 0 || strSrc.localeCompare('png') === 0){
				obj.setAttribute("src", e.target.result) ;
			}else{
				throw new Error('File type Error! please image file upload..') ;
			}
  		// alert(e.target.result); 
		}
 		reader.readAsDataURL(file);
	}
}

function show(fileid,imgid){
	//���¼�Ϊ�����ͻ���·��
	//alert('fileid:'+fileid+'--imgid:'+imgid);
	var file_img=document.getElementById(imgid),
	iptfileupload = document.getElementById(fileid) ;
	getPath(file_img,iptfileupload,file_img) ;
}