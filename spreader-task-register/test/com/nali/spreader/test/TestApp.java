package com.nali.spreader.test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.CharArrayBuffer;

public class TestApp {
	public static void main(String[] args) {
		HttpClient hc = new DefaultHttpClient();
		String url = "https://itunes.apple.com/WebObjects/MZStore.woa/wa/topChartFragmentData?cc=cn&genreId="+6011+"&pageSize=100&popId="+27+"&pageNumbers="+10;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", "iTunes-iPad/5.1.1 (2; 16GB; dt:74)");
		post.setHeader("Accept", "*/*");
		post.setHeader("X-Apple-Client-Versions", "GameCenter/2.0");
		post.setHeader("X-Apple-Partner", "origin.0");
		post.setHeader("X-Apple-Connection-Type", "WiFi");
		post.setHeader("Referer", "https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewTop?cc=cn&genreId=6001&df=1");
		post.setHeader("X-Apple-Store-Front", "143465-19,9");
		post.setHeader("X-Apple-Client-Application", "Software");
		post.setHeader("Accept-Language", "zh-cn");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Proxy-Connection", "keep-alive");
		try {
			HttpResponse rep = hc.execute(post);
//			rep.setHeader("Content-Type", "text/plain; charset=UTF-8");
			System.out.println(Arrays.asList(rep.getAllHeaders()));
			HttpEntity entity = rep.getEntity();
            Reader reader = new InputStreamReader(entity.getContent(), "utf-8");
            CharArrayBuffer buffer = new CharArrayBuffer(4096);
            char[] tmp = new char[1024];
            int l;
            while((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
            System.out.println(buffer.toString());
//			System.out.println(x);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
