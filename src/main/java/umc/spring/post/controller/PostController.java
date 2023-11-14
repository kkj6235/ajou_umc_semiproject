package umc.spring.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import umc.spring.post.data.dto.CommentDto;
import umc.spring.post.data.dto.PostDto;
import umc.spring.post.data.dto.PostResDto;
import umc.spring.post.data.entity.Post;
import umc.spring.post.service.PostService;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
public class PostController {
    @Autowired
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts")
    public List<PostResDto> getAllPost(){
        return postService.getAllPost();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/post/upload")
    public void upload(@RequestBody PostDto postDto){
        postService.upload(postDto);
    }

    @GetMapping("/post/{id}")
    public PostResDto getPostById(@PathVariable Long id){
        return postService.getPostById(id);
    }

    @DeleteMapping("/post/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable Long id){
        if(!postService.deletePost(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }
    @PutMapping("/post/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editPost(@RequestBody PostDto postDto,@PathVariable Long id){
        if(!postService.editPost(postDto,id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }
    @PostMapping("/post/likes")
    @ResponseStatus(HttpStatus.OK)
    public void likeCrew(@RequestParam Long id){
        postService.likeCrew(id);
    }
    @DeleteMapping ("/post/likes")
    @ResponseStatus(HttpStatus.OK)
    public void dislikeCrew(@RequestParam Long id){
        postService.dislikeCrew(id);
    }

    @PostMapping("/post/search")
    public List<PostResDto> search(@RequestBody PostDto postDto){
        return postService.search(postDto.getTitle());
    }

    @PostMapping("/post/comments")
    @ResponseStatus(HttpStatus.OK)
    public void addComment(@RequestBody CommentDto commentDto){
        postService.addComment(commentDto);
    }

    @DeleteMapping("/post/comments")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@RequestParam Long id){
        if(!postService.deleteComment(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }
}
