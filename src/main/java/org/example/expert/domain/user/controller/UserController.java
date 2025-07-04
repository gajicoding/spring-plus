package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/users")
    public void changePassword(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody UserChangePasswordRequest userChangePasswordRequest
    ) {
        userService.changePassword(authUser.getId(), userChangePasswordRequest);
    }

    @GetMapping("/users")
    public ResponseEntity<List<String>> findUsersByNickname(
            @RequestParam(defaultValue = "") String nickname
    ) {
        return ResponseEntity.ok(userService.findUsersByNickname(nickname));
    }

    @PatchMapping("/users/profile-image")
    public ResponseEntity<UserResponse> saveProfileImage(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(userService.saveProfileImage(authUser.getId(), file));
    }
}
