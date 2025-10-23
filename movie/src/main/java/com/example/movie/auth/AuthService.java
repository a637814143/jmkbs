package com.example.movie.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.movie.auth.dto.AuthUserView;
import com.example.movie.auth.dto.LoginRequest;
import com.example.movie.auth.dto.RegisterRequest;
import com.example.movie.domain.user.User;
import com.example.movie.domain.user.UserRepository;
import com.example.movie.domain.user.UserRole;
import com.example.movie.domain.user.UserStatus;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthUserView register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "邮箱已被注册");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        User savedUser = userRepository.save(user);
        return toView(savedUser);
    }

    @Transactional(readOnly = true)
    public AuthUserView login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "邮箱或密码错误"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "邮箱或密码错误");
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "账号不可用，请联系管理员");
        }
        return toView(user);
    }

    private AuthUserView toView(User user) {
        return new AuthUserView(user.getId(), user.getEmail(), user.getNickname(), user.getAvatarUrl(), user.getRole());
    }
}
