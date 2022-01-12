package com.kh.di.weapon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sword implements Weapon {

	private String name;
	
	@Override
	public String attack() {
		return "검을 휘두른다.";
	}

}
