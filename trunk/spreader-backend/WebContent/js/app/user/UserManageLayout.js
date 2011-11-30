/**
 * 用户管理的布局，只包含一个TABS
 */
Ext.onReady(function() {
			var stgdispviewport = new Ext.Viewport({
						layout : 'border',
						items : [{
									region : 'center',
									id : 'usermaintabs',
									header : false,
									collapsible : true,
									split : true,
//									width : 1000,
									height : 500,
									items : [stgdisplistgrid]
								}]
					});
		});