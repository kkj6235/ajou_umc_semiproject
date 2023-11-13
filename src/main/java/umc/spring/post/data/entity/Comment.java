package umc.spring.post.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "postId", nullable = false,insertable=false, updatable=false)
    private Post post;

    @Column(nullable = false)
    private Date timestamp;

}
