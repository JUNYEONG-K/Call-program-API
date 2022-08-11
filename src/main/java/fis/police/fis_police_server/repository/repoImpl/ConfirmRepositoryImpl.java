package fis.police.fis_police_server.repository.repoImpl;

import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Confirm;
import fis.police.fis_police_server.domain.Schedule;
import fis.police.fis_police_server.domain.enumType.Complete;
import fis.police.fis_police_server.repository.ConfirmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

/*
    작성 날짜: 2022/02/14 11:46 오전
    작성자: 고준영
    작성 내용: 확인서 저장, 조회
*/
@Repository
@RequiredArgsConstructor
public class ConfirmRepositoryImpl implements ConfirmRepository {

    private final EntityManager em;

    // 확인서 제출 후 저장 (요원별로 저장)
    @Override
    public void save(Confirm confirm) {
        em.persist(confirm);
    }

    @Override
    public Confirm findById(Long id) {
        return em.find(Confirm.class, id);
    }

    // 해당 스케쥴에 대한 확인서(결재 상관 없이 일단 뽑음) 조회
    // (만약 해당 스케쥴을 2명 이상이 수행하여, 확인서가 2개 이상 나오기 때문에 list 처리. service 에서 하나로 봉합해야함)
    @Override
    public List<Confirm> findSameCenterDate(Center center, String visit_date) {
        return em.createQuery("select c from Confirm c where c.center =: center and c.visit_date =: visit_date", Confirm.class)
                .setParameter("center", center)
                .setParameter("visit_date", visit_date)
                .getResultList();
    }
    public List<Confirm> findSameCenterDateV2(Center center, String visit_date) {
        return em.createQuery("select c from Confirm c where c.center = : center and c.visit_date = : visit_date", Confirm.class)
                .setParameter("center", center)
                .setParameter("visit_date", visit_date)
                .getResultList();
    }

    // 시설 담당자의 확인서 결재 (확인서의 컬럼 값을 complete 로 바꿔주기)
    @Override
    @Modifying
    public void updateConfirmComplete(Long confirm_id, Complete complete, String name) {
        em.createQuery("update Confirm confirm set confirm.complete = : complete, confirm.center_manger =: name where confirm.id = : confirm_id")
                .setParameter("confirm_id", confirm_id)
                .setParameter("complete", complete)
                .setParameter("name", name)
                .executeUpdate();
    }

    // 과거 방문 이력 조회 => 시설에 대한 모든 확인서
    @Override
    public List<Confirm> findCompleteConfirmListForCenter(Center center) {
        return em.createQuery("select c from Confirm c where c.center = : center", Confirm.class)
                .setParameter("center", center)
                .getResultList();
    }

    // 현장요원별에 대한 모든 확인서 (결재 완료인)
    @Override
    public List<Confirm> findCompleteConfirmListForAgent(Complete complete, Agent agent) {
        return em.createQuery("select c from Confirm c where c.agent = : agent and c.complete = : complete", Confirm.class)
                .setParameter("agent", agent)
                .setParameter("complete", complete)
                .getResultList();
    }

}
