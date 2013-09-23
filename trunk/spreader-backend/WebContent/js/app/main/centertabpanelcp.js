var mainTabPanel = new Ext.TabPanel({
			id : 'centerPanel',
			frame : true,
			activeTab : 0,
			height : 500,
			items : {
				title : '喜马拉雅任务提醒',
				id : 'frame1',
				html : getXimalayaExecute(),
				autoScroll : true
			}
		});

function getXimalayaExecute() {
	var str = '';
	Ext.Ajax.request({
				url : '../extutil/ximalayatask?_time=' + new Date().getTime(),
				async : false,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					str = result.msg;
				},
				failure : function() {
				}
			});
	return str;
}
