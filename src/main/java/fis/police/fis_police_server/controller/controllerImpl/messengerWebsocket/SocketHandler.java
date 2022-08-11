package fis.police.fis_police_server.controller.controllerImpl.messengerWebsocket;

import fis.police.fis_police_server.domain.Messenger;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.domain.enumType.UserAuthority;
import fis.police.fis_police_server.repository.UserRepository;
import fis.police.fis_police_server.service.MessengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler {

    private final MessengerService messengerService;
    private final UserRepository userRepository;
    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();

    private static void logging(WebSocketSession session, String websocketKey, String errorMsg, boolean check) {
        Long userId = (Long) session.getAttributes().get("loginUser");
        //true 이면 info false 이면 error log
        if(check){
            log.info("[로그인 id값: {}] [sessionId: {}] [요청: 성공]", userId, websocketKey);
        }
        else {
            log.error("[로그인 id값: {}] [sessionId: {}] [에러정보: {}]", userId, websocketKey, errorMsg);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            super.afterConnectionEstablished(session);
            sessionMap.put(session.getId(), session);

            // 전에 있던 메세지들 보내주기
            Long user_id = (Long) session.getAttributes().get("loginUser");
            User requestUser = userRepository.findById(user_id);

            messengerService.getMessenger(requestUser).stream()
                    .forEach(msg -> {
                        User user = msg.getUser();
                        if (requestUser.getU_auth() == UserAuthority.ADMIN || requestUser.getId().equals(user.getId())) {
                            String textMsg = msg.getContext() + " " + msg.getSendTime() + " " + user.getU_name() + " " + msg.getId();
                            try {
                                session.sendMessage(new TextMessage(textMsg));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            logging(session, session.getId(), "", true);
        } catch (Exception exception) {
            logging(session, session.getId(), "웹소캣에서 예기치 않은 오류 발생", false);
            exception.printStackTrace();
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            String msg = message.getPayload();
            log.info("session = {} 에서 message = {} 보냄", session, msg);
            Map<String, Object> attribute = session.getAttributes();
            Long user_id = (Long) attribute.get("loginUser");
            User user = userRepository.findById(user_id);
            log.info("[(userId, userName): ({},{})] [웹소켓 메시지 전송 발생] [메시지 내용: {}]", user_id, user.getU_name(), msg);

            Messenger messenger = new Messenger(msg, user);
            messengerService.saveMessenger(messenger);

            for (String key : sessionMap.keySet()) {
                WebSocketSession wss = sessionMap.get(key);
                // admin 과 보낸 사용자 에게만 보낸다.
                user_id = (Long) wss.getAttributes().get("loginUser");
                User receiveUser = userRepository.findById(user_id);
                if (receiveUser.getU_auth() == UserAuthority.ADMIN || wss.equals(session)) {
                    wss.sendMessage(new TextMessage(messenger.getContext() + " " + messenger.getSendTime() + " " + user.getU_name() + " " + messenger.getId()));
                }
            }
            logging(session, session.getId(), "", true);
        }catch (Exception e){
            logging(session, session.getId(), "웹소켓 메시지 전송 도중 예기치 않은 오류 발생", false);
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.remove(session.getId());
        Long userId = (Long) session.getAttributes().get("loginUser");
        log.info("[로그인 id값: {}] [sessionId: {}] [연결종료 완료됨]", userId, session.getId());
        super.afterConnectionClosed(session, status);
    }
}
