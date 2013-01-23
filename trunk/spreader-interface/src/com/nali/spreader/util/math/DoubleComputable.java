package com.nali.spreader.util.math;

public class DoubleComputable extends AbstractComputable<Double, DoubleComputable> {

	public DoubleComputable(Double number) {
		super(number);
	}

	@Override
	protected int getLevel() {
		return LV_DOUBLE;
	}

	@Override
	protected DoubleComputable generate(Number n) {
		return new DoubleComputable(n.doubleValue());
	}

	@Override
	protected DoubleComputable add0(DoubleComputable e) {
		return new DoubleComputable(number + e.number);
	}

	@Override
	protected DoubleComputable decrease0(DoubleComputable e) {
		return new DoubleComputable(number - e.number);
	}

	@Override
	protected DoubleComputable multiply0(DoubleComputable e) {
		return new DoubleComputable(number * e.number);
	}

	@Override
	protected DoubleComputable divide0(DoubleComputable e) {
		return new DoubleComputable(number / e.number);
	}

}
