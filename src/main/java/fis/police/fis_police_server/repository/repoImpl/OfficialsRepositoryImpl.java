package fis.police.fis_police_server.repository.repoImpl;

import fis.police.fis_police_server.domain.Officials;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.repository.OfficialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/*
    작성 날짜: 2022/02/14 11:46 오전
    작성자: 고준영
    작성 내용: 시설 담당자 저장, 조회
*/
@Repository
@RequiredArgsConstructor
public class OfficialsRepositoryImpl implements OfficialsRepository {

    private final EntityManager em;


    @Override
    public void saveOfficials(Officials officials) {
        em.persist(officials);
    }

    @Override
    public Officials findById(Long id) {
        return em.find(Officials.class, id);
    }

    @Override
    public List<Officials> findAll() {
        return em.createQuery("select o from Officials o", Officials.class)
                .getResultList();
    }

    @Override
    public List<Officials> findByNickname(String nickname) {
        return em.createQuery("select o from Officials o where o.o_nickname = :nickname", Officials.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
