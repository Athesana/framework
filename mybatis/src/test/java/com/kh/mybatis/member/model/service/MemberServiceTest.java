package com.kh.mybatis.member.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.kh.mybatis.member.model.vo.Member;

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
	@DisplayName("회원 수 조회 테스트")
	public void getMemberCountTest() {
		int count = service.getMemberCount();
		
		assertThat(count).isPositive().isGreaterThanOrEqualTo(2);
	}
	
	@Test
	@DisplayName("모든 회원 조회 테스트")
	public void findAllTest() {
		List<Member> members = null;
		
		members = service.findAll();
		
		assertThat(members).isNotNull()
						   .isNotEmpty()
						   .extracting("id")
						   .isNotNull()
						   .contains("Athesana", "admin2");
	}

	@ParameterizedTest
	@ValueSource(strings = {"Athesana", "admin2"})
	@DisplayName("회원 조회 테스트(ID로 검색)")
	public void findMemberByIdTest(String userId) {
		
		Member member = service.findMemberById(userId);
		
		assertThat(member).isNotNull()
						  .extracting("id")
						  .isEqualTo(userId);
		// null인지 확인 -> Member 객체에서 id 라는 필드 값만 추출 -> 그리고 그 값이 내가 지금 조회할 떄 쓰는 userId와 동일한지
	}
	
	@Test
	@DisplayName("회원 등록 테스트")
	public void insertMemberTest() {
		int result = 0;
		Member member = new Member();
		
		// NOT NULL 제약 조건 안 걸려있는 애들만 세팅하면 된다. (테이블 > 데이터 > NULLABLE이 NO인 상태인 애들!)
		// NOT NULL 제약 조건이 걸려서 NULL 넘겼을 때 에러 발생할 애들만 미리 세팅을 한다.
		member.setId("test1");
		member.setPassword("1234");
		member.setName("이방원");
		
		System.out.println(member.getNo());
		
		result = service.save(member);
		
		// member에 NO 값을 가져오고 싶다면?
		// Member member = service.findMemberyById("test1");
		// syso(member.getNo()); 이런식으로 처리했었어야 하지만, get 하지않고 INSERT 하고나서 NO값 받아올 것임
				
		System.out.println(member.getNo());

		assertThat(result).isGreaterThan(0);
		
		assertThat(member.getNo()).isGreaterThan(0);
		
		
	}
	
	
	
	
	
	
	
	
}
