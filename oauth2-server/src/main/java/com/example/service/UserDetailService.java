package com.example.service;

import com.example.user.model.LoginUser;
import com.example.user.model.User;
import com.example.user.model.UserEx;
import com.example.user.model.UserRs;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private final String SPLIT_STR = ";;";

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final String[] params = username.split(SPLIT_STR);
        String usertypeParam = "N";
        String usernameParam = params[0];
        if (params != null && 1 < params.length) {
            usertypeParam = params[1];
        }
        final User user = userRepository.findByUsername(usertypeParam, usernameParam);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Username not found.[%s]", username));
        }
        final LoginUser loginUser = createUser(user, usertypeParam);
        return loginUser;
    }

    private LoginUser createUser(final User user, final String usertype) {
        final UserRs userRs = new UserRs();
        userRs.setId(user.getId());
        userRs.setName(user.getName());
        userRs.setUsername(user.getUsername() + SPLIT_STR + usertype);
        userRs.setRemark(user.getRemark());
        final Optional<List<UserEx>> optionalUserExes = Optional.ofNullable(user.getMemberExs());
        if (optionalUserExes.isPresent()) {
            final Optional<UserEx> optionalUserEx = optionalUserExes.get().stream().filter(e -> usertype.equals(e.getUsertype())).findFirst();
            if (optionalUserEx.isPresent()) {
                userRs.setPassword(optionalUserEx.get().getPassword());
                userRs.setUsertype(optionalUserEx.get().getUsertype());
                userRs.setDesc(optionalUserEx.get().getDesc());
            }
        }
        final LoginUser loginUser = new LoginUser(userRs);
        loginUser.setRoles(Arrays.asList("ROLE_USER"));
        return loginUser;
    }
}
