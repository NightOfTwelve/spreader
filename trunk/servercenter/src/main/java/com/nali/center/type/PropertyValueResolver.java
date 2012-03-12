package com.nali.center.type;

import com.nali.center.properties.exception.ValueResolveException;

public interface PropertyValueResolver {
    Object toValue(String type, String text) throws ValueResolveException;
    
    String toText(Object value);
} 
