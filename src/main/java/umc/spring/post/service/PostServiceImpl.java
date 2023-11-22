package umc.spring.post.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.spring.post.config.security.SecurityUtil;
import umc.spring.post.data.dto.CommentDto;
import umc.spring.post.data.dto.PostDto;
import umc.spring.post.data.dto.PostResDto;
import umc.spring.post.data.dto.UserInfoDto;
import umc.spring.post.data.entity.Comment;
import umc.spring.post.data.entity.Post;
import umc.spring.post.data.entity.User;
import umc.spring.post.repository.CommentRepository;
import umc.spring.post.repository.PostRepository;
import umc.spring.post.repository.UserRepository;

import java.util.*;

import static umc.spring.post.config.security.SecurityUtil.getCurrentMemberId;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void upload(PostDto postDto){

        UserInfoDto userInfoDto = getCurrentMemberId();

        Post post = new Post();
        setPost(postDto, post);
        post.setAuthor(userInfoDto.getUserName());
        post.setUserId(userInfoDto.getUserId());
        post.setCreatedTime((new Date()));
        post.setModifiedTime(post.getCreatedTime());

        postRepository.save(post);
    }


    @Override
    public List<PostResDto> getAllPost(){
        List<Post> posts = postRepository.findAll();
        List<PostResDto> resDtos = new ArrayList<>();
        posts.forEach(post -> {
            PostResDto dto = PostResDto.toDTO(post);
            resDtos.add(dto);
        });
        return resDtos;
    }


    @Override
    public PostResDto getPostById(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("id가 존재하지 않습니다."));;
        return PostResDto.toDTO(post);
    }

    @Override
    public void likeCrew(Long id) {
        UserInfoDto userInfoDto = getCurrentMemberId();

        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("id가 존재하지 않습니다."));
        int likeCount = post.getLikeCount();
        post.setLikeCount(++likeCount);
        postRepository.save(post);
    }

    @Override
    public void dislikeCrew(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("id가 존재하지 않습니다."));
        int likeCount = post.getLikeCount();
        if(likeCount!=0){
            post.setLikeCount(--likeCount);
            postRepository.save(post);
        }
    }

    @Override
    public boolean deletePost(Long id) {
        if(postRepository.findById(id).isPresent()){
            postRepository.deleteById(id);
            return true;
        }
        else return false;
    }
    
    @Override
    public boolean editPost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("id가 존재하지 않습니다."));;
        if(post!=null){
            post.setTitle(postDto.getTitle() != null ? postDto.getTitle() : post.getTitle());
            post.setBody(postDto.getBody() != null ? postDto.getBody() : post.getBody());
            post.setImage(postDto.getImage() != null ? postDto.getImage() : post.getImage());
            post.setModifiedTime(new Date());
            postRepository.save(post);
            return true;
        }
        else return false;
    }

    @Override
    public List<PostResDto> search(String title) {
        List<Post> postList = postRepository.findAll();
        List<PostResDto> findList = new ArrayList<>();

        postList.forEach(post->{
            if(post.getTitle().equals(title)){
                findList.add(PostResDto.toDTO(post));
            }
        });
        return findList;
    }

    @Override
    public void addComment(CommentDto commentDto){
        Comment comment = setComment(commentDto);
        commentRepository.save(comment);
    }

    @Override
    public boolean deleteComment(Long id) {
        Optional<Comment> option = commentRepository.findById(id);
        if(option.isPresent()){
            Comment comment = option.get();
            Post post = comment.getPost();
            if(post!=null){
                post.getComments().removeIf(data ->
                        data.getId().equals(id)
                );
                postRepository.save(post);
            }
            return true;
        }
        else return false;
    }


    private Comment setComment(CommentDto commentDto) {
        Comment comment = new Comment();
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new RuntimeException("id가 존재하지 않습니다."));

        post.getComments().add(comment);
        comment.setPost(post);
        post.setAuthor(userInfoDto.getUserName());

        comment.setUserId(commentDto.getUserId());
        comment.setTimestamp(new Date());
        comment.setText(commentDto.getText());
        comment.setAuthor(commentDto.getAuthor());
        comment.setPostId(commentDto.getPostId());
        return comment;
    }

    private static void setPost(PostDto postDto, Post post) {
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setLikeCount(postDto.getLikeCount());
        post.setImage(postDto.getImage());
    }
}
