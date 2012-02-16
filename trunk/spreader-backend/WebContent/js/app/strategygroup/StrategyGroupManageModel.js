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
			dataUrl : '../strategy/createdisptree?time=' + new Date().getTime(),
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
						id : GDISPID,
						isGroup : GISGROUP
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
	// 给tree添加右键菜单事件
	stgdisptree.on('rightMenu', stgdisptree.rightMenu, stgdisptree);
	// 定义右键菜单
	var rightMenu = new Ext.menu.Menu({
				id : 'rightMenu',
				items : [{
							id : 'delNode',
							text : '删除',
							handler : function(tree) {
								dispatchDeleteNode();
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
											minValue : new Date(),
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
								strategyGroupSubmitTreeData(stgdisptree,triggerDispForm,radioForm,simpleDispForm,editstgWindow,store,groupStore);
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
								strategyGroupSubmitTreeData();
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
	// 分组类型的数据源
	var groupTypeStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['-1', '--'], ['1', '简单'], ['2', '复杂']]
			});
	// 增加分组选择策略的数据源
	var addGroupTypeStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['1', '简单'], ['2', '复杂']]
			});
	// 策略分组的FORM
	var groupForm = new Ext.form.FormPanel({
				region : 'north',
				title : "组合查询",
				autoWidth : true,
				height : 100,
				split : true,
				frame : true,
				layout : "form",
				labelWidth : 65,
				labelAlign : "right",
				items : [{ // 行1
					layout : "column", // 从左往右的布局
					items : [{
						columnWidth : .3, // 该列有整行中所占百分比
						layout : "form", // 从上往下的布局
						items : [{
							fieldLabel : '分组类型',
							name : 'groupTypeCmp',
							xtype : 'combo',
							width : 100,
							store : groupTypeStore,
							id : 'groupType',
							hiddenName : 'groupType',
							valueField : 'ID',
							editable : false,
							displayField : 'NAME',
							mode : 'local',
							forceSelection : false,// 必须选择一项
							emptyText : '分组类型...',// 默认值
							triggerAction : 'all',
							listeners : {
								select : function() {
									var tform = groupForm.getForm();
									var groupType = tform
											.findField('groupType').getValue();
									groupStore.setBaseParam('groupType',
											groupType);
									var num = groupNumtext.getValue();
									groupStore.setBaseParam('limit', Ext
													.isEmpty(num)
													? groupNumber
													: Number(num));
									groupStore.load();
								}
							}
						}]
					}, {
						columnWidth : .3,
						layout : "form",
						items : [{
									xtype : "textfield",
									fieldLabel : "分组名称",
									name : 'groupName',
									width : 100
								}]
					}]
				}],
				buttonAlign : "center",
				buttons : [{
					text : "查询",
					handler : function() {
						var tform = groupForm.getForm();
						var groupType = tform.findField('groupType').getValue();
						var groupName = tform.findField('groupName').getValue();
						var num = groupNumtext.getValue();
						groupStore.setBaseParam('groupType', groupType);
						groupStore.setBaseParam('groupName', groupName);
						groupStore.setBaseParam('limit', Ext.isEmpty(num)
										? groupNumber
										: Number(num));
						groupStore.load();
					}
				}, {
					text : "重置",
					handler : function() { // 按钮响应函数
						groupForm.form.reset();
					}
				}]
			});
	// 策略分组的数据源
	var groupStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../stggroup/groupgrid'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'groupName'
								}, {
									name : 'transformName'
								}, {
									name : 'groupType'
								}, {
									name : 'createTime'
								}, {
									name : 'description'
								}, {
									name : 'dispname'
								}]),
				autoLoad : {
					params : {
						start : 0,
						limit : 20
					}
				}

			});
	// 策略分组的Checkbox
	var groupsm = new Ext.grid.CheckboxSelectionModel();
	// 策略分组的CM
	var groupcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
			groupsm, {
				header : '分组编号',
				dataIndex : 'id',
				width : 80
			}, {
				header : '分组名称',
				dataIndex : 'transformName',
				// renderer : rendDispName,
				width : 100
			}, {
				header : '分组类型',
				dataIndex : 'groupType',
				width : 100,
				renderer : renderGroupType
			}, {
				header : '分组描述',
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
	// 策略分组表格页数
	var groupNumber = 20;
	// 策略分组表格自定义页数
	var groupNumtext = new Ext.form.TextField({
				id : 'maxpage',
				name : 'maxpage',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							groupBbar.pageSize = parseInt(groupNumtext
									.getValue());
							groupNumber = parseInt(groupNumtext.getValue());
							groupStore.reload({
										params : {
											start : 0,
											limit : groupBbar.pageSize
										}
									});
						}
					}
				}
			});

	// 策略分组分页菜单
	var groupBbar = new Ext.PagingToolbar({
				pageSize : groupNumber,
				store : groupStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', groupNumtext]
			});

	// 策略分组grid
	var groupGrid = new Ext.grid.GridPanel({
				height : 500,
				autoWidth : true,
				autoScroll : true,
				split : true,
				region : 'center',
				store : groupStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				// stripeRows : true,
				frame : true,
				// autoExpandColumn : 'remark',
				sm : groupsm,
				cm : groupcm,
				tbar : [{
							text : '新增',
							iconCls : 'page_addIcon',
							handler : function() {
								groupAddWindow.show();
							}
						}, '-', {
							text : '删除',
							iconCls : 'page_delIcon',
							handler : function() {
								// TODO
								// deleteData();
							}
						}, '-', {
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								groupStore.reload();
							}
						}],
				bbar : groupBbar,
				onCellClick : function(grid, rowIndex, columnIndex, e) {
					// 找出表格中‘配置’按钮
					if (e.target.defaultValue == '配置') {
						var record = grid.getStore().getAt(rowIndex);
						var data = record.data;
						// 获取分组类型分别做判断
						var gType = data.groupType;
						cleanGlobalVar();
						// 简单分组
						if (gType == 1) {
							GDISNAME = data.groupName;
							GOBJID = data.groupName;
							GDISPID = data.id;
							GGROUPID = data.id;
							GISGROUP = true;
							GGROUPTYPE = gType;
							var gid = data.id;
							settingCreateTrigger(gid, GISGROUP);
							editstgWindow.title = data.transformName;
							editstgWindow.show();
						} else {
							// 复杂分组
							GGROUPID = data.id;
							GGROUPNAME = data.groupName;
							var wtitle = '当前分组:' + data.groupName + ',编号:'
									+ data.id;
							// TODO
							compGroupWindow.title = '<font color = "red">'
									+ wtitle + '</font>';
							store.setBaseParam('groupId', data.id);
							store.reload();
							compGroupWindow.show();
						}
					}
				}

			});
	// 注册事件
	groupGrid.on('cellclick', groupGrid.onCellClick, groupGrid);
	// 增加策略分组的ComboBox
	var groupAddCombo = new Ext.form.ComboBox({
				id : 'groupAddCombo',
				fieldLabel : '分组类型',
				emptyText : '请选择...',
				triggerAction : 'all',
				store : addGroupTypeStore,
				hiddenName : 'groupType',
				valueField : 'ID',
				displayField : 'NAME',
				loadingText : '正在加载数据...',
				mode : 'local', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
				forceSelection : true,
				typeAhead : true,
				allowBlank : false,
				resizable : true,
				editable : false,
				anchor : '100%'
			});
	// 选择事件，用于联动
	groupAddCombo.on('select', function() {
				var groupTypeId = groupAddCombo.getValue();
				var tform = addGroupForm.getForm();
				var gnameField = tform.findField("groupName");
				// 非简单策略分组不能选择策略
				if (groupTypeId == 1) {
					stgSelectCombo.enable(true);
					gnameField.disable(true);
				} else {
					stgSelectCombo.disable(true);
					gnameField.enable(true);
				}
				stgSelectCombo.reset();
			});

	// 创建ComboBox数据源，支持Ajax取值
	var stgCmbStore = new Ext.data.Store({
				// 代理模式
				proxy : new Ext.data.HttpProxy({
							url : '../strategy/combstore'
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
	// 选择事件，用于联动
	stgSelectCombo.on('select', function() {
				var gtId = groupAddCombo.getValue();
				if (Ext.isEmpty(gtId)) {
					Ext.MessageBox.alert("提示", "请先选择分组类型");
					stgSelectCombo.reset();
					return;
				}
			});
	// 选择策略的COMB
	var stgSelectCombo2 = new Ext.form.ComboBox({
				hiddenName : 'name',
				id : 'stgSelectCombo2',
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
	// 嵌入的FORM
	var addGroupForm = new Ext.form.FormPanel({
				id : 'addGroupForm',
				name : 'addGroupForm',
				labelWidth : 50, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				defaultType : 'textfield', // 表单元素默认类型
				labelAlign : 'right', // 标签对齐方式
				bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
				items : [groupAddCombo, stgSelectCombo, {
							fieldLabel : '分组名称',
							name : 'groupName',
							xtype : 'textfield',
							anchor : '100%'
						}, {
							fieldLabel : '分组说明',
							name : 'description',
							xtype : 'textarea',
							height : 50, // 设置多行文本框的高度
							emptyText : '默认初始值', // 设置默认初始值
							anchor : '100%'
						}]
			});
	// 嵌入的FORM
	var stgCmbForm = new Ext.form.FormPanel({
				id : 'stgCmbForm',
				name : 'stgCmbForm',
				labelWidth : 50, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				defaultType : 'textfield', // 表单元素默认类型
				labelAlign : 'right', // 标签对齐方式
				bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
				items : [stgSelectCombo2]
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
						GOBJID = stgSelectCombo2.getValue();
						GDISNAME = stgSelectCombo2.lastSelectionText;
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
	stgCmbWindow.on('show', function() {
				// stgCmbForm.form.reset();
			});
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
				// title : "组合查询",
				bodyStyle : 'width: 100%',
				// autoWidth : true,
				autoScroll : true,
				height : 100,
				split : true,
				frame : true,
				layout : "form", // 整个大的表单是form布局
				labelWidth : 65,
				labelAlign : "right",
				items : [{ // 行1
					layout : "column", // 从左往右的布局
					items : [
							// {
							// columnWidth : .5, // 该列有整行中所占百分比
							// layout : "form", // 从上往下的布局
							// items : [{
							// fieldLabel : '调度类型',
							// // name:'TIMEFER',
							// xtype : 'combo',
							// width : 100,
							// store : tgTypeStore,
							// id : 'triggerType1',
							// hiddenName : 'triggerType',
							// valueField : 'ID',
							// editable : false,
							// displayField : 'NAME',
							// mode : 'local',
							// forceSelection : false,// 必须选择一项
							// emptyText : '调度类型...',// 默认值
							// triggerAction : 'all'
							// }]
							// },
							{
						columnWidth : .5,
						layout : "form",
						items : [{
									xtype : "textfield",
									fieldLabel : "调度名称",
									name : 'dispname1',
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
					url : '../strategy/stgdispgridstore'
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
						}, {
							name : 'gid'
						}, {
							name : 'gname'
						}])
			// ,
			// autoLoad : {
			// params : {
			// start : 0,
			// limit : 20
			// }
			// }
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
				// renderer : rendDispName,
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
				header : '分组编号',
				dataIndex : 'gid',
				width : 100
			}, {
				header : '所属分组',
				dataIndex : 'gname',
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
						GGROUPTYPE = 21;
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

	// var dispanels = new Ext.Panel({
	// layout : 'border',
	// items : [stgdispgridform, stgdisplistgrid]
	// });
	// 复杂分组的弹出窗口
	var compGroupWindow = new Ext.Window({
				title : '<span class="commoncss">复杂分组</span>', // 窗口标题
				id : 'compGroupWindow',
				closeAction : 'hide',
				maximized : true,
				layout : 'border', // 设置窗口布局模式
				width : 500, // 窗口宽度
				height : 600, // 窗口高度
				// closable : true, // 是否可关闭
				collapsible : true, // 是否可收缩
				maximizable : true, // 设置是否可以最大化
				border : false, // 边框线设置
				constrain : true, // 设置窗口是否可以溢出父容器
				pageY : 20, // 页面定位Y坐标
				pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
				items : [stgdispgridform, stgdisplistgrid], // 嵌入的表单面板
				buttons : [{
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								compGroupWindow.hide();
							}
						}]
			});
	// 弹出前事件
	compGroupWindow.on('show', function() {
				stgdispgridform.form.reset();
			});
	// 弹出窗口
	var groupAddWindow = new Ext.Window({
				title : '<span class="commoncss">创建分组</span>', // 窗口标题
				id : 'groupAddWindow',
				closeAction : 'hide',
				layout : 'fit', // 设置窗口布局模式
				width : 350, // 窗口宽度
				height : 300, // 窗口高度
				// closable : true, // 是否可关闭
				collapsible : true, // 是否可收缩
				maximizable : true, // 设置是否可以最大化
				border : false, // 边框线设置
				constrain : true, // 设置窗口是否可以溢出父容器
				pageY : 20, // 页面定位Y坐标
				pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
				items : [addGroupForm], // 嵌入的表单面板
				buttons : [{ // 窗口底部按钮配置
					text : '确定', // 按钮文本
					iconCls : 'tbar_synchronizeIcon', // 按钮图标
					handler : function() { // 按钮响应函数
						var gType = groupAddCombo.getValue();
						var tform = addGroupForm.getForm();
						var gname = tform.findField("groupName").getValue();
						var gnote = tform.findField("description").getValue();
						cleanGlobalVar();
						cleanCreateTrigger();
						// 简单分组
						if (gType == 1) {
							GOBJID = stgSelectCombo.getValue();
							GDISNAME = stgSelectCombo.lastSelectionText;
							GGROUPNAME = stgSelectCombo.lastSelectionText;
							GGROUPNOTE = gnote;
							editstgWindow.title = rendDispName(GOBJID);
							editstgWindow.show();
						} else {
							// 复杂分组
							GGROUPNAME = gname;
							GGROUPNOTE = gnote;
							GGROUPTYPE = gType;
							editstgWindow.title = gname;
							compGroupWindow.title = gname;
							// 设置新建的分组ID
							getCompGroupId(GGROUPTYPE, GGROUPNAME, GGROUPNOTE);
							groupStore.reload();
							compGroupWindow.show();
						}
						GGROUPTYPE = gType;
						groupAddWindow.hide();
					}
				}, {	// 窗口底部按钮配置
							text : '重置', // 按钮文本
							iconCls : 'tbar_synchronizeIcon', // 按钮图标
							handler : function() { // 按钮响应函数
								addGroupForm.form.reset();
							}
						}, {
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								groupAddWindow.hide();
							}
						}]
			});
	// 弹出前事件
	groupAddWindow.on('show', function() {
				addGroupForm.form.reset();
			});

	/**
	 * 设置调度配置的相关参数
	 * 
	 * @param {}
	 *            trgid
	 */
	function settingCreateTrigger(trgid, isGroup) {
		Ext.Ajax.request({
					url : '../strategy/settgrparam',
					params : {
						'id' : trgid,
						'isGroup' : isGroup
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
	 * 设置简单分组调度配置的相关参数
	 * 
	 * @param {}
	 *            trgid
	 */
	function settingGroupCreateTrigger(gid) {
		Ext.Ajax.request({
					url : '../strategy/setgroupparam',
					params : {
						'gid' : gid
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
										url : '../dispsys/deletetrg',
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
		var list = groupStore.reader.jsonData.dispname;
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

	/**
	 * 获取分组ID
	 */
	function getCompGroupId(groupType, groupName, groupNote) {
		var params = {};
		params['groupType'] = groupType;
		params['groupName'] = groupName;
		params['groupNote'] = groupNote;
		Ext.Ajax.request({
					url : '../strategy/newgroupid',
					success : function(response, opts) {
						var result = Ext.util.JSON
								.decode(response.responseText);
						GGROUPID = result.groupId;
						store.setBaseParam('groupId', GGROUPID);
						// TODO
						store.reload();
					},
					failure : function(response, opts) {
						Ext.MessageBox.alert('提示', '保存分组失败，无法进入编辑页面');
					},
					params : params
				});
	}

	/**
	 * 清空所有全局变量
	 */
	function cleanGlobalVar() {
		GOBJID = null;
		GDISNAME = null;
		// 全局调度ID
		GDISPID;
		// 全局分组类型
		GGROUPTYPE = null;
		// 全局分组ID
		GGROUPID = null;
		// 复杂分组名称
		GGROUPNAME = null;
		// 复杂分组说明
		GGROUPNOTE = null;
		// 是否是分组
		GISGROUP = null;
	}

	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [groupForm, groupGrid]
			});
});