package com.sparta.hanghaeboardproject.service;

import com.sparta.hanghaeboardproject.domain.*;
import com.sparta.hanghaeboardproject.dto.BoardDto;
import com.sparta.hanghaeboardproject.repository.AccountRepository;
import com.sparta.hanghaeboardproject.repository.BoardRepository;
import com.sparta.hanghaeboardproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;

    public void saveBoard(BoardDto boardRequestDto, Account account) {
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .contents(boardRequestDto.getContents())
                .build();

        Board newBoard =  boardRepository.save(board);
        account.addBoard(newBoard);
    }

    public Board updateBoard(Long boardId, BoardDto boardRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        board.updateBoard(boardRequestDto);
        return board;
    }

    public void deleteBoard(Board board, Account account) {
        List<Comment> commentList = commentRepository.findAllByBoardId(board.getId());

        board.deleteComments(commentList);
        commentRepository.deleteAll((Iterable<? extends javax.xml.stream.events.Comment>) commentList);

        for(com.sparta.hanghaeboardproject.domain.Comment comment : commentList) {
            Account havenCommentAccount = accountRepository.findById(comment.getAccount().getId()).orElseThrow(
                    () -> new IllegalArgumentException()
            );
            havenCommentAccount.deleteComment(comment);
        }
        account.deleteBoard(board);
        boardRepository.delete(board);
    }

}
