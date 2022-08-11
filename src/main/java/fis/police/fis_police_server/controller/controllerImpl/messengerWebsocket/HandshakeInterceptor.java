package fis.police.fis_police_server.controller.controllerImpl.messengerWebsocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/*
    날짜 : 2022/01/17 4:08 오후
    작성자 : 현승구
    작성내용 : HandshakeInterceptor 코드 작성
*/

@Slf4j
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        // 위의 파라미터 중, attributes 에 값을 저장하면 웹소켓 핸들러 클래스의 WebSocketSession에 전달된다
        try {
            ServletServerHttpRequest ssreq = (ServletServerHttpRequest) request;
            HttpServletRequest req =  ssreq.getServletRequest();
            // HttpSession 에 저장된 이용자의 아이디를 추출하는 경우
            Long user_id = (Long) req.getSession().getAttribute("loginUser");
            attributes.put("loginUser", user_id);
            log.info("[Before Handshake] [요청 Userid: {}]", user_id);
            return super.beforeHandshake(request, response, wsHandler, attributes);
        } catch (Exception e) {
            ServletServerHttpRequest ssreq = (ServletServerHttpRequest) request;
            HttpServletRequest req =  ssreq.getServletRequest();
            // HttpSession 에 저장된 이용자의 아이디를 추출하는 경우
            Long user_id = (Long) req.getSession().getAttribute("loginUser");
            log.error("[Before Handshake] [요청 Userid: {}] [오류: {}]", user_id ,e.getMessage());
            return false;
        }

    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        ServletServerHttpRequest ssreq = (ServletServerHttpRequest) request;
        //형변환 필요 session 건드리기 위해서
        HttpServletRequest req =  ssreq.getServletRequest();
        Long user_id = (Long) req.getSession().getAttribute("loginUser");
        log.info("[after Handshake] [요청 Userid: {}]", user_id);
        super.afterHandshake(request, response, wsHandler, ex);
    }

}
