package com.example.movie.user.dto;

import java.time.Instant;

import com.example.movie.domain.user.UserRole;
import com.example.movie.domain.user.UserStatus;

public class UserProfileView {

    private Long id;
    private String username;
    private String nickname;
    private String avatarUrl;
    private UserRole role;
    private UserStatus status;
    private Instant createdAt;

    public UserProfileView() {
    }

    public UserProfileView(Long id, String username, String nickname, String avatarUrl, UserRole role,
            UserStatus status, Instant createdAt) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
