Ext.onReady(function() {
	// 保存分类ID的隐藏域
	var categoryIdHidden = new Ext.form.Hidden({
				name : 'categoryIdHidden'
			});
	// 推拽
	var ddrowCagegoryTagGrid;
	var ddrowCagegoryNotTagGrid;
	// 分类的store
	var categoryStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../category/categorygrid'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'name'
								}, {
									name : 'description'
								}]),
				autoLoad : true
			});
	// 分类表格页数
	var categoryGridNum = 20;
	var categoryGridText = new Ext.form.TextField({
				id : 'categoryGridText',
				name : 'categoryGridText',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							categoryGridBbar.pageSize = Number(categoryGridText
									.getValue());
							categoryGridNum = Number(categoryGridText
									.getValue());
							categoryStore.reload({
										params : {
											start : 0,
											limit : categoryGridBbar.pageSize
										}
									});
						}
					}
				}
			});
	// 分类查询的form
	var categoryQueryForm = new Ext.form.FormPanel({
				region : 'north',
				title : "筛选条件",
				// collapsible : true,
				frame : true,
				id : 'categoryQueryForm',
				// border : true,
				labelWidth : 90, // 标签宽度
				frame : true,
				labelAlign : 'right',
				bodyStyle : 'padding:5 5 5 5',
				buttonAlign : 'center',
				height : 100,
				items : [{
							layout : "column",
							items : [{
										columnWidth : .3,
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
													fieldLabel : "分类ID",
													name : 'categoryId'
												}]
									}]
						}],
				buttonAlign : "center",
				buttons : [{
					text : "查询",
					handler : function() { // 按钮响应函数
						var tform = categoryQueryForm.getForm();
						var categoryName = tform.findField("categoryName")
								.getValue();
						var categoryId = tform.findField("categoryId")
								.getValue();
						categoryStore
								.setBaseParam('categoryName', categoryName);
						categoryStore.setBaseParam('categoryId', categoryId);
						categoryStore.load();
					}
				}, {
					text : "重置",
					handler : function() { // 按钮响应函数
						categoryQueryForm.form.reset();
					}
				}]
			});
	// 定义Checkbox
	var categoryGridsm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : false
			});
	// 定义自动当前页行号
	var categoryGridRownum = new Ext.grid.RowNumberer({
				header : 'NO.',
				locked : true,
				width : 28
			});
	// RowEditor
	var categoryGridEditor = new Ext.ux.grid.RowEditor({
				saveText : '更新',
				cancelText : '取消',
				clicksToEdit : 2
			});
	// 增加编辑事件
	categoryGridEditor.on({
				scope : this,
				afteredit : function(roweditor, changes, record, rowIndex) {
					var categoryId = record.id;
					var categoryName = changes.name;
					var description = changes.description;
					Ext.Ajax.request({
								url : '../category/updatecategory?_time='
										+ new Date().getTime(),
								params : {
									'id' : categoryId,
									'name' : categoryName,
									'description' : description
								},
								scope : this,
								success : function(response) {
									// 页面提交
									record.commit();
									return;
								},
								failure : function() {
									Ext.Msg.alert("提示", "更新失败");
									// 还原数据
									record.reject();
									return;
								}
							});
				}
			});

	// 定义锁定列模型
	var categoryGridcm = new Ext.grid.ColumnModel([
			// categoryGridsm,
			categoryGridRownum, {
				header : '编号',
				dataIndex : 'id',
				width : 80
			}, {
				header : '关键字',
				dataIndex : 'name',
				editor : {
					xtype : 'textfield',
					allowBlank : false
				},
				width : 100
			}, {
				header : '分类说明',
				dataIndex : 'description',
				editor : {
					xtype : 'textfield'
				},
				width : 100
			}, {
				header : '添加关键字',
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='添加'/>";
					return returnStr;
				},
				width : 100
			}]);
	// 分页菜单
	var categoryGridBbar = new Ext.PagingToolbar({
				pageSize : categoryGridNum,
				store : categoryStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', categoryGridText]
			});

	// 分类列表
	var categoryGrid = new Ext.grid.GridPanel({
		height : 500,
		autoWidth : true,
		autoScroll : true,
		split : true,
		plugins : [categoryGridEditor],
		region : 'center',
		store : categoryStore,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		// stripeRows : true,
		frame : true,
		// autoExpandColumn : 'remark',
		// sm : categoryGridsm,
		cm : categoryGridcm,
		tbar : [{
					text : '新增',
					iconCls : 'page_addIcon',
					handler : function() {
						addCategoryCmbForm.form.reset();
						addCategoryWindow.show();
					}
				}, '-', {
					text : '刷新',
					iconCls : 'arrow_refreshIcon',
					handler : function() {
						categoryStore.load();
					}
				}, '-', {
					ref : '../removeBtn',
					iconCls : 'deleteIcon',
					text : '删除分类',
					disabled : true,
					handler : function() {
						categoryGridEditor.stopEditing();
						var s = categoryGrid.getSelectionModel()
								.getSelections();
						var ids = [];
						for (var i = 0, r; r = s[i]; i++) {
							var cid = r.data.id;
							ids[i] = cid;
						}
						if (ids.length == 0) {
							Ext.MessageBox.alert('提示', '至少选择一行记录');
							return;
						}
						Ext.Msg.show({
							title : '提示',
							msg : '确认删除？',
							buttons : Ext.Msg.YESNO,
							fn : function(ans) {
								if (ans == 'yes') {
									Ext.Ajax.request({
										url : '../category/deletecategory?_time='
												+ new Date().getTime(),
										params : {
											'ids' : ids
										},
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											Ext.MessageBox.alert('提示', '成功删除：'
															+ result + '条记录');
											categoryStore.reload();
											return;
										},
										failure : function() {
											Ext.MessageBox.alert('提示', '删除失败');
											return;
										}
									});
								}
							}
						});
					}
				}],
		bbar : categoryGridBbar,
		onCellClick : function(grid, rowIndex, columnIndex, e) {
			var buttons = e.target.defaultValue;
			var record = grid.getStore().getAt(rowIndex);
			var data = record.data;
			// 找出表格中‘配置’按钮
			if (buttons == '添加') {
				var cgid = data.id;
				categoryIdHidden.setValue(cgid);
				addTagWindow.show();
				cagegoryTagStore.load();
				cagegoryNotTagStore.load();
			}
		}
	});
	// 注册事件
	categoryGrid.on('cellclick', categoryGrid.onCellClick, categoryGrid);
	categoryGrid.getSelectionModel().on('selectionchange', function(sm) {
				categoryGrid.removeBtn.setDisabled(sm.getCount() < 1);
			});
	// 嵌入的FORM
	var addCategoryCmbForm = new Ext.form.FormPanel({
		id : 'addCategoryCmbForm',
		name : 'addCategoryCmbForm',
		labelWidth : 90, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		defaultType : 'textfield', // 表单元素默认类型
		labelAlign : 'right', // 标签对齐方式
		bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
		items : [{
					xtype : "displayfield",
					fieldLabel : "分类ID",
					id : 'cgId',
					anchor : '100%'
				}, {
					fieldLabel : "分类名称",
					name : 'name',
					allowBlank : false,
					anchor : '100%',
					listeners : {
						'blur' : function(foc) {
							var categoryName = foc.getValue();
							if (Ext.isEmpty(categoryName)) {
								Ext.Msg.alert("提示", "请填写分类名");
								return;
							}
							var tform = addCategoryCmbForm.getForm();
							Ext.Ajax.request({
										url : '../category/checkname',
										params : {
											'categoryName' : categoryName
										},
										scope : addCategoryCmbForm,
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											if (result.isPresence) {
												Ext.Msg.alert("提示",
														"已存在相同分类名,请重新输入");
												tform.findField('name')
														.setValue("");
											}
											return;
										},
										failure : function() {
											Ext.Msg.alert("提示", "分类名检查失败");
											return;
										}
									});
						}
					}
				}, {
					xtype : "textarea",
					fieldLabel : "分类说明",
					name : 'description',
					anchor : '100%'
				}]
	});
	// 添加分类弹出窗口
	var addCategoryWindow = new Ext.Window({
				title : '<span class="commoncss">新建分类</span>', // 窗口标题
				id : 'addCategoryWindow',
				closeAction : 'hide',
				layout : 'fit', // 设置窗口布局模式
				width : 360, // 窗口宽度
				height : 220, // 窗口高度
				// closable : true, // 是否可关闭
				collapsible : true, // 是否可收缩
				maximizable : true, // 设置是否可以最大化
				border : false, // 边框线设置
				constrain : true, // 设置窗口是否可以溢出父容器
				pageY : 20, // 页面定位Y坐标
				pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
				items : [addCategoryCmbForm], // 嵌入的表单面板
				buttons : [{ // 窗口底部按钮配置
					text : '确定', // 按钮文本
					iconCls : 'tbar_synchronizeIcon', // 按钮图标
					handler : addCategory
				}, {	// 窗口底部按钮配置
							text : '重置', // 按钮文本
							iconCls : 'tbar_synchronizeIcon', // 按钮图标
							handler : function() { // 按钮响应函数
								addCategoryCmbForm.form.reset();
							}
						}, {
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								addCategoryWindow.hide();
							}
						}]
			});
	// 与分类关联的grid相关组件
	var cagegoryTagStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../category/categorytags'
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
								}, {
									name : 'fromGrid'
								}])
			});
	cagegoryTagStore.on('beforeload', function() {
				this.baseParams = {
					categoryId : categoryIdHidden.getValue()
				};
			});
	var cagegoryTagsm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : false
			});
	var cagegoryTagcm = new Ext.grid.ColumnModel([cagegoryTagsm, {
				header : '关键字编号',
				dataIndex : 'keywordId',
				width : 80
			}, {
				header : '关键字',
				dataIndex : 'keywordName',
				width : 100
			}, {
				header : '分类ID',
				dataIndex : 'categoryId',
				hidden : true,
				width : 10
			}, {
				header : '是否手动',
				dataIndex : 'tag',
				renderer : rendTrueFalse,
				width : 80
			}, {
				header : '创建时间',
				dataIndex : 'createTime',
				renderer : renderDateHis,
				width : 80
			}, {
				header : '说明',
				dataIndex : 'description',
				renderer : renderBrief,
				width : 100
			}, {
				header : '添加状态',
				dataIndex : 'addstat',
				renderer : renderAddStatus,
				width : 100
			}, {
				header : '原始表格',
				dataIndex : 'fromGrid',
				width : 100
			}]);
	var cagegoryTagnumber = 20;
	var cagegoryTagnumtext = new Ext.form.TextField({
		id : 'cagegoryTagnumtext',
		width : 60,
		emptyText : '每页条数',
		// 激活键盘事件
		enableKeyEvents : true,
		listeners : {
			specialKey : function(field, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
					cagegoryTagbbar.pageSize = parseInt(cagegoryTagnumber
							.getValue());
					cagegoryTagnumber = parseInt(cagegoryTagnumtext.getValue());
					cagegoryTagStore.reload({
								params : {
									start : 0,
									limit : cagegoryTagbbar.pageSize
								}
							});
				}
			}
		}
	});
	var cagegoryTagbbar = new Ext.PagingToolbar({
				pageSize : cagegoryTagnumber,
				store : cagegoryTagStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', cagegoryTagnumtext]
			});

	var cagegoryTagGrid = new Ext.grid.GridPanel({
				ddGroup : 'cagegoryNotTagGridGroup',
				id : 'cagegoryTagGrid',
				region : 'center',
				autoScroll : true,
				split : true,
				store : cagegoryTagStore,
				enableDragDrop : true,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				// stripeRows : true,
				frame : true,
				// autoExpandColumn : 'remark',
				sm : cagegoryTagsm,
				cm : cagegoryTagcm,
				tbar : [{
							text : '提交',
							iconCls : 'page_delIcon',
							handler : submitCategoryTags
						}, '-', {
							text : '刷新/还原',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								cagegoryTagStore.setBaseParam('categoryId',
										categoryIdHidden.getValue());
								cagegoryTagStore.load();
								cagegoryNotTagStore.load();
							}
						}, '-', new Ext.form.TextField({
									id : 'queryCategoryTagsForm',
									name : 'queryCategoryTagsForm',
									emptyText : '请输入关键字',
									enableKeyEvents : true,
									listeners : {
										specialkey : function(field, e) {
											var keyName = Ext
													.getCmp('queryCategoryTagsForm')
													.getValue();
											if (e.getKey() == Ext.EventObject.ENTER) {
												queryKeywordByName(
														cagegoryTagStore,
														keyName);
											}
										}
									},
									width : 130
								}), {
							text : '查询',
							iconCls : 'previewIcon',
							handler : function() {
								var keyName = Ext
										.getCmp('queryCategoryTagsForm')
										.getValue();
								queryKeywordByName(cagegoryTagStore, keyName);
							}
						}],
				bbar : cagegoryTagbbar
			});

	// 待筛选的grid相关组件
	var cagegoryNotTagStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../category/notcategorytags'
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
								}, {
									name : 'fromGrid'
								}])
			});
	cagegoryNotTagStore.on('beforeload', function() {
				this.baseParams = {
					categoryId : categoryIdHidden.getValue()
				};
			});
	var cagegoryNotTagsm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : false
			});
	var cagegoryNotTagcm = new Ext.grid.ColumnModel([cagegoryNotTagsm, {
				header : '关键字编号',
				dataIndex : 'keywordId',
				width : 80
			}, {
				header : '关键字',
				dataIndex : 'keywordName',
				width : 100
			}, {
				header : '分类ID',
				dataIndex : 'categoryId',
				hidden : true,
				width : 10
			}, {
				header : '是否手动',
				dataIndex : 'tag',
				renderer : rendTrueFalse,
				width : 80
			}, {
				header : '创建时间',
				dataIndex : 'createTime',
				renderer : renderDateHis,
				width : 80
			}, {
				header : '说明',
				dataIndex : 'description',
				renderer : renderBrief,
				width : 100
			}, {
				header : '添加状态',
				dataIndex : 'addstat',
				renderer : renderAddStatus,
				width : 100
			}, {
				header : '原始表格',
				dataIndex : 'fromGrid',
				width : 100
			}]);
	var cagegoryNotTagnumber = 20;
	var cagegoryNotTagnumtext = new Ext.form.TextField({
				id : 'cagegoryNotTagnumtext',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							cagegoryNotTagbbar.pageSize = parseInt(cagegoryNotTagnumber
									.getValue());
							cagegoryNotTagnumber = parseInt(cagegoryNotTagnumtext
									.getValue());
							cagegoryNotTagStore.reload({
										params : {
											start : 0,
											limit : cagegoryNotTagbbar.pageSize
										}
									});
						}
					}
				}
			});
	var cagegoryNotTagbbar = new Ext.PagingToolbar({
				pageSize : cagegoryNotTagnumber,
				store : cagegoryNotTagStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', cagegoryNotTagnumtext]
			});

	var cagegoryNotTagGrid = new Ext.grid.GridPanel({
		ddGroup : 'cagegoryTagGridGroup',
		region : 'center',
		id : 'cagegoryNotTagGrid',
		autoScroll : true,
		split : true,
		store : cagegoryNotTagStore,
		enableDragDrop : true,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		// stripeRows : true,
		frame : true,
		// autoExpandColumn : 'remark',
		sm : cagegoryNotTagsm,
		cm : cagegoryNotTagcm,
		tbar : [{
			text : '刷新/还原',
			iconCls : 'arrow_refreshIcon',
			handler : function() {
				cagegoryNotTagStore.setBaseParam('categoryId', categoryIdHidden
								.getValue());
				cagegoryTagStore.load();
				cagegoryNotTagStore.load();
			}
		}, '-', new Ext.form.TextField({
					id : 'queryCategoryNotTagForm',
					name : 'queryCategoryNotTagForm',
					emptyText : '请输入关键字',
					enableKeyEvents : true,
					listeners : {
						specialkey : function(field, e) {
							var keyName = Ext.getCmp('queryCategoryNotTagForm')
									.getValue();
							if (e.getKey() == Ext.EventObject.ENTER) {
								queryKeywordByName(cagegoryNotTagStore, keyName);
							}
						}
					},
					width : 130
				}), {
			text : '查询',
			iconCls : 'previewIcon',
			handler : function() {
				var keyName = Ext.getCmp('queryCategoryNotTagForm').getValue();
				queryKeywordByName(cagegoryNotTagStore, keyName);
			}
		}],
		bbar : cagegoryNotTagbbar
	});

	var addTagWindow = new Ext.Window({
				layout : 'border',
				width : document.documentElement.clientWidth - 200,
				height : document.documentElement.clientHeight - 200,
				resizable : true,
				draggable : true,
				closeAction : 'hide',
				title : '<span class="commoncss">分类标签配置</span>',
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
							width : 700,
							layout : 'border',
							items : [cagegoryTagGrid]
						}, {
							region : 'center',
							split : true,
							layout : 'border',
							items : [cagegoryNotTagGrid]
						}],
				buttons : [{
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								addTagWindow.hide();
							}
						}]
			});

	/**
	 * show事件做拖放的初始化动作 因为表格组件没有renderTo,所以必须在窗口弹出后Grid组件加载完毕才能获取CMP
	 */
	addTagWindow.on('show', function() {
		// 获取组件
		var cagegoryTagGridCmp = Ext.getCmp('cagegoryTagGrid');
		var cagegoryNotTagGridCmp = Ext.getCmp('cagegoryNotTagGrid');
		// 拖放相关操作
		ddrowCagegoryTagGrid = new Ext.dd.DropTarget(
				cagegoryTagGridCmp.container, {
					// 用于控制拖动的范围，只能拖动同组的数据
					ddGroup : 'cagegoryTagGridGroup',
					copy : false,
					notifyDrop : function(dd, e, data) {
						var rows = data.selections;
						var index = dd.getDragData(e).rowIndex;
						if (typeof(index) == 'undefined') {
							index = 0;
						}
						for (i = 0; i < rows.length; i++) {
							var rowData = rows[i];
							if (!this.copy) {
								cagegoryNotTagStore.remove(rowData);
							}
							cagegoryTagStore.insert(index, rowData);
							setAddRows(rowData, rowData.data.addstat,
									rowData.data.fromGrid, 'cagegoryTagGrid');
						}
					}
				});
		ddrowCagegoryNotTagGrid = new Ext.dd.DropTarget(
				cagegoryNotTagGridCmp.container, {
					ddGroup : 'cagegoryNotTagGridGroup',
					copy : false,
					notifyDrop : function(dd, e, data) {
						var rows = data.selections;
						var index = dd.getDragData(e).rowIndex;
						if (typeof(index) == 'undefined') {
							index = 0;
						}
						for (i = 0; i < rows.length; i++) {
							var rowData = rows[i];
							if (!this.copy) {
								cagegoryTagStore.remove(rowData);
							}
							cagegoryNotTagStore.insert(index, rowData);
							setAddRows(rowData, rowData.data.addstat,
									rowData.data.fromGrid, 'cagegoryNotTagGrid');
						}
					}
				});
	});

	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [categoryQueryForm, categoryGrid]
			});
	/**
	 * 设置拖拽后数据的状态
	 */
	function setAddRows(rowData, addstat, fromGrid, toGrid) {
		if (Ext.isEmpty(addstat)) {
			rowData.data.addstat = true;
		} else {
			if (fromGrid != toGrid) {
				rowData.data.addstat = '新增记录';
			} else {
				rowData.data.addstat = '';
			}
		}
		rowData.commit();
	}
	/**
	 * 提交更改的数据
	 */
	function submitCategoryTags() {
		var newCategoryId = categoryIdHidden.getValue();
		if (Ext.isEmpty(newCategoryId)) {
			Ext.MessageBox.alert('提示', '分类ID为空，不能提交数据');
			return;
		} else {
			// 待更新的数据
			var submitData = [];
			// 添加的新关联关系
			var gridData = cagegoryTagGrid.store.data.items;
			// 从cagegoryTagGrid中删除的数据，也需一起提交
			var deleteGridData = cagegoryNotTagGrid.store.data.items;
			var newKeywordData = createSubmitData(gridData, newCategoryId);
			// 取消关联的关键字，categoryId为null
			var delKeywordData = createSubmitData(deleteGridData, null);
			// 合并数组
			submitData = newKeywordData.concat(delKeywordData);
			var submitDataLen = submitData.length;
			if (submitDataLen > 0) {
				Ext.Msg.show({
					title : '提示',
					msg : '确认提交？',
					buttons : Ext.Msg.YESNO,
					fn : function(ans) {
						if (ans == 'yes') {
							Ext.Ajax.request({
										url : '../category/updaterela?_time='
												+ new Date().getTime(),
										params : {
											'param' : Ext.encode(submitData)
										},
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											Ext.MessageBox.alert('提示',
													'操作已提交，请稍后查看执行结果');
											return;
										},
										failure : function() {
											Ext.MessageBox.alert('提示', '执行失败');
											return;
										}
									});
						}
					}
				});
			} else {
				Ext.MessageBox.alert('提示', '没有可以提交的数据');
				return;
			}
		}
	}

	function getObj(array) {
		for (var i = 0; i < array.length; i++) {
			var u = array[i];

		}
	}
	/**
	 * 构造待提交的数据
	 */
	function createSubmitData(gridData, newCategoryId) {
		var submitData = [];
		var dataLen = gridData.length;
		// 先处理需要新增的关系
		if (dataLen > 0) {
			for (var i = 0; i < dataLen; i++) {
				var row = gridData[i].data;
				// 新增数据标识符
				var newtag = row.addstat;
				if (newtag) {
					var UpdateUserTagParam = {};
					var oldCategoryId = row.categoryId;
					var keywordId = row.keywordId;
					UpdateUserTagParam['oldCategoryId'] = oldCategoryId;
					UpdateUserTagParam['newCategoryId'] = newCategoryId;
					UpdateUserTagParam['keywordId'] = keywordId;
					submitData.push(UpdateUserTagParam);
				}
			}
		}
		return submitData;
	}

	/**
	 * 按关键字检索数据
	 */
	function queryKeywordByName(store, keyName) {
		var categoryId = categoryIdHidden.getValue();
		store.setBaseParam('categoryId', categoryId);
		store.setBaseParam('keywordName', keyName);
		store.load({
					params : {
						categoryId : categoryId,
						keywordName : keyName
					}
				});
	}

	/**
	 * 新增分类
	 */
	function addCategory() {
		var category = getCategory();
		maintainCategory(category.id, category.name, category.description);
	}

	/**
	 * 获取一个分类的实例
	 */
	function getCategory() {
		var category = {};
		var addForm = addCategoryCmbForm.getForm();
		// 分类ID
		var categoryId = Ext.getCmp('cgId').getValue();
		// 分类名称
		var categoryName = addForm.findField('name').getValue();
		// 分类说明
		var description = addForm.findField('description').getValue();
		category['id'] = categoryId;
		category['name'] = categoryName;
		category['description'] = description;
		return category;
	}

	/**
	 * 维护分类
	 */
	function maintainCategory(id, name, desc) {
		Ext.Ajax.request({
					url : '../category/addcategory',
					params : {
						'id' : id,
						'name' : name,
						'description' : desc
					},
					scope : addCategoryCmbForm,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						if (result.success) {
							Ext.MessageBox.alert("提示", "创建成功");
						} else {
							Ext.Msg.alert("提示", "分类创建失败");
						}
						categoryStore.load();
						return;
					},
					failure : function() {
						Ext.Msg.alert("提示", "分类创建失败");
						categoryStore.load();
						return;
					}
				});
		addCategoryWindow.hide();
	}
});