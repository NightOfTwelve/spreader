
/**
 * 运行状态的COMB的数据源
 */
var statTypeStore = new Ext.data.ArrayStore({
			fields : ['ID', 'NAME'],
			data : [['-1', '----------------------'], ['1', '正常'], ['2', '异常']]
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
						sinaUserStore.reload({
									params : {
										start : 0,
										limit : bbar.pageSize
									}
								});
					}
				}
			}
		});
/**
 * 用户信息列表的查询FORM
 */
var userSinaForm = new Ext.form.FormPanel({
			// region : 'north',
			title : "筛选条件",
			// collapsible : true,
			frame : true,
			id : 'userSinaForm',
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
							items : [{
										fieldLabel : '运行状态',
										// name:'TIMEFER',
										xtype : 'combo',
										store : statTypeStore,
										id : 'runstat',
										hiddenName : 'runstat',
										valueField : 'ID',
										editable : false,
										displayField : 'NAME',
										mode : 'local',
										forceSelection : false,// 必须选择一项
										emptyText : '运行状态...',// 默认值
										triggerAction : 'all'
									}]
						}, {
							columnWidth : .33,
							layout : "form",
							items : [{
										xtype : "textfield",
										fieldLabel : "用户昵称",
										name : 'nickName'
									}]
						}, {
							columnWidth : .3,
							layout : "form",
							items : [{
										xtype : "textfield",
										fieldLabel : "分类",
										name : 'tag'
									}]
						}]
			}, {	// 行2
						layout : "column",
						items : [{
									columnWidth : .33,
									layout : "form",
									items : [{
												xtype : "numberfield",
												fieldLabel : "粉丝数大于",
												name : 'minFans'
											}]
								}, {
									columnWidth : .33,
									layout : "form",
									items : [{
												xtype : "numberfield",
												fieldLabel : "粉丝数小于",
												name : 'maxFans'
											}]
								}]
					}, { // 行3
						layout : "column",
						items : [{
									columnWidth : .33,
									layout : "form",
									items : [{
												xtype : "numberfield",
												fieldLabel : "机器人粉丝数大于",
												name : 'minRobotFans'
											}]
								}, {
									columnWidth : .33,
									layout : "form",
									items : [{
												xtype : "numberfield",
												fieldLabel : "机器人粉丝数小于",
												name : 'maxRobotFans'
											}]
								}]
					}],
			buttonAlign : "center",
			buttons : [{
				text : "查询",
				handler : function() { // 按钮响应函数
					var tform = userSinaForm.getForm();
					var nickName = tform.findField("nickName").getValue();
					var minFans = tform.findField("minFans").getValue();
					var maxFans = tform.findField("maxFans").getValue();
					var minRobotFans = tform.findField("minRobotFans")
							.getValue();
					var maxRobotFans = tform.findField("maxRobotFans")
							.getValue();
					var tag = tform.findField("tag").getValue();
					var num = numtext.getValue();
					sinaUserStore.setBaseParam('nickName', Ext
									.isEmpty(nickName) ? null : nickName);
					sinaUserStore.setBaseParam('minFans', minFans);
					sinaUserStore.setBaseParam('maxFans', maxFans);
					sinaUserStore.setBaseParam('minRobotFans', minRobotFans);
					sinaUserStore.setBaseParam('maxRobotFans', maxRobotFans);
					sinaUserStore.setBaseParam('tag', Ext.isEmpty(tag)
									? null
									: tag);
					sinaUserStore.setBaseParam('limit', Ext.isEmpty(num)
									? number
									: Number(num));
					sinaUserStore.load();
				}
			}, {
				text : "重置",
				handler : function() { // 按钮响应函数
					userSinaForm.form.reset();
				}
			}]
		});
// ///GRID
/**
 * 用户信息列表
 */
// 定义表格数据源
var sinaUserStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : '../userinfo/userlist'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'totalCount',
						root : 'list'
					}, [{
								name : 'id'
							}, {
								name : 'isRobot'
							}, {
								name : 'nickName'
							}, {
								name : 'gender'
							}, {
								name : 'realName'
							}, {
								name : 'fans'
							}, {
								name : 'robotFans'
							}, {
								name : 'articles'
							}, {
								name : 'email'
							}, {
								name : 'region'
							}, {
								name : 'spaceEntry'
							}, {
								name : 'introduction'
							}, {
								name : 'qq'
							}, {
								name : 'msn'
							}, {
								name : 'blog'
							}, {
								name : 'tag'
							}]),
			autoLoad : {
				params : {
					start : 0,
					limit : 20
				}
			}
		});
// 分页带上查询条件
sinaUserStore.on('beforeload', function() {
			var pfrom = userSinaForm.getForm();
			var pnickName = pfrom.findField("nickName").getValue();
			var pminFans = pfrom.findField("minFans").getValue();
			var pmaxFans = pfrom.findField("maxFans").getValue();
			var pminRobotFans = pfrom.findField("minRobotFans").getValue();
			var pmaxRobotFans = pfrom.findField("maxRobotFans").getValue();
			var ptag = pfrom.findField("tag").getValue();
			var limit = numtext.getValue();
			this.baseParams = {
				nickName : Ext.isEmpty(pnickName) ? null : pnickName,
				minFans : pminFans,
				maxFans : pmaxFans,
				minRobotFans : pminRobotFans,
				maxRobotFans : pmaxRobotFans,
				tag : Ext.isEmpty(ptag) ? null : ptag,
				limit : Ext.isEmpty(limit) ? number : Number(limit)
			};
		});

// 定义Checkbox
var sm = new Ext.grid.CheckboxSelectionModel();
var rownums = new Ext.grid.RowNumberer({
			header : 'NO',
			locked : true
		})
