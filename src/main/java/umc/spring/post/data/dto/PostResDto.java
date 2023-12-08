package umc.spring.post.data.dto;

import jakarta.persistence.*;
import lombok.*;
import umc.spring.file.domain.S3File;
import umc.spring.post.data.entity.Comment;
import umc.spring.post.data.entity.LikeData;
import umc.spring.post.data.entity.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static umc.spring.post.config.security.SecurityUtil.getCurrentMemberId;

@Data
@Builder
public class PostResDto {

    Long id;
    Long userId;
    String title;
    String author;
    String body;
    int likeCount;
    S3File s3File;
    Date createdTime;
    Date modifiedTime;
    List<CommentResDto> comments;
    boolean isHeart;


    public static PostResDto toDTO(Post post){
        List<CommentResDto> resDtos = new ArrayList<>();

        post.getComments().forEach(comment -> {
            CommentResDto dto = CommentResDto.toDTO(comment);
            resDtos.add(dto);
        });
        UserInfoDto userInfoDto;

        boolean flag=false;

        try {
            // 유저 모드
            userInfoDto = getCurrentMemberId();
            Long userId = userInfoDto.getUserId();
            List<LikeData> likes = post.getLikes();
            for (LikeData likeData : likes) {
                if (likeData.getUser().getId().equals(userId)) {
                    flag = true;
                    break;
                }
            }
        } catch (Exception e) {
            // 게스트 모드
            return PostResDto.builder()
                    .id(post.getId())
                    .userId(post.getUserId())
                    .s3File(post.getS3File())
                    .title(post.getTitle())
                    .author(post.getAuthor())
                    .body(post.getBody())
                    .likeCount(post.getLikes().size())
                    .createdTime(post.getCreatedTime())
                    .modifiedTime(post.getModifiedTime())
                    .comments(resDtos)
                    .isHeart(flag).build();
        }

        return PostResDto.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .s3File(post.getS3File())
                .title(post.getTitle())
                .author(post.getAuthor())
                .body(post.getBody())
                .likeCount(post.getLikes().size())
                .createdTime(post.getCreatedTime())
                .modifiedTime(post.getModifiedTime())
                .comments(resDtos)
                .isHeart(flag).build();
    }
}
