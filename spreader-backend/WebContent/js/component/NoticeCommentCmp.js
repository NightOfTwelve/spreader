/**
 * 消息展示，评论相关组件
 */

var commentForm = new Ext.form.FormPanel({
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
					title : '评论',
					autoHeight : true,
					items : [{
								layout : 'form',
								columnWidth : .5,
								labelWidth : 60,
								items : [{
											xtype : 'label',
											fieldLabel : 'replayContentId',
											hidden : true,
											id : 'replayContentId',
											labelStyle : 'padding:0px',
											text : ''
										}, {
											xtype : 'label',
											fieldLabel : '回复内容',
											id : 'replayContent',
											style : 'color:green;',
											labelStyle : 'color:#C34B00;',
											text : ''
										}, {
											xtype : 'label',
											fieldLabel : '引用评论',
											id : 'refReplayContent',
											style : 'color:green;',
											labelStyle : 'padding:0px',
											text : ''
										}]
							}, {
								layout : 'form',
								columnWidth : .5,
								items : [{
											xtype : 'label',
											fieldLabel : 'toUid',
											id : 'toUid',
											hidden : true,
											labelStyle : 'padding:0px',
											text : ''
										}, {
											xtype : 'label',
											fieldLabel : '回复人',
											id : 'fromUser',
											style : 'color:green;',
											labelStyle : 'color:red;',
											text : ''
										}, {
											xtype : 'label',
											fieldLabel : '评论人',
											id : 'toUser',
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
		items : [{
					xtype : 'label',
					fieldLabel : '引用微博',
					id : 'refContent',
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
//						var replayContentId = Ext.getCmp("replayContentId").text;
//						var toUid = Ext.getCmp("toUid").text;
//						replayAction(replayContentId, toUid, null, false);
					}
				}]
	}]
});
/**
 * 评论显示窗口
 */
var commentWindow = new Ext.Window({
			title : '评论回复',
			layout : 'fit',
			y : 50,
			modal : true,
			resizable : true,
			width : 600,
			height : 300,
			closeAction : 'hide',
			plain : true,
			items : [commentForm]
		});
/**
 * 获取一个评论详细信息
 * 
 * @param {}
 *            replayId
 * @return {}
 */
function getCommentReplay(replayId) {
	var data = false;
	Ext.Ajax.request({
				url : '../notice/replaycomment?_time=' + new Date().getTime(),
				params : {
					'replayId' : replayId
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
function settingCommentForm(data) {
	Ext.getCmp("replayContentId").setText(data.replayContentId);
	Ext.getCmp("toUid").setText(data.toUid);
	Ext.getCmp("fromUser").setText(data.fromUser);
	Ext.getCmp("toUser").setText(data.toUser);
	Ext.getCmp("replayContent").setText(data.replayContent);
	Ext.getCmp("refReplayContent").setText(data.refReplayContent);
	Ext.getCmp("refContent").setText(data.refContent);
	commentWindow.show();
}