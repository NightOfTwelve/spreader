// WEST导航的Tree菜单
var menuTree_Stg = new Ext.tree.TreePanel({
			id : 'menuTree_Stg',
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
									id : 'stgnode1',
									text : "策略配置",
									leaf : true,
									url : '../strategy/showlist'
								}, {
									id : 'stgnode2',
									text : "调度配置",
									leaf : true,
									url : '../strategy/dispatchlist'
								}, {
									id : 'stgnode3',
									text : "系统策略配置",
									leaf : true,
									url : '../strategysys/init'
								}, {
									id : 'stgnode4',
									text : "系统调度配置",
									leaf : true,
									url : '../dispsys/dispatchlist'
								}, {
									id : 'stgnode5',
									text : "分组配置",
									leaf : true,
									url : '../stggroup/init'
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
/**
 * 查询的树菜单
 */
var menuTree_InfoQuery = new Ext.tree.TreePanel({
			id : 'menuTree_InfoQuery',
			// autoScroll : true,
			autoHeight : true,
			expanded : true,
			singleExpand : true,
			useArrows : true,
			rootVisible : true,
			root : new Ext.tree.AsyncTreeNode({
						id : '-1',
						text : '信息查询',
						children : [{
									id : 'infonode1',
									text : "用户查询",
									leaf : true,
									url : '../userinfo/init'
								}, {
									id : 'infonode2',
									text : "机器人查询",
									leaf : true,
									url : '../rbtregist/init'
								}, {
									id : 'infonode3',
									text : "队列查询",
									leaf : true,
									url : '../queuemoitor/init'
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
/**
 * 内容库查询
 */
var menuTree_ContentLibQuery = new Ext.tree.TreePanel({
			id : 'menuTree_ContentLibQuery',
			// autoScroll : true,
			// autoHeight : true,
			expanded : true,
			singleExpand : true,
			useArrows : true,
			rootVisible : true,
			root : new Ext.tree.AsyncTreeNode({
						id : '-1',
						text : '基础信息',
						children : [{
									id : 'contentLib1',
									text : "微博内容库检索",
									leaf : true,
									url : '../contentlib/init'
								}, {
									id : 'photoLib',
									text : "图片库检索",
									leaf : true,
									url : '../photolib/init'
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
			height : document.documentElement.clientHeight - 120,
			layoutConfig : {
				animate : true
			},
			width : 200,
			collapsible : true,
			minSize : 200,
			maxSize : 350,
			split : true,
			bodyPadding : 5,
			items : [{
						autoScroll : true,
						border : false,
						title : '基础信息查询',
						iconCls : 'app_boxesIcon',
						items : [menuTree_InfoQuery]
					}, {
						autoScroll : true,
						border : false,
						title : '策略及调度维护',
						iconCls : 'wrenchIcon',
						items : [menuTree_Stg]
					}, {
						autoScroll : true,
						border : false,
						title : '微博内容库',
						iconCls : 'folder_cameraIcon',
						items : [menuTree_ContentLibQuery]
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