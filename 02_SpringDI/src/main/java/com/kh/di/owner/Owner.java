package com.kh.di.owner;

import com.kh.di.pet.Pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
	
	private String name;
	
	private int age;
	
	private Pet pet;
	
}
