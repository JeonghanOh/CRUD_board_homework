package com.example.crud_board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.crud_board.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	@Query(value="select * from boardTbl", nativeQuery = true)
	public List<BoardEntity> getAllBoardEntity();
	
	@Query(value="select * from boardTbl where board_block = 0", nativeQuery = true)
	public List<BoardEntity> getAllBoardEntity_nonBlock();
}
