package com.kh.di.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.kh.di.owner.Owner;
import com.kh.di.pet.Cat;
import com.kh.di.pet.Dog;
import com.kh.di.pet.Pet;

@Configuration  // 해당 클래스가 자바 설정 파일임을 선언한다.
@Import(value = {
		OwnerConfig.class, PetConfig.class
})
@ComponentScan("com.kh.di")  // base-package 지정
public class RootConfig {
	// Applicationcontext가 읽어서 각 메소드들을 bean으로 등록한다.
	
	// ▼ 객체를 만들 때 생성자를 통해서 필요한 값을 주입 받은 것
//	@Bean 
//	public Dog dog() {
//		return new Dog("댕댕이");
//	}
	
	// ▼ setter를 통해서 주입 받은 것
//	@Bean("ray")
//	@Primary // <bean primary="true" />와 같다. -> 사용하면 매개 값으로 @Qualifier 생략 가능
//	public Cat cat() {
//		Cat cat = new Cat();
//		
//		cat.setName("레이");
//		
//		return cat;
//	}
//	
	/*
	 * Bean 연결하는 방법 1. 메소드를 직접 호출하는 방법
	 * dog() 메소드는 빈으로 등록되어 있기 때문에 호출할 때마다 새로 객체를 생성하는 것이 아니라, 
	 * 싱글톤 패턴의 특성대로 애플리케이션 컨텍스트에서 등록된 빈 객체를 리턴한다.
	 */
//	@Bean("lee") // ("lee") 별도로 Bean id를 지정하지 않으면 메소드 명으로 id를 지정한다. owner가 된다.
//	public Owner owner() {
//
//		return new Owner("이산아", 22, dog());
//	}
	
	/*
	 * Bean 연결하는 방법 2.
	 * @Autowired : ApplicationContext 안에 있는 bean을 자동으로 주입해주는 것으로써, @를 필드 & 매개변수에 붙일 수 있다.
	 * 이 때, pet를 구현한 bean이 2개여서 구체적으로 어떤 객체를 주입할 것인지 @Qualifier로 정해야 한다.
	 */	
//	@Bean("hong")
//	public Owner owner2(/* @Autowired (생략 가능) */ @Qualifier("ray") Pet pet) {	
//
//		return new Owner("홍길동", 32, pet);
//	}
	
	@Bean
	public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		
		Resource[] resources = new ClassPathResource[] {
				new ClassPathResource("character.properties"),
				new ClassPathResource("driver.properties")
		};
		
		configurer.setLocations(resources);
		
		return configurer;
	}
	
	
	
	
}
