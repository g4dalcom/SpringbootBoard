package com.sparta.hanghaeboardproject.controller;

import com.sparta.hanghaeboardproject.config.UserDetailsImpl;
import com.sparta.hanghaeboardproject.repository.BoardRepository;
import com.sparta.hanghaeboardproject.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardRepository boardRepository;
    private final AccountService accountService;

    // 메인화면
    @GetMapping("/")
    public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boardPage", boardRepository.findAll(pageable));
        model.addAttribute("sortProperty", pageable.getSort().toString().contains("DESC") ? "desc" : "asc");
        model.addAttribute("keyword", "");
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login/kakao/callback")
    public String kakaoLogin(String code) throws JSONException {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        accountService.kakaoLogin(code);
        return "redirect:/";
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String admin(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "user/admin";
    }


    @GetMapping("/forbidden")
    public String forbidden() {
        return "forbidden";
    }
}