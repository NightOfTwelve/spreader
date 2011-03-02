package com.nali.spreader.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
		this.xtream.alias("SN", HashMap.class);
	}
	
	@RequestMapping(value="config", method=RequestMethod.POST)
	public void writeXml(HttpServletRequest request, Model model) {
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
		
		File file = new File("");
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
			this.xtream.toXML(params, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
