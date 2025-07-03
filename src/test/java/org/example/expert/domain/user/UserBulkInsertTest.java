package org.example.expert.domain.user;

import jakarta.persistence.EntityManager;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserBulkInsertTest {

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    @Rollback(false)
    void generateMillionUsers() {
        int total = 1_000_000;
        int batchSize = 1_000;

        for (int i = 0; i < total; i++) {
            String email = "email" + i + "@example.com";
            String nickname = "nickname" + i;
            User user = new User(email, "1234", nickname, UserRole.USER);

            em.persist(user);

            if (i % batchSize == 0) {
                em.flush();
                em.clear();
            }
        }

        em.flush();
    }
}
