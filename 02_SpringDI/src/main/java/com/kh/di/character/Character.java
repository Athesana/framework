package com.kh.di.character;

import com.kh.di.weapon.Weapon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Character {
	private String name;
	
	private int level;
	
	private Weapon weapon;
}
