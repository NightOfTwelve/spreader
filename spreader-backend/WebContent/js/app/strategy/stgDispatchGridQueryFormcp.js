/**
 * 调度类型的COMB的数据源
 */
var tgTypeStore = new Ext.data.ArrayStore({
			fields : ['ID', 'NAME'],
			data : [['-1', '----------------------'], ['1', '简单调度'],
					['2', '复杂调度']]
		});
/**
 * 策略列表的查询FORM
 */
var stgdispgridform = new Ext.form.FormPanel({
			title : "组合查询",
			autoWidth : true,
			height : 100,
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
										fieldLabel : '调度类型',
										// name:'TIMEFER',
										xtype : 'combo',
										width : 100,
										store : tgTypeStore,
										id : 'triggerType',
										hiddenName : 'triggerType',
										valueField : 'ID',
										editable : false,
										displayField : 'NAME',
										mode : 'local',
										forceSelection : false,// 必须选择一项
										emptyText : '调度类型...',// 默认值
										triggerAction : 'all'
									}]
						}, {
							columnWidth : .3,
							layout : "form",
							items : [{
										xtype : "textfield",
										fieldLabel : "调度名称",
										name : 'dispname',
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
							stgdispgridform.form.reset();
						}
					}]

		});