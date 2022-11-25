package com.event.event;

import com.event.domain.comment.entity.Comment;
import com.event.domain.comment.repsotiroy.CommentRepository;
import com.event.domain.member.entity.Member;
import com.event.domain.member.repository.MemberRepository;
import com.event.domain.notification.service.NotificationService;
import com.event.domain.notification.service.NotificationUrlFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentNotificationRequestHandler {

    private final NotificationService notificationService;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Async
    @EventListener
    public void publish(ReplyCommentNotificationRequestEvent event){
        Member toMember = memberRepository.getById(event.getComment().getMember().getId());
        Comment reply = commentRepository.getById(event.getReplyId());
        notificationService.saveNotification(toMember, event.getFromMember(), reply.getMessage().getValue(), NotificationUrlFactory.REPLY_COMMENT_URL(event.getReplyId()));
    }
}
