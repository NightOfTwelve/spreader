package com.nali.spreader.workshop.weibo;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.words.Txt;

public class SupportedEmails {
	private static final String FILE_ACTION_ID = "txt/email.txt";
	private static final String COMPANY_EMAIL_FILE = "txt/company-email.txt";
	private static Map<String, Long> emailActionIds;
	public static final Set<String> COMPANY_EMAILS;

	static {
		try {
			initEmailISPsRandomer();
			COMPANY_EMAILS=TxtFileUtil.read(Txt.getUrl(COMPANY_EMAIL_FILE));
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	private static void initEmailISPsRandomer() throws IOException {
		Map<String, String> emailActionIdStrings = TxtFileUtil.readKeyValueMap(Txt.getUrl(FILE_ACTION_ID));
		emailActionIds = CollectionUtils.newHashMap(emailActionIdStrings.size());
		for (Entry<String, String> entry : emailActionIdStrings.entrySet()) {
			emailActionIds.put(entry.getKey(), Long.valueOf(entry.getValue()));
		}
	}
	
	public static Long getActionId(String emailCode) {
		return emailActionIds.get(emailCode);
	}
	
	public static String listAllEmailCodes() {
		StringBuilder sb = new StringBuilder();
		for(String e : emailActionIds.keySet()) {
			sb.append(e).append(',');
		}
		if(sb.length()>0) {
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
