package com.nali.spreader.util.math;

public class ByteComputable extends AbstractComputable<Byte, ByteComputable> {

	public ByteComputable(Byte number) {
		super(number);
	}

	@Override
	protected int getLevel() {
		return LV_BYTE;
	}

	@Override
	protected ByteComputable generate(Number n) {
		return new ByteComputable(n.byteValue());
	}

	@Override
	protected ByteComputable add0(ByteComputable e) {
		return new ByteComputable((byte) (number + e.number));
	}

	@Override
	protected ByteComputable decrease0(ByteComputable e) {
		return new ByteComputable((byte) (number - e.number));
	}

	@Override
	protected ByteComputable multiply0(ByteComputable e) {
		return new ByteComputable((byte) (number * e.number));
	}

	@Override
	protected ByteComputable divide0(ByteComputable e) {
		return new ByteComputable((byte) (number / e.number));
	}

}
