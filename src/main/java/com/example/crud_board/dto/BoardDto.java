package com.example.crud_board.dto;

import java.time.LocalDate;

import lombok.Data;

@Data

public class BoardDto {
	private int boardId;
	private String boardTitle;
	private String boardContent;
	private String boardFilePath;
	private String userId;
	private LocalDate boardDate;
	private int boardBlock;
}
