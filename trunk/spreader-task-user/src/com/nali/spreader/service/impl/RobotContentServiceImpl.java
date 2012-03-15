package com.nali.spreader.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudContentDao;
import com.nali.spreader.dao.ICrudRobotContentDao;
import com.nali.spreader.dao.IRobotContentDao;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.ContentExample;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.model.RobotContent;
import com.nali.spreader.model.RobotContentExample;
import com.nali.spreader.model.RobotContentExample.Criteria;
import com.nali.spreader.service.IRobotContentService;

/**
 * 排重服务
 * 
 * @author xiefei
 * 
 */
@Service
public class RobotContentServiceImpl implements IRobotContentService {

	@Autowired
	private ICrudRobotContentDao crudRobotContentDao;
	@Autowired
	private ICrudContentDao crudContentDao;
	@Autowired
	private IRobotContentDao robotContentDao;

	@Override
	public void save(Long robotId, Long contentId, Integer type, Integer status) {
		RobotContentExample robotContentExample = new RobotContentExample();
		Criteria robotContentCriteria = robotContentExample.createCriteria();
		if (robotId != null && contentId != null && type != null && status != null) {
			// 获取内容作者
			Long authorId = queryAuthorId(contentId);
			robotContentCriteria.andUidEqualTo(robotId);
			robotContentCriteria.andContentIdEqualTo(contentId);
			robotContentCriteria.andTypeEqualTo(type);
			// robotContentCriteria.andStatusEqualTo(status);
			List<RobotContent> rcList = this.crudRobotContentDao.selectByExample(robotContentExample);
			// 更新
			if (rcList.size() > 0) {
				RobotContent rc = rcList.get(0);
				rc.setAuthorId(authorId);
				rc.setStatus(status);
				rc.setUpdateTime(new Date());
				crudRobotContentDao.updateByPrimaryKey(rc);
			} else {
				// 保存
				RobotContent newrc = new RobotContent();
				newrc.setAuthorId(authorId);
				newrc.setContentId(contentId);
				newrc.setType(type);
				newrc.setUid(robotId);
				newrc.setStatus(status);
				newrc.setUpdateTime(new Date());
				crudRobotContentDao.insert(newrc);
			}
		} else {
			throw new IllegalArgumentException("获取参数为空, robotId=" + robotId + ", contentId=" + contentId + ", type=" + type
					+ ", status=" + status);
		}

	}

	@Override
	public List<Long> findRelatedRobotId(Long contentId, Integer type) {
		KeyValue<Long, Integer> params = new KeyValue<Long, Integer>(contentId, type);
		return robotContentDao.queryRobotIdByContentAndType(params);
	}

	/**
	 * 获取内容作者
	 * 
	 * @param contentId
	 * @return
	 */
	private Long queryAuthorId(Long contentId) {
		Long authorId = null;
		ContentExample contentExample = new ContentExample();
		com.nali.spreader.data.ContentExample.Criteria contentCriteria = contentExample.createCriteria();
		contentCriteria.andIdEqualTo(contentId);
		Content content = this.crudContentDao.selectByPrimaryKey(contentId);
		if (content != null) {
			authorId = content.getUid();
		}
		return authorId;
	}
}
