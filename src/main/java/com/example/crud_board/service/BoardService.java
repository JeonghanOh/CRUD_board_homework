package com.example.crud_board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.crud_board.domain.BoardEntity;
import com.example.crud_board.dto.BoardDto;
import com.example.crud_board.repository.BoardRepository;

@Service
public class BoardService {
	@Autowired
	BoardRepository boardRepository;
	
	public List<BoardEntity> getAllBoardEntity(){
		return boardRepository.getAllBoardEntity();
	}
	
	public Page<BoardEntity> getAllBoardEntity_nonBlock(Pageable pageable){
		Page<BoardEntity> ret = boardRepository.getAllBoardEntity_nonBlock(pageable);
		return ret;
	}
	
	public void setBoardEntity(BoardDto boardDto) {
		boardRepository.save(new BoardEntity(boardDto));
	}
	
	//dd
	public void setBlock(int boardId) {
		boardRepository.setBlock(boardId);
	}
	
	public BoardEntity getByBoardId(int boardId) {
		return boardRepository.getByBoardId(boardId);
	}
	
	public void deleteByBoardId(int boardId) {
		boardRepository.deleteByBoardId(boardId);
	}
	
	public void updateByBoardId(BoardDto boardDto) {
		boardRepository.updateByBoardId(boardDto.getBoardId(), boardDto.getBoardTitle(), boardDto.getBoardContent(), boardDto.getBoardFilePath());
	}
}
