Ext.onReady(function() {
	var yybReportWin;
	var yybPropWin;
	var wdjReportWin;
	var gzipReportWin;
	var yybReportForm = new Ext.form.FormPanel({
		labelWidth : 60,
		fileUpload : true,
		layout : 'fit',
		frame : true,
		bodyStyle : 'padding: 10px',
		defaults : {
			anchor : '95%',
			allowBlank : false,
			msgTarget : 'side'
		},
		items : [{
					xtype : 'fileuploadfield',
					id : 'yybReport',
					emptyText : '请选择数据文件',
					fieldLabel : '数据文件',
					name : 'yybReport',
					buttonText : '···'
				}],
		buttons : [{
			text : '解析',
			handler : function() {
				if (yybReportForm.getForm().isValid()) {
					yybReportForm.getForm().submit({
						url : '/spreader-front/android/tencent/yybreport?_time='
								+ new Date().getTime(),
						method : 'POST',
						waitMsg : '上传中...',
						timeout : 600000,
						success : function(f, action) {
							var str = Ext.decode(action.response.responseText).data;
							setPrintContent(str);
							f.reset();
						},
						failure : function(f, action) {
							setPrintContent(Ext
									.decode(action.response.responseText).data);
							f.reset();
						}
					});
					yybReportWin.hide();
				}
			}
		}, {
			text : '重置',
			handler : function() {
				yybReportForm.getForm().reset();
			}
		}]
	});
	var wdjReportForm = new Ext.form.FormPanel({
		labelWidth : 60,
		fileUpload : true,
		layout : 'fit',
		frame : true,
		bodyStyle : 'padding: 10px',
		defaults : {
			anchor : '95%',
			allowBlank : false,
			msgTarget : 'side'
		},
		items : [{
					xtype : 'fileuploadfield',
					id : 'wdjReport',
					emptyText : '请选择数据文件',
					fieldLabel : '数据文件',
					name : 'wdjReport',
					buttonText : '···'
				}],
		buttons : [{
			text : '解析',
			handler : function() {
				if (wdjReportForm.getForm().isValid()) {
					wdjReportForm.getForm().submit({
						url : '/spreader-front/android/wandoujia/wdjreport?_time='
								+ new Date().getTime(),
						method : 'POST',
						waitMsg : '上传中...',
						timeout : 600000,
						success : function(f, action) {
							var str = Ext.decode(action.response.responseText).data;
							setPrintContent(str);
							f.reset();
						},
						failure : function(f, action) {
							setPrintContent(Ext
									.decode(action.response.responseText).data);
							f.reset();
						}
					});
					wdjReportWin.hide();
				}
			}
		}, {
			text : '重置',
			handler : function() {
				wdjReportForm.getForm().reset();
			}
		}]
	});

	var gzipReportForm = new Ext.form.FormPanel({
		labelWidth : 60,
		fileUpload : true,
		layout : 'fit',
		frame : true,
		bodyStyle : 'padding: 10px',
		defaults : {
			anchor : '95%',
			allowBlank : false,
			msgTarget : 'side'
		},
		items : [{
					xtype : 'fileuploadfield',
					id : 'gzipReport',
					emptyText : '请选择数据文件',
					fieldLabel : '数据文件',
					name : 'gzipReport',
					buttonText : '···'
				}],
		buttons : [{
			text : '解析',
			handler : function() {
				if (gzipReportForm.getForm().isValid()) {
					gzipReportForm.getForm().submit({
						url : '/spreader-front/android/wandoujia/ungzip?_time='
								+ new Date().getTime(),
						method : 'POST',
						waitMsg : '上传中...',
						timeout : 600000,
						success : function(f, action) {
							var str = Ext.decode(action.response.responseText).data;
							setPrintContent(str);
							f.reset();
						},
						failure : function(f, action) {
							setPrintContent(Ext
									.decode(action.response.responseText).data);
							f.reset();
						}
					});
					gzipReportWin.hide();
				}
			}
		}, {
			text : '重置',
			handler : function() {
				gzipReportForm.getForm().reset();
			}
		}]
	});
	var yybPropForm = new Ext.form.FormPanel({
		labelWidth : 120,
		fileUpload : true,
		frame : true,
		bodyStyle : 'padding: 10px',
		defaults : {
			anchor : '95%',
			allowBlank : false,
			msgTarget : 'side'
		},
		items : [{
					xtype : 'fileuploadfield',
					id : 'downReport',
					emptyText : '请选择数据文件',
					fieldLabel : '下载包Type0',
					name : 'downReport',
					buttonText : '···'
				}, {
					xtype : 'fileuploadfield',
					id : 'installReport',
					emptyText : '请选择数据文件',
					fieldLabel : '安装包Type8',
					name : 'installReport',
					buttonText : '···'
				}, {
					xtype : 'fileuploadfield',
					id : 'actionReport',
					emptyText : '请选择数据文件',
					fieldLabel : 'ActionDownload包Type15',
					name : 'actionReport',
					buttonText : '···'
				}
//				, {
//					xtype : 'fileuploadfield',
//					id : 'useropReport',
//					emptyText : '请选择数据文件',
//					fieldLabel : 'userOperation包Type4',
//					name : 'useropReport',
//					buttonText : '···'
//				}
				],
		buttons : [{
			text : '解析',
			handler : function() {
				if (yybPropForm.getForm().isValid()) {
					yybPropForm.getForm().submit({
						url : '/spreader-front/android/tencent/yybproperties?_time='
								+ new Date().getTime(),
						method : 'POST',
						waitMsg : '上传中...',
						timeout : 600000,
						success : function(f, action) {
							var str = Ext.decode(action.response.responseText).data;
							setPrintContent(str);
							f.reset();
						},
						failure : function(f, action) {
							setPrintContent(Ext
									.decode(action.response.responseText).data);
							f.reset();
						}
					});
					yybPropWin.hide();
				}
			}
		}, {
			text : '重置',
			handler : function() {
				yybPropForm.getForm().reset();
			}
		}]
	});

	var printPanel = new Ext.form.FormPanel({
				region : 'center',
				frame : true,
				items : [{
							layout : 'fit',
							title : '分析结果',
							id : 'printContent',
							name : 'printContent',
							xtype : 'textarea',
							width : 800,
							height : 700,
							autoScroll : true
						}],
				tbar : [{
							text : '查看应用宝包文件',
							iconCls : 'uploadIcon',
							handler : function() {
								if (!yybReportWin) {
									yybReportWin = new Ext.Window({
												title : '查看包内容',
												layout : 'fit',
												width : 400,
												height : 140,
												closeAction : 'hide',
												plain : true,
												items : [yybReportForm]
											});
									yybReportWin.on('show', function() {
												yybReportForm.getForm().reset();
											});
								}
								yybReportWin.show(this);
							}
						}, '-', {
							text : '获取应用宝配置',
							iconCls : 'uploadIcon',
							handler : function() {
								if (!yybPropWin) {
									yybPropWin = new Ext.Window({
												title : '上传文件',
												layout : 'fit',
												width : 400,
												height : 280,
												closeAction : 'hide',
												plain : true,
												items : [yybPropForm]
											});
									yybPropWin.on('show', function() {
												yybPropForm.getForm().reset();
											});
								}
								yybPropWin.show(this);
							}
						}, '-', {
							text : '豌豆荚加密过的包',
							iconCls : 'uploadIcon',
							handler : function() {
								if (!wdjReportWin) {
									wdjReportWin = new Ext.Window({
												title : '上传文件',
												layout : 'fit',
												width : 400,
												height : 140,
												closeAction : 'hide',
												plain : true,
												items : [wdjReportForm]
											});
									wdjReportWin.on('show', function() {
												wdjReportForm.getForm().reset();
											});
								}
								wdjReportWin.show(this);
							}
						}, '-', {
							text : 'GZip格式',
							iconCls : 'uploadIcon',
							handler : function() {
								if (!gzipReportWin) {
									gzipReportWin = new Ext.Window({
												title : '上传文件',
												layout : 'fit',
												width : 400,
												height : 140,
												closeAction : 'hide',
												plain : true,
												items : [gzipReportForm]
											});
									gzipReportWin.on('show', function() {
												gzipReportForm.getForm()
														.reset();
											});
								}
								gzipReportWin.show(this);
							}
						}]
			});
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [printPanel]
			});

	function setPrintContent(str) {
		var txtCmp = Ext.getCmp('printContent');
		txtCmp.setValue(null);
		txtCmp.setValue(str);
	}
});