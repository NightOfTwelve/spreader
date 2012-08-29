package com.nali.spreader.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.factory.config.RegularJobResultDto;
import com.nali.spreader.factory.regular.RegularScheduler;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskStatusCountDto;
import com.nali.spreader.service.IRegularJobExecutionService;

@Controller
@RequestMapping(value = "/taskresult")
public class TaskJobResultManageController extends BaseController {
	private final static Logger logger = Logger.getLogger(TaskJobResultManageController.class);
	@Autowired
	private IRegularJobExecutionService execuService;
	@Autowired
	private RegularScheduler schedulerService;
	// 简单分组
	private final static String GROUP_TYPE_SIMPLE = "simple";
	// 复杂分组
	private final static String GROUP_TYPE_COMPLEX = "complex";
	private final static int[] ALL_TASK_STATUS = { 0, 1, 2 };

	/**
	 * 查询执行结果列表
	 * 
	 * @param jobId
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryjobresult")
	public String seachJobResult(Long jobId, Long gid, String groupType, Integer start,
			Integer limit) {
		Limit lit = this.initLimit(start, limit);
		// 复杂分组
		if (GROUP_TYPE_COMPLEX.equals(groupType)) {
			PageResult<RegularJobResultDto> result = this.execuService.findRegularJobResultByJobId(
					jobId, lit);
			return this.write(result);
		}
		// 简单分组
		else if (GROUP_TYPE_SIMPLE.equals(groupType)) {
			if (gid != null) {
				jobId = this.schedulerService.findRegularJobIdBySimpleGroupId(gid);
			}
			if (jobId != null) {
				PageResult<RegularJobResultDto> result = this.execuService
						.findRegularJobResultByJobId(jobId, lit);
				return this.write(result);
			} else {
				logger.error("通过分组ID获取RegularJob失败,gid=" + gid);
			}
		}
		return null;
	}

	/**
	 * 统计任务执行情况
	 * 
	 * @param resultId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resultdtl")
	public String seachResultDtl(Long resultId) {
		List<Task> list = Collections.emptyList();
		if (resultId != null) {
			list = this.execuService.findTaskInfoByResultId(resultId);
		}
		Map<String, List<Task>> m = CollectionUtils.newHashMap(1);
		m.put("list", list);
		return this.write(m);
	}

	/**
	 * 统计任务状态
	 * 
	 * @param resultId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/statuscount")
	public String queryStatusCount(Long resultId) {
		Map<String, List<TaskStatusCountDto>> m = CollectionUtils.newHashMap(1);
		if (resultId == null) {
			return null;
		} else {
			List<TaskStatusCountDto> list = this.execuService.queryTaskStatusCount(resultId);
			if (CollectionUtils.isEmpty(list)) {
				list = this.getAddTaskStatus(ArrayUtils.EMPTY_INT_ARRAY);
				m.put("list", list);
			} else {
				List<TaskStatusCountDto> tmp = new ArrayList<TaskStatusCountDto>();
				tmp.addAll(list);
				int[] array = ArrayUtils.EMPTY_INT_ARRAY;
				for (TaskStatusCountDto t : list) {
					array = ArrayUtils.add(array, t.getStatus());
				}
				tmp.addAll(this.getAddTaskStatus(array));
				m.put("list", tmp);
			}
			return this.write(m);
		}
	}

	@Override
	public String init() {
		return null;
	}

	/**
	 * 补全其它状态
	 * 
	 * @param array
	 * @return
	 */
	private List<TaskStatusCountDto> getAddTaskStatus(int[] array) {
		List<TaskStatusCountDto> list = new ArrayList<TaskStatusCountDto>();
		if (ArrayUtils.isEmpty(array)) {
			for (int stat : ALL_TASK_STATUS) {
				TaskStatusCountDto dto = new TaskStatusCountDto();
				dto.setCnt(0);
				dto.setStatus(stat);
				list.add(dto);
			}
			return list;
		} else {
			for (int i : ALL_TASK_STATUS) {
				if (!ArrayUtils.contains(array, i)) {
					TaskStatusCountDto dto = new TaskStatusCountDto();
					dto.setCnt(0);
					dto.setStatus(i);
					list.add(dto);
				}
			}
		}
		return list;
	}
}