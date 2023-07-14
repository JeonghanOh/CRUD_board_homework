package com.example.crud_board.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.crud_board.domain.BoardEntity;
import com.example.crud_board.dto.BoardDto;
import com.example.crud_board.dto.LoginDto;
import com.example.crud_board.dto.LoginStatusResponse;
import com.example.crud_board.service.BoardService;
import com.example.crud_board.service.LoginService;
import com.example.crud_board.service.SearchService;

@RestController
@RequestMapping("/api")
public class MainController {
	@Autowired
	BoardService boardService;

	@Autowired
	LoginService loginService;
	
	@Autowired
	SearchService searchService;

	@GetMapping("/board")
	public Page<BoardEntity> board(@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "board_id,desc") String sortExpression) {
		Sort sort = Sort.by(sortExpression.split(",")[0]).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);
	    
		return boardService.getAllBoardEntity_nonBlock(pageable);
	}
	
	@GetMapping("/detail/{boardId}")
	public BoardEntity detail(@PathVariable("boardId") int boardId) {
		BoardEntity be = boardService.getByBoardId(boardId);
		
		return be;
	}
	
	@GetMapping("/delete/{boardId}")
	public String delete(@PathVariable("boardId") int boardId) {
		boardService.deleteByBoardId(boardId);
		
		return "success";
	}
	
	@GetMapping("/update/{boardId}")
	public String update(@PathVariable("boardId") int boardId) {
		
		return "ok";
	}
	
	@GetMapping("/toblock/{boardId}")
	public String toBlock(@PathVariable("boardId") int boardId) {
		boardService.setBlock(boardId);
		
		return "ok";
	}
	
	@PostMapping("/loginstatus")
	public String loginStatus(HttpSession session) {
		return (String)session.getAttribute("member");
	}
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpSession session) {
        // 로그인 요청 처리 로직
        // 예: 사용자 인증, 세션 생성 등
		
        // 세션에 로그인 상태 저장
		if(loginService.isOkLogin(loginDto)) {
			session.setAttribute("isLoggedIn", true);
			session.setAttribute("member", loginDto.getUsername());
	        return ResponseEntity.ok().build();
		}

        // 로그인 성공 응답 반환
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패!");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        // 로그아웃 요청 처리 로직
        // 예: 세션 무효화 등

        // 세션에서 로그인 상태 제거
        session.removeAttribute("isLoggedIn");
    	session.invalidate();

        // 로그아웃 성공 응답 반환
        return ResponseEntity.ok().build();
    }

	//dddddddddd
    @GetMapping("/check-login")
    public boolean checkLoginStatus(HttpSession session) {
        // 세션에서 로그인 상태 확인
    	
        boolean isLoggedIn = session.getAttribute("isLoggedIn") != null;

    	System.out.println("isLoggedIn : " + isLoggedIn);
    	
        // 로그인 상태 반환
        return isLoggedIn;
    }
	
	@RequestMapping("/write/{boardId}")
	public ResponseEntity<String> write_boardId(@PathVariable("boardId") int boardId, @RequestBody BoardDto boardDto) {
		System.out.println("write/boardId");
		System.out.println(boardId + "/" + boardDto.getBoardTitle());
		boardDto.setBoardId(boardId);
		boardService.updateByBoardId(boardDto);
        return ResponseEntity.ok("Post updated successfully!");
	}
	
	@RequestMapping("/write")
	public ResponseEntity<String> write(@RequestBody BoardDto boardDto, HttpSession session){
		System.out.println("check");
		
		try {
	        // 받은 데이터를 이용하여 글 작성 로직을 수행
	        // 예: 데이터베이스에 저장 등
			boardDto.setUserId((String)session.getAttribute("member"));
			boardDto.setBoardBlock(0);
			boardDto.setBoardDate(LocalDate.now());
			boardService.setBoardEntity(boardDto);
	        return ResponseEntity.ok("Post created successfully!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create post.");
	    }
	}
	
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
	    if (file.isEmpty()) {
	        // 파일이 비어있는 경우 처리
	    }
	    
	    try {
	    	String originalFileName = file.getOriginalFilename();
	        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
	        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
	        
	        Path filePath = Paths.get("C:/Users/howon/Desktop/UploadServerFile/" + uniqueFileName);
            Files.write(filePath, file.getBytes());
	        
	        return filePath.toString();
	    } catch (IOException e) {
	        // 파일 저장 실패 처리
	        return null;
	    }
	}
	
	@RequestMapping("/searchresult")
	public Page <BoardEntity> searchResult(@RequestParam String query, @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "board_id,desc") String sortExpression) {

		Sort sort = Sort.by(sortExpression.split(",")[0]).descending();
	    Pageable pageable = PageRequest.of(page, size, sort);
	    
		query = query.trim();
		query = query.replaceAll("\\s+", " ");
		
		if(!query.equals("")) {
			return searchService.searchResult(query, pageable);
		}
		else {
			return boardService.getAllBoardEntity_nonBlock(pageable);
		}
	}
}
