/**
 * 主页面的布局JS 使用Ext.Viewport分为NORTH,SOUTH,WEST,CENTER四块 左侧为导航菜单的树形结构
 * 点击树的NODE会出现详细的TabPanel显示在CENTER块
 */
Ext.onReady(function() {
	// 主布局设置
	var mainviewport = new Ext.Viewport({
				layout : 'border',
				items : [{
					region : 'north',
					title : '营销管理系统 <font color="blue">'
							+ new Date().format('Y-m-d')
							+ ' <span id="rTime"><span></font>',
					frame : true,
					html : '<br><center><font size=6>那里营销后台管理系统</font></center>',
					// html:'<img border="0" width="450"
					// height="70"src="$StaticConfig.u('../images/logo.jpg')"
					// ></img>',
					// collapsible : true,
					// autoEl: {
					// tag: 'div',
					// html:'<td style="padding-right:5px"><table width="100%"
					// border="0" cellpadding="0" cellspacing="3"
					// class="banner"><tr align="right"><td><br><center><font
					// size=6>那里营销后台管理系统</font></center><font color="blue">'+new
					// Date().format('Y-m-d')+' <span
					// id="rTime"><span></font></td></tr><tr align="right"><td>'
					// },
					border : false,
					layout : 'fit'
						// autoHeight : true
				}, {
					region : 'center',
					header : false,
					// collapsible : true,
					split : true,
					autoScroll : false,
					layout : 'fit',
					items : [mainTabPanel]
				}, {
					region : 'west',
					title : '系统导航',
					split : true,
					collapsible : true,
					autoHeight : true,
					width : 200,
					// minWidth : 175,
					// maxWidth : 400,
					items : [accordPanel]
				}
				// , {
				// region : 'south',
				// title : '联系我们',
				// collapsible : true,
				// collapsed : true,
				// height : 100
				// }
				]
			});
});