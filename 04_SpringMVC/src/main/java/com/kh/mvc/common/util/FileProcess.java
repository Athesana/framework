package com.kh.mvc.common.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileProcess {
	// static으로 만들면 객체를 만들지 않고 FileProcess.~~ 이렇게 사용할 수 있다는 장점이 있다.
	public static String save(MultipartFile upfile, String location) {
		String renamedFileName = null;
		String originalFileName = upfile.getOriginalFilename();
		
		log.info("Upfile Name : {}", originalFileName);
		log.info("location : {}", location);
		
//		location이 실제로 존재하지 않으면 폴더를 생성하는 로직
		// 아직, 실제로 물리적인것이 아니라 메모리에 location을 가지고 파일 객체의 정보만 가지고 있다.
		File folder = new File(location);
		
		// folder가 없으면, mkdirs() 해라(있으면 안 만듦)
		if(!folder.exists()) {
			folder.mkdirs();
		} 
		
		// 사용자가 업로드한 파일명을 renamedFileName으로 이렇게 바꾸겠다.
		renamedFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS")) + 
							originalFileName.substring(originalFileName.lastIndexOf("."));
		
		try {
			// 사용자가 업로드한 파일 데이터를 지정한 파일에(새로운 File 객체에, 메모리상에 올려져있는 객체(아직 저장x)) 새로 저장한다.
			upfile.transferTo(new File(location + "/" + renamedFileName));
		} catch (IllegalStateException | IOException e) {
			log.error("파일 전송 에러");
			e.printStackTrace();
		}	
		
		return renamedFileName;
	}

	public static void delete(String location) {
		log.info("location : {}", location);
		
		File file = new File(location);
		
		if(file.exists()) {
			file.delete();
		}
		
	}
	
}