// 定义表格列CM
var cm = new Ext.ux.grid.LockingColumnModel([rownums, {
			header : '编号',
			dataIndex : 'id',
			locked : true,
			width : 80
		}, {
			header : '机器人',
			dataIndex : 'isRobot',
			locked : true,
			renderer : rendIsRobot,
			width : 80
		}, {
			header : '昵称',
			dataIndex : 'nickName',
			locked : true,
			width : 100
		}, {
			header : '性别',
			dataIndex : 'gender',
			renderer : renderGender,
			width : 80
		}, {
			header : '真实姓名',
			dataIndex : 'realName',
			width : 100
		}, {
			header : '分类',
			dataIndex : 'tag',
			renderer : renderBrief,
			width : 100
		}, {
			header : '粉丝数',
			dataIndex : 'fans',
			width : 100,
			sortable : true
		}, {
			header : '机器人粉丝数',
			dataIndex : 'robotFans',
			width : 100,
			sortable : true
		}, {
			header : '文章数',
			dataIndex : 'articles',
			width : 100,
			sortable : true
		}, {
			header : 'email',
			dataIndex : 'email',
			width : 100
		}, {
			header : '地区',
			dataIndex : 'region',
			width : 100
		}, {
			header : '个人主页',
			dataIndex : 'spaceEntry',
			width : 100
		}, {
			header : '自我介绍',
			dataIndex : 'introduction',
			renderer : renderBrief,
			width : 100
		}, {
			header : 'QQ',
			dataIndex : 'qq',
			width : 100
		}, {
			header : 'MSN',
			dataIndex : 'msn',
			width : 100
		}, {
			header : '博客',
			dataIndex : 'blog',
			width : 100
		}]);
// // 分页菜单
var bbar = new Ext.PagingToolbar({
			pageSize : number,
			store : sinaUserStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			plugins : new Ext.ux.ProgressBarPager(),
			items : ['-', '&nbsp;&nbsp;', numtext]
		});

// 定义grid表格
var sinaUserGrid = new Ext.grid.GridPanel({
			// region : 'center',
			id : 'sinaUserGrid',
			height : 440,
			stripeRows : true, // 斑马线
			frame : true,
			// autoWidth : true,
			autoScroll : true,
			store : sinaUserStore,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			bbar : bbar,
			// sm : sm,
			cm : cm,
			view : new Ext.ux.grid.LockingGridView(), // 锁定列视图
			tbar : [{
						text : '刷新',
						iconCls : 'arrow_refreshIcon',
						handler : function() {
							sinaUserStore.reload();
						}
					}],
			onCellClick : function(grid, rowIndex, columnIndex, e) {
				var selesm = grid.getSelectionModel().getSelections();
				var userid = selesm[0].data.id;
				var nickname = selesm[0].data.nickName;
				var fanscol = grid.getColumnModel().getDataIndex(columnIndex);
				if (fanscol == 'fans') {
					realFansDtlWin.title = nickname + '的粉丝';
					realFansDtlWin.show();
					GFANSID = userid;
					userFansStore.setBaseParam('id', userid);
					userFansStore.setBaseParam('isRobot', false);
					userFansStore.load();
				} else if (fanscol == 'robotFans') {
					robotFansDtlWin.show();
					GROBOTID = userid;
					userRobotFansStore.setBaseParam('id', userid);
					userRobotFansStore.setBaseParam('isRobot', false);
					userRobotFansStore.load();
				}
			}

		});

// 注册事件
sinaUserGrid.on('cellclick', sinaUserGrid.onCellClick, sinaUserGrid);

// 粉丝信息列表
var realFansDtlWin = new Ext.Window({
			title : '<span class="commoncss">粉丝列表</span>', // 窗口标题
			iconCls : 'imageIcon',
			layout : 'fit', // 设置窗口布局模式
			width : 600, // 窗口宽度
			height : 500, // 窗口高度
			// tbar : tb, // 工具栏
			closable : false, // 是否可关闭
			closeAction : 'hide', // 关闭策略
			collapsible : true, // 是否可收缩
			maximizable : false, // 设置是否可以最大化
			modal : true,
			border : false, // 边框线设置
			pageY : 120, // 页面定位Y坐标
			pageX : document.documentElement.clientWidth / 2 - 400 / 2, // 页面定位X坐标
			constrain : true,
			items : [sinaUserFansGrid],
			// 设置窗口是否可以溢出父容器
			buttonAlign : 'center',
			buttons : [{
						text : '关闭',
						iconCls : 'deleteIcon',
						handler : function() {
							realFansDtlWin.hide();
						}
					}]
		});
// 机器人粉丝
var robotFansDtlWin = new Ext.Window({
			title : '<span class="commoncss">机器人粉丝列表</span>', // 窗口标题
			iconCls : 'imageIcon',
			layout : 'fit', // 设置窗口布局模式
			width : 600, // 窗口宽度
			height : 500, // 窗口高度
			// tbar : tb, // 工具栏
			closable : false, // 是否可关闭
			closeAction : 'hide', // 关闭策略
			collapsible : true, // 是否可收缩
			maximizable : false, // 设置是否可以最大化
			modal : true,
			border : false, // 边框线设置
			pageY : 120, // 页面定位Y坐标
			pageX : document.documentElement.clientWidth / 2 - 400 / 2, // 页面定位X坐标
			constrain : true,
			items : [sinaUserRobotFansGrid],
			// 设置窗口是否可以溢出父容器
			buttonAlign : 'center',
			buttons : [{
						text : '关闭',
						iconCls : 'deleteIcon',
						handler : function() {
							robotFansDtlWin.hide();
						}
					}]
		});
