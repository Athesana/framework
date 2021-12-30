package com.kh.mybatis.member.model.dao;

import org.apache.ibatis.session.SqlSession;

public class MemberDao {

	public int getMemberCount(SqlSession session) {
		
		return session.selectOne("memberMapper.selectCount");
	}

}
