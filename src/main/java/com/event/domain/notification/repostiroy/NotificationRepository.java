package com.event.domain.notification.repostiroy;

import com.event.domain.notification.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /*
    * 이전 알람 정보
    * */
    List<Notification> findAllByToMemberIdOrderByIdDesc(Long toMemberId, Pageable pageable);

    /*
    * 최신 알람 정보
    * */
    List<Notification> findAllByToMemberIdAndConfirmIsFalseOrderByIdDesc(Long toMemberId);



    @Query(value = "UPDATE Notification n SET n.confirm = true WHERE n.id = :id")
    void checkedTheNotification(@Param("id") Long id);
}
