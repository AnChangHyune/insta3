package com.sbs.untactTeacher.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.sbs.untactTeacher.dto.Article;
import com.sbs.untactTeacher.dto.Board;
import com.sbs.untactTeacher.dto.Reply;
import com.sbs.untactTeacher.dto.ResultData;
import com.sbs.untactTeacher.dto.Rq;
import com.sbs.untactTeacher.service.ArticleService;
import com.sbs.untactTeacher.service.GenFileService;
import com.sbs.untactTeacher.service.ReplyService;
import com.sbs.untactTeacher.util.Util;

@Controller
public class MpaAdmArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private GenFileService genFileService;
	
	@RequestMapping("/mpaAdm/article/detail")
	public String showDetail(HttpServletRequest req, int id, String body) {
		Article article = articleService.getForPrintArticleById(id);

		List<Reply> replies = replyService.getForPrintRepliesByRelTypeCodeAndRelId("article", id);
		if (article == null) {
			return Util.msgAndBack(req, id + "번 게시물은 존재하지 않습니다.");
		}

		Board board = articleService.getBoardById(article.getBoardId());

		req.setAttribute("replies", replies);
		req.setAttribute("board", board);
		req.setAttribute("article", article);

		return "mpaAdm/article/detail";
	}

	@RequestMapping("/mpaAdm/article/write")
	public String showWrite(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId) {
		Board board = articleService.getBoardById(boardId);

		if (board == null) {
			return Util.msgAndBack(req, boardId + "번 게시물은 존재하지 않습니다.");
		}

		req.setAttribute("board", board);

		return "mpaAdm/article/write";
	}

	@RequestMapping("/mpaAdm/article/doWrite")
	public String doWrite(HttpServletRequest req, int boardId, String title, String body,MultipartRequest multipartRequest) {

		if (Util.isEmpty(title)) {
			return Util.msgAndBack(req, "제목을 입력해주세요.");
		}

		if (Util.isEmpty(body)) {
			return Util.msgAndBack(req, "내용을 입력해주세요.");
		}

		Rq rq = (Rq) req.getAttribute("rq");

		int memberId = rq.getLoginedMemberId();

		ResultData WriteRd = articleService.writeArticle(boardId, memberId, title, body);

		if (WriteRd.isFail()) {
			return Util.msgAndBack(req, WriteRd.getMsg());
		}

		int newArticleId = (int) WriteRd.getBody().get("id");

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);

			if (multipartFile.isEmpty() == false) {
				genFileService.save(multipartFile, newArticleId);
			}
		}

		String replaceUri = "detail?id=" + WriteRd.getBody().get("id");

		return Util.msgAndReplace(req, WriteRd.getMsg(), replaceUri);
	}

	@RequestMapping("/mpaAdm/article/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String title, String body) {

		if (Util.isEmpty(id)) {
			return new ResultData("F-1", "번호를 입력해주세요.");
		}

		if (Util.isEmpty(title)) {
			return new ResultData("F-2", "제목을 입력해주세요.");
		}

		if (Util.isEmpty(body)) {
			return new ResultData("F-3", "내용을 입력해주세요.");
		}

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return new ResultData("F-4", "존재하지 않는 게시물 번호입니다.");
		}

		return articleService.modifyArticle(id, title, body);
	}

	@RequestMapping("/mpaAdm/article/doDelete")
	public String doDelete(HttpServletRequest req, Integer id) {
		if (Util.isEmpty(id)) {
			return Util.msgAndBack(req, "id를 입력해주세요.");
		}

		ResultData rd = articleService.deleteArticleById(id);

		if (rd.isFail()) {
			return Util.msgAndBack(req, rd.getMsg());
		}

		String redirectUri = "../article/list?boardId=" + rd.getBody().get("boardId");

		return Util.msgAndReplace(req, rd.getMsg(), redirectUri);
	}

	@RequestMapping("/mpaAdm/article/list")
	public String showList(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId,
			String searchKeywordType, String searchKeyword, @RequestParam(defaultValue = "1") int page) {
		Board board = articleService.getBoardById(boardId);

		if (Util.isEmpty(searchKeywordType)) {
			searchKeywordType = "titleAndBody";
		}

		if (board == null) {
			return Util.msgAndBack(req, boardId + "번 게시판이 존재하지 않습니다.");
		}

		req.setAttribute("board", board);

		int totalItemsCount = articleService.getArticlesTotalCount(boardId, searchKeywordType, searchKeyword);

		if (searchKeyword == null || searchKeyword.trim().length() == 0) {

		}

		req.setAttribute("totalItemsCount", totalItemsCount);

		// 한 페이지에 보여줄 수 있는 게시물 최대 개수
		int itemsCountInAPage = 20;
		// 총 페이지 수
		int totalPage = (int) Math.ceil(totalItemsCount / (double) itemsCountInAPage);

		// 현재 페이지(임시)
		req.setAttribute("page", page);
		req.setAttribute("totalPage", totalPage);

		List<Article> articles = articleService.getForPrintArticles(boardId, searchKeywordType, searchKeyword,
				itemsCountInAPage, page);

		req.setAttribute("articles", articles);

		return "mpaAdm/article/list";
	}

	@RequestMapping("/mpaAdm/article/getArticle")
	@ResponseBody
	public ResultData getArticle(Integer id) {
		if (Util.isEmpty(id)) {
			return new ResultData("F-1", "번호를 입력해주세요.");
		}

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return new ResultData("F-1", id + "번 글은 존재하지 않습니다.", "id", id);
		}

		return new ResultData("S-1", article.getId() + "번 글 입니다.", "article", article);
	}
}
