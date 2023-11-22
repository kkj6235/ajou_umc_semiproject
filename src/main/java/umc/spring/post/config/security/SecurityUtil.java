package umc.spring.post.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import umc.spring.post.data.dto.UserInfoDto;
import umc.spring.post.data.entity.User;
import umc.spring.post.repository.UserRepository;

import java.util.Objects;

public class SecurityUtil {

    public static UserInfoDto getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            System.out.println("2번");
            throw new RuntimeException("No authentication information.");
        }
        MyUser myUser = (MyUser) authentication.getPrincipal();
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(myUser.getUserId());
        userInfoDto.setUserName(myUser.getUserName());
        userInfoDto.setLoginId(authentication.getName());
        userInfoDto.setMemberRole(authentication.getAuthorities().stream().toList().get(0).toString().replaceAll("ROLE_", ""));
        return userInfoDto;
    }
}