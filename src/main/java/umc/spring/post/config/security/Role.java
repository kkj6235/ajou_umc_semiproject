package umc.spring.post.config.security;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    public String getValue() {
        return value;
    }

    Role(String value) {
        this.value = value;
    }
}