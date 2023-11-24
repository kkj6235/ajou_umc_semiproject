package umc.spring.post.service;


import jakarta.servlet.http.HttpServletResponse;
import umc.spring.post.data.dto.CommentDto;
import umc.spring.post.data.dto.PostDto;
import umc.spring.post.data.dto.PostResDto;
import umc.spring.post.data.entity.Post;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostResDto upload(PostDto postDto);

    List<PostResDto> getAllPost();

    PostResDto getPostById(Long id);

    void likeCrew(Long id);

    void dislikeCrew(Long id);

    boolean deletePost(Long id);


    boolean editPost(PostDto postDto, Long id);

    List<PostResDto> search(String title);


    void addComment(CommentDto commentDto);

    boolean deleteComment(Long id);
}
