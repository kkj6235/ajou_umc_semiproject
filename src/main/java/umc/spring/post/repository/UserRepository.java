package umc.spring.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.post.data.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
}
