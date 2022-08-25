package com.sparta.hanghaeboardproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //  .ignoringAntMatchers("/user/**");
        //	.ignoringAntMatchers("/api/products/**");

        http.authorizeRequests()
                .antMatchers("/images/**").permitAll()      // image 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()         // css 폴더를 login 없이 허용
                .antMatchers("/user/**").permitAll()        // 회원 관리 처리 API 전부를 login 없이 허용
                .anyRequest().authenticated()                          // 그 외 어떤 요청이든 '인증'
                .and()
                .formLogin()                                        // 로그인 기능
                .loginPage("/user/login")                           // 로그인 View 제공 (GET /user/login)
                .loginProcessingUrl("/user/login")                  // 로그인 처리 (POST / user/login)
                .defaultSuccessUrl("/")                             // 로그인 처리 후 성공 시 URL
                .failureUrl("/user/login?error")  // 로그인 처리 후 실패 시 URL
                .permitAll()
                .and()
                .logout()                                           // 로그아웃 기능
                .logoutUrl("/user/logout")                          // 로그아웃 요청처리 URL
                .permitAll()
                .and()
                .exceptionHandling()     // 예외처리
                .accessDeniedPage("/forbidden.html");  // "접근 불가" 페이지 URL 설정

        return http.build();
    }
}