/**
 * 策略调度列表的布局JS 统一使用Ext.Viewport分为north,CENTER两块 上侧为查询条件 下方为具体的列表显示
 */
Ext.onReady(function() {
			var stgdispviewport = new Ext.Viewport({
						layout : 'border',
						items : [stgdispgridform, stgdisplistgrid]
					});
		});