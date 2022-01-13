package com.kh.aop.character;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:root-context.xml"})
class CharacterTest {
	@Autowired
	private Character character;
	
	@Test
	void test() {
	}
	
	@Test
	void creat() {
		assertThat(character).isNotNull();
		assertThat(character.getWeapon()).isNotNull();
	}
	
	@Test
	void questTest() {
		System.out.println(character.quest("일시점검"));
//		assertThat(character.quest("일시점검")).contains("일시점검");
	}

}
