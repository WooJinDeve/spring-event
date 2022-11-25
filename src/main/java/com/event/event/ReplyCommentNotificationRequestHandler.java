package com.event.event;

import com.event.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyCommentNotificationRequestHandler {

    private final NotificationService notificationService;

    @Async
    @EventListener
    public void publish(ReplyCommentNotificationRequestEvent event){
        notificationService.saveNotification(event.getToMember(), event.getFromMember(), event.getReplyId());
    }
}
