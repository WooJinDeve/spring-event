package com.event.domain.member.entity;


import com.event.global.enbed.BaseEntity;
import com.event.global.enbed.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {
    private static final int NICKNAME_MAXLENGTH = 10;

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    @Column(unique = true, nullable = false)
    private Email email;

    @Column(unique = true ,length = 10, nullable = false)
    private String nickname;

    @Builder
    public Member(Long id, Email email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public static Member of(final Email email, final String nickname){
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }

    private void validate(final String nickname){
        if (nickname.length() >= NICKNAME_MAXLENGTH){
            throw new IllegalArgumentException("닉네임의 최대 길이는 10자보다 작아야합니다");
        }
    }
}
