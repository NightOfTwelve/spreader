/**
 * 调度配置组件
 */

/**
 * 策略调度配置模块
 */
// 创建简单调度的FORM
var simpleDispForm = new Ext.form.FormPanel({
			id : 'simpcard',
			height : 80,
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
										minValue : new Date(),
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
				text : '保存',
				handler : function() {
					strategyGroupSubmitTreeData(strategyTree, triggerDispForm,
							radioForm, simpleDispForm, editstgWindow, null,
							null, null, null, strategyIdHidden.getValue(),
							strategyIdHidden.getValue(), null, groupTypeHidden
									.getValue(), objIdHidden.getValue(), null);
				}
			}, {
				text : "重置",
				handler : function() { // 按钮响应函数
					simpleDispForm.form.reset();
				}
			}]
		});
// 表达式配置FORM
var triggerDispForm = new Ext.form.FormPanel({
			height : 80,
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
						text : '保存',
						handler : function() {
							strategyDispatchSubmitTreeData();
						}
					}, {
						text : "重置",
						handler : function() { // 按钮响应函数
							triggerDispForm.form.reset();
						}
					}]
		});
// 首先创建一个card布局的Panel
var cardPanel = new Ext.Panel({
			region : 'center',
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
// 用于提示的Toolbar
var radioTbar = new Ext.Toolbar();
radioTbar.add({
			id : 'jobremind',
			name : 'jobremind',
			xtype : 'tbtext'
		})
// RADIO组件
var radioForm = new Ext.form.FormPanel({
			region : 'north',
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
					}],
			tbar : radioTbar
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
						collapsible : true,
						split : true
					}, {
						region : 'west',
						split : true,
						width : 200,
						minWidth : 175,
						maxWidth : 400,
						items : [strategyTree]
					}, {
						region : 'south',
						title : '调度配置',
						layout : 'border',
						split : true,
						height : 200,
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
			strategyTree.getRootNode().reload();
			strategyTree.root.select();
			var pptGrid = Ext.getCmp("pptGrid");
			var pptMgr = Ext.getCmp("pptgridmanage");
			if (pptGrid != null) {
				pptMgr.remove(pptGrid);
			}
			pptMgr.doLayout();
		});
/**
 * 设置trigger
 * 
 * @param {}
 *            trigger
 */
function settingTrigger(trigger) {
	var description = trigger.description;
	var triggerType = trigger.triggerType;
	var cron = trigger.cron;
	var start = trigger.start;
	var sdate = new Date(start);
	var repeatTimes = trigger.repeatTimes;
	var repeatInternal = trigger.repeatInternal;
	var remind = trigger.remind;
	// 获取FORM
	var tradioForm = radioForm.getForm();
	var ttriggerDispForm = triggerDispForm.getForm();
	var tsimpleDispForm = simpleDispForm.getForm();
	// 设置参数
	tradioForm.findField("triggerType").setValue(triggerType);
	tradioForm.findField("description").setValue(description);
	tsimpleDispForm.findField("start").setValue(sdate);
	tsimpleDispForm.findField("repeatTimes").setValue(repeatTimes);
	tsimpleDispForm.findField("repeatInternal").setValue(repeatInternal);
	ttriggerDispForm.findField("cron").setValue(cron);
	var remindcmp = Ext.getCmp("jobremind");
	var tstr = '任务:' + rendDispName(strategyIdHidden.getValue()) + ',编号:'
			+ objIdHidden.getValue() + ',目前运行信息:' + remind;
	remindcmp.setText('<font color = "red">' + tstr + '</font>');
}

/**
 * 初始化新增对象的参数
 * 
 * @param {}
 *            trgid
 */
function cleanCreateTrigger() {
	// 获取FORM
	var tradioForm = radioForm.getForm();
	var ttriggerDispForm = triggerDispForm.getForm();
	var tsimpleDispForm = simpleDispForm.getForm();
	// 设置参数
	// tradioForm.findField("triggerType").setValue(1);
	tradioForm.findField("description").setValue(null);
	tsimpleDispForm.findField("start").setValue(null);
	tsimpleDispForm.findField("repeatTimes").setValue(null);
	tsimpleDispForm.findField("repeatInternal").setValue(null);
	ttriggerDispForm.findField("cron").setValue(null);
	var remindcmp = Ext.getCmp("jobremind");
	remindcmp.setText(null);
}
/**
 * 删除调度信息
 * 
 * @param {}
 *            id
 */
function deleteData() {
	// 获取选中行
	var rows = stgdisplistgrid.getSelectionModel().getSelections();
	// 可能是批量删除，需循环所有的选中行
	if (rows.length > 0) {
		var tmpstr = '';
		for (var i = 0; i < rows.length; i++) {
			var trgid = rows[i].data.id;
			tmpstr += trgid + ',';
		}
		var idstr = subStrLastId(tmpstr);
		Ext.Msg.confirm('提示',
				'<span style="color:red"><b>提示:</b></span><br>确定删除？', function(
						btn, text) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : '../strategydisp/deletetrg',
									params : {
										'idstr' : idstr
									},
									scope : stgdisplistgrid,
									success : function(response) {
										var result = Ext
												.decode(response.responseText);
										Ext.Msg.alert("提示", result.message);
										store.reload();
									},
									failure : function() {
										Ext.Msg.alert("提示", "删除失败");
									}
								});
					}
				});
	} else {
		Ext.Msg.alert("提示", "请选择要删除的调度");
		return;
	}
}
/**
 * 获取策略的中文名
 */
var dispNameStore = new Ext.data.Store({
			// 代理模式
			proxy : new Ext.data.HttpProxy({
						url : '../strategydisp/combstore'
					}),
			// 读取模式
			reader : new Ext.data.JsonReader({}, [{
								name : 'name'
							}, {
								name : 'displayName'
							}]),
			autoLoad : true
		});
/**
 * 渲染策略名称为中文名
 * 
 * @param {}
 *            value
 * @return {}
 */
function rendDispName(value) {
	var list = dispNameStore.reader.jsonData;
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