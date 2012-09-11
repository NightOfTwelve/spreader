/**
 * 消息展示，评论相关组件
 */

var commentWeiboForm = new Ext.form.FormPanel({
	labelWidth : 55,
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
					title : '评论',
					autoHeight : true,
					items : [{
								layout : 'form',
								columnWidth : .5,
								labelWidth : 60,
								items : [{
											xtype : 'label',
											fieldLabel : 'replayContentIdWeibo',
											hidden : true,
											id : 'replayContentIdWeibo',
											labelStyle : 'padding:0px',
											text : ''
										}, {
											xtype : 'label',
											fieldLabel : 'toUidWeibo',
											id : 'toUidWeibo',
											hidden : true,
											labelStyle : 'padding:0px',
											text : ''
										}, {
											xtype : 'label',
											fieldLabel : '回复人',
											id : 'fromUserWeibo',
											style : 'color:green;',
											labelStyle : 'color:red;',
											text : ''
										}, {
											xtype : 'label',
											fieldLabel : '评论作者',
											id : 'toUserWeibo',
											labelStyle : 'padding:0px',
											text : ''
										}]
							}, {
								layout : 'form',
								columnWidth : .5,
								items : [{
											xtype : 'label',
											fieldLabel : '回复内容',
											id : 'replayContentWeibo',
											style : 'color:green;',
											labelStyle : 'color:#C34B00;',
											text : ''
										}]
							}]
				}]
	}, {
		xtype : 'fieldset',
		title : '微博',
		collapsible : true,
		autoHeight : true,
		items : [{
					xtype : 'label',
					fieldLabel : '评论微博',
					id : 'contentWeibo',
					style : 'color:green;',
					labelStyle : 'color:#A54BF0;padding:0px;',
					text : ''
				}]
	}, {
		xtype : 'fieldset',
		title : '操作',
		collapsible : true,
		autoHeight : true,
		buttonAlign : "center",
		buttons : [{
					text : '回复评论',
					handler : function() {
						Ext.MessageBox.alert("提示", "开发中...");
						return;
						// var replayContentId = Ext
						// .getCmp("replayContentIdWeibo").text;
						// var toUid = Ext.getCmp("toUidWeibo").text;
						// replayAction(replayContentId, toUid, null, false);
					}
				}]
	}]
});
/**
 * 评论显示窗口
 */
var commentWeiboWindow = new Ext.Window({
			title : '评论微博',
			layout : 'fit',
			y : 50,
			modal : true,
			resizable : true,
			width : 600,
			height : 300,
			closeAction : 'hide',
			plain : true,
			items : [commentWeiboForm]
		});
/**
 * 设置显示区域
 * 
 * @param {}
 *            data
 * @return {}
 */
function settingCommentWeiboForm(data) {
	Ext.getCmp("replayContentIdWeibo").setText(data.replayContentId);
	Ext.getCmp("toUidWeibo").setText(data.toUid);
	Ext.getCmp("fromUserWeibo").setText(data.fromUser);
	Ext.getCmp("toUserWeibo").setText(data.toUser);
	Ext.getCmp("replayContentWeibo").setText(data.replayContent);
	Ext.getCmp("contentWeibo").setText(data.content);
	commentWeiboWindow.show();
}