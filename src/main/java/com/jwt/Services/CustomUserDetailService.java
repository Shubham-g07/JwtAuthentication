package com.jwt.Services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component
public class CustomUserDetailService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (userName.equals("Durgesh")) {
            User user = new User("Durgesh", "Durgesh123", new ArrayList<>());
            System.out.println("Loaded user: " + user.getUsername() + " | password=" + user.getPassword());
            return user;
        } else {
            throw new UsernameNotFoundException("User Not Found!!!");
        }
    }
}
