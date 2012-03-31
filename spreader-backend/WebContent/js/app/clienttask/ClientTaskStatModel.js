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
		labelWidth : 90, // 标签宽度
		frame : true,
		labelAlign : 'right',
		bodyStyle : 'padding:5 5 5 5',
		buttonAlign : 'center',
		height : 100,
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
											fieldLabel : "客户端ID",
											name : 'cid'
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
						var cid = tform.findField("cid").getValue();
						taskStore.setBaseParam('startTime',
								renderDateHis(startTime));
						taskStore.setBaseParam('endTime',
								renderDateHis(endTime));
						taskStore.setBaseParam('cid', cid);
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
							url : '../taskstat/taskgridstore'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'cid'
								}, {
									name : 'taskCode'
								}, {
									name : 'success'
								}, {
									name : 'fail'
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
	var cm = new Ext.grid.ColumnModel([sm,rownum, {
				header : '客户端编号',
				dataIndex : 'cid',
				locked : true,
				width : 80
			}, {
				header : '任务编号',
				dataIndex : 'taskCode',
				locked : true,
				width : 100
			}, {
				header : '成功次数',
				dataIndex : 'success',
				renderer : renderGreen,
				locked : true,
				width : 100
			}, {
				header : '失败次数',
				dataIndex : 'fail',
				renderer : renderRed,
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
				height : 500,
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
//						,
//				onCellClick : function(grid, rowIndex, columnIndex, e) {
//					var selesm = grid.getSelectionModel().getSelections();
//					// var userid = selesm[0].data.id;
//					var cont = selesm[0].data.content;
//					var contentcol = grid.getColumnModel()
//							.getDataIndex(columnIndex);
//					if (contentcol == 'showdtl') {
//					}
//				}
			});

	// 注册事件
//	taskGrid.on('cellclick', taskGrid.onCellClick, taskGrid);

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [taskQueryForm, taskGrid]
			});
});
