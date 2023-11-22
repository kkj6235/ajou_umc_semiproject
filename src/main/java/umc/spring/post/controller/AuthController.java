package umc.spring.post.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.spring.post.config.security.TokenInfo;
import umc.spring.post.data.dto.UserInfoDto;
import umc.spring.post.data.dto.UserJoinDto;
import umc.spring.post.data.dto.UserLoginDto;
import umc.spring.post.service.AuthService;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody UserLoginDto userLoginDto) {
        return authService.login(userLoginDto);
    }
    @PostMapping("/register")
    public void register(@RequestBody UserJoinDto userJoinDto) {
        authService.join(userJoinDto);
    }

    @GetMapping("/info")
    public UserInfoDto info() {
        return authService.info();
    }
}
