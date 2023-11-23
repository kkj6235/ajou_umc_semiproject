package umc.spring.post.data.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import umc.spring.file.domain.S3File;

@Data
public class PostDto {
    String title;
    String body;
    int likeCount;
    S3File s3File;
}
