package fis.police.fis_police_server.controller.controllerImpl.messengerWebsocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
    날짜 : 2022/01/17 4:08 오후
    작성자 : 현승구
    작성내용 : 웹소켓 설정 정보 작성
*/

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final SocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/messenger/websocket")
            .addInterceptors(new HandshakeInterceptor())
                .setAllowedOrigins("http://3.35.135.214")
                .setAllowedOriginPatterns("http://3.35.135.214", "*");
    }
}
