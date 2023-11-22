package umc.spring.post.data.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserInfoDto {
    private Long userId;
    private String userName;
    private String loginId;
    private String memberRole;
}