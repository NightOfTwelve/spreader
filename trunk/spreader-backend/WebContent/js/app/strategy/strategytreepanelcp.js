// 构造树的根节点ROOT
var stgroot = new Ext.tree.AsyncTreeNode({
			id : '-1',
			text : '配置列表'
		});
// 策略列表树
var stgtree = new Ext.tree.TreePanel({
	id : 'stgtree',
	autoScroll : false,
	autoHeight : true,
	expanded : true,
	singleExpand : true,
	useArrows : true,
	rootVisible : true,
	tbar : [{
				text : '保存修改',
				iconCls : 'addIcon',
				tooltip : '<button type="button" value="增加">增加</button>',
				tooltipType : 'qtip',
				handler : function() {
					submitTreeData();
				}
			}],
	root : stgroot,
	loader : new Ext.tree.TreeLoader({
				dataUrl : '../strategy/createtree?time=' + new Date().getTime(),
				processResponse : function(response, node, callback, scope) {
					var json = response.responseText;
					var respObj = Ext.util.JSON.decode(json);
					try {
						submitid = respObj.id;
						var o = [tranNodeConfig('data', respObj.name,
								respObj.def, respObj.data)];
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
						this.runCallback(callback, scope || node, [node]);
					} catch (e) {
						this.handleFailure(response);
					}
				},
				listeners : {
					"beforeload" : function(treeloader, node) {
						treeloader.baseParams = {
							name : GOBJID,
							disname : GDISNAME
						};
					}
				}
			})
});
stgtree.expand(true, true);
// 树形编辑器
var treeEditor = new Ext.tree.TreeEditor(Ext.getCmp('stgtree'), {
			id : 'stgtreeEdit',
			allowBlank : false
		});
/**
 * 右键菜单相关代码
 */
// 给tree添加右键菜单事件
stgtree.on('rightMenu', stgtree.rightMenu, stgtree);
// 定义右键菜单
var rightMenu = new Ext.menu.Menu({
			id : 'rightMenu',
			items : [{
						id : 'addNode',
						text : '添加',
						// 增加菜单点击事件
						menu : [{
									id : 'insertNode',
									text : '添加兄弟节点',
									handler : function(tree) {
										insertNode();
									}

								}, {
									id : 'appendNode',
									text : '添加儿子节点',
									handler : function(tree) {
										appendNodeAction();
									}
								}]
					}, '-', {
						id : 'delNode',
						text : '删除',
						handler : function(tree) {
							deleteNode();
						}
					}
					// , {
					// id : 'modifNode',
					// text : '修改',
					// handler : function() {
					// modifNode()
					// }
					// }
					, {
						id : 'viewNode',
						text : '查看',
						handler : function(tree) {

							veiwNodeAction();
						}
					}]
		});
// 添加点击事件
stgtree.on('click', function(node) {
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
stgtree.on('contextmenu', function(node, event) {
			// 使用preventDefault方法可防止浏览器的默认事件操作发生
			event.preventDefault();
			node.select();
			rightMenu.showAt(event.getXY());// 取得鼠标点击坐标，展示菜单
		});
// ////////////////////////////右键菜单代码结束
/**
 * 右键菜单相关的功能函数实现
 */
// 删除节点事件实现
function deleteNode() {
	// 得到选中的节点
	var selectedNode = stgtree.getSelectionModel().getSelectedNode();
	selectedNode.remove();
};
// 修改节点事件实现
function modifNode() {
	var selectedNode = stgtree.getSelectionModel().getSelectedNode();// 得到选中的节点
	treeEditor.editNode = selectedNode;
	treeEditor.startEdit(selectedNode.ui.textNode);
};
// 添加兄弟节点事件实现
function insertNode() {
	var selectedNode = stgtree.getSelectionModel().getSelectedNode();
	var selectedParentNode = selectedNode.parentNode;
	var newNode = new Ext.tree.TreeNode({
				text : '新建节点' + selectedNode.id
			});
	if (selectedParentNode == null) {
		selectedNode.appendChild(newNode);
	} else {
		selectedParentNode.insertBefore(newNode, selectedNode);
	}
	setTimeout(function() {
				treeEditor.editNode = newNode;
				treeEditor.startEdit(newNode.ui.textNode);
			}, 10);
};
// 添加子节点事件实现
// TODO
function appendNodeAction(node) {
	if (node.isLeaf()) {
		node.leaf = false;
	}
	if (!node.hasExpanded) {
		node.expand(true, true);
		node.hasExpanded = true;
	}
	var newNode = node.appendChild(node.attributes.getChildConfig());
	node.attributes.children.push(newNode);
	// var newNode = node.attributes.getChildConfig;
	// newNode.parentNode.expand(true, true, function() {
	// // stgtree.getSelectionModel().select(newNode);
	// setTimeout(function() {
	// treeEditor.editNode = newNode;
	// treeEditor.startEdit(newNode.ui.textNode);
	// }, 10);
	// });// 将上级树形展开
}
/**
 * 提交树的数据对象
 */
function submitTreeData() {
	// 获取ROOT数组
	var treearray = stgtree.root.childNodes;
	// 循环ROOT数组
	for (var i = 0; i < treearray.length; i++) {
		var arrayobj = treearray[i].attributes;
		var submitStr = treejson2str(arrayobj);
		Ext.Ajax.request({
					url : '../strategy/cfgsave',
					params : {
						'name' : GOBJID,
						'config' : submitStr
					},
					scope : stgtree,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						if (result.success) {
							stgtree.getRootNode().reload();
							Ext.Msg.alert("提示", "保存成功");
						} else {
							Ext.Msg.alert("提示", "保存失败");
						}
					},
					failure : function() {
						Ext.Msg.alert("提示", "保存失败");
					}
				});
	}
}