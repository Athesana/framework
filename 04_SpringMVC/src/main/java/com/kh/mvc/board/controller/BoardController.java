package com.kh.mvc.board.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.mvc.board.model.service.BoardService;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.common.util.FileProcess;
import com.kh.mvc.common.util.PageInfo;
import com.kh.mvc.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	/*
	 * 스프링에서 기본적으로 가지고 있는 Bean이다.
	 * classpath를 기준(접두어 classpath:) || 물리적인 파일 시스템 기준(접두어 file:) || webroot(webapp)를 기준(default)으로 리소스들을 쉽게 가져올 수 있는 기능을 제공한다.
	 */
	@Autowired
	private ResourceLoader resourceLoader;
	
	// 메소드의 리턴 타입이 void일 경우 Mapping URL을 유추해서 View를 찾는다.
//	@GetMapping("/board/list")
//	public void list(){
//	}

	// ▼ 페이징
	@GetMapping("/list")
	public ModelAndView list(
			ModelAndView model,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int count) {
		
//		int listCount = 0;
		PageInfo pageInfo = null;
		List<Board> list = null;
		
		log.info("page number : {}", page);
		
//		listCount = service.getBoardCount();
		
		pageInfo = new PageInfo(page, 10, service.getBoardCount(), count);
		
		list = service.getBoardList(pageInfo);
		
		model.addObject("pageInfo", pageInfo);
		model.addObject("list", list);
		model.setViewName("board/list");
		
		return model;
		
	}
	
	
	@GetMapping("/write")
	public String write(){

		return "/board/write";
	}
	
	
	@PostMapping("/write")
//	public ModelAndView write(ModelAndView model, @ModelAttribute Board board, @RequestParam("upfile") MultipartFile[] upfile) {
	public ModelAndView write(ModelAndView model, 
//			HttpServletRequest request, 
			@SessionAttribute(name = "loginMember") Member loginMember,
			@ModelAttribute Board board, @RequestParam("upfile") MultipartFile upfile) {
		
		int result = 0;
		
//		log.info(board.toString());
		// 파일을 업로드하지 않으면 빈문자열 (""), 파일을 업로드하면 "파일명"
		log.info("Upfile Name : {}", upfile.getOriginalFilename());
		// 파일을 업로드하지 않으면 true, 파일을 업로드하면 false
		log.info("Upfile is Empty : {}", upfile.isEmpty());
		
//		System.out.println(upfile[0].getOriginalFilename());
//		System.out.println(upfile[0].isEmpty());
//		System.out.println(upfile[1].getOriginalFilename());
//		System.out.println(upfile[1].isEmpty());
		
		// 1. 파일을 업로드했는지 확인 후, 파일을 저장하는 로직
		
		if(upfile != null && !upfile.isEmpty()) {
			// 실제 파일을 저장하는 로직 작성
			
			// 서버에서 저장되는 파일의 이름
			String renamedFileName = null;
			// 파일을 저장하는 경로(실제로 저장될 물리적인 위치를 알려줘야 한다.)
//			String location = request.getSession().getServletContext().getRealPath("resources/upload/board");
			
			String location = null;
			
			try {
				location = resourceLoader.getResource("resources/upload/board").getFile().getPath();
				// FileProcess에서 save수행된 결과를 renamedFileName에 담는다.
				renamedFileName = FileProcess.save(upfile, location);
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			
			// 이렇게 해야 DB의 테이블에 2개(오리지널, 리네임) 값이 저장되는 것이다.
			if(renamedFileName != null) {
				board.setOriginalFileName(upfile.getOriginalFilename());
				board.setRenamedFileName(renamedFileName);
			}
		}
		
		// 2. 작성한 게시글 데이터를 데이터베이스에 저장하는 로직
		
		// board객체에 작성자No값이 없어서 담아야한다. (보드 테이블 -> 멤버 테이블 참조하고 있는데, 해당하는 컬럼이 insert가 안되서 생기는 상황)
		// 컨트롤러에서 login 정보를 가져올 것인데 @SessionAttribute를 매개변수에 넣는다.
		board.setWriterNo(loginMember.getNo());
		result = service.save(board);
		
		
		if(result > 0) {
			model.addObject("msg", "게시글이 정상적으로 등록되었습니다.");
			model.addObject("location", "/board/view?no=" + board.getNo());
		} else {
			model.addObject("msg", "게시글 등록이 실패하였습니다.");
			model.addObject("location", "/board/write");
		}
		
		model.setViewName("common/msg");
		
		return model;
	}
	
	
	// ▼ 게시글 상세조회
	@GetMapping("/view")
	public ModelAndView view(ModelAndView model, @RequestParam("no") int no) {
		
		Board board = service.findBoardByNo(no);
		
		model.addObject("board", board);
		model.setViewName("board/view");
		
		return model;
	}
	
	// ▼ 파일 다운로드
	/*
	 * HttpEntity
	 * 	- 스프링 프레임워크에서 제공하는 클래스로 HTTP 요청 또는 응답에 해당하는 HTTP Header와 HTTP Body를 포함하는 클래스이다.
	 * ResponseEntity
	 * 	- HttpEntity를 상속하는 클래스로 HTTP 응답 데이터를 포함하는 클래스이다.
	 * 	- 개발자가 직접 HTTP Header, Body, Status를 세팅해서 반환할 수 있다.
	 * 		- Hedear : 값 세팅
	 * 		- Body 	 : 문자열
	 * 		- Status : 응답 자체가 성공인지 실패인지
	 * 	- 컨트롤러에서 ResponseEntity를 리턴하면 View에 대한 정보가 아니라 HTTP 정보를 반환한다.
	 * 
	 * @ResponseBody와의 차이점
	 * 	- @ResponseBody의 경우 Header값을 변경해야할 경우에는 직접 파라미터로 Response 객체를 받아와서 이 객체에서 Header를 변경해야 한다.
	 * 	- BUT! ResponseEntity는 객체를 생성한 뒤 객체에서 Header 값을 변경시키면 된다.
	 */
	@GetMapping("/fileDown")
	public ResponseEntity<Resource> fileDown(
			@RequestHeader(name = "user-agent") String userAgent,
			@RequestParam("oname") String oname, @RequestParam("rname") String rname) {
		String downName = null;
		Resource resource = null;
		
		try {
			resource = resourceLoader.getResource("resources/upload/board/" + rname);
			
			if(userAgent.indexOf("MSIE") != -1 || userAgent.indexOf("Trident") != -1) {
					downName = URLEncoder.encode(oname, "UTF-8").replaceAll("\\+", "%20");
				
			} else {
				downName = new String(oname.getBytes("UTF-8"), "ISO-8859-1");			
			}
			
			return ResponseEntity.ok()
								 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
								 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + downName + "\"")
								 .body(resource);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
