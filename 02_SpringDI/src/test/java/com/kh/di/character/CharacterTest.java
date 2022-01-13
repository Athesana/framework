package com.kh.di.character;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.di.config.RootConfig;
import com.kh.di.weapon.Sword;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:spring/root-context.xml")
//@ContextConfiguration(classes = RootConfig.class)
class CharacterTest {
	@Autowired(required = false)
	private Character character;
	/*
	 * @Autowired
	 * - Applicationcontext 클래스 타입과 일치하는 타입(여기에서는 Character)과 일치하는 빈을 자동으로 주입받고 싶을 때 사용
	 * - required는 빈 주입이 필수로 진행되어야 하는지 설정하는지 옵션이다.  
	 * - 기본 값 true : 주입되어야 하는 Applicationcontext 상에 Bean이 존재하지 않으면 에러가 발생한다. 
	 * - 해결 위해서 required 속성을 false로 하면 된다. 그러면 빈이 Applicationcontext 상에 존재하지 않으면 NULL을 주입한다. 
	 */

	@Value("${character.name}")
	private String name;
	
	@Value("${character.level}")
	private int level;
	
	@Value("${db.driver}")
	private String driver;
	
	@Value("${db.url}")
	private String url;
	
	@Test
//	@Disabled
	public void test() {
		assertThat(driver).isNotNull().isEqualTo("oracle.jdbc.driver.OracleDriver");
		assertThat(url).isNotNull().isEqualTo("jdbc:oracle:thin:@localhost:1521:xe");
	}
	
	@Test
	public void create() {
		System.out.println(character);
		
		assertThat(character).isNotNull();
		assertThat(character.getName()).isNotNull();
		assertThat(character.getLevel()).isPositive().isGreaterThan(0).isEqualTo(level);
		assertThat(character.getWeapon()).isNotNull();
		
	}

}
