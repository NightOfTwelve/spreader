package com.nali.spreader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.Photo;
import com.nali.spreader.service.IPhotoLibraryService;

@Controller
@RequestMapping(value = "/photolib")
public class PhotoLibraryController extends BaseController {
	@Autowired
	private IPhotoLibraryService photoService;

	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	public String init() {
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
	 */
	@ResponseBody
	@RequestMapping(value = "/gridstore")
	public String photoLibgridStore(String picType, Boolean avatarflg, Boolean photolibflg,
			Integer start, Integer limit) {
		Limit lit = this.initLimit(start, limit);
		PageResult<Photo> pr = photoService.findPhotoLibraryList(picType, avatarflg, photolibflg,
				lit);
		return this.write(pr);
	}
}
