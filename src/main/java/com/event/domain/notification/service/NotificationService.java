package com.event.domain.notification.service;

import com.event.domain.member.entity.Member;
import com.event.domain.notification.entity.Notification;
import com.event.domain.notification.repostiroy.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void saveNotification(Member toMember, Member fromMember, String message, String URL){
        Notification notification = Notification.of(toMember, fromMember, message, URL);
        notificationRepository.save(notification);
    }
}
