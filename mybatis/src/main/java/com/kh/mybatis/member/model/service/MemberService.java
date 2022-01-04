package com.kh.mybatis.member.model.service;

import static com.kh.mybatis.common.template.SqlSessionTemplate.getSession;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.member.model.dao.MemberDao;
import com.kh.mybatis.member.model.vo.Member;

public class MemberService {

	private MemberDao dao = new MemberDao();
	
	public int getMemberCount() {
		
		int count = 0;
		SqlSession session = getSession();
		
		count = dao.getMemberCount(session);
		
		session.close();
				
		return count;
	}

	public List<Member> findAll() {
		List<Member> members = null;
		SqlSession session = getSession();
		
		members = dao.findAll(session);
		
		session.close();
		
		return members;
	}

	public Member findMemberById(String id) {
		Member member = null;
		SqlSession session = getSession();
		
		member = dao.findMemberById(session, id);
		
		session.close();
		
		return member;
	}

	public int save(Member member) {
		int result = 0;
		SqlSession session = getSession();
		
		if(member.getNo() != 0) {
			// udpate
			result = dao.updateMember(session, member);
			
		} else {
			// insert
			result = dao.insertMember(session, member);
		}
		
		if(result > 0) {
			session.commit();
		} else {
			session.rollback();
		}
		
		session.close();
		
		return result;
	}

	
	public int delete(int no) {
		int result = 0;
		SqlSession session = getSession();
		
		result = dao.delete(session, no);
		
		if(result > 0) {
			session.commit();
		} else {
			session.rollback();
		}
		
		session.close();
		
		return result;
	}

	
	
	
	
	
	
}
