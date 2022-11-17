package com.event.presentation.controller;

import com.event.domain.member.dto.MemberRequest;
import com.event.presentation.usecase.CreateMemberUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final CreateMemberUsecase createMemberUsecase;

    @PostMapping("/signup")
    public Long signUp(@RequestBody MemberRequest memberRequest){
        return createMemberUsecase.execute(memberRequest);
    }
}
