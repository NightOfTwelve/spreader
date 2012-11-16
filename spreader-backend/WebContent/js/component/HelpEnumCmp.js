/**
 * 获取帮助组件
 */
function getHelpEnumCmp() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../help/enumstore?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'enumName'
								}, {
									name : 'enumValues'
								}, {
									name : 'sortId'
								}]),
				autoLoad : true
			});
	var number = 20;
	var numtext = new Ext.form.TextField({
				id : 'helpMaxpage',
				name : 'helpMaxpage',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							bbar.pageSize = Number(numtext.getValue());
							number = Number(numtext.getValue());
							store.reload({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
						}
					}
				}
			});
	// // 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext]
			});
	var grid = new Ext.grid.GridPanel({
				store : store,
				margins : '0 5 5 5',
				bbar : bbar,
				tbar : [{
							iconCls : 'page_refreshIcon',
							text : '刷新',
							handler : function() {
								store.load();
							}
						}, '-', new Ext.form.TextField({
									id : 'helpEnumQueryName',
									name : 'helpEnumQueryName',
									emptyText : '请输入分类名称',
									enableKeyEvents : true,
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												var text = field.getValue();
												store.setBaseParam('enumName',
														text);
												store.load();
											}
										}
									},
									width : 130
								}), '-', {
							text : '查询',
							iconCls : 'page_refreshIcon',
							handler : function() {
								var text = Ext.getCmp('helpEnumQueryName')
										.getValue();
								store.setBaseParam('enumName', text);
								store.load();
							}
						}],
				columns : [new Ext.grid.RowNumberer(), {
							header : '信息分类',
							dataIndex : 'enumName',
							width : 100,
							sortable : true
						}, {
							header : '分类说明',
							dataIndex : 'enumValues',
							width : 150,
							sortable : false
						}]
			});
	// 弹出窗口
	var windows = new Ext.Window({
		title : '<span class="commoncss">帮助</span>', // 窗口标题
		id : 'helpEnumCmbWindow',
		closeAction : 'hide',
		modal : true,
		layout : 'fit', // 设置窗口布局模式
		width : 350, // 窗口宽度
		height : 250, // 窗口高度
		closable : true, // 是否可关闭
		collapsible : true, // 是否可收缩
		maximizable : true, // 设置是否可以最大化
		border : false, // 边框线设置
		constrain : true, // 设置窗口是否可以溢出父容器
		pageY : 20, // 页面定位Y坐标
		pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
		items : [grid]
			// 嵌入的表单面
		});
	return windows;
}