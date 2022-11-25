package com.event.domain.notification.entity;

import com.event.global.enbed.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Notification extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long toMember;
    private Long fromMember;

     private Long commentId;

    private boolean confirm;

    @Builder
    public Notification(final Long toMember,final Long fromMember,final Long commentId) {
        this.toMember = toMember;
        this.fromMember = fromMember;
        this.commentId = commentId;
    }

    public static Notification of(final Long toMember,final Long fromMember,final Long commentId){
        return new Notification(toMember, fromMember, commentId);
    }

    public boolean hasFromMember(){
        return Objects.isNull(fromMember);
    }
}
