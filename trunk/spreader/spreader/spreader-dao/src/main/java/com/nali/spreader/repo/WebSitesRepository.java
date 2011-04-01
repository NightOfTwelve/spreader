package com.nali.spreader.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.dao.ICrudWebsitesDao;
import com.nali.spreader.dao.IWebsitesDao;
import com.nali.spreader.model.Websites;

public class WebSitesRepository extends InmemoryResourceRepository<Websites>{
	@Autowired
	private ICrudWebsitesDao crudWebsitesDao;
	
	@Autowired
	private IWebsitesDao websitesDao;

	@Override
	public Websites load(int id) {
		return this.crudWebsitesDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Websites> loadAll() {
		return this.websitesDao.getAllWebsites();
	}  
}
