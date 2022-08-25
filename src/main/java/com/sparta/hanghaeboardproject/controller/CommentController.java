package com.sparta.hanghaeboardproject.controller;

import com.sparta.hanghaeboardproject.domain.Board;
import com.sparta.hanghaeboardproject.dto.CommentRequestDto;
import com.sparta.hanghaeboardproject.config.UserDetailsImpl;
import com.sparta.hanghaeboardproject.repository.BoardRepository;
import com.sparta.hanghaeboardproject.repository.CommentRepository;
import com.sparta.hanghaeboardproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.xml.stream.events.Comment;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/board/{boardId}/comments")
    public List<Comment> getCommentsByRecipeNo(@PathVariable Long boardId) {

        return commentRepository.findByBoardId(boardId);
    }

    @PostMapping("/board/{boardId}/comments")
    public ResponseEntity saveComment(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if(board == null) {
            return ResponseEntity.badRequest().build();
        }
        commentService.saveComment(board, commentRequestDto, userDetails.getAccount());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/board/{boardId}/comments/{commentId}")
    public void updateComment(@PathVariable Long boardId, @PathVariable Long commentId,
                              @RequestBody String comment) throws Exception {
        commentService.updateComment(commentId, comment);
    }

    @DeleteMapping("/board/{boardId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long boardId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(boardId, commentId, userDetails.getAccount().getId());
    }

}