/**
 * 用户分组组件
 * 
 * @param {}
 *            fieldId
 * @param {}
 *            fieldName
 * @param {}
 *            hiddenName
 */
function getUserGroupCmp(fieldId, fieldName, hiddenName) {
	var selectUserGroupStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '../extutil/usergroupcombo'
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'totalCount',
							root : 'list'
						}, [{
									name : 'gid'
								}, {
									name : 'gname'
								}, {
									name : 'websiteId'
								}])
			});
	selectUserGroupStore.load();
	// 用户分组筛选模版
	var selectUserGroupTpl = new Ext.XTemplate('<tpl for="."><div class="x-combo-list-item"><span>{gid}({gname})</span></div></tpl>');
	var selectUserGroupCombo = new Ext.form.ComboBox({
				hiddenName : Ext.isEmpty(hiddenName)
						? 'userGroupid'
						: hiddenName,
				id : Ext.isEmpty(fieldId) ? 'userGroupFieldId' : fieldId,
				name : Ext.isEmpty(fieldName)
						? 'userGroupFieldName'
						: fieldName,
				fieldLabel : '选择分组',
				emptyText : '请选择分组...',
				triggerAction : 'all',
				store : selectUserGroupStore,
				displayField : 'gname',
				valueField : 'gid',
				loadingText : '正在加载数据...',
				mode : 'local', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
				forceSelection : true,
				typeAhead : true,
				resizable : true,
				minChars : 1,
				pageSize : 10,
				itemSelector : 'div.x-combo-list-item',
				tpl : selectUserGroupTpl,
				editable : true,
				// enableKeyEvents:true,
				anchor : '100%'
			});
	return selectUserGroupCombo;
}