package com.nali.spreader.util.math;

public class MathUtil {
	public static LongComputable wrap(long number) {
		return new LongComputable(number);
	}
	public static IntegerComputable wrap(int number) {
		return new IntegerComputable(number);
	}
	public static ByteComputable wrap(byte number) {
		return new ByteComputable(number);
	}
	public static ShortComputable wrap(short number) {
		return new ShortComputable(number);
	}
	public static FloatComputable wrap(float number) {
		return new FloatComputable(number);
	}
	public static DoubleComputable wrap(double number) {
		return new DoubleComputable(number);
	}
}
