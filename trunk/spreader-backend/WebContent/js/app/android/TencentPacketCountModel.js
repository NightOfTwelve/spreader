Ext.onReady(function() {
	var packetStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/spreader-front/android/tencent/packet?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'machineId'
								}, {
									name : 'phone'
								}, {
									name : 'guid'
								}, {
									name : 'mac'
								}, {
									name : 'imsi'
								}, {
									name : 'androidVersion'
								}, {
									name : 'productId'
								}, {
									name : 'fileId'
								}, {
									name : 'clientIp'
								}, {
									name : 'postTime'
								}, {
									name : 'clientId'
								}]),
				// remoteSort : true,
				autoLoad : true
			});
	// 页数
	var number = 500;
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
							packetStore.reload({
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
	packetStore.on('beforeload', function() {
				var productIdCmp = Ext.getCmp('productId');
				var productId = productIdCmp.getValue();
				var clientIdCmp = Ext.getCmp('clientId');
				var clientId = clientIdCmp.getValue();
				var postDateCmp = Ext.getCmp('postDate');
				var postDate = postDateCmp.getValue();
				this.baseParams = {
					productId : productId,
					clientId : clientId,
					postDate : postDate
				};
			});
	var cm = new Ext.grid.ColumnModel([{
				header : 'APP编号',
				dataIndex : 'productId',
				sortable : true,
				width : 100
			}, {
				header : '设备编号',
				dataIndex : 'machineId',
				sortable : true,
				width : 120
			}, {
				header : '手机型号',
				dataIndex : 'phone',
				sortable : true,
				width : 110
			}, {
				header : '网卡地址',
				dataIndex : 'mac',
				sortable : true,
				width : 110
			}, {
				header : 'IMSI',
				dataIndex : 'imsi',
				sortable : true,
				width : 120
			}, {
				header : '安卓版本',
				dataIndex : 'androidVersion',
				sortable : true,
				width : 110
			}, {
				header : '客户端IP',
				dataIndex : 'clientIp',
				sortable : true,
				width : 110
			}, {
				header : '客户端ID',
				dataIndex : 'clientId',
				sortable : true,
				width : 90
			}, {
				header : '发送时间',
				dataIndex : 'postTime',
				sortable : true,
				renderer : renderDateHis,
				width : 120
			}]);
	// // 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : packetStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext]
			});

	var packetGrid = new Ext.grid.GridPanel({
				id : 'packetGrid',
				region : 'west',
				width : 860,
				split : true,
				stripeRows : true, // 斑马线
				frame : true,
				store : packetStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				bbar : bbar,
				cm : cm,
				tbar : [new Ext.form.TextField({
									id : 'postDate',
									name : 'postDate',
									emptyText : '输入日期(yyyymmdd)',
									width : 130
								}), '-', new Ext.form.TextField({
									id : 'productId',
									name : 'productId',
									emptyText : '输入App编号*',
									width : 130
								}), '-', new Ext.form.TextField({
									id : 'clientId',
									name : 'clientId',
									emptyText : '输入客户端编号',
									width : 100
								}), '-', {
							text : '查询',
							iconCls : 'previewIcon',
							handler : function() {
								var productIdCmp = Ext.getCmp('productId');
								var productId = productIdCmp.getValue();
								if (Ext.isEmpty(productId)) {
									Ext.Msg.alert("提示", "productId不能为空");
									return;
								}
								var clientIdCmp = Ext.getCmp('clientId');
								var clientId = clientIdCmp.getValue();
								var postDateCmp = Ext.getCmp('postDate');
								var postDate = postDateCmp.getValue();
								packetStore
										.setBaseParam('productId', productId);
								packetStore.setBaseParam('postDate', postDate);
								packetStore.setBaseParam('clientId', clientId);
								packetStore.load();
								phoneStore.setBaseParam('productId', productId);
								phoneStore.setBaseParam('postDate', postDate);
								phoneStore.setBaseParam('clientId', clientId);
								phoneStore.load();
								adverStore.setBaseParam('productId', productId);
								adverStore.setBaseParam('postDate', postDate);
								adverStore.setBaseParam('clientId', clientId);
								adverStore.load();
								ipStore.setBaseParam('productId', productId);
								ipStore.setBaseParam('postDate', postDate);
								ipStore.setBaseParam('clientId', clientId);
								ipStore.load();
							}
						}]
			});
	// /
	var phoneStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/spreader-front/android/tencent/phone?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'phone'
								}, {
									name : 'phoneCount'
								}])
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
							phoneStore.reload({
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
	phoneStore.on('beforeload', function() {
				var productIdCmp = Ext.getCmp('productId');
				var productId = productIdCmp.getValue();
				var clientIdCmp = Ext.getCmp('clientId');
				var clientId = clientIdCmp.getValue();
				var postDateCmp = Ext.getCmp('postDate');
				var postDate = postDateCmp.getValue();
				this.baseParams = {
					productId : productId,
					clientId : clientId,
					postDate : postDate
				};
			});

	var cm2 = new Ext.grid.ColumnModel([{
				header : '手机型号',
				dataIndex : 'phone',
				sortable : true,
				width : 100
			}, {
				header : '使用次数',
				dataIndex : 'phoneCount',
				sortable : true,
				width : 100
			}]);
	var bbar2 = new Ext.PagingToolbar({
				pageSize : number2,
				store : phoneStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext2]
			});

	var phoneGrid = new Ext.grid.GridPanel({
				id : 'phoneGrid',
				region : 'north',
				height : 200,
				split : true,
				stripeRows : true, // 斑马线
				frame : true,
				store : phoneStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				bbar : bbar2,
				cm : cm2
			});
	// //adver
	var adverStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/spreader-front/android/tencent/adv?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'androidVersion'
								}, {
									name : 'verCount'
								}])
			});
	// 页数
	var number3 = 200;
	var numtext3 = new Ext.form.TextField({
				id : 'maxpage3',
				name : 'maxpage3',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							bbar3.pageSize = Number(numtext3.getValue());
							number3 = Number(numtext3.getValue());
							adverStore.reload({
										params : {
											start : 0,
											limit : bbar3.pageSize
										}
									});
						}
					}
				}
			});

	// 分页带上查询条件
	adverStore.on('beforeload', function() {
				var productIdCmp = Ext.getCmp('productId');
				var productId = productIdCmp.getValue();
				var clientIdCmp = Ext.getCmp('clientId');
				var clientId = clientIdCmp.getValue();
				var postDateCmp = Ext.getCmp('postDate');
				var postDate = postDateCmp.getValue();
				this.baseParams = {
					productId : productId,
					clientId : clientId,
					postDate : postDate
				};
			});

	var cm3 = new Ext.grid.ColumnModel([{
				header : '安卓版本',
				dataIndex : 'androidVersion',
				sortable : true,
				width : 100
			}, {
				header : '使用次数',
				dataIndex : 'verCount',
				sortable : true,
				width : 100
			}]);
	var bbar3 = new Ext.PagingToolbar({
				pageSize : number3,
				store : adverStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext3]
			});

	var adverGrid = new Ext.grid.GridPanel({
				id : 'adverGrid',
				region : 'center',
				split : true,
				stripeRows : true, // 斑马线
				frame : true,
				store : adverStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				bbar : bbar3,
				cm : cm3
			});

	// IP
	var ipStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/spreader-front/android/tencent/ip?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'clientIp'
								}, {
									name : 'ipCount'
								}])
			});
	// 页数
	var number4 = 200;
	var numtext4 = new Ext.form.TextField({
				id : 'maxpage4',
				name : 'maxpage4',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							bbar4.pageSize = Number(numtext4.getValue());
							number4 = Number(numtext4.getValue());
							ipStore.reload({
										params : {
											start : 0,
											limit : bbar4.pageSize
										}
									});
						}
					}
				}
			});

	// 分页带上查询条件
	ipStore.on('beforeload', function() {
				var productIdCmp = Ext.getCmp('productId');
				var productId = productIdCmp.getValue();
				var clientIdCmp = Ext.getCmp('clientId');
				var clientId = clientIdCmp.getValue();
				var postDateCmp = Ext.getCmp('postDate');
				var postDate = postDateCmp.getValue();
				this.baseParams = {
					productId : productId,
					clientId : clientId,
					postDate : postDate
				};
			});

	var cm4 = new Ext.grid.ColumnModel([{
				header : '客户端IP',
				dataIndex : 'clientIp',
				sortable : true,
				width : 100
			}, {
				header : '使用次数',
				dataIndex : 'ipCount',
				sortable : true,
				width : 100
			}]);
	var bbar4 = new Ext.PagingToolbar({
				pageSize : number4,
				store : ipStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext4]
			});

	var ipGrid = new Ext.grid.GridPanel({
				id : 'ipGrid',
				region : 'south',
				height : 200,
				split : true,
				stripeRows : true, // 斑马线
				frame : true,
				store : ipStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				bbar : bbar4,
				cm : cm4
			});

	var statPanel = new Ext.Panel({
				id : 'statPanel',
				region : 'center',
				layout : 'border',
				split : true,
				frame : true,
				items : [phoneGrid, adverGrid, ipGrid]
			});
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [packetGrid, statPanel]
			});
});