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
								}, {
									name : 'successCount'
								}]),
				// remoteSort : true,
				autoLoad : true
			});
	// 页数
	var number = 200;
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
				width : 80
			}, {
				header : '市场代码',
				dataIndex : 'actionId',
				sortable : true,
				width : 90
			}, {
				header : '市场',
				dataIndex : 'actionId',
				renderer : renderMarketName,
				sortable : true,
				width : 100
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
				header : '成功数',
				dataIndex : 'successCount',
				sortable : true,
				width : 100
			}, {
				header : '任务日期',
				dataIndex : 'taskDate',
				sortable : true,
				renderer : renderDate,
				width : 100
			}, {
				header : '任务批次',
				dataIndex : 'clientSeq',
				sortable : true,
				width : 150
			}, {
				header : '更新时间',
				dataIndex : 'updateTime',
				sortable : true,
				renderer : renderDateHis,
				width : 120
			}, {
				header : '创建时间',
				dataIndex : 'createTime',
				sortable : true,
				renderer : renderDateHis,
				width : 120
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
									name : 'actualScale'
								}, {
									name : 'sumSuccessCount'
								}, {
									name : 'successScale'
								}]),
				autoLoad : true
			});
	// 页数
	var number2 = 200;
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
				header : '市场代码',
				dataIndex : 'actionId',
				sortable : true,
				width : 100
			}, {
				header : '市场',
				dataIndex : 'actionId',
				renderer : renderMarketName,
				sortable : true,
				width : 100
			}, {
				header : 'AppName',
				dataIndex : 'appName',
				sortable : true,
				width : 80
			}, {
				header : '计划总量',
				dataIndex : 'sumExpectCount',
				sortable : true,
				width : 100
			}, {
				header : '执行总量',
				dataIndex : 'sumActualCount',
				sortable : true,
				width : 100
			}, {
				header : '完成率(%)',
				dataIndex : 'actualScale',
				sortable : true,
				width : 90
			}, {
				header : '成功总量',
				dataIndex : 'sumSuccessCount',
				sortable : true,
				width : 90
			}, {
				header : '成功率(%)',
				dataIndex : 'successScale',
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

	function renderMarketName(value) {
		if (value == '4009') {
			return '360手机助手';
		}
		if (value == '4010') {
			return '安卓市场';
		}
		if (value == '4011') {
			return '应用汇';
		}
		if (value == '4012') {
			return '91助手';
		}
		if (value == '4013') {
			return '安智市场';
		}
		if (value == '4014') {
			return '机锋市场';
		}
		if (value == '4015') {
			return '百度手机助手';
		}
		if (value == '4016') {
			return '搜狐应用中心';
		}
		if (value == '4017') {
			return '网易应用中心';
		}
		if (value == '4018') {
			return '腾讯应用宝';
		}
		if (value == '4019') {
			return '360桌面端';
		}
		if (value == '4020') {
			return '小米市场';
		}
		if (value == '4021') {
			return '豌豆荚';
		}
		if (value == '4022') {
			return '安智桌面端';
		}
		return '新增市场';
	}
});