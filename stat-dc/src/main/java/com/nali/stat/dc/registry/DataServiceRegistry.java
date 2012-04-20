package com.nali.stat.dc.registry;

import com.nali.stat.dc.data.StatService;

public interface DataServiceRegistry {
	
	StatService lookup(String name);
	
	StatService[] getServices();
	
	StatService[] lookup(String[] names);
	
//	ServiceGroup lookupGroup(String groupName);
}
