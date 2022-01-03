package com.kh.mybatis.member.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.kh.mybatis.member.model.vo.Member;

@DisplayName("Member 테스트")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
	@Order(1)
	public void getMemberCountTest() {
		int count = service.getMemberCount();
		
		assertThat(count).isPositive().isGreaterThanOrEqualTo(2);
	}
	
	@Test
	@DisplayName("모든 회원 조회 테스트")
	@Order(2)
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
	@Order(3)
	public void findMemberByIdTest(String userId) {
		
		Member member = service.findMemberById(userId);
		
		assertThat(member).isNotNull()
						  .extracting("id")
						  .isEqualTo(userId);
		// null인지 확인 -> Member 객체에서 id 라는 필드 값만 추출 -> 그리고 그 값이 내가 지금 조회할 떄 쓰는 userId와 동일한지
	}
	
	/*
	 * @ValueSource : 하나의 값을 넘길 때
	 * @CsvSrouce : 여러 개의 값을 넘길 때
	 */
	
	@ParameterizedTest
	@CsvSource({
		"test1, 1234, 이방원",
		"test2, 4567, 이성계",
		
	})
	@DisplayName("회원 등록 테스트")
	@Order(4)
	public void insertMemberTest(String id, String password, String name) {
		int result = 0;
		Member findMember = null;
		// Member.java에서 3개에 해당하는 생성자 만들고 나서 이렇게 만듬
//		Member member = new Member("test1", "1234", "이방원");
		// @Csv에 값 넣고, 매개값 세팅하고 이렇게 바꿈
		Member member = new Member(id, password, name);
		
		// NOT NULL 제약 조건 안 걸려있는 애들만 세팅하면 된다. (테이블 > 데이터 > NULLABLE이 NO인 상태인 애들!)
		// NOT NULL 제약 조건이 걸려서 NULL 넘겼을 때 에러 발생할 애들만 미리 세팅을 한다.
		/*
		member.setId("test1");
		member.setPassword("1234");
		member.setName("이방원");
		*/
		
		result = service.save(member);
		// 실제로 DB에 Member가 저장되었는지 확인하기 위해서 다시 Member를 조회(ID로 조회)
		findMember = service.findMemberById(id);
		
		// member에 NO 값을 가져오고 싶다면?
		// Member member = service.findMemberyById("test1");
		// syso(member.getNo()); 이런식으로 처리했었어야 하지만, get 하지않고 INSERT 하고나서 NO값 받아올 것임

		assertThat(result).isGreaterThan(0);
		assertThat(member.getNo()).isGreaterThan(0);
		assertThat(findMember).isNotNull().extracting("name").isEqualTo(name);
		
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"test1, 5678, 세종대왕",
		"test2, 0000, 정조",
		
	})
	@DisplayName("회원 정보 수정 테스트")
	@Order(5)
	public void updateMemberTest(String id, String password, String name) {
		int result = 0;
		Member member = null;
		Member findMember = null;
		
		member = service.findMemberById(id);
		
		member.setPassword(password);
		member.setName(name);
		
		result = service.save(member);
		
		// 실제로 DB에 Member가 수정되었는지 확인하기 위해서 다시 Member를 조회(ID로 조회)
		findMember = service.findMemberById(id);
		
		assertThat(result).isGreaterThan(0);
		assertThat(findMember.getName()).isNotNull().isEqualTo(name);
		assertThat(findMember.getPassword()).isNotNull().isEqualTo(password);
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test1", "test2"})
	@DisplayName("회원 삭제 테스트")
	@Order(6)
	public void deleteTest(String id) {
		int result = 0;
		Member findMember = null;
		Member member = service.findMemberById(id);
		
		result = service.delete(member.getNo());
		
		// 실제로 DB에 Member가 삭제되었는지 확인하기 위해서 다시 Member를 조회(ID로 조회)
		findMember = service.findMemberById(id);
		
		assertThat(result).isPositive().isEqualTo(1);
		assertThat(findMember).isNull();
		
	}
	
	
	
}
