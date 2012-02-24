/**
 * 队列信息查询
 */
Ext.onReady(function() {
	// 定义表格数据源
	var store = new Ext.data.Store({
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : '../queuemoitor/grid?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'normalSize'
								}, {
									name : 'registerSize'
								}, {
									name : 'instantSize'
								}])
			});
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				locked : true,
				width : 28
			});

	// 定义锁定列模型
	var cm = new Ext.grid.ColumnModel([rownum, {
				header : '普通任务',
				dataIndex : 'normalSize',
				width : 100
			}, {
				header : '注册任务',
				dataIndex : 'registerSize',
				width : 80
			}, {
				header : '实时任务',
				dataIndex : 'instantSize',
				width : 80
			}]);

	// 定义grid表格
	var grid = new Ext.grid.GridPanel({
				region : 'center',
				id : 'grid',
				height : 500,
				stripeRows : true, // 斑马线
				frame : true,
				// autoWidth : true,
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
						}, {
							text : '清空普通任务',
							iconCls : 'deleteIcon',
							handler : function() {
								removeQueue('normal')
							}
						}, {
							text : '清空注册任务',
							iconCls : 'deleteIcon',
							handler : function() {
								removeQueue('register')
							}
						}, {
							text : '清空实时任务',
							iconCls : 'deleteIcon',
							handler : function() {
								removeQueue('instant')
							}
						}]
			});
	/**
	 * 清空队列
	 */
	function removeQueue(qtype) {
		Ext.Msg.show({
					title : '确认信息',
					msg : '确定清空?',
					buttons : Ext.Msg.YESNO,
					fn : function(ans) {
						if (ans == 'yes') {
							Ext.Ajax.request({
										url : '../queuemoitor/remove?_time'
												+ new Date().getTime(),
										params : {
											qtype : qtype
										},
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											if (result.success) {
												Ext.Msg.alert("提示", "删除成功");
											} else {
												Ext.Msg.alert("提示", "删除失败");
											}
											store.reload();
										},
										failure : function() {
											Ext.Msg.alert("提示", "删除失败");
											store.reload();
										}
									});
						}
					}
				});
	}

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [grid]
			});
	// 定义任务
	var task = {
		run : function() {
			store.reload()
		},
		interval : 10000,
		scope : this
	};
	// 定时执行任务
	Ext.TaskMgr.start(task);
});
