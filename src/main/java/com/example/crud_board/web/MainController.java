package com.example.crud_board.web;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.crud_board.domain.BoardEntity;
import com.example.crud_board.dto.BoardDto;
import com.example.crud_board.dto.LoginDto;
import com.example.crud_board.service.BoardService;
import com.example.crud_board.service.LoginService;
import com.example.crud_board.service.SearchService;

@Controller
public class MainController {
	
	@Autowired
	LoginService loginService;
	@Autowired
	BoardService boardService;
	@Autowired
	SearchService searchService;
	
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
	public String board(Model model, @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable) {
		
		if(model.getAttribute("boardList") == null) {
			Page <BoardEntity> boardList = boardService.getAllBoardEntity_nonBlock(pageable);
			model.addAttribute("boardList", boardList.getContent());
			model.addAttribute("pageBoard", boardList);
		}
		
		Page <BoardEntity> tmp = (Page<BoardEntity>) model.getAttribute("pageBoard");
		
		int nowPage = tmp.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, tmp.getTotalPages());
		
		model.addAttribute("nowpage", nowPage);
		model.addAttribute("startpage", startPage);
		model.addAttribute("endpage", endPage);
		
		return "board";
	}
	
	@RequestMapping("/write")
	public String write() {
		return "write";
	}
	
	@RequestMapping("/writeresult")
	public String writeResult(@ModelAttribute BoardDto boardDto) {
		
		if(boardDto.getBoardId() == 0) {
			boardDto.setBoardId(0);
			boardDto.setBoardBlock(0);
			boardDto.setBoardDate(LocalDate.now());
			if(!boardDto.getBoardTitle().equals("") && !boardDto.getBoardContent().equals("") &&!boardDto.getUserId().equals("")) {
				boardService.setBoardEntity(boardDto);
			}
		}
		else {
			boardDto.setBoardDate(LocalDate.now());
			boardService.updateByBoardId(boardDto);
		}

		return "redirect:/board";
	}
	
	@RequestMapping("/searchresult")
	public String searchResult(RedirectAttributes rttr, Model model, @RequestParam String query, @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable) {
		query = query.trim();
		query = query.replaceAll("\\s+", " ");
		
		if(!query.equals("")) {
			Page <BoardEntity> res = searchService.searchResult(query, pageable);
			rttr.addFlashAttribute("boardList", res.getContent());
			rttr.addFlashAttribute("pageBoard", res);
		}
		
		return "redirect:/board";
	}
	
	@RequestMapping("/toblock")
	public String toBlock(HttpServletRequest request) {
		String[] list = request.getParameterValues("block");
		for(int i=0;i<list.length;i++) {
			boardService.setBlock(Integer.parseInt(list[i]));
		}
		
		return "redirect:/board";
	}
	
	@RequestMapping("/boardDetail/{boardId}")
	public String boardDetail(@PathVariable("boardId") int boardId, Model model) {
		BoardEntity be = boardService.getByBoardId(boardId);
		model.addAttribute("board", be);
		
		return "boardDetail";
	}
	
	@RequestMapping("/boardDetail/delete")
	public String deleteBoardDetail(int boardId) {
		boardService.deleteByBoardId(boardId);
		
		return "redirect:/board";
	}
	
	@RequestMapping("/boardDetail/update")
	public String updateBoardDetail(RedirectAttributes rttr, int boardId) {
		BoardEntity be = boardService.getByBoardId(boardId);
		rttr.addFlashAttribute("board", be);
		
		return "redirect:/write";
	}
}
