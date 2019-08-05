var setting = {
			view: {
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				onClick:zTreeOnClick
			}
		};
		//禁止拖拽
		function beforeDrag(treeId, treeNodes) {
			return false;
		}
		
		function zTreeOnClick(event,treeId,treeNode){
			//点击事件
			if(!treeNode.isParent){
				new EasyuiDatagrid(treeNode.id);
				$("#right").show();
				$("#f_pa_resourceId").val(treeNode.id);
				gotoSearch($('#easyui-treegrid'));
			}
		}
		
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("ztree");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		
		$(document).ready(function(){
			$.ajax({
				url:"resource_list.do?f_pr_id=0",
				success:function(data){
					zNodes = JSON.parse(data);
					var root = {id:"0",name:"资源目录",open:true};
					zNodes.push(root);
					$.fn.zTree.init($("#ztree"), setting, zNodes);
					$("#selectAll").bind("click", selectAll);
				}
			});
		});