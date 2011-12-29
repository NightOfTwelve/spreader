/**
 * 策略调度列表的布局JS 统一使用Ext.Viewport分为north,CENTER两块 上侧为查询条件 下方为具体的列表显示
 */
Ext.onReady(function() {
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
		root : stgroot,
		loader : new Ext.tree.TreeLoader({
					dataUrl : '../strategy/system/createdisptree?time='
							+ new Date().getTime(),
					processResponse : function(response, node, callback, scope) {
						var json = response.responseText;
						var respObj = Ext.util.JSON.decode(json);
						try {
							submitid = respObj.id;
							var o = [tranNodeConfig('data', respObj.treename,
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
								disname : GDISNAME,
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
				items : [{
							id : 'delNode',
							text : '删除',
							handler : function(tree) {
								deleteNode();
							}
						}, {
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
			var start = renderDateHis(tsimpleDispForm.findField("start")
					.getValue());
			// var fstart = start.dateFormat('Y/m/d H:i:s');
			// alert(fstart);
			var repeatTimes = tsimpleDispForm.findField("repeatTimes")
					.getValue();
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
					url : '../strategy/system/dispsave',
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
	/**
	 * 调度类型的COMB的数据源
	 */
	var tgTypeStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['-1', '----------------------'], ['1', '简单调度'],
						['2', '复杂调度']]
			});
	/**
	 * 策略列表的查询FORM
	 */
	var stgdispgridform = new Ext.form.FormPanel({
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
											fieldLabel : '调度类型',
											// name:'TIMEFER',
											xtype : 'combo',
											width : 100,
											store : tgTypeStore,
											id : 'triggerType',
											hiddenName : 'triggerType',
											valueField : 'ID',
											editable : false,
											displayField : 'NAME',
											mode : 'local',
											forceSelection : false,// 必须选择一项
											emptyText : '调度类型...',// 默认值
											triggerAction : 'all'
										}]
							}, {
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "调度名称",
											name : 'dispname',
											width : 100
										}]
							}]
				}],
				buttonAlign : "center",
				buttons : [{
							text : "查询"
						}, {
							text : "重置",
							handler : function() { // 按钮响应函数
								stgdispgridform.form.reset();
							}
						}]
			});
	/**
	 * 策略配置列表页面 实现所有策略的简单列表，点击某一行记录跳转到详细配置页面
	 */
	// 定义表格数据源
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../strategy/system/stgdispgridstore'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'cnt',
							root : 'data'
						}, [{
									name : 'id'
								}, {
									name : 'name'
								}, {
									name : 'triggerType'
								}, {
									name : 'triggerInfo'
								}, {
									name : 'description'
								}]),
				autoLoad : {
					params : {
						start : 0,
						limit : 25
					}
				}

			});
	// 定义Checkbox
	var sm = new Ext.grid.CheckboxSelectionModel();
	// 定义表格列CM
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
				header : '调度编号',
				dataIndex : 'id',
				width : 80
			}, {
				header : '调度名称',
				dataIndex : 'name',
				renderer : rendDispName,
				width : 100
			}, {
				header : '调度类型',
				dataIndex : 'triggerType',
				width : 100,
				renderer : rendTrigger
			}, {
				header : '调度备注',
				dataIndex : 'triggerInfo',
				renderer : renderBrief,
				width : 100
			}, {
				header : '描述',
				dataIndex : 'description',
				renderer : renderBrief,
				width : 100
			}, {
				header : '相关操作',
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='配置'/>";
					return returnStr;
				},
				width : 100
			}]);
	// 页数
	var number = 20;
	var numtext = new Ext.form.TextField({
				id : 'maxpage',
				name : 'maxpage',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							bbar.pageSize = parseInt(numtext.getValue());
							number = parseInt(numtext.getValue());
							store.reload({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
						}
					}
				}
			});

	// 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', numtext]
			});

	// 定义grid表格
	var stgdisplistgrid = new Ext.grid.GridPanel({
				height : 500,
				autoWidth : true,
				autoScroll : true,
				split : true,
				region : 'center',
				store : store,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				// stripeRows : true,
				frame : true,
				// autoExpandColumn : 'remark',
				sm : sm,
				cm : cm,
				tbar : [{
							text : '新增',
							iconCls : 'page_addIcon',
							handler : function() {
								stgCmbWindow.show();
							}
						}, '-', {
							text : '删除',
							iconCls : 'page_delIcon',
							handler : function() {
								// TODO
								deleteData();
							}
						}, {
							text : '查询',
							iconCls : 'previewIcon',
							handler : function() {
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
						GDISNAME = data.name;
						GOBJID = data.name;
						GDISPID = data.id;
						var trgid = data.id;
						settingCreateTrigger(trgid);
						editstgWindow.title = rendDispName(GDISNAME);
						editstgWindow.show();
					}
				}

			});
	// 注册事件
	stgdisplistgrid.on('cellclick', stgdisplistgrid.onCellClick,
			stgdisplistgrid);

	/**
	 * 创建新增事件ComboBox
	 */
	// 创建ComboBox数据源，支持Ajax取值
	var stgCmbStore = new Ext.data.Store({
				// 代理模式
				proxy : new Ext.data.HttpProxy({
							url : '../strategy/system/combstore'
						}),
				// 读取模式
				reader : new Ext.data.JsonReader({}, [{
									name : 'name'
								}, {
									name : 'displayName'
								}])
			});
	// 选择策略的COMB
	var stgSelectCombo = new Ext.form.ComboBox({
				hiddenName : 'name',
				id : 'stgSelectCombo',
				fieldLabel : '策略',
				emptyText : '请选择策略...',
				triggerAction : 'all',
				store : stgCmbStore,
				displayField : 'displayName',
				valueField : 'name',
				loadingText : '正在加载数据...',
				mode : 'remote', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
				forceSelection : true,
				typeAhead : true,
				resizable : true,
				editable : false,
				anchor : '100%'
			});
	// 选择事件，用于联动 TODO
	// stgSelectCombo.on('select', function() {
	// cityCombo.reset();
	// countyCombo.reset();
	// var value = provinceCombo.getValue();
	// cityStore.load({
	// params : {
	// areacode : value
	// }
	// });
	// });
	// 嵌入的FORM
	var stgCmbForm = new Ext.form.FormPanel({
				id : 'stgCmbForm',
				name : 'stgCmbForm',
				labelWidth : 50, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				defaultType : 'textfield', // 表单元素默认类型
				labelAlign : 'right', // 标签对齐方式
				bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
				items : [stgSelectCombo]
			});
	/**
	 * 策略调度配置模块
	 */
	// 创建简单调度的FORM
	var simpleDispForm = new Ext.form.FormPanel({
				// autoWidth : true,
				id : 'simpcard',
				height : 100,
				frame : true,
				layout : "form", // 整个大的表单是form布局
				labelWidth : 100,
				labelAlign : "left",
				items : [{ // 行1
					layout : "column", // 从左往右的布局
					items : [{
								columnWidth : .3, // 该列有整行中所占百分比
								layout : "form", // 从上往下的布局
								items : [{
											xtype : "datetimefield",
											fieldLabel : "开始时间",
											name : 'start',
											width : 150
										}]
							}, {
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "numberfield",
											fieldLabel : "重复次数",
											name : 'repeatTimes',
											width : 100
										}]
							}, {
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "numberfield",
											fieldLabel : "毫秒数",
											name : 'repeatInternal',
											width : 100
										}]
							}]
				}],
				buttonAlign : "center",
				buttons : [{
							text : '保存',
							handler : function() {
								submitTreeData();
							}
						}, {
							text : "重置",
							handler : function() { // 按钮响应函数
								simpleDispForm.form.reset();
							}
						}]
			});
	// 表达式配置FORM
	var triggerDispForm = new Ext.form.FormPanel({
				autoWidth : true,
				height : 100,
				id : 'trgcard',
				frame : true,
				layout : "form", // 整个大的表单是form布局
				labelWidth : 100,
				labelAlign : "left",
				items : [{ // 行1
					layout : "column", // 从左往右的布局
					items : [{
								columnWidth : .3, // 该列有整行中所占百分比
								layout : "form", // 从上往下的布局
								items : [{
											xtype : "textfield",
											fieldLabel : "表达式",
											name : 'cron',
											width : 100
										}]
							}]
				}],
				buttonAlign : "center",
				buttons : [{
							text : '保存',
							handler : function() {
								submitTreeData();
							}
						}, {
							text : "重置",
							handler : function() { // 按钮响应函数
								triggerDispForm.form.reset();
							}
						}]
			});
	// 首先创建一个card布局的Panel
	var cardPanel = new Ext.Panel({
				autoWidth : true,
				id : 'cardPanel',
				height : 100,
				layout : 'card',
				activeItem : 0,
				// bodyStyle : 'padding:15px',
				defaults : {
					border : false
				},
				items : [simpleDispForm, triggerDispForm]
			});
	// 用于提示的Toolbar
	var radioTbar = new Ext.Toolbar();
	radioTbar.add({
				id : 'jobremind',
				name : 'jobremind',
				xtype : 'tbtext'
			})
	// RADIO组件
	var radioForm = new Ext.form.FormPanel({
				// width : 200,
				frame : true,
				height : 80,
				labelWidth : 65,
				labelAlign : "left",
				items : [{
							xtype : 'radiogroup',
							fieldLabel : '配置方式',
							items : [{
										xtype : 'radio',
										boxLabel : '简单调度',
										inputValue : '1',
										width : 50,
										name : 'triggerType',
										checked : true
									}, {
										xtype : 'radio',
										boxLabel : '配置表达式',
										width : 50,
										inputValue : '2',
										name : 'triggerType'
									}],
							listeners : {
								'change' : function(group, ck) {
									var cardPanelCmp = Ext.getCmp('cardPanel').layout;
									var activeid = cardPanelCmp.activeItem.id;
									if (ck.inputValue == '1') {
										cardPanelCmp.setActiveItem(0);
									} else {
										cardPanelCmp.setActiveItem(1);
									}
								}
							}
						}, {
							xtype : "textfield",
							fieldLabel : "备注信息",
							name : 'description',
							width : 100
						}],
				tbar : radioTbar
			});
	// 弹出窗口
	var stgCmbWindow = new Ext.Window({
				title : '<span class="commoncss">策略选择</span>', // 窗口标题
				id : 'stgCmbWindow',
				closeAction : 'hide',
				layout : 'fit', // 设置窗口布局模式
				width : 300, // 窗口宽度
				height : 150, // 窗口高度
				// closable : true, // 是否可关闭
				collapsible : true, // 是否可收缩
				maximizable : true, // 设置是否可以最大化
				border : false, // 边框线设置
				constrain : true, // 设置窗口是否可以溢出父容器
				pageY : 20, // 页面定位Y坐标
				pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
				items : [stgCmbForm], // 嵌入的表单面板
				buttons : [{ // 窗口底部按钮配置
					text : '确定', // 按钮文本
					iconCls : 'tbar_synchronizeIcon', // 按钮图标
					handler : function() { // 按钮响应函数
						GDISPID = null;
						cleanCreateTrigger();
						GOBJID = stgSelectCombo.getValue();
						GDISNAME = stgSelectCombo.lastSelectionText;
						editstgWindow.title = GDISNAME;
						editstgWindow.show();
						stgCmbWindow.hide();
					}
				}, {	// 窗口底部按钮配置
							text : '重置', // 按钮文本
							iconCls : 'tbar_synchronizeIcon', // 按钮图标
							handler : function() { // 按钮响应函数
								stgCmbForm.form.reset();
							}
						}, {
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								stgCmbWindow.hide();
							}
						}]
			});
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
							collapsible : true,
							split : true
						}, {
							region : 'west',
							split : true,
							width : 200,
							minWidth : 175,
							maxWidth : 400,
							items : [stgdisptree]
						}, {
							region : 'south',
							title : '调度配置',
							split : true,
							// height : 300,
							autoHeight : true,
							items : [radioForm, cardPanel]
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
				stgdisptree.getRootNode().reload();
				stgdisptree.root.select();
				var pptGrid = Ext.getCmp("pptGrid");
				var pptMgr = Ext.getCmp("pptgridmanage");
				if (pptGrid != null) {
					pptMgr.remove(pptGrid);
				}
				pptMgr.doLayout();
			});

	/**
	 * 设置调度配置的相关参数
	 * 
	 * @param {}
	 *            trgid
	 */
	function settingCreateTrigger(trgid) {
		Ext.Ajax.request({
					url : '../strategy/system/settgrparam',
					params : {
						'id' : trgid
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var description = result.description;
						var triggerType = result.triggerType;
						var cron = result.cron;
						var start = result.start;
						var sdate = new Date(start);
						var repeatTimes = result.repeatTimes;
						var repeatInternal = result.repeatInternal;
						var remind = result.remind;
						// 获取FORM
						var tradioForm = radioForm.getForm();
						var ttriggerDispForm = triggerDispForm.getForm();
						var tsimpleDispForm = simpleDispForm.getForm();
						// 设置参数
						tradioForm.findField("triggerType")
								.setValue(triggerType);
						tradioForm.findField("description")
								.setValue(description);
						tsimpleDispForm.findField("start").setValue(sdate);
						tsimpleDispForm.findField("repeatTimes")
								.setValue(repeatTimes);
						tsimpleDispForm.findField("repeatInternal")
								.setValue(repeatInternal);
						ttriggerDispForm.findField("cron").setValue(cron);
						// TODO
						var remindcmp = Ext.getCmp("jobremind");
						var tstr = '任务:' + rendDispName(GDISNAME) + ',编号:'
								+ GDISPID + ',目前运行信息:' + remind;
						remindcmp.setText('<font color = "red">' + tstr
								+ '</font>');
					},
					failure : function() {
						// Ext.Msg.alert("提示", "数据获取异常");
					}
				});
	}
	/**
	 * 初始化新增对象的参数
	 * 
	 * @param {}
	 *            trgid
	 */
	function cleanCreateTrigger() {
		// 获取FORM
		var tradioForm = radioForm.getForm();
		var ttriggerDispForm = triggerDispForm.getForm();
		var tsimpleDispForm = simpleDispForm.getForm();
		// 设置参数
		// tradioForm.findField("triggerType").setValue(1);
		tradioForm.findField("description").setValue(null);
		tsimpleDispForm.findField("start").setValue(null);
		tsimpleDispForm.findField("repeatTimes").setValue(null);
		tsimpleDispForm.findField("repeatInternal").setValue(null);
		ttriggerDispForm.findField("cron").setValue(null);
		var remindcmp = Ext.getCmp("jobremind");
		remindcmp.setText(null);
	}
	/**
	 * 删除调度信息
	 * 
	 * @param {}
	 *            id
	 */
	function deleteData() {
		// 获取选中行
		var rows = stgdisplistgrid.getSelectionModel().getSelections();
		// 可能是批量删除，需循环所有的选中行
		if (rows.length > 0) {
			var tmpstr = '';
			for (var i = 0; i < rows.length; i++) {
				var trgid = rows[i].data.id;
				tmpstr += trgid + ',';
			}
			var idstr = subStrLastId(tmpstr);
			Ext.Msg.confirm('提示',
					'<span style="color:red"><b>提示:</b></span><br>确定删除？',
					function(btn, text) {
						if (btn == 'yes') {
							Ext.Ajax.request({
										url : '../strategy/system/deletetrg',
										params : {
											'idstr' : idstr
										},
										scope : stgdisplistgrid,
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											Ext.Msg.alert("提示", result.message);
											store.reload();
										},
										failure : function() {
											Ext.Msg.alert("提示", "删除失败");
										}
									});
						}
					});
		} else {
			Ext.Msg.alert("提示", "请选择要删除的调度");
			return;
		}
	}
	/**
	 * 渲染策略名称为中文名
	 * 
	 * @param {}
	 *            value
	 * @return {}
	 */
	function rendDispName(value) {
		var list = store.reader.jsonData.dispname;
		for (var idx in list) {
			var tmp = list[idx].name;
			var dname = list[idx].displayName;
			if (value == tmp) {
				return dname;
			}
		}
	}
	/**
	 * 
	 * @param {}
	 *            value
	 * @return {}
	 */
	function rendTrigger(value) {
		if (value == 1) {
			return '简单调度';
		} else {
			return '复杂调度';
		}
	}

	var stgdispviewport = new Ext.Viewport({
				layout : 'border',
				items : [stgdispgridform, stgdisplistgrid]
			});
});