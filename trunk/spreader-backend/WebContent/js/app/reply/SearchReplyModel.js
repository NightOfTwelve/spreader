Ext.onReady(function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../reply/search?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'reply'
								}, {
									name : 'score'
								}])
			});

	// 定义Checkbox
	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true
			});
	// 定义自动当前页行号
	var rm = new Ext.grid.RowNumberer({
				header : 'NO.',
				locked : true,
				width : 28
			});

	// 定义锁定列模型
	var cm = new Ext.grid.ColumnModel([{
				header : '回复内容',
				dataIndex : 'reply',
				sortable : true,
				width : 450
			}, {
				header : '系统评分',
				dataIndex : 'score',
				sortable : true,
				width : 100
			}]);

	// 定义grid表格
	var grid = new Ext.grid.GridPanel({
				region : 'center',
				id : 'grid',
				stripeRows : true, // 斑马线
				frame : true,
				title : '回复搜索',
				split : true,
				autoScroll : true,
				store : store,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				colModel : cm,
				tbar : [{
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								store.reload();
							}
						}, '-', new Ext.form.TextField({
									id : 'weibo',
									name : 'weibo',
									fieldLabel : '微博',
									emptyText : '请输入微博内容',
									allowBlank : false,
									enableKeyEvents : true,
									width : 130
								}), '-', new Ext.form.TextField({
									id : 'rows',
									name : 'rows',
									fieldLabel : '显示的行数',
									emptyText : '10',
									enableKeyEvents : true,
									width : 130
								}), '-', {
							text : '查询记录',
							iconCls : 'page_findIcon',
							handler : function() {
								var weibo = Ext.getCmp('weibo').getValue();
								var rows = Ext.getCmp('rows').getValue();
								if (Ext.isEmpty(weibo)) {
									Ext.Msg.alert("提示", "请输入要搜索的内容");
									return;
								}
								store.setBaseParam('weibo', weibo);
								store.setBaseParam('rows', rows);
								store.load();
							}
						}, '-', new Ext.form.TextField({
									id : 'cookies',
									name : 'cookies',
									fieldLabel : 'cookies',
									emptyText : '输入cookies',
									enableKeyEvents : true,
									width : 200
								}), '-', {
							text : '设置cookies',
							iconCls : 'page_findIcon',
							handler : function() {
								var cookies = Ext.getCmp('cookies').getValue();
								if (Ext.isEmpty(cookies)) {
									Ext.Msg.alert("提示", "请输入cookies");
									return;
								}
								// TODO 数据访问
								Ext.Ajax.request({
											url : '../reply/cookies?_time'
													+ new Date().getTime(),
											params : {
												cookies : cookies
											},
											success : function(response) {
												var result = Ext
														.decode(response.responseText);
												if (result.success) {
													Ext.Msg.alert("提示", "设置成功");
												} else {
													Ext.Msg.alert("提示", "设置失败");
												}
											},
											failure : function() {
												Ext.Msg.alert("提示", "设置失败");
											}
										});
								Ext.getCmp('cookies').setValue(null);
								return;
							}
						}]
			});

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [grid]
			});
});