package com.event.domain.mail.entity;

import com.event.global.enbed.BaseEntity;
import com.event.global.enbed.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Mail extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Email email;

    @Builder
    public Mail(Long id, Email email) {
        this.id = id;
        this.email = email;
    }

    public static Mail of(final Email email){
        return Mail.builder()
                .email(email)
                .build();
    }
}
