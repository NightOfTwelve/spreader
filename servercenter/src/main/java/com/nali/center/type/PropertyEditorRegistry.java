package com.nali.center.type;

import java.beans.PropertyEditor;

public interface PropertyEditorRegistry {
	void registerCustomEditor(Class requiredType, PropertyEditor propertyEditor);
	PropertyEditor findEditor(Class requiredType);
	
}
