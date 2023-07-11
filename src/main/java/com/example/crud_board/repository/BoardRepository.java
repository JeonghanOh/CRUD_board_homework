package com.example.crud_board.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.crud_board.domain.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	@Query(value="select * from boardTbl", nativeQuery = true)
	public List<BoardEntity> getAllBoardEntity();
	
	@Query(value="select * from boardTbl where board_block = 0", nativeQuery = true)
	public Page<BoardEntity> getAllBoardEntity_nonBlock(Pageable pageable);
	
	@Query(value="SELECT * FROM boardTbl WHERE board_title regexp(:keyword) and board_block=0", nativeQuery = true)
    public Page<BoardEntity> getSearchResult(@Param("keyword") String keyword, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query(value="update boardTbl set board_block=1 where board_id = ?1", nativeQuery = true)
	public void setBlock(int boardId);
	
	@Query(value="select * from boardTbl where board_id = ?1", nativeQuery = true)
	public BoardEntity getByBoardId(int boardId);

	@Modifying
	@Transactional
	@Query(value="delete from boardTbl where board_id=?1", nativeQuery = true)
	public void deleteByBoardId(int boardId);
	
	@Modifying
	@Transactional
	@Query(value="update boardTbl set board_title=?2, board_content=?3 where board_id=?1", nativeQuery = true)
	public void updateByBoardId(int boardId, String boardTitle, String boardContent);
}
