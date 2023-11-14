package umc.spring.post.service;


import umc.spring.post.config.security.TokenInfo;
import umc.spring.post.data.dto.UserInfoDto;
import umc.spring.post.data.dto.UserJoinDto;
import umc.spring.post.data.dto.UserLoginDto;

public interface AuthService {
    TokenInfo login(UserLoginDto userLoginDto);

    void join(UserJoinDto userJoinDto);

    UserInfoDto info();
}
