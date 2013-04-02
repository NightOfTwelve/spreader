Ext.onReady(function() {
	var form = new Ext.form.FormPanel({
				region : 'north',
				id : 'settingForm',
				name : 'settingForm',
				height : 100,
				labelWidth : 100, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				defaultType : 'textfield', // 表单元素默认类型
				labelAlign : 'right', // 标签对齐方式
				bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
				items : [{
							fieldLabel : '已设置的值', // 标签
							id : 'perHour',
							name : 'perHour', // name:后台根据此name属性取值
							readOnly : true,
							anchor : '95%' // 宽度百分比
						}, {
							fieldLabel : 'MaxHour', // 标签
							id : 'maxDownloadPerHour',
							name : 'MaxDownloadPerHour', // name:后台根据此name属性取值
							anchor : '95%' // 宽度百分比
						}],
				buttonAlign : "center",
				buttons : [{
							text : "保存",
							handler : function() { // 按钮响应函数
								submitPerHour();
							}
						}, {
							text : "重置",
							handler : function() { // 按钮响应函数
								form.form.reset();
							}
						}]
			});
	var form2 = new Ext.form.FormPanel({
				region : 'center',
				id : 'settingForm2',
				name : 'settingForm2',
				labelWidth : 100, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				defaultType : 'textfield', // 表单元素默认类型
				labelAlign : 'right', // 标签对齐方式
				bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
				items : [{
							fieldLabel : 'Rates', // 标签
							xtype : 'textarea',
							autoScroll : true,
							height : 350,
							id : 'maxDownloadRates',
							name : 'maxDownloadRates', // name:后台根据此name属性取值
							anchor : '95%' // 宽度百分比
						}],
				buttonAlign : "center",
				buttons : [{
							text : "保存",
							handler : function() { // 按钮响应函数
								submitRates();
							}
						}, {
							text : "重置",
							handler : function() { // 按钮响应函数
								form2.form.reset();
							}
						}]
			});
	var win = new Ext.Window({
		title : '设置曲线',
		layout : 'border',
		width : 400, // 窗口宽度
		height : 600, // 窗口高度
		closable : false, // 是否可关闭
		collapsible : true, // 是否可收缩
		maximizable : true, // 设置是否可以最大化
		border : false, // 边框线设置
		constrain : true, // 设置窗口是否可以溢出父容器
		pageY : 20, // 页面定位X坐标
		pageX : document.body.clientWidth / 2 - 400 / 2, // 页面定位Y坐标
		items : [form, form2]
			// 嵌入的表单面板
		});
	win.on('show', function() {
				getHour();
				getRates();
			});
	win.show();

	function submitPerHour() {
		var tform = form.getForm();
		var hour = tform.findField("maxDownloadPerHour").getValue();
		if (Ext.isEmpty(hour)) {
			Ext.Msg.alert('提示', '请填写值');
			return;
		}
		Ext.Ajax.request({
					url : '/spreader-front/cooldown/sethour?_time='
							+ new Date().getTime(),
					success : function(response, opts) {
						Ext.Msg.alert('提示', '设置成功');
						getHour();
					},
					failure : function(response, opts) {
						Ext.MessageBox.alert('提示', '数据保存失败');
					},
					params : {
						hour : hour
					}
				});
	}
	function submitRates() {
		var tform = form2.getForm();
		var rates = tform.findField("maxDownloadRates").getValue();
		var ratesArr = new Array();
		if (!Ext.isEmpty(rates)) {
			ratesArr = text2Array(rates);
		}
		if (!checkRates(ratesArr)) {
			Ext.Msg.alert('提示', 'coolDown_rate数据不正确');
			return;
		}
		Ext.Ajax.request({
					url : '/spreader-front/cooldown/setrates?_time='
							+ new Date().getTime(),
					success : function(response, opts) {
						Ext.Msg.alert('提示', '设置成功');
						getHour();
					},
					failure : function(response, opts) {
						Ext.MessageBox.alert('提示', '数据保存失败');
					},
					params : {
						rates : ratesArr
					}
				});
	}
	function checkRates(rates) {
		for (var i = 0; i < rates.length; i++) {
			var rate = rates[i];
			if (rate > 1 || rate < 0) {
				return false;
			}
		}
		return true;
	}
	function text2Array(text) {
		var r = text.split(/\r?\n/);
		return r;
	}
	function text2Rows(text) {
		var str = '';
		if (Ext.isEmpty(text)) {
			return str;
		}
		for (var i = 0; i < text.length; i++) {
			var num = text[i];
			str = str + num + '\n';
		}
		var txt = str.substr(0, str.length - 1);
		return txt;
	}
	function getHour() {
		Ext.Ajax.request({
					url : '/spreader-front/cooldown/get?_time='
							+ new Date().getTime(),
					success : function(response, opts) {
						var hour = Ext.util.JSON.decode(response.responseText);
						form.getForm().findField("perHour").setValue(hour);
					},
					failure : function(response, opts) {
						Ext.MessageBox.alert('提示', '获取数据失败');
					}
				});
	}
	function getRates() {
		Ext.Ajax.request({
					url : '/spreader-front/cooldown/rates?_time='
							+ new Date().getTime(),
					success : function(response, opts) {
						var rates = Ext.util.JSON.decode(response.responseText);
						form2.getForm().findField("maxDownloadRates")
								.setValue(text2Rows(rates));
					},
					failure : function(response, opts) {
						Ext.MessageBox.alert('提示', '获取数据失败');
					}
				});
	}
});