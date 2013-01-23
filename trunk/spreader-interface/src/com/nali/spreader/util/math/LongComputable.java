package com.nali.spreader.util.math;

public class LongComputable extends AbstractComputable<Long, LongComputable> {

	public LongComputable(Long number) {
		super(number);
	}

	@Override
	protected int getLevel() {
		return LV_LONG;
	}

	@Override
	protected LongComputable generate(Number n) {
		return new LongComputable(n.longValue());
	}

	@Override
	protected LongComputable add0(LongComputable e) {
		return new LongComputable(number + e.number);
	}

	@Override
	protected LongComputable decrease0(LongComputable e) {
		return new LongComputable(number - e.number);
	}

	@Override
	protected LongComputable multiply0(LongComputable e) {
		return new LongComputable(number * e.number);
	}

	@Override
	protected LongComputable divide0(LongComputable e) {
		return new LongComputable(number / e.number);
	}

}
