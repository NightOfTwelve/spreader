Ext.onReady(function() {
	/**
	 * 富文本输入框
	 */
	var contentPanel = new Ext.form.FormPanel({
				layout : 'fit',
				frame : true,
				items : [{
					id : 'htmleditor',
					name : 'htmleditor',
					xtype : 'htmleditor',
					plugins : [new Ext.ux.form.HtmlEditor.Link(),
							new Ext.ux.form.HtmlEditor.Image(),
							new Ext.ux.form.HtmlEditor.Table(),
							new Ext.ux.form.HtmlEditor.HR(),
							new Ext.ux.form.HtmlEditor.Word(),
							new Ext.ux.form.HtmlEditor.RemoveFormat()]
				}]
			});
	/**
	 * 富文本弹出窗口
	 */
	var contentWindow = new Ext.Window({
				title : '<span class="commoncss">更多内容</span>', // 窗口标题
				layout : 'fit', // 设置窗口布局模式
				width : 700, // 窗口宽度
				height : 450, // 窗口高度
				closable : false, // 是否可关闭
				collapsible : false, // 是否可收缩
				maximizable : true, // 设置是否可以最大化
				border : false, // 边框线设置
				constrain : true, // 设置窗口是否可以溢出父容器
				draggable : false,
				animateTarget : Ext.getBody(),
				pageY : 20, // 页面定位Y坐标
				pageX : document.documentElement.clientWidth / 2 - 700 / 2, // 页面定位X坐标
				items : [contentPanel], // 嵌入的表单面板
				buttons : [{ // 窗口底部按钮配置
					text : '关闭', // 按钮文本
					iconCls : 'deleteIcon', // 按钮图标
					handler : function() { // 按钮响应函数
						contentWindow.hide();
					}
				}
				// ,
				// { // 窗口底部按钮配置
				// text : '保存', // 按钮文本
				// iconCls : 'acceptIcon', // 按钮图标
				// handler : function() { // 按钮响应函数
				// var value = Ext.getCmp('htmleditor').getValue();
				// // Ext.MessageBox.alert('提示', value);
				// alert(value);
				// }
				// }, { // 窗口底部按钮配置
				// text : '重置', // 按钮文本
				// iconCls : 'tbar_synchronizeIcon', // 按钮图标
				// handler : function() { // 按钮响应函数
				// contentPanel.form.reset();
				// }
				// }
				]
			});

	Ext.getCmp('htmleditor').on('initialize', function() {
				Ext.getCmp('htmleditor').focus();
			})
	// 页数
	var number = 20;
	var numtext = new Ext.form.TextField({
				id : 'maxpage',
				name : 'maxpage',
				width : 60,
				emptyText : '每页条数',
				// 激活键盘事件
				enableKeyEvents : true,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							bbar.pageSize = Number(numtext.getValue());
							number = Number(numtext.getValue());
							contentStore.reload({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
						}
					}
				}
			});
	/**
	 * 内容库查询条件
	 */
	var contentQueryForm = new Ext.form.FormPanel({
				region : 'north',
				title : "筛选条件",
				// collapsible : true,
				frame : true,
				id : 'contentQueryForm',
				// border : true,
				labelWidth : 90, // 标签宽度
				frame : true,
				labelAlign : 'right',
				bodyStyle : 'padding:5 5 5 5',
				buttonAlign : 'center',
				height : 180,
				items : [{ // 行1
					layout : "column",
					items : [{
								columnWidth : .33,
								layout : "form",
								items : [{
											xtype : "datefield",
											fieldLabel : "发布起始时间",
											name : 'sPubDate'
										}]
							}, {
								columnWidth : .33,
								layout : "form",
								items : [{
											xtype : "datefield",
											fieldLabel : "发布结束时间",
											name : 'ePubDate'
										}]
							}, {
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "微博分类",
											name : 'categoryName'
										}]
							}]
				}, {	// 行2
							layout : "column",
							items : [{
										columnWidth : .33,
										layout : "form",
										items : [{
													xtype : "datefield",
													fieldLabel : "爬取起始时间",
													name : 'sSyncDate'
												}]
									}, {
										columnWidth : .33,
										layout : "form",
										items : [{
													xtype : "datefield",
													fieldLabel : "爬取结束时间",
													name : 'eSyncDate'
												}]
									}, {
										columnWidth : .3,
										layout : "form",
										items : [{
													xtype : "textfield",
													fieldLabel : "微博作者",
													name : 'userName'
												}]
									}]
						}],
				buttonAlign : "center",
				buttons : [{
					text : "查询",
					handler : function() { // 按钮响应函数
						var tform = contentQueryForm.getForm();
						var sPubDate = tform.findField("sPubDate").getValue();
						var ePubDate = tform.findField("ePubDate").getValue();
						var categoryName = tform.findField("categoryName")
								.getValue();
						var sSyncDate = tform.findField("sSyncDate").getValue();
						var eSyncDate = tform.findField("eSyncDate").getValue();
						var userName = tform.findField("userName").getValue();
						var num = numtext.getValue();
						contentStore.setBaseParam('categoryName', Ext
										.isEmpty(categoryName)
										? null
										: categoryName);
						contentStore.setBaseParam('sPubDate', sPubDate == ''
										? null
										: sPubDate);
						contentStore.setBaseParam('ePubDate', ePubDate == ''
										? null
										: ePubDate);
						contentStore.setBaseParam('sSyncDate', sSyncDate == ''
										? null
										: sSyncDate);
						contentStore.setBaseParam('eSyncDate', eSyncDate == ''
										? null
										: eSyncDate);
						contentStore.setBaseParam('userName', Ext
										.isEmpty(userName) ? null : userName);
						contentStore.setBaseParam('limit', Ext.isEmpty(num)
										? number
										: Number(num));
						contentStore.load();
					}
				}, {
					text : "重置",
					handler : function() { // 按钮响应函数
						contentQueryForm.form.reset();
					}
				}]
			});
	/**
	 * 用户信息列表
	 */
	// 定义表格数据源
	var contentStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../contentlib/grid'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'type'
								}, {
									name : 'websiteId'
								}, {
									name : 'websiteContentId'
								}, {
									name : 'websiteRefId'
								}, {
									name : 'websiteUid'
								}, {
									name : 'uid'
								}, {
									name : 'nickName'
								}, {
									name : 'title'
								}, {
									name : 'pubDate'
								}, {
									name : 'syncDate'
								}, {
									name : 'refCount'
								}, {
									name : 'replyCount'
								}, {
									name : 'entry'
								}, {
									name : 'content'
								}]),
				autoLoad : {
					params : {
						start : 0,
						limit : 20
					}
				}
			});
	// 分页带上查询条件
	// contentStore.on('beforeload', function() {
	// var pfrom = contentQueryForm.getForm();
	// var sPubDate = pfrom.findField("sPubDate").getValue();
	// var ePubDate = pfrom.findField("ePubDate").getValue();
	// var categoryName = pfrom.findField("categoryName").getValue();
	// var sSyncDate = pfrom.findField("sSyncDate").getValue();
	// var eSyncDate = pfrom.findField("eSyncDate").getValue();
	// var userName = pfrom.findField("userName").getValue();
	// var limit = numtext.getValue();
	// this.baseParams = {
	// sPubDate : Ext.isEmpty(sPubDate) ? null : sPubDate,
	// ePubDate : Ext.isEmpty(ePubDate) ? null : ePubDate,
	// sSyncDate : Ext.isEmpty(sSyncDate) ? null : sSyncDate,
	// eSyncDate : Ext.isEmpty(eSyncDate) ? null : eSyncDate,
	// categoryName : Ext.isEmpty(categoryName)
	// ? null
	// : categoryName,
	// userName : Ext.isEmpty(userName) ? null : userName,
	// limit : Ext.isEmpty(limit) ? number : Number(limit)
	// };
	// });

	// 定义Checkbox
	var sm = new Ext.grid.CheckboxSelectionModel();
	var rownums = new Ext.grid.RowNumberer({
				header : 'NO',
				locked : true
			})
	// 定义表格列CM
	var cm = new Ext.ux.grid.LockingColumnModel([rownums, {
				header : '编号',
				dataIndex : 'id',
				locked : true,
				width : 80
			}, {
				header : 'uid',
				dataIndex : 'uid',
				locked : true,
				hidden : true,
				width : 80
			}, {
				header : '昵称',
				dataIndex : 'nickName',
				locked : true,
				width : 100
			}, {
				header : '类型',
				dataIndex : 'type',
				// renderer : renderGender,
				locked : true,
				width : 80
			}, {
				header : '标题',
				dataIndex : 'title',
				width : 100
			}, {
				header : '内容',
				dataIndex : 'content',
				// renderer : renderBrief,
				width : 100
			}, {
				header : '详细',
				dataIndex : 'showdtl',
				renderer : function(value, cellmeta, record) {
					return '<a href="javascript:void(0);"><img src="../css/images/icon/preview.png"/></a>';
				},
				width : 35
			}, {
				header : 'websiteId',
				dataIndex : 'websiteId',
				width : 100,
				sortable : true
			}, {
				header : 'websiteContentId',
				dataIndex : 'websiteContentId',
				width : 100,
				sortable : true
			}, {
				header : 'websiteRefId',
				dataIndex : 'websiteRefId',
				width : 100,
				sortable : true
			}, {
				header : 'websiteUid',
				dataIndex : 'websiteUid',
				width : 100
			}, {
				header : '发布时间',
				dataIndex : 'pubDate',
				width : 100
			}, {
				header : '爬取时间',
				dataIndex : 'syncDate',
				width : 100
			}, {
				header : 'refCount',
				dataIndex : 'refCount',
				width : 100
			}, {
				header : 'replyCount',
				dataIndex : 'replyCount',
				width : 100
			}, {
				header : 'entry',
				dataIndex : 'entry',
				width : 100
			}]);
	// // 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : contentStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext]
			});

	// 定义grid表格
	var contentGrid = new Ext.grid.GridPanel({
				region : 'center',
				id : 'contentGrid',
				height : 440,
				stripeRows : true, // 斑马线
				frame : true,
				// autoWidth : true,
				autoScroll : true,
				store : contentStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				bbar : bbar,
				// sm : sm,
				cm : cm,
				view : new Ext.ux.grid.LockingGridView(), // 锁定列视图
				tbar : [{
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								contentStore.reload();
							}
						}],
				onCellClick : function(grid, rowIndex, columnIndex, e) {
					var selesm = grid.getSelectionModel().getSelections();
					// var userid = selesm[0].data.id;
					var cont = selesm[0].data.content;
					var contentcol = grid.getColumnModel()
							.getDataIndex(columnIndex);
					if (contentcol == 'showdtl') {
						// TODO
						var edit = Ext.getCmp('htmleditor');
						edit.setValue(cont);
						contentWindow.show();

					}
				}
			});

	// 注册事件
	contentGrid.on('cellclick', contentGrid.onCellClick, contentGrid);

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [contentQueryForm, contentGrid]
			});
});
