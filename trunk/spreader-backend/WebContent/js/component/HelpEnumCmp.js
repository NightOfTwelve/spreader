var helpEnumStore = new Ext.data.Store({
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
	var helpEnumNumber = 20;
	var helpEnumNumtext = new Ext.form.TextField({
				id : 'helpEnumMaxpage',
				name : 'helpEnumMaxpage',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							bbar.pageSize = Number(helpEnumNumtext.getValue());
							helpEnumNumber = Number(helpEnumNumtext.getValue());
							helpEnumStore.reload({
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
	var helpEnumBbar = new Ext.PagingToolbar({
				pageSize : helpEnumNumber,
				store : helpEnumStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', helpEnumNumtext]
			});
	var helpEnumGrid = new Ext.grid.GridPanel({
				store : helpEnumStore,
				margins : '0 5 5 5',
				bbar : helpEnumBbar,
				tbar : [{
							iconCls : 'page_refreshIcon',
							text : '刷新',
							handler : function() {
								helpEnumStore.load();
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
												helpEnumStore.setBaseParam('enumName',
														text);
												helpEnumStore.load();
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
								helpEnumStore.setBaseParam('enumName', text);
								helpEnumStore.load();
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
	var helpEnumwWndows = new Ext.Window({
		title : '<span class="commoncss">帮助</span>', // 窗口标题
		id : 'helpEnumCmbWindow',
		closeAction : 'hide',
		modal : false,
		layout : 'fit', // 设置窗口布局模式
		width : 350, // 窗口宽度
		height : 250, // 窗口高度
		closable : true, // 是否可关闭
		collapsible : true, // 是否可收缩
		maximizable : false, // 设置是否可以最大化
		border : false, // 边框线设置
		constrain : false, // 设置窗口是否可以溢出父容器
		pageY : 20, // 页面定位Y坐标
		pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
		items : [helpEnumGrid]
			// 嵌入的表单面
		});