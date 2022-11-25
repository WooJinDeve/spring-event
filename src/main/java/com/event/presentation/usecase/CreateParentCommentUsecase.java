package com.event.presentation.usecase;

import com.event.domain.comment.dto.CommentSaveRequest;
import com.event.domain.comment.service.CommentWriteService;
import com.event.domain.member.entity.Member;
import com.event.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateParentCommentUsecase {

    private final CommentWriteService commentWriteService;
    private final MemberRepository memberRepository;

    public Long execute(final Long memberId,final CommentSaveRequest commentSaveRequest){
        Member member = memberRepository.getById(memberId);
        return commentWriteService.saveComment(member, commentSaveRequest);
    }
}
