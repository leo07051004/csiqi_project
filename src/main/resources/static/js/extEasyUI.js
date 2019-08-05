/**
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 */
$.extend($.fn.panel.defaults, {
	onBeforeDestroy : function() {
		var frame = $('iframe', this);
		try {
			if (frame.length > 0) {
				for (var i = 0; i < frame.length; i++) {
					frame[i].src = '';
					frame[i].contentWindow.document.write('');
					frame[i].contentWindow.close();
				}
				frame.remove();
				if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
					try {
						CollectGarbage();
					} catch (e) {
					}
				}
			}
		} catch (e) {
		}
	}
});
/**
 * 通用错误提示
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 * @requires jQuery,EasyUI,ajaxInterceptor
 */
_onLoadError = {
	onLoadError : function(XMLHttpRequest) {
		checkAjaxRequestValidity(XMLHttpRequest.responseText);
	}
};
$.extend($.fn.datagrid.defaults, _onLoadError);
$.extend($.fn.treegrid.defaults, _onLoadError);
$.extend($.fn.tree.defaults, _onLoadError);
$.extend($.fn.combogrid.defaults, _onLoadError);
$.extend($.fn.combobox.defaults, _onLoadError);
$.extend($.fn.form.defaults, _onLoadError);
/**
 * 获得树数据
 * @param {数据} data
 * @param {树属性} opt
 * @return {树数据}
 */
_getTreeData = function(data, opt) {
	var idField, textField, parentField;
	if (opt.parentField) {
		idField = opt.idField || 'id';
		textField = opt.textField || 'text';
		parentField = opt.parentField || 'pid';
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idField]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]] && data[i][idField] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textField];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textField];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};
/**
 * 扩展tree和combotree，使其支持平滑数据格式
 * @requires jQuery,EasyUI
 */
_treeLoadFilter = {
	loadFilter : function(data, parent) {
		var opt = $(this).data().tree.options;
		return _getTreeData(data, opt);
	}
};
$.extend($.fn.combotree.defaults, _treeLoadFilter);
$.extend($.fn.tree.defaults, _treeLoadFilter);
/**
 * 扩展treegrid，使其支持平滑数据格式
 * @requires jQuery,EasyUI
 */
$.extend($.fn.treegrid.defaults, {
	loadFilter : function(data, parentId) {
		var opt = $(this).data().treegrid.options;
		return _getTreeData(data, opt);
	}
});