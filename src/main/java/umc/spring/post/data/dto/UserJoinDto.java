package umc.spring.post.data.dto;

import lombok.Data;

@Data
public class UserJoinDto {
    private String userId;
    private String password;
    private String userName;
}