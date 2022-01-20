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
	public Member findMemberById(String id) {
		
		return mapper.findMemberById(id);
	}
	
	
	@Override
	public Member login(String id, String password) {
		Member member = null;
		
//		member = dao.findMemberById(session, id);
		member = this.findMemberById(id);
//		System.out.println(mapper.findAll());
		
		// ▼ 암호화할 때 마다 진짜 사용자가 입력한 비밀번호가 달라지는지 찍어서 확인해보자
//		System.out.println(passwordEncoder.encode(password));
		// ▼ 회원가입할 떄 암호화되서 저장된 비번 가져와보기
//		System.out.println(member.getPassword());
		// ▼ matches("사용자가입력한패스워드", "암호화된패스워드"); -> true나 false로 나옴
//		System.out.println(passwordEncoder.matches(password, member.getPassword()));
		
		// ▼ 사용자가 입력한 passsword랑 실제 조회한 member의 password가 같은지 체크중
//		if(member != null && member.getPassword().equals(password)) {
		// ▼ equal(암호화된 패스워드)랑 member에 저장된 패스워드가 같냐? => 당연히 일치하지않음
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
			result = mapper.updateMember(member);
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
		
		return this.findMemberById(id) != null;
	}


	@Override
	public int delete(int no) {
		
		return mapper.deleteMember(no);
	}


	
	
	
	
	
	

}
