// 构造树的根节点ROOT
var stgroot = new Ext.tree.AsyncTreeNode({
			id : '-1',
			text : '配置列表'
		});
// 策略列表树
var stgdisptree = new Ext.tree.TreePanel({
			id : 'stgtree',
			autoScroll : false,
			autoHeight : true,
			expanded : true,
			singleExpand : true,
			useArrows : true,
			rootVisible : true,
			// tbar : [
			// // {
			// // text : '保存修改',
			// // iconCls : 'addIcon',
			// // handler : function() {
			// // submitTreeData();
			// // }
			// // }, '-',
			// {
			// text : '修改',
			// iconCls : 'edit1Icon',
			// handler : function() {
			// editInit();
			// }
			// }],
			root : stgroot,
			loader : new Ext.tree.TreeLoader({
						dataUrl : '../strategy/createdisptree?time='
								+ new Date().getTime(),
						processResponse : function(response, node, callback,
								scope) {
							var json = response.responseText;
							var respObj = Ext.util.JSON.decode(json);
							try {
								submitid = respObj.id;
								var o = [tranNodeConfig('data',
										respObj.treename, respObj.def,
										respObj.data)];
								// var o = response.responseData ||
								// Ext.decode(json);
								node.beginUpdate();
								for (var i = 0, len = o.length; i < len; i++) {
									var n = this.createNode(o[i]);
									if (n) {
										node.appendChild(n);
									}
								}
								node.endUpdate();
								this.runCallback(callback, scope || node,
										[node]);
							} catch (e) {
								this.handleFailure(response);
							}
						},
						listeners : {
							"beforeload" : function(treeloader, node) {
								treeloader.baseParams = {
									name : GOBJID,
									disname : GDISNAME,
									// TODO
									id : GDISPID
								};
							}
						}
					})
		});
stgdisptree.expand(true, true);
// 树形编辑器
var treeEditor = new Ext.tree.TreeEditor(Ext.getCmp('stgtree'), {
			id : 'stgtreeEdit',
			allowBlank : false
		});
/**
 * 右键菜单相关代码
 */
// 给tree添加右键菜单事件
stgdisptree.on('rightMenu', stgdisptree.rightMenu, stgdisptree);
// 定义右键菜单
var rightMenu = new Ext.menu.Menu({
			id : 'rightMenu',
			items : [
					// {
					// id : 'addNode',
					// text : '添加',
					// // 增加菜单点击事件
					// menu : [{
					// id : 'insertNode',
					// text : '添加兄弟节点',
					// handler : function(tree) {
					// insertNode();
					// }
					//
					// }, {
					// id : 'appendNode',
					// text : '添加儿子节点',
					// handler : function(tree) {
					// appendNodeAction();
					// }
					// }]
					// }, '-',
					{
				id : 'delNode',
				text : '删除',
				handler : function(tree) {
					dispatchDeleteNode(stgdisptree);
				}
			}
					// , {
					// id : 'modifNode',
					// text : '修改',
					// handler : function() {
					// modifNode()
					// }
					// }
//			, {
//				id : 'viewNode',
//				text : '查看',
//				handler : function(tree) {
//					veiwNodeAction();
//				}
//			}
			]
		});
// 添加点击事件
stgdisptree.on('click', function(node) {
			if (node.id == -1) {
				return;
			}
			var isCollection = node.attributes.isCollection;
			if (isCollection) {
				collectionRender(node);
			} else {
				renderPropertyGrid(node);
			}
		});

// 增加右键弹出事件
stgdisptree.on('contextmenu', function(node, event) {
			// 使用preventDefault方法可防止浏览器的默认事件操作发生
			event.preventDefault();
			node.select();
			rightMenu.showAt(event.getXY());// 取得鼠标点击坐标，展示菜单
		});
