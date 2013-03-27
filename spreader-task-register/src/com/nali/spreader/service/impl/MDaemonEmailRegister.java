package com.nali.spreader.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
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
	@Value("${spreader.email.mdaemon.mailLoginUrl}")
	private String mailLoginUrl;
	@Value("${spreader.email.mdaemon.mailRegisterUrl}")
	private String mailRegisterUrl;
	@Value("${spreader.email.mdaemon.mailDeleteUrl}")
	private String mailDeleteUrl;
	private static final String LOGIN_LOCATION = "login.wdm?expired=1";
	private String firstAccessHeader;
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
		return execute(xml, mailRegisterUrl);
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
		return execute(xml, mailDeleteUrl);
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
		StringBuffer buff = new StringBuffer();
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
		StringBuffer buff = new StringBuffer();
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

	private boolean execute(String postXML, String postUrl) {
		if (isNeedLogin(postUrl)) {
			login();
		}
		HttpPost post = new HttpPost(postUrl);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			StringEntity myEntity = new StringEntity(postXML, DEFAULT_CHARSET);
			post.addHeader("Content-Type", "text/xml");
			post.setHeader("Cookie", firstAccessHeader);
			post.setEntity(myEntity);
			HttpResponse response = httpClient.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				return true;
			} else {
				HttpEntity resEntity = response.getEntity();
				InputStreamReader reader = new InputStreamReader(
						resEntity.getContent(), DEFAULT_CHARSET);
				char[] buff = new char[1024];
				int length = 0;
				while ((length = reader.read(buff)) != -1) {
					logger.error(new String(buff, 0, length));
				}
			}
			post.abort();
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return false;
	}

	private void login() {
		HttpPost post = new HttpPost(mailLoginUrl);
		if (firstAccessHeader == null) {
			getFirstLoginCookies();
		}
		post.setHeader("Cookie", firstAccessHeader);
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
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			post.abort();
		}
	}

	private boolean isNeedLogin(String url) {
		if (firstAccessHeader == null) {
			return true;
		}
		HttpGet get = new HttpGet(url);
		get.addHeader("Content-Type", "text/xml");
		HttpParams hp = httpClient.getParams();
		hp.setParameter(ClientPNames.HANDLE_REDIRECTS, false);
		HttpResponse response;
		try {
			response = httpClient.execute(get);
			Header hs = response.getFirstHeader("Location");
			if (hs == null) {
				return false;
			} else {
				return LOGIN_LOCATION.equals(hs.getValue());
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			get.abort();
		}
		return true;
	}

	/**
	 * 获取首次访问页面得到的cookies
	 * 
	 * @return
	 */
	private void getFirstLoginCookies() {
		HttpGet get = new HttpGet(mailLoginUrl);
		try {
			HttpResponse response = httpClient.execute(get);
			Header setCookie = response.getFirstHeader("Set-Cookie");
			if (setCookie != null) {
				firstAccessHeader = setCookie.getValue();
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			get.abort();
		}
	}

	private String getCDATA(String value) {
		StringBuffer buff = new StringBuffer("<![CDATA[");
		buff.append(value);
		buff.append("]]>");
		return buff.toString();
	}

	public static void main(String[] args) {
		MDaemonEmailRegister m = new MDaemonEmailRegister();
		m.register("testCookies4", "abc.com", "123");
		m.del("testCookies1", "abc.com");
	}
}
