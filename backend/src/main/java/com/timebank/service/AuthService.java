package com.timebank.service;

import com.timebank.dto.AuthRequest;
import com.timebank.dto.AuthResponse;
import com.timebank.dto.RegisterRequest;
import com.timebank.dto.UserDTO;
import com.timebank.model.User;
import com.timebank.repository.UserRepository;
import com.timebank.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setTimeBalance(0);

        user = userRepository.save(user);
        String token = tokenProvider.generateToken(user.getEmail());

        return AuthResponse.builder()
            .token(token)
            .user(mapToDTO(user))
            .build();
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        String token = tokenProvider.generateToken(authentication);

        return AuthResponse.builder()
            .token(token)
            .user(mapToDTO(user))
            .build();
    }

    public void logout() {
        // Implement any necessary logout logic
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setTimeBalance(user.getTimeBalance());
        return dto;
    }
}