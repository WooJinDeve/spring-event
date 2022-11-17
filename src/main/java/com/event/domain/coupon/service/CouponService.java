package com.event.domain.coupon.service;

import com.event.domain.coupon.entity.Coupon;
import com.event.domain.coupon.entity.CouponEntity;
import com.event.domain.coupon.repository.CouponRepository;
import com.event.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void publish(Member member, Coupon coupon){
        couponRepository.save(CouponEntity.of(member, coupon));
    }
}
