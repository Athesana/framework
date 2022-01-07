package com.kh.mybatis.board.model.service;

import static com.kh.mybatis.common.template.SqlSessionTemplate.getSession;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.board.model.dao.BoardDao;
import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.util.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardService {

	private BoardDao dao = new BoardDao();
	
	public int getBoardCount(String[] filters) {
		log.info("getBoardCount() 호출");
		int count = 0;
		SqlSession session = getSession();
		
		count = dao.getBoardCount(session, Arrays.asList(filters));  // filters가 list로 바뀌어서 dao로 넘어감
		
		session.close();
		
		return count;
	}
	
	public List<Board> findAll(String writer, String title, String content) {
		log.info("findAll() 호출");
		List<Board> list = null;
		SqlSession session = getSession();
		
		list = dao.findAll(session, writer, title, content);
		
		session.close();
		
		return list;
	}

	public List<Board> findAll(String[] filters, PageInfo pageInfo) {
		log.info("findAll(filters) 호출");
		List<Board> list = null; 
		SqlSession session = getSession();
		
		list = dao.findAll(session, filters, pageInfo);
		
		session.close();
		
		return list;
	}

	// ▼ 단건 SELECT 조회
	public Board findBoardByNo(int no) {
		Board board = null;
		SqlSession session = getSession();
		
		board = dao.findBoardByNo(session, no);
		
		session.close();
		
		return board;
	}

	
	public int save(Board board) {
		int result = 0;
		SqlSession session = getSession();
		
		// board의 PK 로 구분
		if(board.getNo() != 0) {
			result = dao.updateBoard(session, board);
		} else {
			result = dao.insertBoard(session, board);
		}
		
		if(result > 0) {
			session.commit();
		} else {
			session.rollback();
		}
		
		session.close();
		
		return result;
	}

	public int delete(int no) {
		int result = 0;
		SqlSession session = getSession();
		
		result = dao.updateStatus(session, no, "N");
		
		if(result > 0) {
			session.commit();
		} else {
			session.rollback();
		}
		
		return result;
	}

}
