package com.nali.spreader.stat;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.util.KeyValuePair;

@Service
public class StatService implements IStatService {
	@Autowired
	private StatMetaContainer statMetaContainer;
	@Autowired
	private IStatDao statDao;

	@Override
	public List<Map<String, Object>> queryData(String name, Object obj) {
		BaseStatMeta baseStatMeta = statMetaContainer.getStatMetas().get(name);
		if(baseStatMeta==null) {
			throw new IllegalArgumentException("statMeta doesnot exist:"+name);
		}
		if(baseStatMeta.getExtendMeta()==null) {
			return statDao.select(baseStatMeta.getSqlmap());
		} else {
			return statDao.select(baseStatMeta.getSqlmap(), obj);
		}
	}

	@Override
	public StatMetaDisplayData getStatMetaDisplayData(String name) {
		return new StatMetaDisplayData(statMetaContainer.getStatMetas().get(name));
	}

	@Override
	public List<KeyValuePair<String, String>> listStatNames() {
		return statMetaContainer.getStatNameList();
	}
}
