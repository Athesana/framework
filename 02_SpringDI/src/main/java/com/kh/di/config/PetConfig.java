package com.kh.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kh.di.pet.Cat;
import com.kh.di.pet.Dog;

@Configuration
public class PetConfig {
	@Bean 
	public Dog dog() {
		return new Dog("댕댕이");
	}
	
	@Bean("ray")
	public Cat cat() {
		Cat cat = new Cat();
		
		cat.setName("레이");
		
		return cat;
	}
}
