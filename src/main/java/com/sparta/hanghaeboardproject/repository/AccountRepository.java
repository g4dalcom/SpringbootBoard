package com.sparta.hanghaeboardproject.repository;

import com.sparta.hanghaeboardproject.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByUsername(String username);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByKakaoId(Long kakaoId);

}