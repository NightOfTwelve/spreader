Ext.onReady(function() {
	var fromGroupId = null;
	var toGroupId = null;
	// 构造树的根节点ROOT
	var stgroot = new Ext.tree.AsyncTreeNode({
				id : '-1',
				text : '配置列表'
			});
	// 策略列表树
	var stgdisptree = new Ext.tree.TreePanel({
		id : 'stgtree',
		region : 'center',
		autoScroll : false,
		// autoHeight : true,
		expanded : true,
		singleExpand : true,
		useArrows : true,
		rootVisible : true,
		root : stgroot,
		loader : new Ext.tree.TreeLoader({
			dataUrl : '../strategy/createdisptree?time=' + new Date().getTime(),
			processResponse : function(response, node, callback, scope) {
				toSelectUserGroupCombo.hide();
				var json = response.responseText;
				var respObj = Ext.util.JSON.decode(json);
				// 展示类型
				var extendType = respObj.extendType;
				var meta = respObj.extendMeta;
				// 是否显示分组A
				var hasFromGroup = false;
				// 是否显示分组B
				var hasToGroup = false;
				// 获取StrategyUserGroup
				var sug = respObj.sug;
				// 如果存在则设置comboBox的初始值
				if (!Ext.isEmpty(sug)) {
					var fromId = sug.fromUserGroup;
					var toId = sug.toUserGroup;
					fromSelectUserGroupCombo.setValue(fromId);
					toSelectUserGroupCombo.setValue(toId);
				}
				// 策略信息
				var desc = '';
				if (extendType != null && meta != null) {
					hasFromGroup = meta.hasFromGroup;
					hasToGroup = meta.hasToGroup;
					desc = meta.strategyDesc;
				}
				descField.setValue(desc);
				userGroupCompIsShow(extendType, hasFromGroup, hasToGroup);
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
								dispatchDeleteNode(stgdisptree);
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
				id : 'simpcard',
				height : 80,
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
						var fhidden = fromSelectUserGroupCombo.hidden;
						var thidden = toSelectUserGroupCombo.hidden;
						var fromId = fromSelectUserGroupCombo.getValue();
						var toId = toSelectUserGroupCombo.getValue();
						if (!fhidden && Ext.isEmpty(fromId)) {
							Ext.MessageBox.alert("提示", "用户分组不能为空");
							return;
						}
						if (!thidden && Ext.isEmpty(toId)) {
							Ext.MessageBox.alert("提示", "用户分组不能为空");
							return;
						}
						strategyGroupSubmitTreeData(stgdisptree,
								triggerDispForm, radioForm, simpleDispForm,
								editstgWindow, store, groupStore, fromId, toId);
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
				height : 80,
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
						var fhidden = fromSelectUserGroupCombo.hidden;
						var thidden = toSelectUserGroupCombo.hidden;
						var fromId = fromSelectUserGroupCombo.getValue();
						var toId = toSelectUserGroupCombo.getValue();
						if (!fhidden && Ext.isEmpty(fromId)) {
							Ext.MessageBox.alert("提示", "用户分组不能为空");
							return;
						}
						if (!thidden && Ext.isEmpty(toId)) {
							Ext.MessageBox.alert("提示", "用户分组不能为空");
							return;
						}
						strategyGroupSubmitTreeData(stgdisptree,
								triggerDispForm, radioForm, simpleDispForm,
								editstgWindow, store, groupStore, fromId, toId);
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
				region : 'center',
				id : 'cardPanel',
				layout : 'card',
				split : true,
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
				frame : true,
				region : 'north',
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
									var t = radioForm.getForm();
									if (ck.inputValue == '1') {
										cardPanelCmp.setActiveItem(0);
										triggerDispForm.getForm().reset();
									} else {
										cardPanelCmp.setActiveItem(1);
										simpleDispForm.getForm().reset();
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

	// 嵌入的FORM
	// var infoViewForm = new Ext.form.FormPanel({
	// autoScroll : true,
	// id : 'infoViewForm',
	// split : true,
	// title:'KKKK',
	// region : 'north',
	// height : 100,
	// // border : true,
	// // labelWidth : 100, // 标签宽度
	// frame : true, // 是否渲染表单面板背景色
	// defaultType : 'textfield', // 表单元素默认类型
	// labelAlign : 'left', // 标签对齐方式
	// bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
	// items : [{
	// xtype : 'label',
	// fieldLabel : '策略信息',
	// id : 'stginfo',
	// labelStyle : 'padding:0px',
	// text : ''
	// }]
	// });
	// 策略说明的隐藏域
	var descField = new Ext.form.Hidden({
				name : 'descField'
			});
	// 嵌入的FORM
	var infoViewForm = new Ext.form.FormPanel({
				autoScroll : true,
				title : '选择用户分组',
				id : 'infoViewForm',
				split : true,
				region : 'north',
				height : 120,
				border : true,
				// labelWidth : 100, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				defaultType : 'textfield', // 表单元素默认类型
				labelAlign : 'left', // 标签对齐方式
				bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
				items : [{
							xtype : 'label',
							fieldLabel : '策略信息',
							id : 'stginfo',
							labelStyle : 'padding:0px',
							text : ''
						}, fromSelectUserGroupCombo, toSelectUserGroupCombo]
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
							layout : 'border',
							id : 'editview',
							split : true,
							width : 500,
							items : [infoViewForm, stgdisptree
							// ,
							// addUserGroupSelectCmbForm
							]
						}, {
							region : 'south',
							title : '调度配置',
							layout : 'border',
							split : true,
							height : 200,
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
	// 为Combo加入选择事件 TODO
	fromSelectUserGroupCombo.on('select', function(combo, record, index) {
				var agroup = fromSelectUserGroupCombo.getRawValue();
				var btag = toSelectUserGroupCombo.hidden;
				if (btag) {
					var text = createStrategyExplain(agroup, null);
					settingDescInfo(text);
				} else {
					return;
				}
			});
	// 为Combo加入选择事件
	toSelectUserGroupCombo.on('select', function(combo, record, index) {
				var agroup = fromSelectUserGroupCombo.getRawValue();
				var bgroup = toSelectUserGroupCombo.getRawValue();
				var text = createStrategyExplain(agroup, bgroup);
				settingDescInfo(text);
			});
	// show事件，需先删除组件，再重新创建PPTGRID
	editstgWindow.on('show', function() {
				stgdisptree.getRootNode().reload();
				stgdisptree.root.select();
				// 初始化清理相关信息
				cleanStrategyExplain();
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
						var gidArray = [];
						var rows = groupGrid.getSelectionModel()
								.getSelections();
						if (rows.length > 0) {
							for (var i = 0; i < rows.length; i++) {
								var uid = rows[i].data.id;
								gidArray.push(uid);
							}
						} else {
							Ext.Msg.alert("提示", "请至少选择一个分组");
							return;
						}
						Ext.Msg.show({
							title : '确认信息',
							msg : '确定删除?',
							buttons : Ext.Msg.YESNO,
							fn : function(ans) {
								if (ans == 'yes') {
									Ext.Ajax.request({
										url : '../stggroup/deletegroup?_time'
												+ new Date().getTime(),
										params : {
											gids : gidArray
										},
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											if (result.success) {
												Ext.Msg.alert("提示", "删除成功");
											} else {
												Ext.Msg.alert("提示", "删除失败");
											}
											groupStore.reload();
										},
										failure : function() {
											Ext.Msg.alert("提示", "删除失败");
										}
									});
								}
							}
						});
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
				var descText = descField.getValue();
				settingDescInfo(descText);
				// TODO
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
					// editstgWindow.title = data.transformName;
					// TODO
					// editstgWindow.show();
				} else {
					// 复杂分组
					GGROUPID = data.id;
					GGROUPNAME = data.groupName;
					GGROUPTYPE = gType;
					var wtext = '当前分组:' + data.groupName + ',编号:' + data.id;
					Ext.getCmp('groupinfo').setText(wtext);
					compGroupWindow.show();
					store.setBaseParam('groupId', data.id);
					store.reload();
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
				modal : true,
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
						// TODO
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
				stgCmbForm.getForm().reset();
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
					items : [{
								columnWidth : .5,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "调度名称",
											name : 'dispname1',
											width : 100
										}, {
											xtype : 'label',
											fieldLabel : '分组信息',
											id : 'groupinfo',
											labelStyle : 'padding:0px',
											text : ''
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
								newtag = true;
								stgCmbWindow.show();
							}
						}, '-', {
							text : '删除',
							iconCls : 'page_delIcon',
							handler : function() {
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
				modal : true,
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
				modal : true,// 遮罩
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
				// 新增 先清空GDISPID
				GDISPID = null;
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
				if (result.error) {
					Ext.MessageBox.alert('错误', '任务获取失败，不能设置参数');
					return;
				} else {
					editstgWindow.show();
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
					tradioForm.findField("triggerType").setValue(triggerType);
					tradioForm.findField("description").setValue(description);
					tsimpleDispForm.findField("start").setValue(sdate);
					tsimpleDispForm.findField("repeatTimes")
							.setValue(repeatTimes);
					tsimpleDispForm.findField("repeatInternal")
							.setValue(repeatInternal);
					ttriggerDispForm.findField("cron").setValue(cron);
					var remindcmp = Ext.getCmp("jobremind");
					var tstr = '任务:' + rendDispName(GDISNAME) + ',编号:'
							+ GDISPID + ',目前运行信息:' + remind;
					remindcmp
							.setText('<font color = "red">' + tstr + '</font>');
				}
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
		var list = groupStore.reader.jsonData.list;
		for (var idx in list) {
			var tmp = list[idx].groupName;
			var dname = list[idx].transformName;
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
	/**
	 * 设置策略说明面板
	 */
	function settingDescInfo(text) {
		var selectstr = Ext.getCmp('stginfo');
		selectstr.setText(text);
	}

	/**
	 * 通过策略类型转换说明信息 note = ateam 关注 bteam
	 */
	function createStrategyExplain(ateam, bteam) {
		var desc = descField.getValue();
		var text = '';
		if (!Ext.isEmpty(desc)) {
			if (desc.indexOf('${fromGroup}') != -1) {
				text = desc.replace('${fromGroup}', ' 分组A:【' + ateam + '】');
			}
			if (text.indexOf('${toGroup}') != -1) {
				text = text.replace('${toGroup}', ' 分组B:【' + bteam + '】');
			}
		}
		return text;
	}
	/**
	 * 设置字体颜色
	 */
	function redenInfoColor(text) {
		return '<font color="red">text</font>';
	}

	/**
	 * 判断是否显示用户分组的组件
	 */
	function userGroupCompIsShow(isUserGroup, hasFromGroup, hasToGroup) {
		if (!Ext.isEmpty(isUserGroup) && isUserGroup == 'userGroup') {
			if (hasFromGroup) {
				fromSelectUserGroupCombo.show();
			} else {
				fromSelectUserGroupCombo.hide();
			}
			if (hasToGroup) {
				toSelectUserGroupCombo.show();
			} else {
				toSelectUserGroupCombo.hide();
			}
		} else {
			fromSelectUserGroupCombo.hide();
			toSelectUserGroupCombo.hide();
		}
	}

	/**
	 * 初始化清空策略描述
	 */
	function cleanStrategyExplain() {
		var exp = Ext.getCmp('stginfo');
		exp.setText('');
		var fcombo = Ext.getCmp('fromSelectUserGroupCombo');
		var tcombo = Ext.getCmp('toSelectUserGroupCombo');
		fcombo.setValue('');
		tcombo.setValue('');
	}

	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [groupForm, groupGrid]
			});
});