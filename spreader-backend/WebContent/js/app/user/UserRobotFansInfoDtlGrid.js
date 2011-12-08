// 页数
var robotfansnumber = 20;
var robotfansnumtext = new Ext.form.TextField({
			id : 'robotfansmaxpage',
			name : 'robotfansmaxpage',
			width : 60,
			emptyText : '每页条数',
			// 激活键盘事件
			enableKeyEvents : true,
			listeners : {
				specialKey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
						if (e.getKey() == Ext.EventObject.ENTER) {// 响应回车
							robotfansbbar.pageSize = Number(robotfansnumtext.getValue());
							robotfansnumber = Number(robotfansnumtext.getValue());
							userFansStore.reload({
										params : {
											start : 0,
											limit : robotfansbbar.pageSize
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
var userRobotFansStore = new Ext.data.Store({
			autoLoad : false,
			proxy : new Ext.data.HttpProxy({
						url : '../userinfo/robotfansinfo'
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
userRobotFansStore.on('beforeload', function() {
			var limit = robotfansnumtext.getValue();
			this.baseParams = {
				id:GROBOTID,
				limit : Ext.isEmpty(limit) ? number : Number(limit)
			};
		});
// 定义Checkbox
// var fansm = new Ext.grid.CheckboxSelectionModel();
// 定义表格列CM
var robotfanscm = new Ext.ux.grid.LockingColumnModel([{
			header : '编号',
			dataIndex : 'id',
			locked : true,
			width : 80
		}, {
			header : '机器人',
			dataIndex : 'isRobot',
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
var robotfansbbar = new Ext.PagingToolbar({
			pageSize : robotfansnumber,
			store : userRobotFansStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', robotfansnumtext]
		});

// 定义grid表格
var sinaUserRobotFansGrid = new Ext.grid.GridPanel({
			// region : 'center',
			id : 'sinaUserRobotFansGrid',
			// autoHeight:true,
			height : 500,
			stripeRows : true, // 斑马线
			frame : true,
			// autoWidth : true,
			autoScroll : true,
			view : new Ext.ux.grid.LockingGridView(), // 锁定列视图
			store : userRobotFansStore,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			bbar : robotfansbbar,
			cm : robotfanscm
		});
