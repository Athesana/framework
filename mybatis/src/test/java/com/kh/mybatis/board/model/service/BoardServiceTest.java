package com.kh.mybatis.board.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.util.PageInfo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Board Service 테스트")
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
	@Order(1)
	@DisplayName("게시글 목록 조회(검색 기능 적용) 테스트")
	public void findAllTest(String writer, String title, String content, int expected) {
		List<Board> list = null;
		
		/*
		 * 위에 CsvSource 경우 
		 * syso(writer.length()); 경우, writer가 진짜 null이면 writer.length();는 NullPointerException이야 
		 * 근데, 이게 실제 null이 아니라 문자열 null이라면 writer.length();는 4가 되게 될 것임
		 * 따라서 null을 넘겨주기 위해서 value랑 nullValues 속성을 넣어서 처리해야 된다.
		 * (nullValues : null 글자를 보면 실제 null 값을 넣어준다.)
		 * @CsvSource 지금 테스트 결과를 syso(list.size()); 해보면 1, 2, 1, 157 건의 결과가 나온다. => 예상값 int expected
		 */
		
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
	
	// for 페이징 (전체 리스트 갯수를 알아오자, 페이징 하기 위해서는 전체 게시글 겟수를 알아야 하니까)
	// 여기에서는 필터링에 걸리거나 검색해서 조회된 게시글의 수만 알아와야된다. (B1 은 152개, B2, B3 합쳐서 5개)
	// filterProvider 메소드에서 반환해주는 값을 가지고 파라미터로 사용한다.
	@ParameterizedTest
	@MethodSource("filterProvider")
	@Order(2)
	@DisplayName("게시글 수 조회(필터 적용) 테스트)")
	public void getBoardCountTest(String[] filters) {
		int count = 0;
		
		count = service.getBoardCount(filters);
		
		assertThat(count).isPositive().isGreaterThan(0);
	}
	
	
	@ParameterizedTest
	@MethodSource("listProvider")
	@Order(3)
	@DisplayName("게시글 목록 조회(필터 적용) 테스트")
	public void findAllTest(String[] filters, int currentPage, int expected) {
		//String[] filters = new String[] {"B2", "B3"};  // request.getParameterValues("filter"); 배열 -> 리스트 객체 변환 -> 서비스에 넘기기
		List<Board> list = null;
		PageInfo pageInfo = null;
		int listCount = 0;
		
		listCount = service.getBoardCount(filters);
		
		pageInfo = new PageInfo(currentPage, 10, listCount, 10);
		
		list = service.findAll(filters, pageInfo);
		
//		System.out.println(list);
//		System.out.println(list.size());
		
		assertThat(list).isNotNull();
		assertThat(list.size()).isPositive().isEqualTo(expected);
	}
	

	@ParameterizedTest
	@ValueSource(ints = {122})
	@Order(4)
	@DisplayName("게시글 상세 조회(댓글 포함) 테스트")
	public void findBoardByNoTest(int no) {
		Board board = null;
		
		board = service.findBoardByNo(no);
		
		assertThat(board).isNotNull().extracting("no").isEqualTo(no);
		assertThat(board.getReplies()).isNotNull();
		assertThat(board.getReplies().size()).isGreaterThan(0);
		
	}

	
	@Test
	@Order(5)
	@DisplayName("게시글 등록 테스트")
	public void insertBoardTest() {
		int result = 0;
		Board board = new Board();
		Board findBoard = null;
		
		board.setWriterNo(3);
		board.setTitle("mybatis 게시글");
		board.setContent("mybatis로 게시글 등록을 해봤습니다.");
		
		result = service.save(board);
		
		// Board 객체에는 no의 값이 담겨있지 않다. -> null (실제 게시글은 작성됐지만, 따로 no의 값을 세팅하지 않았기 때문에)
		// PK값인 no는 insert될 때 시퀀스에서 만들어주는 값이기 때문에
		findBoard = service.findBoardByNo(board.getNo());
		
		assertThat(result).isGreaterThan(0);
		assertThat(findBoard).isNotNull().extracting("no").isEqualTo(board.getNo()) ;

	}
	
	
	@Test
	@Order(6)
	@DisplayName("게시글 수정 테스트")
	public void updateBoardTest() {
		int result = 0;
		Board board = service.findBoardByNo(166);
		Board findBoard = null;
		
		board.setTitle("mybatis 게시글 - 수정");
		board.setContent("mybatis로 게시글 등록을 해보았습니다. - 수정");
		board.setOriginalFileName(null);
		board.setRenamedFileName(null);
		
		result = service.save(board);
		findBoard = service.findBoardByNo(board.getNo());
		
		assertThat(result).isGreaterThan(0);
		assertThat(findBoard).isNotNull().extracting("title").isEqualTo(board.getTitle());
	}
	
	
	@Test
	@Order(7)
	@DisplayName("게시글 삭제 테스트")
	public void deleteBoardTest() {
		int result = 0;
		Board board = null;
		
		result = service.delete(161);
		board = service.findBoardByNo(161);
		
		assertThat(result).isPositive().isEqualTo(1);
		assertThat(board).isNull();
	}
	
	
	/*
	 * arguments는 Object... Object의 가변 인자로 넣어준 것이기 때문에 추가로 여러 객체를 매개 변수로 넘겨줄 수도 있다.
	 * 메소드 소스 만들어서 반환해주는 Stream 객체를 가지고 MethodSource에서 필요한 파라미터로 가져올 수 있다.
	 * Stream의 요소로 Arguments를 만들어서 넣어주면 되고, Arguments는 실제로 사용할 파라미터 값을 가변인자로 넣어줄 수 있는 객체이다.
	 */
	public static Stream<Arguments> filterProvider(){
		return Stream.of(
			Arguments.arguments((Object) new String[] {"B2", "B3"}),
			Arguments.arguments((Object) new String[] {"B1"})
		);
	}
	
	
	public static Stream<Arguments> listProvider() {
		return Stream.of(
				Arguments.arguments((Object) new String[] {"B2", "B3"}, 1, 5),
				Arguments.arguments((Object) new String[] {"B1"}, 1, 10),
				Arguments.arguments((Object) new String[] {"B1"}, 16, 2)
		);
	}

	
}
