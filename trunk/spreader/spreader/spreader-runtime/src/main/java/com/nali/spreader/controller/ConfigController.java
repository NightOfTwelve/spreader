package com.nali.spreader.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thoughtworks.xstream.XStream;

@Controller
public class ConfigController {
	private XStream xtream = new XStream();

	public ConfigController() {
		this.xtream.alias("SN", Params.class);
//		this.xtream.aliasAttribute("Weibo", "weibo");
//		this.xtream.aliasAttribute("56", "wuliu");
//		this.xtream.aliasAttribute("Renren", "renren");
		this.xtream.aliasField("Weibo", String.class, "weibo");
		this.xtream.aliasField("56",  String.class, "wuliu");
		this.xtream.aliasField("Renren",  String.class, "renren");
	}
	
	private static class Params {
		private String Weibo;
		private String Wuliu;
		private String Renren;
		public Params() {
		}
		public String getWeibo() {
			return Weibo;
		}
		public void setWeibo(String weibo) {
			Weibo = weibo;
		}
		public String getWuliu() {
			return Wuliu;
		}
		public void setWuliu(String wuliu) {
			Wuliu = wuliu;
		}
		public String getRenren() {
			return Renren;
		}
		public void setRenren(String renren) {
			Renren = renren;
		}
	}
	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request, Model model){
		return "index";
	}

	@RequestMapping(value = "config", method = RequestMethod.POST)
	public String writeXml(HttpServletRequest request, Model model) {
		Map requestParams = request.getParameterMap();
		Map params = new HashMap(requestParams.size());
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		String weibo = request.getParameter("Weibo");
		String wuliu = request.getParameter("Wuliu");
		String renren = request.getParameter("Renren");
		
		Params paramsObj = new Params();
		paramsObj.setWeibo(weibo);
		paramsObj.setWuliu(wuliu);
		paramsObj.setRenren(renren);

		String path = request.getSession().getServletContext().getRealPath("/");
		path = path + "templates" + File.separator
				+ "seed.xml";
		File file = new File(path);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					out, "UTF-8"));
			this.xtream.toXML(paramsObj, writer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		model.addAllAttributes(params);
		return "index";
	}
}
