package com.kh.di.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kh.di.owner.Owner;
import com.kh.di.pet.Pet;

@Configuration
public class OwnerConfig {
	
	@Bean("lee")
	public Owner owner( @Autowired @Qualifier("dog") Pet pet) {

		return new Owner("이산아", 22, pet);
	}
	
	@Bean("hong")
	public Owner owner2(/* @Autowired (생략 가능) */ @Qualifier("ray") Pet pet) {

		return new Owner("홍길동", 32, pet);
	}
	
}
