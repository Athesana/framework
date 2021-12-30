package com.kh.mybatis.member.model.service;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.common.SqlSessionTemplate;
import com.kh.mybatis.member.model.dao.MemberDao;

public class MemberService {

	private MemberDao dao = new MemberDao();
	
	public int getMemberCount() {
		
		int count = 0;
		SqlSession session = SqlSessionTemplate.getSession();
		
		count = dao.getMemberCount(session);
		
		session.close();
				
		return count;
	}

}
