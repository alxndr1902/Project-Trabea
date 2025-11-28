package com.bc46.trabea.user;

import com.bc46.trabea.error.exception.ResourceNotFoundException;
import com.bc46.trabea.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public User findUserByToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new UnauthorizedException("Authentication Not Found");
        }
        String authName = auth.getName();

        return userRepository.findById(authName)
                .orElseThrow(() -> new ResourceNotFoundException("User With Work Email " + SecurityContextHolder.getContext().getAuthentication().getName() + " Is Not Found"));
    }
}
