/**
 * 策略配置列表页面 实现所有策略的简单列表，点击某一行记录跳转到详细配置页面
 */
// 定义表格数据源
var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : './organization.ered?reqCode=queryDeptsForManage'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'TOTALCOUNT',
						root : 'ROOT'
					}, [{
								name : 'deptid'
							}])
		});
// 定义表格列CM
var cm = new Ext.grid.ColumnModel([{
			header : '策略名称',
			dataIndex : 'deptname',
			width : 100
		}, {
			header : '显示列1',
			dataIndex : 'customid',
			width : 100
		}, {
			header : '显示列2',
			dataIndex : 'parentdeptname',
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
//			title : '<span class="commoncss">策略配置列表</span>',
			// iconCls : 'buildingIcon',
			height : 500,
			autoWidth : true,
			autoScroll : true,
			region : 'center',
			store : store,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			// stripeRows : true,
			frame : true,
			// autoExpandColumn : 'remark',
			cm : cm,
			tbar : [{
						text : '新增',
						iconCls : 'page_addIcon',
						handler : function() {
							addInit();
						}
					}, '-', {
						text : '修改',
						iconCls : 'page_edit_1Icon',
						handler : function() {
							editInit();
						}
					}, '-', {
						text : '删除',
						iconCls : 'page_delIcon',
						handler : function() {
							deleteDeptItems('1', '');
						}
					}, {
						text : '查询',
						iconCls : 'previewIcon',
						handler : function() {
							queryDeptItem();
						}
					}, '-', {
						text : '刷新',
						iconCls : 'arrow_refreshIcon',
						handler : function() {
							store.reload();
						}
					}],
			bbar : bbar
		});
