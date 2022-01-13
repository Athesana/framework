package com.kh.di.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.kh.di.weapon.Weapon;

import lombok.Data;

@Data
@Component 
//@PropertySource("classpath:character.properties")
public class Character {
	// 스프링 프로퍼티 플레이스 홀더 사용 (${key:기본값})
	@Value("${character.name:홍길동}")
	private String name;
	
	@Value("${character.level:99}")
	private int level;
	
	@Autowired
	@Qualifier("windForce")
	private Weapon weapon;
	
//	public Character(/* @Autowired */ Environment env) {
//		this.name = env.getProperty("character.name");
//		this.level = Integer.parseInt(env.getProperty("character.level"));
//	}
	
	/*
	 * @Component로 등록하면 ApplicationContext가 해당하는 클래스로 Bean객체를 만들어서 보관한다.
	 *  - 빈 생성시 (value)로 별도의 ID를 지정해주지 않으면 클래스 이름에서 앞글자를 소문자로 바꾼 ID를 갖는다. (character)
	 *  - BUT, ApplicationContext는 모든 패키지를 뒤지면서 @컴포넌트를 찾는 것이 아니기 때문에 최소한의 설정을 지정해야 한다.
	 */

	/*
	 * @Value(필드에 직접 세팅하는 것)로 하드코딩 안하고 설정하기(실행하는 시점에 값이 결정되어야 할 때)
	 * 1. character.properties 파일 생성
	 * 2. @PropertySource에 경로 지정
	 * 		[2-1. 어노테이션을 사용해서 properties 파일의 값을 읽어오는 방법]
	 * 			2-1-1. Envrionment 객체 사용(ApplicationContext에 이미 등록되어있는 내장 Bean)
	 * 					- 해당하는 프로퍼티에 접근해서 값을 가져올 수 있다. by getProperty()
	 * 			2-1-2. 스프링 프로퍼티 플레이스 홀더 사용(${프로퍼티의 key값:해당하는 값이 없으면 기본 값으로 쓸 값을 지정한다.}) 
	 * 
	 * 		[2-2. 어노테이션을 생략 후, 스프링 프로퍼티 플레이스 홀더를 통해서 properties 파일의 값을 읽어오는 방법]
	 * 			2-2-1. xml 설정 파일의 경우  : <context:property-placeholder /> 추가
	 * 			2-2-2. java 설정 파일의 경우 : PropertySourcesPlaceholderConfigurer 빈 등록 -> Resource 객체 이용
	 * 		
	 */


}
