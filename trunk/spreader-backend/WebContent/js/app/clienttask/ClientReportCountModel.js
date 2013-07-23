Ext.onReady(function() {
	var clientStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/spreader-front/clientreport/clientStore?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'clientId'
								}, {
									name : 'taskType'
								}, {
									name : 'taskDate'
								}, {
									name : 'clientSeq'
								}, {
									name : 'updateTime'
								}, {
									name : 'createTime'
								}, {
									name : 'expectCount'
								}, {
									name : 'actualCount'
								}, {
									name : 'actionId'
								}, {
									name : 'appName'
								}]),
				// remoteSort : true,
				autoLoad : true
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
							clientStore.reload({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
						}
					}
				}
			});

	// 分页带上查询条件
	clientStore.on('beforeload', function() {
				var clientDaysCmp = Ext.getCmp('clientDays');
				var clientDays = clientDaysCmp.getValue();
				this.baseParams = {
					clientDays : clientDays
				};
			});
	var rownums = new Ext.grid.RowNumberer({
				header : 'NO',
				locked : true
			});

	var cm = new Ext.grid.ColumnModel([rownums, {
				header : '客户端ID',
				dataIndex : 'clientId',
				sortable : true,
				width : 90
			}, {
				header : 'ActionId',
				dataIndex : 'actionId',
				sortable : true,
				width : 90
			}, {
				header : 'AppName',
				dataIndex : 'appName',
				sortable : true,
				width : 90
			}, {
				header : '执行数量',
				dataIndex : 'actualCount',
				sortable : true,
				width : 100
			}, {
				header : '计划数量',
				dataIndex : 'expectCount',
				sortable : true,
				width : 100
			}, {
				header : '任务日期',
				dataIndex : 'taskDate',
				sortable : true,
				renderer : renderDate,
				width : 150
			}, {
				header : '任务批次',
				dataIndex : 'clientSeq',
				sortable : true,
				width : 200
			}, {
				header : '更新时间',
				dataIndex : 'updateTime',
				sortable : true,
				renderer : renderDateHis,
				width : 150
			}, {
				header : '创建时间',
				dataIndex : 'createTime',
				sortable : true,
				renderer : renderDateHis,
				width : 150
			}]);
	// // 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : clientStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext]
			});

	// 定义grid表格
	var clientGrid = new Ext.grid.GridPanel({
				id : 'clientGrid',
				region : 'center',
				split : true,
				stripeRows : true, // 斑马线
				frame : true,
				store : clientStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				bbar : bbar,
				cm : cm,
				tbar : [new Ext.form.TextField({
									id : 'clientDays',
									name : 'clientDays',
									emptyText : '输入天数',
									width : 130
								}), '-', {
							text : '查询',
							iconCls : 'previewIcon',
							handler : function() {
								var clientDaysCmp = Ext.getCmp('clientDays');
								var clientDays = clientDaysCmp.getValue();
								clientStore.setBaseParam('clientDays',
										clientDays);
								clientStore.load();
							}
						}]
			});

	// Market
	var marketStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/spreader-front/clientreport/marketStore?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'actionId'
								}, {
									name : 'appName'
								}, {
									name : 'sumExpectCount'
								}, {
									name : 'sumActualCount'
								}, {
									name : 'scale'
								}]),
				autoLoad : true
			});
	// 页数
	var number2 = 20;
	var numtext2 = new Ext.form.TextField({
				id : 'maxpage2',
				name : 'maxpage2',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							bbar2.pageSize = Number(numtext2.getValue());
							number2 = Number(numtext2.getValue());
							marketStore.reload({
										params : {
											start : 0,
											limit : bbar2.pageSize
										}
									});
						}
					}
				}
			});

	// 分页带上查询条件
	marketStore.on('beforeload', function() {
				var marketDaysCmp = Ext.getCmp('marketDays');
				var marketDays = marketDaysCmp.getValue();
				this.baseParams = {
					marketDays : marketDays
				};
			});
	var rownums2 = new Ext.grid.RowNumberer({
				header : 'NO',
				locked : true
			});

	var cm2 = new Ext.grid.ColumnModel([rownums, {
				header : 'ActionId',
				dataIndex : 'actionId',
				sortable : true,
				width : 100
			}, {
				header : 'AppName',
				dataIndex : 'appName',
				sortable : true,
				width : 80
			}, {
				header : '执行总量',
				dataIndex : 'sumActualCount',
				sortable : true,
				width : 100
			}, {
				header : '计划总量',
				dataIndex : 'sumExpectCount',
				sortable : true,
				width : 100
			}, {
				header : '完成比例',
				dataIndex : 'scale',
				sortable : true,
				width : 90
			}]);
	// // 分页菜单
	var bbar2 = new Ext.PagingToolbar({
				pageSize : number2,
				store : marketStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext2]
			});

	// 定义grid表格
	var marketGrid = new Ext.grid.GridPanel({
				id : 'marketGrid',
				region : 'center',
				split : true,
				stripeRows : true, // 斑马线
				frame : true,
				store : marketStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				bbar : bbar2,
				cm : cm2,
				tbar : [new Ext.form.TextField({
									id : 'marketDays',
									name : 'marketDays',
									emptyText : '输入天数',
									width : 130
								}), '-', {
							text : '查询',
							iconCls : 'previewIcon',
							handler : function() {
								var marketDaysCmp = Ext.getCmp('marketDays');
								var marketDays = marketDaysCmp.getValue();
								marketStore.setBaseParam('marketDays',
										marketDays);
								marketStore.load();
							}
						}]
			});
	// Market
	var tabs = new Ext.TabPanel({
				region : 'center',
				enableTabScroll : true,
				tabWidth : 200,
				// autoWidth : true,
				height : 200
			});
	// 每一个Tab都可以看作为一个Panel
	tabs.add({
		title : '<span class="commoncss"><font color="red">按客户端统计打榜情况</font></span>',
		id : 'clientCountTab',
		layout : 'border',
		items : [clientGrid]
	});
	tabs.add({
		id : 'marketCountTab',
		title : '<span class="commoncss"><font color="blue">按市场统计打榜情况</font></span>',
		layout : 'border',
		items : [marketGrid]
	});
	tabs.activate(0);
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [tabs]
			});
});