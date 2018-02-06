package com.example.user.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "member_ex")
public class UserEx {
    @Id
    @GeneratedValue
    private Long id;
    private Long member_id;
    private String password;
    private String usertype;
    private String desc;
}
