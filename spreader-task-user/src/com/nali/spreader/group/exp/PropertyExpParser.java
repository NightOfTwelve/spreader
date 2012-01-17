package com.nali.spreader.group.exp;


public interface PropertyExpParser {
	Object parseQuery(PropertyExpression expression);  
	
	int parsePropVal(PropertyExpressionDTO expression);
}
