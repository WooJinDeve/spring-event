package com.event.event;

import com.event.domain.member.entity.Member;
import com.event.global.enbed.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpRequestEvent {

    private Member member;
    private Email email;

    @Builder
    public SignUpRequestEvent(Member member, Email email) {
        this.member = member;
        this.email = email;
    }
}
