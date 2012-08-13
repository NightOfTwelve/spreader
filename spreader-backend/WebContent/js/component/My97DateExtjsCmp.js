/**
 * 结合my97date的日历控件
 * 
 * @param {}
 *            fieldId TriggerField 的id属性 如果同一页面引用该控件多次则必须传入，否则会报错
 * @param {}
 *            fieldName TriggerField 的name属性 如果同一页面引用该控件多次则必须传入，否则会报错
 * @param {}
 *            text TriggerField 的信息
 * @param {}
 *            dateTimeParams my97date的一些属性，一般无需填写
 * @return {}
 */
function calendarCmp(fieldId, fieldName, text, dateTimeParams) {
	// 日期时间的默认参数
	var defaultDateTimeParams = new function() {
		this.readOnly = true; // 不允许在文本输入框中修改时间
		// 4.8版本可以，如切换到4.7则为false
		this.isShowWeek = true; // 默认显示周
		this.startDate = '%y-%M-01 00:00:00'; // 开始时间
		this.dateFmt = 'yyyy-MM-dd HH:mm:ss'; // 格式化时间
		this.alwaysUseStartDate = true; // 默认使用初始时间
	};
	// 构造参数取形参还是默认参数
	var params = dateTimeParams ? dateTimeParams : defaultDateTimeParams;
	// 采用TriggerField，保持与extjs控件一致
	var triggerField = new Ext.form.TriggerField({
				fieldLabel : Ext.isEmpty(text) ? '查询时间' : text,
				labelWidth : 90,
				id : Ext.isEmpty(fieldId) ? 'startDate' : fieldId,
				name : Ext.isEmpty(fieldName) ? 'startDate' : fieldName,
				// 使用TextField用到的样式
				// style : 'cursor:pointer',
				// 使用TextField用到的样式
				// cls : 'Wdate',
				// 指定按钮的样式
				triggerClass : 'x-form-date-trigger',
				anchor : '60%',
				labelStyle : 'text-align:right;width:90;',
				// TriggerField点击按钮函数
				onTriggerClick : function(field) {
					WdatePicker(params);
				}
			});
	// 给构造参数的el属性增加值
	params.el = triggerField.id;
	// 使用TextField用到的焦点事件
	// triggerField.addListener('focus', function(field) {
	// WdatePicker(params);
	// });
	// 返回
	return triggerField;
};