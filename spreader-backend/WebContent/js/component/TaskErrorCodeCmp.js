/**
 * 格式化errorcode
 */
var errorCodeStore = new Ext.data.Store({
			// 代理模式
			proxy : new Ext.data.HttpProxy({
						url : '/spreader-front/taskexecue/errorcode'
					}),
			// 读取模式
			reader : new Ext.data.JsonReader({}, [{
								name : 'code'
							}, {
								name : 'msg'
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
function rendErrorCodeMsgFn(value) {
	var list = errorCodeStore.reader.jsonData;
	for (var idx in list) {
		var msg = list[idx];
		if (value == idx) {
			return renderBrief(msg);
		}
	}
}