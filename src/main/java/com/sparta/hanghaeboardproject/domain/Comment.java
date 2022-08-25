package com.sparta.hanghaeboardproject.domain;

import com.sparta.hanghaeboardproject.dto.CommentRequestDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String comment;


    @ManyToOne
    private Board board;

    @ManyToOne
    private Account account;

    public void updateComment(String tempComment) {
        this.comment = tempComment;
    }
}