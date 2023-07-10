package com.example.crud_board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="usertbl")
public class LoginEntity {
	@Id
	@Column(name="user_id")
	private String userId;
	@Column(name="user_pw")
	private String userPw;
}
