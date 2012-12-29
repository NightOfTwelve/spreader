var gwebsiteHidden = new Ext.form.Hidden({
			name : 'gwebsiteHidden'
		});

/**
 * 运行状态的COMB的数据源
 */
var isRobotStore = new Ext.data.ArrayStore({
			fields : ['ID', 'NAME'],
			data : [[true, '是'], [false, '否']]
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
			region : 'north',
			title : "筛选条件",
			// collapsible : true,
			frame : true,
			id : 'userSinaForm',
			split : true,
			height : 220,
			// border : true,
			labelWidth : 90, // 标签宽度
			frame : true,
			labelAlign : 'right',
			bodyStyle : 'padding:5 5 5 5',
			buttonAlign : 'center',
			items : [{ // 行1
				layout : "column",
				items : [{
							columnWidth : .33,
							layout : "form",
							items : [{
										fieldLabel : '机器人',
										xtype : 'combo',
										store : isRobotStore,
										id : 'isRobot',
										name : 'isRobot',
										hiddenName : 'isRobot',
										valueField : 'ID',
										editable : false,
										displayField : 'NAME',
										mode : 'local',
										forceSelection : false,// 必须选择一项
										emptyText : '...',// 默认值
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
										fieldLabel : "用户标签",
										name : 'tag'
									}]
						}]
			}, {	// 行2
						layout : "column",
						items : [{
							columnWidth : .33,
							layout : "form",
							items : [webSiteComboUtil('selectWebSiteComboUtil',
									'selectWebSiteComboUtil', null, null)]
						}, {
							columnWidth : .33,
							layout : "form",
							items : [{
										xtype : "numberfield",
										fieldLabel : "粉丝数大于等于",
										name : 'minFans'
									}]
						}, {
							columnWidth : .33,
							layout : "form",
							items : [{
										xtype : "numberfield",
										fieldLabel : "粉丝数小于等于",
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
												fieldLabel : "机器人粉丝数大于等于",
												name : 'minRobotFans'
											}]
								}, {
									columnWidth : .33,
									layout : "form",
									items : [{
												xtype : "numberfield",
												fieldLabel : "机器人粉丝数小于等于",
												name : 'maxRobotFans'
											}]
								}, {
									columnWidth : .33,
									layout : "form",
									items : [{
												xtype : "numberfield",
												fieldLabel : "网站Uid",
												name : 'websiteUid'
											}]
								}]
					}, { // 行4
						layout : "column",
						items : [{
									columnWidth : .33,
									layout : "form",
									items : [{
												xtype : "numberfield",
												fieldLabel : "Uid",
												name : 'id'
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
					var websiteUid = tform.findField("websiteUid").getValue();
					var uid = tform.findField("id").getValue();
					var minRobotFans = tform.findField("minRobotFans")
							.getValue();
					var maxRobotFans = tform.findField("maxRobotFans")
							.getValue();
					var tag = tform.findField("tag").getValue();
					var isRobot = tform.findField("isRobot").getValue();
					var websiteId = Ext.getCmp('selectWebSiteComboUtil')
							.getValue();
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
					sinaUserStore.setBaseParam('isRobot', isRobot);
					sinaUserStore.setBaseParam('websiteId', websiteId);
					sinaUserStore.setBaseParam('websiteUid', websiteUid);
					sinaUserStore.setBaseParam('id', uid);
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
						url : '../userinfo/userlist?_time='
								+ new Date().getTime()
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
								name : 'websiteId'
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
							}, {
								name : 'avatarUrl'
							}, {
								name : 'websiteUid'
							}, {
								name : 'nationality'
							}, {
								name : 'province'
							}, {
								name : 'city'
							}]),
			autoLoad : true
		});
// 分页带上查询条件
sinaUserStore.on('beforeload', function() {
			var pfrom = userSinaForm.getForm();
			var pnickName = pfrom.findField("nickName").getValue();
			var pminFans = pfrom.findField("minFans").getValue();
			var pmaxFans = pfrom.findField("maxFans").getValue();
			var pminRobotFans = pfrom.findField("minRobotFans").getValue();
			var pmaxRobotFans = pfrom.findField("maxRobotFans").getValue();
			var websiteUid = pfrom.findField("websiteUid").getValue();
			var uid = pfrom.findField("id").getValue();
			var ptag = pfrom.findField("tag").getValue();
			var isRobot = pfrom.findField("isRobot").getValue();
			var websiteId = Ext.getCmp('selectWebSiteComboUtil').getValue();
			var limit = numtext.getValue();
			this.baseParams = {
				nickName : Ext.isEmpty(pnickName) ? null : pnickName,
				minFans : pminFans,
				maxFans : pmaxFans,
				minRobotFans : pminRobotFans,
				maxRobotFans : pmaxRobotFans,
				tag : Ext.isEmpty(ptag) ? null : ptag,
				limit : Ext.isEmpty(limit) ? number : Number(limit),
				isRobot : isRobot,
				websiteId : websiteId,
				id : uid,
				websiteUid : websiteUid
			};
		});
// 导入excel文件窗口
var importAccWindows;
// 导入用户分组窗口
var importUserGroupWin;
// 选择用户分组的组件
var groupCombo = getUserGroupCmp('userGroupField', 'userGroupField', 'gid');
// 为Combo加入选择事件
groupCombo.on('select', function(combo, record, index) {
			gwebsiteHidden.setValue(null);
			var gWebsiteId = record.data.websiteId;
			gwebsiteHidden.setValue(gWebsiteId);
		});
// 嵌入的FORM
var userGroupForm = new Ext.form.FormPanel({
	id : 'userGroupForm',
	split : true,
	autoScroll : true,
	height : 150,
	frame : true, // 是否渲染表单面板背景色
	defaultType : 'textfield', // 表单元素默认类型
	labelAlign : 'left', // 标签对齐方式
	buttonAlign : "center",
	bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
	items : [groupCombo],
	buttons : [{
				text : '保存',
				handler : function() {
					var uidArr = [];
					var alertStr = '';
					var gWebsiteId = gwebsiteHidden.getValue();
					var rows = sinaUserGrid.getSelectionModel().getSelections();
					if (rows.length > 0) {
						for (var i = 0; i < rows.length; i++) {
							var uid = rows[i].data.id;
							var uwebsite = rows[i].data.websiteId;
							if (gWebsiteId == uwebsite) {
								uidArr.push(uid);
							} else {
								var uname = rows[i].data.nickName;
								alertStr = alertStr + '【' + uid + ':' + uname
										+ '】,';
							}
						}
					} else {
						Ext.Msg.alert("提示", "请至少选择一个用户");
						return;
					}
					if (!Ext.isEmpty(alertStr)) {
						Ext.Msg.alert("提示", alertStr
										+ '这些用户与分组的所属网站不符，不能导入，请重新选择');
						return;
					}
					var groupId = groupCombo.getValue();
					if (Ext.isEmpty(groupId)) {
						Ext.Msg.alert("提示", '用户分组不能为空');
						return;
					}
					importUserGroup(groupId, uidArr);
					importUserGroupWin.hide();
				}
			}, {
				text : '重置',
				handler : function() {
					userGroupForm.getForm().reset();
				}
			}]
});

// 导入excelForm
var importAccForm = new Ext.form.FormPanel({
	labelWidth : 60,
	fileUpload : true,
	frame : true,
	bodyStyle : 'padding: 10px',
	defaults : {
		anchor : '95%',
		allowBlank : false,
		msgTarget : 'side'
	},
	items : [{
				xtype : 'fileuploadfield',
				id : 'uploadFile',
				emptyText : '请选择数据文件(.xlsx)',
				fieldLabel : '数据文件',
				name : 'file',
				buttonText : '···'
			}],
	buttons : [{
		text : '导入',
		handler : function() {
			if (importAccForm.getForm().isValid()) {
				var uploadForm = Ext.getCmp('uploadFile');
				var fileName = uploadForm.value;
				var extName = /\.[^\.]+$/.exec(fileName);
				if (extName != '.xlsx') {
					Ext.Msg.alert('提示,', '请导入.xlsx文件,目前只支持Office2007以上版本 ');
					importAccForm.getForm().reset();
					return;
				}
				importAccForm.getForm().submit({
					url : '../userinfo/uploadacc?_time=' + new Date().getTime(),
					method : 'POST',
					waitMsg : '上传中...',
					timeout : 600000,
					success : function(form, obj) {
						var count = obj.result.count;
						Ext.Msg.alert('提示,', '成功导入:' + count + '条记录 ');
						form.reset();
						sinaUserStore.load();
					},
					failure : function(tform, action) {
						if (action.failureType === Ext.form.Action.CONNECT_FAILURE) {
							Ext.Msg.alert('连接错误', '状态:'
											+ action.response.status + ': '
											+ action.response.statusText);
						}
						if (action.failureType === Ext.form.Action.SERVER_INVALID) {
							Ext.Msg.alert('服务无效', action.result);
						}
						tform.reset();
					}
				});
				importAccWindows.hide();
			}
		}
	}, {
		text : '重置',
		handler : function() {
			importAccForm.getForm().reset();
		}
	}]
});
// 定义Checkbox
var sm = new Ext.grid.CheckboxSelectionModel();
var rownums = new Ext.grid.RowNumberer({
			header : 'NO',
			locked : true
		})
// 定义表格列CM LockingColumnModel
var cm = new Ext.grid.ColumnModel([rownums, sm, {
			header : '编号',
			dataIndex : 'id',
			// locked : true,
			width : 80
		}, {
			header : '网站编号',
			dataIndex : 'websiteUid',
			// locked : true,
			width : 80
		}, {
			header : '网站分类',
			dataIndex : 'websiteId',
			renderer : renderWebsiteType,
			width : 80
		}, {
			header : '机器人',
			dataIndex : 'isRobot',
			// locked : true,
			renderer : rendTrueFalse,
			width : 80
		}, {
			header : '昵称',
			dataIndex : 'nickName',
			// locked : true,
			width : 100
		}
		// , {
		// header : '头像',
		// dataIndex : 'avatarUrl',
		// renderer : renderImage,
		// locked : true,
		// width : 0
		// }
		, {
			header : '头像',
			dataIndex : 'avatarUrl',
			// renderer : renderImage,
			width : 80
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
			header : '国家',
			dataIndex : 'nationality',
			width : 100
		}, {
			header : '省份',
			dataIndex : 'province',
			width : 100
		}, {
			header : '城市',
			dataIndex : 'city',
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
			region : 'center',
			id : 'sinaUserGrid',
			height : 440,
			stripeRows : true, // 斑马线
			frame : true,
			autoScroll : true,
			store : sinaUserStore,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			bbar : bbar,
			sm : sm,
			cm : cm,
			// view : new Ext.ux.grid.LockingGridView(), // 锁定列视图
			tbar : [{
						text : '刷新',
						iconCls : 'arrow_refreshIcon',
						handler : function() {
							sinaUserStore.reload();
						}
					}, '-', {
						text : '导出用户',
						iconCls : 'downloadIcon',
						handler : expUserInfo
					}, '-', {
						id : 'updateremind',
						name : 'updateremind',
						xtype : 'tbtext',
						text : '<font color = "red">双击表格修改用户信息</font>'
					}, '-', {
						text : '批量修改用户标签',
						iconCls : 'edit1Icon',
						handler : function() {
							updateUserTagWin.show();
						}
					}, '-', {
						text : '导入微博帐号',
						iconCls : 'uploadIcon',
						handler : function() {
							if (!importAccWindows) {
								importAccWindows = new Ext.Window({
											title : '批量导入',
											layout : 'fit',
											width : 400,
											height : 140,
											closeAction : 'hide',
											plain : true,
											items : [importAccForm]
										});
								importAccWindows.on('show', function() {
											importAccForm.getForm().reset();
										});
							}
							importAccWindows.show(this);
						}
					}, '-', {
						text : '导入到指定用户分组',
						iconCls : 'addIcon',
						handler : function() {
							if (!importUserGroupWin) {
								importUserGroupWin = new Ext.Window({
											title : '批量导入',
											layout : 'fit',
											width : 400,
											height : 140,
											closeAction : 'hide',
											plain : true,
											items : [userGroupForm]
										});
								importUserGroupWin.on('show', function() {
											userGroupForm.getForm().reset();
										});
							}
							importUserGroupWin.show(this);
						}
					}],
			onCellClick : function(grid, rowIndex, columnIndex, e) {
				var selesm = grid.getSelectionModel().getSelections();
				if (selesm.length == 0) {
					return;
				}
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
// 性别
var genderStore = new Ext.data.ArrayStore({
			fields : ['ID', 'NAME'],
			data : [['1', '男'], ['2', '女']]
		});
// 嵌入的FORM
var addUserCmbForm = new Ext.form.FormPanel({
			id : 'addUserCmbForm',
			name : 'addUserCmbForm',
			labelWidth : 90, // 标签宽度
			frame : true, // 是否渲染表单面板背景色
			defaultType : 'textfield', // 表单元素默认类型
			labelAlign : 'right', // 标签对齐方式
			bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
			items : [{
						xtype : "displayfield",
						fieldLabel : "用户ID",
						id : 'uid',
						anchor : '100%'
					}, {
						fieldLabel : "昵称",
						name : 'nickName',
						allowBlank : false,
						anchor : '100%'
					}, {
						fieldLabel : '性别',
						name : 'genderCmp',
						xtype : 'combo',
						width : 100,
						store : genderStore,
						id : 'gender',
						hiddenName : 'gender',
						valueField : 'ID',
						editable : false,
						displayField : 'NAME',
						mode : 'local',
						forceSelection : false,// 必须选择一项
						emptyText : '...',// 默认值
						triggerAction : 'all'
					}, {
						fieldLabel : "国家",
						name : 'nationality',
						anchor : '100%'
					}, {
						fieldLabel : "省份",
						name : 'province',
						anchor : '100%'
					}, {
						fieldLabel : "城市",
						name : 'city',
						anchor : '100%'
					}, {
						xtype : "textarea",
						fieldLabel : "个人简介",
						name : 'introduction',
						anchor : '100%'
					}]
		});
var addUserWindow = new Ext.Window({
			title : '<span class="commoncss">用户信息修改</span>', // 窗口标题
			id : 'addUserWindow',
			closeAction : 'hide',
			layout : 'fit', // 设置窗口布局模式
			width : 400, // 窗口宽度
			height : 350, // 窗口高度
			// closable : true, // 是否可关闭
			collapsible : true, // 是否可收缩
			maximizable : true, // 设置是否可以最大化
			border : false, // 边框线设置
			constrain : true, // 设置窗口是否可以溢出父容器
			pageY : 20, // 页面定位Y坐标
			pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
			items : [addUserCmbForm], // 嵌入的表单面板
			buttons : [{ // 窗口底部按钮配置
				text : '确定', // 按钮文本
				iconCls : 'tbar_synchronizeIcon', // 按钮图标
				handler : function() { // 按钮响应函数
					var addForm = addUserCmbForm.getForm();
					var uid = addForm.findField('uid').getValue();
					var nickName = addForm.findField('nickName').getValue();
					var nationality = addForm.findField('nationality')
							.getValue();
					var province = addForm.findField('province').getValue();
					var city = addForm.findField('city').getValue();
					var gender = addForm.findField('gender').getValue();
					var introduction = addForm.findField('introduction')
							.getValue();
					Ext.Ajax.request({
								url : '../userinfo/updateuser',
								params : {
									'id' : uid,
									'nickName' : nickName,
									'nationality' : nationality,
									'province' : province,
									'city' : city,
									'gender' : gender,
									'introduction' : introduction
								},
								scope : addUserCmbForm,
								success : function(response) {
									var result = Ext
											.decode(response.responseText);
									if (result.success) {
										Ext.MessageBox.alert("提示", "修改成功");
										addUserWindow.hide();
										sinaUserStore.reload();
									} else {
										Ext.Msg.alert("提示", "修改失败");
										return;
									}
								},
								failure : function() {
									Ext.Msg.alert("提示", "修改失败");
									return;
								}
							});
					addUserWindow.hide();
				}
			}, {	// 窗口底部按钮配置
						text : '重置', // 按钮文本
						iconCls : 'tbar_synchronizeIcon', // 按钮图标
						handler : function() { // 按钮响应函数
							addKeywordCmbForm.form.reset();
						}
					}, {
						text : '关闭',
						iconCls : 'deleteIcon',
						handler : function() {
							addUserWindow.hide();
						}
					}]
		});
addUserWindow.on('show', function() {
		});
// 定义右键菜单
var rightMenu = new Ext.menu.Menu({
	id : 'rightMenu',
	items : [{
		text : '查看密码',
		handler : function() {
			var grid = sinaUserGrid.selModel.selections.items[0].data;
			var robot = grid.isRobot;
			var uid = grid.id;
			if (robot) {
				if (Ext.isEmpty(uid)) {
					Ext.Msg.alert("提示", "uid为空无法查看密码");
					return;
				} else {
					Ext.Ajax.request({
								url : '../userinfo/querypwd',
								params : {
									'uid' : uid
								},
								success : function(response) {
									var result = Ext
											.decode(response.responseText);
									Ext.MessageBox.alert("密码", result.password);
								},
								failure : function() {
									Ext.Msg.alert("提示", "获取密码失败");
									return;
								}
							});
				}
			} else {
				Ext.Msg.alert("提示", "该用户不是机器人无法查看密码");
				return;
			}
		}
	}]
});
sinaUserGrid.on("rowcontextmenu", function(grid, rowIndex, e) {
			e.preventDefault();
			grid.getSelectionModel().selectRow(rowIndex);
			rightMenu.showAt(e.getXY());
		});
// 注册事件
sinaUserGrid.on('cellclick', sinaUserGrid.onCellClick, sinaUserGrid);

sinaUserGrid.on('celldblclick', function(grid, rowIndex, columnIndex, e) {
			var sms = grid.selModel.selections.items[0];
			if (sms == undefined) {
				return;
			}
			var selectData = sms.data;
			var uid = selectData.id;
			var nickName = selectData.nickName;
			var nationality = selectData.nationality;
			var province = selectData.province;
			var city = selectData.city;
			var gender = selectData.gender;
			var introduction = selectData.introduction;
			var updateForm = addUserCmbForm.getForm();
			updateForm.findField("uid").setValue(uid);
			updateForm.findField("nickName").setValue(nickName);
			updateForm.findField("nationality").setValue(nationality);
			updateForm.findField("province").setValue(province);
			updateForm.findField("gender").setValue(gender);
			updateForm.findField("city").setValue(city);
			updateForm.findField("introduction").setValue(introduction);
			addUserWindow.show();
		});

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
var expUserStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : '../userinfo/expuser'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'totalCount',
						root : 'list'
					}, [{
								name : 'uid'
							}, {
								name : 'websiteUid'
							}, {
								name : 'loginName'
							}, {
								name : 'loginPwd'
							}])
		});
var expcm = new Ext.ux.grid.LockingColumnModel([{
			header : '编号',
			dataIndex : 'uid',
			locked : true,
			width : 80
		}, {
			header : '网站编号',
			dataIndex : 'websiteUid',
			width : 100
		}, {
			header : '用户名',
			dataIndex : 'loginName',
			width : 120
		}, {
			header : '密码',
			dataIndex : 'loginPwd',
			width : 100
		}]);
var expUserGrid = new Ext.grid.GridPanel({
			id : 'expUserGrid',
			height : 440,
			stripeRows : true, // 斑马线
			frame : true,
			autoScroll : true,
			store : expUserStore,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			cm : expcm
		});
var expUserWin = new Ext.Window({
			title : '<span class="commoncss">导出用户信息</span>', // 窗口标题
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
			items : [expUserGrid],
			// 设置窗口是否可以溢出父容器
			buttonAlign : 'center',
			buttons : [{
						text : '关闭',
						iconCls : 'deleteIcon',
						handler : function() {
							Ext.Msg.confirm('提示', '请确认是否已经复制帐号信息', function op(
											btn) {
										if (btn == 'yes') {
											expUserWin.hide();
										} else {
											return;
										}
									});
						}
					}]
		});
expUserWin.on('show', function() {
			// 清除store缓存
			expUserStore.clearData();
		});
expUserWin.on('hide', function() {
			sinaUserStore.load();
		});

// 嵌入的FORM
var updateUserTagForm = new Ext.form.FormPanel({
			id : 'updateUserTagForm',
			name : 'updateUserTagForm',
			labelWidth : 90, // 标签宽度
			frame : true, // 是否渲染表单面板背景色
			defaultType : 'textfield', // 表单元素默认类型
			labelAlign : 'right', // 标签对齐方式
			bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
			items : [{
						xtype : "textarea",
						fieldLabel : "核心关键字",
						name : 'corekeyword',
						allowBlank : false,
						anchor : '100%'
					}, {
						xtype : "textarea",
						fieldLabel : "随机关键字",
						name : 'randomkeyword',
						anchor : '100%'
					}, {
						xtype : "numberfield",
						fieldLabel : "随机下限",
						name : 'rangegte',
						anchor : '90%'
					}, {
						xtype : "numberfield",
						fieldLabel : "随机上限",
						name : 'rangelte',
						anchor : '90%'
					}]
		});

/**
 * 修改用户标签win
 */

var updateUserTagWin = new Ext.Window({
			title : '<span class="commoncss">更新用户标签</span>', // 窗口标题
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
			items : [updateUserTagForm],
			// 设置窗口是否可以溢出父容器
			buttonAlign : 'center',
			buttons : [{
						text : '保存',
						iconCls : 'addIcon',
						handler : updateUserTag
					}, {
						text : '关闭',
						iconCls : 'deleteIcon',
						handler : function() {
							updateUserTagWin.hide();
						}
					}]
		});
/**
 * 导出用户信息
 */
function expUserInfo() {
	Ext.Msg.confirm('警告', '此操作会导致帐号删除，是否确认导出？，取消请按‘否’', function op(btn) {
				if (btn == 'yes') {
					var expArray = getSelectUsers();
					var rows = [];
					if (expArray.length > 0) {
						for (var i = 0; i < expArray.length; i++) {
							var uid = expArray[i];
							rows.push(uid);
						}
					} else {
						Ext.Msg.alert("提示", "请至少选择一个用户");
						return;
					}
					expUserStore.setBaseParam('uids', rows);
					expUserStore.load();
					expUserWin.show();
				} else {
					return;
				}
			});
}

/**
 * 更新用户标签
 */
function updateUserTag() {
	var expArray = getSelectUsers();
	var tagForm = updateUserTagForm.getForm();
	var corekeyword = tagForm.findField("corekeyword").getValue();
	var randomkeyword = tagForm.findField("randomkeyword").getValue();
	var rangegte = tagForm.findField("rangegte").getValue();
	var rangelte = tagForm.findField("rangelte").getValue();

	Ext.Msg.show({
				title : '确认信息',
				msg : '确定更新?',
				buttons : Ext.Msg.YESNO,
				fn : function(ans) {
					if (ans == 'yes') {
						Ext.Ajax.request({
									url : '../userinfo/updatetag?_time'
											+ new Date().getTime(),
									params : {
										uids : expArray,
										corekeyword : corekeyword,
										randomkeyword : randomkeyword,
										rangegte : rangegte,
										rangelte : rangelte
									},
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										var count = result.count;
										Ext.Msg.alert("提示", "已更新" + count
														+ "条记录");
									},
									failure : function() {
										Ext.Msg.alert("提示", "更新失败");
									}
								});
					}
				}
			});
}
/**
 * 获取选中的用户
 */
function getSelectUsers() {
	var expArray = [];
	var rows = sinaUserGrid.getSelectionModel().getSelections();
	if (rows.length > 0) {
		for (var i = 0; i < rows.length; i++) {
			var uid = rows[i].data.id;
			expArray.push(uid);
		}
	}
	return expArray;
}
/**
 * 将用户导入用户分组
 * 
 * @param {}
 *            keywordId
 * @return {}
 */
function importUserGroup(gid, uids) {
	Ext.Msg.show({
				title : '确认信息',
				msg : '确定导入?',
				buttons : Ext.Msg.YESNO,
				fn : function(ans) {
					if (ans == 'yes') {
						Ext.Ajax.request({
									url : '../usergroup/impusertogroup?_time'
											+ new Date().getTime(),
									params : {
										gid : gid,
										uids : uids
									},
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										var msg = result.message;
										Ext.Msg.alert("提示", msg);
									},
									failure : function() {
										Ext.Msg.alert("提示", "导入失败");
									}
								});
					}
				}
			});
}