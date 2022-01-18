package com.kh.mvc.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.kh.mvc.member.model.service.MemberService;
import com.kh.mvc.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
// Model 객체에 loginMember라는 키값으로 객체가 추가되면 해당 객체를 세션 스코프에 추가하는 어노테이션이다.
@SessionAttributes("loginMember")
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
			- 메소드의 매개변수로 HttpServletRequest(GET 요청시에는 매개변수로 있어도 되고 없어도 된다.)를 작성하면 메소드 실행 시 스프링 컨테이너가 자동으로 객체를 인자로 주입해준다.

	@PostMapping("/login")
	public String login(HttpServletRequest request) {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// 가변인자로 파라미터를 넘겨줄 수 있는데, 받은 순서대로 {} 안에 넣어줄 수 있다.
		log.info("login() - 호출 : {} {}", id, password);
		
		return "home";
	}

		2-1. @RequestParam 어노테이션을 통해 전송받기
			- 스프링에서 조금 더 간편하게 파라미터를 받아올 수 있는 방법 중 하나이다.
			- 내부적으로는 Request 객체를 이용해서 데이터를 전송받는 방법이다.
			- 단, 매개변수의 이름과 name 속성의 값이 동일하게 설정된 경우는 @RequestParam("이름") 생략이 가능하고 자동으로 주입된다.
			  (이 경우는 어노테이션이 생략되어있기 때문에 defaultValue 설정이 불가능하다.)

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
			- 단, 기본 생성자(없으면 아예 객체를 생성하지 못하기 때문에 500 에러)와 Setter(없으면 null로 출력)가 존재해야 한다.
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
 * 1. HttpSession과 Model 객체
 * 	1) Model
 * 		- 컨트롤러에서 데이터를 뷰로 전달하고자 할 때 사용하는 객체이다.
 * 		- 전달하고자 하는 데이터를 맵(key, value)로 형태로 담을 수 있다. (addAttribute 활용)
 * 		- Model 객체의 scope는 Request이다.


	@PostMapping("/login")
	public String login(
			HttpSession session, Model model,
			@RequestParam String id, @RequestParam String password) {
		
		log.info("{}, {}", id, password);
		
		Member member = service.login(id, password);
		
		if (member != null) {
			session.setAttribute("loginMember", member);
			
			 // return "home"; 
			 //  - forwarding 방식으로 ViewResolver에 의해 /WEB-INF/views/home.jsp로 포워딩 한다. 
			 
			 // return "redirect:/";
			 //  - redirect 방식으로 여기서 리턴한 경로로 브라우저에서 다시 요청하도록 반환한다.
			
			return "redirect:/";
		} else {
			model.addAttribute("msg","아이디나 비밀번호가 일치하지 않습니다.");
			model.addAttribute("location","/");

			// ViewResolver에 의해 /WEB-INF/views/common/msg.jsp로 포워딩 한다.
			return "common/msg";
		}
		
	}
	

	// 로그아웃 처리
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	2. @SessionAttributes과 ModelAndView 객체
		1) @SessionAttributes("키값")
			- Model 객체에 "키값"에 해당하는 Attribute를 Session Scope까지 범위를 확장시킨다.
		2) ModelAndView
			- 컨트롤러에서 뷰로 전달할 데이터와 뷰에 정보를 담는 객체이다.
			- addAttribute() 메소드가 아니라, addObject() 메소드를 사용해서 데이터를 담을 수 있다.
 */

	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	public ModelAndView login(ModelAndView model,
			@RequestParam("id")String id, @RequestParam("password")String password) {
		
		log.info("{}, {}", id, password);
		
		Member loginMember = service.login(id, password);
		
		if(loginMember != null) {
			
			model.addObject("loginMember", loginMember);
			model.setViewName("redirect:/");
			
		} else {
			
			model.addObject("msg","아이디나 비밀번호가 일치하지 않습니다.");
			model.addObject("location", "/");
			model.setViewName("common/msg");
			
		}
				
		return model;
	}
	
	// 로그아웃 처리 (SessionStatus 객체 사용)
	@PostMapping("/logout")
	public String logout(SessionStatus status) {
		
		log.info("status.isComplete() : {}", status.isComplete());
		
		// SessionStatus 객체의 setComplete() 메소드로 세션 스코프로 확장된 객체들을 지워준다.
		status.setComplete();
		
		log.info("status.isComplete() : {}", status.isComplete());
		
		return "redirect:/";
	}
	
	
	@GetMapping("/member/enroll")
	public String enroll() {
		log.info("회원 가입 페이지 요청");
		return "member/enroll";
	}
	
	@PostMapping("/member/enroll")
	public String enroll(@ModelAttribute Member member) {
		
		log.info(member.toString());
		
		return "member/enroll";
	}
	

}
