Ext.QuickTips.init();
Ext.BLANK_IMAGE_URL = '../js/extjs3/resources/images/default/s.gif';
Ext.chart.Chart.CHART_URL = '../js/extjs3/resources/charts.swf';
Ext.onReady(function() {
	var genreStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['0', '0.全部'], ['6021', '6021.报刊杂志'],
						['6015', '6015.财务'], ['6006', '6006.参考'],
						['6010', '6010.导航'], ['6002', '6002.工具'],
						['6013', '6013.健康健美'], ['6017', '6017.教育'],
						['6003', '6003.旅行'], ['6023', '6023.美食佳饮'],
						['6016', '6016.娱乐'], ['6000', '6000.商业'],
						['6022', '6022.商品指南'], ['6005', '6005.社交'],
						['6008', '6008.摄影与录像'], ['6012', '6012.生活'],
						['6004', '6004.体育'], ['6001', '6001.天气'],
						['6018', '6018.图书'], ['6007', '6007.效率'],
						['6009', '6009.新闻'], ['6020', '6020.医疗'],
						['6011', '6011.音乐'], ['6014', '6014.游戏']]
			});
	/**
	 * 设备分类
	 */
	var popStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['27', 'iPhone'], ['44', 'iPad']]
			});
	var popCombo = new Ext.form.ComboBox({
				id : 'popCombo',
				fieldLabel : '设备',
				emptyText : '请选择设备类型',
				triggerAction : 'all',
				store : popStore,
				hiddenName : 'popId',
				valueField : 'ID',
				displayField : 'NAME',
				loadingText : '正在加载数据...',
				mode : 'local', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
				forceSelection : true,
				typeAhead : true,
				allowBlank : false,
				resizable : true,
				editable : false,
				anchor : '90%',
				listeners : {
					afterRender : function(combo) {
						combo.setValue(27);
					}
				}
			});
	popCombo.on('load', function() {
				popCombo.setValue(27);
			});
	var genreStore2 = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['0', '0.全部'], ['6021', '6021.报刊杂志'],
						['6015', '6015.财务'], ['6006', '6006.参考'],
						['6010', '6010.导航'], ['6002', '6002.工具'],
						['6013', '6013.健康健美'], ['6017', '6017.教育'],
						['6003', '6003.旅行'], ['6023', '6023.美食佳饮'],
						['6016', '6016.娱乐'], ['6000', '6000.商业'],
						['6022', '6022.商品指南'], ['6005', '6005.社交'],
						['6008', '6008.摄影与录像'], ['6012', '6012.生活'],
						['6004', '6004.体育'], ['6001', '6001.天气'],
						['6018', '6018.图书'], ['6007', '6007.效率'],
						['6009', '6009.新闻'], ['6020', '6020.医疗'],
						['6011', '6011.音乐'], ['6014', '6014.游戏']]
			});
	/**
	 * 设备分类
	 */
	var popStore2 = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['27', 'iPhone'], ['44', 'iPad']]
			});
	var popCombo2 = new Ext.form.ComboBox({
				id : 'popCombo2',
				fieldLabel : '设备',
				emptyText : '请选择设备类型',
				triggerAction : 'all',
				store : popStore2,
				hiddenName : 'popId',
				valueField : 'ID',
				displayField : 'NAME',
				loadingText : '正在加载数据...',
				mode : 'local', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
				forceSelection : true,
				typeAhead : true,
				allowBlank : false,
				resizable : true,
				editable : false,
				anchor : '90%',
				listeners : {
					afterRender : function(combo) {
						combo.setValue(27);
					}
				}
			});
	popCombo2.on('load', function() {
				popCombo2.setValue(27);
			});
	var record = new Ext.data.Record.create([{
				name : 'appId'
			}, {
				name : 'appName'
			}, {
				name : 'rankTime'
			}, {
				name : 'ranking'
			}]);
	var hisTopChartStore = new Ext.data.JsonStore({
				fields : ['appId', 'appName', 'rankTime', 'ranking'],
				data : []
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
							curTopStore.reload({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
						}
					}
				}
			});

	var curTopStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../appletop/currtop?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'genre'
								}, {
									name : 'genreId'
								}, {
									name : 'ranking'
								}, {
									name : 'appId'
								}, {
									name : 'popId'
								}, {
									name : 'appName'
								}]),
				autoLoad : true
			});
	// 分页带上查询条件
	curTopStore.on('beforeload', function() {
				var appIdForm = Ext.getCmp('appId2');
				var genreCombo = Ext.getCmp('genreCombo2');
				var popCombo = Ext.getCmp('popCombo2');
				var appId = appIdForm.getValue();
				var popId = popCombo.getValue();
				var genreId = genreCombo.getValue();
				this.baseParams = {
					appId : appId,
					popId : popId,
					genreId : genreId
				};
			});
	var sm = new Ext.grid.CheckboxSelectionModel();
	var rownums = new Ext.grid.RowNumberer({
				header : 'NO',
				locked : true
			})
	var cm = new Ext.grid.ColumnModel([rownums, sm, {
				header : 'ID',
				dataIndex : 'appId',
				width : 120
			}, {
				header : 'APP',
				dataIndex : 'appName',
				width : 150
			}, {
				header : '分类',
				dataIndex : 'genre',
				width : 100
			}, {
				header : '排名',
				dataIndex : 'ranking',
				width : 100
			}, {
				header : '设备',
				dataIndex : 'popId',
				width : 100
			}, {
				header : '分类ID',
				dataIndex : 'genreId',
				width : 100
			}, {
				header : '排名走势图',
				width : 100,
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='查看'/>";
					return returnStr;
				}
			}]);
	// // 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : curTopStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext]
			});

	// 定义grid表格
	var currTopGrid = new Ext.grid.GridPanel({
		title : 'App实时排行信息',
		id : 'currTopGrid',
		split : true,
		stripeRows : true, // 斑马线
		frame : true,
		store : curTopStore,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		bbar : bbar,
		sm : sm,
		cm : cm,
		tbar : [new Ext.form.NumberField({
							id : 'appId2',
							name : 'appId2',
							fieldLabel : 'appId',
							emptyText : '请输入AppId'
						}), '-', new Ext.form.ComboBox({
							id : 'genreCombo2',
							name : 'genreCombo2',
							fieldLabel : '分类',
							emptyText : '请选择App分类',
							triggerAction : 'all',
							store : genreStore2,
							hiddenName : 'genreId',
							valueField : 'ID',
							displayField : 'NAME',
							loadingText : '正在加载数据...',
							mode : 'local', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
							forceSelection : true,
							typeAhead : true,
							allowBlank : false,
							resizable : true,
							editable : false,
							anchor : '90%'
						}), '-', popCombo2, '-', {
					text : '查询',
					iconCls : 'arrow_refreshIcon',
					handler : function() {
						var appIdForm = Ext.getCmp('appId2');
						var genreCombo = Ext.getCmp('genreCombo2');
						var popCombo = Ext.getCmp('popCombo2');
						var appId = appIdForm.getValue();
						var popId = popCombo.getValue();
						var genreId = genreCombo.getValue();
						curTopStore.setBaseParam('appId', appId);
						curTopStore.setBaseParam('popId', popId);
						curTopStore.setBaseParam('genreId', genreId);
						curTopStore.load();
					}
				}],
		onCellClick : function(grid, rowIndex, columnIndex, e) {
			var butname = e.target.defaultValue;
			var record = grid.getStore().getAt(rowIndex);
			var data = record.data;
			if (butname == '查看') {
				var appId = data.appId;
				var popId = data.popId;
				var genreId = data.genreId;
				var data = getHisTopData(appId, popId, genreId, new Date(),
						null);
				hisTopChartStore.loadData(data);
				var appIdForm = Ext.getCmp('appId');
				var genreCombo = Ext.getCmp('genreCombo');
				var popCombo = Ext.getCmp('popCombo');
				var startCreateDateForm = Ext.getCmp('startCreateDateForm').form;
				appIdForm.setValue(appId);
				genreCombo.setValue(genreId);
				popCombo.setValue(popId);
				startCreateDateForm.findField("startCreateDate")
						.setValue(renderDateHis(new Date()));
				tabs.setActiveTab(1);
			}
		}
	});
	currTopGrid.on('cellclick', currTopGrid.onCellClick, currTopGrid);
	var hisTopChart = new Ext.Panel({
		// iconCls : 'chart',
		title : 'App排行走势图',
		width : 450,
		autoScroll : true,
		frame : true,
		split : true,
		layout : 'fit',
		tbar : [new Ext.form.NumberField({
							id : 'appId',
							name : 'appId',
							fieldLabel : 'appId',
							emptyText : '请输入AppId'
						}), '-', new Ext.form.ComboBox({
							id : 'genreCombo',
							fieldLabel : '分类',
							emptyText : '请选择App分类',
							triggerAction : 'all',
							store : genreStore,
							hiddenName : 'genreId',
							valueField : 'ID',
							displayField : 'NAME',
							loadingText : '正在加载数据...',
							mode : 'local', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
							forceSelection : true,
							typeAhead : true,
							allowBlank : false,
							resizable : true,
							editable : false,
							anchor : '90%'
						}), '-', popCombo, '-', new Ext.form.FormPanel({
							id : 'startCreateDateForm',
							name : 'startCreateDateForm',
							items : [calendarCmp('startCreateDate',
									'startCreateDate', '开始时间')]
						}), '-', new Ext.form.FormPanel({
							id : 'endCreateDateForm',
							name : 'endCreateDateForm',
							items : [calendarCmp('endCreateDate',
									'endCreateDate', '结束时间')]
						}), '-', {
					text : '查询',
					iconCls : 'arrow_refreshIcon',
					handler : function() {
						var appIdForm = Ext.getCmp('appId');
						var genreCombo = Ext.getCmp('genreCombo');
						var popCombo = Ext.getCmp('popCombo');
						var appId = appIdForm.getValue();
						if (Ext.isEmpty(appId)) {
							Ext.MessageBox.alert('提示', 'App代码不能为空');
							return;
						}
						var genreId = genreCombo.getValue();
						if (Ext.isEmpty(genreId)) {
							Ext.MessageBox.alert('提示', '分类不能为空');
							return;
						}
						var popId = popCombo.getValue();
						var startCreateDateForm = Ext
								.getCmp('startCreateDateForm').form;
						var endCreateDateForm = Ext.getCmp('endCreateDateForm').form;
						var startDate = startCreateDateForm
								.findField("startCreateDate").getValue();
						if (Ext.isEmpty(startDate)) {
							Ext.MessageBox.alert('提示', '开始时间必须填写');
							return;
						}
						var endDate = endCreateDateForm
								.findField("endCreateDate").getValue();
						var data = getHisTopData(appId, popId, genreId,
								startDate, endDate);
						hisTopChartStore.loadData(data);
					}
				}],
		items : {
			// xtype : 'columnchart',
			xtype : 'linechart',
			id : 'currLineChart',
			name : 'currLineChart',
			store : hisTopChartStore,
			animate : true,
			shadow : true,
			url : '../js/extjs3/resources/charts.swf',
			xField : 'rankTime',
			yField : 'ranking',
			yAxis : new Ext.chart.NumericAxis({
						displayName : '排名位置',
						majorUnit : 20,
						adjustMaximumByMajorUnit : true,
						calculateByLabelSize : true,
						reverse : true
					}),
			tipRenderer : function(chart, record) {
				var appName = record.get('appName');
				var ranking = record.get('ranking');
				return '【' + appName + '】   排名:' + ranking;
			},
			chartStyle : {
				padding : 10,
				animationEnabled : true,
				font : {
					name : 'Tahoma',
					color : 0x444444,
					size : 11
				},
				dataTip : {
					padding : 5,
					border : {
						color : 0x99bbe8,
						size : 1
					},
					background : {
						color : 0xDAE7F6,
						alpha : .9
					},
					font : {
						name : 'Tahoma',
						color : 0x15428B,
						size : 10,
						bold : true
					}
				},
				xAxis : {
					color : 0x69aBc8,
					majorTicks : {
						color : 0x69aBc8,
						length : 4
					},
					minorTicks : {
						color : 0x69aBc8,
						length : 2
					},
					majorGridLines : {
						size : 1,
						color : 0xeeeeee
					}
				},
				yAxis : {
					color : 0x69aBc8,
					majorTicks : {
						color : 0x69aBc8,
						length : 4
					},
					minorTicks : {
						color : 0x69aBc8,
						length : 2
					},
					majorGridLines : {
						size : 1,
						color : 0xdfe8f6
					}
				}
			},
			series : [{// 列
				type : 'line', // 类型可以改变（线）
				displayName : '排名位置',
				yField : 'ranking',
				style : {
					color : '#FF6600'
				}
			}]
		}
	});

	/**
	 * 获取历史排名的JSON数据
	 */
	function getHisTopData(appId, popId, genreId, startCreateDate,
			endCreateDate) {
		var data = [];
		Ext.Ajax.request({
					url : '../appletop/histop?_time=' + new Date().getTime(),
					params : {
						'appId' : appId,
						'popId' : popId,
						'genreId' : genreId,
						'startCreateDate' : startCreateDate,
						'endCreateDate' : Ext.isEmpty(endCreateDate)
								? null
								: endCreateDate
					},
					async : false,
					success : function(response) {
						var result = Ext.decode(response.responseText);
						data = result;
					},
					failure : function() {
					}
				});
		return data;
	}
	var tabs = new Ext.TabPanel({
				id : 'tabs',
				frame : true,
				region : 'center',
				activeTab : 0,
				height : 500,
				// autoHeight:true,
				// collapsible:true,
				items : [currTopGrid, hisTopChart]
			});
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [tabs]
			});
});