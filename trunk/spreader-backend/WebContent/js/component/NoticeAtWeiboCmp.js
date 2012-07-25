/**
 * 消息展示，‘@’微博相关组件
 */
var atWeiboForm = new Ext.form.FormPanel({
	labelWidth : 60,
	labelAlign : 'right',
	layout : 'form',
	frame : true,
	items : [{
				layout : 'column',
				items : [{
							layout : 'column',
							columnWidth : 1,
							xtype : 'fieldset',
							style : 'margin-bottom: 20px',
							collapsible : true,
							title : '作者',
							autoHeight : true,
							items : [{
										layout : 'form',
										items : [{
													xtype : 'label',
													fieldLabel : '微博作者',
													id : 'fromUserAtWeibo',
													labelStyle : 'padding:0px',
													text : ''
												}, {
													xtype : 'label',
													fieldLabel : '@人',
													id : 'toUserAtWeibo',
													style : 'color:green;',
													labelStyle : 'color:red;',
													text : ''
												}, {
													xtype : 'label',
													fieldLabel : '@人',
													id : 'toUidAtWeibo',
													hidden : true,
													labelStyle : 'padding:0px',
													text : ''
												}]
									}]
						}]
			}, {
				xtype : 'fieldset',
				title : '微博',
				collapsible : true,
				autoHeight : true,
				style : 'margin-bottom: 10px',
				items : [{
							xtype : 'label',
							fieldLabel : '@微博ID',
							id : 'contentIdAtWeibo',
							hidden : true,
							labelStyle : 'padding:0px',
							text : ''
						}, {
							xtype : 'label',
							fieldLabel : '@微博',
							id : 'contentAtWeibo',
							style : 'color:#87783C;',
							labelStyle : 'color:#E13CB4;',
							text : ''
						}, {
							xtype : 'label',
							fieldLabel : '引用微博',
							id : 'refContentAtWeibo',
							style : 'color:green;',
							labelStyle : 'color:#A54BF0;',
							text : ''
						}]
			}, {
				xtype : 'fieldset',
				title : '操作',
				collapsible : true,
				autoHeight : true,
				buttonAlign : "center",
				buttons : [{
							text : '转发微博',
							handler : function() {
								var contentId = Ext.getCmp("contentIdAtWeibo").text;
								var toUid = Ext.getCmp("toUidAtWeibo").text;
								forwardAction(toUid, contentId);
							}
						}, {
							text : '回复微博',
							handler : function() {
								var contentId = Ext.getCmp("contentIdAtWeibo").text;
								var toUid = Ext.getCmp("toUidAtWeibo").text;
								replayAction(contentId, toUid, null, false);
							}
						}]
			}]
});
/**
 * 微博显示窗口
 */
var atWeiboWindow = new Ext.Window({
			title : '@到的微博',
			layout : 'fit',
			y : 50,
			modal : true,
			resizable : true,
			width : 600,
			height : 400,
			closeAction : 'hide',
			plain : true,
			items : [atWeiboForm]
		});
/**
 * 获取一个@详细信息
 * 
 * @param {}
 *            noticeId
 * @return {}
 */
function getAtWeibo(noticeId) {
	var data = false;
	Ext.Ajax.request({
				url : '../notice/atweibo?_time=' + new Date().getTime(),
				params : {
					'noticeId' : noticeId
				},
				async : false,
				success : function(response) {
					var result = Ext.decode(response.responseText);
					data = result;
				},
				failure : function() {
				}
			});
	return data;
}
/**
 * 设置显示区域
 * 
 * @param {}
 *            data
 * @return {}
 */
function settingAtWeiForm(data) {
	Ext.getCmp("toUidAtWeibo").setText(data.toUid);
	Ext.getCmp("contentIdAtWeibo").setText(data.contentId);
	Ext.getCmp("fromUserAtWeibo").setText(data.fromUser);
	Ext.getCmp("toUserAtWeibo").setText(data.toUser);
	Ext.getCmp("refContentAtWeibo").setText(data.refContent);
	Ext.getCmp("contentAtWeibo").setText(data.content);
	atWeiboWindow.show();
}