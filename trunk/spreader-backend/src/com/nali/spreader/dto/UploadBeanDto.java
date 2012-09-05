package com.nali.spreader.dto;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 用于上传文件的DTO
 * 
 * @author xiefei
 * 
 */
public class UploadBeanDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private CommonsMultipartFile file;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
		this.name = file.getOriginalFilename();
	}
}
