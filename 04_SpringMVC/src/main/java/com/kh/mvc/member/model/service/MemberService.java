package com.kh.mvc.member.model.service;

import com.kh.mvc.member.model.vo.Member;

public interface MemberService {

	Member login(String id, String password);

	int save(Member member);

	Boolean isDuplicateID(String userId);

	
}
