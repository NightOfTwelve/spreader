/**
 * propertyGrid的相关操作函数
 */
// ///////////////////////
/**
 * 构造对应类型的编辑框，用于propertyGrid
 * 
 * @param {}
 *            stype
 * @return {}
 */
function getTypeDefaultEdit(stype) {
	var defValue = new Ext.grid.GridEditor(new Ext.form.TextField());
	if (!Ext.isEmpty(stype)) {
		if (stype == 'Integer') {
			defValue = new Ext.grid.GridEditor(new Ext.form.NumberField());
		} else if (stype == 'Float') {
			defValue = new Ext.grid.GridEditor(new Ext.form.NumberField());
		} else if (stype == 'Date') {
			// new Ext.ux.form.DateTimeField() calendarCmp('start', 'start',
			// '开始时间')
			defValue = new Ext.grid.GridEditor(new Ext.ux.form.DateTimeField());
		}
	}
	return defValue;
}
/**
 * 绑定PropertyGrid数据源的相关函数
 * 
 * @param {}
 *            node 当前的节点对象
 */
function renderPropertyGrid(node) {
	var nText = node.text;
	// 获取数据
	var data = node.attributes.data;
	// 获取数据对应的表结构
	var def = node.attributes.def;
	// 获取布局组件
	var pptgridcmp = Ext.getCmp('pptgridmanage');
	// 创建一个PropertyGrid
	var pptgrid = new Ext.grid.PropertyGrid({
				id : 'pptGrid',
				// 隐藏横向滚动条
				bodyStyle : 'overflow-x:visible;overflow-y:scroll',
				autoScroll : true,
				tbar : [{
							id : 'commNodePropText',
							text : ''
						}]
			})
	// 得到构造好的propertyNames对象
	var pptnameobj = createPptGridStoreDef(def);
	// 设置propertyNames
	pptgrid.propertyNames = pptnameobj;
	// 构造数据源对象
	var newdata = createPptGridStoreData(data, def);
	// 构造编辑属性对象
	var custEdit = createPptGridCustEdit(data, def);
	// 设定自定义渲染列
	var custRenderers = createCustRenderers(data, def);
	// 设置数据源
	pptgrid.source = newdata;
	// 设置自定义编辑列属性
	pptgrid.customEditors = custEdit;
	// 设置布尔型的渲染列
	pptgrid.customRenderers = custRenderers;
	// 删除添加的组件，防止重复出现
	pptgridcmp.removeAll(pptgrid);
	// 绑定到布局页面
	pptgridcmp.add(pptgrid);
	Ext.getCmp('commNodePropText').setText(renderTextColor('属性-->' + nText,
			'mediumorchid'));
	pptgridcmp.doLayout();
}
/**
 * 如果是collection节点则需创建子节点的展示菜单 TODO
 * 
 * @param {}
 *            node
 */
function collectionRender(node) {
	var nText = node.text;
	// 获取数据
	var data = node.attributes.data;
	// 获取数据对应的表结构
	var def = node.attributes.def;
	// 获取布局组件
	var pptgridcmp = Ext.getCmp('pptgridmanage');
	// 创建一个PropertyGrid
	var pptgrid = new Ext.Panel({
				id : 'collPanle',
				layout : 'form',
				// 隐藏横向滚动条
				bodyStyle : 'overflow-x:visible;overflow-y:scroll',
				tbar : [{
							id : 'collNodeText',
							text : ''
						}, '-', {
							text : renderTextColor('操作-->新增节点', 'lightseagreen'),
							// iconCls : 'addIcon',
							handler : function() {
								appendNodeAction(node);
							}
						}]
			});
	// 删除添加的组件，防止重复出现
	pptgridcmp.removeAll(pptgrid);
	// 绑定到布局页面
	pptgridcmp.add(pptgrid);
	Ext.getCmp('collNodeText').setText(renderTextColor('属性-->' + nText,
			'mediumorchid'));
	pptgridcmp.doLayout();
}
/**
 * 构造propertyNames对象
 * 
 * @param {}
 *            def 结构
 * @return {}
 */
function createPptGridStoreDef(def) {
	// 创建propertyNames的对象
	var pptNameObj = {};
	for (var i = 0; i < def.length; i++) {
		var relname = def[i].propertyName;
		pptNameObj[relname] = def[i].name;
	}
	return pptNameObj;
}
/**
 * 重新构造一遍data，因为有可能data是null，导致不能与propertyNames匹配不能显示
 * 
 * @param {}
 *            data
 */
function createPptGridStoreData(data, def) {
	if (data != null && def != null) {
		for (var i = 0; i < def.length; i++) {
			var defObj = def[i];
			var defname = defObj.propertyName;
			var pType = defObj.propertyDefinition.type;
			if (Ext.isEmpty(data[defname])) {
				if (pType == 'Boolean') {
					data[defname] = NO_SELECT;
				} else {
					data[defname] = '';
				}
			}
		}
		return data;
	} else {
		Ext.MessageBox.alert("提示", "对象获取错误");
		return;
	}
}

/**
 * 创建用户定义的输入框类型
 * 
 * @param {}
 *            data
 * @param {}
 *            def
 * @return {}
 */
function createPptGridCustEdit(data, def) {
	var custEdit = {};
	if (data != null && def != null) {
		for (var i = 0; i < def.length; i++) {
			var defObj = def[i];
			var defname = defObj.propertyName;
			var pType = defObj.propertyDefinition.type;
			if (pType == 'Boolean') {
				custEdit[defname] = new Ext.grid.GridEditor(createBooleanFiled(defname));
			}
			// if (!data.hasOwnProperty(defname)) {
			if (Ext.isEmpty(data[defname])) {
				custEdit[defname] = getTypeDefaultEdit(pType);
			}
		}
		return custEdit;
	} else {
		Ext.MessageBox.alert("提示", "对象获取错误");
		return;
	}
}
/**
 * 创建boolean型的输入框
 * 
 * @param {}
 *            name
 */
function createBooleanFiled(filedName) {
	var combo = new Ext.form.ComboBox({
				name : filedName,
				id : filedName,
				fieldLabel : 'condition',
				mode : 'local',
				valueField : 'id',
				displayField : 'name',
				allowBlank : false,
				minListWidth : 220,
				store : new Ext.data.ArrayStore({
							id : 'isCons',
							fields : ['id', 'name'],
							data : [[true, "是"], [false, "否"], [NO_SELECT, "不限"]]
						}),
				listeners : {
					expand : function() {
						/* 下拉框展开时重置，否则不能重新加载列表 */
						Ext.getCmp(filedName).reset();
					}
				}
			});
	return combo;
}
/**
 * 渲染布尔型的列
 * 
 * @param {}
 *            data
 * @param {}
 *            def
 * @return {}
 */
function createCustRenderers(data, def) {
	var customRenderers = {};
	if (data != null && def != null) {
		for (var i = 0; i < def.length; i++) {
			var defObj = def[i];
			var defname = defObj.propertyName;
			var pType = defObj.propertyDefinition.type;
			// if (!data.hasOwnProperty(defname)) {
			if (pType == 'Boolean') {
				customRenderers[defname] = function(v) {
					if (Ext.isEmpty(v)) {
						return '不限';
					}
					if (v == NO_SELECT) {
						return '不限';
					}
					return v ? '是' : '否';
				}
			}
		}
		return customRenderers;
	} else {
		Ext.MessageBox.alert("提示", "对象获取错误");
		return;
	}
}