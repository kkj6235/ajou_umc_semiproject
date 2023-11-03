package umc.spring.domain.board.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.spring.domain.board.Board;

@Getter
@Setter
public class BoardRequestDto {

    private String title;

    private String author;

    private String body;

    @Builder
    public BoardRequestDto(String title, String author, String body) {
        this.title = title;
        this.author = author;
        this.body = body;
    }

    public Board toBoard() {
        return Board.builder()
                .author(this.getAuthor())
                .title(this.getTitle())
                .body(this.getBody())
                .build();
    }
}
