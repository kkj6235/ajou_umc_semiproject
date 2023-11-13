package umc.spring.post.data.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PostDto {
    Long userId;
    String title;
    String author;
    String body;
    String image;
    int likeCount;
}
