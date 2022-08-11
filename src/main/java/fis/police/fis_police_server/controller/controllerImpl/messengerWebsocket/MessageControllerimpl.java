package fis.police.fis_police_server.controller.controllerImpl.messengerWebsocket;

import fis.police.fis_police_server.service.MessengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageControllerimpl {

    private final MessengerService messengerService;

    private static void logging(HttpServletRequest request, String url, String errorMsg, boolean check) {
        Long userId = (Long) request.getSession().getAttribute("loginUser");
        //true 이면 info false 이면 error log
        if(check){
            log.info("[로그인 id값: {}] [url: {}] [요청: 성공]", userId, url);
        }
        else {
            log.error("[로그인 id값: {}] [url: {}] [에러정보: {}]", userId, url, errorMsg);
        }
    }

    @GetMapping("message/{message_id}")
    private void delete(@PathVariable Long message_id, HttpServletRequest request){
        try {
            messengerService.deleteMessenger(message_id);
            logging(request, "message/" + message_id, "", true);
        } catch (Exception e){
            logging(request, "message/" + message_id, "메세지 삭제 실패", false);
        }

    }
}
