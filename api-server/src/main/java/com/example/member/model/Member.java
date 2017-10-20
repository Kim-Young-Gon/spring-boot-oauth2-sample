package com.example.member.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Member {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String username;
	private String password;
	private String remark;

	public Member() {}

	public Member(String name, String username, String password, String remark) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.remark = remark;
	}
}