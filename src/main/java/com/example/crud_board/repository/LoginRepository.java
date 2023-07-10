package com.example.crud_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.crud_board.entity.LoginEntity;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Long> {
	@Query(value="select user_pw from userTbl where user_id = ?1", nativeQuery = true)
	public String getPassword(String userId);
}
