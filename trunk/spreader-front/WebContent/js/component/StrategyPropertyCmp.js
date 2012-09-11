// 可编辑的属性GRID
var pptGrid = new Ext.grid.PropertyGrid({
			id : 'pptGrid',
			title : '相关属性',
			autoHeight : true,
			width : 300,
			tools : [{ // 生成面板右上角的工具栏
				id : "save",
				handler : function() {
					//TODO
					Ext.Msg.alert('提示', '保存成功');
				} // 点击工具栏调用些方法
			}, {
				id : "help",
				handler : function() {
					//TODO
					Ext.Msg.alert('帮助', 'please help me!');
				}
			}, {
				id : "close",
				handler : function() {
					//TODO
					Ext.getCmp('pptGrid').disabled;
				}
			}, {
				id : "refresh",
				handler : function() {
					//TODO
					Ext.Msg.alert('提示', '刷新');
				}
			}]
		});