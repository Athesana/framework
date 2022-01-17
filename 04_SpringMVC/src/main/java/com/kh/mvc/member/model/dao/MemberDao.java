package com.kh.mvc.member.model.dao;

import org.apache.ibatis.session.SqlSession;

import com.kh.mvc.member.model.vo.Member;

public interface MemberDao {

	Member findMemberById(SqlSession session, String id);

}
