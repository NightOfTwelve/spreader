package com.nali.spreader.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nali.spreader.service.IEmailRegisterService;

@Service
public class MDaemonEmailRegister implements IEmailRegisterService {
	private static final Logger logger = Logger
			.getLogger(MDaemonEmailRegister.class);
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String MAIL_USER_ADMIN = "admin@abc.com";
	private static final String MAIL_USER_ADMIN_PSW = "admin";
	// TOOD DELETE
	// private String mailLoginUrl = "http://192.168.3.133:1000/login.wdm";
	// private String mailRegisterUrl =
	// "http://192.168.3.133:1000/useredit_account.wdm?postXML=1";
	// private String mailDeleteUrl =
	// "http://192.168.3.133:1000/userlist.wdm?postXML=1";
	@Value("${spreader.email.mdaemon.mailLoginUrl}")
	private String mailLoginUrl = "http://192.168.3.133:1000/login.wdm";
	@Value("${spreader.email.mdaemon.mailRegisterUrl}")
	private String mailRegisterUrl = "http://192.168.3.133:1000/useredit_account.wdm?postXML=1";
	@Value("${spreader.email.mdaemon.mailDeleteUrl}")
	private String mailDeleteUrl = "http://192.168.3.133:1000/userlist.wdm?postXML=1";
	private static final DefaultHttpClient httpClient = new DefaultHttpClient();

	/**
	 * 注册
	 * 
	 * @param userName
	 * @param domain
	 * @param password
	 * @return
	 */
	@Override
	public boolean register(String userName, String domain, String password) {
		String xml = getRegisterPostXML(userName, domain, password);
		return execute(xml, mailRegisterUrl, 5);
	}

	/**
	 * 删除
	 * 
	 * @param userName
	 * @param domain
	 * @return
	 */
	@Override
	public boolean del(String userName, String domain) {
		String xml = getDelPostXML(userName, domain);
		return execute(xml, mailDeleteUrl, 5);
	}

	/**
	 * 构造用于注册的XML
	 * 
	 * @param userName
	 * @param domain
	 * @param password
	 * @return
	 */
	private String getRegisterPostXML(String userName, String domain,
			String password) {
		StringBuilder buff = new StringBuilder();
		buff.append("<root> ");
		buff.append("<Form Name=\"waForm\" Document=\"useredit_account.wdm\" UrlVars=\"\">");
		buff.append("<SmartPassword Type=\"password\"><![CDATA[]]></SmartPassword> ");
		buff.append("<SmartUser Type=\"text\"><![CDATA[]]></SmartUser> ");
		buff.append("<IMAPAccess Type=\"checkbox\"><![CDATA[1]]></IMAPAccess> ");
		buff.append("<MultiPOPAccess Type=\"checkbox\"><![CDATA[0]]></MultiPOPAccess> ");
		buff.append("<POPAccess Type=\"checkbox\"><![CDATA[1]]></POPAccess> ");
		buff.append("<IsEnabled Type=\"checkbox\"><![CDATA[1]]></IsEnabled> ");
		buff.append("<Action Type=\"hidden\"><![CDATA[]]></Action>");
		buff.append("<SyncMLPassword Type=\"password\">");
		buff.append(getCDATA(password));
		buff.append("<Password Type=\"password\">");
		buff.append(getCDATA(password));
		buff.append("</Password> ");
		buff.append("<Domain Type=\"select-one\">");
		buff.append(getCDATA(domain));
		buff.append("</Domain> ");
		buff.append("<MailBox Type=\"text\">");
		buff.append(getCDATA(userName));
		buff.append("</MailBox> ");
		buff.append("<FullName Type=\"text\">");
		buff.append(getCDATA(userName + domain));
		buff.append("</FullName> ");
		buff.append("</Form> ");
		buff.append("</root>");
		return buff.toString();
	}

