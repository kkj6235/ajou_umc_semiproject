package umc.spring.post.config.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import umc.spring.post.data.dto.UserInfoDto;

public class SecurityUtil {
    public static UserInfoDto getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(authentication.getName());
        userInfoDto.setMemberRole(authentication.getAuthorities().stream().toList().get(0).toString().replaceAll("ROLE_", ""));

        return userInfoDto;
    }
}