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
						expanded : true,
						children : [{
									id : 'cfgmenu1',
									text : "策略配置",
									leaf : true,
									url : '../strategy/init'
								}, {
									id : 'cfgmenu2',
									text : "系统策略配置",
									leaf : true,
									url : '../strategysys/init'
								}, {
									id : 'cfgmenu3',
									text : "系统调度配置",
									leaf : true,
									url : '../dispsys/init'
								}, {
									id : 'cfgmenu4',
									text : "策略分组配置",
									leaf : true,
									url : '../stggroup/init'
								}, {
									id : 'cfgmenu5',
									text : "用户分组配置",
									leaf : true,
									url : '../usergroup/init'
								}, {
									id : 'querymenu3',
									text : "队列查询",
									leaf : true,
									url : '../queuemoitor/init'
								}, {
									id : 'clientTaskStat',
									text : "客户端任务检索",
									leaf : true,
									url : '../taskstat/init'
								}, {
									id : 'clientTaskStatReport',
									text : "报表查询",
									leaf : true,
									url : '../taskreport/init'
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
						text : '查询',
						expanded : true,
						children : [{
									id : 'querymenu1',
									text : "用户查询",
									leaf : true,
									url : '../userinfo/init'
								}, {
									id : 'querymenu2',
									text : "虚拟身份查询",
									leaf : true,
									url : '../rbtregist/init'
								},
								// {
								// id : 'clientTaskStatDtl',
								// text : "客户端任务详细信息",
								// leaf : true,
								// url : '../taskstatdtl/init'
								// },
								{
									id : 'helpManage',
									text : "帮助信息维护",
									leaf : true,
									url : '../help/init'
								}, {
									id : 'passwordUpdate',
									text : "密码修改",
									leaf : true,
									url : '../psw/init'
								}, {
									id : 'logView',
									text : "日志查询",
									leaf : true,
									url : '../log/init'
								}, {
									id : 'appTopView',
									text : "App排行查询",
									leaf : true,
									url : '../appletop/init'
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
						expanded : true,
						children : [{
									id : 'contentLib',
									text : "微博内容库检索",
									leaf : true,
									url : '../contentlib/init'
								}, {
									id : 'photoLib',
									text : "图片库检索",
									leaf : true,
									url : '../photolib/init'
								}, {
									id : 'replySearch',
									text : "微博回复搜索",
									leaf : true,
									url : '../reply/init'
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
// TODO
var menuTree_ClientMgr = new Ext.tree.TreePanel({
			id : 'menuTree_ClientMgr',
			// autoScroll : true,
			// autoHeight : true,
			expanded : true,
			singleExpand : true,
			useArrows : true,
			rootVisible : true,
			root : new Ext.tree.AsyncTreeNode({
						id : '-1',
						text : '客户端管理',
						expanded : true,
						children : [{
									id : 'iprecord',
									text : "客户端IP查询",
									leaf : true,
									url : '/spreader-front/iprecord/init'
								}, {
									id : 'cooldownsetting',
									text : "下载曲线设置",
									leaf : true,
									url : '/spreader-front/cooldown/init'
								}, {
									id : 'clientCfgsetting',
									text : "客户端配置",
									leaf : true,
									url : '../clientcfg/init'
								}, {
									id : 'clientReportExecu',
									text : "客户端任务执行统计",
									leaf : true,
									url : '../cr/init'
								}, {
									id : 'clientMarketReportExecu',
									text : "执行总量统计",
									leaf : true,
									url : '../crcount/init'
								}, {
									id : 'androidUtils',
									text : "apk反编译帮助",
									leaf : true,
									url : '../androidutil/init'
								}, {
									id : 'yybPostData',
									text : "应用宝发包数据统计",
									leaf : true,
									url : '../yyb/init'
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
 * 关键字分类管理
 */
var menuTree_CategoryKeywordQuery = new Ext.tree.TreePanel({
			id : 'menuTree_CategoryKeywordQuery',
			// autoScroll : true,
			// autoHeight : true,
			expanded : true,
			singleExpand : true,
			useArrows : true,
			rootVisible : true,
			root : new Ext.tree.AsyncTreeNode({
						id : '-1',
						text : '关键字与分类管理',
						expanded : true,
						children : [{
									id : 'keywordmng',
									text : "关键字管理",
									leaf : true,
									url : '../keyword/init'
								}, {
									id : 'categorymng',
									text : "分类管理",
									leaf : true,
									url : '../category/init'
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
 * 消息系统
 */
var menuTree_NoticeSystem = new Ext.tree.TreePanel({
			id : 'menuTree_NoticeSystem',
			// autoScroll : true,
			// autoHeight : true,
			expanded : true,
			singleExpand : true,
			useArrows : true,
			rootVisible : true,
			root : new Ext.tree.AsyncTreeNode({
						id : '-1',
						text : '消息管理',
						expanded : true,
						children : [{
									id : 'noticeList',
									text : "消息汇总",
									leaf : true,
									url : '../notice/init'
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
						title : '客户端管理',
						iconCls : 'wrenchIcon',
						items : [menuTree_ClientMgr]
					}, {
						autoScroll : true,
						border : false,
						title : '策略及调度维护',
						iconCls : 'wrenchIcon',
						items : [menuTree_Stg]
					}, {
						autoScroll : true,
						border : false,
						title : '关键字与分类',
						iconCls : 'folder_cameraIcon',
						items : [menuTree_CategoryKeywordQuery]
					}, {
						autoScroll : true,
						border : false,
						title : '消息管理',
						iconCls : 'folder_cameraIcon',
						items : [menuTree_NoticeSystem]
					}, {
						autoScroll : true,
						border : false,
						title : '基础信息查询',
						iconCls : 'app_boxesIcon',
						items : [menuTree_InfoQuery]
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