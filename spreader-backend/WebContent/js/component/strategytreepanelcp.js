// 模拟本地JSON数据，用于构造树结构
var treedata = new Object();

// [{
// id : '1',
// text : '用户A',
// def : [{
// "propertyDefinition" : {
// "type" : "String"
// },
// "name" : "姓名",
// "propertyName" : "name"
// }, {
// "propertyDefinition" : {
// "type" : "String"
// },
// "name" : "年龄",
// "propertyName" : "age"
// }],
// data : {
// name : "xf",
// age : 11
// },
// children : [{
// id : '11',
// text : '图书',
// children : [{
// id : '111',
// text : 'ExtJS设计',
// qtip : '书的内容十分详细',
// data : {
// title : "ExtJS设计",
// page : 112
// },
// leaf : true
// }, {
// id : '112',
// text : 'JAVA设计',
// data : {
// title : "JAVA设计",
// page : 115
// },
// leaf : true
// }]
// }, {
// id : '12',
// text : '手机',
// children : [{
// id : '121',
// text : '诺基亚',
// data : {
// weight : 22,
// type : 1
// },
// leaf : true
// }, {
// id : '122',
// text : '摩托罗拉',
// data : {
// weight : 25,
// type : 2
// },
// leaf : true
// }]
// }]
// }];
// store
var treestore = new Ext.data.Tree()
// 构造树的根节点ROOT
var stgroot = new Ext.tree.AsyncTreeNode({
	id : '-1',
	text : '配置列表'
		// ,
		// children : [treedata]
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
			root : stgroot,
			loader : new Ext.tree.TreeLoader({
						dataUrl : '../strategy/createtree',
						processResponse : function(response, node, callback,
								scope) {
							var json = response.responseText;
							var respObj = Ext.util.JSON.decode(json);
							try {
								var o = [tranNodeConfig(respObj.id,
										respObj.name, respObj.def, respObj.data)];
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
			renderPropertyGrid(node);
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
	// 获取PropertyGrid组件
	var pptgrid = Ext.getCmp('pptGrid');
	// 得到构造好的propertyNames对象
	var pptnameobj = createPptGridStore(data, def);
	// 设置propertyNames
	pptgrid.propertyNames = pptnameobj;
	// 设置数据源
	pptgrid.setSource(data);
	// 绑定到布局页面
	pptgridcmp.add(pptgrid);
	pptgridcmp.doLayout();
}

/**
 * 构造propertyNames对象
 * 
 * @param {}
 *            data 数据
 * @param {}
 *            def 结构
 * @return {}
 */
function createPptGridStore(data, def) {
	// 创建propertyNames的字符串需要JSON格式
	var pptname = null;
	findDataAndDef();
	pptname = "{";
	// //循环def获取属性对应的名称和TYPE
	for (var i = 0; i < def.length; i++) {
		var relname = def[i].propertyName;
		pptname += relname + ":";
		var showname = def[i].name;
		pptname += "'" + showname + "'";
		pptname += ",";
	}
	pptname = pptname.substring(0, pptname.length - 1);
	pptname += "}";
	// 创建JSON对象
	var pptobj = new Object();
	// 将String转换成JSON
	pptobj = Ext.util.JSON.decode(pptname);
	return pptobj;
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
