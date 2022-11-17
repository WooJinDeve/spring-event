package com.event.presentation.usecase;

import com.event.domain.member.dto.MemberRequest;
import com.event.domain.member.entity.Member;
import com.event.domain.member.service.MemberService;
import com.event.event.SignUpRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateMemberUsecase {

    private final MemberService memberService;
    private final ApplicationEventPublisher publisher;

    public Long execute(MemberRequest request){
        //회원 가입
        Member member = memberService.signUp(request);

        //비동기로 이메일 발송 및 쿠폰 발급
        publisher.publishEvent(new SignUpRequestEvent(member, member.getEmail()));

        return member.getId();
    }
}
