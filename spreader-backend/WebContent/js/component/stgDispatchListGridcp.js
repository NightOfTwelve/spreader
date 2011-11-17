/**
 * 策略配置列表页面 实现所有策略的简单列表，点击某一行记录跳转到详细配置页面
 */
// 定义表格数据源
var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : '../strategy/stgdispgridstore'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'cnt',
						root : 'data'
					}, [{
								name : 'id'
							}, {
								name : 'name'
							}, {
								name : 'triggerType'
							}, {
								name : 'triggerInfo'
							}, {
								name : 'description'
							}]),
			autoLoad : {
				params : {
					start : 0,
					limit : 25
				}
			}

		});
// 定义表格列CM
var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
			header : '调度编号',
			dataIndex : 'id',
			width : 80
		}, {
			header : '调度名称',
			dataIndex : 'name',
			renderer : rendDispName,
			width : 100
		}, {
			header : '调度类型',
			dataIndex : 'triggerType',
			width : 100,
			renderer : rendTrigger
		}, {
			header : '调度备注',
			dataIndex : 'triggerInfo',
			renderer:renderBrief,
			width : 100
		}, {
			header : '描述',
			dataIndex : 'description',
			renderer:renderBrief,
			width : 100
		}, {
			header : '相关操作',
			renderer : function showbutton() {
				var returnStr = "<input type='button' value='配置'/>";
				return returnStr;
			},
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
						store.reload({
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
var bbar = new Ext.PagingToolbar({
			pageSize : number,
			store : store,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', numtext]
		});
// 定义grid表格
var stgdisplistgrid = new Ext.grid.GridPanel({
			// title : '<span class="commoncss">策略配置列表</span>',
			// iconCls : 'buildingIcon',
			height : 540,
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
							stgCmbWindow.show();
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
			bbar : bbar,
			onCellClick : function(grid, rowIndex, columnIndex, e) {
				// 找出表格中‘配置’按钮
				if (e.target.defaultValue == '配置') {
					var record = grid.getStore().getAt(rowIndex);
					var data = record.data;
					GDISNAME = data.displayName;
					GOBJID = data.name;
					var trgid = data.id;
					// TODO
					settingCreateTrigger(trgid);
					editstgWindow.show();
				}
			}

		});
// 注册事件
stgdisplistgrid.on('cellclick', stgdisplistgrid.onCellClick, stgdisplistgrid);

/**
 * 创建新增事件ComboBox
 */
// 创建ComboBox数据源，支持Ajax取值
var stgCmbStore = new Ext.data.Store({
			// 代理模式
			proxy : new Ext.data.HttpProxy({
						url : '../strategy/combstore'
					}),
			// 读取模式
			reader : new Ext.data.JsonReader({}, [{
								name : 'name'
							}, {
								name : 'displayName'
							}])
		});
// 选择策略的COMB
var stgSelectCombo = new Ext.form.ComboBox({
			hiddenName : 'name',
			id : 'stgSelectCombo',
			fieldLabel : '策略',
			emptyText : '请选择策略...',
			triggerAction : 'all',
			store : stgCmbStore,
			displayField : 'displayName',
			valueField : 'name',
			loadingText : '正在加载数据...',
			mode : 'remote', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
			forceSelection : true,
			typeAhead : true,
			resizable : true,
			editable : false,
			anchor : '100%'
		});
// 选择事件，用于联动 TODO
// stgSelectCombo.on('select', function() {
// cityCombo.reset();
// countyCombo.reset();
// var value = provinceCombo.getValue();
// cityStore.load({
// params : {
// areacode : value
// }
// });
// });
// 嵌入的FORM
var stgCmbForm = new Ext.form.FormPanel({
			id : 'stgCmbForm',
			name : 'stgCmbForm',
			labelWidth : 50, // 标签宽度
			frame : true, // 是否渲染表单面板背景色
			defaultType : 'textfield', // 表单元素默认类型
			labelAlign : 'right', // 标签对齐方式
			bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
			items : [stgSelectCombo]
		});
/**
 * 策略调度配置模块
 */
// 创建简单调度的FORM
var simpleDispForm = new Ext.form.FormPanel({
			// autoWidth : true,
			id : 'simpcard',
			height : 100,
			frame : true,
			layout : "form", // 整个大的表单是form布局
			labelWidth : 100,
			labelAlign : "left",
			items : [{ // 行1
				layout : "column", // 从左往右的布局
				items : [{
							columnWidth : .3, // 该列有整行中所占百分比
							layout : "form", // 从上往下的布局
							items : [{
										xtype : "datetimefield",
										fieldLabel : "开始时间",
										name : 'start',
										width : 150
									}]
						}, {
							columnWidth : .3,
							layout : "form",
							items : [{
										xtype : "numberfield",
										fieldLabel : "重复次数",
										name : 'repeatTimes',
										width : 100
									}]
						}, {
							columnWidth : .3,
							layout : "form",
							items : [{
										xtype : "numberfield",
										fieldLabel : "毫秒数",
										name : 'repeatInternal',
										width : 100
									}]
						}]
			}],
			buttonAlign : "center",
			buttons : [{
						text : "重置",
						handler : function() { // 按钮响应函数
							simpleDispForm.form.reset();
						}
					}]
		});
// 表达式配置FORM
var triggerDispForm = new Ext.form.FormPanel({
			autoWidth : true,
			height : 100,
			id : 'trgcard',
			frame : true,
			layout : "form", // 整个大的表单是form布局
			labelWidth : 100,
			labelAlign : "left",
			items : [{ // 行1
				layout : "column", // 从左往右的布局
				items : [{
							columnWidth : .3, // 该列有整行中所占百分比
							layout : "form", // 从上往下的布局
							items : [{
										xtype : "textfield",
										fieldLabel : "表达式",
										name : 'cron',
										width : 100
									}]
						}]
			}],
			buttonAlign : "center",
			buttons : [{
						text : "重置",
						handler : function() { // 按钮响应函数
							triggerDispForm.form.reset();
						}
					}]
		});
// 首先创建一个card布局的Panel
var cardPanel = new Ext.Panel({
			autoWidth : true,
			id : 'cardPanel',
			height : 100,
			layout : 'card',
			activeItem : 0,
			// bodyStyle : 'padding:15px',
			defaults : {
				border : false
			},
			items : [simpleDispForm, triggerDispForm]
		});
// RADIO组件
var radioForm = new Ext.form.FormPanel({
			// width : 200,
			frame : true,
			height : 80,
			labelWidth : 65,
			labelAlign : "left",
			items : [{
						xtype : 'radiogroup',
						fieldLabel : '配置方式',
						items : [{
									xtype : 'radio',
									boxLabel : '简单调度',
									inputValue : '1',
									width : 50,
									name : 'triggerType',
									checked : true
								}, {
									xtype : 'radio',
									boxLabel : '配置表达式',
									width : 50,
									inputValue : '2',
									name : 'triggerType'
								}],
						listeners : {
							'change' : function(group, ck) {
								var cardPanelCmp = Ext.getCmp('cardPanel').layout;
								var activeid = cardPanelCmp.activeItem.id;
								if (ck.inputValue == '1') {
									cardPanelCmp.setActiveItem(0);
								} else {
									cardPanelCmp.setActiveItem(1);
								}
							}
						}
					}, {
						xtype : "textfield",
						fieldLabel : "备注信息",
						name : 'description',
						width : 100
					}]
		});
// 弹出窗口
var stgCmbWindow = new Ext.Window({
			title : '<span class="commoncss">策略选择</span>', // 窗口标题
			id : 'stgCmbWindow',
			closeAction : 'hide',
			layout : 'fit', // 设置窗口布局模式
			width : 300, // 窗口宽度
			height : 150, // 窗口高度
			// closable : true, // 是否可关闭
			collapsible : true, // 是否可收缩
			maximizable : true, // 设置是否可以最大化
			border : false, // 边框线设置
			constrain : true, // 设置窗口是否可以溢出父容器
			pageY : 20, // 页面定位Y坐标
			pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
			items : [stgCmbForm], // 嵌入的表单面板
			buttons : [{ // 窗口底部按钮配置
				text : '确定', // 按钮文本
				iconCls : 'tbar_synchronizeIcon', // 按钮图标
				handler : function() { // 按钮响应函数
					// TODO
					GOBJID = stgSelectCombo.getValue();
					GDISNAME = stgSelectCombo.lastSelectionText;
					// alert("name:"+GOBJID+",dis:"+GDISNAME);
					editstgWindow.show();
					stgCmbWindow.hide();
				}
			}, {	// 窗口底部按钮配置
						text : '重置', // 按钮文本
						iconCls : 'tbar_synchronizeIcon', // 按钮图标
						handler : function() { // 按钮响应函数
							stgCmbForm.form.reset();
						}
					}, {
						text : '关闭',
						iconCls : 'deleteIcon',
						handler : function() {
							stgCmbWindow.hide();
						}
					}]
		});
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
						title : '选择配置',
						split : true,
						width : 200,
						minWidth : 175,
						maxWidth : 400,
						items : [stgdisptree]
					}, {
						region : 'south',
						title : '调度配置',
						split : true,
						width : 300,
						height : 180,
						items : [radioForm, cardPanel]
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
			stgdisptree.getRootNode().reload();
			stgdisptree.root.select();
			var pptGrid = Ext.getCmp("pptGrid");
			var pptMgr = Ext.getCmp("pptgridmanage");
			if (pptGrid != null) {
				pptMgr.remove(pptGrid);
			}
			pptMgr.doLayout();
		});

function settingCreateTrigger(trgid) {
	Ext.Ajax.request({
				url : '../strategy/settgrparam',
				params : {
					'id' : trgid
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					var description = result.description;
					var triggerType = result.triggerType;
					var cron = result.cron;
					var start = result.start.dateFormat('Y/m/d H:i:s');
					var repeatTimes = result.repeatTimes;
					var repeatInternal = result.repeatInternal;
					var description = result.description;
					// 获取FORM
					var tradioForm = radioForm.getForm();
					var ttriggerDispForm = triggerDispForm.getForm();
					var tsimpleDispForm = simpleDispForm.getForm();
					// 设置参数
					tradioForm.findField("triggerType").setValue(triggerType);
					tradioForm.findField("description").setValue(description);
					tsimpleDispForm.findField("start").setValue(start);
					tsimpleDispForm.findField("repeatTimes")
							.setValue(repeatTimes);
					tsimpleDispForm.findField("repeatInternal")
							.setValue(repeatInternal);
					ttriggerDispForm.findField("cron").setValue(cron);
				},
				failure : function() {
//					Ext.Msg.alert("提示", "数据获取异常");
				}
			});
}

/**
 * 渲染策略名称为中文名
 * 
 * @param {}
 *            value
 * @return {}
 */
function rendDispName(value) {
	var list = store.reader.jsonData.dispname;
	for (var idx in list) {
		var tmp = list[idx].name;
		var dname = list[idx].displayName;
		if (value == tmp) {
			return dname;
		}
	}
}
/**
 * 
 * @param {}
 *            value
 * @return {}
 */
function rendTrigger(value) {
	if (value == 1) {
		return '简单调度';
	} else {
		return '复杂调度';
	}
}
