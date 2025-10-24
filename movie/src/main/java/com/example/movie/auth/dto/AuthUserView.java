package com.example.movie.auth.dto;

import com.example.movie.domain.user.UserRole;

public record AuthUserView(Long id, String username, String nickname, String avatarUrl, UserRole role) {
}
