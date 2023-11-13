package umc.spring.post.service.service;


import org.springframework.stereotype.Service;
import umc.spring.post.data.dto.PostDto;
import umc.spring.post.data.entity.Post;
import umc.spring.post.repository.repository.PostRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void upload(PostDto postDto){
        Post post = new Post();
        setPost(postDto, post);
        post.setCreatedTime((new Date()));
        post.setModifiedTime(post.getCreatedTime());
        postRepository.save(post);
    }

    @Override
    public List<Post> getAllPost(){
        return postRepository.findAll();
    }


    @Override
    public Post getPostById(Long id){
        return postRepository.findById(id).get();
    }

    @Override
    public void likeCrew(Long id) {
        Post post = getPostById(id);
        int likeCount = post.getLikeCount();
        post.setLikeCount(++likeCount);
    }

    @Override
    public void dislikeCrew(Long id) {
        Post post = getPostById(id);
        int likeCount = post.getLikeCount();
        if(likeCount!=0){
            post.setLikeCount(--likeCount);
        }
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
    
    @Override
    public void editPost(PostDto postDto,Long id) {
        Post post = getPostById(id);
        setPost(postDto,post);
        post.setModifiedTime(new Date());
    }

    @Override
    public List<Post> search(String title) {
        List<Post> postList = postRepository.findAll();
        List<Post> findList = new ArrayList<>();

        postList.iterator().forEachRemaining(post->{
            if(post.getTitle().equals(title)){
                findList.add(post);
            }
        });
        return findList;
    }

    @Override
    public void createComment(Long id) {

        Post post = getPostById(id);

        post.set

    }

    private static void setPost(PostDto postDto, Post post) {
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setAuthor(postDto.getAuthor());
        post.setLikeCount(postDto.getLikeCount());
        post.setImage(postDto.getImage());
    }
}
