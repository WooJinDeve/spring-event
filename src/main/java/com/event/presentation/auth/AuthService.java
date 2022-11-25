package com.event.presentation.auth;

import com.event.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.TypeMismatchException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public Long extractMemberId(String accessInfo){
        Long memberId = validateTypeMissMatch(accessInfo);
        return memberRepository.getById(memberId).getId();
    }

    private Long validateTypeMissMatch(String type){
        try {
            return Long.parseLong(type);
        }catch (TypeMismatchException e){
            throw new IllegalArgumentException();
        }
    }
}
