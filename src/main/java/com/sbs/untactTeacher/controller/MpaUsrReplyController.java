package com.sbs.untactTeacher.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untactTeacher.dto.Article;
import com.sbs.untactTeacher.dto.Board;
import com.sbs.untactTeacher.dto.ResultData;
import com.sbs.untactTeacher.dto.Rq;
import com.sbs.untactTeacher.service.ArticleService;
import com.sbs.untactTeacher.service.ReplyService;
import com.sbs.untactTeacher.util.Util;

@Controller
public class MpaUsrReplyController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ReplyService replyService;
	
	@RequestMapping("/mpaUsr/reply/doDelete")
	public String doDelete(HttpServletRequest req, int id) {
		if (Util.isEmpty(id)) {
			return Util.msgAndBack(req, "id를 입력해주세요.");
		}

		ResultData rd = replyService.deleteReplyById(id);

		if (rd.isFail()) {
			return Util.msgAndBack(req, rd.getMsg());
		}

		String redirectUri = "../article/detail?id=" + rd.getBody().get("id");

		return Util.msgAndReplace(req, rd.getMsg(), redirectUri);
	}

	@RequestMapping("/mpaUsr/reply/doWrite")
	public String showWrite(HttpServletRequest req, String relTypeCode, int relId, String body, String redirectUri) {
		switch (relTypeCode) {
		case "article":
			Article article = articleService.getArticleById(relId);
			if (article == null) {
				return Util.msgAndBack(req, "해당 게시물이 존재하지 않습니다.");
			}
			break;
		default:
			return Util.msgAndBack(req, "올바르지 않은 relTypeCode 입니다.");
		}

		Rq rq = (Rq) req.getAttribute("rq");

		int memberId = rq.getLoginedMemberId();

		ResultData writeResultData = replyService.write(relTypeCode, relId, memberId, body);

		return Util.msgAndReplace(req, writeResultData.getMsg(), redirectUri);
	}
}
