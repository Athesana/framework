package com.kh.di.weapon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Primary // 동일한 타입의 빈이 여러 개 존재할 때, 기본으로 주입될 빈을 지정한다. (지정되어있어도 다른 곳에서 @Qualifier가 붙어있으면 그게 빈에 주입된다. 없으면 Primary 지정한게 빈에 주입됨)
@Component
public class Sword implements Weapon {
	
	@Value("집행검")
	private String name;
	
	@Override
	public String attack() {
		return "검을 휘두른다.";
	}

}
