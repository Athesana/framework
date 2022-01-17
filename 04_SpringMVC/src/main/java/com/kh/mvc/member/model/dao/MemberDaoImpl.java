package com.kh.mvc.member.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.mvc.member.model.vo.Member;

@Repository
public class MemberDaoImpl implements MemberDao {

	@Override
	public Member findMemberById(SqlSession session, String id) {

		return session.selectOne("memberMapper.selectMemberById", id);
	}

}
