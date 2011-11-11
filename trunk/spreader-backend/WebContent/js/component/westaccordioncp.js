// WEST导航的Tree菜单
var menuTree_1 = new Ext.tree.TreePanel({
			id : 'menuTree_1',
			// autoScroll : true,
			autoHeight : true,
			expanded : true,
			singleExpand : true,
			useArrows : true,
			rootVisible : true,
			root : new Ext.tree.AsyncTreeNode({
						id : '-1',
						text : '相关配置',
						children : [{
									id : 'node1',
									text : "策略调整",
									leaf : true,
									// url : '../strategy/showinit'
									url : '../strategy/showlist'
								}, {
									id : 'node2',
									text : "策略调度",
									leaf : true,
									url : '../strategy/dispatchlist'
								}]
					}),
			// 添加监听事件
			listeners : {
				'click' : function(view, rec) {
					var nodeurl = view.attributes.url;
					var nodetext = view.attributes.text;
					var nodeid = view.attributes.id;
					var leaf = view.attributes.leaf;
					if (leaf) {
						addTabNew(nodeurl, nodetext, nodeid, nodetext, '');
					}
				}
			}
		});
// 左侧菜单，可以收缩的模式
var accordPanel = new Ext.Panel({
			id : 'accordPanel',
			layout : 'accordion',
			frame : true,
			header : false,
			height : 500,
			animate : true,
			width : 200,
			collapsible : true,
			minSize : 200,
			maxSize : 350,
			split : true,
			bodyPadding : 5,
			items : [{
						autoScroll : true,
						border : false,
						title : '系统设置',
						iconCls : 'folder_cameraIcon',
						items : [menuTree_1]
					}, {
						autoScroll : true,
						border : false,
						title : '系统查询',
						html : '系统查询'
					}]
		});

// 相关公共函数
// 添加一个标签的函数
function addTab(tabname, url) {
	var tabs = mainTabPanel.add({
				title : tabname,
				html : url,
				closable : true
			});
	mainTabPanel.setActiveTab(tabs);
}