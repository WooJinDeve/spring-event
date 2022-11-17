package com.event.domain.coupon.entity;


import com.event.domain.member.entity.Member;
import com.event.global.enbed.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "Coupon")
@NoArgsConstructor(access = PROTECTED)
public class CouponEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Coupon coupon;

    private boolean softRemoved;

    @Builder
    public CouponEntity(Long id, Member member, Coupon coupon) {
        this.id = id;
        this.member = member;
        this.coupon = coupon;
    }

    public static CouponEntity of(Member member, Coupon coupon){
        return CouponEntity.builder()
                .member(member)
                .coupon(coupon)
                .build();
    }


    public void validateOwner(final Long id){
        if (member.getId() != id)
            throw new IllegalArgumentException("해당 회원에 쿠폰이 아닙니다.");
    }

    public void changePretendingToBeRemoved() {
        this.softRemoved = true;
    }
}
