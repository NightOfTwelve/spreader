package com.nali.center.type;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nali.center.properties.exception.ValueResolveException;
import com.nali.common.util.StringUtil;

public class PropertyEditoerValueResolver implements PropertyValueResolver,
		InitializingBean {
	@Autowired
	private PropertyEditorRegistry registry;
	
	@Autowired
	private TypeResolver typeResolver;

	private final Map<Class, Class<? extends PropertyEditor>> customEditors = new HashMap<Class, Class<? extends PropertyEditor>>(
			4);

	public Map<Class, Class<? extends PropertyEditor>> getCustomEditors() {
		return customEditors;
	}

	@Override
	public String toText(Object value) {
		if (null == value) {
			return null;
		}

		PropertyEditor editor = this.registry.findEditor(value.getClass());
		if (editor != null) {
			editor.setValue(value);
			return editor.getAsText();
		}
		return value.toString();
	}

	@Override
	public Object toValue(String type, String text)
			throws ValueResolveException {
		if (StringUtil.isEmpty(type)) {
			return text;
		}

		try {
			Class requiredType = this.typeResolver.resolve(type);
			PropertyEditor editor = this.registry.findEditor(requiredType);
			if (editor != null) {
				editor.setAsText(text);
				return editor.getValue();
			}
			return text;
		} catch (ClassNotFoundException e) {
			throw new ValueResolveException(e.getMessage(), e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!this.customEditors.isEmpty()) {
			for (Map.Entry<Class, Class<? extends PropertyEditor>> entry : this.customEditors
					.entrySet()) {
				Class requiredType = entry.getKey();
				Class<? extends PropertyEditor> editorClass = entry.getValue();
				registry.registerCustomEditor(requiredType, BeanUtils
						.instantiateClass(editorClass));
			}
		}
	}
}
