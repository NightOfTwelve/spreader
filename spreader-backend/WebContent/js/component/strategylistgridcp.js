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
			header : '策略名称',
			dataIndex : 'name',
			width : 100
		}, {
			header : '显示列1',
			dataIndex : 'displayName',
			width : 100
		}, {
			header : '显示列2',
			dataIndex : 'description',
			width : 100
		}, {
			header : '相关操作',
			renderer : function showbutton(value) {
				var returnStr = "<INPUT type='button' value='配置' onclick=showbutton("+value+")>";
				return returnStr;
			},
			width : 100
		}]);
// 按钮触发事件
var showbutton = function(value) {
	alert("value:"+value);
}
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
			// title : '<span class="commoncss">策略配置列表</span>',
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
