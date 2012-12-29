var mainTabPanel = new Ext.TabPanel({
	id : 'centerPanel',
	frame : true,
	activeTab : 0,
	height : 500,
	// autoHeight:true,
	// collapsible:true,
	items : {
		title : '我的任务',
		id : 'frame1',
//		html : '<iframe src="http://www.baidu.com" frameborder="0" width="100%" height="100%" ></iframe>',
		autoScroll : true
	}
});
