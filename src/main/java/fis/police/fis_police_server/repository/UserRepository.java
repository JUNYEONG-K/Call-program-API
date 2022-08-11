package fis.police.fis_police_server.repository;

import fis.police.fis_police_server.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
    날짜 : 2022/01/10 10:25 오전
    작성자 : 원보라
    작성내용 : user repository interface 기본 메서드(save, findById)
*/
@Repository
public interface UserRepository {
    void save(User user);
    User findById(Long id);
    List<User> findByNickname(String nickname);
    List<User> findAll();
}
