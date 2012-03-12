package com.nali.center.type;

import java.beans.PropertyEditor;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.springframework.beans.propertyeditors.ByteArrayPropertyEditor;
import org.springframework.beans.propertyeditors.CharArrayPropertyEditor;
import org.springframework.beans.propertyeditors.CharacterEditor;
import org.springframework.beans.propertyeditors.CharsetEditor;
import org.springframework.beans.propertyeditors.ClassArrayEditor;
import org.springframework.beans.propertyeditors.ClassEditor;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomMapEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.FileEditor;
import org.springframework.beans.propertyeditors.InputSourceEditor;
import org.springframework.beans.propertyeditors.InputStreamEditor;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.beans.propertyeditors.PatternEditor;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.beans.propertyeditors.TimeZoneEditor;
import org.springframework.beans.propertyeditors.URIEditor;
import org.springframework.beans.propertyeditors.URLEditor;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceArrayPropertyEditor;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

public class DefaultPropertyEditorRegistry implements PropertyEditorRegistry{
	private ConcurrentHashMap<Class, PropertyEditor> editors;
	
	public DefaultPropertyEditorRegistry() {
		this.createDefaultEditors();
	}
	
	public void registerCustomEditor(Class requiredType, PropertyEditor propertyEditor) {
		this.editors.put(requiredType, propertyEditor);
	}
	
	public PropertyEditor findEditor(Class requiredType) {
		return this.editors.get(requiredType);
	}

	
	private void createDefaultEditors() {
		this.editors = new ConcurrentHashMap<Class, PropertyEditor>(64);

		// Simple editors, without parameterization capabilities.
		// The JDK does not contain a default editor for any of these target types.
		this.editors.put(Charset.class, new CharsetEditor());
		this.editors.put(Class.class, new ClassEditor());
		this.editors.put(Class[].class, new ClassArrayEditor());
		this.editors.put(Currency.class, new CurrencyEditor());
		this.editors.put(File.class, new FileEditor());
		this.editors.put(InputStream.class, new InputStreamEditor());
		this.editors.put(InputSource.class, new InputSourceEditor());
		this.editors.put(Locale.class, new LocaleEditor());
		this.editors.put(Pattern.class, new PatternEditor());
		this.editors.put(Properties.class, new PropertiesEditor());
		this.editors.put(Resource[].class, new ResourceArrayPropertyEditor());
		this.editors.put(TimeZone.class, new TimeZoneEditor());
		this.editors.put(URI.class, new URIEditor());
		this.editors.put(URL.class, new URLEditor());
		this.editors.put(UUID.class, new UUIDEditor());

		// Default instances of collection editors.
		// Can be overridden by registering custom instances of those as custom editors.
		this.editors.put(Collection.class, new CustomCollectionEditor(Collection.class));
		this.editors.put(Set.class, new CustomCollectionEditor(Set.class));
		this.editors.put(SortedSet.class, new CustomCollectionEditor(SortedSet.class));
		this.editors.put(List.class, new CustomCollectionEditor(List.class));
		this.editors.put(SortedMap.class, new CustomMapEditor(SortedMap.class));

		// Default editors for primitive arrays.
		this.editors.put(byte[].class, new ByteArrayPropertyEditor());
		this.editors.put(char[].class, new CharArrayPropertyEditor());

		// The JDK does not contain a default editor for char!
		this.editors.put(char.class, new CharacterEditor(false));
		this.editors.put(Character.class, new CharacterEditor(true));

		// Spring's CustomBooleanEditor accepts more flag values than the JDK's default editor.
		this.editors.put(boolean.class, new CustomBooleanEditor(false));
		this.editors.put(Boolean.class, new CustomBooleanEditor(true));

		// The JDK does not contain default editors for number wrapper types!
		// Override JDK primitive number editors with our own CustomNumberEditor.
		this.editors.put(byte.class, new CustomNumberEditor(Byte.class, false));
		this.editors.put(Byte.class, new CustomNumberEditor(Byte.class, true));
		this.editors.put(short.class, new CustomNumberEditor(Short.class, false));
		this.editors.put(Short.class, new CustomNumberEditor(Short.class, true));
		this.editors.put(int.class, new CustomNumberEditor(Integer.class, false));
		this.editors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
		this.editors.put(long.class, new CustomNumberEditor(Long.class, false));
		this.editors.put(Long.class, new CustomNumberEditor(Long.class, true));
		this.editors.put(float.class, new CustomNumberEditor(Float.class, false));
		this.editors.put(Float.class, new CustomNumberEditor(Float.class, true));
		this.editors.put(double.class, new CustomNumberEditor(Double.class, false));
		this.editors.put(Double.class, new CustomNumberEditor(Double.class, true));
		this.editors.put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
		this.editors.put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));

		// Only register config value editors if explicitly requested.
//		if (this.configValueEditorsActive) {
		StringArrayPropertyEditor sae = new StringArrayPropertyEditor();
		this.editors.put(String[].class, sae);
		this.editors.put(short[].class, sae);
		this.editors.put(int[].class, sae);
		this.editors.put(long[].class, sae);
//		}
	}

}
