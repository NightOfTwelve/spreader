package com.nali.spreader.dao;

import java.util.Date;
import java.util.List;

import com.nali.common.model.Limit;
import com.nali.spreader.dto.TaskResultDto;
import com.nali.spreader.model.ActiveTaskDto;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.TaskBatch;
import com.nali.spreader.model.UserTaskCount;
import com.nali.spreader.model.UserTaskCount.QueryDto;

public interface ITaskDao {
	@Deprecated
	List<UserTaskCount> countUserTask();

	Long insertTaskBatch(TaskBatch batch);

	List<ClientTask> assignTasksSelect(UserTaskCount dto);

	int expireTasks(Date now);

	List<UserTaskCount> countTask(QueryDto queryDto);

	int activeTasks(ActiveTaskDto dto);

	int refreshPriority(String priorityExpression);

	List<TaskResultDto> selectTaskResult(Long resultId, int status, Limit limit);
	
	int countTaskResultDto(Long resultId, int status);
}