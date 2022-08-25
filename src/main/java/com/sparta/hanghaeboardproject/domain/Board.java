package com.sparta.hanghaeboardproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaeboardproject.dto.BoardDto;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    // @JoinColumn(name = "account_id")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();

    public void updateBoard(BoardDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setBoard(this);
    }

    public void deleteComment(Comment deleteComment) {
        this.comments.remove(deleteComment);
        deleteComment.setBoard(null);
    }

    public void deleteComments(List<Comment> commentList) {
        this.comments.removeAll(commentList);
    }
}
