package com.example.axolotl.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;

import javax.servlet.http.HttpSession;
import java.util.Set;

public class SessionUtils {

    // Обновляем сессию в базе данных (перезаписываем атрибут)
    public static void updateSessionSecurityContext(HttpSession httpSession) {
        SecurityContext auth = SecurityContextHolder.getContext();
        httpSession.setAttribute("SPRING_SECURITY_CONTEXT", auth);
    }

    // Удаляет сессию пользователя по его username
    public static void deleteSessionByUsername(String username, JdbcIndexedSessionRepository sessionRepository) {
        Set<String> sessionIds = sessionRepository.findByPrincipalName(username).keySet();
        for (String sessionId : sessionIds) {
            sessionRepository.deleteById(sessionId);
        }
    }
}
