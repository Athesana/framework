package com.kh.aop.owner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.aop.config.RootConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
class OwnerTest {
	
	@Autowired
	private Owner owner;
	
	@Test
	public void creat() {
		assertThat(owner).isNotNull();
		assertThat(owner.getName()).isNotNull();
		assertThat(owner.getAge()).isPositive().isGreaterThan(0);
	}

	@Test
	public void barkTest() throws Exception {
		assertThat(owner.getPet()).isNotNull();
		assertThat(owner.getPet().bark()).isNotNull();
	}
	
}
