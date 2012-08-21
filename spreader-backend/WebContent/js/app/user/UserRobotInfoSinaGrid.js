Ext.onReady(function() {
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
									sinaUserRobotStore.reload({
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
			var userRobotSinaForm = new Ext.form.FormPanel({
						region : 'north',
						title : "筛选条件",
						// collapsible : true,
						frame : true,
						id : 'userRobotSinaForm',
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
													xtype : "textfield",
													fieldLabel : "用户昵称",
													name : 'nickName'
												}]
									}, {
										columnWidth : .3,
										layout : "form",
										items : [{
													xtype : "textfield",
													fieldLabel : "地区",
													name : 'province'
												}]
									}]
						}],
						buttonAlign : "center",
						buttons : [{
							text : "查询",
							handler : function() { // 按钮响应函数
								var tform = userRobotSinaForm.getForm();
								var nickName = tform.findField("nickName")
										.getValue();
								var province = tform.findField("province")
										.getValue();
								var num = numtext.getValue();
								sinaUserRobotStore.setBaseParam('nickName', Ext
												.isEmpty(nickName)
												? null
												: nickName);
								sinaUserRobotStore.setBaseParam('province', Ext
												.isEmpty(province)
												? null
												: province);
								sinaUserRobotStore.setBaseParam('limit', Ext
												.isEmpty(num)
												? number
												: Number(num));
								sinaUserRobotStore.load();
							}
						}, {
							text : "重置",
							handler : function() { // 按钮响应函数
								userRobotSinaForm.form.reset();
							}
						}]
					});
			// ///GRID
			/**
			 * 用户信息列表
			 */
			// 定义表格数据源
			var sinaUserRobotStore = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : '../rbtregist/robotlist'
								}),
						reader : new Ext.data.JsonReader({
									totalProperty : 'totalCount',
									root : 'list'
								}, [{
											name : 'id'
										}, {
											name : 'nickName'
										}, {
											name : 'gender'
										}, {
											name : 'province'
										}, {
											name : 'firstName '
										}, {
											name : 'lastName'
										}, {
											name : 'firstNamePinyin'
										}, {
											name : 'email'
										}, {
											name : 'lastNamePinyin'
										}, {
											name : 'enName'
										}, {
											name : 'introduction'
										}, {
											name : 'city'
										}, {
											name : 'birthdayYear'
										}, {
											name : 'birthdayMonth'
										}, {
											name : 'birthdayDay'
										}, {
											name : 'constellation'
										}, {
											name : 'school'
										}, {
											name : 'career'
										}, {
											name : 'pwd'
										}, {
											name : 'updateTime'
										}]),
						autoLoad : {
							params : {
								start : 0,
								limit : 20
							}
						}
					});
			// 分页带上查询条件
			// sinaUserRobotStore.on('beforeload', function() {
			// var pfrom = userRobotSinaForm.getForm();
			// var pnickName = pfrom.findField("nickName").getValue();
			// var pminFans = pfrom.findField("minFans").getValue();
			// var pmaxFans = pfrom.findField("maxFans").getValue();
			// var pminRobotFans = pfrom.findField("minRobotFans")
			// .getValue();
			// var pmaxRobotFans = pfrom.findField("maxRobotFans")
			// .getValue();
			// var ptag = pfrom.findField("tag").getValue();
			// var isRobot = pfrom.findField("isRobot").getValue();
			// var limit = numtext.getValue();
			// this.baseParams = {
			// nickName : Ext.isEmpty(pnickName)
			// ? null
			// : pnickName,
			// minFans : pminFans,
			// maxFans : pmaxFans,
			// minRobotFans : pminRobotFans,
			// maxRobotFans : pmaxRobotFans,
			// tag : Ext.isEmpty(ptag) ? null : ptag,
			// limit : Ext.isEmpty(limit) ? number : Number(limit),
			// isRobot : isRobot
			// };
			// });

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
						header : '地区',
						dataIndex : 'province',
						width : 100
					}, {
						header : '星座',
						dataIndex : 'constellation',
						width : 100
					}, {
						header : 'email',
						dataIndex : 'email',
						width : 100
					}, {
						header : '自我介绍',
						dataIndex : 'introduction',
						renderer : renderBrief,
						width : 100
					}]);
			// // 分页菜单
			var bbar = new Ext.PagingToolbar({
						pageSize : number,
						store : sinaUserRobotStore,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						emptyMsg : "没有符合条件的记录",
						plugins : new Ext.ux.ProgressBarPager(),
						items : ['-', '&nbsp;&nbsp;', numtext]
					});

			// 定义grid表格
			var sinaUserRobotGrid = new Ext.grid.GridPanel({
				region : 'center',
				id : 'sinaUserRobotGrid',
				height : 440,
				stripeRows : true, // 斑马线
				frame : true,
				// autoWidth : true,
				autoScroll : true,
				store : sinaUserRobotStore,
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
								sinaUserRobotStore.reload();
							}
						}]
				});

			// 布局模型
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [userRobotSinaForm, sinaUserRobotGrid]
					});
		});