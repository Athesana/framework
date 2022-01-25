package com.kh.mvc.board.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ModelAndView write(ModelAndView model, HttpServletRequest request, 
			@SessionAttribute(name = "loginMember") Member loginMember,
			@ModelAttribute Board board, @RequestParam("upfile") MultipartFile upfile) {
		
		int result = 0;
		
		log.info(board.toString());
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
			String renamedFileName = null;
			String location = request.getSession().getServletContext().getRealPath("resources/upload/board");
			
			renamedFileName = FileProcess.save(upfile, location);
			
			if(renamedFileName != null) {
				board.setOriginalFileName(upfile.getOriginalFilename());
				board.setRenamedFileName(renamedFileName);
			}
		}
		
		// 2. 작성한 게시글 데이터를 데이터베이스에 저장하는 로직
		board.setWriterNo(loginMember.getNo());
		result = service.save(board);
		
		
		if(result > 0) {
			model.addObject("msg", "게시글이 정상적으로 등록되었습니다.");
			model.addObject("location", "/board/list");
		} else {
			model.addObject("msg", "게시글 등록이 실패하였습니다.");
			model.addObject("location", "/board/write");
		}
		
		model.setViewName("common/msg");
		
		return model;
	}
	
	
	
}
