/**
 * 策略管理的布局JS 使用Ext.Viewport分为WEST,CENTER两块 左侧为选择树形结构
 * 点击树的NODE会出现详细的GRID显示在CENTER块
 */
Ext.onReady(function() {
			var stviewport = new Ext.Viewport({
						layout : 'border',
						items : [{
									region : 'center',
									id : 'pptgridmanage',
									header : false,
									collapsible : true,
									split : true,
									height : 100
								}, {
									region : 'west',
									// title : '选择配置',
									split : true,
									width : 200,
									minWidth : 175,
									maxWidth : 400,
									items : [stgtree]
								}]
					});
		});