Ext.onReady(function() {
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
							reportListStore.reload({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
						}
					}
				}
			});

	// 定义表格数据源
	var reportListStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../taskreport/replist?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'key'
								}, {
									name : 'value'
								}]),
				autoLoad : {
					params : {
						start : 0,
						limit : 20
					}
				}
			});
	// 翻页排序时带上查询条件
	// reportListStore.on('beforeload', function() {
	// var tform = taskQueryForm.getForm();
	// var startTime = tform.findField("startTime").getValue();
	// var endTime = tform.findField("endTime").getValue();
	// var taskId = tform.findField("taskId").getValue();
	// var clientId = tform.findField("clientId").getValue();
	// var taskCode = tform.findField("taskCode").getValue();
	// var status = tform.findField("status").getValue();
	// var uid = tform.findField("uid").getValue();
	// var errorCode = tform.findField("errorCode").getValue();
	// var websiteId = tform.findField("websiteId").getValue();
	// this.baseParams = {
	// startTime : renderDateHis(startTime),
	// endTime : renderDateHis(endTime),
	// taskId : taskId,
	// clientId : clientId,
	// taskCode : taskCode,
	// status : status,
	// uid : uid,
	// errorCode : errorCode,
	// websiteId : websiteId
	// };
	// });
	// 定义Checkbox
	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true
			});
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'NO.',
				locked : true,
				width : 28
			});

	// 定义锁定列模型
	var cm = new Ext.grid.ColumnModel([sm, rownum, {
				header : '报表编号',
				dataIndex : 'key',
				locked : true,
				width : 80
			}, {
				header : '报表名称',
				dataIndex : 'value',
				locked : true,
				width : 120
			}, {
				header : '详情',
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='查看'/>";
					return returnStr;
				},
				width : 100
			}]);
	// // 分页菜单
	// var bbar = new Ext.PagingToolbar({
	// pageSize : number,
	// store : reportListStore,
	// displayInfo : true,
	// displayMsg : '显示{0}条到{1}条,共{2}条',
	// emptyMsg : "没有符合条件的记录",
	// plugins : new Ext.ux.ProgressBarPager(),
	// items : ['-', '&nbsp;&nbsp;', numtext]
	// });
	// 定义grid表格
	var reportListGrid = new Ext.grid.GridPanel({
				region : 'center',
				id : 'reportListGrid',
				stripeRows : true, // 斑马线
				frame : true,
				// autoWidth : true,
				autoScroll : true,
				store : reportListStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				// bbar : bbar,
				sm : sm,
				colModel : cm,
				tbar : [{
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								reportListStore.reload();
							}
						}],
				onCellClick : function(grid, rowIndex, columnIndex, e) {
					var button = e.target.defaultValue;
					var record = grid.getStore().getAt(rowIndex);
					var data = record.data;
					if (button == '查看') {
						var repName = data.key;
						dynamicReport(repName);
					}
				}
			});
	// 注册事件
	reportListGrid.on('cellclick', reportListGrid.onCellClick, reportListGrid);

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [reportListGrid]
			});
	// 报表查询FORM
	var reportQueryForm = new Ext.form.FormPanel({
		frame : true,
		id : 'reportQueryForm',
		height : 100,
		autoWidth : true,
		labelWidth : 150, // 标签宽度
		split : true,
		labelAlign : 'left',
		bodyStyle : 'padding:5 5 5 5',
		buttonAlign : 'left',
		items : [{// 行1
			layout : "column",
			items : [{
						columnWidth : .5,
						layout : "form",
						items : [{
									xtype : "datetimefield",
									fieldLabel : "起始时间",
									width : 150,
									value : createInitDate(),
									name : 'startTime'
								}]
					}, {
						columnWidth : .5,
						layout : "form",
						items : [{
									xtype : "datetimefield",
									fieldLabel : "结束时间",
									value : createNextDate(),
									width : 150,
									name : 'endTime'
								}]
					}]
		}],
		buttonAlign : "center",
		buttons : [{
			text : "查询",
			handler : function() { // 按钮响应函数
				var tform = reportQueryForm.getForm();
				var startTime = tform.findField("startTime").getValue();
				var endTime = tform.findField("endTime").getValue();
				reportStore.setBaseParam('startTime', renderDateHis(startTime));
				reportStore.setBaseParam('endTime', renderDateHis(endTime));
				reportStore.load();
			}
		}, {
			text : "重置",
			handler : function() { // 按钮响应函数
				reportQueryForm.form.reset();
			}
		}]
	});
	// 带时间查询条件的Window
	var reportTimeWindow = new Ext.Window({
				title : '<span class="commoncss">报表信息</span>', // 窗口标题
				id : 'reportTimeWindow',
				closeAction : 'hide',
				modal : true,
				layout : 'border', // 设置窗口布局模式
				width : 700, // 窗口宽度
				height : 300, // 窗口高度
				closable : false, // 是否可关闭
				collapsible : true, // 是否可收缩
				maximizable : true, // 设置是否可以最大化
				border : false, // 边框线设置
				constrain : true, // 设置窗口是否可以溢出父容器
				pageY : 20, // 页面定位Y坐标
				pageX : document.documentElement.clientWidth / 2 - 300 / 2,// 页面定位X坐标
				items : [{
							region : 'north',
							id : 'reportQueryFormMgr',
							height : 100,
							layout : 'fit',
							header : false,
							collapsible : true,
							items : [reportQueryForm],
							split : true
						}, {
							// 设定动态编辑的组件位置
							region : 'center',
							id : 'reportGridMgr',
							layout : 'fit',
							header : false,
							collapsible : true,
							split : true
						}],
				buttons : [{
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								reportTimeWindow.hide();
							}
						}]
			});

	// 无时间查询条件的Window
	var reportNoTimeWindow = new Ext.Window({
				title : '<span class="commoncss">报表信息</span>', // 窗口标题
				id : 'reportNoTimeWindow',
				closeAction : 'hide',
				modal : true,
				layout : 'border', // 设置窗口布局模式
				width : 700, // 窗口宽度
				height : 300, // 窗口高度
				closable : false, // 是否可关闭
				collapsible : true, // 是否可收缩
				maximizable : true, // 设置是否可以最大化
				border : false, // 边框线设置
				constrain : true, // 设置窗口是否可以溢出父容器
				pageY : 20, // 页面定位Y坐标
				pageX : document.documentElement.clientWidth / 2 - 300 / 2,// 页面定位X坐标
				items : [{
							// 设定动态编辑的组件位置
							region : 'center',
							id : 'noTimereportGridMgr',
							layout : 'fit',
							header : false,
							collapsible : true,
							split : true
						}],
				buttons : [{
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								reportNoTimeWindow.hide();
							}
						}]
			});

	// 报表的Store
	var reportStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '../taskreport/repinfo?_time=' + new Date().getTime(),
					timeout : 20000
				})
	});

	/**
	 * 动态构造报表
	 */
	function dynamicReport(repName) {
		var colunm = null;
		var reader = null;
		if (Ext.isEmpty(repName)) {
			return null;
		} else {
			var params = {};
			params['repName'] = repName;
			Ext.Ajax.request({
				url : '../taskreport/repprop?_time=' + new Date().getTime(),
				success : function(response, opts) {
					var result = Ext.util.JSON.decode(response.responseText);
					if (Ext.isEmpty(result)) {
						Ext.Msg.alert("提示", "获取报表属性失败");
						return;
					} else {
						// 构造grid的列名
						var colNames = result.columnDisNames;
						// 构造grid的列索引及store列
						var colIndexs = result.columnNames;
						// 报表名称
						var disName = result.disName;
						// 区分是否显示日期查询
						var extcode = result.extendCode;
						if (Ext.isEmpty(colNames) || Ext.isEmpty(colIndexs)) {
							Ext.Msg.alert("提示", "获取报表属性失败");
							return;
						} else {
							colunm = createColumnModel(colNames, colIndexs);
							var reader = createReportStoreReader(colIndexs);
							reportStore.reader = reader;
							// 报表grid
							var reportGrid = new Ext.grid.GridPanel({
										split : true,
										id : 'reportGrid',
										height : 200,
										store : reportStore,
										loadMask : {
											msg : '正在加载表格数据,请稍等...'
										},
										cm : colunm,
										tbar : [{
													id : 'reportName',
													name : 'reportName',
													xtype : 'tbtext'
												}, {
													text : '刷新',
													iconCls : 'arrow_refreshIcon',
													handler : function() {
														reportStore.reload();
													}
												}]
									});
							reportStore.setBaseParam('repName', repName);
							reportStore.setBaseParam('startTime', null);
							reportStore.setBaseParam('endTime', null);
							var repNameCmp = Ext.getCmp("reportName");
							repNameCmp.setText('<font color = "red">' + disName
									+ '</font>');
							if ('time' == extcode) {
								reportStore.setBaseParam('extCode', 'time');
								// 获取组件的区域
								var reportGridMgr = Ext.getCmp("reportGridMgr");
								// 设置组件前先情况区域
								reportGridMgr.removeAll(true);
								// 添加组件
								reportGridMgr.add(reportGrid);
								// 布局刷新
								reportGridMgr.doLayout();
								reportStore.clearData();
								reportStore.load();
								reportTimeWindow.show();
							} else {
								reportStore.setBaseParam('extCode', null);
								var noTimeReportGridMgr = Ext
										.getCmp("noTimereportGridMgr");
								noTimeReportGridMgr.removeAll(true);
								noTimeReportGridMgr.add(reportGrid);
								noTimeReportGridMgr.doLayout();
								reportStore.clearData();
								reportStore.load();
								reportNoTimeWindow.show();
							}
						}
					}
				},
				failure : function(response, opts) {
					Ext.MessageBox.alert('提示', '获取配置失败');
				},
				params : params
			});
		}
	}

	/**
	 * 构造grid的ColumnModel
	 */
	function createColumnModel(columnNames, columnIndexs) {
		var columnArray = new Array();
		var rm = new Ext.grid.RowNumberer({
					header : 'NO.',
					locked : true,
					width : 28
				});
		columnArray[0] = rm;
		for (var i = 0; i < columnIndexs.length; i++) {
			columnArray[i + 1] = {
				header : columnNames[i],
				width : 120,
				dataIndex : columnIndexs[i]
			}
		}
		var column = new Ext.grid.ColumnModel(columnArray);
		return column;
	}

	/**
	 * 构造数据源的Reader
	 */
	function createReportStoreReader(columnIndexs) {
		var idxArray = new Array();
		for (var i = 0; i < columnIndexs.length; i++) {
			idxArray[i] = {
				name : columnIndexs[i]
			}
		}
		var reader = new Ext.data.JsonReader({
					totalProperty : 'totalCount',
					root : 'list'
				}, idxArray);
		// 报表的Store
		// var reportStore = new Ext.data.Store({
		// proxy : new Ext.data.HttpProxy({
		// url : '../taskreport/repinfo?_time='
		// + new Date().getTime()
		// }),
		// reader : reader
		// });
		return reader;
	}
});