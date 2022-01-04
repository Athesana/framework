package com.kh.mybatis.board.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
	
	@ParameterizedTest
	@CsvSource( 
		value = {
			"sana, null, null, 1",
			"null, 22, null, 2",
			"null, null, 크리스마스, 1",
			"null, null, null, 157"},
		nullValues = "null"
	)
	public void findAllTest(String writer, String title, String content, int expected) {
		List<Board> list = null;
		
		/*
		 * findAllTest(매개값) 으로 넣어주기 전에 
		 * String writer = null;
		 * String title = null;
		 * String content = "크리스마스";
		 */
		
		list = service.findAll(writer, title, content);
		
		assertThat(list).isNotNull();
		assertThat(list.size()).isPositive().isEqualTo(expected);
		
	}
	
	@Test
	public void getBoardCountTest() {
		int count = 0;
		String[] filters = new String[] {"B1"};
		
		count = service.getBoardCount(filters);
		
		System.out.println(count);
		
		assertThat(count).isPositive().isGreaterThan(0);
	}
	
	
	@Test
	public void findAllTest() {
		String[] filters = new String[] {"B2", "B3"};  // request.getParameterValues("filter"); 배열 -> 리스트 객체 변환 -> 서비스에 넘기기
		List<Board> list = null;
		
		list = service.findAll(filters);
		
		System.out.println(list);
		System.out.println(list.size());
		
		assertThat(list).isNotNull();
	}
	
	
	
	
	
	
	
	
	
	
	
}
