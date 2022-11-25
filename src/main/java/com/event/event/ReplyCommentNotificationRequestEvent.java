package com.event.event;

import com.event.domain.comment.entity.Comment;
import com.event.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplyCommentNotificationRequestEvent {
    private Member fromMember;
    private Comment comment;
    private Long replyId;

    @Builder
    public ReplyCommentNotificationRequestEvent(Member fromMember, Comment comment, Long replyId) {
        this.fromMember = fromMember;
        this.comment = comment;
        this.replyId = replyId;
    }
}
