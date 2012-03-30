package com.nali.spreader.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.nali.spreader.util.KeyValuePair;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.bean.PropertyUtil;

@Component
public class StatMetaContainer {
	private static final String STAT_PREFIX = "stat";
	private Pattern dotSplitPattern=Pattern.compile("\\.");
	private Pattern commaSplitPattern=Pattern.compile("\\s*\\,\\s*");
	private String path="txt/stat-cfg.txt";
	private Map<String, BaseStatMeta> statMetas;
	private List<KeyValuePair<String, String>> statNameList;
	
	@PostConstruct
	public void init() throws Exception {
		List<Entry<String, String>> keyValueList = TxtFileUtil.readKeyValue(StatMetaContainer.class.getClassLoader().getResource(path));
		statMetas = new HashMap<String, BaseStatMeta>();
		for (Entry<String, String> entry : keyValueList) {
			String key = entry.getKey();
			String[] name = dotSplitPattern.split(key);
			String value = entry.getValue().trim();
			if(STAT_PREFIX.equals(name[0])) {
				if(name.length==1) {
					throw new IllegalArgumentException("unknown key:" + key);
				}
				String metaName=name[1];
				if(name.length==2) {
					BaseStatMeta statMeta = new BaseStatMeta();
					statMeta.setName(metaName);
					statMeta.setDisName(value);
					BaseStatMeta old = statMetas.put(metaName, statMeta);
					if(old!=null) {
						throw new IllegalArgumentException("double key:" + key);
					}
				} else {
					BaseStatMeta statMeta = statMetas.get(metaName);
					if(statMeta==null) {
						throw new IllegalArgumentException("baseMeta hasn't been inited:" + key);
					}
					if("sql".equals(name[2])) {
						statMeta.setSqlmap(value);
					} else if("extend".equals(name[2])) {
						if(name.length==3) {
							Class<?> extendClass = Class.forName(value);
							statMeta.setExtendMeta((ExtendStatMeta) extendClass.newInstance());
						} else {
							ExtendStatMeta extendMeta = statMeta.getExtendMeta();
							if(extendMeta==null) {
								throw new IllegalArgumentException("extendMeta hasn't been inited:" + key);
							}
							StringBuilder sb = new StringBuilder();
							for (int i=3; i < name.length; i++) {
								if(i!=3) {
									sb.append('.');
								}
								sb.append(name[i]);
							}
							String prop = sb.toString();
							PropertyUtil.setValue(extendMeta, prop, value);//TODO type convert
						}
					} else if("columns".equals(name[2])) {
						String[] words = commaSplitPattern.split(value);
						if(words.length%2==1) {
							throw new IllegalArgumentException("columns are not paired:"+key);
						}
						List<String> columnNames=new ArrayList<String>(words.length/2);
						List<String> columnDisNames=new ArrayList<String>(words.length/2);
						String col=null;
						for (String word : words) {
							if(col==null) {
								col = word;
							} else {
								columnNames.add(col);
								columnDisNames.add(word);
								col = null;
							}
						}
						statMeta.setColumnNames(columnNames);
						statMeta.setColumnDisNames(columnDisNames);
					} else {
						throw new IllegalArgumentException("unknown setting:" + key);
					}
				}
			} else {
				throw new IllegalArgumentException("unknown prefix:"+name[0]);
			}
		}
		statNameList = new ArrayList<KeyValuePair<String, String>>(statMetas.size());
		for (Entry<String, BaseStatMeta> entry : statMetas.entrySet()) {
			BaseStatMeta statMeta = entry.getValue();
			boolean check = statMeta.check();
			if(check==false) {
				throw new IllegalArgumentException("property missing for statMeta:"+entry.getKey());
			}
			statNameList.add(new KeyValuePair<String, String>(entry.getKey(), entry.getValue().getDisName()));
		}
	}

	public Map<String, BaseStatMeta> getStatMetas() {
		return statMetas;
	}

	public List<KeyValuePair<String, String>> getStatNameList() {
		return statNameList;
	}

}
