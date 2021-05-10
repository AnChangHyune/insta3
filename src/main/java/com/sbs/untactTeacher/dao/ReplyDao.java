package com.sbs.untactTeacher.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untactTeacher.dto.Reply;

@Mapper
public interface ReplyDao {
	void write(@Param("relTypeCode") String relTypeCode, @Param("relId") int relId, @Param("memberId") int memberId,
			@Param("body") String body);

	int getLastInsertId();

	List<Reply> getForPrintRepliesByRelTypeCodeAndRelId(
			@Param("relTypeCode") String relTypeCode, 
			@Param("relId") int relId);

	void delete(
			@Param("id") int id);

	Reply getReplyById(@Param("id")  int id);

	void modify(@Param("id") int id, @Param("body") String body);

}
