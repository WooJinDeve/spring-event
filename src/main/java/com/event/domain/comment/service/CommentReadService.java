package com.event.domain.comment.service;

import com.event.domain.comment.dto.CommentResponse;
import com.event.domain.comment.dto.ReplyResponse;
import com.event.domain.comment.entity.Comment;
import com.event.domain.comment.repsotiroy.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentReadService {

    private final CommentRepository commentRepository;


    public List<CommentResponse> findComment(int page){
        return commentRepository.findAllByParentIsNull(PageRequest.of(page, 20)).stream()
                .map(this::convertToCommentResponse)
                .collect(Collectors.toList());
    }

    public CommentResponse convertToCommentResponse(Comment comment){
        if(comment.isSoftRemoved()){
            return CommentResponse.of(comment, convertToRepliesResponse(comment));
        }
        return CommentResponse.of(comment, comment.getMember(), convertToRepliesResponse(comment));
    }

    public List<ReplyResponse> convertToRepliesResponse(Comment parent){
        return commentRepository.findRepliesByParent(parent).stream()
                .map(comment -> ReplyResponse.of(comment, comment.getMember()))
                .collect(Collectors.toList());
    }
}
