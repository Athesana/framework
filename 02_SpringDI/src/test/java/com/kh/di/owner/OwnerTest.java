package com.kh.di.owner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.kh.di.pet.Cat;
import com.kh.di.pet.Dog;

class OwnerTest {

	@Test
	@Disabled
	public void nothing() {
	}
	
	@Test
	public void create() {
		// 기존에 자바 애플리케이션에서는 다형성(인터페이스(추상 클래스))과 생성자 주입을 통해 객체 간의 결합을 느슨하게 만들어 준다.
		Owner owner = new Owner("이산아", 20, new Cat("치치"));
		
		assertThat(owner).isNotNull();
		assertThat(owner.getPet()).isNotNull();
	}
	
	@Test
	public void contextTest() {
		// 스프링의 애플리케이션 컨텍스를 활용하여 객체 간의 결합을 더욱 느슨하게 만들어준다.
		// new GenericXmlApplicationContext("클래스패스 상의 xml 파일의 위치 지정");
		GenericXmlApplicationContext context = new GenericXmlApplicationContext("spring/root-context.xml");
		
//		Owner owner = (Owner)context.getBean("owner");
		Owner owner = context.getBean("owner", Owner.class);
		
		System.out.println(owner);
		
		assertThat(owner).isNotNull();
		assertThat(owner.getPet()).isNotNull();
	}
	
	
	
	

}
