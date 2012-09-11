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
							recordIpStore.reload({
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
	var currentIpStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/spreader-front/iprecord/currentrecord?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'clientId'
								}, {
									name : 'ip'
								}, {
									name : 'createTime'
								}, {
									name : 'recordTime'
								}, {
									name : 'token'
								}]),
				autoLoad : {
					params : {
						start : 0,
						limit : 20
					}
				}
			});
	var recordIpStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/spreader-front/iprecord/record?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							root : 'list',
							totalProperty : 'totalCount'
						}, [{
									name : 'clientId'
								}, {
									name : 'ip'
								}, {
									name : 'createTime'
								}, {
									name : 'recordTime'
								}, {
									name : 'id'
								}]),
				autoLoad : {
					params : {
						start : 0,
						limit : 20
					}
				}
			});
	// 定义Checkbox
	var cursm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true
			});
	// 定义自动当前页行号
	var currownum = new Ext.grid.RowNumberer({
				header : 'NO.',
				locked : true,
				width : 28
			});

	// 定义锁定列模型
	var curcm = new Ext.grid.ColumnModel([cursm, currownum, {
				header : '客户端ID',
				dataIndex : 'clientId',
				locked : true,
				width : 80
			}, {
				header : 'IP',
				dataIndex : 'ip',
				locked : true,
				width : 100
			}, {
				header : 'Token',
				dataIndex : 'token',
				locked : true,
				width : 100
			}, {
				header : '起始时间',
				dataIndex : 'createTime',
				renderer : renderDateHis,
				width : 120
			}, {
				header : '记录时间',
				dataIndex : 'recordTime',
				renderer : renderDateHis,
				width : 120
			}]);

	// 定义grid表格
	var currentIpGrid = new Ext.grid.GridPanel({
				region : 'west',
				id : 'currentIpGrid',
				title : '实时IP记录查询',
				stripeRows : true, // 斑马线
				frame : true,
				width : 600,
				split : true,
				autoScroll : true,
				store : currentIpStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				sm : cursm,
				colModel : curcm,
				tbar : [{
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								currentIpStore.reload();
							}
						}]
			});

	// 定义Checkbox
	var recsm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true
			});
	// 定义自动当前页行号
	var recrownum = new Ext.grid.RowNumberer({
				header : 'NO.',
				locked : true,
				width : 28
			});

	// 定义锁定列模型
	var reccm = new Ext.grid.ColumnModel([recsm, recrownum, {
				header : '客户端ID',
				dataIndex : 'clientId',
				locked : true,
				width : 80
			}, {
				header : 'IP',
				dataIndex : 'ip',
				locked : true,
				width : 100
			}, {
				header : '起始时间',
				dataIndex : 'createTime',
				renderer : renderDateHis,
				width : 120
			}, {
				header : '记录时间',
				dataIndex : 'recordTime',
				renderer : renderDateHis,
				width : 120
			}]);
	// 分页菜单
	var recbbar = new Ext.PagingToolbar({
				pageSize : number,
				store : recordIpStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext]
			});

	// 定义grid表格
	var recordIpGrid = new Ext.grid.GridPanel({
		region : 'center',
		id : 'recordIpGrid',
		stripeRows : true, // 斑马线
		frame : true,
		title : 'IP切换情况查询',
		split : true,
		autoScroll : true,
		store : recordIpStore,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		sm : recsm,
		colModel : reccm,
		bbar : recbbar,
		tbar : [
				{
					text : '刷新',
					iconCls : 'arrow_refreshIcon',
					handler : function() {
						recordIpStore.reload();
					}
				},
				'-',
				new Ext.form.TextField({
							id : 'clientId',
							name : 'clientId',
							fieldLabel : '客户端ID',
							emptyText : '请输入客户端ID',
							enableKeyEvents : true,
							width : 130
						}),
				'-',
				calendarCmp('startTime', 'startTime', 'IP起始时间', null, '请输入起始时间'),
				'-',
				calendarCmp('endTime', 'endTime', 'IP结束时间', null, '请输入结束时间'),
				'-', {
					text : '查询记录',
					iconCls : 'edit1Icon',
					handler : function() {
						var startTime = Ext.getCmp('startTime').getValue();
						var endTime = Ext.getCmp('endTime').getValue();
						var clientId = Ext.getCmp('clientId').getValue();
						recordIpStore.setBaseParam('clientId', clientId);
						recordIpStore.setBaseParam('startTime', startTime);
						recordIpStore.setBaseParam('endTime', endTime);
						recordIpStore.load();
					}
				}]
	});

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [recordIpGrid, currentIpGrid]
			});
});