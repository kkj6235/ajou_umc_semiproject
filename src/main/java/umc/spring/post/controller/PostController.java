package com.umc.post.controller;

import com.umc.post.data.dto.PostDto;
import com.umc.post.data.entity.Post;
import com.umc.post.service.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/upload")
    public void upload(@RequestBody PostDto postDto){
        postService.upload(postDto);
    }
    @GetMapping("")
    public List<Post> getAllPost(){
        return postService.getAllPost();

    }
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id){
        return postService.getPostById(id);
    }
}
