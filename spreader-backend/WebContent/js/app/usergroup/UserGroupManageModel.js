Ext.onReady(function() {
	// 用户数组
	var userArray = [];
	var userGroupPropExpRoot = new Ext.tree.AsyncTreeNode({
				id : '-1',
				text : '配置列表'
			});
	var userGroupPropExpTree = new Ext.tree.TreePanel({
		id : 'userGroupPropExpTree',
		autoScroll : false,
		autoHeight : true,
		expanded : true,
		singleExpand : true,
		useArrows : true,
		rootVisible : true,
		root : userGroupPropExpRoot,
		tbar : [{
					text : '保存修改',
					iconCls : 'addIcon',
					handler : function() {
						userGroupSubmitTreeData(userGroupPropExpTree, store);
					}
				}],
		loader : new Ext.tree.TreeLoader({
					dataUrl : '../usergroup/genpropexp?time='
							+ new Date().getTime(),
					processResponse : function(response, node, callback, scope) {
						var json = response.responseText;
						var respObj = Ext.util.JSON.decode(json);
						try {
							submitid = respObj.gid;
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
								gid : GUSERGROUPID
							};
						}
					}
				})
	});
	userGroupPropExpTree.expand(true, true);
	// 树形编辑器
	var treeEditor = new Ext.tree.TreeEditor(
			Ext.getCmp('userGroupPropExpTree'), {
				id : 'userGroupPropExpTreeEdit',
				allowBlank : false
			});
	/**
	 * 右键菜单相关代码
	 */
	// 给tree添加右键菜单事件
	userGroupPropExpTree.on('rightMenu', userGroupPropExpTree.rightMenu,
			userGroupPropExpTree);
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
	userGroupPropExpTree.on('click', function(node) {
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
	userGroupPropExpTree.on('contextmenu', function(node, event) {
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
		var selectedNode = userGroupPropExpTree.getSelectionModel()
				.getSelectedNode();
		var parent = selectedNode.parentNode;
		parent.removeChild(selectedNode);
		parent.attributes.children.pop(selectedNode);
		// selectedNode.remove();
	};
	// 修改节点事件实现
	function modifNode() {
		var selectedNode = userGroupPropExpTree.getSelectionModel()
				.getSelectedNode();// 得到选中的节点
		treeEditor.editNode = selectedNode;
		treeEditor.startEdit(selectedNode.ui.textNode);
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
	 * 分组类型的COMB的数据源
	 */
	var groupTypeStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['0', '静态分组'], ['1', '动态分组'], ['2', '手动分组']]
			});

	/**
	 * 用户分组查询FORM
	 */
	var userGroupForm = new Ext.form.FormPanel({
				region : 'north',
				title : "组合查询",
				autoWidth : true,
				height : 220,
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
											fieldLabel : '分组类型',
											xtype : 'combo',
											store : groupTypeStore,
											id : 'gtype',
											hiddenName : 'gtype',
											valueField : 'ID',
											editable : false,
											displayField : 'NAME',
											mode : 'local',
											forceSelection : false,// 必须选择一项
											emptyText : '分组类型...',// 默认值
											triggerAction : 'all',
											anchor : '53%'
										}]
							}, {
								columnWidth : .3, // 该列有整行中所占百分比
								layout : "form", // 从上往下的布局
								items : [webSiteComboUtil(
										'selectWebSiteComboBakUtil',
										'selectWebSiteComboBakUtil', null, null)]
							}, {
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "分组名称",
											name : 'groupName',
											anchor : '53%'
										}]
							}]
				}, {
					layout : "column", // 从左往右的布局
					items : [{
								columnWidth : .3, // 该列有整行中所占百分比
								layout : "form", // 从上往下的布局
								items : [{
											xtype : "datetimefield",
											fieldLabel : "开始时间",
											name : 'fromModifiedTime',
											width : 150
										}]
							}, {
								columnWidth : .3, // 该列有整行中所占百分比
								layout : "form", // 从上往下的布局
								items : [{
											xtype : "datetimefield",
											fieldLabel : "结束时间",
											name : 'toModifiedTime',
											width : 150
										}]
							}]
				}, {
					xtype : 'fieldset',
					title : '属性筛选',
					autoHeight : true,
					animCollapse : true,
					layout : 'form',
					collapsed : false,
					collapsible : true,
					items : [{
								xtype : 'checkboxgroup',
								fieldLabel : '条件',
								name : 'propValBox',
								items : [{
											boxLabel : '分类',
											value : 1,
											name : 'category'
										}, {
											boxLabel : '评分',
											value : 2,
											name : 'score'
										}, {
											boxLabel : '粉丝数',
											value : 4,
											name : 'fans'
										}, {
											boxLabel : '关注数',
											value : 8,
											name : 'attentions'
										}, {
											boxLabel : '文章数',
											value : 16,
											name : 'articles'
										}, {
											boxLabel : '性别',
											value : 32,
											name : 'gender'
										}, {
											boxLabel : '机器人粉丝数',
											value : 64,
											name : 'robotFans'
										}, {
											boxLabel : '星座',
											value : 128,
											name : 'constellation'
										}, {
											boxLabel : '出生日期',
											value : 256,
											name : 'birthDay'
										}, {
											boxLabel : '是否机器人',
											value : 512,
											name : 'isRobot'
										}, {
											boxLabel : '是否认证',
											value : 1024,
											name : 'vType'
										}, {
											boxLabel : '昵称',
											value : 2048,
											name : 'nickName'
										}, {
											boxLabel : '国家',
											value : 4096,
											name : 'nationality'
										}, {
											boxLabel : '省份',
											value : 8192,
											name : 'province'
										}, {
											boxLabel : '城市',
											value : 16384,
											name : 'city'
										}, {
											boxLabel : '个人简介',
											value : 32768,
											name : 'introduction'
										}]
							}]
				}],
				buttonAlign : "center",
				buttons : [{
					text : "查询",
					handler : function() {
						var tform = userGroupForm.getForm();
						var tstime = tform.findField('fromModifiedTime')
								.getValue();
						var tetime = tform.findField('toModifiedTime')
								.getValue();
						var groupName = tform.findField('groupName').getValue();
						var gtype = tform.findField('gtype').getValue();
						var websiteid = tform.findField('websiteId').getValue();
						var propField = tform.findField('propValBox')
								.getValue();
						var propValue = 0;
						if (propField.length > 0) {
							for (var i = 0; i < propField.length; i++) {
								propValue += propField[i].value;
							}
						}
						store.setBaseParam('gtype', gtype);
						store.setBaseParam('websiteid', websiteid);
						store.setBaseParam('propVal', propValue);
						store.setBaseParam('gname', groupName);
						store.setBaseParam('fromModifiedTime', tstime != null
										&& tstime != '' ? new Date(tstime)
										.format('Y-m-d') : null);
						store.setBaseParam('toModifiedTime', tetime != null
										&& tetime != '' ? new Date(tetime)
										.format('Y-m-d') : null);
						store.load();
					}
				}, {
					text : "重置",
					handler : function() {
						userGroupForm.form.reset();
					}
				}]
			});

	// 定义表格数据源
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../usergroup/grouplist'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'gid'
								}, {
									name : 'gname'
								}, {
									name : 'gtype'
								}, {
									name : 'websiteId'
								}, {
									name : 'description'
								}]),
				autoLoad : {
					params : {
						start : 0,
						limit : 20
					}
				}
			});
	// 定义Checkbox
	var sm = new Ext.grid.CheckboxSelectionModel();
	// 定义表格列CM
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
				header : '分组编号',
				dataIndex : 'gid',
				width : 80
			}, {
				header : '分组名称',
				dataIndex : 'gname',
				width : 100
			}, {
				header : '分组类型',
				dataIndex : 'gtype',
				width : 100,
				renderer : renderUserGroupType
			}, {
				header : '分组说明',
				dataIndex : 'description',
				renderer : renderBrief,
				width : 100
			}, {
				header : '网站类型',
				dataIndex : 'websiteId',
				renderer : renderWebsiteType,
				width : 100
			}, {
				header : '属性配置',
				renderer : function showbutton(value, cellmeta, record,
						rowIndex, columnIndex, store) {
					var gtype = record.data['gtype'];
					var returnStr = "";
					// 手动分组不显示配置按钮
					if (gtype != 2) {
						returnStr = "<input type='button' value='配置'/>";
					}
					return returnStr;
				},
				width : 100
			}, {
				header : '添加成员',
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='添加'/>";
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

	// 用户分组列表
	var userGroupGrid = new Ext.grid.GridPanel({
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
						addGroupCmbForm.form.reset();
						addGroupWindow.show();
					}
				}, '-', {
					text : '删除',
					iconCls : 'page_delIcon',
					handler : function() {
						var delGroupArray = [];
						var rows = userGroupGrid.getSelectionModel()
								.getSelections();
						if (rows.length > 0) {
							for (var i = 0; i < rows.length; i++) {
								var gid = rows[i].data.gid;
								delGroupArray.push(gid);
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
										url : '../usergroup/removegroup?_time'
												+ new Date().getTime(),
										params : {
											gids : delGroupArray
										},
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											if (result.success) {
												Ext.Msg.alert("提示", "删除成功");
											} else {
												Ext.Msg.alert("提示", "删除失败");
											}
											store.reload();
										},
										failure : function() {
											Ext.Msg.alert("提示", "删除失败");
											store.reload();
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
						store.reload();
					}
				}],
		bbar : bbar,
		onCellClick : function(grid, rowIndex, columnIndex, e) {
			GUSERGROUPID = null;
			var buttons = e.target.defaultValue;
			var record = grid.getStore().getAt(rowIndex);
			var data = record.data;
			// 找出表格中‘配置’按钮
			if (buttons == '配置') {
				var gname = data.gname;
				GUSERGROUPID = data.gid;
				editstgWindow.title = gname;
				editstgWindow.show();
			}
			if (buttons == '添加') {
				GUSERGROUPID = data.gid;
				Ext.getCmp("groupinfo").setText(data.gname + ',编号:'
						+ GUSERGROUPID);
				addGroupUserWindow.show();
				deleteUserStore.setBaseParam('gid', GUSERGROUPID);
				selectUserStore.setBaseParam('gid', GUSERGROUPID);
				deleteUserStore.load();
				selectUserStore.load();
			}
		}
	});
	// 注册事件
	userGroupGrid.on('cellclick', userGroupGrid.onCellClick, userGroupGrid);

	/**
	 * 创建新增事件ComboBox
	 */
	// 创建ComboBox数据源，支持Ajax取值
	// var stgCmbStore = new Ext.data.Store({
	// // 代理模式
	// proxy : new Ext.data.HttpProxy({
	// url : '../dispsys/combstore'
	// }),
	// // 读取模式
	// reader : new Ext.data.JsonReader({}, [{
	// name : 'name'
	// }, {
	// name : 'displayName'
	// }])
	// });
	// 分组类型COMB
	var gtypeCombo = new Ext.form.ComboBox({
				hiddenName : 'gtype',
				id : 'gtypeCombo',
				fieldLabel : '分组类型',
				emptyText : '请选择分组类型...',
				triggerAction : 'all',
				store : groupTypeStore,
				displayField : 'NAME',
				valueField : 'ID',
				loadingText : '正在加载数据...',
				allowBlank : false,
				mode : 'local', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
				forceSelection : true,
				typeAhead : true,
				resizable : true,
				editable : false,
				anchor : '100%'
			});
	// 选择事件，用于联动
	gtypeCombo.on('select', function() {
				var value = gtypeCombo.getValue();
				if (value == 0) {
					Ext.MessageBox.alert('提示', '静态分组暂不支持,请选择其它类型');
					gtypeCombo.setValue('');
					return;
				}
			});
	// 嵌入的FORM
	var addGroupCmbForm = new Ext.form.FormPanel({
		id : 'addGroupCmbForm',
		name : 'addGroupCmbForm',
		labelWidth : 100, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		defaultType : 'textfield', // 表单元素默认类型
		labelAlign : 'left', // 标签对齐方式
		bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
		items : [
				gtypeCombo,
				webSiteComboUtil('selectWebSiteComboUtil',
						'selectWebSiteComboUtil', null, null), {
					xtype : "textfield",
					fieldLabel : "分组名称",
					name : 'gname',
					allowBlank : false,
					width : 100,
					listeners : {
						'blur' : function(foc) {
							var tname = foc.getValue();
							var tform = addGroupCmbForm.getForm();
							Ext.Ajax.request({
										url : '../usergroup/checkname',
										params : {
											'gname' : tname
										},
										scope : addGroupCmbForm,
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											if (result.success) {
												Ext.Msg.alert("提示",
														"已存在相同组名,请重新输入");
												tform.findField('gname')
														.setValue("");
											}
										},
										failure : function() {
											Ext.Msg.alert("提示", "分组名检查失败");
											return;
										}
									});
						}
					}
				}, {
					xtype : "textfield",
					fieldLabel : "分组说明",
					name : 'description',
					width : 100
				}]
	});
	// 添加用户分组弹出窗口
	var addGroupWindow = new Ext.Window({
				title : '<span class="commoncss">新增用户分组</span>', // 窗口标题
				id : 'addGroupWindow',
				closeAction : 'hide',
				layout : 'fit', // 设置窗口布局模式
				width : 350, // 窗口宽度
				height : 180, // 窗口高度
				// closable : true, // 是否可关闭
				collapsible : true, // 是否可收缩
				maximizable : true, // 设置是否可以最大化
				border : false, // 边框线设置
				constrain : true, // 设置窗口是否可以溢出父容器
				pageY : 20, // 页面定位Y坐标
				pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
				items : [addGroupCmbForm], // 嵌入的表单面板
				buttons : [{ // 窗口底部按钮配置
					text : '确定', // 按钮文本
					iconCls : 'tbar_synchronizeIcon', // 按钮图标
					handler : function() { // 按钮响应函数
						GUSERGROUPID = null;
						var addForm = addGroupCmbForm.getForm();
						// 分组类型
						var gtype = gtypeCombo.getValue();
						// 网站类型
						// var websiteid = selectWebSiteComboUtil.getValue();
						var websiteid = addForm.findField('websiteId')
								.getValue();
						// 分组名称
						var gname = addForm.findField('gname').getValue();
						// 分组说明
						var description = addForm.findField('description')
								.getValue();
						editstgWindow.title = gname;
						Ext.Ajax.request({
									url : '../usergroup/createusergroup',
									params : {
										'gname' : gname,
										'gtype' : gtype,
										'websiteid' : websiteid,
										'description' : description
									},
									scope : addGroupCmbForm,
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										if (result.success) {
											GUSERGROUPID = result.gid;
											// 如果是手工分组不弹出属性编辑
											if (gtype != 2) {
												editstgWindow.show();
											} else {
												Ext.MessageBox.alert("提示",
														"创建成功");
												store.reload();
											}
										} else {
											Ext.Msg.alert("提示", "用户分组创建失败");
											return;
										}
									},
									failure : function() {
										Ext.Msg.alert("提示", "用户分组创建失败");
										return;
									}
								});
						// editstgWindow.show();
						addGroupWindow.hide();
					}
				}, {	// 窗口底部按钮配置
							text : '重置', // 按钮文本
							iconCls : 'tbar_synchronizeIcon', // 按钮图标
							handler : function() { // 按钮响应函数
								addGroupCmbForm.form.reset();
							}
						}, {
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								addGroupWindow.hide();
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
				title : '<span class="commoncss">详细配置</span>',
				iconCls : 'app_rightIcon',
				modal : true,
				collapsible : true,
				maximizable : true,
				animCollapse : true,
				animateTarget : document.head,
				buttonAlign : 'right',
				constrain : true,
				items : [{
							region : 'center',
							id : 'pptgridmanage',
							header : false,
							layout : 'fit',
							autoScroll : true,
							collapsible : true,
							split : true
						}, {
							region : 'west',
							split : true,
							width : 200,
							minWidth : 175,
							maxWidth : 400,
							items : [userGroupPropExpTree]
						}],
				buttons : [{
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								editstgWindow.hide();
							}
						}]
			});
	// ///////////////////添加用户部分代码//////////////////
	// 为Combo加入选择事件
	selectUserComboUtil.on('select', function(combo, record, index) {
				var uid = selectUserComboUtil.getValue();
				var uname = selectUserComboUtil.getRawValue();
				var tutil = Ext.getCmp('selectUserComboUtil');
				if (!Ext.isEmpty(uid)) {
					if (userArray.indexOf(uid) < 0) {
						userArray.push(uid);
						var selectstr = Ext.getCmp('selectusername');
						selectstr.setText(uname + ";" + selectstr.text);
					} else {
						Ext.MessageBox.alert("提示:", uname + "已添加，请勿重复添加");
					}
				}
				tutil.setValue('');
			});
	// 嵌入的FORM
	var addUserSelectCmbForm = new Ext.form.FormPanel({
		autoScroll : true,
		id : 'addUserSelectCmbForm',
		split : true,
		region : 'north',
		height : 150,
		border : true,
		// labelWidth : 100, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		defaultType : 'textfield', // 表单元素默认类型
		labelAlign : 'right', // 标签对齐方式
		bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
		items : [{
					xtype : 'label',
					fieldLabel : '分组信息',
					id : 'groupinfo',
					labelStyle : 'padding:0px',
					text : ''
				}, selectUserComboUtil, {
					xtype : 'label',
					fieldLabel : '已筛选人员',
					id : 'selectusername',
					labelStyle : 'padding:0px',
					text : ''
				}],
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			handler : function() {
				var addForm = addUserSelectCmbForm.getForm();
				if (addForm.isValid()) {
					Ext.Msg.show({
						title : '确认信息',
						msg : '确定添加?',
						buttons : Ext.Msg.YESNO,
						fn : function(ans) {
							if (ans == 'yes') {
								Ext.Ajax.request({
											url : '../usergroup/adduser?_time'
													+ new Date().getTime(),
											params : {
												gid : GUSERGROUPID,
												uids : userArray
											},
											success : function(response) {
												var result = Ext
														.decode(response.responseText);
												Ext.Msg.alert("提示",
														result.message);
												cleanParams();
												selectUserStore.setBaseParam(
														'gid', GUSERGROUPID);
												selectUserStore.reload();
											},
											failure : function() {
												cleanParams();
												selectUserStore.setBaseParam(
														'gid', GUSERGROUPID);
												selectUserStore.reload();
												Ext.Msg.alert("提示", "添加失败");
											}
										});
							}
						}
					});
				}
			}
		}, {
			text : "清空",
			handler : function() { // 按钮响应函数
				cleanParams();
			}
		}]
	});
	// 定义表格数据源
	var selectUserStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../usergroup/selectuserlist'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'user.id'
								}, {
									name : 'user.websiteId'
								}, {
									name : 'user.isRobot'
								}, {
									name : 'manual'
								}, {
									name : 'user.nickName'
								}, {
									name : 'user.gender'
								}, {
									name : 'user.realName'
								}]),
				autoLoad : {
					params : {
						start : 0,
						limit : 20,
						gid : GUSERGROUPID
					}
				}
			});
	// 已经选择的USER列表
	var selectsm = new Ext.grid.CheckboxSelectionModel();
	// 定义表格列CM
	var selectcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
			selectsm, {
				header : '编号',
				dataIndex : 'user.id',
				width : 80
			}, {
				header : '昵称',
				dataIndex : 'user.nickName',
				width : 100
			}, {
				header : '性别',
				dataIndex : 'user.gender',
				renderer : renderGender,
				width : 80
			}, {
				header : '网站',
				dataIndex : 'user.websiteId',
				renderer : renderWebsiteType,
				width : 100
			}, {
				header : '机器人',
				dataIndex : 'user.isRobot',
				renderer : rendTrueFalse,
				width : 80
			}, {
				header : '手工添加',
				dataIndex : 'manual',
				renderer : rendTrueFalse,
				width : 80
			}, {
				header : '真名',
				dataIndex : 'user.realName',
				width : 80
			}]);
	// 页数
	var selectnumber = 20;
	var selectnumtext = new Ext.form.TextField({
				id : 'selectnumbermaxpage',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							selectbbar.pageSize = parseInt(selectnumber
									.getValue());
							selectnumber = parseInt(selectnumtext.getValue());
							selectUserStore.reload({
										params : {
											start : 0,
											limit : selectbbar.pageSize
										}
									});
						}
					}
				}
			});

	// 分页菜单
	var selectbbar = new Ext.PagingToolbar({
				pageSize : selectnumber,
				store : selectUserStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', selectnumtext]
			});

	// 用户分组列表
	var selectUserGroupGrid = new Ext.grid.GridPanel({
		region : 'center',
		autoScroll : true,
		split : true,
		store : selectUserStore,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		// stripeRows : true,
		frame : true,
		// autoExpandColumn : 'remark',
		sm : selectsm,
		cm : selectcm,
		tbar : [{
			text : '删除',
			iconCls : 'page_delIcon',
			handler : function() {
				var delUserArray = [];
				var rows = selectUserGroupGrid.getSelectionModel()
						.getSelections();
				if (rows.length > 0) {
					for (var i = 0; i < rows.length; i++) {
						var uid = rows[i].data['user.id'];
						delUserArray.push(uid);
					}
				} else {
					Ext.Msg.alert("提示", "请至少选择一个人员");
					return;
				}
				Ext.Msg.show({
					title : '确认信息',
					msg : '确定删除?',
					buttons : Ext.Msg.YESNO,
					fn : function(ans) {
						if (ans == 'yes') {
							Ext.Ajax.request({
										url : '../usergroup/deleteuser?_time'
												+ new Date().getTime(),
										params : {
											gid : GUSERGROUPID,
											uids : delUserArray
										},
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											Ext.Msg.alert("提示", result.message);
											selectUserStore.setBaseParam('gid',
													GUSERGROUPID);
											deleteUserStore.setBaseParam('gid',
													GUSERGROUPID);
											selectUserStore.reload();
											deleteUserStore.reload();
										},
										failure : function() {
											selectUserStore.setBaseParam('gid',
													GUSERGROUPID);
											deleteUserStore.setBaseParam('gid',
													GUSERGROUPID);
											selectUserStore.reload();
											deleteUserStore.reload();
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
				selectUserStore.setBaseParam('gid', GUSERGROUPID);
				selectUserStore.reload();
			}
		}, '-', new Ext.form.TextField({
					id : 'queryDelUserForm',
					name : 'queryDelUserForm',
					emptyText : '请输入昵称',
					enableKeyEvents : true,
					listeners : {
						specialkey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								// queryRoleItem();
							}
						}
					},
					width : 130
				}), {
			text : '查询',
			iconCls : 'previewIcon',
			handler : function() {
				// queryRoleItem();
			}
		}],
		bbar : selectbbar
	});
	// 定义表格数据源
	var deleteUserStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../usergroup/deleteuserlist'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'user.id'
								}, {
									name : 'user.websiteId'
								}, {
									name : 'user.isRobot'
								}, {
									name : 'manual'
								}, {
									name : 'user.nickName'
								}, {
									name : 'user.gender'
								}, {
									name : 'user.realName'
								}]),
				autoLoad : {
					params : {
						start : 0,
						limit : 20,
						gid : GUSERGROUPID
					}
				}
			});
	// 已经删除的USER列表
	var deletesm = new Ext.grid.CheckboxSelectionModel();
	// 定义表格列CM
	var deletecm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
			deletesm, {
				header : '编号',
				dataIndex : 'user.id',
				width : 80
			}, {
				header : '昵称',
				dataIndex : 'user.nickName',
				width : 100
			}, {
				header : '性别',
				dataIndex : 'user.gender',
				renderer : renderGender,
				width : 80
			}, {
				header : '网站',
				dataIndex : 'user.websiteId',
				renderer : renderWebsiteType,
				width : 100
			}, {
				header : '机器人',
				dataIndex : 'user.isRobot',
				renderer : rendTrueFalse,
				width : 80
			}, {
				header : '手工添加',
				dataIndex : 'manual',
				renderer : rendTrueFalse,
				width : 80
			}, {
				header : '真名',
				dataIndex : 'user.realName',
				width : 80
			}]);
	// 页数
	var deletenumber = 20;
	var deletenumtext = new Ext.form.TextField({
				id : 'deletenumbermaxpage',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							deletebbar.pageSize = parseInt(deletenumber
									.getValue());
							deletenumber = parseInt(deletenumtext.getValue());
							deleteUserStore.reload({
										params : {
											start : 0,
											limit : deletebbar.pageSize
										}
									});
						}
					}
				}
			});

	// 分页菜单
	var deletebbar = new Ext.PagingToolbar({
				pageSize : deletenumber,
				store : deleteUserStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', deletenumtext]
			});

	// 用户分组列表
	var deleteUserGroupGrid = new Ext.grid.GridPanel({
		region : 'center',
		autoScroll : true,
		split : true,
		store : deleteUserStore,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		// stripeRows : true,
		frame : true,
		// autoExpandColumn : 'remark',
		sm : deletesm,
		cm : deletecm,
		tbar : [{
			text : '还原',
			iconCls : 'database_refreshIcon',
			handler : function() {
				var rollbackUserArray = [];
				var rows = deleteUserGroupGrid.getSelectionModel()
						.getSelections();
				if (rows.length > 0) {
					for (var i = 0; i < rows.length; i++) {
						var uid = rows[i].data['user.id'];
						rollbackUserArray.push(uid);
					}
				}
				Ext.Msg.show({
					title : '确认信息',
					msg : '确定还原?',
					buttons : Ext.Msg.YESNO,
					fn : function(ans) {
						if (ans == 'yes') {
							Ext.Ajax.request({
										url : '../usergroup/rollbackuser?_time'
												+ new Date().getTime(),
										params : {
											gid : GUSERGROUPID,
											uids : rollbackUserArray
										},
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											Ext.Msg.alert("提示", result.message);
											selectUserStore.setBaseParam('gid',
													GUSERGROUPID);
											deleteUserStore.setBaseParam('gid',
													GUSERGROUPID);
											selectUserStore.reload();
											deleteUserStore.reload();
										},
										failure : function() {
											selectUserStore.setBaseParam('gid',
													GUSERGROUPID);
											deleteUserStore.setBaseParam('gid',
													GUSERGROUPID);
											selectUserStore.reload();
											deleteUserStore.reload();
											Ext.Msg.alert("提示", "还原失败");
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
				deleteUserStore.setBaseParam('gid', GUSERGROUPID);
				deleteUserStore.reload();
			}
		}],
		bbar : deletebbar
	});
	// 手工添加成员的窗口组件
	var addGroupUserWindow = new Ext.Window({
				layout : 'border',
				width : document.documentElement.clientWidth - 200,
				height : document.documentElement.clientHeight - 200,
				resizable : true,
				draggable : true,
				closeAction : 'hide',
				title : '<span class="commoncss">分组人员配置</span>',
				iconCls : 'app_rightIcon',
				modal : true,
				collapsible : true,
				maximizable : true,
				animCollapse : true,
				animateTarget : document.head,
				buttonAlign : 'right',
				constrain : true,
				border : true,
				items : [{
							region : 'west',
							split : true,
							width : 500,
							layout : 'border',
							items : [addUserSelectCmbForm, selectUserGroupGrid]
						}, {
							region : 'center',
							split : true,
							layout : 'border',
							items : [deleteUserGroupGrid]
						}],
				buttons : [{
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								addGroupUserWindow.hide();
							}
						}]
			});
	// show事件，需先删除组件，再重新创建PPTGRID
	addGroupUserWindow.on('show', function() {
				cleanParams();
			});
	// show事件，需先删除组件，再重新创建PPTGRID
	editstgWindow.on('show', function() {
				userGroupPropExpTree.getRootNode().reload();
				userGroupPropExpTree.root.select();
				var pptGrid = Ext.getCmp("pptGrid");
				var pptMgr = Ext.getCmp("pptgridmanage");
				if (pptGrid != null) {
					pptMgr.remove(pptGrid);
				}
				pptMgr.doLayout();
			});
	/**
	 * 删除调度信息
	 * 
	 * @param {}
	 *            id
	 */
	function deleteData() {
		// 获取选中行
		var rows = userGroupGrid.getSelectionModel().getSelections();
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
										scope : userGroupGrid,
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
	 * 清除参数
	 */
	function cleanParams() {
		userArray = [];
		var namestr = Ext.getCmp('selectusername');
		namestr.setText('');
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
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [userGroupForm, userGroupGrid]
			});
});