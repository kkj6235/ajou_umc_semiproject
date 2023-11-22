package umc.spring.post.config.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUser extends User {

    Long userId;
    String userName;

    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Claims claims) {
        super(username, password, authorities);
        this.userId = (long) ((Integer) claims.get("userId")).intValue();
        this.userName = claims.get("userName").toString();
    }

    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Claims claims) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }
}
