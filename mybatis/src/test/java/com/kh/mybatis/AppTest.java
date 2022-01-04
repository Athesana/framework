package com.kh.mybatis;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kh.mybatis.common.template.SqlSessionTemplate;


@DisplayName("첫 번째 테스트 코드 작성")
public class AppTest {
	private SqlSession session = null;
	
	// 테스트 메소드들이 실행되기 전에 실행되는 메소드(딱 한번만 실행된다.)
	@BeforeAll
	public static void init() {
		System.out.println("@BeforeAll");
	}
	
	// 각각의 테스트 메소드들이 실행되기 전에 무조건 실행되는 메소드이다.
	@BeforeEach
	public void setup() {
		System.out.println("@BeforeEach");
		
		session = SqlSessionTemplate.getSession();
	}
	
	@Test
	@Disabled // 테스트 클래스 또는 메소드를 비활성할 수 있다.
	public void nothing() {
		// 과정 1) 이 테스트 메소드를 통해서 현재 프로젝트가 테스트가 가능한 환경인지 확인하는 작업부터 TDD가 시작된다. (내용은 아무것도 없다.)
	}
    
	@Test
	@DisplayName("SqlSession 생성 테스트")
	public void create() {
		// 과정 2) 테스트 하고자 하는 객체가 생성되는지 확인
		assertNotNull(session);
	}
}
