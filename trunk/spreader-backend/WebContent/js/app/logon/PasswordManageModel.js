Ext.onReady(function() {
			var form = new Ext.form.FormPanel({
						id : 'settingForm',
						name : 'settingForm',
						labelWidth : 100, // 标签宽度
						frame : true, // 是否渲染表单面板背景色
						defaultType : 'textfield', // 表单元素默认类型
						labelAlign : 'right', // 标签对齐方式
						bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
						items : [{
									fieldLabel : '旧密码', // 标签
									inputType : 'password',
									id : 'oldPassword',
									name : 'oldPassword', // name:后台根据此name属性取值
									anchor : '95%' // 宽度百分比
								}, {
									fieldLabel : '新密码', // 标签
									inputType : 'password',
									id : 'newPassword',
									name : 'newPassword',
									allowBlank : false,
									anchor : '95%' // 宽度百分比
								}, {
									fieldLabel : '确认密码', // 标签
									inputType : 'password',
									id : 'newPassword2',
									name : 'newPassword2',
									allowBlank : false,
									anchor : '95%' // 宽度百分比
								}]
					});
			var win = new Ext.Window({
						title : '修改密码',
						layout : 'fit',
						width : 400, // 窗口宽度
						height : 300, // 窗口高度
						closable : false, // 是否可关闭
						collapsible : true, // 是否可收缩
						maximizable : true, // 设置是否可以最大化
						border : false, // 边框线设置
						constrain : true, // 设置窗口是否可以溢出父容器
						pageY : 20, // 页面定位X坐标
						pageX : document.body.clientWidth / 2 - 400 / 2, // 页面定位Y坐标
						items : [form], // 嵌入的表单面板
						buttons : [{ // 窗口底部按钮配置
							text : '提交', // 按钮文本
							handler : function() { // 按钮响应函数
								update();
							}
						}, {	// 窗口底部按钮配置
									text : '重置', // 按钮文本
									handler : function() { // 按钮响应函数
										form.form.reset();
									}
								}]
					});
			win.show();

			function update() {
				var tform = form.getForm();
				var oldPassword = tform.findField("oldPassword").getValue();
				var newPassword = tform.findField("newPassword").getValue();
				var newPassword2 = tform.findField("newPassword2").getValue();
				if (newPassword != newPassword2) {
					Ext.Msg.alert('提示', '两次输入的密码不一致');
					return;
				}
				Ext.Ajax.request({
							url : '../psw/update?_time=' + new Date().getTime(),
							success : function(response, opts) {
								var result = Ext.decode(response.responseText);
								if (result.success) {
									Ext.Msg.alert('提示', '修改成功');
								} else {
									Ext.Msg.alert('提示', '修改失败');
								}
							},
							failure : function(response, opts) {
								Ext.MessageBox.alert('提示', '修改失败');
							},
							params : {
								'oldPassword' : oldPassword,
								'newPassword' : newPassword
							}
						});
			}
		});