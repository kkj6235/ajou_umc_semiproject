package umc.spring.post.repository.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.post.data.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {

}
