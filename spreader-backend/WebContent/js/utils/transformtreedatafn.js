/**
 * 构造树形结构的函数
 * 
 * @param {}
 *            def 对象的结构
 * @param {}
 *            data 对象保存的真实数据
 */
function transformdata(id, name, def, data) {
	// 组装树结构的OBJ
	var treeObj = new Object();
	if (def != null && data != null) {
		treeObj['id'] = id;
		treeObj['text'] = name;
		// 创建基本属性的数组
		var basearray = new Array();
		// 创建复合属性的数组
		var conarray = new Array();
		// 创建基本属性的数组
		var basedataarray = new Array();
		// 创建复合属性的数组
		var condataarray = new Array();
		// 获取结构数组
		var properties = def.properties;
		// 遍历数组
		for (var i = 0; i < properties.length; i++) {
			// 通过数据类型判断是否是叶子节点
			var isleaf = isPrimitive(properties[i].propertyDefinition.type);
			if (isleaf) {
				basedataarray.push(data[properties[i].propertyName]);
				basearray.push(properties[i]);
			} else {
				// 对象的实际ID
				var treeid = properties[i].propertyName;
				// ID的名称
				var treetext = properties[i].name;
				var chobj = new Object();
				chobj['id'] = treeid;
				chobj['text'] = treetext;
				chobj[properties[i].name]=data[properties[i].name];
//				condataarray.push(data[properties[i].name]);
				conarray.push(properties[i]);
			}
		}
		treeObj['def'] = basearray;
		treeObj['data'] = basedataarray;
		treeObj['children'] = conarray;
		treeObj['children'] = condataarray;
	} else {
		return;
	}
	return treeObj;
}
/**
 * 
 * @param {}
 *            type
 * @return {Boolean}
 */
function isPrimitive(type) {
	var primitives = ['String', 'Integer', 'Float', 'Boolean', 'Character'];
	for (var i = 0; i < primitives.length; i++) {
		if (primitives[i] == type) {
			return true;
		}
	}
	return false;
}