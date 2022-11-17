package com.event.domain.member.repository;

import com.event.domain.member.entity.Member;
import com.event.global.enbed.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getById(final Long id){
        return findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    Optional<Member> findByEmail(Email email);
}
