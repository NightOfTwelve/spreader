/**
 * 获取毫秒转换RepeatTrigger
 */
function getTimeUnitsTrigger(repeatFormId, unitComboId) {
	if (Ext.isEmpty(repeatFormId)) {
		repeatFormId = 'repeatForm';
	}
	if (Ext.isEmpty(unitComboId)) {
		unitComboId = 'unitSelect';
	}
	var unitStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [[1, '分'], [2, '时'], [3, '天']]
			});
	var repeatForm = new Ext.form.FormPanel({
		// collapsible : true,
		frame : true,
		id : repeatFormId,
		// border : true,
		labelWidth : 30, // 标签宽度
		frame : true,
		labelAlign : 'right',
		bodyStyle : 'padding:5 5 5 5',
		buttonAlign : 'center',
		width : 400,
		items : [{ // 行1
			layout : "column",
			items : [{
						columnWidth : .5,
						layout : "form",
						items : [{
									xtype : "numberfield",
									fieldLabel : "数值",
									name : 'repeatInternal'
								}]
					}, {
						columnWidth : .5,
						layout : "form",
						items : [{
									fieldLabel : '单位',
									xtype : 'combo',
									store : unitStore,
									id : unitComboId,
									name : unitComboId,
									hiddenName : 'unit',
									getListParent : function() {
										return this.el.up('.x-menu');
									},
									listClass : 'fixedZIndex',
									iconCls : 'no-icon',
									valueField : 'ID',
									editable : false,
									displayField : 'NAME',
									mode : 'local',
									forceSelection : false,// 必须选择一项
									emptyText : '...',// 默认值
									triggerAction : 'all'
								}]
					}]
		}],
		buttonAlign : "center",
		buttons : [{
					text : "确定",
					handler : function() { // 按钮响应函数
						var tform = repeatForm.getForm();
						var vars = tform.findField("repeatInternal").getValue();
						var unit = tform.findField("unit").getValue();
						var repeatInternal = getMillisecond(vars, unit);
						// tform.findField("repeatInternal")
						// .setValue(repeatInternal);
						repeatTrigger.setValue(repeatInternal);
						selectMenu.hide();
					}
				}, {
					text : "重置",
					handler : function() { // 按钮响应函数
						repeatForm.form.reset();
					}
				}]
	});

	var selectMenu = new Ext.menu.Menu({
				items : [repeatForm]
			});

	var repeatTrigger = new Ext.form.TriggerField({
				fieldLabel : '毫秒数',
				// readOnly : true,
				id : 'repeatInternal',
				name : 'repeatInternal',
				// 指定按钮的样式
				triggerClass : 'x-form-search-trigger',
				anchor : '90%',
				labelStyle : 'text-align:right;width:90;',
				onSelect : function(record) {

				},
				// TriggerField点击按钮函数
				onTriggerClick : function() {
					if (this.menu == null) {
						this.menu = selectMenu;
					}
					repeatForm.form.reset();
					this.menu.show(this.el, "tl-bl?");
				}
			})
	return repeatTrigger;
}

/**
 * 获取毫秒数
 */
function getMillisecond(vars, unit) {
	if (unit == 1) {
		return vars * 60 * 1000;
	}
	if (unit == 2) {
		return vars * 60 * 60 * 1000;
	}
	if (unit == 3) {
		return vars * 60 * 60 * 1000 * 24;
	}
	return 100000;
}