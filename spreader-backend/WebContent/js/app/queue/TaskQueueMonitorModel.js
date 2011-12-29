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
								}]
					});

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
