package fis.police.fis_police_server.repository.repoImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import fis.police.fis_police_server.domain.*;
import fis.police.fis_police_server.domain.enumType.Accept;
import fis.police.fis_police_server.domain.enumType.AgentStatus;
import fis.police.fis_police_server.dto.CenterSelectDateResponseDTO;
import fis.police.fis_police_server.dto.QAgentLocation;
import fis.police.fis_police_server.dto.QCenterSelectDateResponseDTO;
import fis.police.fis_police_server.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/*
    작성날짜: 2022/01/10 10:56 AM
    작성자: 이승범
    작성내용: AgentRepositoryImpl 생성
*/
@Repository
@RequiredArgsConstructor
public class AgentRepositoryImpl implements AgentRepository {

    private final EntityManager em;

    private final JPAQueryFactory jpaQueryFactory;
    QSchedule qSchedule =QSchedule.schedule;
    QCenter qCenter = QCenter.center;
    QAgent qAgent = QAgent.agent;




    @Override
    public Agent findById(Long id) {
        return em.find(Agent.class, id);
    }

    @Override
    public List<Agent> findByA_code(String a_code) {
        return em.createQuery("select a from Agent a where a.a_code =:a_code", Agent.class)
                .setParameter("a_code", a_code)
                .getResultList();
    }

    @Override
    public List<Agent> findAll() {
        return em.createQuery("select a from Agent a", Agent.class).getResultList();
    }

    @Override
    public void save(Agent agent) {
        em.persist(agent);
    }

    @Override
    public List<CenterSelectDateResponseDTO> findNearAgent(Double latitude, Double longitude, Long range) {
        Double latitude_l = latitude - (0.009D * range);
        Double latitude_h = latitude + (0.009D * range);
        Double longitude_l = longitude - (0.009D * range);
        Double longitude_h = longitude + (0.009D * range);
//        return em.createQuery
//                ("select distinct agent from Agent agent " +
//                        "left join fetch agent.scheduleList as schedule " +
//                        "left join fetch schedule.center " +
//                        "where agent.a_latitude < :latitude_h and agent.a_latitude > :latitude_l " +
//                        "and agent.a_longitude < :longitude_h and agent.a_longitude > :longitude_l " +
//                        "and agent.a_status = :a_status", Agent.class)
//                    .setParameter("latitude_h", latitude_h)
//                    .setParameter("latitude_l", latitude_l)
//                    .setParameter("longitude_l", longitude_l)
//                    .setParameter("longitude_h", longitude_h)
//                    .setParameter("a_status", AgentStatus.WORK)
//                    .getResultList();
        return jpaQueryFactory
                .select(new QCenterSelectDateResponseDTO(qAgent.agent.id, qAgent.a_name, qAgent.a_ph, qAgent.a_code, qAgent.a_address, qAgent.a_hasCar, qAgent.a_equipment, qAgent.a_receiveDate, qAgent.a_latitude, qAgent.a_longitude))
                .from(qAgent)
                .leftJoin(qAgent.scheduleList, qSchedule)
                .distinct()
                .where((qAgent.a_status.eq(AgentStatus.WORK))
                        .and(qAgent.a_latitude.lt(latitude_h))
                        .and(qAgent.a_latitude.gt(latitude_l))
                        .and(qAgent.a_longitude.lt(longitude_h))
                        .and(qAgent.a_longitude.gt(longitude_l))
                )
                .fetch();
    }

    @Override
    public List<Agent> findByNickname(String nickname) {
        return em.createQuery("select a from Agent a where a.a_nickname = :nickname", Agent.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    /*
        날짜 : 2022/02/17 5:15 오후
        작성자 : 원보라
        작성내용 : 프로필 사진 삭제 (null 로 바꿈)
    */
    @Override
    public void deletePicture(Long agent_id) {
        em.createQuery("update Agent a set a.a_picture=null where a.id= :agent_id")
                .setParameter("agent_id", agent_id)
                .executeUpdate();
    }

}


//    select * from Agent where a_latitude < h and a_latitude > l and a_longitude < h and a_longitude > l and a_status = "WORK"