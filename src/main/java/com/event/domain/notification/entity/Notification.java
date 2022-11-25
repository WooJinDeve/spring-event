package com.event.domain.notification.entity;

import com.event.domain.comment.entity.Message;
import com.event.domain.member.entity.Member;
import com.event.global.enbed.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Notification extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_member", nullable = false)
    private Member toMember;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_member")
    private Member fromMember;
    @Embedded
    private Message message;
    private String URL;

    private boolean confirm;

    @Builder
    public Notification(Member toMember, Member fromMember, String message, String URL) {
        this.toMember = toMember;
        this.fromMember = fromMember;
        this.message = new Message(message);
        this.URL = URL;
    }

    public static Notification of(Member toMember, Member fromMember, String message, String URL){
        return new Notification(toMember, fromMember, message, URL);
    }

    public boolean hasFromMember(){
        return Objects.isNull(fromMember);
    }
}
