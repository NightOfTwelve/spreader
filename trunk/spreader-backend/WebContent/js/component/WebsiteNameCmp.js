/**
 * 格式化website中文名
 */
var websiteNameStore = new Ext.data.Store({
			// 代理模式
			proxy : new Ext.data.HttpProxy({
						url : '../extutil/website'
					}),
			// 读取模式
			reader : new Ext.data.JsonReader({}, [{
								name : 'id'
							}, {
								name : 'name'
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
function rendWebsiteNameFn(value) {
	var list = websiteNameStore.reader.jsonData;
	for (var idx in list) {
		var tmp = list[idx].id;
		var wname = list[idx].name;
		if (value == tmp) {
			return wname;
		}
	}
}