package org.example.expert.global.common;

import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.test.util.ReflectionTestUtils;


public class DataSetting {
    public static AuthUser authUser = new AuthUser(1L, "a@a.com", UserRole.ADMIN);
    public static long todoId = 1L;
    public static long managerId = 2L;

    // 유저 생성
    public static User createUser() {
        AuthUser authUser = new AuthUser(1L, "a@a.com", UserRole.USER);
        User user = User.fromAuthUser(authUser);
        return user;
    }

    // 일정 생성
    public static Todo createTodo() {
        Todo todo = new Todo("Test Title", "Test Contents", "Sunny", createUser());
        return todo;
    }

    // 매니저 객체 생성 - 일정과 연결
    public static Manager createManagerWithTodo() {
        Manager manager = new Manager();
        ReflectionTestUtils.setField(manager, "id", managerId);
        ReflectionTestUtils.setField(manager, "todo", createTodo());
        return manager;
    }

}
