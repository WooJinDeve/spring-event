package com.event.presentation.usecase;

import com.event.domain.coupon.entity.Coupon;
import com.event.domain.coupon.service.CouponService;
import com.event.domain.mail.service.MailService;
import com.event.domain.member.dto.MemberRequest;
import com.event.domain.member.entity.Member;
import com.event.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateMemberUsecase {

    private final MemberService memberService;
    private final MailService mailService;
    private final CouponService couponService;

    @Transactional
    public Long execute(MemberRequest request){

        //회원 가입
        Member member = memberService.signUp(request);

        //메일 전송
        mailService.send(member.getEmail());

        //쿠폰 발급
        couponService.publish(member, Coupon.signUpCoupon());

        return member.getId();
    }
}
