package com.example.user.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "member")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String username;
    private String remark;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private List<UserEx> memberExs;
}
