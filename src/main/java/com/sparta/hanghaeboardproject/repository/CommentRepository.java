package com.sparta.hanghaeboardproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import javax.xml.stream.events.Comment;
import java.util.List;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoardId(Long boardId);

    List<com.sparta.hanghaeboardproject.domain.Comment> findAllByBoardId(Long id);
}
