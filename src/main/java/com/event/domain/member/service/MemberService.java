package com.event.domain.member.service;

import com.event.domain.member.dto.MemberRequest;
import com.event.domain.member.entity.Member;
import com.event.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member signUp(MemberRequest request){
        validateSave(request);
        return memberRepository.save(Member.of(request.getEmail(), request.getNickname()));
    }

    private void validateSave(MemberRequest request) {
        memberRepository.validateExistByEmail(request.getEmail());
        memberRepository.validateExistByNickname(request.getNickname());
    }
}
