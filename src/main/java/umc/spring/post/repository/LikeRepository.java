package umc.spring.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.post.data.entity.LikeData;
import umc.spring.post.data.entity.Post;
import umc.spring.post.data.entity.User;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeData, Long> {

    Optional<LikeData> findByPostAndUser(Post post, User user);

    List<LikeData> findLikeByPost(Post post);
}
