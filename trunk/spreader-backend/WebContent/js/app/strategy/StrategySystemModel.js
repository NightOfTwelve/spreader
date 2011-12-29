Ext.onReady(function() {
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
					dataUrl : '../system/createtree?time='
							+ new Date().getTime(),
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
							id : 'delNode',
							text : '删除',
							handler : function(tree) {
								deleteNode();
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
		var parent = selectedNode.parentNode;
		parent.removeChild(selectedNode);
		parent.attributes.children.pop(selectedNode);
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
						url : '../system/cfgsave',
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

	var stggridform = new Ext.form.FormPanel({
				region : 'north',
				title : "组合查询",
				autoWidth : true,
				height : 100,
				split : true,
				frame : true,
				layout : "form", // 整个大的表单是form布局
				labelWidth : 65,
				labelAlign : "right",
				items : [{ // 行1
					layout : "column", // 从左往右的布局
					items : [{
								columnWidth : .3, // 该列有整行中所占百分比
								layout : "form", // 从上往下的布局
								items : [{
											xtype : "datefield",
											fieldLabel : "日期",
											width : 100
										}]
							}, {
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "策略名称",
											width : 100
										}]
							}, {
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "其他条件",
											width : 100
										}]
							}]
				}],
				buttonAlign : "center",
				buttons : [{
							text : "查询"
						}, {
							text : "重置"
						}]
			});
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../system/stggridstore'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'TOTALCOUNT',
							root : 'data'
						}, [{
									name : 'name'
								}, {
									name : 'displayName'
								}, {
									name : 'description'
								}]),
				autoLoad : true
			});
	// 定义表格列CM
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
				header : '策略编号',
				dataIndex : 'name',
				width : 100
			}, {
				header : '策略名称',
				dataIndex : 'displayName',
				width : 100
			}, {
				header : '说明',
				dataIndex : 'description',
				width : 100
			}, {
				header : '相关操作',
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='配置'/>";
					return returnStr;
				},
				width : 100
			}]);
	// 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : 10,
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录"
			});
	// 定义grid表格
	var stglistgrid = new Ext.grid.GridPanel({
				region : 'center',
				height : 500,
				autoWidth : true,
				autoScroll : true,
				split : true,
				store : store,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				stripeRows : true,
				frame : true,
				// autoExpandColumn : 'remark',
				cm : cm,
				tbar : [{
							text : '新增',
							iconCls : 'page_addIcon',
							handler : function() {
								addInit();
							}
						}, '-', {
							text : '修改',
							iconCls : 'page_edit_1Icon',
							handler : function() {
								editInit();
							}
						}, '-', {
							text : '删除',
							iconCls : 'page_delIcon',
							handler : function() {
								deleteDeptItems('1', '');
							}
						}, {
							text : '查询',
							iconCls : 'previewIcon',
							handler : function() {
								queryDeptItem();
							}
						}, '-', {
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								store.reload();
							}
						}],
				bbar : bbar,
				onCellClick : function(grid, rowIndex, columnIndex, e) {
					// 找出表格中‘配置’按钮
					if (e.target.defaultValue == '配置') {
						var record = grid.getStore().getAt(rowIndex);
						var data = record.data;
						var disnames = record.data.displayName;
						GDISNAME = data.displayName;
						GOBJID = data.name;
						editstgWindow.title = disnames;
						editstgWindow.show();
					}
				}

			});
	// 注册事件
	stglistgrid.on('cellclick', stglistgrid.onCellClick, stglistgrid);
	// 创建策略维护的窗口组件
	var editstgWindow = new Ext.Window({
				layout : 'border',
				width : document.documentElement.clientWidth - 200,
				height : document.documentElement.clientHeight - 200,
				resizable : true,
				draggable : true,
				closeAction : 'hide',
				title : '<span class="commoncss">策略详细配置</span>',
				iconCls : 'app_rightIcon',
				modal : true,
				collapsible : true,
				maximizable : true,
				animCollapse : true,
				animateTarget : document.head,
				buttonAlign : 'right',
				constrain : true,
				border : false,
				items : [{
							region : 'center',
							id : 'pptgridmanage',
							header : false,
							// TODO
							collapsible : true,
							split : true,
							height : 100
						}, {
							region : 'west',
							// title : '选择配置',
							split : true,
							width : 200,
							minWidth : 175,
							maxWidth : 400,
							items : [stgtree]
						}],
				buttons : [{
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								editstgWindow.hide();
							}
						}]
			});
	// show事件，需先删除组件，再重新创建PPTGRID
	editstgWindow.on('show', function() {
				stgtree.getRootNode().reload();
				stgtree.root.select();
				var pptGrid = Ext.getCmp("pptGrid");
				var pptMgr = Ext.getCmp("pptgridmanage");
				if (pptGrid != null) {
					pptMgr.remove(pptGrid);
				}
				pptMgr.doLayout();
			});
	var stgridviewport = new Ext.Viewport({
				layout : 'border',
				items : [stggridform, stglistgrid]
			});
});