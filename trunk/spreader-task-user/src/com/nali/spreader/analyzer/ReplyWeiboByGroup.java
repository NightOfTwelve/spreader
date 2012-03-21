package com.nali.spreader.analyzer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.Range;
import com.nali.spreader.config.RobotReplyListDto;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.group.config.UserGroupMetaInfo;
import com.nali.spreader.model.ReplyDto;
import com.nali.spreader.model.RobotContent;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IRobotContentService;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.util.random.WeightRandomer;

@Component
@ClassDescription("分组·回复微博")
public class ReplyWeiboByGroup extends UserGroupExtendedBeanImpl implements RegularAnalyzer,Configable<RobotReplyListDto> {
	private static final String FILE_REPLY_WORDS = "txt/reply.txt";
	private static Logger logger = Logger.getLogger(ReplyWeiboByGroup.class);
	static Randomer<String> defaultReplyWords;
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@Autowired
	private IContentService contentService;
	@Autowired
	private IRobotContentService robotContentService;
	@AutowireProductLine
	private TaskProduceLine<ReplyDto> replyWeibo;
	
	private Randomer<String> replyWords;
	private Randomer<Boolean> needForward;
	private NumberRandomer random;
	private List<String> urlList;
	
	public ReplyWeiboByGroup() {
		super(UserGroupMetaInfo.FROM_GROUP + "回复微博");
	}
	
	static {
		try {
			Set<String> datas = TxtFileUtil.read(ReplyWeiboByGroup.class.getClassLoader().getResource(FILE_REPLY_WORDS));
			defaultReplyWords = new AvgRandomer<String>(datas);
		} catch (IOException e) {
			logger.error(e, e);
		}
	}
	
	@PostConstruct
	public void init() throws IOException {
	}
	
	@Override
	public void work() {
		Long fromGroup = this.getFromUserGroup();
		for (String url : urlList) {
			Content content = contentService.parseUrl(url);
			if(content==null) {
				logger.error("illegal url:" + url);
				continue;
			}
			List<Long> existRobotIds = robotContentService.findRelatedRobotId(content.getId(), null);
			Iterator<User> robots = userGroupFacadeService.queryLimitedRandomGrouppedUser(fromGroup, random.get(), existRobotIds);
			while (robots.hasNext()) {
				User robot = robots.next();
				Boolean needForwardFlag = needForward.get();
				robotContentService.save(robot.getId(), content.getId(), RobotContent.TYPE_REPLY);
				if(needForwardFlag==true) {
					robotContentService.save(robot.getId(), content.getId(), RobotContent.TYPE_FORWARD);
				}
				ReplyDto dto = new ReplyDto(robot.getId(), content.getId(), replyWords.get(), needForwardFlag);
				replyWeibo.send(dto);
			}
		}
	}

	@Override
	public void init(RobotReplyListDto config) {
		Range<Integer> range = config.getCount();
		List<String> urlList = config.getUrlList();
		if (range != null && range.checkNotNull() && urlList!=null && !urlList.isEmpty()) {
			random = new NumberRandomer(range.getGte(), range.getLte()+1);
			this.urlList = urlList;
		} else {
			throw new IllegalArgumentException("输入值不完整");
		}
		Integer needForwardPercent = config.getNeedForwardPercent();
		if(needForwardPercent==null) {
			needForwardPercent=0;
		} else {
			needForwardPercent=Math.max(0, needForwardPercent);
			needForwardPercent=Math.min(100, needForwardPercent);
		}
		if(config.getWords()==null) {
			replyWords = defaultReplyWords;
		} else {
			List<String> words = config.getWords();
			replyWords = new AvgRandomer<String>(words);
		}
		WeightRandomer<Boolean> needForward = new WeightRandomer<Boolean>();
		needForward.add(true, needForwardPercent);
		needForward.add(false, 100 - needForwardPercent);
		this.needForward = needForward;
	}

}
