package com.event.domain.comment.entity;

import com.event.domain.member.entity.Member;
import com.event.global.enbed.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = ALL)
    private List<Comment> children = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Embedded
    private Message message;

    @Column(nullable = false)
    private boolean softRemoved;

    @Builder
    public Comment(final Comment parent,final  Member member,final String message) {
        this.parent = parent;
        this.member = member;
        this.message = new Message(message);
    }

    public static Comment parent(final Member member,final String message){
        return new Comment(null, member, message);
    }

    public static Comment child(final Comment parent,final Member member,final String message){
        return new Comment(parent, member, message);
    }

    public void verifyOwner(final Long userId){
        Assert.isTrue(userId.equals(this.member.getId()), "댓글의 작성자만 삭제할 수 있습니다.");
    }

    public void deleteChild(final Comment reply){
        this.children.remove(reply);
    }

    public boolean isParent(){
        return Objects.isNull(this.parent);
    }

    public boolean hasNoReply(){
        return this.children.isEmpty();
    }

    public void changePretendingToBeRemoved(){
        this.softRemoved = true;
    }
}
