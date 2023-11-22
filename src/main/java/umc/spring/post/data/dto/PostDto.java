package umc.spring.post.data.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PostDto {
    String title;
    String body;
    String image;
    int likeCount;
}
