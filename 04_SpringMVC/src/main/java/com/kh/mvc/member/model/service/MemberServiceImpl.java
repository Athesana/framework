package com.kh.mvc.member.model.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.mvc.member.model.dao.MemberMapper;
import com.kh.mvc.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberMapper mapper;
	
//	@Autowired
//	private SqlSession session;
	
	@Override
	public Member login(String id, String password) {
		Member member = null;
		
//		member = dao.findMemberById(session, id);
		member = mapper.findMemberById(id);
//		System.out.println(mapper.findAll());
		
		if(member != null && member.getPassword().equals(password)) {
			
			return member;
			
		} else {
			
			return null;
			
		}
				
	}

}
