package fis.police.fis_police_server.controller.controllerImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@CrossOrigin
@RestController
public class SessionInfoController {
    //세션 정보 출력
    @CrossOrigin
    @GetMapping("/login")
    public String sessionInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); //406
            return "세션이 없습니다.";
        }
//        session.getAttributeNames().asIterator()
//                .forEachRemaining(name -> log.info("session name={},value={}", name, session.getAttribute(name)));

        log.debug("sessionId={}", session.getId());
        log.debug("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.debug("creationTime={}", new Date(session.getCreationTime()));//세션 생성 시간
        log.debug("lastAccessedTime={}", new Date(session.getLastAccessedTime()));//마지막 세션 접근 시간
        log.debug("isNew={}", session.isNew());

        response.setStatus(HttpServletResponse.SC_OK); //200
        return "세션 출력";
    }
}
