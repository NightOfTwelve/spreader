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
				}]
			});

	Ext.getCmp('htmleditor').on('initialize', function() {
				Ext.getCmp('htmleditor').focus();
			});
	var keywrodStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../contentlib/keyword?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							root : 'list'
						}, [{
									name : 'keywordId'
								}, {
									name : 'keywordName'
								}, {
									name : 'categoryId'
								}, {
									name : 'categoryName'
								}])
			});

	var krm = new Ext.grid.RowNumberer({
				header : 'NO',
				locked : true,
				width : 28
			});

	var kcm = new Ext.grid.ColumnModel([krm, {
				header : '关键编号',
				dataIndex : 'keywordId',
				width : 100
			}, {
				header : '关键字',
				dataIndex : 'keywordName',
				width : 100
			}, {
				header : '分类编号',
				dataIndex : 'categoryId',
				width : 100
			}, {
				header : '分类',
				dataIndex : 'categoryName',
				width : 100
			}]);

	var kgrid = new Ext.grid.GridPanel({
				id : 'kgrid',
				height : 300,
				stripeRows : true, // 斑马线
				frame : true,
				autoScroll : true,
				store : keywrodStore,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				},
				cm : kcm,
				tbar : [{
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								keywrodStore.reload();
							}
						}]
			});
	var keywordWindow = new Ext.Window({
				title : '<span class="commoncss">关键字</span>', // 窗口标题
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
				items : [kgrid], // 嵌入的表单面板
				buttons : [{ // 窗口底部按钮配置
					text : '关闭', // 按钮文本
					iconCls : 'deleteIcon', // 按钮图标
					handler : function() { // 按钮响应函数
						keywordWindow.hide();
					}
				}]
			});
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
						}, { // 行3
							layout : "column",
							items : [{
										columnWidth : .33,
										layout : "form",
										items : [{
													xtype : "textfield",
													fieldLabel : "关键字",
													name : 'keyword'
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
						var keyword = tform.findField("keyword").getValue();
						var num = numtext.getValue();
						contentStore.setBaseParam('categoryName', categoryName);
						contentStore.setBaseParam('keyword', keyword);
						contentStore.setBaseParam('sPubDate',
								renderDateHis(sPubDate));
						contentStore.setBaseParam('ePubDate',
								renderDateHis(ePubDate));
						contentStore.setBaseParam('sSyncDate',
								renderDateHis(sSyncDate));
						contentStore.setBaseParam('eSyncDate',
								renderDateHis(eSyncDate));
						contentStore.setBaseParam('userName', Ext
										.isEmpty(userName) ? '' : userName);
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
							url : '../contentlib/grid?_time='
									+ new Date().getTime()
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
								}
								// , {
								// name : 'url',
								// convert : function(value, rec) {
								// return "<a href='" + value
								// + "' target='_blank'>" + value + "</a>";
								// }
								// }
								, {
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
								}, {
									name : 'webSiteName'
								}, {
									name : 'categoryNames'
								}, {
									name : 'typeName'
								}]),
				autoLoad : {
					params : {
						start : 0,
						limit : 20
					}

				},
				timeout : 600000
			});
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				locked : true,
				width : 28
			});

	// 定义锁定列模型
	var cm = new Ext.grid.ColumnModel([rownum, {
				header : '编号',
				dataIndex : 'id',
				width : 80
			}, {
				header : '昵称',
				dataIndex : 'nickName',
				width : 100
			}, {
				header : '类型',
				dataIndex : 'websiteId',
				renderer : rendWebsiteNameFn,
				width : 80
			}, {
				header : '微博链接',
				dataIndex : 'url',
				renderer : function(value, cellmeta, record) {
					var entry = record.data.entry
					var websiteUid = record.data.websiteUid;
					if (!Ext.isEmpty(entry) && !Ext.isEmpty(websiteUid)) {
						var url = 'http://www.weibo.com/' + websiteUid + '/'
								+ entry;
						return "<a href='" + url + "' target='_blank'>" + url
								+ "</a>";
					}
					return '';
				},
				width : 100
			}, {
				header : '内容',
				dataIndex : 'content',
				// renderer : function(value, cellmeta, record) {
				// return '<a href="javascript:void(0);">'+value+'</a>';
				// },
				width : 100
			}, {
				header : '详细',
				dataIndex : 'showdtl',
				renderer : function(value, cellmeta, record) {
					return '<a href="javascript:void(0);"><img src="../css/images/icon/pencil_1.png"/></a>';
				},
				width : 35
			}, {
				header : '关键字',
				renderer : function showbutton() {
					var returnStr = "<input type='button' value='查看'/>";
					return returnStr;
				},
				width : 100
			}, {
				header : '发布时间',
				dataIndex : 'pubDate',
				renderer : renderDateHis,
				width : 120
			}, {
				header : '爬取时间',
				dataIndex : 'syncDate',
				renderer : renderDateHis,
				width : 120
			}, {
				header : '转发数',
				dataIndex : 'refCount',
				width : 100
			}, {
				header : '回复数',
				dataIndex : 'replyCount',
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
				height : 500,
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
				colModel : cm,
				tbar : [{
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								contentStore.reload();
							}
						}],
				onCellClick : function(grid, rowIndex, columnIndex, e) {
					var button = e.target.defaultValue;
					var record = grid.getStore().getAt(rowIndex);
					var data = record.data;
					var contentId = data.id;
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
					if (button == '查看') {
						keywrodStore.removeAll();
						keywrodStore.setBaseParam('contentId', contentId);
						keywrodStore.load();
						keywordWindow.show();
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
