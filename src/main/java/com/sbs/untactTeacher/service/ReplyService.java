package com.sbs.untactTeacher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.untactTeacher.dao.ReplyDao;
import com.sbs.untactTeacher.dto.Article;
import com.sbs.untactTeacher.dto.Reply;
import com.sbs.untactTeacher.dto.ResultData;
import com.sbs.untactTeacher.util.Util;

@Service
public class ReplyService {
	@Autowired
    private ReplyDao replyDao;

    public ResultData write(String relTypeCode, int relId, int memberId, String body) {
        replyDao.write(relTypeCode, relId, memberId, body);
        int id = replyDao.getLastInsertId();

        return new ResultData("S-1", "댓글이 작성되었습니다.", "id", id);
    }

	public List<Reply> getForPrintRepliesByRelTypeCodeAndRelId(String relTypeCode, int relId) {
		return replyDao.getForPrintRepliesByRelTypeCodeAndRelId(relTypeCode, relId);
	}

	public ResultData deleteReplyById(int id) {
		Reply reply = getReplyById(id);


		replyDao.deleteReplyById(id);

		return new ResultData("S-1", id + "번 댓글이 삭제되었습니다.", "id", id);
	}

	private Reply getReplyById(int id) {
		return replyDao.getReplyById(id);
	}
}
