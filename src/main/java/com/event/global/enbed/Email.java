package com.event.global.enbed;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor
public class Email {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");
    private String email;

    @Builder
    public Email(final String email) {
        validate(email);
        this.email = email;
    }

    public static Email of(final String email){
        return Email.builder()
                .email(email)
                .build();
    }

    private void validate(final String email){
        Matcher matcher = EMAIL_PATTERN.matcher(email);

        if (!matcher.matches()){
            throw new IllegalArgumentException("잘못된 이메일 패턴입니다.");
        }
    }
}
