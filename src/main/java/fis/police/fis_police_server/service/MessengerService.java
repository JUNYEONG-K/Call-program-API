package fis.police.fis_police_server.service;

import fis.police.fis_police_server.domain.Messenger;
import fis.police.fis_police_server.domain.User;

import java.util.List;

public interface MessengerService {

    // 메신져 추기
    void saveMessenger(Messenger messenger);

    // 메신져 삭제(보기완료)
    void deleteMessenger(Long id);

    // 메신져 조회
    List<Messenger> getMessenger(User user);
}
