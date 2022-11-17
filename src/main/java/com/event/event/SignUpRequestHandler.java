package com.event.event;

import com.event.domain.coupon.entity.Coupon;
import com.event.domain.coupon.service.CouponService;
import com.event.domain.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpRequestHandler {

    private final MailService mailService;
    private final CouponService couponService;

    @Async
    @Order(1)
    @EventListener
    public void send(SignUpRequestEvent event){
        mailService.send(event.getEmail());
    }

    @Async
    @Order(2)
    @EventListener
    public void publish(SignUpRequestEvent event){
        couponService.publish(event.getMember(), Coupon.signUpCoupon());
    }
}
