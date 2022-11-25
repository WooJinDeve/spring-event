package com.event.domain.notification.service;

public class NotificationUrlFactory {

    private static final String REPLY_COMMENT_URL = "/api/member/%d/comment";

    public static String REPLY_COMMENT_URL(Long id){
        return String.format(REPLY_COMMENT_URL, id);
    }
}
