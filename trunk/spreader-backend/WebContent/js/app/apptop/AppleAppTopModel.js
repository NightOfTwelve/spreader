Ext.QuickTips.init();
Ext.BLANK_IMAGE_URL = '../js/extjs3/resources/images/default/s.gif';
Ext.chart.Chart.CHART_URL = '../js/extjs3/resources/charts.swf';
Ext.onReady(function() {
	var genreStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['0', '全部'], ['6021', '报刊杂志'], ['6015', '财务'],
						['6006', '参考'], ['6010', '导航'], ['6002', '工具'],
						['6013', '健康健美'], ['6017', '教育'], ['6003', '旅行'],
						['6023', '美食佳饮'], ['6016', '娱乐'], ['6000', '商业'],
						['6022', '商品指南'], ['6005', '社交'], ['6008', '摄影与录像'],
						['6012', '生活'], ['6004', '体育'], ['6001', '天气'],
						['6018', '图书'], ['6007', '效率'], ['6009', '新闻'],
						['6020', '医疗'], ['6011', '音乐'], ['6014', '游戏']]
			});
	/**
	 * 设备分类
	 */
	var popStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['27', 'iPhone'], ['44', 'iPad']],
				listeners : {
					load : function() {
						Ext.getCmp('popCombo').setValue(27);
					}
				}
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

	var currTopChart = new Ext.Panel({
		// iconCls : 'chart',
		title : 'App排行走势图',
		region : 'center',
		frame : true,
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
						}), '-', new Ext.form.ComboBox({
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
							anchor : '90%'
						}), '-', new Ext.form.FormPanel({
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
						var endDate = string2Date(endCreateDateForm
								.findField("endCreateDate").getValue());
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
						majorUnit : 10,
						adjustMaximumByMajorUnit : true,
						reverse : true
					}),
			tipRenderer : function(chart, record) {
				var appName = record.get('appName');
				var ranking = record.get('ranking');
				return 'app:' + appName + ',排名:' + ranking;
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
					url : '../appletop/currtop?_time=' + new Date().getTime(),
					params : {
						'appId' : appId,
						'popId' : popId,
						'genreId' : genreId,
						'startCreateDate' : startCreateDate,
						'endCreateDate' : endCreateDate
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
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [currTopChart]
			});
});