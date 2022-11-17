package com.event.domain.coupon.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Getter
@Embeddable
@NoArgsConstructor
public class Coupon {
    private static final int SIGNUP_COUPON_DISCOUNT = 30000;
    private static final Pattern DISCOUNT_ONLY_NUM = Pattern.compile("[0-9]+");
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CouponType type;

    @Column(nullable = false)
    private int discount;

    @Builder
    public Coupon(final CouponType type, final int discount) {
        validate(discount);
        this.type = type;
        this.discount = discount;
    }

    private static Coupon of(final CouponType type, final int discount) {
        return Coupon.builder()
                .type(type)
                .discount(discount)
                .build();
    }

    private static Coupon signUpCoupon() {
        return Coupon.builder()
                .type(CouponType.FIX)
                .discount(SIGNUP_COUPON_DISCOUNT)
                .build();
    }

    private void validate(final int discount) {
        Matcher valid = DISCOUNT_ONLY_NUM.matcher(String.valueOf(discount));
        if (!valid.matches())
            throw new IllegalArgumentException("할인은 오직 숫자만 입력할 수 있습니다.");
    }
}
