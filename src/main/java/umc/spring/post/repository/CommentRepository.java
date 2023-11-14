package umc.spring.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.post.data.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
