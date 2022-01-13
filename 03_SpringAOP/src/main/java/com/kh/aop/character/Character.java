package com.kh.aop.character;

import com.kh.aop.weapon.Weapon;

import lombok.Data;

@Data
public class Character {

	private String name;

	private int level;
	
	private Weapon weapon;
	
	public String quest(String questName) {
		return questName + " 퀘스트 진행 중";
	}

	// quest 메소드 호출 전후로 필요한 로직을 AOP를 통해 구현해보자.

}
