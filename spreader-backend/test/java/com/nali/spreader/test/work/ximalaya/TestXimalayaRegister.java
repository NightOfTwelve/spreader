package com.nali.spreader.test.work.ximalaya;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.bcel.util.ClassLoader;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.analyzer.system.FetchWeiboDetailAnalyzer;
import com.nali.spreader.analyzer.ximalaya.WeiboGenerateXimalayaUsers;
import com.nali.spreader.config.GenXimalayaUsersConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestXimalayaRegister {
	// @Autowired
	// private WeiboGenerateXimalayaUsers weiboGenerateXimalayaUsers;
//	@Autowired
//	private FetchWeiboDetailAnalyzer fetchWeiboDetailAnalyzer;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static int is = 1;

	@Test
	public void test() {
		Long x = (Long) redisTemplate.opsForValue().getAndSet(
				"spreader_user_spider_last_content_id", 101L);
		Long x2 = (Long) redisTemplate.opsForValue().get(
				"spreader_user_spider_last_content_id");
		System.out.println("id>>" + x+",x2>>"+x2);
	}

	// @Test
	// public void test() {
	// GenXimalayaUsersConfig config = new GenXimalayaUsersConfig();
	// // config.setExcludeUserGroup(excludeUserGroup);
	// config.setGenCount(100);
	// weiboGenerateXimalayaUsers.init(config);
	// weiboGenerateXimalayaUsers.work();
	// }
	// @Test
	// public void test2() {
	// fetchWeiboDetailAnalyzer.work();
	// }
	public void t2(String s,Object ...objects){
		System.out.println(t3(s,objects));
	}
	public String t3(String s,Object ...objects){
		String x = "";
		Object[] oo = new Object[]{};
		String.format("x", oo);
		System.out.println(s+objects);
		return "nimei";
	}
	public static class VariantTest {
		public static int staticVar = 0;
		public int instanceVar = 0;

		public VariantTest() {
			staticVar++;
			instanceVar++;
			System.out.println("staticVar=" + staticVar + ",instanceVar="
					+ instanceVar);
		}

		public void x() {
			System.out.println(is);
		}
	}

	public static void main(String[] args) throws ClassNotFoundException,
			SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		TestXimalayaRegister x = new TestXimalayaRegister();
		Object [] os = new Object[]{"1","2","4"};
		x.t2("x", os);
//		VariantTest vt1 = new VariantTest();
//		VariantTest vt2 = new VariantTest();
//		VariantTest vt3 = new VariantTest();
		// File f1 = new File("D:\\nali\\tmp\\t1.txt");
		// File f2 = new File("D:\\nali\\tmp\\t2.txt");
		// try {
		// FileUtils.copyFile(f1, f2);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// ClassLoader classLoader = new ClassLoader(
		// new String[] { "D:\\nali\\tmp" });// 类根路径
		// Class<?> cl = classLoader
		// .loadClass("com.nali.spreader.controller.ReplySearchController");//
		// 类名
		// Method method = cl.getMethod("settingCookies", String.class);// 类的方法
		// System.out.println(method.getName());// 打印方法名
		// String[] arrS = (String[]) method.invoke(null, "a,a", ",");// 调用方法
		// for (String string : arrS) {// 打印返回值
		// System.out.println(string);
		// }
	}

}
