package com.kh.mybatis.board.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kh.mybatis.board.model.vo.Board;

class BoardServiceTest {
	
	private BoardService service;

	@BeforeEach
	public void setUp() throws Exception {
		service = new BoardService();
	}

	@Test
	@Disabled
	public void nothing() {
		
	}

	@Test
	@Disabled
	public void creat() {
		assertThat(service).isNotNull();
	}
	
	@Test
	@DisplayName("전체 게시글 조회 테스트")
	public void findAll() {
		List<Board> list = null;
		
		list = service.findAll();
		
		System.out.println(list);
		
		assertThat(list).isNotNull();
		
		
	}
	
}
