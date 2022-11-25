package com.event.domain.comment.dto;

import com.event.domain.comment.entity.Comment;
import com.event.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private static final String DELETE_COMMENT_MESSAGE = "삭제된 댓글 입니다.";
    private static final String DELETE_COMMENT_NAME = "USER";

    private Long id;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
    private boolean doesExist;
    private List<ReplyResponse> replies;

    @Builder
    public CommentResponse(final Long id, final String nickname, final String content, final LocalDateTime createdAt, final boolean doesExist, final List<ReplyResponse> replies) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
        this.doesExist = doesExist;
        this.replies = replies;
    }

    public static CommentResponse of(final Comment comment, final Member member, List<ReplyResponse> replies){
        return CommentResponse.builder()
                .id(comment.getId())
                .nickname(member.getNickname())
                .content(comment.getMessage().getValue())
                .createdAt(comment.getCreateAt())
                .doesExist(true)
                .replies(replies)
                .build();
    }

    public static CommentResponse of(final Comment comment, List<ReplyResponse> replies){
        return CommentResponse.builder()
                .id(comment.getId())
                .nickname(DELETE_COMMENT_NAME)
                .content(DELETE_COMMENT_MESSAGE)
                .createdAt(comment.getCreateAt())
                .doesExist(false)
                .replies(replies)
                .build();
    }
}
