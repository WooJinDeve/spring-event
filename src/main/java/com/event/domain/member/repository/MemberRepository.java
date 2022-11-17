package com.event.domain.member.repository;

import com.event.domain.member.entity.Member;
import com.event.global.enbed.Email;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getById(final Long id){
        return findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    boolean existsByEmail(Email email);

    boolean existsByNickname(String nickname);

    Optional<Member> findByEmail(Email email);

    default void validateExistByEmail(final Email email){
        if (existsByEmail(email))
            throw new DuplicateRequestException("중복된 이메일이 존재합니다.");
    }

    default void validateExistByNickname(final String nickname){
        if (existsByNickname(nickname))
            throw new DuplicateRequestException("중복된 닉네임이 존재합니다.");
    }

}
