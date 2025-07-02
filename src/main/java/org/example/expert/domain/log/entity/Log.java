package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.log.enums.ActionType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "log")    // 요구사항 - DB 테이블명: log
public class Log {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requesterUserId;   // 요청한 유저

    @Enumerated(EnumType.STRING)
    private ActionType action;

    private Long todoId;

    private Long managerUserId;  // (매니저로) 등록된 유저

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Log(Long requesterUserId, ActionType action, Long todoId, Long managerUserId) {
        this.requesterUserId = requesterUserId;
        this.action = action;
        this.todoId = todoId;
        this.managerUserId = managerUserId;
    }
}
