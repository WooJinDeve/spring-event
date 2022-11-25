package com.event.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CommentSaveRequest {

    @NotBlank(message = "댓글은 1자 이상 100자 이하여야 합니다.")
    String message;

    @Builder
    public CommentSaveRequest(final String message) {
        this.message = message;
    }
}
