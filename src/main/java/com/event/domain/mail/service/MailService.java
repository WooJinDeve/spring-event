package com.event.domain.mail.service;

import com.event.domain.mail.entity.Mail;
import com.event.domain.mail.repository.MailRepository;
import com.event.global.enbed.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MailService {

    private final MailRepository mailRepository;

    @Transactional
    public Long send(Email email){
        Mail mail = mailRepository.save(Mail.of(email));
        return mail.getId();
    }
}
