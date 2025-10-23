package com.example.movie.auth.dto;

import com.example.movie.domain.user.UserRole;

public record AuthUserView(Long id, String email, String nickname, String avatarUrl, UserRole role) {
}
