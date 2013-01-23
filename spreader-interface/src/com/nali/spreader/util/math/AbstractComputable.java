package com.nali.spreader.util.math;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class AbstractComputable<N extends Number, E extends AbstractComputable<N, E>> implements Computable {
	public static final int LV_BYTE = 10;
	public static final int LV_SHORT = 20;
	public static final int LV_INT = 30;
	public static final int LV_LONG = 40;
	public static final int LV_FLOAT = 50;
	public static final int LV_DOUBLE = 60;
	protected abstract int getLevel();
	protected abstract E add0(E e);
	protected abstract E decrease0(E e);
	protected abstract E multiply0(E e);
	protected abstract E divide0(E e);
	protected abstract E generate(Number n);
	protected N number;
	public AbstractComputable(N number) {
		super();
		this.number = number;
	}
	protected E tran(AbstractComputable<?, ?> e) {
		return generate(e.number);
	}
	public N getNumber() {
		return number;
	}
	@Override
	public AbstractComputable add(Computable c) {
		Pair pair = new Pair(this, (AbstractComputable) c);
		return pair.from.add0(pair.to);
	}
	@Override
	public AbstractComputable decrease(Computable c) {
		Pair pair = new Pair(this, (AbstractComputable) c);
		return pair.from.decrease0(pair.to);
	}
	@Override
	public AbstractComputable multiply(Computable c) {
		Pair pair = new Pair(this, (AbstractComputable) c);
		return pair.from.multiply0(pair.to);
	}
	@Override
	public AbstractComputable divide(Computable c) {
		Pair pair = new Pair(this, (AbstractComputable) c);
		return pair.from.divide0(pair.to);
	}
	@Override
	public int intValue() {
		return getNumber().intValue();
	}
	@Override
	public long longValue() {
		return getNumber().longValue();
	}
	@Override
	public float floatValue() {
		return getNumber().floatValue();
	}
	@Override
	public double doubleValue() {
		return getNumber().doubleValue();
	}
	@Override
	public byte byteValue() {
		return getNumber().byteValue();
	}
	@Override
	public short shortValue() {
		return getNumber().shortValue();
	}
	private static class Pair<N extends Number, X extends AbstractComputable<N, X>> {
		X from;
		X to;
		public Pair(AbstractComputable from, AbstractComputable to) {
			if(from.getLevel()>to.getLevel()) {
				to = from.tran(to);
			} else if(from.getLevel()<to.getLevel()) {
				from = to.tran(from);
			}
			this.from = (X) from;
			this.to = (X) to;
		}
	}
	@Override
	public String toString() {
		return number.getClass().getSimpleName() + ":" + number;
	}
}
