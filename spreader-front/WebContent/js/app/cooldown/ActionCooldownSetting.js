Ext.onReady(function() {
			var form = new Ext.form.FormPanel({
						id : 'settingForm',
						name : 'settingForm',
						labelWidth : 50, // 标签宽度
						// frame : true, // 是否渲染表单面板背景色
						defaultType : 'textfield', // 表单元素默认类型
						labelAlign : 'right', // 标签对齐方式
						bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
						items : [{
									fieldLabel : '已设置的值', // 标签
									name : 'perHour', // name:后台根据此name属性取值
									allowBlank : false,
									anchor : '95%' // 宽度百分比
								}, {
									fieldLabel : 'MaxDownloadPerHour', // 标签
									id : 'maxDownloadPerHour',
									name : 'maxDownloadPerHour', // name:后台根据此name属性取值
									anchor : '95%' // 宽度百分比
								}]
					});
			var win = new Ext.Window({
						title : '设置曲线',
						layout : 'fit',
						width : 400, // 窗口宽度
						height : 200, // 窗口高度
						closable : false, // 是否可关闭
						collapsible : true, // 是否可收缩
						maximizable : true, // 设置是否可以最大化
						border : false, // 边框线设置
						constrain : true, // 设置窗口是否可以溢出父容器
						pageY : 20, // 页面定位X坐标
						pageX : document.body.clientWidth / 2 - 400 / 2, // 页面定位Y坐标
						items : [firstForm], // 嵌入的表单面板
						buttons : [{ // 窗口底部按钮配置
							text : '提交', // 按钮文本
							handler : function() { // 按钮响应函数
								submitTheForm();
							}
						}, {	// 窗口底部按钮配置
									text : '重置', // 按钮文本
									handler : function() { // 按钮响应函数
										form.form.reset();
									}
								}]
					});
			win.on('show', function() {
						getHour();
					});
			win.show();

			function submitTheForm() {
				var tform = form.getForm();
				var hour = tform.findField("maxDownloadPerHour").getValue();
				if (Ext.isEmpty(hour)) {
					Ext.Msg.alert('提示', '请填写值');
					return;
				}
				Ext.Ajax.request({
							url : '/spreader-front/cooldown/setting?_time='
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

			function getHour() {
				Ext.Ajax.request({
							url : '/spreader-front/cooldown/get?_time='
									+ new Date().getTime(),
							success : function(response, opts) {
								var hour = Ext.util.JSON
										.decode(response.responseText);
								form.getForm().findField("maxDownloadPerHour")
										.setValue(hour);
							},
							failure : function(response, opts) {
								Ext.MessageBox.alert('提示', '获取数据失败');
							}
						});
			}
		});