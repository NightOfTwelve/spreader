Ext.onReady(function() {

	var paramsLab = new Ext.form.Label({
				fieldLabel : '详细参数',
				labelStyle : 'padding:0px',
				id : 'paramDtl',
				text : ''
			});
	var paramWin = new Ext.Window({
				title : '<span class="commoncss">请求参数</span>', // 窗口标题
				layout : 'fit', // 设置窗口布局模式
				width : 500, // 窗口宽度
				height : 300, // 窗口高度
				closable : false, // 是否可关闭
				collapsible : false, // 是否可收缩
				maximizable : true, // 设置是否可以最大化
				border : false, // 边框线设置
				constrain : true, // 设置窗口是否可以溢出父容器
				draggable : false,
				animateTarget : Ext.getBody(),
				pageY : 20, // 页面定位Y坐标
				pageX : document.documentElement.clientWidth / 2 - 700 / 2, // 页面定位X坐标
				items : [paramsLab], // 嵌入的表单面板
				buttons : [{ // 窗口底部按钮配置
					text : '关闭', // 按钮文本
					iconCls : 'deleteIcon', // 按钮图标
					handler : function() { // 按钮响应函数
						paramWin.hide();
					}
				}]
			});

	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../log/logs?_time=' + new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'opName'
								}, {
									name : 'url'
								}, {
									name : 'params'
								}, {
									name : 'createTime'
								}, {
									name : 'accountId'
								}]),
				autoLoad : true
			});
	// 页数
	var number = 20;
	var numtext = new Ext.form.TextField({
				id : 'maxpage',
				name : 'maxpage',
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

	var form = new Ext.form.FormPanel({
		region : 'north',
		title : "筛选条件",
		// collapsible : true,
		frame : true,
		id : 'form',
		// border : true,
		labelWidth : 90, // 标签宽度
		frame : true,
		labelAlign : 'right',
		bodyStyle : 'padding:5 5 5 5',
		buttonAlign : 'center',
		height : 180,
		items : [{ // 行1
			layout : "column",
			items : [{
						columnWidth : .33,
						layout : "form",
						items : [calendarCmp('startTime', 'startTime', '日志起始时间')]
					}, {
						columnWidth : .33,
						layout : "form",
						items : [calendarCmp('endTime', 'endTime', '日志结束时间')]
					}, {
						columnWidth : .3,
						layout : "form",
						items : [{
									xtype : "textfield",
									fieldLabel : "操作帐号",
									name : 'accountId'
								}]
					}]
		}],
		buttonAlign : "center",
		buttons : [{
					text : "查询",
					handler : function() { // 按钮响应函数
						var tform = form.getForm();
						var startTime = tform.findField("startTime").getValue();
						var endTime = tform.findField("endTime").getValue();
						var accountId = tform.findField("accountId").getValue();
						store.setBaseParam('startTime', startTime);
						store.setBaseParam('endTime', endTime);
						store.setBaseParam('accountId', accountId);
						store.load();
					}
				}, {
					text : "重置",
					handler : function() { // 按钮响应函数
						form.form.reset();
					}
				}]
	});
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				locked : true,
				width : 28
			});

	// 定义锁定列模型
	var cm = new Ext.grid.ColumnModel([rownum, {
				header : '编号',
				dataIndex : 'id',
				width : 80
			}, {
				header : '操作账户',
				dataIndex : 'accountId',
				width : 100
			}, {
				header : '操作',
				dataIndex : 'opName',
				width : 180
			}, {
				header : '请求URL',
				dataIndex : 'url',
				width : 180
			}, {
				header : 'params',
				dataIndex : 'params',
				hidden : true,
				width : 10
			}, {
				header : '查看参数',
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='查看'/>";
					return returnStr;
				},
				width : 100
			}, {
				header : '记录时间',
				dataIndex : 'createTime',
				renderer : renderDateHis,
				width : 120
			}]);
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
				bbar : bbar,
				// sm : sm,
				colModel : cm,
				tbar : [{
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								store.reload();
							}
						}],
				onCellClick : function(grid, rowIndex, columnIndex, e) {
					var button = e.target.defaultValue;
					var record = grid.getStore().getAt(rowIndex);
					var data = record.data;
					var params = data.params;
					if (button == '查看') {
						Ext.getCmp('paramDtl').setText(params);
						paramWin.show();
					}
				}
			});

	// 注册事件
	grid.on('cellclick', grid.onCellClick, grid);

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [form, grid]
			});
});