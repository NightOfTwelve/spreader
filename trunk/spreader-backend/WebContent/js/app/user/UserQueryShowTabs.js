
Ext.onReady(function() {
	var tabs = new Ext.TabPanel({
				region : 'center',
				enableTabScroll : true,
				tabWidth : 200,
				// autoWidth : true,
				height : 200
			});
	// 每一个Tab都可以看作为一个Panel
	tabs.add({
				title : '<span class="commoncss"><font color="red">新浪微博</font></span>',
				id : 'sina',
				layout : 'border',
				// tbar:tb, //工具栏
				items : [{
							region : 'north',
							split : true,
							// collapsible : true,
							height : 120,
							items : [userSinaForm]
						}, {
							region : 'center',
							split : true,
							height : 500,
							items : [sinaUsergGrid]
						}],
				iconCls : 'book_previousIcon', // 图标
				closable : true
			});
	tabs.add({
				id : 'renren',
				title : '<span class="commoncss"><font color="blue">人人网</font></span>',
				html : '明细信息'
			});
	tabs.add({
				id : 'qqweibo',
				title : '<span class="commoncss"><font color="#006400">腾讯微博</font></span>',
				html : '明细信息'
			});
	tabs.add({
				id : 'kaixin01',
				title : '<span class="commoncss"><font color="#8b008b">开心网</font></span>',
				html : '明细信息'
			});
	tabs.activate(0);

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [tabs]
			});

});