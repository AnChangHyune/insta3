package com.sbs.untactTeacher.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.untactTeacher.dto.Article;
import com.sbs.untactTeacher.dto.Board;
import com.sbs.untactTeacher.service.ArticleService;
import com.sbs.untactTeacher.util.Util;

@Controller
public class MpaUsrHomeController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/")
	public String showMainRoot() {

		return "redirect:/mpaUsr/home/main";
	}

	@RequestMapping("/mpaUsr/home/main")
	public String showMain(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId,
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
		int itemsCountInAPage = 5;
		// 총 페이지 수
		int totalPage = (int) Math.ceil(totalItemsCount / (double) itemsCountInAPage);

		// 현재 페이지(임시)
		req.setAttribute("page", page);
		req.setAttribute("totalPage", totalPage);

		List<Article> articles = articleService.getForPrintArticles(boardId, searchKeywordType, searchKeyword,
				itemsCountInAPage, page);

		req.setAttribute("articles", articles);
		return "mpaUsr/home/main";
	}

}