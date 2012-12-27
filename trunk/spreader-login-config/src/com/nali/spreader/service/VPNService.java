package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.collection.CycleIterator;
import com.nali.spreader.util.web.IPUtil;

@Service
public class VPNService implements IVPNService {
	private static Logger logger = Logger.getLogger(VPNService.class);
	private CycleIterator<IpCollection> pools;
	
	{
		try {
			Set<String> vpnTexts = TxtFileUtil.read(VPNService.class.getResource("/vpn.txt"));
			if(vpnTexts.size()<2) {
				throw new IllegalArgumentException("Invalid vpns' size:"+vpnTexts.size());
			}
			List<IpCollection> cols=new LinkedList<IpCollection>();
			int poolSize=vpnTexts.size()/2+vpnTexts.size()%2;
			List<Integer> vpns = null;
			for (String vpn : vpnTexts) {
				if(vpns==null) {
					vpns = new ArrayList<Integer>(poolSize);
				}
				vpns.add(IPUtil.ip(vpn));
				if(vpns.size()==poolSize) {
					cols.add(new IpCollection(vpns));
					vpns=null;
				}
			}
			if(vpns!=null) {
				cols.add(new IpCollection(vpns));
			}
			pools=new CycleIterator<IpCollection>(cols);
			pools.next();
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	public String getVpn() {
		Integer ip;
		synchronized (pools) {
			IpCollection vpns = pools.getCurrent();
			ip = vpns.get();
			if(ip==null) {
				vpns.reset();
				vpns = pools.next();
				logger.debug("to next pool");
				ip = vpns.get();
			}
		}
		return IPUtil.text(ip);
	}
	
	static class IpCollection {
		private List<Integer> ips;
		private int idx;
		
		public IpCollection(List<Integer> ips) {
			super();
			this.ips = ips;
			reset();
		}
		
		public Integer get() {
			if(idx>=0) {
				return ips.get(idx--);
			} else {
				return null;
			}
		}
		
		public void reset() {
			Collections.shuffle(ips);
			idx=ips.size()-1;
		}
	}

}
