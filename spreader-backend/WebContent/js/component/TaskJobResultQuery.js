var taskIdHidden = new Ext.form.Hidden({
			name : 'taskIdHidden'
		});
var statusHidden = new Ext.form.Hidden({
			name : 'statusHidden'
		});
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
					taskIdHidden.setValue(resultId);
					// 先清除缓存
					statusCountStore.removeAll();
					statusCountStore.setBaseParam('resultId', resultId);
					statusCountStore.load();
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
			renderer : renderHtmlBrief,
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
// var taskDtlStore = new Ext.data.GroupingStore({
// proxy : new Ext.data.HttpProxy({
// url : '../taskresult/resultdtl?_time='
// + new Date().getTime()
// }),
// reader : new Ext.data.JsonReader({
// root : 'list'
// }, [{
// name : 'id'
// }, {
// name : 'taskCode'
// }, {
// name : 'bid'
// }, {
// name : 'startTime'
// }, {
// name : 'handleTime'
// }, {
// name : 'status'
// }, {
// name : 'uid'
// }, {
// name : 'createTime'
// }, {
// name : 'resultId'
// }, {
// name : 'traceLink'
// }]),
// groupField : 'status',
// autoLoad : true
// });
/**
 * 统计任务状态
 */
var statusCountStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : '../taskresult/statuscount?_time='
								+ new Date().getTime()
					}),
			reader : new Ext.data.JsonReader({
						root : 'list'
					}, [{
								name : 'status'
							}, {
								name : 'cnt'
							}]),
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

var taskgridcm2 = new Ext.grid.ColumnModel([{
			header : '任务状态',
			dataIndex : 'status',
			renderer : renderTaskStatus,
			width : 80
		}, {
			header : '数量',
			dataIndex : 'cnt',
			width : 100
		}]);

// var taskBbar = new Ext.PagingToolbar({
// pageSize : 20,
// store : taskDtlStore,
// displayInfo : true,
// displayMsg : '显示{0}条到{1}条,共{2}条',
// emptyMsg : "没有符合条件的记录"
// });
// Task统计列表
var taskGrid = new Ext.grid.GridPanel({
			region : 'west',
			width : 150,
			split : true,
			id : 'taskGrid',
			store : statusCountStore,
			cm : taskgridcm2,
			// ,
			// view : new Ext.grid.GroupingView({
			// forceFit : true,
			// startCollapsed : true, // 默认收起
			// groupTextTpl : '{text} ({[values.rs.length]} {[values.rs.length >
			// 1 ?
			// "Items" : "Task"]})'
			// }),
			// bbar : taskBbar
			// ,
			// fbar : ['->', {
			// text : 'Clear Grouping',
			// iconCls : 'icon-clear-group',
			// handler : function() {
			// taskDtlStore.clearGrouping();
			// }
			// }]
			onCellClick : function(grid, rowIndex, columnIndex, e) {
				var butname = e.target.defaultValue;
				var record = grid.getStore().getAt(rowIndex);
				var data = record.data;
				var status = data.status;
				var taskId = taskIdHidden.getValue();
				if (status != 0) {
					resultGrid.colModel.setHidden(5, true);
				} else {
					resultGrid.colModel.setHidden(5, false);
				}
				if (status != 2) {
					resultGrid.colModel.setHidden(6, true);
				} else {
					resultGrid.colModel.setHidden(6, false);
				}
				statusHidden.setValue(status);
				resultStore.setBaseParam('resultId', taskId);
				resultStore.setBaseParam('status', status);
				resultStore.load();
			}
		});
taskGrid.on('cellclick', taskGrid.onCellClick, taskGrid);

var resultStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : '/spreader-front/taskexecue/taskresult?_time='
								+ new Date().getTime()
					}),
			reader : new Ext.data.JsonReader({
						root : 'list',
						totalProperty : 'totalCount'
					}, [{
								name : 'uid'
							}, {
								name : 'clientId'
							}, {
								name : 'clientTaskId'
							}, {
								name : 'taskCode'
							}, {
								name : 'executedTime'
							}, {
								name : 'executeStatus'
							}, {
								name : 'errorCode'
							}])
		});

// 翻页排序时带上查询条件
resultStore.on('beforeload', function() {
			this.baseParams = {
				resultId : taskIdHidden.getValue(),
				status : statusHidden.getValue()
			};
		});
