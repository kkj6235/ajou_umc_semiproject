package umc.spring.post.service.service;


import umc.spring.post.data.dto.PostDto;
import umc.spring.post.data.entity.Post;

import java.util.List;

public interface PostService {
    void upload(PostDto postDto);

    List<Post> getAllPost();

    Post getPostById(Long id);

    void likeCrew(Long id);

    void dislikeCrew(Long id);

    void deletePost(Long id);


    void editPost(PostDto postDto, Long id);

    List<Post> search(String title);


    void createComment(Long id);
}
