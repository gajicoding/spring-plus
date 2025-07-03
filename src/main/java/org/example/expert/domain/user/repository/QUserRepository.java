package org.example.expert.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class QUserRepository {

    private final JPAQueryFactory queryFactory;

    public List<String> findByNickname(String nickname) {
        return queryFactory.select(user.nickname)
                .from(user)
                .where(user.nickname.eq(nickname))
                .fetch();
    }
}
