package com.nali.spreader.analyzer.ximalaya;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.config.desc.PropertyDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;

@Component
@ClassDescription("喜马拉雅·收录指定用户")
public class RecordDesignationUsers implements RegularAnalyzer,
		Configable<RecordDesignationUsers.RecordUsersConfig> {
	private List<String> nickNames;
	private List<Long> uids;
	@AutowireProductLine
	private TaskProduceLine<String> recordXimalayaUsersByNickName;
	@AutowireProductLine
	private TaskProduceLine<Long> recordXimalayaUsersByUid;

	@Override
	public String work() {
		int namesCount = 0;
		int uidCount = 0;
		if (nickNames != null) {
			for (String nickName : nickNames) {
				recordXimalayaUsersByNickName.send(nickName);
			}
			namesCount = nickNames.size();
		}
		if (uids != null) {
			for (Long uid : uids) {
				recordXimalayaUsersByUid.send(uid);
			}
			uidCount = uids.size();
		}
		return "通过昵称生成：" + namesCount + "条任务，通过ID生成:" + uidCount + "条记录";
	}

	@Override
	public void init(RecordUsersConfig config) {
		nickNames = config.getNickNames();
		uids = config.getUids();
	}

	public static class RecordUsersConfig implements Serializable {
		private static final long serialVersionUID = 2389888329694167402L;
		@PropertyDescription("用户昵称的列表")
		private List<String> nickNames;
		@PropertyDescription("用户ID的列表")
		private List<Long> uids;

		public List<String> getNickNames() {
			return nickNames;
		}

		public void setNickNames(List<String> nickNames) {
			this.nickNames = nickNames;
		}

		public List<Long> getUids() {
			return uids;
		}

		public void setUids(List<Long> uids) {
			this.uids = uids;
		}
	}
}
