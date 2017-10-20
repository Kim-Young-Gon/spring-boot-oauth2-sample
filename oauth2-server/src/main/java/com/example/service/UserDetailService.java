package com.example.service;

import com.example.user.model.LoginUser;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Username not found.[%s]", username));
        }
        LoginUser loginUser = createUser(user);
        return loginUser;
    }

    private LoginUser createUser(final User user) {
        LoginUser loginUser = new LoginUser(user);
        loginUser.setRoles(Arrays.asList("ROLE_USER"));
        return loginUser;
    }
}
