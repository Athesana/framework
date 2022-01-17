package com.kh.mvc.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.mvc.member.model.service.MemberService;
import com.kh.mvc.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	/*
	 * ▼ 기본적인 GET 요청 틀
	// 컨트롤러가 처리할 요청을 정의한다. (URL, Method 등)
//	@RequestMapping(value = "/login", method = {RequestMethod.GET})
	@GetMapping("/login")
	public String login() {
		
		log.info("login() - 호출");
		
		// view의 이름을 리턴한다. -> WEB-INF > views > home.jsp를 보여준다.
		return "home";
	}
	
	▼ 사용자의 파라미터를 전송받는 방법
		1. HttpServletRequest를 통해서 전송받기(기존 JSP/Servlet 방식)
			- 메소드의 매개변수로 HttpServletRequest를 작성하면 메소드 실행 시 스프링 컨테이너가 자동으로 객체를 인자로 주입해준다.

	@PostMapping("/login")
	public String login(HttpServletRequest request) {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// 가변인자로 파라미터를 받을 수 있는데, 받은 순서대로 {} 안에 넣어줄 수 있다.
		log.info("login() - 호출 : {} {}", id, password);
		
		return "home";
	}

		2-1. @RequestParam 어노테이션을 통해 전송받기
			- 스프링에서 조금 더 간편하게 파라미터를 받아올 수 있는 방법 중 하나이다.
			- 내부적으로는 Request 객체를 이용해서 데이터를 전송받는 방법이다.
			- 단, 매개변수의 이름과 name 속성의 값이 동일하게 설정된 경우는 자동으로 주입된다.
			  (어노테이션 사용하는 것이 아니라서 defaultValue 설정이 불가능하다.)

	@RequestMapping(value = "login", method = {RequestMethod.POST})
//	public String login(@RequestParam("id") String id, @RequestParam("password")String password) {
	public String login(String id, String password) {
		
		log.info("login() - 호출 : {} {}", id, password);
		
		return "home";
	}


		2-2. @RequestParam에 default 값을 설정하기
			- defaultValue 속성을 사용하면 파라미터 name 속성에 값이 없을 경우 기본값을 지정할 수 있다.
		
	
	@PostMapping("/login")
	public String login(@RequestParam("id") String id, 
						@RequestParam(value = "password", defaultValue = "0000")String password) {
		
		log.info("login() - 호출 : {} {}", id, password);
		
		return "home";
	}

		2-3. @RequestParam에 실제 존재하지 않는 파라미터를 받으려고 할 때
			- 파라미터 name 속성에 없는 값이 넘어올 경우, 에러가 발생한다.
			- @RequestParam(required = false)로 지정하면 null 값을 넘겨준다.
			- 단, defaultValue를 설정하면 defaultValue에 설정된 값으로 넘겨준다. (에러 발생X)


	@PostMapping("/login")
	public String login(@RequestParam("id")String id, 
						@RequestParam("password")String password,
//						@RequestParam(value = "address", required = false)String address) {
						@RequestParam(value = "address", defaultValue = "서울특별시")String address) {
		
		log.info("login() - 호출 : {} {} {}", new Object[] {id, password, address});
		
		return "home";
	}

		3. @PathVariable 어노테이션을 통해서 전송받기
			- URL 패스상에 있는 특정 값을 가져오기 위해 사용하는 방법이다.
			- REST API를 사용할 때, 요청 URL 상에서 필요한 값을 가져오는 경우 주로 사용한다.
			- 매핑 URL에 {}로 묶는다면, {} 안의 값을 Path Variable 로 사용하고 클라이언트에서 요청 할 때, 실제 경로상의 값을 해당 Path Variable로 받겠다는 의미이다.
			- 매핑 URL에 {} 안의 변수명과 매개 변수의 변수명이 동일하다면 @PathVariable의 괄호("")는 생략이 가능하다. (대신 어노테이션 자체를 생략할 수는 없다. - requestParam 인지 명확하지 않기 때문)

	@GetMapping("/member/{id}")
//	public String findMember(@PathVariable("id")String id) {
	public String findMember(@PathVariable String id) {
		
		log.info("Member ID : {}", id);
		
		return "home";
	}

	
		4. @ModelAttribute 어노테이션을 통해서 전송받기
			- 요청 파라미터가 많은 경우 객체 타입으로 파라미터를 넘겨받는 방법이다.
			- 스프링 컨테이너가 "기본 생성자"를 통해서 객체를 생성하고 파라미터 name 속성과 동일한 필드명을 가진 필드에 값을 주입해준다.
			- 단, 기본 생성자(없으면 500 에러)와 Setter(없으면 null로 출력)가 존재해야 한다.
			- 파라미터 name 속성과 넘겨받을 객체의 필드명이 동일하면, setter를 통해서 주입시켜준다. 
			- @ModelAttribute 어노테이션을 생략해도 객체로 매핑되지만 가독성을 위해 적어주는 것 추천!
			
	
	@PostMapping("/login")
//	public String login(@ModelAttribute Member member) {
	public String login(Member member) {
		
		log.info("{}, {}", member.getId(), member.getPassword());
		
		return "home";
	}
	
	 */

	@Autowired
	private MemberService service;
	
/*
 * 로그인 처리(~DB에서 멤버 조회하기)
 */


	@PostMapping("/login")
	public String login(@RequestParam String id, @RequestParam String password) {
		
		log.info("{}, {}", id, password);
		
		Member member = service.login(id, password);
		
		System.out.println(member);
		
		return "home";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
