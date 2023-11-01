package com.hrms.usermanagement.security;

import com.hrms.usermanagement.repository.UserRepository;
import com.hrms.usermanagement.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HRMSUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        var userName = user.getUsername();
        var password = user.getPassword();
        var roles = userRoleRepository.findAllByUser(user.getUserId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.stream().forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role.getRole().getName().toUpperCase()));
                });
        return new org.springframework.security.core.userdetails.User(userName, password, authorities);
    }
}
