// 页数
var fansnumber = 20;
var fansnumtext = new Ext.form.TextField({
			id : 'fansmaxpage',
			name : 'fansmaxpage',
			width : 60,
			emptyText : '每页条数',
			// 激活键盘事件
			enableKeyEvents : true,
			listeners : {
				specialKey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							fansbbar.pageSize = Number(fansnumtext.getValue());
							fansnumber = Number(fansnumtext.getValue());
							userFansStore.reload({
										params : {
											start : 0,
											limit : fansbbar.pageSize
										}
									});
						}
					}
				}
			}
		});
/**
 * 用户粉丝信息列表
 */
// 定义粉丝信息表格数据源
var userFansStore = new Ext.data.Store({
			autoLoad : false,
			proxy : new Ext.data.HttpProxy({
						url : '../userinfo/realfansinfo'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'totalCount',
						root : 'list'
					}, [{
								name : 'id'
							}, {
								name : 'isRobot'
							}, {
								name : 'nickName'
							}, {
								name : 'gender'
							}, {
								name : 'realName'
							}, {
								name : 'fans'
							}, {
								name : 'robotFans'
							}, {
								name : 'articles'
							}, {
								name : 'email'
							}, {
								name : 'region'
							}, {
								name : 'spaceEntry'
							}, {
								name : 'introduction'
							}, {
								name : 'qq'
							}, {
								name : 'msn'
							}, {
								name : 'blog'
							}, {
								name : 'tag'
							}])
		});
// 分页带上查询条件
userFansStore.on('beforeload', function() {
			var limit = fansnumtext.getValue();
			this.baseParams = {
				id : GFANSID,
				limit : Ext.isEmpty(limit) ? number : Number(limit)
			};
		});
// 定义Checkbox
// var fansm = new Ext.grid.CheckboxSelectionModel();
// 定义表格列CM
var fanscm = new Ext.ux.grid.LockingColumnModel([{
			header : '编号',
			dataIndex : 'id',
			locked : true,
			width : 80
		}, {
			header : '机器人',
			dataIndex : 'isRobot',
			renderer : rendIsRobot,
			locked : true,
			width : 80
		}, {
			header : '昵称',
			dataIndex : 'nickName',
			locked : true,
			width : 100
		}, {
			header : '性别',
			dataIndex : 'gender',
			renderer : renderGender,
			width : 80
		}, {
			header : '真实姓名',
			dataIndex : 'realName',
			width : 100
		}, {
			header : '分类',
			dataIndex : 'tag',
			renderer : renderBrief,
			width : 100
		}, {
			header : '粉丝数',
			dataIndex : 'fans',
			width : 100
		}, {
			header : '机器人粉丝数',
			dataIndex : 'robotFans',
			width : 100
		}, {
			header : '文章数',
			dataIndex : 'articles',
			width : 100
		}, {
			header : 'email',
			dataIndex : 'email',
			width : 100
		}, {
			header : '地区',
			dataIndex : 'region',
			width : 100
		}, {
			header : '个人主页',
			dataIndex : 'spaceEntry',
			width : 100
		}, {
			header : '自我介绍',
			dataIndex : 'introduction',
			renderer : renderBrief,
			width : 100
		}, {
			header : 'QQ',
			dataIndex : 'qq',
			width : 100
		}, {
			header : 'MSN',
			dataIndex : 'msn',
			width : 100
		}, {
			header : '博客',
			dataIndex : 'blog',
			width : 100
		}]);

// // 分页菜单
var fansbbar = new Ext.PagingToolbar({
			pageSize : fansnumber,
			store : userFansStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', fansnumtext]
		});

// 定义grid表格
var sinaUserFansGrid = new Ext.grid.GridPanel({
			// region : 'center',
			id : 'sinaUserFansGrid',
			// autoHeight:true,
			height : 500,
			stripeRows : true, // 斑马线
			frame : true,
			// autoWidth : true,
			autoScroll : true,
			view : new Ext.ux.grid.LockingGridView(), // 锁定列视图
			store : userFansStore,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			bbar : fansbbar,
			cm : fanscm
		});
