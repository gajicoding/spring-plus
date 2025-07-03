package org.example.expert.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.enums.ActionType;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)  // 새 트랜잭션을 시작
    public void saveLog(Long requesterUserId, ActionType action, Long todoId, Long registeredUserId) {
        logRepository.save(new Log(requesterUserId, action, todoId, registeredUserId));
    }
}
