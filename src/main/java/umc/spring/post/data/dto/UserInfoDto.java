package umc.spring.post.data.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserInfoDto {
    private String loginId;
    private String memberRole;

}