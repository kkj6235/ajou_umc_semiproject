package umc.spring.post.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Post")
@Getter @Setter
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private String author;

    @Column(nullable = true)
    private Date createdTime;

    @Column(nullable = true)
    private Date modifiedTime;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

}
