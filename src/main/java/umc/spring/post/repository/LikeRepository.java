package umc.spring.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.post.data.entity.Like;

public interface LikeRepository extends JpaRepository<Like,Long> {
}
