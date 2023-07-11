package com.example.crud_board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.crud_board.domain.BoardEntity;
import com.example.crud_board.repository.BoardRepository;

@Service
public class SearchService {
	@Autowired
	BoardRepository boardRepository;
	public Page<BoardEntity> searchResult(String query, Pageable pageable){
		Page <BoardEntity> ret;
		
		String[] tmp = query.split(" ");
		String val = "";
		
		for(int i=0;i<tmp.length;i++) {
			val+=tmp[i];
			if(i < tmp.length - 1) {
				val += "|";
			}
		}
		
		ret = boardRepository.getSearchResult(val, pageable);
		
		return ret;
	}
}
