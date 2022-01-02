package com.kh.mybatis.member.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.member.model.vo.Member;

public class MemberDao {

	public int getMemberCount(SqlSession session) {
		
		/*
		 * SqlSession 객체가 제공하는 메소드를 통해서 SQL을 실행 시킨다.
		 * 객체 한 개를 조회하기 위해서 SqlSession 객체의 selectOne() 메소드를 사용한다.
		 * 파라미터는 최대 2개를 받을 수 있다. (2개 이상일 경우는 대부분 Map, List, 배열 등 매개 값으로 전달한다.)
		 * 	- 첫 번째 매개 값은 쿼리문이 존재하는 경로이다. ("Mapper의 namespace.쿼리문아이디"), config에 등록된 매퍼 파일에서 해당하는 쿼리문을 찾아서 실행한다.
		 * 	- 두 번째 매개 값은 쿼리문에서 사용될 파라미터 객체이다.
		 */
		
		return session.selectOne("memberMapper.selectCount");
	}

	public List<Member> findAll(SqlSession session) {
		// 값이 조회되면 LIST에 값을 담아서 반환, 없으면 빈 LIST 객체를 반환(무조건 테스트는 통과하게되어있다.)
		return session.selectList("memberMapper.selectAll");
	}

	public Member findMemberById(SqlSession session, String id) {
		// 지정된 객체의 타입으로 반환되고, 조회되는 값이 없으면 NULL을 반환		
		return session.selectOne("memberMapper.selectMemberById", id);
	}

	public int insertMember(SqlSession session, Member member) {
				
		return session.insert("memberMapper.insertMember", member);
	}

}
