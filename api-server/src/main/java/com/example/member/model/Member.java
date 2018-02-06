package com.example.member.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String username;
	private String remark;

	@OneToMany(mappedBy = "member")
	private List<MemberEx> memberExs;

	public Member() {}

	public Member(String name, String username, String remark) {
		this.name = name;
		this.username = username;
		this.remark = remark;
	}
}