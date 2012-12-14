// 树对象的基本操作函数，区分调度和策略树
/**
 * 策略树操作
 */
// 删除节点事件实现
function strategyDeleteNode(stgtree) {
	// 得到选中的节点
	var selectedNode = stgtree.getSelectionModel().getSelectedNode();
	var selectid = selectedNode.id;
	var parent = selectedNode.parentNode;
	var parentArray = parent.attributes.children;
	var idx = findArrayIndex(parentArray, selectid);
	parent.removeChild(selectedNode);
	parentArray.splice(idx, 1);
};
// 修改节点事件实现
function strategyModifNode() {
	var selectedNode = stgtree.getSelectionModel().getSelectedNode();// 得到选中的节点
	treeEditor.editNode = selectedNode;
	treeEditor.startEdit(selectedNode.ui.textNode);
};
// 添加兄弟节点事件实现
function strategyInsertNode() {
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

/**
 * 调度树操作
 */
// 删除节点事件实现
function dispatchDeleteNode(stgdisptree) {
	var selectedNode = stgdisptree.getSelectionModel().getSelectedNode();
	var selectid = selectedNode.id;
	var parent = selectedNode.parentNode;
	var parentArray = parent.attributes.children;
	var idx = findArrayIndex(parentArray, selectid);
	parent.removeChild(selectedNode);
	parentArray.splice(idx, 1);
};
// 修改节点事件实现
function dispatchModifNode() {
	var selectedNode = stgdisptree.getSelectionModel().getSelectedNode();// 得到选中的节点
	treeEditor.editNode = selectedNode;
	treeEditor.startEdit(selectedNode.ui.textNode);
};
// 添加兄弟节点事件实现
function dispatchInsertNode() {
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
 * 策略提交树的数据对象
 */
function strategySubmitTreeData(stgtree, windows) {
	// 获取ROOT数组
	var treearray = stgtree.root.childNodes;
	// 循环ROOT数组
	for (var i = 0; i < treearray.length; i++) {
		var arrayobj = treearray[i].attributes;
		var submitStr = treejson2str(arrayobj);
		Ext.Ajax.request({
					url : '../strategy/cfgsave?_time=' + new Date().getTime(),
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
							windows.hide();
						} else {
							Ext.Msg.alert("提示", "保存失败");
							windows.hide();
						}
					},
					failure : function() {
						Ext.Msg.alert("提示", "保存失败");
					}
				});
	}
}
/**
 * 用户分组提交树的数据对象
 */
function userGroupSubmitTreeData(userGroupPropExpTree, store, gid) {
	// 获取ROOT数组
	var treearray = userGroupPropExpTree.root.childNodes;
	// 循环ROOT数组
	for (var i = 0; i < treearray.length; i++) {
		var arrayobj = treearray[i].attributes;
		var submitStr = treejson2str(arrayobj);
		Ext.Ajax.request({
					url : '../usergroup/updategroup',
					params : {
						'gid' : gid,
						'propexp' : submitStr
					},
					scope : userGroupPropExpTree,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						if (result.success) {
							userGroupPropExpTree.getRootNode().reload();
							Ext.Msg.alert("提示", "保存成功");
							store.reload();
						} else {
							Ext.Msg.alert("提示", "保存失败");
							store.reload();
						}
					},
					failure : function() {
						Ext.Msg.alert("提示", "保存失败");
						store.reload();
					}
				});
	}
}
/**
 * 调度提交数据的函数
 */
function strategyDispatchSubmitTreeData(stgdisptree, triggerDispForm,
		radioForm, simpleDispForm, editstgWindow, store, strategyId, objId) {
	// 获取ROOT数组
	var treearray = stgdisptree.root.childNodes;
	var tparam = {};
	tparam['name'] = strategyId;
	tparam['_time'] = new Date().getTime();
	tparam['id'] = objId;
	tparam['groupType'] = 1;
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
		tparam['repeatInternal'] = repeatInternal;
	} else {
		var cron = ttriggerDispForm.findField("cron").getValue();
		tparam['cron'] = cron;
	}
	Ext.Ajax.request({
				url : '../strategydisp/dispsave',
				params : tparam,
				scope : stgdisptree,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						// stgdisptree.getRootNode().reload();
						Ext.Msg.alert("提示", "保存成功");
						editstgWindow.hide();
						if (!Ext.isEmpty(store)) {
							store.reload();
						}
					} else {
						Ext.Msg.alert("提示", "保存失败");
					}
				},
				failure : function() {
					Ext.Msg.alert("提示", "保存失败");
				}
			});
}

/**
 * 策略分组树提交数据的函数
 */
function strategyGroupSubmitTreeData(stgdisptree, triggerDispForm, radioForm,
		simpleDispForm, editstgWindow, store, groupStore, fromId, toId,
		strategyId, groupName, groupNote, groupType, objId, groupId, refid) {
	// 获取ROOT数组
	var treearray = stgdisptree.root.childNodes;
	var tparam = {};
	// 简单分组名，默认就用调用名
	tparam['name'] = strategyId;
	// 复杂分组用自定义的组名
	tparam['groupName'] = groupName;
	// 复杂分组的说明
	tparam['groupNote'] = groupNote;
	// 分组类型
	tparam['groupType'] = groupType;
	tparam['_time'] = new Date().getTime();
	tparam['id'] = objId;
	tparam['groupId'] = groupId;
	// 分组ID
	tparam['fromGroupId'] = fromId;
	tparam['toGroupId'] = toId;
	tparam['refId'] = refid;
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
	var triggerType = tradioForm.findField("triggerType").getGroupValue();
	var description = tradioForm.findField("description").getValue();
	tparam['description'] = description;
	tparam['triggerType'] = triggerType;
	if (triggerType == 1) {
		var start = tsimpleDispForm.findField("start").getValue();
		var repeatTimes = tsimpleDispForm.findField("repeatTimes").getValue();
		var repeatInternal = tsimpleDispForm.findField("repeatInternal")
				.getValue();
		tparam['start'] = start;
		tparam['repeatTimes'] = repeatTimes;
		tparam['repeatInternal'] = repeatInternal;
	} else {
		var cron = ttriggerDispForm.findField("cron").getValue();
		tparam['cron'] = cron;
	}
	Ext.Ajax.request({
				url : '../strategydisp/dispsave',
				params : tparam,
				scope : stgdisptree,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result.success) {
						Ext.Msg.alert("提示", "保存成功");
						editstgWindow.hide();
						if (!Ext.isEmpty(store)) {
							store.reload();
						}
						if (!Ext.isEmpty(groupStore)) {
							groupStore.reload();
						}
					} else {
						Ext.Msg.alert("提示", result.message);
						editstgWindow.hide();
						if (!Ext.isEmpty(store)) {
							store.reload();
						}
						if (!Ext.isEmpty(groupStore)) {
							groupStore.reload();
						}
					}
				},
				failure : function() {
					Ext.Msg.alert("提示", result.message);
				}
			});
}
/**
 * 获取选中元素的下标
 * 
 * @param {}
 *            array
 * @param {}
 *            item
 */
function findArrayIndex(array, item) {
	var tindex = -1;
	if (!Ext.isEmpty(array) && array.length > 0) {
		for (var i in array) {
			if (array[i].id == item) {
				tindex = i;
			}
		}
	}
	return tindex;
}