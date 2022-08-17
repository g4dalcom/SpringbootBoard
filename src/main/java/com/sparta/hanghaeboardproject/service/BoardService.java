package com.sparta.hanghaeboardproject.service;

import com.sparta.hanghaeboardproject.domain.Board;
import com.sparta.hanghaeboardproject.domain.BoardDto;
import com.sparta.hanghaeboardproject.domain.BoardRepository;
import com.sparta.hanghaeboardproject.domain.PasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    @Transactional
    public Long update(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        board.update(boardDto);
        return board.getId();
    }

    //비밀번호 일치 확인
//    @Transactional
//    public void checkPw(Long id, PasswordDto passwordDto) {
//        Optional<Board> optional = BoardRepository.findById(id);
//        Board board = optional.get();
//        String realPassword = board.getPassword();
//
//        if(passwordDto.getPassword().equals(realPassword)){
//            System.out.println("Success");
//        }else{
//            System.out.println("failed");
//            System.out.println(realPassword);
//            System.out.println(passwordDto.getPassword());
//        }
//    }

}
