package com.nali.spreader;

import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import com.nali.spreader.model.RealUser;
import com.nali.spreader.serialize.XstreamSerializer;

public class XStreamTest extends TestCase{
	private XstreamSerializer serializer;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.serializer = new XstreamSerializer("user", RealUser.class, "users");
	}
	
	public void testParseXml() throws Exception {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream ins = loader.getResourceAsStream("DBTagWeiboAccount.xml");
		List<RealUser> users = this.serializer.toBean(ins);
		for(RealUser user : users) {
			System.out.println(user.getNickName());
		}
	}
}
