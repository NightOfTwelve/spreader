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
// ////////////////////////////右键菜单代码结束
/**
 * 右键菜单相关的功能函数实现
 */
// 删除节点事件实现
function deleteNode() {
	// 得到选中的节点
	var selectedNode = stgdisptree.getSelectionModel().getSelectedNode();
	var parent = selectedNode.parentNode;
	parent.removeChild(selectedNode);
	parent.attributes.children.pop(selectedNode);
	// selectedNode.remove();
};
// 修改节点事件实现
function modifNode() {
	var selectedNode = stgdisptree.getSelectionModel().getSelectedNode();// 得到选中的节点
	treeEditor.editNode = selectedNode;
	treeEditor.startEdit(selectedNode.ui.textNode);
};
// 添加兄弟节点事件实现
function insertNode() {
	var selectedNode = stgdisptree.getSelectionModel().getSelectedNode();
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
	// var selectedNode = stgtree.getSelectionModel().getSelectedNode();
	if (node.isLeaf()) {
		node.leaf = false;
	}
	if (!node.hasExpanded) {
		node.expand(true, true);
		node.hasExpanded = true;
	}
	var newNode = node.appendChild(node.attributes.getChildConfig());
	node.attributes.children.push(newNode);
	// newNode.parentNode.reload();
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
 * 提交数据的函数
 */
function submitTreeData() {
	// 获取ROOT数组
	var treearray = stgdisptree.root.childNodes;
	var tparam = {};
	tparam['name'] = GOBJID;
	tparam['_time'] = new Date().getTime();
	tparam['id'] = GDISPID;
	if (treearray.length > 0) {
		var arrayobj = treearray[0].attributes;
		var submitStr = treejson2str(arrayobj);
		tparam['config'] = submitStr;
	}
	// 获取调度FORM
	var tradioForm = radioForm.getForm();
	var ttriggerDispForm = triggerDispForm.getForm();
	var tsimpleDispForm = simpleDispForm.getForm();
	// 获取调度参数
	var triggerType = tradioForm.findField("triggerType").inputValue;
	var description = tradioForm.findField("description").getValue();
	tparam['description'] = description;
	tparam['triggerType'] = triggerType;
	if (triggerType == 1) {
		var start = renderDateHis(tsimpleDispForm.findField("start").getValue());
		// var fstart = start.dateFormat('Y/m/d H:i:s');
		// alert(fstart);
		var repeatTimes = tsimpleDispForm.findField("repeatTimes").getValue();
		var repeatInternal = tsimpleDispForm.findField("repeatInternal")
				.getValue();
		tparam['start'] = start;
		tparam['repeatTimes'] = repeatTimes;
		if (repeatTimes < new Date().getTime()) {
			Ext.MessageBox.alert("提示", "任务开始时间不能早于当前时间");
			return;
		}
		tparam['repeatInternal'] = repeatInternal;
	} else {
		var cron = ttriggerDispForm.findField("cron").getValue();
		tparam['cron'] = cron;
	}
	Ext.Ajax.request({
				url : '../strategy/dispsave',
				params : tparam,
				scope : stgdisptree,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						// stgdisptree.getRootNode().reload();
						Ext.Msg.alert("提示", "保存成功");
						editstgWindow.hide();
						store.reload();
					} else {
						Ext.Msg.alert("提示", "保存失败");
					}
				},
				failure : function() {
					Ext.Msg.alert("提示", "保存失败");
				}
			});
}
