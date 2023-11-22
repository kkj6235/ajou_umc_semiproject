package umc.spring.file.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import umc.spring.post.data.entity.Post;

@Entity
@Getter
public class S3File {

    @Id @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Post post;

    private String originalFileName;

    private String uploadFileName;

    private String uploadFilePath;

    private String uploadFileUrl;
}
