package com.kh.aop.weapon;

import lombok.Data;

@Data
public class Bow implements Weapon {

	public String name;
	
	@Override
	public String attack() {
		return "민첩하게 활을 쏜다";
	}

}
