package umc.spring.domain.board;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import umc.spring.domain.member.Member;

import java.sql.Date;

@Entity
@Getter
@Setter
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String body;

    @CreationTimestamp
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    protected Board(){}

    @Builder
    public Board(String title, String author, String body) {
        this.title = title;
        this.author = author;
        this.body = body;
    }
}
