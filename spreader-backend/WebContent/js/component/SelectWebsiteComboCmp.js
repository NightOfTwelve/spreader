/**
 * 创建网站分类下来菜单组件的通用函数
 * 
 * @param {}
 *            fieldId
 * @param {}
 *            fieldName
 * @param {}
 *            fieldLabel
 * @param {}
 *            emptyText
 */
function webSiteComboUtil(fieldId, fieldName, fieldLabel, emptyText) {
	var websiteStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../extutil/website'
						}),
				reader : new Ext.data.JsonReader({}, [{
									name : 'id'
								}, {
									name : 'name'
								}])
			});
	// 网站分类的Combo
	var selectWebSiteComboUtil = new Ext.form.ComboBox({
				hiddenName : 'websiteId',
				id : Ext.isEmpty(fieldId) ? 'selectWebSiteComboUtil' : fieldId,
				name : Ext.isEmpty(fieldName)
						? 'selectWebSiteComboUtil'
						: fieldName,
				fieldLabel : Ext.isEmpty(fieldLabel) ? '网站分类' : fieldLabel,
				emptyText : Ext.isEmpty(emptyText) ? '网站分类...' : emptyText,
				triggerAction : 'all',
				store : websiteStore,
				displayField : 'name',
				valueField : 'id',
				loadingText : '正在加载数据...',
				mode : 'remote', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
				forceSelection : true,
				typeAhead : true,
				resizable : true,
				editable : false,
				anchor : '100%'
			});
	return selectWebSiteComboUtil;
}