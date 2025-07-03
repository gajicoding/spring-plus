package org.example.expert.domain.user.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;

@Getter
public class UserResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String imageUrl;
    private final UserRole userRole;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.imageUrl = user.getImageUrl();
        this.userRole = user.getUserRole();
    }
}
