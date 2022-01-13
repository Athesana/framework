package com.kh.di.weapon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component("windForce")
public class Bow implements Weapon {

	@Value("${character.weapon.name:활}")
	public String name;
	
	@Override
	public String attack() {
		return "민첩하게 활을 쏜다";
	}

}
