package com.event.domain.comment.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Message {

    private static final int MAX_MESSAGE_LENGTH = 100;

    @Column(name = "message", nullable = false, length = 100)
    private String value;

    @Builder
    public Message(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value){
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("댓글의 내용이 존재하지 않습니다.");
        }
        if (value.length() > MAX_MESSAGE_LENGTH){
            throw new IllegalArgumentException("댓글의 길이가 제한을 초과합니다.");
        }
    }
}
