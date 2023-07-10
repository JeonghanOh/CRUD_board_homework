package com.example.crud_board.web;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.crud_board.dto.BoardDto;
import com.example.crud_board.dto.LoginDto;
import com.example.crud_board.entity.BoardEntity;
import com.example.crud_board.service.BoardService;
import com.example.crud_board.service.LoginService;

@Controller
public class MainController {
	
	@Autowired
	LoginService loginService;
	@Autowired
	BoardService boardService;
	
	@RequestMapping("/")
	public String main() {
		return "main";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/loginresult")
	public String loginResult(@ModelAttribute LoginDto loginDto, HttpSession session) {
		if(loginService.isOkLogin(loginDto)) {
			session.setAttribute("member", loginDto.getUserId());
			return "redirect:/";
		}
		
		return "login";
	}
	
	@RequestMapping("/board")
	public String board(Model model) {
		List <BoardEntity> boardList = boardService.getAllBoardEntity_nonBlock();
		System.out.println(boardList.size());
		
		model.addAttribute("boardList", boardList);
		return "board";
	}
	
	@RequestMapping("/write")
	public String write() {
		return "write";
	}
	
	@RequestMapping("/writeresult")
	public String writeResult(@ModelAttribute BoardDto boardDto) {
		boardDto.setBoardBlock(0);
		boardDto.setBoardDate(LocalDate.now());
		if(!boardDto.getBoardTitle().equals("") && !boardDto.getBoardContent().equals("")) {
			boardService.setBoardEntity(boardDto);
		}

		return "redirect:/board";
	}
}
