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
							taskStore.reload({
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
	var taskQueryForm = new Ext.form.FormPanel({
		region : 'north',
		title : "筛选条件",
		// collapsible : true,
		frame : true,
		id : 'taskQueryForm',
		// border : true,
		labelWidth : 150, // 标签宽度
		frame : true,
		split : true,
		labelAlign : 'right',
		bodyStyle : 'padding:5 5 5 5',
		buttonAlign : 'center',
		height : 150,
		items : [{// 行1
			layout : "column",
			items : [{
						columnWidth : .3,
						layout : "form",
						items : [{
									xtype : "datefield",
									fieldLabel : "起始时间",
									width : 150,
									name : 'startTime'
								}]
					}, {
						columnWidth : .3,
						layout : "form",
						items : [{
									xtype : "datefield",
									fieldLabel : "结束时间",
									width : 150,
									name : 'endTime'
								}]
					}, {
						columnWidth : .3,
						layout : "form",
						items : [{
									xtype : "numberfield",
									fieldLabel : "客户端ID",
									name : 'clientId'
								}]
					}]
		}, {	// 行2
					layout : "column",
					items : [{
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "numberfield",
											fieldLabel : "任务编号",
											name : 'taskId'
										}]
							}, {
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "任务代码",
											name : 'taskCode'
										}]
							}, {
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "任务状态",
											name : 'status'
										}]
							}]
				}, {// 行3
					layout : "column",
					items : [{
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "用户编号 ",
											name : 'uid'
										}]
							}, {
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "错误代码",
											name : 'errorCode'
										}]
							}, {
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "网站ID",
											name : 'websiteId'
										}]
							}]
				}],
		buttonAlign : "center",
		buttons : [{
					text : "查询",
					handler : function() { // 按钮响应函数
						var tform = taskQueryForm.getForm();
						var startTime = tform.findField("startTime").getValue();
						var endTime = tform.findField("endTime").getValue();
						var taskId = tform.findField("taskId").getValue();
						var clientId = tform.findField("clientId").getValue();
						var taskCode = tform.findField("taskCode").getValue();
						var status = tform.findField("status").getValue();
						var uid = tform.findField("uid").getValue();
						var errorCode = tform.findField("errorCode").getValue();
						var websiteId = tform.findField("websiteId").getValue();
						taskStore.setBaseParam('startTime',
								renderDateHis(startTime));
						taskStore.setBaseParam('endTime',
								renderDateHis(endTime));
						taskStore.setBaseParam('taskId', taskId);
						taskStore.setBaseParam('taskCode', taskCode);
						taskStore.setBaseParam('clientId', clientId);
						taskStore.setBaseParam('status', status);
						taskStore.setBaseParam('uid', uid);
						taskStore.setBaseParam('errorCode', errorCode);
						taskStore.setBaseParam('websiteId', websiteId);
						taskStore.load();
					}
				}, {
					text : "重置",
					handler : function() { // 按钮响应函数
						taskQueryForm.form.reset();
					}
				}]
	});

	// 定义表格数据源
	var taskStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../taskstatdtl/dtlgrid?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'taskId'
								}, {
									name : 'contents'
								}, {
									name : 'taskCode'
								}, {
									name : 'clientId'
								}, {
									name : 'status'
								}, {
									name : 'executedTime'
								}, {
									name : 'uid'
								}, {
									name : 'errorCode'
								}, {
									name : 'websiteId'
								}, {
									name : 'websiteErrorDesc'
								}]),
				autoLoad : {
					params : {
						start : 0,
						limit : 20
					}
				}
			});
	// 翻页排序时带上查询条件
	taskStore.on('beforeload', function() {
				var tform = taskQueryForm.getForm();
				var startTime = tform.findField("startTime").getValue();
				var endTime = tform.findField("endTime").getValue();
				var taskId = tform.findField("taskId").getValue();
				var clientId = tform.findField("clientId").getValue();
				var taskCode = tform.findField("taskCode").getValue();
				var status = tform.findField("status").getValue();
				var uid = tform.findField("uid").getValue();
				var errorCode = tform.findField("errorCode").getValue();
				var websiteId = tform.findField("websiteId").getValue();
				this.baseParams = {
					startTime : renderDateHis(startTime),
					endTime : renderDateHis(endTime),
					taskId : taskId,
					clientId : clientId,
					taskCode : taskCode,
					status : status,
					uid : uid,
					errorCode : errorCode,
					websiteId : websiteId
				};
			});
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
				header : '任务编号',
				dataIndex : 'taskId',
				locked : true,
				width : 80
			}, {
				header : '任务代码',
				dataIndex : 'taskCode',
				locked : true,
				width : 80
			}, {
				header : '客户端ID',
				dataIndex : 'clientId',
				locked : true,
				width : 80
			}, {
				header : '内容',
				dataIndex : 'contents',
				// renderer : renderBrief,
				width : 100
			}, {
				header : '状态',
				dataIndex : 'status',
				width : 80
			}, {
				header : '执行时间',
				dataIndex : 'executedTime',
				renderer : renderDateHis,
				width : 110
			}, {
				header : 'UID',
				dataIndex : 'uid',
				width : 80
			}, {
				header : '网站ID',
				dataIndex : 'websiteId',
				width : 100
			}, {
				header : '错误代码',
				dataIndex : 'errorCode',
				width : 80
			}, {
				header : '错误内容',
				dataIndex : 'websiteErrorDesc',
				// renderer : renderBrief,
				width : 100
			}]);
	// // 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : taskStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext]
			});

	// 定义grid表格
	var taskGrid = new Ext.grid.GridPanel({
				region : 'center',
				id : 'taskGrid',
				stripeRows : true, // 斑马线
				frame : true,
				// autoWidth : true,
				autoScroll : true,
				store : taskStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				bbar : bbar,
				sm : sm,
				colModel : cm,
				tbar : [{
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								taskStore.reload();
							}
						}]
			});

	// 注册事件
	// taskGrid.on('cellclick', taskGrid.onCellClick, taskGrid);

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [taskQueryForm, taskGrid]
			});
});
