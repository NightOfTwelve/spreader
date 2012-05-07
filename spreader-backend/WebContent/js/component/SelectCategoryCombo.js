/**
 * 筛选用户
 */
var addUserStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : '../extutil/categorycombo?_time='+new Date().getTime()
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'totalCount',
						root : 'list'
					}, [{
								name : 'id'
							}, {
								name : 'name'
							}])
		});
// 用户筛选模版
var resultTpl = new Ext.XTemplate('<tpl for="."><div class="x-combo-list-item"><span>{id}({name})</span></div></tpl>');
// 用户筛选的Combo
var selectCategoryComboUtil = new Ext.form.ComboBox({
			hiddenName : 'keywordId',
			id : 'selectCategoryComboUtil',
			fieldLabel : '选择分类',
			emptyText : '选择分类...',
			triggerAction : 'all',
			store : addUserStore,
			displayField : 'name',
			valueField : 'id',
			loadingText : '正在加载数据...',
			mode : 'remote', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
			forceSelection : true,
			typeAhead : true,
			resizable : true,
			minChars : 1,
			pageSize : 10,
			itemSelector : 'div.x-combo-list-item',
			tpl : resultTpl,
			editable : true,
//			enableKeyEvents:true,
			anchor : '100%'
		});