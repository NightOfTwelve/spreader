package com.nali.spreader.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.data.Photo;
import com.nali.spreader.service.IPhotoLibraryService;

@Controller
@RequestMapping(value = "/photolib")
public class PhotoLibraryController {
	private static ObjectMapper json = new ObjectMapper();
	@Autowired
	private IPhotoLibraryService photoService;

	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String inti() {
		return "/show/main/PhotoLibShow";
	}

	/**
	 * 列表页查询
	 * 
	 * @param picType
	 * @param avatarflg
	 * @param photolibflg
	 * @param start
	 * @param limit
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/gridstore")
	public String photoLibgridStore(String picType, Boolean avatarflg,
			Boolean photolibflg, Integer start, Integer limit)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (start == null)
			start = 0;
		if (limit == null || limit <= 0)
			limit = 20;
		PageResult<Photo> pr = photoService.findPhotoLibraryList(picType,
				avatarflg, photolibflg, start, limit);
		return json.writeValueAsString(pr);
	}
}
