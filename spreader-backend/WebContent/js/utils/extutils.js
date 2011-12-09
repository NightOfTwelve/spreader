/**
 * 隐藏一个panel
 * 
 * @param divId
 *            例 : <a id="hideit" href="#">Toggle the west region</a> , 这里的id
 * @param panelId
 *            需要被隐藏的面板的id
 */
function hidePanelLink(divId, panelId) {
	Ext.get(divId).on('click', function() {
				var w = Ext.getCmp(panelId);
				w.collapsed ? w.expand() : w.collapse();
			});
}

function hidePanelDbClick(panelId) {
	var w = Ext.getCmp(panelId);
	w.collapsed ? w.expand() : w.collapse();
}

function addTabNew(url, name, menuid, pathCh, icon) {
	var id = "tab_id_" + menuid;
	if (url == '#' || url == '') {
		Ext.Msg.alert('提示', '此菜单还没有指定请求地址,无法为您打开页面.');
	} else {
		// var index = url.indexOf('.ered');
		// if(index != -1)
		// url = url + '&menuid4Log=' + menuid;
		var n = mainTabPanel.getComponent(id);
		if (!n) {
			// 如果对centerPanel进行遮罩,则可以出现阴影mainTabs
			Ext.getCmp('centerPanel').getEl().mask(
					'<span style=font-size:12>正在加载页面,请稍等...</span>',
					'x-mask-loading');
			// document.getElementById('endIeStatus').click();//解决Iframe
			// IE加载不完全的问题
			// 兼容IE和FF触发.click()函数
			// var endIeStatus = document.getElementById("endIeStatus");
			// if(document.createEvent){
			// var ev = document.createEvent('HTMLEvents');
			// ev.initEvent('click', false, true);
			// endIeStatus.dispatchEvent(ev);
			// }
			// else endIeStatus.click();
			n = mainTabPanel.add({
				id : id,
				// title:"<img align='top' class='IEPNG'
				// src='./resource/image/ext/" + icon + "'/>" + name,
				title : name,
				closable : true,
				layout : 'fit',
				listeners : {
					activate : function() {
						Ext.getCmp('centerPanel').setTitle(pathCh)
					}
				},
				// html:'<iframe scrolling="auto" frameborder="0" width="100%"
				// height="100%" src='+url+'></iframe>'
				// 如果功能页面使用中心区域阴影加载模式则使用下面的代码unmaskCenterPanel();页面加载完毕后取消阴影
				html : '<iframe scrolling="auto" frameborder="0" onload="unmaskCenterPanel()" width="100%" height="100%" src='
						+ url + '></iframe>'
			}).show();
		}
		mainTabPanel.setActiveTab(n);
	}
}
function unmaskCenterPanel() {
	// 如果对centerPanel进行遮罩,则可以出现阴影
	Ext.getCmp('centerPanel').getEl().unmask();
}
/**
 * 鼠标悬浮显示细节函数
 * 
 * @param {}
 *            value
 * @param {}
 *            p
 * @param {}
 *            record
 * @return {}
 */
function renderBrief(value, p, record) {
	if (!Ext.isEmpty(value)) {
		var bk = '<div title="' + value + '">' + value.substr(0, 50);
		if (value.length > 50) {
			bk = bk + "...";
		}
		bk = bk + "</div>";
		return bk;
	} else {
		return "";
	}
}
/**
 * 截取最后一个分隔符
 * 
 * @param {}
 *            id
 * @return {}
 */
function subStrLastId(id) {
	if (id != null) {
		var str = '';
		str = id.substring(0, id.length - 1);
		return str;
	}
}
/**
 * 日期格式
 * 
 * @param {}
 *            value
 * @return {}
 */
function renderDate(value) {
	return value != null ? new Date(value).format('Y-m-d') : '';
}
/**
 * 性别中文渲染
 * 
 * @param {}
 *            value
 */
function renderGender(value) {
	if (value == '1') {
		return '男';
	} else {
		return '女';
	}

}
/**
 * 渲染是否位机器人
 * 
 * @param {}
 *            value
 * @return {}
 */
function rendIsRobot(value) {
	if (value) {
		return '是';
	} else {
		return '否';
	}
}
/**
 * 显示系统时钟
 */
function showTime() {
	var date = new Date();
	var h = date.getHours();
	h = h < 10 ? '0' + h : h;
	var m = date.getMinutes();
	m = m < 10 ? '0' + m : m;
	var s = date.getSeconds();
	s = s < 10 ? '0' + s : s;
	document.getElementById('rTime').innerHTML = h + ":" + m + ":" + s;
}

window.onload = function() {
	setInterval("showTime()", 1000);
}