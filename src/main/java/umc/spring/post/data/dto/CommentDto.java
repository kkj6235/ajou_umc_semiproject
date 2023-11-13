package umc.spring.post.data.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Long postId;
    private Long userId;
    private String author;
    private String text;

}
