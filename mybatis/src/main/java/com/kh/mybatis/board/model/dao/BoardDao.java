package com.kh.mybatis.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.board.model.vo.Board;

public class BoardDao {

	public List<Board> findAll(SqlSession session) {
		
		return session.selectList("boardMapper.selectAll");
	}

}
