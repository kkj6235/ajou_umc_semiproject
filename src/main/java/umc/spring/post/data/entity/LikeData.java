package umc.spring.post.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Getter
@NoArgsConstructor
public class LikeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "postId",nullable = false)
    Post post;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "userId",nullable = false)
    User user;
}
