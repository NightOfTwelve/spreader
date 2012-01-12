/**
 * 策略配置列表页面 实现所有策略的简单列表，点击某一行记录跳转到详细配置页面
 */
// 定义表格数据源
var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : '../strategy/stggridstore'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'TOTALCOUNT',
						root : 'data'
					}, [{
								name : 'name'
							}, {
								name : 'displayName'
							}, {
								name : 'description'
							}]),
			autoLoad : true
		});
// 定义表格列CM
var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
			header : '策略编号',
			dataIndex : 'name',
			width : 100
		}, {
			header : '策略名称',
			dataIndex : 'displayName',
			width : 100
		}, {
			header : '说明',
			dataIndex : 'description',
			width : 100
		}, {
			header : '相关操作',
			renderer : function showbutton() {
				var returnStr = "<input type='button' value='配置'/>";
				return returnStr;
			},
			width : 100
		}]);
// 分页菜单
var bbar = new Ext.PagingToolbar({
			pageSize : 10,
			store : store,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录"
		});
// 定义grid表格
var stglistgrid = new Ext.grid.GridPanel({
			region : 'center',
			height : 500,
			autoWidth : true,
			autoScroll : true,
			split : true,
			store : store,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			stripeRows : true,
			frame : true,
			// autoExpandColumn : 'remark',
			cm : cm,
			tbar : [{
						text : '刷新',
						iconCls : 'arrow_refreshIcon',
						handler : function() {
							store.reload();
						}
					}],
			bbar : bbar,
			onCellClick : function(grid, rowIndex, columnIndex, e) {
				// 找出表格中‘配置’按钮
				if (e.target.defaultValue == '配置') {
					var record = grid.getStore().getAt(rowIndex);
					var data = record.data;
					var disnames = record.data.displayName;
					GDISNAME = data.displayName;
					GOBJID = data.name;
					editstgWindow.title = disnames;
					editstgWindow.show();
				}
			}

		});
// 注册事件
stglistgrid.on('cellclick', stglistgrid.onCellClick, stglistgrid);
// 创建策略维护的窗口组件
var editstgWindow = new Ext.Window({
			layout : 'border',
			width : document.documentElement.clientWidth - 200,
			height : document.documentElement.clientHeight - 200,
			resizable : true,
			draggable : true,
			closeAction : 'hide',
			title : '<span class="commoncss">策略详细配置</span>',
			iconCls : 'app_rightIcon',
			modal : true,
			collapsible : true,
			maximizable : true,
			animCollapse : true,
			animateTarget : document.head,
			buttonAlign : 'right',
			constrain : true,
			border : false,
			items : [{
						region : 'center',
						id : 'pptgridmanage',
						header : false,
						// TODO
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
					}],
			buttons : [{
						text : '关闭',
						iconCls : 'deleteIcon',
						handler : function() {
							editstgWindow.hide();
						}
					}]
		});
// show事件，需先删除组件，再重新创建PPTGRID
editstgWindow.on('show', function() {
			stgtree.getRootNode().reload();
			stgtree.root.select();
			var pptGrid = Ext.getCmp("pptGrid");
			var pptMgr = Ext.getCmp("pptgridmanage");
			if (pptGrid != null) {
				pptMgr.remove(pptGrid);
			}
			pptMgr.doLayout();
		});
