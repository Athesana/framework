package com.kh.mybatis.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionTemplate {
	public static SqlSession getSession() {
		InputStream is = null;
		SqlSessionFactoryBuilder builder = null;
		SqlSessionFactory factory = null;
		SqlSession session = null;
		
		try {
			// Resources는 Mybatis에서 제공하는 유틸 클래스로
			// 클래스 path로부터 자원(Resource)을 쉽게 읽어오는 메소드를 제공한다.
			// 설정 파일에 연결된 리소스의 스트림을 연결한다.
			is = Resources.getResourceAsStream("mybatis-config.xml");
		
			builder = new SqlSessionFactoryBuilder();
			factory = builder.build(is);
//			factory = builder.build(is, "kh");
//			default가 아니라 내가 원하는 데이터베이스를 연결하고 싶을 떄, 2번째 매개값으로 id를 적는다.
			
			// true : autocommit 활성, false : autocommit 비활성
			session = factory.openSession(false);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return session;
	}

}
