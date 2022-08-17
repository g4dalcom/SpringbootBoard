package com.sparta.hanghaeboardproject.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByCreatedAtDesc();

    @Override
    Page<Board> findAll(Pageable pageable);

//    Optional<PasswordDto> findByIdOrderById(Long id);
}
