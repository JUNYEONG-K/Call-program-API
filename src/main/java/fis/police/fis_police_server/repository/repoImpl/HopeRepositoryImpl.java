package fis.police.fis_police_server.repository.repoImpl;

import fis.police.fis_police_server.domain.Hope;
import fis.police.fis_police_server.domain.enumType.Complete;
import fis.police.fis_police_server.repository.HopeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/*
    작성 날짜: 2022/02/14 11:46 오전
    작성자: 고준영
    작성 내용: 지문등록 신청서 저장, 조회
*/
@Repository
@RequiredArgsConstructor
public class HopeRepositoryImpl implements HopeRepository {

    private final EntityManager em;

    @Override
    public void saveHope(Hope hope) {
        em.persist(hope);
    }

    @Override
    public Hope findById(Long id) {
        return em.find(Hope.class, id);
    }

    @Override
    public List<Hope> listOfHope() {
        return em.createQuery("select h from Hope h", Hope.class)
                .getResultList();
    }

    @Override
    @Modifying
    public void updateHopeComplete(Long id, Complete complete) {
        em.createQuery("update Hope hope set hope.complete =: complete where hope.id =: id")
                .setParameter("complete", complete)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Hope> findHopesByCenter(Long id) {
        return em.createQuery("select h from Hope h where h.center.id =: id", Hope.class)
                .setParameter("id", id)
                .getResultList();
    }
}
