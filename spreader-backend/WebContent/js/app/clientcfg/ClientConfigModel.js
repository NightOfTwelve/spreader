Ext.onReady(function() {
	var colsHidden = new Ext.form.Hidden({
				name : 'colsHidden'
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
							sinaUserStore.reload({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
						}
					}
				}
			});
	var addwin;
	// 属性编辑窗口
	var propWin;
	// 编辑器组件
	var editorWin;
	// 定义表格数据源
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/spreader-front/clientcfg/cfgs?_time='
									+ new Date().getTime()
						}),
				autoLoad : true,
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'clientId'
								}, {
									name : 'configName'
								}, {
									name : 'configType'
								}, {
									name : 'clientConfig'
								}, {
									name : 'type'
								}])
			});
	var cfgcm = new Ext.grid.ColumnModel([{
				header : '编号',
				dataIndex : 'id',
				width : 80
			}, {
				header : '客户端编号',
				dataIndex : 'clientId',
				width : 80
			}, {
				header : '客户端类型',
				dataIndex : 'type',
				width : 80
			}, {
				header : '配置名称',
				dataIndex : 'configName',
				width : 120
			}, {
				header : '配置类型',
				dataIndex : 'configType',
				renderer : renderEditorType,
				width : 120
			}, {
				header : '配置信息',
				dataIndex : 'clientConfig',
				width : 350
			}, {
				header : '属性配置',
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='编辑'/>";
					return returnStr;
				},
				width : 100
			}]);
	// // 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext]
			});
	var configTypes = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [[1, '普通文本模式'], [2, '属性表格模式'], [3, '多行表格模式']]
			});
	var clientTypes = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [[0, '客户端'], [1, '客户端组']]
			});
	var selectConfigTypes = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [[1, '普通文本模式'], [2, '属性表格模式'], [3, '多行表格模式']]
			});

	var recordType = Ext.data.Record.create([]);
	var multiGridStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/spreader-front/clientcfg/multicfg?_time='
									+ new Date().getTime(),
							timeout : 20000
						}),
				// 隐含创建reader
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, recordType // 记录的类型
				)
			});
	var textForm = new Ext.FormPanel();
	var copyWin;
	var copyTextForm = new Ext.FormPanel({
				labelWidth : 75,
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				items : [{
							fieldLabel : '配置信息',
							xtype : 'textarea',
							name : 'copycfg',
							id : 'copycfg',
							width : 460,
							height : 300,
							autoScroll : true
						}],
				buttons : [{
							text : '保存',
							handler : function() {
								var tform = copyTextForm.getForm();
								var copyText = tform.findField('copycfg')
										.getValue();
								text2Grid(copyText);
								copyWin.hide();
							}
						}, {
							text : "重置",
							handler : function() { // 按钮响应函数
								copyTextForm.form.reset();
							}
						}]
			});
	var addForm = new Ext.FormPanel({
				labelWidth : 75,
				frame : true,
				bodyStyle : 'padding:5px 5px 0',
				defaultType : 'textfield',
				// width : 350,
				items : [{
							fieldLabel : '客户端ID',
							name : 'clientId',
							allowBlank : false
						}, {
							fieldLabel : '配置名称',
							allowBlank : false,
							name : 'configName'
						}, {
							fieldLabel : '编辑类型',
							xtype : 'combo',
							store : configTypes,
							id : 'configType',
							name : 'configType',
							hiddenName : 'configType',
							valueField : 'ID',
							editable : false,
							displayField : 'NAME',
							mode : 'local',
							forceSelection : false,// 必须选择一项
							emptyText : '...',// 默认值
							triggerAction : 'all'
						}, {
							fieldLabel : '客户端/组',
							xtype : 'combo',
							store : clientTypes,
							id : 'clientType',
							name : 'clientType',
							hiddenName : 'clientType',
							valueField : 'ID',
							editable : false,
							displayField : 'NAME',
							mode : 'local',
							forceSelection : false,// 必须选择一项
							emptyText : '...',// 默认值
							triggerAction : 'all'
						}],
				buttons : [{
					text : '保存',
					handler : function() {
						var tform = addForm.getForm();
						var clientId = tform.findField('clientId').getValue();
						var configName = tform.findField('configName')
								.getValue();
						var configType = tform.findField('configType')
								.getValue();
						var clientType = tform.findField('clientType')
								.getValue();
						if (Ext.isEmpty(clientId)) {
							Ext.Msg.alert("提示", "请输入客户端编号");
							return;
						}
						if (Ext.isEmpty(configName)) {
							Ext.Msg.alert("提示", "请输入配置名称");
							return;
						}
						if (Ext.isEmpty(configType)) {
							Ext.Msg.alert("提示", "请输入配置类型");
							return;
						}
						if (Ext.isEmpty(clientType)) {
							Ext.Msg.alert("提示", "请输入分组类型");
							return;
						}
						Ext.Ajax.request({
									url : '/spreader-front/clientcfg/savecfg?_time='
											+ new Date().getTime(),
									async : false,
									params : {
										'clientId' : clientId,
										'configName' : configName,
										'configType' : configType,
										'clientType' : clientType
									},
									success : function(response, opts) {
										var result = Ext.util.JSON
												.decode(response.responseText);
										Ext.MessageBox.alert('提示', '保存成功');
										store.load();
									},
									failure : function(response, opts) {
										Ext.MessageBox.alert('提示', '保存失败');
									}
								});
						addwin.hide();
					}
				}, {
					text : "重置",
					handler : function() { // 按钮响应函数
						addForm.form.reset();
					}
				}]
			});

	// 定义grid表格
	var cfgGrid = new Ext.grid.GridPanel({
				region : 'center',
				id : 'cfgGrid',
				// height : 440,
				stripeRows : true, // 斑马线
				frame : true,
				// autoScroll : true,
				store : store,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				bbar : bbar,
				sm : new Ext.grid.CheckboxSelectionModel({
							singleSelect : true
						}),
				cm : cfgcm,
				tbar : [{
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								store.load();
							}
						}, '-', new Ext.form.TextField({
									id : 'clientId',
									name : 'clientId',
									fieldLabel : '客户端编号',
									emptyText : '请输入客户端编号',
									allowBlank : true,
									enableKeyEvents : true,
									width : 130
								}), '-', {
							text : '查询记录',
							iconCls : 'page_findIcon',
							handler : function() {
								var clientId = Ext.getCmp('clientId')
										.getValue();
								if (Ext.isEmpty(clientId)) {
									Ext.Msg.alert("提示", "请输入要搜索的客户端编号");
									return;
								}
								store.setBaseParam('clientId', clientId);
								store.load();
							}
						}, '-', {
							text : '新增配置',
							iconCls : 'uploadIcon',
							handler : function() {
								if (!addwin) {
									addwin = new Ext.Window({
												title : '新增配置',
												layout : 'fit',
												width : 300,
												height : 300,
												closeAction : 'hide',
												plain : true,
												items : [addForm]
											});
									addwin.on('show', function() {
												addForm.form.reset();
											});
								}
								addwin.show(this);
							}
						}],
				onCellClick : function(grid, rowIndex, columnIndex, e) {
					var butname = e.target.defaultValue;
					var selesm = grid.getSelectionModel().getSelections();
					var configId = selesm[0].data.id;
					var clientId = selesm[0].data.clientId;
					var cfg = selesm[0].data.clientConfig;
					var configName = selesm[0].data.configName;
					var configType = selesm[0].data.configType;
					var clientType = selesm[0].data.type;
					var cardPanelCmp = Ext.getCmp('cardPanel');
					if (butname == '编辑') {
						if (configType == 1) {
							createTextModel(cardPanelCmp, cfg);
						}
						if (configType == 2) {
							createPropertyModel(cardPanelCmp, cfg, configId,
									clientId);
						}
						if (configType == 3) {
							createMultiGridModel(cardPanelCmp, configId);
						}
						var cardPanelLayout = cardPanelCmp.layout;
						if (Ext.isEmpty(cardPanelLayout.id)) {
							cardPanelCmp.activeItem = 0;
						} else {
							cardPanelLayout.setActiveItem(0);
						}
						editorCmp(configId, clientId, configName, configType,
								clientType);
					}
				}
			});
	// 注册点击事件
	cfgGrid.on('cellclick', cfgGrid.onCellClick, cfgGrid);

	var cardPanel = new Ext.Panel({
				region : 'center',
				id : 'cardPanel',
				name : 'cardPanel',
				layout : 'card',
				split : true,
				activeItem : 0,
				items : [textForm],
				listeners : {
					afterlayout : function(t, lay) {
					}
				}
			});
	function checkExistsCol(colarr, col) {
		for (var i = 0; i < colarr.length; i++) {
			var idx = colarr[i].dataIndex;
			if (idx == col) {
				return true;
			}
		}
		return false;
	}
	var infoForm = new Ext.FormPanel({
		labelAlign : 'right',
		frame : true,
		region : 'north',
		bodyStyle : 'padding:5px 5px 0',
		height : 100,
		items : [{
			layout : 'column',
			items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : 'ID',
									id : 'id',
									name : 'id',
									readOnly : true,
									anchor : '95%'
								}, {
									xtype : 'textfield',
									fieldLabel : '客户端ID',
									readOnly : true,
									id : 'clientId',
									name : 'clientId',
									anchor : '95%'
								}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '配置名称',
									id : 'configName',
									name : 'configName',
									anchor : '95%'
								}, {
									fieldLabel : '编辑类型',
									xtype : 'combo',
									store : selectConfigTypes,
									id : 'selectConfigType',
									name : 'selectConfigType',
									hiddenName : 'configType',
									valueField : 'ID',
									editable : false,
									displayField : 'NAME',
									mode : 'local',
									forceSelection : false,// 必须选择一项
									emptyText : '...',// 默认值
									triggerAction : 'all',
									listeners : {
										select : function(combo, record, index) {
											var configType = combo.getValue();
											var cardPanelCmp = Ext
													.getCmp('cardPanel');
											var tform = infoForm.getForm();
											var cfgId = tform.findField("id")
													.getValue();
											var clientId = tform
													.findField("clientId")
													.getValue();
											if (configType == 1) {
												createTextModel(cardPanelCmp,
														null);
											}
											if (configType == 2) {
												createPropertyModel(
														cardPanelCmp, "{}",
														cfgId, clientId);
											}
											if (configType == 3) {
												createMultiGridModel(
														cardPanelCmp, cfgId);
											}
											var cardPanelLayout = cardPanelCmp.layout;
											cardPanelLayout.setActiveItem(0);
										}
									}
								}, {
									fieldLabel : '客户端/组',
									xtype : 'combo',
									store : clientTypes,
									id : 'selectClientType',
									name : 'selectClientType',
									hiddenName : 'clientType',
									valueField : 'ID',
									editable : false,
									displayField : 'NAME',
									mode : 'local',
									forceSelection : false,// 必须选择一项
									emptyText : '...',// 默认值
									triggerAction : 'all'
								}]
					}]
		}]
	});
	function editorCmp(configId, clientId, configName, configType, clientType) {
		if (!editorWin) {
			// 组件存在，先销毁
			// editorWin.destroy();
			// var cardPanelCmp = Ext.getCmp('cardPanel').layout;
			// cardPanelCmp.setActiveItem(configType - 1);
			editorWin = new Ext.Window({
						title : '客户端配置编辑器',
						layout : 'border',
						width : 600,
						height : 450,
						closeAction : 'hide',
						plain : true,
						items : [infoForm, cardPanel]
					});

		}
		editorWin.on('show', function() {
					var tform = infoForm.getForm();
					tform.findField("id").setValue(configId);
					tform.findField("clientId").setValue(clientId);
					tform.findField("configName").setValue(configName);
					tform.findField("selectConfigType").setValue(configType);
					tform.findField("selectClientType").setValue(clientType);
				});
		editorWin.show(this);
	}

	/**
	 * 提交修改的配置
	 */
	function submitGrid(id, clientId, configName, configType, configs,
			clientType) {
		var success = false;
		Ext.Ajax.request({
					url : '/spreader-front/clientcfg/savecfg?_time='
							+ new Date().getTime(),
					params : {
						'id' : id,
						'clientId' : clientId,
						'configName' : configName,
						'configType' : configType,
						'cfg' : configs,
						'clientType' : clientType
					},
					async : false,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						success = result.success;
					},
					failure : function() {
					}
				});
		return success;
	}
	// 构造动态表格数据的index
	function createMultiGridReader(idxArray) {
		var idx = new Array();
		for (var i = 0; i < idxArray.length; i++) {
			idx[i] = {
				name : idxArray[i]
			}
		}
		var reader = new Ext.data.JsonReader({
					root : 'list'
				}, idx);
		return reader;
	}
	// 构造动态表格列
	function createMultiGridColumns(idxArray) {
		var columnArray = new Array();
		for (var i = 0; i < idxArray.length; i++) {
			columnArray[i] = {
				header : idxArray[i],
				width : 180,
				dataIndex : idxArray[i],
				editor : new Ext.form.TextField()
			}
		}
		var column = new Ext.grid.ColumnModel(columnArray);
		return column;
	}

	/**
	 * 创建多行多列表格编辑模式
	 */
	function createMultiGridModel(cardPanelCmp, configId) {
		cardPanelCmp.removeAll();
		colsHidden.setValue(null);
		var cols = getMultiGridColumns(configId);
		colsHidden.setValue(cols);
		var columns = createMultiGridColumns(cols);
		var colcfg = new Array();
		if (columns.config) {
			colcfg = columns.config;
		}
		var reader = createMultiGridReader(cols);
		multiGridStore.reader = reader;
		// 定义grid表格
		var grid = new Ext.grid.EditorGridPanel({
			id : 'grid',
			// height : 440,
			stripeRows : true, // 斑马线
			store : multiGridStore,
			frame : false,
			// autoScroll : true,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			cm : columns,
			tbar : [{
				text : '添加一列',
				iconCls : 'addIcon',
				handler : function() {
					Ext.Msg.prompt('请输入', '列名:', function(btn, text) {
								if (btn == 'ok') {
									if (checkExistsCol(colcfg, text)) {
										Ext.Msg.alert("提示", "该列已存在请勿重复添加");
										return;
									}
									colcfg.push({
												header : text,
												dataIndex : text,
												editor : new Ext.form.TextField(),
												width : 180
											});
									cols.push(text)
									colsHidden.setValue(cols);
									columns = new Ext.grid.ColumnModel(colcfg);
									grid.reconfigure(multiGridStore, columns);
								}
							});
				}
			}, '-', {
				text : '添加一行',
				iconCls : 'addIcon',
				handler : function() {
					var row = {};
					for (var i = 0; i < colcfg.length; i++) {
						var cmobj = colcfg[i];
						var idx = cmobj.dataIndex;
						row[idx] = null;
					}
					columns = new Ext.grid.ColumnModel(colcfg);
					var r = new multiGridStore.recordType(row);
					multiGridStore.insert(0, r);
					grid.reconfigure(multiGridStore, columns);
				}
			}, '-', {
				text : '保存修改',
				iconCls : 'addIcon',
				handler : function() {
					var storeData = grid.getStore();
					// var mod = storeData.getModifiedRecords();
					// all data
					var mod = storeData.data.items;
					var modsize = mod.length;
					var cmcfg = grid.cm.config;
					if (modsize > 0) {
						var tform = infoForm.getForm();
						var cfgId = tform.findField("id").getValue();
						var clientId = tform.findField("clientId").getValue();
						var configName = tform.findField("configName")
								.getValue();
						var configType = tform.findField("selectConfigType")
								.getValue();
						var clientType = tform.findField("selectClientType")
								.getValue();
						if (clientId != null && !Ext.isEmpty(configType)) {
							var tt = new Array();
							for (var i = 0; i < modsize; i++) {
								var cmCfgData = cmcfg[i];
								var tData = mod[i].data;
								if (!Ext.isEmpty(cmCfgData)) {
									var hide = cmCfgData.hidden;
									// 如果列隐藏了就当作是删除了该列
									if (Ext.isEmpty(hide) || !hide) {
										tt[i] = tData;
									}
								}
								if (Ext.isEmpty(cmCfgData) && i > 0) {
									tt[i] = tData;
								}
							}
							var configs = Ext.util.JSON.encode(tt);
							if (submitGrid(cfgId, clientId, configName,
									configType, configs, clientType)) {
								storeData.commitChanges();
								Ext.Msg.alert("提示", "保存成功");
								store.load();
								return;
							} else {
								storeData.rejectChanges();
								Ext.Msg.alert("提示", "保存失败");
								return;
							}
						}
					}
				}
			}, '-', {
				text : '刷新',
				iconCls : 'addIcon',
				handler : function() {
					multiGridStore.reload();
				}
			}, '-', {
				text : '复制到表格',
				iconCls : 'addIcon',
				handler : function() {
					if (!copyWin) {
						copyWin = new Ext.Window({
									title : '客户端配置编辑器',
									layout : 'fit',
									width : 600,
									height : 450,
									closeAction : 'hide',
									plain : true,
									items : [copyTextForm]
								});
					}
					copyWin.on('show', function() {
								// TODO
								var mod = multiGridStore.data.items;
								var copyCmp = Ext.getCmp('copycfg');
								var text = grid2Text(mod);
								copyCmp.setValue(text);
							});
					copyWin.show(this);
				}
			}]
		});
		grid.cm = columns;
		cardPanelCmp.add(grid);
		multiGridStore.setBaseParam('id', configId);
		multiGridStore.clearData();
		multiGridStore.load();
	}
	/**
	 * 普通文本编辑方式
	 */
	function createTextModel(cardPanelCmp, cfg) {
		cardPanelCmp.removeAll();
		var txtForm = new Ext.FormPanel({
			labelWidth : 75,
			frame : true,
			bodyStyle : 'padding:5px 5px 0',
			items : [{
						fieldLabel : '配置文本',
						xtype : 'textarea',
						name : 'textcfg',
						width : 460,
						height : 300,
						autoScroll : true
					}],
			buttons : [{
				text : '保存',
				handler : function() {
					Ext.MessageBox.confirm('提示', '确认提交?', function(e) {
						if (e == 'yes') {
							var textform = txtForm.getForm();
							var textcfg = textform.findField('textcfg')
									.getValue();
							var tform = infoForm.getForm();
							var cfgId = tform.findField("id").getValue();
							var clientId = tform.findField("clientId")
									.getValue();
							var configName = tform.findField("configName")
									.getValue();
							var configType = tform
									.findField("selectConfigType").getValue();
							var clientType = tform
									.findField("selectClientType").getValue();

							if (Ext.isEmpty(cfgId)) {
								Ext.MessageBox.alert('提示', '配置ID不能为空');
								return;
							}
							var result = submitGrid(cfgId, clientId,
									configName, configType, textcfg, clientType);
							if (result) {
								Ext.MessageBox.alert('提示', '保存成功');
							} else {
								Ext.MessageBox.alert('提示', '保存失败');
							}
							store.load();
						}
					});
				}
			}, {
				text : "重置",
				handler : function() { // 按钮响应函数
					txtForm.form.reset();
				}
			}]
		});
		var tf = txtForm.getForm();
		tf.findField("textcfg").setValue(cfg);
		cardPanelCmp.add(txtForm);
	}
	/**
	 * 创建属性表格编辑模式
	 */
	function createPropertyModel(cardPanelCmp, cfg, cfgId, clientId) {
		cardPanelCmp.removeAll();
		var propSrc = Ext.decode(cfg);
		var propGrid = new Ext.grid.PropertyGrid({
			width : 300,
			source : propSrc,
			tbar : [{
						text : '添加一行',
						iconCls : 'addIcon',
						handler : function() {
							Ext.Msg.prompt('请输入', '属性名:', function(btn, text) {
										if (btn == 'ok') {
											propGrid
													.setProperty(text, '', true);
										}
									});
						}
					}, '-', {
						text : '删除一行',
						iconCls : 'addIcon',
						handler : function() {
							var data = propGrid.getSelectionModel().selection.record.data;
							var porp = data.name;
							Ext.MessageBox.confirm('提示', '是否确认删除?',
									function(e) {
										if (e == 'ok') {
											propGrid.removeProperty(porp);
										}
										return;
									});
						}
					}, '-', {
						text : '保存',
						iconCls : 'addIcon',
						handler : function() {
							var prop = propGrid.getSource();
							Ext.MessageBox.confirm('提示', '确认提交?', function(e) {
								if (e == 'yes') {
									var tform = infoForm.getForm();
									var configName = tform
											.findField("configName").getValue();
									var configType = tform
											.findField("selectConfigType")
											.getValue();
									var clientType = tform
											.findField("selectClientType")
											.getValue();
									if (Ext.isEmpty(cfgId)) {
										Ext.MessageBox.alert('提示', '配置ID不能为空');
										return;
									}
									var configs = Ext.util.JSON.encode(prop);
									var result = submitGrid(cfgId, clientId,
											configName, configType, configs,
											clientType);
									if (result) {
										Ext.MessageBox.alert('提示', '保存成功');
									} else {
										Ext.MessageBox.alert('提示', '保存失败');
									}
									store.load();
								}
							});
						}
					}]
		});
		cardPanelCmp.add(propGrid);
	}
	// 获取动态表格列头数组
	function getMultiGridColumns(id) {
		var idxArray = new Array();
		Ext.Ajax.request({
					url : '/spreader-front/clientcfg/cols?_time='
							+ new Date().getTime(),
					params : {
						'id' : id
					},
					async : false,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						idxArray = result;
					},
					failure : function() {
					}
				});
		return idxArray;
	}
	function text2Grid(text) {
		var cols = colsHidden.getValue();
		if (Ext.isEmpty(cols)) {
			Ext.Msg.alert("提示", "请编辑好表格列再进行操作");
			return;
		}
		var reader = createMultiGridReader(cols);
		multiGridStore.reader = reader;
		var d = new Array();
		var r = text.split(/\r?\n/);
		for (var i = 0, size = r.length; i < size; i++) {
			var c = r[i].split("\t");
			var a = {};
			for (var j = 0, size2 = c.length; j < size2; j++) {
				a[cols[j]] = c[j];
			}
			d.push(a);
		}
		var loadData = {
			"list" : d
		};
		multiGridStore.loadData(loadData);
	}

	function grid2Text(gridMod) {
		if (Ext.isEmpty(gridMod)) {
			return null;
		} else {
			var len = gridMod.length;
			var text = '';
			for (var i = 0; i < len; i++) {
				var data = gridMod[i].data;
				for (var j in data) {
					var col = data[j];
					text += col + '\t';
				}
				text += '\r';
			}
			return text;
		}
	}
	function renderEditorType(value) {
		if (value == 1) {
			return '普通文本编辑模式';
		} else if (value == 2) {
			return '属性表格编辑模式';
		} else if (value == 3) {
			return '自定义表格编辑模式';
		}
		return value;
	}
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [cfgGrid]
			});
});