/**
 * 策略列表的布局JS 统一使用Ext.Viewport分为north,CENTER两块 上侧为查询条件 下方为具体的列表显示
 */
Ext.onReady(function() {
			var stgridviewport = new Ext.Viewport({
						layout : 'border',
						items : [{
									region : 'north',
//									title : '查询条件',
									split : true,
									width : 1000,
									height : 100,
//									minWidth : 175,
//									maxWidth : 400,
									items : [stggridform]
								}, {
									region : 'center',
									id : 'stglistgridly',
									header : false,
									collapsible : true,
									split : true,
									width : 1000,
									height : 500,
									items : [stglistgrid]
								}]
					});
		});