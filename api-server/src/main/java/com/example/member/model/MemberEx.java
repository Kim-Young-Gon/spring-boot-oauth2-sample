package com.example.member.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class MemberEx {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String usertype;
    private String desc;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberEx() {}

    public MemberEx(String password, String usertype, String desc) {
        this.password = password;
        this.usertype = usertype;
        this.desc = desc;
    }
}
