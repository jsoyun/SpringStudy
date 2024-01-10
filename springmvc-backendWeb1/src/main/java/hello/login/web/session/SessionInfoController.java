package hello.login.web.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {
    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }
        //세션 데이터 출력
        session.getAttributeNames().asIterator()
            .forEachRemaining(name -> log.info("session name={},value={}", name, session.getAttribute(name)));
        log.info("sessionId ={}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval()); //비활성화시키는 최대인터벌
        log.info("creationTime={}", new Date(session.getCreationTime())); //세션 생성일자
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime())); //마지막에 이세션에 접근한 시간
        log.info("isNew={}", session.isNew()); //이미 생성된 세션을 가져다가 썼다.

        return "세션 출력";
    }
}
