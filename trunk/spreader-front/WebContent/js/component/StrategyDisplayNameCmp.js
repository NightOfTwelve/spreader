/**
 * 格式化策略中文名称组件
 */
var dispNameStore = new Ext.data.Store({
			// 代理模式
			proxy : new Ext.data.HttpProxy({
						url : '../strategydisp/alldisplayname'
					}),
			// 读取模式
			reader : new Ext.data.JsonReader({}, [{
								name : 'name'
							}, {
								name : 'displayName'
							}]),
			autoLoad : true
		});
/**
 * 渲染函数
 * 
 * @param {}
 *            value
 * @return {}
 */
function rendDispNameFn(value) {
	var list = dispNameStore.reader.jsonData;
	for (var idx in list) {
		var tmp = list[idx].name;
		var dname = list[idx].displayName;
		if (value == tmp) {
			return dname;
		}
	}
}