	/**
	 * 构造删除的XML
	 * 
	 * @param userName
	 * @param domain
	 * @return
	 */
	private String getDelPostXML(String userName, String domain) {
		String delete = getCDATA(userName + "@" + domain);
		StringBuilder buff = new StringBuilder();
		buff.append("<root><Form Name=\"waForm\" Document=\"userlist.wdm\" UrlVars=\"\"> ");
		buff.append("<FilterCol Type=\"hidden\"><![CDATA[]]></FilterCol> ");
		buff.append("<FilterMatch Type=\"hidden\"><![CDATA[]]></FilterMatch> ");
		buff.append("<Filter Type=\"hidden\"><![CDATA[]]></Filter> ");
		buff.append("<SelectId Type=\"hidden\">");
		buff.append(delete);
		buff.append("</SelectId> ");
		buff.append("<Action Type=\"hidden\"><![CDATA[delete]]></Action> </Form> </root>");
		return buff.toString();
	}

	private boolean execute(String postXML, String postUrl, int tryTimes) {
		HttpPost post = new HttpPost(postUrl);
		// if (tryTimes <= 1) {
		// return false;
		// }
		try {
			StringEntity myEntity = new StringEntity(postXML, DEFAULT_CHARSET);
			post.addHeader("Content-Type", "text/xml");
			post.setEntity(myEntity);
			HttpResponse response = httpClient.execute(post);
			if (response != null) {
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {
					HttpEntity ht = response.getEntity();
					String html = EntityUtils.toString(ht);
					if (isLoginPage(html)) {
						login();
						tryTimes--;
						System.out.println(html);
						return execute(postXML, postUrl, tryTimes);
					} else {
						System.out.println(html);
						return true;
					}
				} else {
					HttpEntity resEntity = response.getEntity();
					InputStreamReader reader = new InputStreamReader(
							resEntity.getContent(), DEFAULT_CHARSET);
					logger.error("execute :" + postUrl + ",error,"
							+ IOUtils.toString(reader));
					login();
					tryTimes--;
					return execute(postXML, postUrl, tryTimes);
				}
			}
		} catch (IOException e) {
			logger.error(e);
		} finally {
			post.abort();
		}
		return false;
	}

	private synchronized void login() {
		HttpPost post = new HttpPost(mailLoginUrl);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		nvs.add(new BasicNameValuePair("CheckForCookie", "1"));
		nvs.add(new BasicNameValuePair("RequestedPage", "login"));
		nvs.add(new BasicNameValuePair("lang", "zh"));
		nvs.add(new BasicNameValuePair("logon", "Sign In"));
		nvs.add(new BasicNameValuePair("passwd", MAIL_USER_ADMIN_PSW));
		nvs.add(new BasicNameValuePair("username", MAIL_USER_ADMIN));
		try {
			post.setEntity(new UrlEncodedFormEntity(nvs, DEFAULT_CHARSET));
			httpClient.execute(post);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			post.abort();
		}
	}

	/**
	 * 分析是否是登录页面
	 * 
	 * @param htmlText
	 * @return
	 */
	private boolean isLoginPage(String htmlText) {
		Document doc = Jsoup.parse(htmlText);
		Elements els = doc.select(".loginButton");
		if (els.size() > 0) {
			return true;
		}
		if(htmlText.indexOf("Your session has expired")>0) {
			return true;
		}
		return false;
	}

	private String getCDATA(String value) {
		StringBuilder buff = new StringBuilder("<![CDATA[");
		buff.append(value);
		buff.append("]]>");
		return buff.toString();
	}

	public static void main(String[] args) {
		MDaemonEmailRegister m = new MDaemonEmailRegister();
		String names[] = { "t1", "t2", "t3", "t4", "t5" };
		for (String x : names) {
			boolean t = m.register(x, "360ke.net", "123");
			// boolean t = m.del(x, "360ke.net");
			System.out.println(t);
		}

		// m.del("", "abc2.com");
		// private String mailLoginUrl = "http://192.168.3.133:1000/login.wdm";
		// private String mailRegisterUrl =
		// "http://192.168.3.133:1000/useredit_account.wdm?postXML=1";
		// private String mailDeleteUrl =
		// "http://192.168.3.133:1000/userlist.wdm?postXML=1";
	}
}
