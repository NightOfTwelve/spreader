var mainTabPanel = new Ext.TabPanel({
	id : 'centerPanel',
	frame : true,
	activeTab : 0,
	height : 500,
	items : {
		title : '喜马拉雅任务提醒',
		id : 'frame1',
		html : '<div id="taskManagerId">加载中,请稍候...</div>' + getXimalayaExecute(),
		autoScroll : true,
		tbar : [{
			text : '刷新',
			iconCls : 'arrow_refreshIcon',
			handler : function() {
				Ext.Ajax.request({
					url : '../extutil/ximalayatask?_time=' + new Date().getTime(),
					async : true,
					success : function(response) {
						Ext.select("#taskManagerId").update(Ext.decode(response.responseText).msg);
					},
					failure : function() {
					}
				});
			}
		}]
	}
});

function getXimalayaExecute() {
	var str = '';
	Ext.Ajax.request({
		url : '../extutil/ximalayatask?_time=' + new Date().getTime(),
		async : true,
		success : function(response) {
			Ext.select("#taskManagerId").update(Ext.decode(response.responseText).msg);
		},
		failure : function() {
		}
	});
	return str;
}