package com.kh.mvc.member.model.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.mvc.member.model.dao.MemberDao;
import com.kh.mvc.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDao dao;
	
	@Autowired
	private SqlSession session;
	
	@Override
	public Member login(String id, String password) {
		Member member = null;
		
		member = dao.findMemberById(session, id);
		
		return member;
	}

}
