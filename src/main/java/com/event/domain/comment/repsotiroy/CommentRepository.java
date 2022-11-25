package com.event.domain.comment.repsotiroy;

import com.event.domain.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.NoSuchElementException;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"member"})
    List<Comment> findAllByParentIsNull(Pageable pageable);

    @EntityGraph(attributePaths = {"member"})
    List<Comment> findRepliesByParent(final Comment parent);

    void deleteByMemberId(final Long memberId);

    boolean existsById(final Long id);

    default void validateExistsById(final Long id){
        if (!existsById(id)){
            throw new NoSuchElementException("존재하지 않는 댓글입니다.");
        }
    }

    default Comment getById(final Long id){
        return findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 댓글입니다."));
    }
}
