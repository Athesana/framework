package com.kh.aop.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kh.aop.pet.Pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Owner {
	
	@Value("이산아")
	private String name;
	
	@Value("20")
	private int age;
	
	@Autowired
	@Qualifier("dog")
	private Pet pet;
	
}
