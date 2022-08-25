package com.sparta.hanghaeboardproject.repository;

import com.sparta.hanghaeboardproject.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUserId(Long userId);

    @Override
    Page<Board> findAll(Pageable pageable);

    Page<Board> findByTitleIgnoreCaseContains(Pageable pageable, String keyword);

//    Optional<PasswordDto> findByIdOrderById(Long id);
}
