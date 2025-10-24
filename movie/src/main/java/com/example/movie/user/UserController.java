package com.example.movie.user;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie.user.dto.UserExportView;
import com.example.movie.user.dto.UserProfileView;

@RestController
@RequestMapping("/api/users")
@Validated
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserProfileView profile(@PathVariable Long userId) {
        return userService.getProfile(userId);
    }

    @GetMapping("/{userId}/export")
    public UserExportView export(@PathVariable Long userId) {
        return userService.export(userId);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.deleteAccount(userId);
    }

    @GetMapping
    public List<UserProfileView> listAll() {
        return userService.listAll();
    }
}
