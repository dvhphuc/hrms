package com.hrms.usermanagement.service;

import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.repository.HRISUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HRISUserDetailsService implements UserDetailsService {
    private HRISUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) user
                .getRoles()
                .stream()
                .map(role -> new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return role.getName();
                    }
                });
        if (user ==  null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }
}