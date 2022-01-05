package com.kh.mybatis.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.util.PageInfo;

public class BoardDao {

	public int getBoardCount(SqlSession session, List<String> filters) {
		
		return session.selectOne("boardMapper.getBoardCountByFilters", filters);
	}
	
	public List<Board> findAll(SqlSession session, String writer, String title, String content) {
		// 여러 개의 객체를 담을 수 있는 Collection 중 Map 객체를 생성해서 매개 값으로 넘겨준다.
		
		Map<String, String> map = new HashMap<>();
		
		map.put("writer", writer);
		map.put("title", title);
		map.put("content", content);
		
		return session.selectList("boardMapper.selectAll", map);
	}

	public List<Board> findAll(SqlSession session, String[] filters, PageInfo pageInfo) {
		// 선택된 필터가 없이 null일 경우도 있다. 그럴 경우에는, 
		// 기존에는 변수명으로 접근했지만, list나 배열 타입은 파라미터로 전달하게 되면 내부적으로는 map 객체로 변환이 된다.
		// list -> 키: collection 혹은 list, 밸류:object / 배열 -> 키: array, 밸류: 필터 객체 이런식으로 바뀐다.
		// mapper의 쿼리문에서는 list 타입은 list로 배열은 array 이름으로 사용할 수 있는 것이다.
		// 그래도 굳이 굳이 filters라는 이름을 그대로 쓰고 싶다면 내부적으로 map 타입으로 변환되지 않도록 map객체로 만들어주면 된다.
		
		/*
		 * List 타입이나 Array 타입의 데이터를 실제 쿼리문 수행시킬 때 파라미터로 전달하면 내부적으로는 Map으로 타입이 변환되어서 저장되기 때문에
		 * Mapper에서는 list나 array라는 이름으로 사용해야 한다.
		 * 
		 * Dao.java
		 * 		session.selectList("boardMapper.selectBoardListByFilters", filters);
		 * 
		 * Mapper.xml
		 * 		<if test="array != null">
		 *  		...	
		 * 		</if>
		 * 
		 * 만약에, filters 라는 이름을 Mapper에서 사용하고 싶다면 Map map 객체를 생성하고 -> map에 담아서 파라미터로 전달한다.
		 */
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("filters", filters);
		
		/*
		 * 기존에 Servlet/JSP 에서는 쿼리문에서 RowNum 컬럼과 서브쿼리를 통해서 페이징 처리를했다.
		 * DB 쿼리가 바뀌면 페이징을 위해서 수정 작업이 지속적으로 필요하다.
		 * 하지만, Mybatis에서는 페이징 처리를 위해서 RowBounds 클래스를 제공해준다.
		 * 쿼리문 조회 결과를 가지고 RowBounds를 session 객체에 매개값으로 넘겨주면 쿼리 조회해서 나온 결과를 RowBounds 내용을 가지고 페이징에 필요한 데이터를 가져다 준다.
		 * 
		 * RowBounds의 객체를 생성할 때 offset과 limit 값을 전달해서 내가 원하는 데이터만 가지고 페이징 처리를 쉽게 구현한다.
		 * 
		 * - offset : 얼마만큼 건너 뛸지? - limit : 얼마만큼 가져올지? 
		 * 
		 * offset = 0, limit = 10 이라면
		 * 	-> 0개를 건너뛰고 10개를 가져온다. (첫 번째 게시글(= 첫번째 결과, 첫번째 행부터) ~ 10번째 게시글까지) 
		 * offset = 10, limit = 10 이라면
		 * 	-> 10개를 건너뛰고 10개를 가져온다. (11번째 게시글 ~ 20번째 게시글까지)
		 * offset = 20, limit = 10 이라면
		 * 	-> 20개를 건너뛰고 10개를 가져온다. (21번째 게시글 ~ 30번째 게시글까지)
		 */
		
		// 현재 페이지가 1페이지이면 -1을 하고 10을 곱해! 그럼 어차피 0이지 따라서 1페이지일때는 offset이 0이 되도록 만든 공식
		// 현재 페이지가 2페이지라면 (2 - 1) * 10 이라서 10이니까 10개 건너뛰라는 뜻이 됨
		int offset = (pageInfo.getCurrentPage() - 1 ) * pageInfo.getListLimit();
		int limit = pageInfo.getListLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return session.selectList("boardMapper.selectBoardListByFilters", map, rowBounds);
	}

	public Board findBoardByNo(SqlSession session, int no) {
		// 순서 1. 쿼리 실행
		return session.selectOne("boardMapper.selectBoardByNo", no);
	}



}
