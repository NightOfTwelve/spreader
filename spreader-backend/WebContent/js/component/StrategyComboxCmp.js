// 创建ComboBox数据源，支持Ajax取值
var stgCmbStore = new Ext.data.Store({
			// 代理模式
			proxy : new Ext.data.HttpProxy({
						url : '../strategydisp/combstore'
					}),
			// 读取模式
			reader : new Ext.data.JsonReader({}, [{
								name : 'name'
							}, {
								name : 'displayName'
							}])
		});
// 选择策略的COMB
var stgSelectCombo = new Ext.form.ComboBox({
			hiddenName : 'name',
			id : 'stgSelectCombo',
			fieldLabel : '策略',
			emptyText : '请选择策略...',
			triggerAction : 'all',
			store : stgCmbStore,
			displayField : 'displayName',
			valueField : 'name',
			loadingText : '正在加载数据...',
			mode : 'remote', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
			forceSelection : true,
			typeAhead : true,
			resizable : true,
			editable : false,
			anchor : '100%'
		});
// 选择事件，用于联动 TODO
// stgSelectCombo.on('select', function() {
// cityCombo.reset();
// countyCombo.reset();
// var value = provinceCombo.getValue();
// cityStore.load({
// params : {
// areacode : value
// }
// });
// });
// 嵌入的FORM
var stgCmbForm = new Ext.form.FormPanel({
			id : 'stgCmbForm',
			name : 'stgCmbForm',
			labelWidth : 50, // 标签宽度
			frame : true, // 是否渲染表单面板背景色
			defaultType : 'textfield', // 表单元素默认类型
			labelAlign : 'right', // 标签对齐方式
			bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
			items : [stgSelectCombo]
		});
// 弹出窗口
var stgCmbWindow = new Ext.Window({
			title : '<span class="commoncss">策略选择</span>', // 窗口标题
			id : 'stgCmbWindow',
			closeAction : 'hide',
			layout : 'fit', // 设置窗口布局模式
			width : 300, // 窗口宽度
			height : 150, // 窗口高度
			// closable : true, // 是否可关闭
			collapsible : true, // 是否可收缩
			maximizable : true, // 设置是否可以最大化
			border : false, // 边框线设置
			constrain : true, // 设置窗口是否可以溢出父容器
			pageY : 20, // 页面定位Y坐标
			pageX : document.documentElement.clientWidth / 2 - 300 / 2, // 页面定位X坐标
			items : [stgCmbForm], // 嵌入的表单面板
			buttons : [{ // 窗口底部按钮配置
				text : '确定', // 按钮文本
				iconCls : 'tbar_synchronizeIcon', // 按钮图标
				handler : function() { // 按钮响应函数
					// cleanCreateTrigger();
					strategyIdHidden.setValue(stgSelectCombo.getValue());
					dispNameHidden.setValue(stgSelectCombo.lastSelectionText);
					// editstgWindow.title = GDISNAME;
					editstgWindow.show();
					stgCmbWindow.hide();
				}
			}, {	// 窗口底部按钮配置
						text : '重置', // 按钮文本
						iconCls : 'tbar_synchronizeIcon', // 按钮图标
						handler : function() { // 按钮响应函数
							stgCmbForm.form.reset();
						}
					}, {
						text : '关闭',
						iconCls : 'deleteIcon',
						handler : function() {
							stgCmbWindow.hide();
						}
					}]
		});