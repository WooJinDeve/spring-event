package com.event.presentation.usecase;

import com.event.domain.comment.dto.CommentSaveRequest;
import com.event.domain.comment.entity.Comment;
import com.event.domain.comment.repsotiroy.CommentRepository;
import com.event.domain.comment.service.CommentWriteService;
import com.event.domain.member.entity.Member;
import com.event.domain.member.repository.MemberRepository;
import com.event.event.ReplyCommentNotificationRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateChildCommentUseCase {

    private final MemberRepository memberRepository;
    private final CommentWriteService commentWriteService;
    private final CommentRepository commentRepository;

    private final ApplicationEventPublisher publisher;

    public Long execute(final Long id, final Long memberId, final CommentSaveRequest request){
        Member member = memberRepository.getById(memberId);
        Comment parent = commentRepository.getById(id);
        Long commentId = commentWriteService.saveReply(parent, member, request);

        publisher.publishEvent(new ReplyCommentNotificationRequestEvent(member.getId(), parent.getMember().getId(), commentId));

        return commentId;
    }
}
