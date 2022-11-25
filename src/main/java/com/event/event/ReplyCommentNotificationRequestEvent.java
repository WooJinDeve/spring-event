package com.event.event;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplyCommentNotificationRequestEvent {
    private Long toMember;
    private Long fromMember;
    private Long replyId;

    @Builder
    public ReplyCommentNotificationRequestEvent(Long toMember, Long fromMember, Long replyId) {
        this.toMember = toMember;
        this.fromMember = fromMember;
        this.replyId = replyId;
    }
}
