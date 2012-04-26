Ext.onReady(function() {

	// 是否手动
	var isManualStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [[true, '是'], [false, '否']]
			});
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
							bbar.pageSize = Number(numtext.getValue());
							number = Number(numtext.getValue());
							keywordStore.reload({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
						}
					}
				}
			});
	/**
	 * 查询条件
	 */
	var keywordQueryForm = new Ext.form.FormPanel({
				region : 'north',
				title : "筛选条件",
				// collapsible : true,
				frame : true,
				id : 'keywordQueryForm',
				// border : true,
				labelWidth : 90, // 标签宽度
				frame : true,
				labelAlign : 'right',
				bodyStyle : 'padding:5 5 5 5',
				buttonAlign : 'center',
				height : 150,
				items : [{
							layout : "column",
							items : [{
										columnWidth : .33,
										layout : "form",
										items : [{
													xtype : "datefield",
													fieldLabel : "起始时间",
													name : 'startTime'
												}]
									}, {
										columnWidth : .33,
										layout : "form",
										items : [{
													xtype : "datefield",
													fieldLabel : "结束时间",
													name : 'endTime'
												}]
									}, {
										columnWidth : .3,
										layout : "form",
										items : [{
													xtype : "textfield",
													fieldLabel : "关键字",
													name : 'keywordName'
												}]
									}]
						}, {
							layout : "column",
							items : [{
										columnWidth : .33,
										layout : "form",
										items : [{
													fieldLabel : '是否手动',
													name : 'isManualCmp',
													xtype : 'combo',
													width : 100,
													store : isManualStore,
													id : 'isManual',
													hiddenName : 'isManual',
													valueField : 'ID',
													editable : false,
													displayField : 'NAME',
													mode : 'local',
													forceSelection : false,// 必须选择一项
													emptyText : '标签类型...',// 默认值
													triggerAction : 'all'
												}]
									}, {
										columnWidth : .33,
										layout : "form",
										items : [{
													xtype : "textfield",
													fieldLabel : "分类名称",
													name : 'categoryName'
												}]
									}, {
										columnWidth : .3,
										layout : "form",
										items : [{
													xtype : "textfield",
													fieldLabel : "关键字",
													name : 'keywordName'
												}]
									}]
						}],
				buttonAlign : "center",
				buttons : [{
					text : "查询",
					handler : function() { // 按钮响应函数
						var tform = keywordQueryForm.getForm();
						var startTime = tform.findField("startTime").getValue();
						var endTime = tform.findField("endTime").getValue();
						var cid = tform.findField("cid").getValue();
						keywordStore.setBaseParam('startTime',
								renderDateHis(startTime));
						keywordStore.setBaseParam('endTime',
								renderDateHis(endTime));
						keywordStore.setBaseParam('cid', cid);
						keywordStore.load();
					}
				}, {
					text : "重置",
					handler : function() { // 按钮响应函数
						keywordQueryForm.form.reset();
					}
				}]
			});
	// 嵌入的FORM
	var addKeywordCmbForm = new Ext.form.FormPanel({
				id : 'addKeywordCmbForm',
				name : 'addKeywordCmbForm',
				labelWidth : 100, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				defaultType : 'textfield', // 表单元素默认类型
				labelAlign : 'left', // 标签对齐方式
				bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
				items : [selectCategoryComboUtil, {
					xtype : "textfield",
					fieldLabel : "标签名称",
					name : 'keywordName',
					allowBlank : false,
					width : 100,
					listeners : {
						'blur' : function(foc) {
							var tname = foc.getValue();
							var tform = addKeywordCmbForm.getForm();
							Ext.Ajax.request({
										url : '../usergroup/checkname',
										params : {
											'gname' : tname
										},
										scope : addKeywordCmbForm,
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
				}]
			});
	// 添加新关键字
	var addKeywordWindow = new Ext.Window({
				title : '<span class="commoncss">新建关键字</span>', // 窗口标题
				id : 'addKeywordWindow',
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
				items : [addKeywordCmbForm], // 嵌入的表单面板
				buttons : [{ // 窗口底部按钮配置
					text : '确定', // 按钮文本
					iconCls : 'tbar_synchronizeIcon', // 按钮图标
					handler : function() { // 按钮响应函数
						var addForm = addKeywordCmbForm.getForm();
						// 分组类型
						var gtype = gtypeCombo.getValue();
						// 网站类型
						var websiteid = websiteCombo.getValue();
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
									scope : addKeywordCmbForm,
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
						addKeywordWindow.hide();
					}
				}, {	// 窗口底部按钮配置
							text : '重置', // 按钮文本
							iconCls : 'tbar_synchronizeIcon', // 按钮图标
							handler : function() { // 按钮响应函数
								addKeywordCmbForm.form.reset();
							}
						}, {
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								addKeywordWindow.hide();
							}
						}]
			});
	// 定义表格数据源
	var keywordStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../keyword/keywordgrid'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'keywordId'
								}, {
									name : 'keywordName'
								}, {
									name : 'categoryId'
								}, {
									name : 'categoryName'
								}, {
									name : 'tag'
								}, {
									name : 'createTime'
								}, {
									name : 'description'
								}]),
				autoLoad : true
			});

	// 定义Checkbox
	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true,
				listeners : {
	// 选择事件
				// rowselect : function(model, rowIndex, record) {
				// var resultId = record.get('id');
				// taskDtlStore.setBaseParam('resultId', resultId);
				// taskDtlStore.reload();
				// }
				}
			});
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'NO.',
				locked : true,
				width : 28
			});

	// 定义锁定列模型
	var cm = new Ext.grid.ColumnModel([sm, rownum, {
				header : '编号',
				dataIndex : 'id',
				// locked : true,
				width : 80
			}, {
				header : '关键字',
				dataIndex : 'name',
				// locked : true,
				width : 100
			}, {
				header : '分类ID',
				dataIndex : 'categoryId',
				// locked : true,
				width : 100
			}, {
				header : '分类名称',
				dataIndex : 'categoryName',
				renderer : renderRed,
				width : 100
			}, {
				header : '分类说明',
				dataIndex : 'description',
				width : 100
			}, {
				header : '是否手动',
				dataIndex : 'tag',
				renderer : renderRed,
				width : 100
			}, {
				header : '创建时间',
				dataIndex : 'createTime',
				renderer : renderRed,
				width : 100
			}]);
	// // 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : keywordStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext]
			});

	// 定义grid表格
	var keywordGrid = new Ext.grid.GridPanel({
		region : 'center',
		id : 'keywordGrid',
		height : 500,
		stripeRows : true, // 斑马线
		frame : true,
		// autoWidth : true,
		autoScroll : true,
		store : keywordStore,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		bbar : bbar,
		sm : sm,
		colModel : cm,
		tbar : [{
					text : '新增',
					iconCls : 'comments_addIcon',
					handler : function() {
						addKeywordWindow.show();
					}
				}, {
					text : '刷新',
					iconCls : 'arrow_refreshIcon',
					handler : function() {
						keywordStore.reload();
					}
				}]
			// ,
			// onCellClick : function(grid, rowIndex, columnIndex, e) {
			// var selesm = grid.getSelectionModel().getSelections();
			// // var userid = selesm[0].data.id;
			// var cont = selesm[0].data.content;
			// var contentcol = grid.getColumnModel()
			// .getDataIndex(columnIndex);
			// if (contentcol == 'showdtl') {
			// }
			// }
		});

	// 注册事件
	// keywordGrid.on('cellclick', keywordGrid.onCellClick, taskGrid);

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [keywordQueryForm, keywordGrid]
			});
});
