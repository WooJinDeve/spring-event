package com.event.domain.comment.dto;

import com.event.domain.comment.entity.Comment;
import com.event.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReplyResponse {

    private Long id;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public ReplyResponse(final Long id, final String nickname,  final String content, final LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static ReplyResponse of(Comment comment, Member member){
        return ReplyResponse.builder()
                .id(comment.getId())
                .nickname(member.getNickname())
                .content(comment.getMessage().getValue())
                .createdAt(comment.getCreateAt())
                .build();
    }
}
