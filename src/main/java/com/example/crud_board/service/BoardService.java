package com.example.crud_board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud_board.dto.BoardDto;
import com.example.crud_board.entity.BoardEntity;
import com.example.crud_board.repository.BoardRepository;

@Service
public class BoardService {
	@Autowired
	BoardRepository boardRepository;
	
	public List<BoardEntity> getAllBoardEntity(){
		return boardRepository.getAllBoardEntity();
	}
	
	public List<BoardEntity> getAllBoardEntity_nonBlock(){
		return boardRepository.getAllBoardEntity_nonBlock();
	}
	
	public void setBoardEntity(BoardDto boardDto) {
		boardRepository.save(new BoardEntity(boardDto));
	}
}
