
/**
 * 运行状态的COMB的数据源
 */
var statTypeStore = new Ext.data.ArrayStore({
			fields : ['ID', 'NAME'],
			data : [['-1', '----------------------'], ['1', '正常'], ['2', '异常']]
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
			// frame : true, //是否渲染表单面板背景色
			labelAlign : 'right', // 标签对齐方式
			// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height : 120,
			items : [{ // 行1
				layout : "column", // 从左往右的布局
				items : [{
							columnWidth : .3, // 该列有整行中所占百分比
							layout : "form", // 从上往下的布局
							items : [{
										fieldLabel : '运行状态',
										// name:'TIMEFER',
										xtype : 'combo',
										width : 100,
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
							columnWidth : .3,
							layout : "form",
							items : [{
										xtype : "textfield",
										fieldLabel : "用户昵称",
										name : 'nickname',
										width : 100
									}]
						}]
			}],
			buttonAlign : "center",
			buttons : [{
						text : "查询"
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
						url : '../strategy/stgdispgridstore'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'cnt',
						root : 'data'
					}, [{
								name : 'id'
							}, {
								name : 'is_robot'
							}, {
								name : 'nick_name'
							}, {
								name : 'gender'
							}, {
								name : 'real_name'
							}, {
								name : 'fans'
							}, {
								name : 'robot_fans'
							}, {
								name : 'articles'
							}, {
								name : 'email'
							}, {
								name : 'region'
							}, {
								name : 'space_entry'
							}, {
								name : 'introduction'
							}, {
								name : 'qq'
							}, {
								name : 'msn'
							}, {
								name : 'blog'
							}]),
			autoLoad : {
				params : {
					start : 0,
					limit : 25
				}
			}

		});
// 定义Checkbox
var sm = new Ext.grid.CheckboxSelectionModel();
// 定义表格列CM
var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
			header : '编号',
			dataIndex : 'id',
			width : 80
		}, {
			header : '是否机器人',
			dataIndex : 'is_robot',
			width : 100
		}, {
			header : '昵称',
			dataIndex : 'nick_name',
			width : 100
		}, {
			header : '性别',
			dataIndex : 'gender',
			width : 100
		}, {
			header : '真实姓名',
			dataIndex : 'real_name',
			width : 100
		}, {
			header : '粉丝数',
			dataIndex : 'fans',
			width : 100
		}, {
			header : '机器人粉丝数',
			dataIndex : 'robot_fans',
			width : 100
		}, {
			header : '文章数',
			dataIndex : 'articles',
			width : 100
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
			dataIndex : 'space_entry',
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
						bbar.pageSize = parseInt(numtext.getValue());
						number = parseInt(numtext.getValue());
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

// // 分页菜单
var bbar = new Ext.PagingToolbar({
			pageSize : number,
			store : sinaUserStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', numtext]
		});

// 定义grid表格
var sinaUsergGrid = new Ext.grid.GridPanel({
			// region : 'center',
			id : 'sinaUsergGrid',
			height : 300,
			stripeRows : true, // 斑马线
			frame : true,
			// autoWidth : true,
			autoScroll : true,
			store : sinaUserStore,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			bbar : bbar,
			sm : sm,
			cm : cm,
			tbar : [{
						text : '新增',
						iconCls : 'page_addIcon',
						handler : function() {
							// stgCmbWindow.show();
						}
					}, '-', {
						text : '删除',
						iconCls : 'page_delIcon',
						handler : function() {
							// TODO
							// deleteData();
						}
					}, {
						text : '查询',
						iconCls : 'previewIcon',
						handler : function() {
						}
					}, '-', {
						text : '刷新',
						iconCls : 'arrow_refreshIcon',
						handler : function() {
							sinaUserStore.reload();
						}
					}],
			onCellClick : function(grid, rowIndex, columnIndex, e) {
				// 找出表格中‘配置’按钮
				if (e.target.defaultValue == '配置') {
					var record = grid.getStore().getAt(rowIndex);
					var data = record.data;
					GDISNAME = data.displayName;
					GOBJID = data.name;
					GDISPID = data.id;
					var trgid = data.id;
					// editstgWindow.show();
				}
			}

		});

// 注册事件
sinaUsergGrid.on('cellclick', sinaUsergGrid.onCellClick, sinaUsergGrid);

// // 创建策略维护的窗口组件
// var editstgWindow = new Ext.Window({
// layout : 'border',
// width : document.documentElement.clientWidth - 200,
// height : document.documentElement.clientHeight - 200,
// resizable : true,
// draggable : true,
// closeAction : 'hide',
// title : '<span class="commoncss">策略详细配置</span>',
// iconCls : 'app_rightIcon',
// modal : true,
// collapsible : true,
// maximizable : true,
// animCollapse : true,
// animateTarget : document.head,
// buttonAlign : 'right',
// constrain : true,
// border : false,
// items : [{
// region : 'center',
// id : 'pptgridmanage',
// header : false,
// collapsible : true,
// split : true,
// height : 100
// }],
// buttons : [{
// text : '关闭',
// iconCls : 'deleteIcon',
// handler : function() {
// editstgWindow.hide();
// }
// }]
// });
// show事件，需先删除组件，再重新创建PPTGRID
// editstgWindow.on('show', function() {
// stgdisptree.getRootNode().reload();
// stgdisptree.root.select();
// var pptGrid = Ext.getCmp("pptGrid");
// var pptMgr = Ext.getCmp("pptgridmanage");
// if (pptGrid != null) {
// pptMgr.remove(pptGrid);
// }
// pptMgr.doLayout();
// });

/**
 * 渲染策略名称为中文名
 * 
 * @param {}
 *            value
 * @return {}
 */
// function rendDispName(value) {
// var list = sinaUserStore.reader.jsonData.dispname;
// for (var idx in list) {
// var tmp = list[idx].name;
// var dname = list[idx].displayName;
// if (value == tmp) {
// return dname;
// }
// }
// }
/**
 * 
 * @param {}
 *            value
 * @return {}
 */
// function rendTrigger(value) {
// if (value == 1) {
// return '简单调度';
// } else {
// return '复杂调度';
// }
// }
