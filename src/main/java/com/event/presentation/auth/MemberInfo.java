package com.event.presentation.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfo {

    private Long id;

    @Builder
    public MemberInfo(Long id) {
        this.id = id;
    }
}
