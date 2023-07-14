package com.example.crud_board.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.crud_board.domain.BoardEntity;

import lombok.Data;

@Data
public class PageBoard {
	private List <BoardEntity> list;
	private Page <BoardEntity> page;
	private int nowPage;
	private int startPage;
	private int endPage;
}
