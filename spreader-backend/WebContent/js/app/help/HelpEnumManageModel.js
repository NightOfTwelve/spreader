Ext.onReady(function() {
	var HelpEnumInfo = Ext.data.Record.create([{
				name : 'id',
				type : 'number'
			}, {
				name : 'enumName',
				type : 'string'
			}, {
				name : 'enumValues',
				type : 'string'
			}, {
				name : 'sortId',
				type : 'number'
			}]);
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../help/enumstore?_time='
									+ new Date().getTime()
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'id'
								}, {
									name : 'enumName'
								}, {
									name : 'enumValues'
								}, {
									name : 'sortId'
								}]),
				autoLoad : true
			});
	var editor = new Ext.ux.grid.RowEditor({
				saveText : '更新',
				cancelText : '取消'
			});

	editor.on({
				scope : this,
				afteredit : function(roweditor, changes, record, rowIndex) {
					Ext.Ajax.request({
								url : '../help/updateenum',
								method : 'post',
								params : {
									sortId : Ext.isEmpty(changes['sortId'])
											? record.data['sortId']
											: changes['sortId'],
									enumValues : Ext
											.isEmpty(changes['enumValues'])
											? record.data['enumValues']
											: changes['enumValues'],
									enumName : Ext.isEmpty(changes['enumName'])
											? record.data['enumName']
											: changes['enumName'],
									id : record.data['id']
								},
								success : function() {
									record.commit();
									store.load();
								}
							});
				}
			});
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
							store.reload({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
						}
					}
				}
			});
	// // 分页菜单
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				plugins : new Ext.ux.ProgressBarPager(),
				items : ['-', '&nbsp;&nbsp;', numtext]
			});
	var grid = new Ext.grid.GridPanel({
				store : store,
				// width : 600,
				margins : '0 5 5 5',
				plugins : [editor],
				bbar : bbar,
				tbar : [{
							iconCls : 'comments_addIcon',
							text : '添加',
							handler : function() {
								var e = new HelpEnumInfo({
											id : null,
											enumName : '',
											enumValues : '',
											sortId : 1
										});
								editor.stopEditing();
								store.insert(0, e);
								grid.getView().refresh();
								grid.getSelectionModel().selectRow(0);
								editor.startEditing(0);
							}
						}, '-', {
							ref : '../removeBtn',
							iconCls : 'deleteIcon',
							text : '删除',
							disabled : true,
							handler : function() {
								editor.stopEditing();
								var s = grid.getSelectionModel()
										.getSelections();
								for (var i = 0, r; r = s[i]; i++) {
									store.remove(r);
								}
							}
						}, '-', {
							iconCls : 'page_refreshIcon',
							text : '刷新',
							handler : function() {
								store.load();
							}
						}, '-', new Ext.form.TextField({
									id : 'queryName',
									name : 'queryName',
									emptyText : '请输入分类名称',
									enableKeyEvents : true,
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												var text = field.getValue();
												store.setBaseParam('enumName',
														text);
												store.load();
											}
										}
									},
									width : 130
								}), '-', {
							text : '查询',
							iconCls : 'page_refreshIcon',
							handler : function() {
								var text = Ext.getCmp('queryName').getValue();
								store.setBaseParam('enumName', text);
								store.load();
							}
						}],

				columns : [new Ext.grid.RowNumberer(), {
							id : 'id',
							header : '编号',
							dataIndex : 'id',
							width : 80,
							sortable : false
						}, {
							header : '信息分类',
							dataIndex : 'enumName',
							width : 100,
							sortable : false,
							editor : {
								xtype : 'textfield',
								allowBlank : false
							}
						}, {
							header : '分类说明',
							dataIndex : 'enumValues',
							width : 180,
							sortable : false,
							editor : {
								xtype : 'textfield',
								allowBlank : false
							}
						}, {
							header : '排序号',
							dataIndex : 'sortId',
							width : 80,
							sortable : true,
							editor : {
								xtype : 'numberfield',
								allowBlank : true
							}
						}]
			});
	grid.getSelectionModel().on('selectionchange', function(sm) {
				grid.removeBtn.setDisabled(sm.getCount() < 1);
			});
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [grid]
			});
});