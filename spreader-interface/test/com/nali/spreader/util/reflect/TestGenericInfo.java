package com.nali.spreader.util.reflect;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.nali.spreader.util.reflect.GenericInfo;


public class TestGenericInfo {
	public static void main(String[] args) {
//		print(CC.class);
//		GenericInfo<BB> bb = GenericInfo.get(BB.class);
//		GenericInfo<DD> dd = bb.build(DD.class);
//		print(bb);
//		print(dd);
//		GenericInfo<EE> ee = bb.build(EE.class);
//		print(ee);
//		print(PP.class);
//		print(QQ.class);
//		print(UU.class);
//		
//		
//		print(FF.class);
//		print(GG.class);
//		print(HH.class);
//		print(II.class);
//		print(KK.class);//两个参数变成一个 K,V --> Map<K, V>[]
//		print(WW.class);
//		print(List.class);
		print(TestInterface.class);//测接口的泛型参数
		print(TestInterface2.class);//测接口的泛型参数
		System.out.println(TestInterface2.class.getGenericInterfaces().length);
		System.out.println(TestInterface3.class.getGenericInterfaces().length);
	}
	

	
	public static void print(Class<?> clazz) {
		GenericInfo<?> genericInfo = GenericInfo.get(clazz);
		print(genericInfo);
	}
	public static void print(GenericInfo<?> genericInfo) {
		System.out.println("======================");
		Map<TypeVariable<?>, Type> map = genericInfo.getGenericArgumentMap();
		for (Entry<TypeVariable<?>, Type> e : map.entrySet()) {
			TypeVariable<?> key = e.getKey();
			Type value = e.getValue();
			System.out.println(ts(key) + " = " + ts(value));
		}
	}

	private static String ts(Type t) {
		if (t instanceof TypeVariable) {
			TypeVariable<?> tv = (TypeVariable<?>) t;
			return tv.getGenericDeclaration() + "<" + tv + ">";
		}
		if (t instanceof StatefulGenericType) {
			StatefulGenericType st = (StatefulGenericType) t;
			return st + "<" + st.hasVars() + ">";
		}
		return t.toString();
	}
}
class BB<T extends Serializable & List> {
}
class CC<T> extends ArrayList<List<? extends T>> {
}
class DD<H extends Serializable & List> extends BB<H> {
}
class AA extends BB {}
class EE extends DD<ArrayList<List<?>[]>> {
	
}
class OO<T extends Serializable & List<String>> {}
class PP extends OO {}
class QQ<N extends Map<N, Object>> {}
class UU<T extends Serializable & List<? extends X>, X, Y extends T> {}


class FF<T extends Serializable & List<? extends X[]>, X, Y extends T> extends BB {}
class GG<T extends GG<T>> {}
class HH<T extends GG<T>> extends GG<T> {}
class II<T> extends HashMap<T, List<T>[]> {}
class JJ<K, V> extends ArrayList<Map<K, V>[]> {}
class KK extends JJ<String, Integer> {}
class WW<T>{}

class TF<X> {
	TF<TF<X>> t;
}
class TA<X> {
	TB<X> b;
}
class TB<X> {
	TA<X> a;
}
class TC<X> {
	TD<TC<X>> c;
	{
		TC<TD<TC<TD<TC<X>>>>> d = c.d.c.d;
		TC<X> x = c.x.c.x.c.x.c.x;
	}
}
class TD<X> {
	TC<TD<X>> d;
	X x;
	X[] xx;
}
interface IA<E,F> {}
interface IB<E,F> extends IA<E,F> {}
interface IC<E,F> extends IB<E,F> {}
class TestInterface implements IC<String, Integer> {}
interface ID<E> extends IC<E,Long> {}
class TestInterface2 implements ID<Double> {}
class TestInterface3 extends TestInterface2 {}