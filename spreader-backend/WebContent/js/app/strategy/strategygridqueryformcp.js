/**
 * 策略列表的查询FORM
 */
var stggridform = new Ext.form.FormPanel({
			region : 'north',
			title : "组合查询",
			autoWidth : true,
			height : 100,
			split : true,
			frame : true,
			layout : "form", // 整个大的表单是form布局
			labelWidth : 65,
			labelAlign : "right",
			items : [{ // 行1
				layout : "column", // 从左往右的布局
				items : [{
							columnWidth : .3, // 该列有整行中所占百分比
							layout : "form", // 从上往下的布局
							items : [{
										xtype : "datefield",
										fieldLabel : "日期",
										width : 100
									}]
						}, {
							columnWidth : .3,
							layout : "form",
							items : [{
										xtype : "textfield",
										fieldLabel : "策略名称",
										width : 100
									}]
						}, {
							columnWidth : .3,
							layout : "form",
							items : [{
										xtype : "textfield",
										fieldLabel : "其他条件",
										width : 100
									}]
						}]
			}],
			buttonAlign : "center",
			buttons : [{
						text : "查询"
					}, {
						text : "重置"
					}]
		});
