package com.event.presentation.controller;

import com.event.domain.comment.dto.CommentResponse;
import com.event.domain.comment.dto.CommentSaveRequest;
import com.event.domain.comment.service.CommentReadService;
import com.event.domain.comment.service.CommentWriteService;
import com.event.presentation.auth.AuthPrincipal;
import com.event.presentation.auth.MemberInfo;
import com.event.presentation.usecase.CreateChildCommentUseCase;
import com.event.presentation.usecase.CreateParentCommentUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentReadService commentReadService;
    private final CommentWriteService commentWriteService;
    private final CreateParentCommentUsecase createParentCommentUsecase;
    private final CreateChildCommentUseCase createChildCommentUseCase;

    @PostMapping("/members/{id}/comment")
    public ResponseEntity<Void> addComment(@PathVariable(name = "id") Long memberId,
                                           @AuthPrincipal MemberInfo memberInfo,
                                           @RequestBody @Valid CommentSaveRequest commentSaveReqDto) {
        createParentCommentUsecase.execute(memberId, commentSaveReqDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comment/{id}/reply")
    public ResponseEntity<?> addReply(@PathVariable Long id,
                                      @AuthPrincipal MemberInfo memberInfo,
                                      @RequestBody @Valid CommentSaveRequest commentSaveReqDto) {
        createChildCommentUseCase.execute(id, memberInfo.getId(), commentSaveReqDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/members/{id}/comment")
    public ResponseEntity<?> findComments(@RequestParam int page) {
        List<CommentResponse> comment = commentReadService.findComment(page);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id,
                                              @AuthPrincipal MemberInfo memberInfo) {
        commentWriteService.delete(id, memberInfo.getId());
        return ResponseEntity.noContent().build();
    }
}
