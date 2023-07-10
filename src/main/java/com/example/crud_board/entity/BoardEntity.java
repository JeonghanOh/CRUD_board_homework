package com.example.crud_board.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.crud_board.dto.BoardDto;

import lombok.Data;

@Entity
@Data
@Table(name="boardtbl")
public class BoardEntity {
	@Id
	@Column(name="board_id")
	private int boardId;
	
	@Column(name="board_title")
	private String boardTitle;
	
	@Column(name="board_content")
	private String boardContent;
	
	@Column(name="board_file_path")
	private String boardFilePath;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="board_date")
	private Date boardDate;
	
	@Column(name="board_block")
	private int boardBlock;
	
	public BoardEntity() {
		
	}
	
	public BoardEntity(BoardDto boardDto) {
		this.boardTitle = boardDto.getBoardTitle();
		this.boardContent = boardDto.getBoardContent();
		this.boardFilePath = null;
		this.userId = boardDto.getUserId();
		this.boardBlock = boardDto.getBoardBlock();
		this.boardDate = java.sql.Date.valueOf(boardDto.getBoardDate());
	}
}
