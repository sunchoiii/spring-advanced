package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class adminTimeAop {

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..))")
    private void comment() {
    }

    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    private void user() {
    }

    // 이 중 하나라도 성립하면 실행
    // args(users, ..)는 매개변수가 users 로 시작하는 메서드 모두 실행한다는 뜻
    @Before("(comment() || user()) && args(authUser, ..)")
    public void execute(JoinPoint joinPoint, AuthUser authUser) {
        // 컨트롤러의 매개변수를 가져온다
        Object[] args = joinPoint.getArgs();
        String url = "";
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                url = ((HttpServletRequest) arg).getRequestURI();
            }
        }
        log.info("::접근 ID : {}::", authUser.getId());
        log.info("::요청 시각 : {}::", LocalDateTime.now());
        log.info("::요청 URL : {}::", url);

    }

}

