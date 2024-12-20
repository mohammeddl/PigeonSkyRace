package com.PigeonSkyRace.PigeonSkyRace.service;

import com.PigeonSkyRace.PigeonSkyRace.model.User;
import com.PigeonSkyRace.PigeonSkyRace.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return user;
    }

    public UserDetails syncUserWithKeycloak(String email, Collection<? extends GrantedAuthority> authorities) {
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createLocalUserFromKeycloak(email, authorities));
        return user;
    }

    private User createLocalUserFromKeycloak(String email, Collection<? extends GrantedAuthority> authorities) {
        User user = new User();
        user.setEmail(email);
        return userRepository.save(user);
    }
}