package com.nali.spreader.util.math;

public class IntegerComputable extends AbstractComputable<Integer, IntegerComputable> {

	public IntegerComputable(Integer number) {
		super(number);
	}

	@Override
	protected int getLevel() {
		return LV_INT;
	}

	@Override
	protected IntegerComputable generate(Number n) {
		return new IntegerComputable(n.intValue());
	}

	@Override
	protected IntegerComputable add0(IntegerComputable e) {
		return new IntegerComputable(number + e.number);
	}

	@Override
	protected IntegerComputable decrease0(IntegerComputable e) {
		return new IntegerComputable(number - e.number);
	}

	@Override
	protected IntegerComputable multiply0(IntegerComputable e) {
		return new IntegerComputable(number * e.number);
	}

	@Override
	protected IntegerComputable divide0(IntegerComputable e) {
		return new IntegerComputable(number / e.number);
	}

}
