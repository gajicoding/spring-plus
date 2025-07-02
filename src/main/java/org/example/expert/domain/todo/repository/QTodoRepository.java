package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class QTodoRepository {
    private final JPAQueryFactory queryFactory;

    public Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne());
    }

    public Page<TodoSearchResponse> searchTodos(String title, String managerNickname, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {

        BooleanBuilder builder = createSearchPredicate(title, managerNickname, startDateTime, endDateTime);

        List<TodoSearchResponse> content = fetchTodoSearchResponses(builder, pageable);

        long total = fetchTotalCount(builder);

        return new PageImpl<>(content, pageable, total);
    }

    private List<TodoSearchResponse> fetchTodoSearchResponses(BooleanBuilder builder, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,
                        todo.title,
                        JPAExpressions
                                .select(manager.countDistinct())
                                .from(manager)
                                .where(manager.todo.eq(todo)),
                        JPAExpressions
                                .select(comment.countDistinct())
                                .from(comment)
                                .where(comment.todo.eq(todo))
                ))
                .from(todo)
                .where(builder)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private long fetchTotalCount(BooleanBuilder predicate) {
        Long total = queryFactory
                .select(todo.countDistinct())
                .from(todo)
                .where(predicate)
                .fetchOne();

        return total != null ? total : 0L;
    }

    private BooleanBuilder createSearchPredicate(
            String title,
            String managerNickname,
            LocalDateTime start,
            LocalDateTime end
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        if (title != null && !title.isBlank()) {
            builder.and(todo.title.contains(title));
        }

        if (managerNickname != null && !managerNickname.isBlank()) {
            builder.and(todo.managers.any().user.nickname.contains(managerNickname));
        }

        if (start != null) {
            builder.and(todo.createdAt.goe(start));
        }

        if (end != null) {
            builder.and(todo.createdAt.loe(end));
        }

        return builder;
    }
}
