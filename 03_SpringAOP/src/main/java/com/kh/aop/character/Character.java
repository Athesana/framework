package com.kh.aop.character;

import com.kh.aop.weapon.Weapon;

import lombok.Data;

@Data
public class Character {

	private String name;

	private int level;
	
	private Weapon weapon;
	
	public String quest(String questName) throws Exception {
	
//		if(true) {
//			throw new Exception("Quest 처리 중 예외 발생");
//		}
		
		System.out.println(questName + "퀘스트 진행 중..");
		return questName + " 퀘스트 진행 중";
	}


}
