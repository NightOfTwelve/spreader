package com.nali.spreader.util.math;

public interface Computable {
	Computable add(Computable c);
	Computable decrease(Computable c);
	Computable multiply(Computable c);
	Computable divide(Computable c);
	int intValue();
	long longValue();
	float floatValue();
	double doubleValue();
	byte byteValue();
	short shortValue();
}
