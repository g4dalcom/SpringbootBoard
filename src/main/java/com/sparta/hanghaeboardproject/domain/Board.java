package com.sparta.hanghaeboardproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String password;


    //  기본 생성자
    public Board(String title, String writer, String contents, String password) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.password = password;
    }

    //  게시글 작성시 이용할 DTO
    public Board(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.writer = boardDto.getWriter();
        this.contents = boardDto.getContents();
        this.password = boardDto.getPassword();
    }

    //  게시글 수정시 이용할 DTO
    public void update(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.writer = boardDto.getWriter();
        this.contents = boardDto.getContents();
        this.password = boardDto.getPassword();
    }
}
