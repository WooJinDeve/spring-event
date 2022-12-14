# 스프링 @EventListener
### 개요

- 어플리케이션을 만들다보면 한 로직에 많은 기능이 결합된 경우가 생긴다.
    - 예를들어 회원가입 시 **가입 축하 이메일과 쿠폰을 제공**해준다고 가정한다면, 밑과 같은 로직이 생성될 것이다.

```java
@Service
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;
    private final MailService mailService;
    private final CouponService couponService;

    @Transactional
    public Long signUp(MemberRequest request){
        //회원 가입
        Member member = memberService.memberRepository(request);

        //메일 전송
        mailService.send(member.getEmail());

        //쿠폰 발급 & 에러 발생
        couponService.publish(member, Coupon.signUpCoupon());
				
        return member.getId();
    }
}
```

- 하지만, 이러한 로직은 **@Transaction 어노테이션**이 걸린 상태라면 치명적인 오류가 생긴다.
    - 가입을 위한 로직이지만, 이메일 발송 또는 쿠폰 제공에서 오류가 생겨도 가입이 안된다는 오류일 것이다.
- 이러한 문제 발생 및 가입 로직에 맞지않은 추가적인 기능 때문에 로직에 결합을 줄일 필요가 생긴다.

### @Transaction 제거

- `@Transaction`으로 인한 롤백을 막기위해, 상위 서비스 코드를 생성하면 문제를 해결 할 수 있다.
    - 쿠폰 발급에서 에러가 발생하더라도 회원가입과 메일전송은 성공이 된다.
- 하지만, 메일 및 쿠폰 발급에 오랜 시간이 걸린다면 회원가입에 대한 시간 또한 매우 길어질 것이다. 또한, 아직도 회원가입시 메일 및 쿠폰발급에 대한 의존도가 높은 코드이다.

```java
@Service
@RequiredArgsConstructor
public class CreateMemberUsecase {

    private final MemberService memberService;
    private final MailService mailService;
    private final CouponService couponService;

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
```

### @EventListener

- 스프링은 이러한 로직에 대해 이벤트 기반 코드를 제공하고 있다.

[EventListener (Spring Framework 6.0.0 API)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/event/EventListener.html)

- 해당 문서에는 이러한 설명이 적혀있다
    - 비동기 이벤트 리스너가 예외를 발생시키면 호출자에게 전파되지 않습니다.
- 회원가입시 이메일, 쿠폰로직이 실패하더라도 회원가입은 성공적으로 이뤄질 수 있게된다.

```java
@Getter
public class SignUpRequestEvent {

    private Member member;
    private Email email;

    @Builder
    public SignUpRequestEvent(Member member, Email email) {
        this.member = member;
        this.email = email;
    }
}

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

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateMemberUsecase {

    private final MemberService memberService;
    private final ApplicationEventPublisher publisher;

    public Long execute(MemberRequest request){
        Member member = memberService.signUp(request);

        //비동기로 이메일 발송 및 쿠폰 발급
        publisher.publishEvent(new SignUpRequestEvent(member, member.getEmail()));

        return member.getId();
    }
}
```