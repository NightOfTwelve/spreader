package com.nali.spreader.util.reflect;

import com.nali.spreader.util.math.DoubleComputable;
import com.nali.spreader.util.math.LongComputable;
import com.nali.spreader.util.math.MathUtil;


public class TestComputable {
	public static void main(String[] args) {
		LongComputable l = MathUtil.wrap(100L);
		DoubleComputable d = MathUtil.wrap(10.0);
		System.out.println(l.multiply(d));
		System.out.println(d.multiply(l));
		System.out.println(l.decrease(MathUtil.wrap(100.0)));
		System.out.println(l.divide(MathUtil.wrap(100f)));
		System.out.println(d.decrease(MathUtil.wrap(100f)));
	}
	
}