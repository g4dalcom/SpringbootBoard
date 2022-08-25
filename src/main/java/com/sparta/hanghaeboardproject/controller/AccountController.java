package com.sparta.hanghaeboardproject.controller;


import com.sparta.hanghaeboardproject.dto.SignUpRequestDto;
import com.sparta.hanghaeboardproject.service.AccountService;
import com.sparta.hanghaeboardproject.validator.SignUpRequestDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final SignUpRequestDto signupRequestDto;
    private final SignUpRequestDtoValidator signUpRequestDtoValidator;

    @InitBinder("signUpRequestDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpRequestDtoValidator);
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute(new SignUpRequestDto());
        return "account/signup";
    }

    @PostMapping("/signup")
    public String signUpSubmit(@Valid SignUpRequestDto signUpRequestDto, Errors errors) {
        if(errors.hasErrors()) {
            return "account/signup";
        }
        accountService.saveNewAccount(signUpRequestDto);
        return "redirect:/login";
    }

}