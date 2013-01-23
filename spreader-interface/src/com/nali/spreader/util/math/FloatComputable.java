package com.nali.spreader.util.math;

public class FloatComputable extends AbstractComputable<Float, FloatComputable> {

	public FloatComputable(Float number) {
		super(number);
	}

	@Override
	protected int getLevel() {
		return LV_FLOAT;
	}

	@Override
	protected FloatComputable generate(Number n) {
		return new FloatComputable(n.floatValue());
	}

	@Override
	protected FloatComputable add0(FloatComputable e) {
		return new FloatComputable(number + e.number);
	}

	@Override
	protected FloatComputable decrease0(FloatComputable e) {
		return new FloatComputable(number - e.number);
	}

	@Override
	protected FloatComputable multiply0(FloatComputable e) {
		return new FloatComputable(number * e.number);
	}

	@Override
	protected FloatComputable divide0(FloatComputable e) {
		return new FloatComputable(number / e.number);
	}

}
