package com.example.user.repository;

import com.example.user.model.User;
import com.example.user.model.UserEx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u join u.memberExs ue on ue.usertype = :usertype where u.username = :username")
    User findByUsername(@Param("usertype") final String usertype, @Param("username") final String username);
}
