/**
 * 消息展示，评论相关组件
 */

var taskCommentWeiboForm = new Ext.form.FormPanel({
	labelWidth : 100,
	labelAlign : 'left',
	layout : 'form',
	frame : true,
	items : [{
		layout : 'column',
		items : [{
					layout : 'column',
					columnWidth : 1,
					xtype : 'form',
					style : 'margin-bottom: 20px',
					items : [{
								layout : 'form',
								columnWidth : 1,
								labelWidth : 100,
								items : [{
											xtype : 'displayfield',
											fieldLabel : '微博的URL',
											id : 'taskCommentWeiboUrl',
											style : 'color:blue;',
											labelStyle : 'color:#A54BF0;padding:0px;',
											text : ''
										}, {
											xtype : 'displayfield',
											fieldLabel : '微博的作者',
											id : 'taskCommentWebsiteUid',
											style : 'color:green;',
											labelStyle : 'color:red;',
											text : ''
										}, {
											xtype : 'displayfield',
											fieldLabel : '评论的内容',
											id : 'taskCommentText',
											style : 'color:green;',
											labelStyle : 'color:#C34B00;',
											text : ''
										}, {
											xtype : 'displayfield',
											fieldLabel : '机器人',
											id : 'taskCommentWeiboUid',
											labelStyle : 'padding:0px',
											style : 'color:blue;',
											text : ''
										}]
							}]
				}]
	}]
});
/**
 * 评论显示窗口
 */
var taskCommentWeiboWindow = new Ext.Window({
			title : '回复微博',
			layout : 'fit',
			y : 50,
			modal : true,
			resizable : true,
			width : 500,
			height : 150,
			closeAction : 'hide',
			plain : true,
			items : [taskCommentWeiboForm]
		});
/**
 * 设置显示区域
 * 
 * @param {}
 *            data
 * @return {}
 */
function settingTaskCommentWeiboForm(data) {
	var websiteUid = data.websiteUid;
	var entry = data.entry;
	var url = 'http://www.weibo.com/' + websiteUid + '/' + entry;
	// Ext.getCmp("taskCommentWeiboUrl").setText('http://www.weibo.com/'
	// + websiteUid + '/' + entry);
	// Ext.getCmp("taskCommentWebsiteUid").setText(websiteUid);
	// Ext.getCmp("taskCommentText").setText(data.text);
	// Ext.getCmp("taskCommentWeiboUid").setText(data.id);
	Ext.getCmp("taskCommentWeiboUrl").setValue('<a href="' + url
			+ '" target="_blank">' + url + '</a>');
	Ext.getCmp("taskCommentWebsiteUid").setValue(websiteUid);
	Ext.getCmp("taskCommentText").setValue(data.text);
	Ext.getCmp("taskCommentWeiboUid").setValue(data.id);
	taskCommentWeiboWindow.show();
}
