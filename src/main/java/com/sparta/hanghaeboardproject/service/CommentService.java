package com.sparta.hanghaeboardproject.service;

import com.sparta.hanghaeboardproject.domain.Account;
import com.sparta.hanghaeboardproject.domain.Board;
import com.sparta.hanghaeboardproject.dto.CommentRequestDto;
import com.sparta.hanghaeboardproject.repository.AccountRepository;
import com.sparta.hanghaeboardproject.repository.BoardRepository;
import com.sparta.hanghaeboardproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.xml.stream.events.Comment;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;


    @Transactional
    public void saveComment(Board board, CommentRequestDto commentRequestDto, Account account) {

        Comment comment = Comment.builder().comment(commentRequestDto.getComment()).build();
        Comment newComment = commentRepository.save(comment);
        board.addComment((com.sparta.hanghaeboardproject.domain.Comment) newComment);
        account.addComment((com.sparta.hanghaeboardproject.domain.Comment) newComment);
    }
    @Transactional
    public void updateComment(Long commentId, String stringComment) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        comment.updateComment(stringComment);
    }
    @Transactional
    public void deleteComment(Long boardId, Long commentId, Long accountId) {
        Comment deleteComment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
        );
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        board.deleteComment((com.sparta.hanghaeboardproject.domain.Comment) deleteComment);
        account.deleteComment((com.sparta.hanghaeboardproject.domain.Comment) deleteComment);
        commentRepository.deleteById(((com.sparta.hanghaeboardproject.domain.Comment) deleteComment).getId());

    }
}