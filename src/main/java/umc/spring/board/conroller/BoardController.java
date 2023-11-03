package umc.spring.board.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.spring.board.Board;
import umc.spring.board.dto.BoardRequestDto;
import umc.spring.board.dto.BoardResponseDto;
import umc.spring.board.repository.BoardRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class BoardController {

    private final BoardRepository boardRepository;

    @PostMapping("/upload")
    public void upload(@RequestBody BoardRequestDto boardDto){
        System.out.println(boardDto);
        Board board = boardDto.toBoard();
        boardRepository.save(board);
    }

    @GetMapping("/")
    public List<BoardResponseDto> getPosts(){
        return boardRepository.findAll().stream().map(BoardResponseDto::toDto).toList();
    }

    @GetMapping("/{id}")
    public BoardResponseDto getPost(@PathVariable(name="id") Long boardId){
        return BoardResponseDto.toDto(boardRepository.findById(boardId).orElseThrow(RuntimeException::new));
    }
}
