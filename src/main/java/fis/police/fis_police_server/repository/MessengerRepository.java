package fis.police.fis_police_server.repository;

import fis.police.fis_police_server.domain.Messenger;
import fis.police.fis_police_server.domain.User;

import java.util.List;

//현승구
public interface MessengerRepository {
    // 시설 추가
    void save(Messenger messenger);

    // 시설 찾기
    Messenger findById(Long id);

    // 시설 삭제
    void delete(Long id);

    List<Messenger> findAll();

    List<Messenger> findByUser(User user);
}
