package com.payconiq.assignment.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    // TO-DO: 25/08/2022 need to get user detail from db
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("payconiqUser", new BCryptPasswordEncoder(12).encode("testpassword"), new ArrayList<>());
    }
}
