package com.sparta.hanghaeboardproject.controller;

import com.sparta.hanghaeboardproject.config.UserDetailsImpl;
import com.sparta.hanghaeboardproject.domain.Board;
import com.sparta.hanghaeboardproject.domain.Account;
import com.sparta.hanghaeboardproject.dto.BoardDto;
import com.sparta.hanghaeboardproject.repository.BoardRepository;
import com.sparta.hanghaeboardproject.repository.AccountRepository;
import com.sparta.hanghaeboardproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final AccountRepository accountRepository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    // 게시글 등록화면
    @GetMapping("/boards/update")
    public String boardPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails == null) {
            return "/error/404";
        }
        model.addAttribute(new BoardDto());

        return "boards";
    }

    // 게시글 수정화면
    @GetMapping("/boards/update/{boardId}")
    public String boardUpdatePage(@PathVariable Long boardId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Board board  = boardRepository.findById(boardId).orElse(null);
        if(board == null || !board.getAccount().getUsername().equals(userDetails.getAccount().getUsername())) {
            return "error/404";
        }
        BoardDto boardRequestDto = new BoardDto();
        boardRequestDto.setTitle(board.getTitle());
        boardRequestDto.setContents(board.getContents());

        model.addAttribute(boardRequestDto);
        model.addAttribute("boardId", board.getId());

        return "board/update-dream";
    }

    // 게시글 전체조회
    @GetMapping("/boards")
    public String readBoards(Model model, String keyword,@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boardPage", boardRepository.findByTitleIgnoreCaseContains(pageable,keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("DESC") ? "desc" : "asc");
        return "index";
    }

    //게시글 등록
    @PostMapping("/boards")
    public String saveBoard(@Valid @ModelAttribute BoardDto boardRequestDto, Errors errors, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (errors.hasErrors()) {
            return "boards";
        }
        boardService.saveBoard(boardRequestDto, userDetails.getAccount());
        return "redirect:/";
    }

    // 게시글 수정 페이지
    @GetMapping("/boards/{boardId}")
    public String readBoard(@PathVariable Long boardId, Model model) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            return "error/404";
        }
        model.addAttribute("board",board);
        return "boards";
    }
    // 게시글 수정
    @PutMapping("/boards/{boardId}")
    public String updateBoard(@PathVariable Long boardId, @Valid @ModelAttribute BoardDto boardRequestDto, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            return "boards";
        }
        Board board = boardService.updateBoard(boardId, boardRequestDto);
        attributes.addFlashAttribute("message", "게시글을 수정했습니다.");

        return "redirect:" + "/boards/update/" + board.getId();
    }
    // 게시글 삭제
    @DeleteMapping("/boards/{boardId}")
    public String deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Board board = boardRepository.findById(boardId).orElse(null);
        Account account = accountRepository.findById(userDetails.getAccount().getId()).orElse(null);

        if(board == null || account == null) {
            return "error/404";
        }
        boardService.deleteBoard(board, account);
        return "redirect:/";
    }
}