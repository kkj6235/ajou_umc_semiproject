package umc.spring.post.data.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import umc.spring.post.data.entity.Comment;
import umc.spring.post.data.entity.Post;

import java.util.Date;

import static umc.spring.post.config.security.SecurityUtil.getCurrentMemberId;

@Data
@Builder
public class CommentResDto {
    private Long id;
    private Long postId;
    private Long userId;
    private String author;
    private String text;
    private Date timestamp;

    public static CommentResDto toDTO(Comment comment){

        // 내가 쓴 글에 댓글이 있는경우는 쓴 사람의 토큰이 맞을경우만 가능하네..

        return CommentResDto.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .userId(comment.getUserId())
                .author(comment.getAuthor())
                .timestamp(comment.getTimestamp())
                .text(comment.getText()).build();
    }

}
