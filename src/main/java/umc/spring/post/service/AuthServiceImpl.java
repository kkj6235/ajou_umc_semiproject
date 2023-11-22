package umc.spring.post.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import umc.spring.post.data.entity.User;
import umc.spring.post.config.security.JwtTokenProvider;
import umc.spring.post.config.security.TokenInfo;
import umc.spring.post.data.dto.UserInfoDto;
import umc.spring.post.data.dto.UserJoinDto;
import umc.spring.post.data.dto.UserLoginDto;
import umc.spring.post.repository.UserRepository;


import java.security.Principal;
import java.util.Objects;

import static umc.spring.post.config.security.SecurityUtil.getCurrentMemberId;


@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public TokenInfo login(UserLoginDto userLoginDto) {
        User user = userRepository.findByLoginId(userLoginDto.getLoginId()).orElseThrow(() -> new UsernameNotFoundException("아이디 혹은 비밀번호를 확인하세요."));

        boolean matches = passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
        if (!matches) throw new BadCredentialsException("아이디 혹은 비밀번호를 확인하세요.");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getLoginId(), user.getPassword(), user.getAuthorities());
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, user.getId(),user.getUsername());
        tokenInfo.setEmail(user.getLoginId());
        tokenInfo.setMemberRole(user.getRole().toString());

        return tokenInfo;
    }

    @Override
    public void join(UserJoinDto userJoinDto) {
        User user = new User();
        user.setLoginId(userJoinDto.getLoginId());
        user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
        user.setUserName(userJoinDto.getUserName());
        userRepository.save(user);
    }

    @Override
    public UserInfoDto info() {
        return getCurrentMemberId();
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByLoginId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRole().toString())
                .build();
    }
}
