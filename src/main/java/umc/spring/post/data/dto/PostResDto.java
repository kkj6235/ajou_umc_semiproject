package umc.spring.post.data.dto;

import jakarta.persistence.*;
import lombok.*;
import umc.spring.post.data.entity.Comment;
import umc.spring.post.data.entity.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class PostResDto {
    Long id;
    Long userId;
    String title;
    String author;
    String body;
    String image;
    int likeCount;
    Date createdTime;
    Date modifiedTime;
    List<CommentResDto> comments;
    public static PostResDto toDTO(Post post){
        List<CommentResDto> resDtos = new ArrayList<>();
        post.getComments().forEach(comment -> {
            CommentResDto dto = CommentResDto.toDTO(comment);
            resDtos.add(dto);
        });
        return PostResDto.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .body(post.getBody())
                .image(post.getImage())
                .likeCount(post.getLikeCount())
                .createdTime(post.getCreatedTime())
                .modifiedTime(post.getModifiedTime())
                .comments(resDtos).build();
    }


}
