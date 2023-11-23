package umc.spring.file.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import umc.spring.post.data.entity.Post;

@Getter
@Embeddable
public class S3File {

    private String originalFileName;

    private String uploadFileName;

    private String uploadFilePath;

    private String uploadFileUrl;
}
