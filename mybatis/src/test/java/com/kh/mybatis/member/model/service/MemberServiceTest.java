package com.kh.mybatis.member.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Member 테스트")
class MemberServiceTest {
	
	private MemberService service;
	
	@BeforeEach
	public void setup() {
		service = new MemberService();
	}
	
	@Test
	@Disabled
	public void nothing() {		
	}
	
	@Test
	@Disabled
	public void creat() {
		assertThat(service).isNotNull();
	}
	
	@Test
	public void getMemberCountTest() {
		int count = service.getMemberCount();
		
		assertThat(count).isPositive().isGreaterThanOrEqualTo(2);
	}
	

}
