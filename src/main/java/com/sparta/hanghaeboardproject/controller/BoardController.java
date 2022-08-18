package com.sparta.hanghaeboardproject.controller;

import com.sparta.hanghaeboardproject.domain.Board;
import com.sparta.hanghaeboardproject.domain.BoardDto;
import com.sparta.hanghaeboardproject.domain.BoardRepository;
import com.sparta.hanghaeboardproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    // 게시판 전체 조회
    @GetMapping("/api/boards")
    public Page<Board> getBoards(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    //  게시글 상세보기
    @GetMapping("/api/board/{id}")
    public Board getBoard(@PathVariable Long id) {
        return boardRepository.findById(id).get();
    }


    //  게시글 작성
    @PostMapping("/api/board")
    public Board createBoard(@RequestBody BoardDto boardDto) {
        Board board = new Board(boardDto);
        return boardRepository.save(board);
    }



    //  게시글 수정
    @PutMapping("/api/board/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        Optional<Board> board = boardRepository.findById(id);
        if(board.isPresent()) {
            System.out.println("board.get().getPassword() : " + board.get().getPassword() + "boardDto.getPassword() : " + boardDto.getPassword());
            if(board.get().getPassword().equals(boardDto.getPassword())) {
                return boardService.update(id, boardDto);
            } else {
                System.out.println("비밀번호 틀림.");
                return 0L;
            }
        }
        return id;
    }


    //  게시글 삭제
    @DeleteMapping("/api/board/{id}")
    public Long deleteBoard(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        Optional<Board> board = boardRepository.findById(id);
        if(board.isPresent()) {
            System.out.println("board.get().getPassword() : " + board.get().getPassword() + "boardDto.getPassword() : " + boardDto.getPassword());
            if(board.get().getPassword().equals(boardDto.getPassword())) {
                boardRepository.deleteById(id);
            } else {
                System.out.println("비밀번호 틀림.");
                return 0L;
            }
        }
        return id;
    }
}