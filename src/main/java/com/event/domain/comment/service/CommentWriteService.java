package com.event.domain.comment.service;

import com.event.domain.comment.dto.CommentSaveRequest;
import com.event.domain.comment.entity.Comment;
import com.event.domain.comment.repsotiroy.CommentRepository;
import com.event.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentWriteService {

    private final CommentRepository commentRepository;

    public Long saveComment(final Member member, final CommentSaveRequest request){
        Comment parent = Comment.parent(member, request.getMessage());
        return commentRepository.save(parent).getId();
    }

    public Long saveReply(final Comment parent, final Member member, final CommentSaveRequest request){
        Comment child = validateReplyForSave(parent, member, request);
        return commentRepository.save(child).getId();
    }

    public Comment validateReplyForSave(final Comment parent, final Member member, final CommentSaveRequest request){
        if(!parent.isParent())
            throw new IllegalArgumentException();
        return Comment.child(parent, member, request.getMessage());
    }


    public void delete(final Long commentId, final Long memberId){
        Comment comment = commentRepository.getById(commentId);
        comment.verifyOwner(memberId);
        deleteDelegate(comment);
    }

    private void deleteDelegate(Comment comment){
        if(comment.isParent()){
            deleteParent(comment);
            return;
        }
        deleteChild(comment);
    }
    private void deleteParent(Comment comment) {
        if (comment.hasNoReply()) {
            commentRepository.delete(comment);
            return;
        }
        comment.changePretendingToBeRemoved();
    }

    private void deleteChild(Comment comment) {
        Comment parent = comment.getParent();
        parent.deleteChild(comment);
        commentRepository.delete(comment);

        if (parent.hasNoReply() && parent.isSoftRemoved()) {
            commentRepository.delete(parent);
        }
    }
}
