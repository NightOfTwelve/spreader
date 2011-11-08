/**
 * 将树对象转换成String
 * 
 * @param {}
 *            rootobj
 */
function treejson2str(rootobj) {
	if (rootobj != null) {
		var str = Ext.util.JSON.encode(createNodeData(rootobj))
		alert(str);
	} else {
		return null;
	}
}

function createNodeData(node) {
	if (node != null) {
		if (node.isObject) {
			var data = {};
			var nodedata = node.data;
			for (var key in nodedata) {
				data[key] = nodedata[key];
			}
			var childarray = node.children;
			if (childarray != null) {
				for (var i = 0; i < childarray.length; i++) {
					var childobj = childarray[i];
					data[splitDataId(childobj.id)] = createNodeData(childobj);
				}
			}
			return data;
		} else if (node.isCollection) {
			var collarray = node.children;
			var tmparray = [];
			for (var i = 0; i < collarray.length; i++) {
				var collobj = collarray[i];
				tmparray.push(createNodeData(collobj));
			}
			return tmparray;
		} else if (node.isPrimitive) {
			var dt = node.data;
			var datavalue = dt["data"];
			return datavalue;
		}
	}
}

function splitDataId(id) {
	if (id != null) {
		var str = id.split(['.']);
		return str[str.length - 1];
	}
}
