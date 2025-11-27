package com.bc46.trabea.auth;

import com.bc46.trabea.error.exception.ResourceNotFoundException;
import com.bc46.trabea.user.User;
import com.bc46.trabea.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String workEmail) throws UsernameNotFoundException {
        User user = userRepository.findByWorkEmail(workEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + workEmail + " is not found"));
        return AuthUserDetails.builder()
                .user(user)
                .build();
    }
}
