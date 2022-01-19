package com.kh.mvc.member.model.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.mvc.member.model.dao.MemberMapper;
import com.kh.mvc.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
//	@Autowired
//	private SqlSession session;
	
	@Override
	public Member login(String id, String password) {
		Member member = null;
		
//		member = dao.findMemberById(session, id);
		member = mapper.findMemberById(id);
//		System.out.println(mapper.findAll());
		
//		System.out.println(passwordEncoder.encode(password));
//		System.out.println(member.getPassword());
//		System.out.println(passwordEncoder.matches(password, member.getPassword()));
		
//		if(member != null && member.getPassword().equals(password)) {
//		if(member != null && member.getPassword().equals(passwordEncoder.encode(password))) {	
//			return member;
//		} else {
//			return null;
//		}
		
		return member != null && passwordEncoder.matches(password, member.getPassword()) ? member : null;
				
	}

	@Override
	@Transactional
	public int save(Member member) {
		int result = 0;
		
		if(member.getNo() != 0) {
			// update
			
		} else {
			// insert
			member.setPassword(passwordEncoder.encode(member.getPassword()));
			
			result = mapper.insertMember(member);
		}
		
//		if(true) {
//			throw new RuntimeException();
//		}
		
		return result;
	}

	@Override
	public Boolean isDuplicateID(String id) {
		
		return mapper.findMemberById(id) != null;
	}
	
	
	
	
	
	
	

}
