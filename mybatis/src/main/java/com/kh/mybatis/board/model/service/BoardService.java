package com.kh.mybatis.board.model.service;

import static com.kh.mybatis.common.template.SqlSessionTemplate.getSession;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.board.model.dao.BoardDao;
import com.kh.mybatis.board.model.vo.Board;

public class BoardService {

	private BoardDao dao = new BoardDao();
	
	public int getBoardCount(String[] filters) {
		int count = 0;
		SqlSession session = getSession();
		
		count = dao.getBoardCount(session, Arrays.asList(filters));
		
		session.close();
		
		return count;
	}
	
	public List<Board> findAll(String writer, String title, String content) {
		List<Board> list = null;
		SqlSession session = getSession();
		
		list = dao.findAll(session, writer, title, content);
		
		session.close();
		
		return list;
	}

	public List<Board> findAll(String[] filters) {
		List<Board> list = null; 
		SqlSession session = getSession();
		
		list = dao.findAll(session, filters);
		
		session.close();
		
		return list;
	}

}
