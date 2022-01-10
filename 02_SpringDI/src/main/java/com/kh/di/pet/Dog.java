package com.kh.di.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dog implements Pet {
	
	private String name;
	
	@Override
	public String bark() {
		return "멍멍";
	}
}
