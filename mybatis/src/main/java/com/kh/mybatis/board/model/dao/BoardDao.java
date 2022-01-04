package com.kh.mybatis.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.board.model.vo.Board;

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

	public List<Board> findAll(SqlSession session, String[] filters) {
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
		
		return session.selectList("boardMapper.selectBoardListByFilters", map);
	}



}
