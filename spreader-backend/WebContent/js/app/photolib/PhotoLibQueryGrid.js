Ext.onReady(function() {
	/**
	 * 是否头像
	 */
	var avaFlgStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['-1', '----------------------'], [true, '是'],
						[false, '否']]
			});
	/**
	 * 是否图片库
	 */
	var libFlgStore = new Ext.data.ArrayStore({
				fields : ['ID', 'NAME'],
				data : [['-1', '----------------------'], [true, '是'],
						[false, '否']]
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
							photoStore.reload({
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
	var photoQueryForm = new Ext.form.FormPanel({
				region : 'north',
				title : "筛选条件",
				// collapsible : true,
				frame : true,
				id : 'photoQueryForm',
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
								columnWidth : .3,
								layout : "form",
								items : [{
											xtype : "textfield",
											fieldLabel : "图片类型",
											name : 'picType'
										}]
							}, {
								columnWidth : .3, // 该列有整行中所占百分比
								layout : "form", // 从上往下的布局
								items : [{
											fieldLabel : '是否头像',
											xtype : 'combo',
											width : 100,
											store : avaFlgStore,
											id : 'avatarflg',
											hiddenName : 'avatarflg',
											valueField : 'ID',
											editable : false,
											displayField : 'NAME',
											mode : 'local',
											forceSelection : false,// 必须选择一项
											emptyText : '...',// 默认值
											triggerAction : 'all'
										}]
							}, {
								columnWidth : .3, // 该列有整行中所占百分比
								layout : "form", // 从上往下的布局
								items : [{
											fieldLabel : '是否图片库',
											xtype : 'combo',
											width : 100,
											store : libFlgStore,
											id : 'photolibflg',
											hiddenName : 'photolibflg',
											valueField : 'ID',
											editable : false,
											displayField : 'NAME',
											mode : 'local',
											forceSelection : false,// 必须选择一项
											emptyText : '...',// 默认值
											triggerAction : 'all'
										}]
							}]
				}],
				buttonAlign : "center",
				buttons : [{
					text : "查询",
					handler : function() { // 按钮响应函数
						var tform = photoQueryForm.getForm();
						var picType = tform.findField("picType").getValue();
						var avatarflg = tform.findField("avatarflg").getValue();
						var photolibflg = tform.findField("photolibflg")
								.getValue();
						var num = numtext.getValue();
						photoStore.setBaseParam('picType', Ext.isEmpty(picType)? null: picType);
						photoStore.setBaseParam('avatarflg', avatarflg);
						photoStore.setBaseParam('photolibflg', photolibflg);
						photoStore.setBaseParam('limit', Ext.isEmpty(num)? number: Number(num));
						photoStore.load();
					}
				}, {
					text : "重置",
					handler : function() { // 按钮响应函数
						photoQueryForm.form.reset();
					}
				}]
			});
	/**
	 * 用户信息列表
	 */
	// 定义表格数据源
	var photoStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../photolib/gridstore'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'gender'
								}, {
									name : 'picType'
								}, {
									name : 'picUrl'
								}, {
									name : 'createtime'
								}, {
									name : 'avatarflg'
								}, {
									name : 'photolibflg'
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
				header : '图片',
				dataIndex : 'picUrl',
				renderer : renderImage,
				width : 100
			}, {
				header : '类型',
				dataIndex : 'picType',
				// renderer : renderGender,
				locked : true,
				width : 80
			}, {
				header : '性别',
				dataIndex : 'gender',
				// renderer : renderGender,
				hidden : true,
				width : 80
			}, {
				header : '创建时间',
				dataIndex : 'createtime',
				renderer : renderDateHis,
				width : 120
			}, {
				header : '是否头像',
				dataIndex : 'avatarflg',
				width : 80
			}, {
				header : '是否图片库',
				dataIndex : 'photolibflg',
				width : 80
			}]);
	// // 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : photoStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext]
			});

	// 定义grid表格
	var photoGrid = new Ext.grid.GridPanel({
				region : 'center',
				id : 'photo',
				height : 500,
				stripeRows : true, // 斑马线
				frame : true,
				// autoWidth : true,
				autoScroll : true,
				store : photoStore,
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
								photoStore.reload();
							}
						}],
				onCellClick : function(grid, rowIndex, columnIndex, e) {
					var selesm = grid.getSelectionModel().getSelections();
					// var userid = selesm[0].data.id;
					var picUrl = selesm[0].data.picUrl;
					var picType = selesm[0].data.picType;
					var contentcol = grid.getColumnModel()
							.getDataIndex(columnIndex);
					if (contentcol == 'picUrl') {
						zoomWin.show();
						zoomWin.setTitle('图片预览-' + picType);
						Ext.get('image').dom.src = picUrl;
					}
				}
			});

	// 注册事件
	photoGrid.on('cellclick', photoGrid.onCellClick, photoGrid);

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [photoQueryForm, photoGrid]
			});
});
