package umc.spring.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.board.domain.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long id);
}