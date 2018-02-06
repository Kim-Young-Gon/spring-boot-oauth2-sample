package com.example.user.model;

import lombok.Data;

@Data
public class UserRs {
    private Long id;
    private String name;
    private String username;
    private String remark;
    private String password;
    private String usertype;
    private String desc;
}