var resultcm = new Ext.grid.ColumnModel([{
			header : '任务ID',
			dataIndex : 'clientTaskId',
			width : 100
		}, {
			header : '用户ID',
			dataIndex : 'uid',
			width : 100
		}, {
			header : '任务代码',
			dataIndex : 'taskCode',
			renderer : rendDispNameWorkShopFn,
			width : 100
		}, {
			header : '客户端',
			dataIndex : 'clientId',
			renderer : renderTaskStatus,
			width : 80
		}, {
			header : '执行时间',
			dataIndex : 'executedTime',
			renderer : renderDateHis,
			width : 100
		}, {
			header : '返回状态',
			dataIndex : 'executeStatus',
			renderer : renderAssignTaskResultStatus,
			width : 100
		}, {
			header : '错误代码',
			dataIndex : 'errorCode',
			renderer : rendErrorCodeMsgFn,
			width : 100
		}]);

var resultBbar = new Ext.PagingToolbar({
			pageSize : 20,
			store : resultStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录"
		});
// Task统计列表
var resultGrid = new Ext.grid.GridPanel({
			region : 'center',
			split : true,
			id : 'resultGrid',
			store : resultStore,
			cm : resultcm,
			bbar : resultBbar,
			listeners : {
				'rowdblclick' : function(grid, rowIndex, e) {
					var buttons = e.target.defaultValue;
					var record = grid.getStore().getAt(rowIndex);
					var data = record.data;
					var taskCode = data.taskCode;
					var clientTaskId = data.clientTaskId;
					var viewData = getResultData(clientTaskId);
					if (excludeTaskCodeFn(taskCode)) {
						if (taskCode == 'replyWeibo') {
							settingTaskCommentWeiboForm(viewData);
						}
					} else {
						settingTaskDefaultSource(viewData);
					}
				}
			}
		});

//resultGrid.on('dblclick', resultGrid.onCellClick, resultGrid);
// task
var taskWindow = new Ext.Window({
	title : '<span class="commoncss">执行情况查询</span>', // 窗口标题
	id : 'taskWindow',
	closeAction : 'hide',
	modal : true,
	layout : 'border', // 设置窗口布局模式
	width : 750, // 窗口宽度
	height : 500, // 窗口高度
	// closable : true, // 是否可关闭
	collapsible : true, // 是否可收缩
	maximizable : true, // 设置是否可以最大化
	border : false, // 边框线设置
	constrain : true, // 设置窗口是否可以溢出父容器
	pageY : 20, // 页面定位Y坐标
	pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
	items : [jobResultGrid, {
				region : 'center',
				layout : 'border',
				items : [taskGrid, resultGrid]
			}]
		// 嵌入的表单面板
	});
taskWindow.on('show', function() {
			jobResultStore.removeAll();
			jobResultStore.reload();
			statusCountStore.removeAll();
			resultStore.removeAll();
		});
/**
 * 渲染任务名称
 * 
 * @param {}
 *            value
 * @return {}
 */
function rendDispNameWorkShopFn(value) {
	var list = {
		'fetchWeiboStarUser' : '爬取明星微博',
		'fetchWeiboUserMainPage' : '爬取用户主页',
		'fetchUserAttentions' : '爬取用户关注',
		'registerWeibo' : '注册微博',
		'activeWeibo' : '激活微博帐号',
		'fetchWeiboContent' : '爬取指定微博',
		'postWeiboContent' : '发微博',
		'addUserFans' : '加粉',
		'forwardWeiboContent' : '转发微博',
		'replyWeibo' : '回复微博',
		'uploadAvatar' : '上传头像',
		'addWeiboTag' : '添加微博标签',
		'updateRobotUserInfo' : '更新机器人信息',
		'firstTimeGuide' : '首次引导',
		'weiboAppeal' : '微博申诉',
		'confirmWeiboAppeal' : '确认微博申诉',
		'fetchKeywordEntrance' : '爬取关键字入口',
		'fetchKeyword' : '爬取关键字',
		'fetchKeywordContent' : '根据关键字爬取内容',
		'fetchNotice' : '爬取消息',
		'registerApple' : '注册苹果帐号',
		'activeApp' : '激活应用',
		'downloadApp' : '下载应用',
		'registerWebApple' : '网页注册苹果',
		'registerCnApple' : '苹果中国区注册',
		'commentApple' : '评论-苹果'
	};
	for (var idx in list) {
		var tmp = list[idx];
		if (value == idx) {
			return tmp;
		}
	}
}

function excludeTaskCodeFn(value) {
	var list = {
		'replyWeibo' : '回复微博'
	};
	for (var idx in list) {
		var tmp = list[idx];
		if (value == idx) {
			return tmp;
		}
	}
}
/**
 * 获取contents
 * 
 * @param {}
 *            id
 * @return {}
 */
function getResultData(id) {
	var data = null;
	Ext.Ajax.request({
				url : '/spreader-front/taskexecue/contents?_time='
						+ new Date().getTime(),
				params : {
					'clientTaskId' : id
				},
				async : false,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					data = result;
				},
				failure : function() {
				}
			});
	return data;
}