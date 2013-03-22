package com.nali.spreader.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class EmailRegister {
	private static Logger logger = Logger.getLogger(EmailRegister.class);
	private static final int RETRY_TIME = 5;
	private String adminUser = "postmaster@9nali.com";
	private String adminPwd = "jacky153246";
	private Map<String, String> cookies;

	public boolean register(String email, String domain, String pwd) throws IOException {
		checkLogin();
		Connection connection = Jsoup.connect("http://mail.9nali.com/iredadmin/create/user")
				.data("username", email)
				.data("newpw", pwd)
				.data("confirmpw", pwd)
				.data("domainName", domain)
				.data("mailQuota", "50")
				.data("oldMailQuota", "")
				.data("cn", "")
				.cookies(cookies)
				.followRedirects(false)
				.timeout(3000)
				.method(Method.POST);
		Response res = execute(connection);
		if(res.statusCode() == HttpURLConnection.HTTP_SEE_OTHER) {//TODO login
			String rltLocation = res.header("Location");
			if(rltLocation.indexOf("msg=CREATED_SUCCESS")!=-1) {
				return true;
			} else if(rltLocation.indexOf("msg=ALREADY_EXISTS")!=-1) {
				return false;
			} else if(checkIsNeedLogin(res)) {
				return register(email, domain, pwd);
			} else {
				throw new IOException("register failed:Location=" + rltLocation);
			}
		} else {
			throw new IOException("register failed:statusCode=" + res.statusCode());
		}
	}
	
	public void del(String user, String domain) throws IOException {//for test
		checkLogin();
		Connection connection = Jsoup.connect("http://mail.9nali.com/iredadmin/users/" + domain)
				.data("action", "delete")
				.data("cur_page", "1")
				.data("mail", user + "@" + domain)
				.cookies(cookies)
				.followRedirects(false)
				.timeout(3000)
				.method(Method.POST);
		Response res = execute(connection);
		if(res.header("Location").indexOf("msg=DELETED_SUCCESS")==-1) {
			if(checkIsNeedLogin(res)) {
				del(user, domain);
				return;
			}
			throw new IOException("del failed:Location=" + res.header("Location"));
		}
	}
	
	private boolean checkIsNeedLogin(Response res) {
		String location = res.header("Location");
		if(location.indexOf("http://mail.9nali.com/iredadmin/login")!=-1 ||
				location.indexOf("msg=loginRequired")!=-1) {
			cookies=null;
			return true;
		}
		return false;
	}

	private void checkLogin() throws IOException {
		if(cookies!=null) {
			return;
		}
		synchronized (this) {
			if(cookies!=null) {
				return;
			}
			Connection connection = Jsoup.connect("http://mail.9nali.com/iredadmin/login")
					.data("username", adminUser)
					.data("password", adminPwd)
					.data("save_pass", "yes")
					.timeout(3000)
					.method(Method.POST);
			Response res = execute(connection);
			if(res.body().indexOf(adminUser)!=-1) {
				cookies = res.cookies();
			} else {
				throw new IOException("login failed:\r\n" + res.body());
			}
		}
	}

	private Response execute(Connection connection) throws IOException {
		for (int i = 0; i < RETRY_TIME; i++) {
			try {
				return connection.execute();
			} catch (IOException e) {
				if(i==RETRY_TIME-1) {
					throw new IOException("retry too much", e);
				}
				logger.warn("connect fail", e);
				try {
					Thread.sleep(i * 5000L + 5000L);
				} catch (InterruptedException e1) {
					logger.error(e1);
				}
			}
		}
		return null;
	}

}
