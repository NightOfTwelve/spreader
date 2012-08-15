Ext.onReady(function() {
	// 消息分类
	var noticeTypeStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['1', '评论微博'], ['2', '评论回复'], ['3', '新增粉丝'],
						['4', '私信'], ['5', '@到我的微博'], ['6', '@到我的评论'],
						['7', '群组消息'], ['8', '相册消息']]
			});
	/**
	 * 消息列表查询form
	 */
	var form = new Ext.form.FormPanel({
				region : 'north',
				// title : "筛选条件",
				// collapsible : true,
				frame : true,
				id : 'noticeForm',
				// border : true,
				labelWidth : 90, // 标签宽度
				frame : true,
				labelAlign : 'right',
				split : true,
				bodyStyle : 'padding:5 5 5 5',
				buttonAlign : 'center',
				height : 100,
				items : [{
							layout : "column",
							items : [{
										columnWidth : .3,
										layout : "form",
										items : [{
													fieldLabel : '消息分类',
													xtype : 'combo',
													width : 100,
													store : noticeTypeStore,
													id : 'noticeType',
													hiddenName : 'noticeType',
													valueField : 'ID',
													editable : false,
													displayField : 'NAME',
													mode : 'local',
													forceSelection : false,// 必须选择一项
													emptyText : '消息分类...',// 默认值
													triggerAction : 'all'
												}]
									}, {
										columnWidth : .2,
										layout : "form",
										items : [selectUserComboUtil]
									}]
						}],
				buttonAlign : "center",
				buttons : [{
					text : "查询",
					handler : function() { // 按钮响应函数
						var tform = form.getForm();
						var noticeType = tform.findField("noticeType")
								.getValue();
						var uid = tform.findField("id").getValue();
						store.setBaseParam('noticeType', noticeType);
						store.setBaseParam('toUid', uid);
						store.load();
					}
				}, {
					text : "重置",
					handler : function() { // 按钮响应函数
						categoryQueryForm.form.reset();
					}
				}]
			});
	// 定义表格数据源
	var store = new Ext.data.Store({
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : '../notice/noticelist?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'fromUserName'
								}, {
									name : 'toUserName'
								}, {
									name : 'noticeId'
								}, {
									name : 'fromUserId'
								}, {
									name : 'toUserId'
								}, {
									name : 'contentId'
								}, {
									name : 'replayId'
								}, {
									name : 'noticeType'
								}, {
									name : 'fromWebsiteUid'
								}, {
									name : 'replayContent'
								}, {
									name : 'quoteContent'
								}, {
									name : 'atCommentContent'
								}])
			});
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				locked : true,
				width : 28
			});
	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true
			});
	// 定义锁定列模型
	var cm = new Ext.grid.ColumnModel([sm, rownum, {
				header : '消息ID',
				dataIndex : 'noticeId',
				width : 80
			}, {
				header : '消息类型',
				dataIndex : 'noticeType',
				renderer : renderNoticeType,
				width : 80
			}, {
				header : '消息来源ID',
				dataIndex : 'fromUserId',
				hidden : true,
				width : 80
			}, {
				header : '消息来源人',
				dataIndex : 'fromUserName',
				width : 80
			}, {
				header : '消息目标人ID',
				dataIndex : 'toUserId',
				hidden : true,
				width : 80
			}, {
				header : '消息目标人',
				dataIndex : 'toUserName',
				width : 80
			}, {
				header : '查看详情',
				renderer : function showbutton(value, cellmeta, record,
						rowIndex, columnIndex, store) {
					var noticeType = record.data['noticeType'];
					var returnStr = "";
					// 如果是新增粉丝则显示回粉按钮
					if (noticeType == 3) {
						returnStr = "<input type='button' value='回粉'/>";
					} else {
						returnStr = "<input type='button' value='详情'/>";
					}
					return returnStr;
				},
				width : 100
			}, {
				header : '查看策略',
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='策略'/>";
					return returnStr;
				},
				width : 100
			}]);

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
				cm : cm,
				sm : sm,
				tbar : [{
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								store.reload();
							}
						}],
				onCellClick : function(grid, rowIndex, columnIndex, e) {
					var buttons = e.target.defaultValue;
					var record = grid.getStore().getAt(rowIndex);
					var data = record.data;
					var noticeId = data.noticeId;
					var fromUserId = data.fromUserId;
					var toUserId = data.toUserId;
					if (buttons == '详情') {
						var noticeType = data.noticeType;
						if (noticeType == 1) {
							var replayId = data.replayId;
							var viewData = getCommentReplay(replayId);
							settingCommentWeiboForm(viewData);
						}
						if (noticeType == 2) {
							var replayId = data.replayId;
							var viewData = getCommentReplay(replayId);
							settingCommentForm(viewData);
						}
						if (noticeType == 5) {
							var viewData = getAtWeibo(noticeId);
							settingAtWeiForm(viewData);
						}
					}
					if (buttons == '回粉') {
						cleanHidden();
						noticeIdHidden.setValue(noticeId);
						addFansAction(toUserId, fromUserId);
					}
					if (buttons == '策略') {
						strategyStore.setBaseParam('noticeId', noticeId);
						strategyStore.load();
						strategyWindow.show();
					}
				}
			});
	// 注册事件
	grid.on('cellclick', grid.onCellClick, grid);
	// 策略数据源
	var strategyStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../strategydisp/noticestrategy'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'name'
								}, {
									name : 'triggerInfo'
								}, {
									name : 'description'
								}])
			})
	// 定义Checkbox
	var strategySm = new Ext.grid.CheckboxSelectionModel();
	// 定义表格列CM
	var strategyCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm,
			{
				header : '调度编号',
				dataIndex : 'id',
				width : 80
			}, {
				header : '调度名称',
				dataIndex : 'name',
				renderer : rendDispNameFn,
				width : 100
			}, {
				header : '调度备注',
				dataIndex : 'triggerInfo',
				renderer : renderHtmlBrief,
				width : 100
			}, {
				header : '描述',
				dataIndex : 'description',
				renderer : renderBrief,
				width : 100
			}, {
				header : '相关操作',
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='配置'/>";
					return returnStr;
				},
				width : 100
			}, {
				header : '执行情况',
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='执行情况'/>";
					return returnStr;
				},
				width : 100
			}]);
	// 页数
	var strategyNumber = 20;
	var strategyNumtext = new Ext.form.TextField({
				id : 'strategymaxpage',
				name : 'strategymaxpage',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							bbar.pageSize = parseInt(strategyNumtext.getValue());
							strategyNumber = parseInt(strategyNumtext
									.getValue());
							strategyStore.reload({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
						}
					}
				}
			});

	// 分页菜单
	var strategyBbar = new Ext.PagingToolbar({
				pageSize : strategyNumber,
				store : strategyStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', strategyNumtext]
			});
	// 子策略列表
	var strategyGrid = new Ext.grid.GridPanel({
				height : 500,
				autoWidth : true,
				autoScroll : true,
				split : true,
				region : 'center',
				store : strategyStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				// stripeRows : true,
				frame : true,
				// autoExpandColumn : 'remark',
				sm : strategySm,
				cm : strategyCm,
				tbar : [{
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								strategyStore.reload();
							}
						}],
				bbar : strategyBbar,
				onCellClick : function(grid, rowIndex, columnIndex, e) {
					var butname = e.target.defaultValue;
					var record = grid.getStore().getAt(rowIndex);
					var data = record.data;
					// 找出表格中‘配置’按钮
					if (butname == '配置') {
						strategyIdHidden.setValue(data.name);
						objIdHidden.setValue(data.id);
						isGroupHidden.setValue(false);
						groupTypeHidden.setValue(3);
						editstgWindow.show();
					}
					if (butname == '执行情况') {
						var jobId = data.id;
						jobResultStore.setBaseParam('jobId', jobId);
						jobResultStore.setBaseParam('groupType', 'simple');
						jobResultStore.reload();
						taskWindow.show();
					}
				}
			});
	// 注册事件
	strategyGrid.on('cellclick', strategyGrid.onCellClick, strategyGrid);
	var strategyWindow = new Ext.Window({
				layout : 'fit',
				width : document.documentElement.clientWidth - 200,
				height : document.documentElement.clientHeight - 200,
				resizable : true,
				draggable : true,
				closeAction : 'hide',
				title : '<span class="commoncss">子策略列表</span>',
				iconCls : 'app_rightIcon',
				modal : true,
				collapsible : true,
				maximizable : true,
				animCollapse : true,
				animateTarget : document.head,
				buttonAlign : 'right',
				constrain : true,
				border : false,
				items : [strategyGrid],
				buttons : [{
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								strategyWindow.hide();
							}
						}]
			});
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [form, grid]
			});
});
/**
 * 回复操作
 */
