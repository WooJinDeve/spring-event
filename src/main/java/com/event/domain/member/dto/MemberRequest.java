package com.event.domain.member.dto;

import com.event.global.enbed.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequest {

    private Email email;

    private String nickname;

    @Builder
    public MemberRequest(final Email email,final String nickname) {
        this.email = email;
        this.nickname = nickname;
    }


}
