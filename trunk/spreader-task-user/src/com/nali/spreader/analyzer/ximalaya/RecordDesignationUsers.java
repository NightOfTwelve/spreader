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
	private RecordUsersConfig config;
	@AutowireProductLine
	private TaskProduceLine<RecordUsersConfig> recordXimalayaUsers;

	@Override
	public String work() {
		recordXimalayaUsers.send(config);
		return null;
	}

	@Override
	public void init(RecordUsersConfig config) {
		this.config = config;
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
