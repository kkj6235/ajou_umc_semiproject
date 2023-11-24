package umc.spring.post.service;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import umc.spring.file.domain.S3File;
import umc.spring.file.domain.S3FileDto;
import umc.spring.file.service.AmazonS3Service;
import umc.spring.post.data.dto.CommentDto;
import umc.spring.post.data.dto.PostDto;
import umc.spring.post.data.dto.PostResDto;
import umc.spring.post.data.dto.UserInfoDto;
import umc.spring.post.data.entity.Comment;
import umc.spring.post.data.entity.LikeData;
import umc.spring.post.data.entity.Post;
import umc.spring.post.data.entity.User;
import umc.spring.post.repository.CommentRepository;
import umc.spring.post.repository.LikeRepository;
import umc.spring.post.repository.PostRepository;
import umc.spring.post.repository.UserRepository;

import java.util.*;

import static umc.spring.post.config.security.SecurityUtil.getCurrentMemberId;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final AmazonS3Service amazonS3Service;

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final LikeRepository likeRepository;

    public PostServiceImpl(PostRepository postRepository, AmazonS3Service amazonS3Service, CommentRepository commentRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.amazonS3Service = amazonS3Service;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    @Override
    public PostResDto upload(PostDto postDto){
        UserInfoDto userInfoDto = getCurrentMemberId();
        Post post = new Post();
        setPost(postDto, post);
        post.setAuthor(userInfoDto.getUserName());
        post.setUserId(userInfoDto.getUserId());
        post.setCreatedTime((new Date()));
        post.setModifiedTime(post.getCreatedTime());

        postRepository.save(post);
        return PostResDto.toDTO(post);
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
    public boolean deletePost(Long id) {
        // 토큰 받은 유저의 post인지 확인하기..
        Optional<Post> byId = postRepository.findById(id);
        if(byId.isPresent()){
            UserInfoDto userInfoDto;
            try {
                userInfoDto = getCurrentMemberId();
            }
            catch(Exception e){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token not found");
            }
            if(Objects.equals(byId.get().getUserId(), userInfoDto.getUserId())){
                S3File s3File = byId.get().getS3File();
                amazonS3Service.deleteFile(s3File.getUploadFilePath(), s3File.getUploadFileName());
                postRepository.deleteById(id);
                return true;
            }
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: You do not have permission to delete this post.");
        }
        else return false;
    }

    @Override
    public boolean editPost(PostDto postDto, Long id) {
        // 토큰 받은 유저의 post인지
        Optional<Post> byId = postRepository.findById(id);

        if(byId.isPresent()){
            UserInfoDto userInfoDto;
            try {
                userInfoDto = getCurrentMemberId();
            }
            catch(Exception e){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token not found");
            }
            Post post = byId.get();
            if(Objects.equals(post.getUserId(), userInfoDto.getUserId())){
                post.setTitle(postDto.getTitle() != null ? postDto.getTitle() : post.getTitle());
                post.setBody(postDto.getBody() != null ? postDto.getBody() : post.getBody());
                post.setS3File(postDto.getS3File() != null ? postDto.getS3File() : post.getS3File());
                post.setModifiedTime(new Date());
                postRepository.save(post);
                return true;
            }
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: You do not have permission to edit this post.");
        }
        else return false;

    }
    @Override
    public void likeCrew(Long id) {
        UserInfoDto userInfoDto = getCurrentMemberId();
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("id가 존재하지 않습니다."));
        User user = userRepository.findByLoginId(userInfoDto.getLoginId()).orElseThrow();
        Optional<LikeData> byPostAndUser = likeRepository.findByPostAndUser(post, user);
        if(byPostAndUser.isEmpty()){
            LikeData likeData = new LikeData();
            post.getLikes().add(likeData);
            user.getLikes().add(likeData);
            likeData.setUser(user);
            likeData.setPost(post);
            likeRepository.save(likeData);
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이미 좋아요를 눌렀습니다.");
        }
    }

    @Override
    public void dislikeCrew(Long id) {
        UserInfoDto userInfoDto = getCurrentMemberId();
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("id가 존재하지 않습니다."));
        User user = userRepository.findByLoginId(userInfoDto.getLoginId()).orElseThrow();
        Optional<LikeData> byPostAndUser = likeRepository.findByPostAndUser(post, user);
        if(byPostAndUser.isPresent()){
            post.getLikes().removeIf(data ->
                    data.getPost().equals(post)
            );
            postRepository.save(post);

            user.getLikes().removeIf(data ->
                    data.getUser().equals(user)
            );
            userRepository.save(user);
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "좋아요를 누른 적이 없습니다");
        }
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
        // 토큰 받은 유저가 우리 회원인지

        UserInfoDto userInfoDto = getCurrentMemberId();
        Comment comment = setComment(commentDto);
        comment.setUserId(userInfoDto.getUserId());
        commentRepository.save(comment);
    }

    @Override
    public boolean deleteComment(Long id) {
        Optional<Comment> option = commentRepository.findById(id);
        if(option.isPresent()){
            UserInfoDto userInfoDto;
            try {
                 userInfoDto = getCurrentMemberId();
            }
            catch(Exception e){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token not found");
            }
            Comment comment = option.get();
            if(Objects.equals(userInfoDto.getUserId(), comment.getUserId())){
                Post post = comment.getPost();
                if(post!=null){
                    post.getComments().removeIf(data ->
                            data.getId().equals(id)
                    );
                    postRepository.save(post);
                }
                return true;
            }
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: You do not have permission to delete this comment.");
        }
        else return false;
    }


    private Comment setComment(CommentDto commentDto) {

        Comment comment = new Comment();
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new RuntimeException("id가 존재하지 않습니다."));
        post.getComments().add(comment);
        comment.setPostId(commentDto.getPostId());
        comment.setAuthor(commentDto.getAuthor());
        comment.setText(commentDto.getText());
        comment.setTimestamp(new Date());
        return comment;

    }

    private static void setPost(PostDto postDto, Post post) {
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setLikeCount(0);
        post.setS3File(postDto.getS3File());
    }
}
