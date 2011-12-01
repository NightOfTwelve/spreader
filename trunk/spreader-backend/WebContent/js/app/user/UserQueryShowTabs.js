/**
 * Tab标签卡范例
 * 
 * @author XiongChun
 * @since 2010-10-27
 */
Ext.onReady(function() {
			var tabs = new Ext.TabPanel({
						region : 'center',
						enableTabScroll : true,
						// autoWidth : true,
						height : 200
					});
			// 每一个Tab都可以看作为一个Panel
			tabs.add({
						title : '<span class="commoncss">汇总信息</span>',
						layout : 'border',
						// tbar:tb, //工具栏
						items : [{
									region : 'north',
									split : true,
//									collapsible : true,
									height : 120,
									items:[userSinaForm]
								}, {
									region : 'center',
									split : true,
									items:[sinaUsergGrid]
								}],
						iconCls : 'book_previousIcon', // 图标
						closable : true
					});
			tabs.add({
						id : 'tab2',
						title : '<span class="commoncss">明细信息</span>',
						html : '明细信息'
					});
			tabs.activate(0);

			// 布局模型
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [tabs]
					});

		});