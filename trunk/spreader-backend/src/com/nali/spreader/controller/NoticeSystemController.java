package com.nali.spreader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.dto.NoticeAtWeiboView;
import com.nali.spreader.dto.NoticeCommentView;
import com.nali.spreader.dto.NoticeQueryParams;
import com.nali.spreader.dto.NoticeView;
import com.nali.spreader.service.INoticeService;

/**
 * 消息系统管理CTRL
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/notice")
public class NoticeSystemController extends BaseController {
	@Autowired
	private INoticeService noticeService;

	@Override
	public String init() {
		return "/show/main/NoticeSystemShow";
	}

	/**
	 * 消息分页列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/noticelist")
	public String queryNotice(NoticeQueryParams param) {
		Integer start = param.getStart();
		Integer limit = param.getLimit();
		Limit lit = this.initLimit(start, limit);
		param.setLit(lit);
		PageResult<NoticeView> data = this.noticeService.queryNoticePageResult(param);
		return this.write(data);
	}

	/**
	 * 查询一个回复评论
	 * 
	 * @param replayId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/replaycomment")
	public String queryNoticeCommentView(Long replayId) {
		NoticeCommentView view = this.noticeService.queryNoticeComment(replayId);
		return this.write(view);
	}

	/**
	 * 查询@到的微博
	 * 
	 * @param noticeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/atweibo")
	public String queryNoticeAtWeiboView(Long noticeId) {
		NoticeAtWeiboView view = this.noticeService.queryNoticeAtWeiboView(noticeId);
		return this.write(view);
	}
}
