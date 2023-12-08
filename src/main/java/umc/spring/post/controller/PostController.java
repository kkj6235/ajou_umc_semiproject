package umc.spring.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import umc.spring.post.data.dto.CommentDto;
import umc.spring.post.data.dto.PostDto;
import umc.spring.post.data.dto.PostResDto;
import umc.spring.post.service.PostService;

import java.util.List;


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
    public PostResDto upload(@RequestBody PostDto postDto){
        try{
            return postService.upload(postDto);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token not Found");
        }
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
        try {
            postService.likeCrew(id);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token not found");
        }
    }

    @DeleteMapping ("/post/likes")
    @ResponseStatus(HttpStatus.OK)
    public void dislikeCrew(@RequestParam Long id){
        try{
            postService.dislikeCrew(id);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token not found");
        }
    }

    @PostMapping("/post/search")
    public List<PostResDto> search(@RequestBody PostDto postDto){
        return postService.search(postDto.getTitle());
    }

    @PostMapping("/post/comments")
    @ResponseStatus(HttpStatus.OK)
    public void addComment(@RequestBody CommentDto commentDto){
        try{
            postService.addComment(commentDto);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token not found");

        }
    }

    @DeleteMapping("/post/comments")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@RequestParam Long id){
        if(!postService.deleteComment(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }
}
