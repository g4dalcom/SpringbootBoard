package com.sparta.hanghaeboardproject.controller;

import com.sparta.hanghaeboardproject.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardRepository boardRepository;

    // 게시판 등록 화면 이동, 해석: /boardWrite로 오면 addBoardForm.html 을 보여줌
    @GetMapping("/boardWrite")
    public String boardWrite() {
        return "addBoardForm";
    }

    // 게시판 목록조회 화면 이동
    @GetMapping("/boards")
    public String getBoards() {
        return "/";
    }
}
