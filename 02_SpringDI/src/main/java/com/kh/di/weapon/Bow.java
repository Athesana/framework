package com.kh.di.weapon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bow implements Weapon {

	public String name;
	
	@Override
	public String attack() {
		return "민첩하게 활을 쏜다";
	}

}
