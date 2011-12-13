Ext.ux.form.HtmlEditorWithPlugins = Ext.extend(Ext.form.HtmlEditor, {
			initComponent : function() {
				this.plugins = [new Ext.ux.form.HtmlEditor.Link(),
						new Ext.ux.form.HtmlEditor.Divider(),
						new Ext.ux.form.HtmlEditor.Word(),
						new Ext.ux.form.HtmlEditor.FindAndReplace(),
						new Ext.ux.form.HtmlEditor.UndoRedo(),
						new Ext.ux.form.HtmlEditor.Divider(),
						new Ext.ux.form.HtmlEditor.Image(),
						new Ext.ux.form.HtmlEditor.Table(),
						new Ext.ux.form.HtmlEditor.HR(),
						new Ext.ux.form.HtmlEditor.SpecialCharacters(),
						new Ext.ux.form.HtmlEditor.IndentOutdent(),
						new Ext.ux.form.HtmlEditor.SubSuperScript(),
						new Ext.ux.form.HtmlEditor.RemoveFormat()];
				Ext.ux.form.HtmlEditorWithPlugins.superclass.initComponent
						.call(this);
			}
		});
Ext.reg('HtmlEditorPlugs', Ext.ux.form.HtmlEditorWithPlugins);
