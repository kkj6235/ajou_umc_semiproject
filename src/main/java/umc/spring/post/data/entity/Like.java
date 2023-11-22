package umc.spring.post.data.entity;

import jakarta.persistence.*;

public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Long postId;
}
