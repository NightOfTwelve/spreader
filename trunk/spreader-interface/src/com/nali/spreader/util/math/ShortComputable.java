package com.nali.spreader.util.math;

public class ShortComputable extends AbstractComputable<Short, ShortComputable> {

	public ShortComputable(Short number) {
		super(number);
	}

	@Override
	protected int getLevel() {
		return LV_SHORT;
	}

	@Override
	protected ShortComputable generate(Number n) {
		return new ShortComputable(n.shortValue());
	}

	@Override
	protected ShortComputable add0(ShortComputable e) {
		return new ShortComputable((short) (number + e.number));
	}

	@Override
	protected ShortComputable decrease0(ShortComputable e) {
		return new ShortComputable((short) (number - e.number));
	}

	@Override
	protected ShortComputable multiply0(ShortComputable e) {
		return new ShortComputable((short) (number * e.number));
	}

	@Override
	protected ShortComputable divide0(ShortComputable e) {
		return new ShortComputable((short) (number / e.number));
	}

}
