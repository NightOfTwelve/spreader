// 执行结果列表的store
var jobResultStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : '../taskresult/queryjobresult?_time='
								+ new Date().getTime()
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'totalCount',
						root : 'list'
					}, [{
								name : 'id'
							}, {
								name : 'jobId'
							}, {
								name : 'jobName'
							}, {
								name : 'startTime'
							}, {
								name : 'endTime'
							}, {
								name : 'status'
							}, {
								name : 'result'
							}])
		});

// 定义Checkbox
var resultgridsm = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			listeners : {
				// 选择事件
				rowselect : function(model, rowIndex, record) {
					var resultId = record.get('id');
					// 先清除缓存
					taskDtlStore.removeAll();
					taskDtlStore.setBaseParam('resultId', resultId);
					taskDtlStore.reload();
				}
			}
		});

var resultgridcm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
		resultgridsm, {
			header : 'ID',
			dataIndex : 'id',
			width : 80
		}, {
			header : '调度编号',
			dataIndex : 'jobId',
			width : 80
		}, {
			header : '名称',
			dataIndex : 'jobName',
			width : 80
		}, {
			header : '任务状态',
			dataIndex : 'status',
			renderer : renderJobResultStatus,
			width : 100
		}, {
			header : '执行结果',
			dataIndex : 'result',
			renderer : renderBrief,
			width : 100
		}, {
			header : '发起时间',
			dataIndex : 'startTime',
			renderer : renderDateHis,
			width : 100
		}, {
			header : '结束时间',
			dataIndex : 'endTime',
			renderer : renderDateHis,
			width : 100
		}]);

var jobResultNumber = 20;
var jobResultNumtext = new Ext.form.TextField({
			id : 'jobResultNumtext',
			name : 'jobResultNumtext',
			width : 60,
			emptyText : '每页条数',
			// 激活键盘事件
			enableKeyEvents : true,
			listeners : {
				specialKey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
						jobResultBbar.pageSize = parseInt(jobResultNumtext
								.getValue());
						jobResultNumber = parseInt(jobResultNumtext.getValue());
						jobResultStore.reload({
									params : {
										start : 0,
										limit : jobResultBbar.pageSize
									}
								});
					}
				}
			}
		});

var jobResultBbar = new Ext.PagingToolbar({
			pageSize : jobResultNumber,
			store : jobResultStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', jobResultNumtext]
		});
// 执行结果列表
var jobResultGrid = new Ext.grid.GridPanel({
			region : 'north',
			split : true,
			height : 230,
			id : 'jobResultGrid',
			store : jobResultStore,
			cm : resultgridcm,
			sm : resultgridsm,
			bbar : jobResultBbar
		})
// 统计任务明细的store
var taskDtlStore = new Ext.data.GroupingStore({
			proxy : new Ext.data.HttpProxy({
						url : '../taskresult/resultdtl?_time='
								+ new Date().getTime()
					}),
			reader : new Ext.data.JsonReader({
						root : 'list'
					}, [{
								name : 'id'
							}, {
								name : 'taskCode'
							}, {
								name : 'bid'
							}, {
								name : 'startTime'
							}, {
								name : 'handleTime'
							}, {
								name : 'status'
							}, {
								name : 'uid'
							}, {
								name : 'createTime'
							}, {
								name : 'resultId'
							}, {
								name : 'traceLink'
							}]),
			groupField : 'status',
			autoLoad : true
		});
// TASK CM
var taskgridcm = new Ext.grid.ColumnModel([{
			header : 'ID',
			dataIndex : 'id',
			width : 100
		}, {
			header : '任务代码',
			dataIndex : 'taskCode',
			width : 100
		}, {
			header : '任务状态',
			dataIndex : 'status',
			renderer : renderTaskStatus,
			width : 80
		}, {
			header : '发起时间',
			dataIndex : 'startTime',
			renderer : renderDateHis,
			width : 100
		}, {
			header : '返回时间',
			dataIndex : 'handleTime',
			renderer : renderDateHis,
			width : 100
		}, {
			header : '创建时间',
			dataIndex : 'createTime',
			renderer : renderDateHis,
			width : 100
		}, {
			header : 'uid',
			dataIndex : 'uid',
			width : 80
		}]);

var taskBbar = new Ext.PagingToolbar({
			pageSize : 20,
			store : taskDtlStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录"
		});
// Task统计列表
var taskGrid = new Ext.grid.GridPanel({
	region : 'center',
	split : true,
	id : 'taskGrid',
	store : taskDtlStore,
	cm : taskgridcm,
	view : new Ext.grid.GroupingView({
		forceFit : true,
		startCollapsed : true, // 默认收起
		groupTextTpl : '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Task"]})'
	}),
	bbar : taskBbar
		// ,
		// fbar : ['->', {
		// text : 'Clear Grouping',
		// iconCls : 'icon-clear-group',
		// handler : function() {
		// taskDtlStore.clearGrouping();
		// }
		// }]
})
// task
var taskWindow = new Ext.Window({
	title : '<span class="commoncss">执行情况查询</span>', // 窗口标题
	id : 'taskWindow',
	closeAction : 'hide',
	modal : true,
	layout : 'border', // 设置窗口布局模式
	width : 600, // 窗口宽度
	height : 500, // 窗口高度
	// closable : true, // 是否可关闭
	collapsible : true, // 是否可收缩
	maximizable : true, // 设置是否可以最大化
	border : false, // 边框线设置
	constrain : true, // 设置窗口是否可以溢出父容器
	pageY : 20, // 页面定位Y坐标
	pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
	items : [jobResultGrid, taskGrid]
		// 嵌入的表单面板
	});
taskWindow.on('show', function() {
			jobResultStore.reload();
			taskDtlStore.removeAll();
		});