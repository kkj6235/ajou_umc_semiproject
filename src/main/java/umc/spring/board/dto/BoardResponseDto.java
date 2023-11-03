package umc.spring.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.spring.board.Board;

@Getter
@Setter
public class BoardResponseDto {

    private Long id;

    private String title;

    private String author;

    private String body;

    @Builder
    public BoardResponseDto(Long id, String title, String author, String body) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.body = body;
    }

    public static BoardResponseDto toDto(Board board){
        return BoardResponseDto.builder()
                .id(board.getId())
                .body(board.getBody())
                .title(board.getTitle())
                .author(board.getAuthor())
                .build();
    }
}
