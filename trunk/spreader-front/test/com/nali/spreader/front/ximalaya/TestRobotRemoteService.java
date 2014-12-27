package com.nali.spreader.front.ximalaya;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.client.ximalaya.XimalayaImportUserActionMethod;
import com.nali.spreader.client.ximalaya.exception.AuthenticationException;
import com.nali.spreader.client.ximalaya.service.IRobotRemoteService;
import com.nali.spreader.client.ximalaya.service.IXimalayInterfaceCheckService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestRobotRemoteService {
	@Autowired
	private IRobotRemoteService robotRemoteService;
	@Autowired
	private XimalayaImportUserActionMethod mtd;
	@Autowired
	private IXimalayInterfaceCheckService interfaceCheckService;

	@Test
	public void test() throws AuthenticationException, NoSuchAlgorithmException, IOException {
		// Map<String, Object> param = new HashMap<String, Object>();
		// param.put("keyword", "xx");
		// param.put("offset", 10);
		// param.put("limit", 100);
		// param.put("fansGte", 10L);
		// param.put("fansLte", 100L);
		// param.put("vType", 1);
		// param.put("startCreateTime", new Date().getTime());
		// param.put("endCreateTime", new Date().getTime());
		// param.put("startUpdateTime", new Date().getTime());
		// param.put("endUpdateTime", new Date().getTime());
		// System.out.println("开始。。。。");
		// mtd.execute(param, new HashMap<String, Object>(), 1L);
		Date sd1 = DateUtils.addDays(new Date(), -100);
		Date ed1 = new Date();
		byte[] md = interfaceCheckService.getParamsMD5(new Object[] { null, 0, 10, null, null, null, sd1, ed1, null, null });
		List<Map<String, Object>> list = robotRemoteService.queryUser(null, 0, 10, null, null, null, sd1, ed1, null, null, md);
		System.out.println(list.toString());
	}

	@Test
	public void test2() throws AuthenticationException, NoSuchAlgorithmException, IOException {
//			Long fromWebsiteUid = 18222244L;
		
			Long toWebsiteUid = 15107843L;
	       File file = new File("D:/222.txt");
	        BufferedReader reader = null;
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	            	
	            	Long fromUid = Long.parseLong(tempString);
					byte[] md5 = interfaceCheckService.getParamsMD5(new Object[] { fromUid, toWebsiteUid });
					robotRemoteService.follow(fromUid, toWebsiteUid, md5);
					
	                System.out.println("line " + line + ": " + tempString);
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
		
	
	}

}
