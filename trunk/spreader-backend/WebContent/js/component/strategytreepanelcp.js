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
			}, '-', {
				text : '修改',
				iconCls : 'edit1Icon',
				handler : function() {
					editInit();
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
function appendNodeAction() {
	var selectedNode = stgtree.getSelectionModel().getSelectedNode();
	if (selectedNode.isLeaf()) {
		selectedNode.leaf = false;
	}
	var newNode = selectedNode.appendChild(new Ext.tree.TreeNode({
				text : '新建节点' + selectedNode.id
			}));
	newNode.parentNode.expand(true, true, function() {
				stgtree.getSelectionModel().select(newNode);
				setTimeout(function() {
							treeEditor.editNode = newNode;
							treeEditor.startEdit(newNode.ui.textNode);
						}, 10);
			});// 将上级树形展开
}
/**
 * 绑定PropertyGrid数据源的相关函数
 * 
 * @param {}
 *            node 当前的节点对象
 */
function renderPropertyGrid(node) {
	// 获取数据
	var data = node.attributes.data;
	// 获取数据对应的表结构
	var def = node.attributes.def;
	// 获取布局组件
	var pptgridcmp = Ext.getCmp('pptgridmanage');
	// 创建一个PropertyGrid
	var pptgrid = new Ext.grid.PropertyGrid({
				id : 'pptGrid',
				title : '相关属性',
				autoHeight : true,
				width : 300
			})
	// 得到构造好的propertyNames对象
	var pptnameobj = createPptGridStoreDef(def);
	// 设置propertyNames
	pptgrid.propertyNames = pptnameobj;
	// 构造数据源对象
	var newdata = createPptGridStoreData(data, def);
	// 构造编辑属性对象
	var custEdit = createPptGridCustEdit(data, def);
	// 设置数据源
	pptgrid.source = newdata;
	pptgrid.customEditors = custEdit;
	// 删除添加的组件，防止重复出现
	pptgridcmp.removeAll(pptgrid);
	// 绑定到布局页面
	pptgridcmp.add(pptgrid);
	pptgridcmp.doLayout();
}
/**
 * 如果是collection节点则需创建子节点的展示菜单 TODO
 * 
 * @param {}
 *            node
 */
function collectionRender(node) {
	// 获取数据
	var data = node.attributes.data;
	// 获取数据对应的表结构
	var def = node.attributes.def;
	// 获取布局组件
	var pptgridcmp = Ext.getCmp('pptgridmanage');
	// 创建一个PropertyGrid
	var pptgrid = new Ext.Panel({
				id : 'collPanle',
				layout : 'form',
				title : '相关操作',
				autoHeight : true,
				width : 300,
				tbar : [{
							text : '请增加子节点',
							iconCls : 'addIcon',
							handler : function() {
								submitTreeData();
							}
						}, '-']
			});
	// // 得到构造好的propertyNames对象
	// var pptnameobj = createPptGridStoreDef(def);
	// // 设置propertyNames
	// pptgrid.propertyNames = pptnameobj;
	// // 构造数据源对象
	// var newdata = createPptGridStoreData(data, def);
	// // 构造编辑属性对象
	// var custEdit = createPptGridCustEdit(data, def);
	// // 设置数据源
	// pptgrid.source = newdata;
	// pptgrid.customEditors = custEdit;
	// 删除添加的组件，防止重复出现
	pptgridcmp.removeAll(pptgrid);
	// 绑定到布局页面
	pptgridcmp.add(pptgrid);
	pptgridcmp.doLayout();
}
/**
 * 构造propertyNames对象
 * 
 * @param {}
 *            def 结构
 * @return {}
 */
function createPptGridStoreDef(def) {
	// 创建propertyNames的对象
	var pptNameObj = {};
	for (var i = 0; i < def.length; i++) {
		var relname = def[i].propertyName;
		pptNameObj[relname] = def[i].name;
	}
	return pptNameObj;
}
/**
 * 重新构造一遍data，因为有可能data是null，导致不能与propertyNames匹配不能显示
 * 
 * @param {}
 *            data
 */
function createPptGridStoreData(data, def) {
	var tdata = {};
	if (data != null && def != null) {
		for (var i = 0; i < def.length; i++) {
			var defObj = def[i];
			var defname = defObj.propertyName;
			if (!data.hasOwnProperty(defname)) {
				tdata[defname] = '';
			}
		}
		return tdata;
	} else {
		Ext.MessageBox.alert("提示", "对象获取错误");
		return;
	}
}
function findDataAndDef() {
	var obj = new Object();
	Ext.Ajax.request({
				url : '../strategy/createtree',
				success : function(res) {
					var sjson = Ext.util.JSON.decode(res.responseText);
					obj = transformdata(sjson.id, sjson.name, sjson.def,
							sjson.data);
				},
				failure : function(res) {
					var sjson2 = Ext.util.JSON.decode(res.responseText);
					return;
				}
			});
	return obj;
}
function createPptGridCustEdit(data, def) {
	var custEdit = {};
	if (data != null && def != null) {
		for (var i = 0; i < def.length; i++) {
			var defObj = def[i];
			var defname = defObj.propertyName;
			var pType = defObj.propertyDefinition.type;
			if (!data.hasOwnProperty(defname)) {
				custEdit[defname] = getTypeDefaultEdit(pType);
			}
		}
		return custEdit;
	} else {
		Ext.MessageBox.alert("提示", "对象获取错误");
		return;
	}
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