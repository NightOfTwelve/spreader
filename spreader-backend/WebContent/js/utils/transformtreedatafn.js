/**
 * 构造树形结构的函数 相关逻辑： 通过def的propertyName属性判断是否是基础类型
 * 1.筛选基础类型构造新的DEF,DATA，用于propertyGrid展示 2.筛选复杂类型用作NODE而不作为LEAF，放在children里
 * 
 * @param {}
 *            def 对象的结构
 * @param {}
 *            data 对象保存的真实数据
 */
function tranNodeConfig(id, name, def, data) {
	if (isPrimitive(def.type)) {
		return {
			id : id,
			text : name,
			isPrimitive : true,
			def : [{
						propertyName : 'data',
						name : '值',
						type : def.type
					}],
			data : {
				data : data
			},
			leaf : true
		};
	} else if (def.type == "Object") {
		return transformdata(id, name, def, data);
	} else if (def.type == "Collection") {
		// 获取集合对象下的ITEM数组
		var itemDef = def.itemDefinition;
		var itemCount = 0;
		var getChildConfig = function(data) {
			var idx = itemCount++;
			return tranNodeConfig(id + "[" + idx + "]", idx + "", itemDef, data);
		}
		var collectionChildren = [];
		// 循环dataArray
		var dataArray = data || [];
		for (var i = 0; i < dataArray.length; i++) {
			var childConfig = getChildConfig(dataArray[i]);
			collectionChildren.push(childConfig);
		}
		return {
			id : id,
			text : name,
			getChildConfig : getChildConfig,
			children : collectionChildren,
			isCollection : true
		};
	} else if (def.type == "Map") {
		// TODO
		return null;
	} else {
		throw "error type:" + def.type;
	}
}
function transformdata(id, name, def, data) {
	// 组装树结构的OBJ
	var treeObj = new Object();
	if (def != null) {
		treeObj['id'] = id;
		treeObj['text'] = name;
		// 创建基本属性的数组
		var basedefarray = new Array();
		// 创建复合属性的数组
		var conarray = new Array();
		// 创建基本属性的对象
		var basedataobj = new Object();
		// 创建复合属性的数组
		var condataarray = new Array();
		// 获取结构数组
		var properties = def.properties;
		// 遍历数组
		for (var i = 0; i < properties.length; i++) {
			// 获取当前数组内容
			var pptitem = properties[i];
			// 获取对象节点的属性类型
			var typeid = pptitem.propertyDefinition.type;
			// 通过数据类型判断是否是叶子节点
			var isleaf = isPrimitive(typeid);
			if (isleaf) {
				var tmppptname = pptitem.propertyName;
				if (data != null) {
					basedataobj[tmppptname] = data[tmppptname];
				}
				basedefarray.push(pptitem);
			} else {
				// 对象的实际ID
				var childId = id + '.' + pptitem.propertyName;
				// ID的名称
				var childName = pptitem.name;
				var childDef = pptitem.propertyDefinition;
				var childData = null;
				if (data != null) {
					childData = data[pptitem.propertyName];
				}
				conarray.push(tranNodeConfig(childId, childName, childDef,
						childData));
			}
		}
		treeObj['def'] = basedefarray;
		treeObj['data'] = basedataobj;
		treeObj['children'] = conarray;
		treeObj['leaf'] = conarray == null || conarray.length == 0;
		treeObj['isObject'] = true;
	} else {
		return;
	}
	return treeObj;
}
/**
 * 筛选基础类型的FN
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