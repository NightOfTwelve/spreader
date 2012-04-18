Ext.onReady(function() {
			// 系统登录FORM
			var logonForm = new Ext.form.FormPanel({
						frame : true,
						name : 'logonForm',
						id : 'logonForm',
						height : 120,
						defaultType : 'textfield',
						labelAlign : 'right',
						bodyStyle : 'padding:20 0 0 50',
						items : [{
									fieldLabel : '账&nbsp;户',
									allowBlank : false,
									name : 'accountId',
									anchor : '90%',
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												Ext.getCmp('password').focus();
											}
										}
									}
								}, {
									fieldLabel : '密&nbsp;码',
									name : 'password',
									allowBlank : false,
									inputType : 'password', // 设置为密码框输入类型
									anchor : '90%',
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												logon();
											}
										}
									}
								}]
					});

			// 系统登录窗口
			var logonWin = new Ext.Window({
						title : '系统登录',
						renderTo : Ext.getBody(),
						layout : 'fit',
						width : 400,
						height : 200,
						closeAction : 'hide',
						plain : true,
						modal : true,
						collapsible : true,
						titleCollapse : true,
						maximizable : false,
						draggable : false,
						closable : false,
						resizable : false,
						animateTarget : document.body,
						items : logonForm,
						buttons : [{
									text : '&nbsp;登录',
									iconCls : 'acceptIcon',
									handler : function() {
										logon();
									}
								}, {
									text : '&nbsp;重置',
									iconCls : 'tbar_synchronizeIcon',
									handler : function() {
										logonForm.form.reset();
									}
								}]
					});
			logonWin.show();
			/**
			 * 登录
			 */
			function logon() {
				var logonForm = Ext.getCmp("logonForm").form;
				if (logonForm.isValid()) {
					logonForm.submit({
								url : '../account/logon?_time='
										+ new Date().getTime(),
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在验证身份,请稍候.....',
								success : function(form, action) {
									var result = action.result;
									if (result.success) {
										window.location.href = '../index/showinit';
									} else {
										Ext.Msg.alert('提示', '验证失败');
										return;
									}
								},
								failure : function(form, action) {
									Ext.Msg.alert('提示', '验证失败');
									return;
								}
							});
				}
			}
		});