function replayAction(replayContentId, toUid, content, needForward) {
	var stgData = {};
	stgData['replayContentId'] = replayContentId;
	stgData['toUid'] = toUid;
	stgData['content'] = content;
	stgData['needForward'] = needForward;
	defaultData.setValue(Ext.util.JSON.encode(stgData));
	strategyIdHidden.setValue('noticeReplayWeibo');
	groupTypeHidden.setValue(3);
	editstgWindow.show();
}
/**
 * 转发操作
 */
function forwardAction(toUid, contentId) {
	var stgData = {};
	stgData['toUid'] = toUid;
	stgData['contentId'] = contentId;
	defaultData.setValue(Ext.util.JSON.encode(stgData));
	strategyIdHidden.setValue('noticeForwardContent');
	groupTypeHidden.setValue(3);
	editstgWindow.show();
}
/**
 * 回粉
 * 
 * @param {}
 *            robotId
 * @param {}
 *            uid
 */
function addFansAction(robotId, uid) {
	var stgData = {};
	stgData['robotId'] = robotId;
	stgData['uid'] = uid;
	defaultData.setValue(Ext.util.JSON.encode(stgData));
	strategyIdHidden.setValue('noticeAddFans');
	groupTypeHidden.setValue(3);
	editstgWindow.show();
}