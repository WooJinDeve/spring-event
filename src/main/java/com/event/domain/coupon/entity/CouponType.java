package com.event.domain.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouponType {
    FIX("고정 할인"),
    PERCENT("퍼센트 할인");

    private String description;
}
