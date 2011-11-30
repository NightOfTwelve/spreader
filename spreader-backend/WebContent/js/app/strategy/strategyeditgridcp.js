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
		})
// pptGrid.setSource(vstore);
// pptGrid.on('beforepropertychange', function(source, recordId, value,
// oldValue) {
// var record = this.getStore().getById(recordId);
// // 把新值映射成文本
// if (recordId=='age') {
// recordId = "年龄";
// }
// // 令PropertyGrid暂停触发事件，以防止事件循环
// this.suspendEvents();
//
// record.set("name", recordId);// 写入映射后的值
// record.commit();// 执行修改
//
// // 恢复事件
// this.resumeEvents();
//
// // 阻止Property的自动更新
// return false;
// })
