Ext.onReady(function() {
	// 系统登录FORM
	var logonForm = new Ext.form.FormPanel({
				frame : true,
				el : 'logon',
				name : 'logonForm',
				id : 'logonForm',
				height : 120,
				defaultType : 'textfield',
				labelAlign : 'right',
				bodyStyle : 'padding:20 0 0 50',
				items : [{
							fieldLabel : '账&nbsp;户',
							allowBlank : false,
							id : 'accountId',
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
							id : 'password',
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
	logonWin.on('show', function() {
				// 设置延迟
				setTimeout(function() {
							var logonCmp = Ext.getCmp("logonForm");
							var account = logonCmp.findById('accountId');
							var password = logonCmp.findById('password');
							var cookieAccountId = getCookie('spreader.account');
							account.setValue(cookieAccountId);
							if (Ext.isEmpty(cookieAccountId)) {
								account.focus();
							} else {
								password.focus();
							}
						}, 200)
			}, this);
	/**
	 * 登录
	 */
	function logon() {
		var logonCmp = Ext.getCmp("logonForm");
		var logonForm = logonCmp.form;
		if (logonForm.isValid()) {
			logonForm.submit({
						url : '../account/logon?_time=' + new Date().getTime(),
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在验证身份,请稍候.....',
						success : function(form, action) {
							var result = action.result;
							if (result.success) {
								var accountId = logonCmp.findById('accountId')
										.getValue();
								// 设置cookie
								setCookie("spreader.account", accountId, 30);
								window.location.href = '../index/init';
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
	/**
	 * 设置cookie
	 */
	function setCookie(name, value, days) {
		if (Ext.isEmpty(days)) {
			days = 30;
		}
		var date = new Date();
		date.setTime(date.getTime() + days * 24 * 3600 * 1000);
		document.cookie = name + '=' + escape(value)
				+ ';path=/spreader-backend;expires=' + date.toGMTString();
	}
	/**
	 * 获取cookie
	 */
	function getCookie(name) {
		var search = name + "="
		if (document.cookie.length > 0) {
			offset = document.cookie.indexOf(search)
			if (offset != -1) {
				offset += search.length
				end = document.cookie.indexOf(";", offset)
				if (end == -1)
					end = document.cookie.length
				return unescape(document.cookie.substring(offset, end))
			} else
				return "";
		} else {
			return "";
		}
	}
});