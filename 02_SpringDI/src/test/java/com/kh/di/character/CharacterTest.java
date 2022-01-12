package com.kh.di.character;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.kh.di.weapon.Sword;

class CharacterTest {

	@Test
	@Disabled
	public void test() {
	}
	
	@Test
	public void create() {
		Character character = new Character("홍길동", 1, new Sword("의검"));
		
		assertThat(character).isNotNull();
		assertThat(character.getWeapon()).isNotNull();
	
	}

}
