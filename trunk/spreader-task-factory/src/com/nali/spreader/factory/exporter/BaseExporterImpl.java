package com.nali.spreader.factory.exporter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.nali.spreader.factory.base.TaskMeta;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskContext;

public abstract class BaseExporterImpl<TM extends TaskMeta> implements Exporter<TM> {
	private static final int CONTEXT_ADDITIONAL_EXPIRED_DAYS = 2;
	private static final int DEFAULT_PRIORITY = 0;
	private static Logger logger = Logger.getLogger(BaseExporterImpl.class);
	private static final ContentSerializer DEFAULT_CONTENT_SERIALIZER=new JacksonSerializer();
	private final TM taskMeta;
	private ContentSerializer contentSerializer = DEFAULT_CONTENT_SERIALIZER;
	private IResultInfo resultInfo;
	private int basePriority=DEFAULT_PRIORITY;
	private Date startTime;
	private Date expiredTime;
	private Long uid;
	private Map<String, Object> contents;
	private Map<String, Object> context;
	
	private final Map<String, Boolean> systemPropertyMap;
	
	public BaseExporterImpl(TM taskMeta, Map<String, Boolean> systemPropertyMap, IResultInfo resultInfo) {
		this.taskMeta = taskMeta;
		this.systemPropertyMap = systemPropertyMap;
		this.resultInfo = resultInfo;
	}

	protected TM getTaskMeta() {
		return taskMeta;
	}

	@Override
	public void setTimes(Date startTime, Date expiredTime) {
		this.startTime = startTime;
		setExpiredTime(expiredTime);
		
	}

	@Override
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	@Override
	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Override
	public void setSystemProperty(String key, Object value) {
		if(context==null) {
			context = new HashMap<String, Object>();
		}
		context.put(key, value);
	}

	@Override
	public void setClientProperty(String key, Object value) {
		if(contents==null) {
			contents = new HashMap<String, Object>();
		}
		contents.put(key, value);
	}

	@Override
	public void setProperty(String key, Object value) {
		Boolean propertySetting=null;
		if(systemPropertyMap!=null) {
			propertySetting = systemPropertyMap.get(key);
		}
		if(propertySetting!=null) {
			setSystemProperty(key, value);
		}
		if(propertySetting==null || propertySetting==false) {
			setClientProperty(key, value);
		}
	}

	@Override
	public void setBasePriority(int basePriority) {
		this.basePriority = basePriority;
	}
	
	final public void reset() {
		startTime = null;
		expiredTime = null;
		uid = null;
		contents = null;
		context = null;
		resetOther();
	}
	
	protected void resetOther() {
	}

	@Override
	public void send() {
		try {
			checkNull(uid, "uid");
			checkNull(expiredTime, "expiredTime");
			String contentString = contentSerializer.serialize(contents);
			Task task = new Task();
			task.setBid(getBid());
			task.setCreateTime(new Date());
			task.setStartTime(startTime==null?task.getCreateTime():startTime);
			task.setTaskCode(taskMeta.getCode());
			task.setUid(uid);
			task.setResultId(resultInfo.getResultId());
			task.setTraceLink(resultInfo.getTraceLink());
			Long taskId = save(task);
			if(context!=null) {
				if(systemPropertyMap==null) {
					throw new IllegalArgumentException("this exporter doesnot support context setting," +
							" make sure the producer's taskMeta have provide a contextMeta");
				}
				saveContext(taskId, new TaskContext(uid, context), DateUtils.addDays(expiredTime, CONTEXT_ADDITIONAL_EXPIRED_DAYS));
			}
			ClientTask clientTask = new ClientTask();
			clientTask.setActionId(getActionId());
			clientTask.setTaskType(taskMeta.getTaskType());
			clientTask.setTaskCode(taskMeta.getCode());
			clientTask.setBasePriority(basePriority);
			clientTask.setContents(contentString);
			clientTask.setStartTime(startTime);
			clientTask.setExpireTime(expiredTime);
			clientTask.setUid(uid);
			clientTask.setId(taskId);
			send(clientTask);
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			reset();
		}
	}
	
	@Override
	public void send(Long uid, Date expiredTime) {
		setUid(uid);
		setExpiredTime(expiredTime);
		send();
	}
	
	private void checkNull(Object obj, String name) {
		if(obj==null) {
			throw new IllegalArgumentException(name + " must be setted");
		}
	}

	protected Long getBid() {
		return 0L;
	}

	protected abstract void send(ClientTask clientTask) throws IOException;

	protected abstract Long save(Task task);

	protected abstract void saveContext(Long taskId, TaskContext taskContext, Date expiredTime);
	
	protected abstract Long getActionId();

}