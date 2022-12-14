package com.sparta.hanghaeboardproject.service;

import com.sparta.hanghaeboardproject.config.UserDetailsImpl;
import com.sparta.hanghaeboardproject.config.kakao.KakaoAccountInfo;
import com.sparta.hanghaeboardproject.config.kakao.KakaoOAuth2;
import com.sparta.hanghaeboardproject.domain.Account;
import com.sparta.hanghaeboardproject.domain.AccountRole;
import com.sparta.hanghaeboardproject.dto.SignUpRequestDto;
import com.sparta.hanghaeboardproject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoOAuth2 kakaoOAuth2;


    public void saveNewAccount(SignUpRequestDto signUpRequestDto) {
        signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        Account account = Account.builder()
                .username(signUpRequestDto.getUsername())
                .password(signUpRequestDto.getPassword())
                .role(AccountRole.ROLE_USER)
                .build();
        if(signUpRequestDto.isAdmin()) {
            account.setRole(AccountRole.ROLE_ADMIN);
        }
        accountRepository.save(account);
    }

    public void kakaoLogin(String authorizedCode) throws JSONException {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoAccountInfo accountInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = accountInfo.getId();
        String nickname = accountInfo.getNickname();

        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = nickname + "(카카오)";
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + UUID.randomUUID().toString();

        // DB 에 중복된 Kakao Id 가 있는지 확인
        Account kakaoUser = accountRepository.findByKakaoId(kakaoId).orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            if (accountRepository.existsByUsername(username)) {
                Account account = accountRepository.findByUsername(username).orElse(null);
                kakaoUser = account;
                kakaoUser.setKakaoId(kakaoId);
            } else {
                // 패스워드 인코딩
                String encodedPassword = passwordEncoder.encode(password);
                // ROLE = 사용자
                AccountRole role = AccountRole.ROLE_USER;

                kakaoUser = Account.builder()
                        .username(username)
                        .role(role)
                        .password(encodedPassword)
                        .kakaoId(kakaoId)
                        .build();
            }
            kakaoUser = accountRepository.save(kakaoUser);
        }

        // 로그인 처리
        UserDetailsImpl userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
