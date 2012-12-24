var taskDefaultGrid = new Ext.grid.PropertyGrid({});

/**
 * 评论显示窗口
 */
var taskDefaultWindow = new Ext.Window({
			title : '默认任务属性菜单',
			layout : 'fit',
			y : 50,
			modal : true,
			resizable : true,
			width : 600,
			height : 300,
			closeAction : 'hide',
			plain : true,
			items : [taskDefaultGrid]
		});
/**
 * 设置显示区域
 * 
 * @param {}
 *            data
 * @return {}
 */
function settingTaskDefaultSource(data) {
	taskDefaultGrid.setSource(data);
	// TODO 这里要格式化日期
//	for(var p in data){
//		var x = data[p];
//		Ext.isEmpty(x);
//	}
	taskDefaultWindow.show();
}
